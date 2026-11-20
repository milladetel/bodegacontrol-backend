package pe.edu.upn.bodegacontrol.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IngresoRequest(
        @NotNull(message = "El producto es obligatorio")
        Long productoId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad a ingresar debe ser al menos 1")
        @Max(value = 10000, message = "La cantidad a ingresar no puede superar 10000 unidades")
        Integer cantidad,

        @Size(max = 150, message = "La observación admite hasta 150 caracteres")
        String observacion) {
}
