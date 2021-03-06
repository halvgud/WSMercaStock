<?php
class SUCURSAL
{
    const TABLA_SUCURSAL = 'ms_sucursal';
    const ID_TABLA = 'idSucursal';
    const DESC_TABLA = 'nombre';
    const ESTADO_EXITO = 100;
    const ESTADO_ERROR = 101;
    const ESTADO_ERROR_BD = 102;
    const ESTADO_MALA_SINTAXIS = 103;
    const ESTADO_NO_ENCONTRADO = 104;


    public static function post($peticion)
    {
     if (empty($peticion[0])){
            return self::obtenerSucursal();
        }else if ($peticion[0] == 'login') {
    return self::login();
}
        else if($peticion[0]=='seleccionar'){
                return self::setearConexion();
        }
        else if($peticion[0]=='sucursalHost'){
            return self::obtenerSucursalHost();
        }


    }

    public static function obtenerSucursal()
{
    try {
        $post = json_decode(file_get_contents('php://input'),true);
        $comando = "SELECT ".self::ID_TABLA.",".self::DESC_TABLA.",claveApi FROM " . self::TABLA_SUCURSAL ." where direccion=:direccion";

        // Preparar sentencia
        $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
        // Ligar idContacto e idUsuario
        $sentencia->bindParam("direccion",$post['direccion']);
        //$sentencia->bindParam(2, $nombre, PDO::PARAM_INT);

        // Ejecutar sentencia preparada
        if ($sentencia->execute()) {
            http_response_code(200);
            $arreglo=$sentencia->fetchAll(PDO::FETCH_ASSOC);
            return (["estado" => self::ESTADO_EXITO,"datos" => $arreglo]);
        } else
            throw new ExcepcionApi(self::ESTADO_ERROR, "Se ha producido un error");

    } catch (PDOException $e) {
        throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
    }
    finally{
        ConexionBD::obtenerInstancia()->_destructor();
    }
}
    public static function obtenerSucursalHost()
    {
        try {

            $comando = "select  direccion from ms_sucursal;";

            // Preparar sentencia
            $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
            // Ligar idContacto e idUsuario
            //$sentencia->bindParam(1, $idTabla, PDO::PARAM_INT);
            //$sentencia->bindParam(2, $nombre, PDO::PARAM_INT);

            // Ejecutar sentencia preparada
            if ($sentencia->execute()) {
                http_response_code(200);
                $arreglo=$sentencia->fetchAll(PDO::FETCH_ASSOC);
                return (["estado" => self::ESTADO_EXITO,"datos" => $arreglo]);
            } else
                throw new ExcepcionApi(self::ESTADO_ERROR, "Se ha producido un error");

        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
        }
        finally{
            ConexionBD::obtenerInstancia()->_destructor();
        }
    }
    public static function setearConexion(){
        $post = json_decode(file_get_contents('php://input'),true);
        if(isset($post['direccion'])){
            $comando = "select claveApi from ms_sucursal where UPPER(direccion)=:direccion";
            try{
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                $sentencia->bindParam("direccion",$post['direccion']);
                $sentencia->execute();
                $resultado = $sentencia->fetchAll(PDO::FETCH_ASSOC);
                if($resultado){
                    $_SESSION['DB']=$resultado[0]['claveApi'];
                    return (["estado" => self::ESTADO_EXITO,"datos" => $resultado]);
                }
            }catch(PDOException $e){}finally{$db=null;}
        }
    }
    public static function login()
    {
         $post = json_decode(file_get_contents('php://input'),true);
        if($post['usuario']=="admin"&& $post['password']=="sysadmin11"){
            $comando = "select idSucursal,usuario,password from ms_sucursal where idSucursal=:idSucursal";
            try {
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                $sentencia->bindParam("idSucursal", $post['idSucursal']);
                if ($sentencia->execute()) {
                    http_response_code(200);
                    return ($sentencia->fetchAll(PDO::FETCH_ASSOC));
                } else
                    throw new ExcepcionApi(self::ESTADO_ERROR, "Se ha producido un error");

            } catch (PDOException $e) {
                throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
            } finally {
                ConexionBD::obtenerInstancia()->_destructor();
            }
        }else{
            throw new ExcepcionApi(401, "error",401);
        }

    }

}