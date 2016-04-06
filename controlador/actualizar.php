<?php
class ACTUALIZAR
{
    const TABLA_INVENTARIO = 'ms_inventario';
    //const TABLA_ARTICULO = 'articulo';
    //const TABLA_CATEGORIA = 'categoria';
    //const ID_INVENTARIO = 'idInvientario';
    const ID_ARTICULO = 'art_id';
    //const ID_CATEGORIA = 'cat_id';
    //const SERVICIO='servicio';
    //const EXISTENCIA='existencia';
   //const UNIDAD='nombre';
    const ID_ESTADO = 'idEstado';
    //const TABLA_PARAMETRO = 'ms_parametro';
    //const PARAMETRO_FILTRO = 'FILTRO';
    //const ACCION_FILTRO = 'CONFIGURACION_ARTICULO';
    //const VALOR_FILTRO = 'valor';
    const ESTADO_EXITO = 100;
    const ESTADO_ERROR = 101;
    const ESTADO_ERROR_BD = 102;
    const ESTADO_MALA_SINTAXIS = 103;
    const ESTADO_NO_ENCONTRADO = 104;
    const ESTADO_URL_INCORRECTA=105;
    const DESCRIPCION='descripcion';

    public static function post($peticion)
    {
        if ($peticion != '') {
            return self::cambiarEstado();
        }
        else {
            throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
        }
    }

    public static function cambiarEstado()
    {
        try {
            $post = json_decode(file_get_contents('php://input'),true);//ID_CATEGORIA
            //return var_dump($post);
                $comando = "UPDATE ".self::TABLA_INVENTARIO." SET idEstado ='X
                ' WHERE idInventario='".$post['idInventario']."'";

                // Preparar sentencia
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                // Ligar idContacto e idUsuario<
                //$sentencia->bindParam(1, $idTabla, PDO::PARAM_INT);
                //$sentencia->bindParam(2, $nombre, PDO::PARAM_INT);
                //for ($i = 0 ;$i<100000000;$i++){
                    
                    
                //}
                
            // Ejecutar sentencia preparada
            if ($sentencia->execute()) {
                http_response_code(200);
                return
                    [
                        "estado" => self::ESTADO_EXITO,
                        "datos" => $sentencia->rowCount()
                    ];
            } else
                throw new ExcepcionApi(self::ESTADO_ERROR, "Se ha producido un error");

        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
        }
    }

}