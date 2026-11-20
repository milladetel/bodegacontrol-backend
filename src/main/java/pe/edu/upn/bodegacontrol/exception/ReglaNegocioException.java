package pe.edu.upn.bodegacontrol.exception;

/** Se lanza cuando los datos tienen formato correcto pero violan una regla del negocio (HTTP 409). */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
