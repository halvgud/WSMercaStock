<?php
/**
 * User: Edwin
 * Date: 2016-05-25
 * Time: 9:20
 */
class importar{
    public static function traducirMensaje($codigoDeError,$e)
    {

        if ($codigoDeError == "23000") {
            return "El usuario que intentó registrar ya existe, favor de validar la información";
        } else if ($codigoDeError == "HY093") {
            return 'El número de parámetros enviados es incorrecto, favor de contactar a Sistemas';
        } else if ($codigoDeError == '42S02') {
            return "Tabla inexistente, favor de contactar a Sistemas";
        } else {
            return $e->getMessage();
        }
    }
    public static function post($peticion)
    {
        if ($peticion[0] == 'parametro') {
            return self::importarParametro();
        } else if ($peticion[0] == 'usuario') {
            return self::importarUsuario();
        }
        else {
            throw new ExcepcionApi(404, "Url mal formada", 404);
        }
    }

    public static function importarUsuario()
    {
        $usuario = json_decode(file_get_contents('php://input'));
        // Preparar operaci�n de modificaci�n para cada contacto

        $comando = "insert into ms_usuario (usuario,contrasena,nombre,apellido,sexo,contacto,idSucursal,claveApi,idNivelAutorizacion,idEstado,fechaEstado,fechaSesion
      ,claveGCM) values (:usuario,:password,:nombre,:apellido,:sexo,:contacto,:idSucursal,:claveApi,:idNivelAutorizacion,:idEstado,now(),now(),'')
        on duplicate key update contrasena=:password,nombre=:nombre,apellido=:apellido,sexo=:sexo,contacto=:contacto,
        idSucursal=:idSucursal,idNivelAutorizacion=:idNivelAutorizacion,idEstado=:idEstado";
        // Preparar la sentencia update
        $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);




        // Procesar array de contactos
        $contador=0;
        foreach ($usuario->data as $usuarioRow) {
            $usuario =$usuarioRow->usuario;
            $password = $usuarioRow->password;
            $nombre = $usuarioRow->nombre;
            $apellido = $usuarioRow->apellido;
            $sexo = $usuarioRow->sexo;
            $contacto = $usuarioRow->contacto;
            $idNivelAutorizacion=$usuarioRow->idNivelAutorizacion;
            $idEstado = $usuarioRow->idEstado;
            $idSucursal =$usuarioRow->idSucursal;
            $claveApi="";
            // $claveGCM = $usuarioRow->claveGCM;
            $sentencia->bindParam("password", $password);
            $sentencia->bindParam("nombre", $nombre);
            $sentencia->bindParam("apellido", $apellido);
            $sentencia->bindParam("sexo", $sexo);
            $sentencia->bindParam("claveApi",$claveApi);
            $sentencia->bindParam("contacto", $contacto);
            $sentencia->bindParam("idNivelAutorizacion", $idNivelAutorizacion);
            $sentencia->bindParam("idEstado", $idEstado);
            //$sentencia->bindParam("claveGCM",$claveGCM);
            $sentencia->bindParam("usuario",$usuario);
            $sentencia->bindParam("idSucursal",$idSucursal);
            $sentencia->execute();
            $contador++;
        }
        return   $arreglo =[
            "estado" => 200,
            "success" => "",
            "data" => $contador
        ];

    }

    public static function importarParametro(){
        $postrequest = json_decode(file_get_contents('php://input'));

        $comando = "insert into ms_parametro (accion, parametro, valor, comentario, usuario, fechaActualizacion) values (:accion, :parametro, :valor, :comentario, :usuario, :fechaActualizacion)
        on duplicate key update accion=:accion, parametro=:parametro, valor=:valor, comentario=:comentario, usuario=:usuario, fechaActualizacion=:fechaActualizacion";


        try{
            $contador=0;
            if(isset($postrequest->data)){
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                foreach ($postrequest->data as $post) {
                    $sentencia->bindParam("accion", $post->accion);
                    $sentencia->bindParam("parametro", $post->parametro);
                    $sentencia->bindParam("valor", $post->valor);
                    $sentencia->bindParam("comentario", $post->comentario);
                    $sentencia->bindParam("usuario", $post->usuario);
                    $sentencia->bindParam("fechaActualizacion", $post->fechaActualizacion);
                    $sentencia->execute();
                    $contador++;
                }
                $resultado = $sentencia -> execute();
                if($resultado){
                    http_response_code(200);
                     return
                                [
                                    "estado" => 200,
                                    "success" => "",
                                    "data" => $resultado
                                ];

                        } else {
                            http_response_code(400);
                            return
                                [
                                    "estado" => "warning",
                                    "mensaje" => "",
                                    "data" => $resultado
                                ];

                          }//else
                }else {
                    throw new ExcepcionApi(401, "parametro data no recibido", 401);
                }
        }catch(PDOException $e){
            $codigoDeError=$e->getCode();
            $error =self::traducirMensaje($codigoDeError,$e);
            throw new ExcepcionApi($e->getCode(), $error, 401);
        }
    }

}//class