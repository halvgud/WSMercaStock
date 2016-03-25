<?php
class CATEGORIA
{
    const TABLA_INVENTARIO = 'ms_inventario';
    const TABLA_ARTICULO = 'articulo';
    const TABLA_CATEGORIA = 'categoria';
    const ID_INVENTARIO = 'idInvientario';
    const ID_ARTICULO = 'art_id';
    const ID_CATEGORIA = 'cat_id';
    
   
    const ESTADO_EXITO = 100;
    const ESTADO_ERROR = 101;
    const ESTADO_ERROR_BD = 102;
    const ESTADO_MALA_SINTAXIS = 103;
    const ESTADO_NO_ENCONTRADO = 104;

    const DESCRIPCION='nombre';

    public static function get($peticion)
    {


        if (empty($peticion[0]))
            return self::obtenerCategoria();
        else
            http_response_code(404);

    }

    public static function obtenerCategoria()
    {
        try {

                $comando = "SELECT d.".self::DESCRIPCION.",count(*)  AS CANTIDAD FROM " . self::TABLA_ARTICULO . " A INNER JOIN ".self::TABLA_INVENTARIO."
                MI ON ( MI.".self::ID_ARTICULO."=A.".self::ID_ARTICULO.") INNER JOIN ".self::TABLA_CATEGORIA." D ON ( D.".self::ID_CATEGORIA."=A.".self::ID_CATEGORIA.")
                group by d.nombre"
                ;

                // Preparar sentencia
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                // Ligar idContacto e idUsuario
                //$sentencia->bindParam(1, $idTabla, PDO::PARAM_INT);
                //$sentencia->bindParam(2, $nombre, PDO::PARAM_INT);

            // Ejecutar sentencia preparada
            if ($sentencia->execute()) {
                http_response_code(200);
                return
                    [
                        "estado" => self::ESTADO_EXITO,
                        "datos" => $sentencia->fetchAll(PDO::FETCH_ASSOC)
                    ];
            } else
                throw new ExcepcionApi(self::ESTADO_ERROR, "Se ha producido un error");

        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
        }
    }

}