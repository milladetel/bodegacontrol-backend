package pe.edu.upn.bodegacontrol.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/** Formato único de error que devuelve la API. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(LocalDateTime timestamp, int status, String error, String mensaje, String ruta,
                            Map<String, String> errores) {
}
