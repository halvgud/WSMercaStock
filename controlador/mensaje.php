<?php
class ACTUALIZAR_PARAMETRO
{
    const TABLA_PARAMETRO = 'ms_parametro';
    const VALOR = 'valor';
    const ESTADO_EXITO = 100;
    const ESTADO_ERROR = 101;
    const ESTADO_ERROR_BD = 102;
    const ESTADO_MALA_SINTAXIS = 103;
    const ESTADO_NO_ENCONTRADO = 104;
    const ESTADO_URL_INCORRECTA=105;

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
        ConexionBD::obtenerInstancia()->_destructor();
        try {
            ConexionBD::obtenerInstancia()->obtenerBD()->beginTransaction();
            $post = json_decode(file_get_contents('php://input'),true);
            //return var_dump($post);
                $comando = "UPDATE ".self::TABLA_PARAMETRO." SET ".self::VALOR." ='".$post['valor']."' WHERE parametro='CONFIRMACION_MENSAJE_GUARDADO'";
                // Preparar sentencia
                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
            // Ejecutar sentencia preparada
            if ($sentencia->execute()) {
                http_response_code(200);
                ConexionBD::obtenerInstancia()->obtenerBD()->commit();
                return
                    [
                        "estado" => self::ESTADO_EXITO,
                        "datos" => $sentencia->rowCount()
                    ];
            } else {
                ConexionBD::obtenerInstancia()->obtenerBD()->rollBack();
                throw new ExcepcionApi(self::ESTADO_ERROR, "Se ha producido un error");
            }

        } catch (PDOException $e) {
            ConexionBD::obtenerInstancia()->obtenerBD()->rollBack();
            throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
        }
        finally{
            ConexionBD::obtenerInstancia()->_destructor();
        }
    }
}