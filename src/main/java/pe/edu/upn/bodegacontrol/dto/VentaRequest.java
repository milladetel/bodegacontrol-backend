package pe.edu.upn.bodegacontrol.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pe.edu.upn.bodegacontrol.entity.MetodoPago;

import java.math.BigDecimal;
import java.util.List;

public record VentaRequest(
        @NotEmpty(message = "La venta debe tener al menos un producto")
        List<@Valid ItemVentaRequest> items,

        @NotNull(message = "El método de pago es obligatorio")
        MetodoPago metodoPago,

        @DecimalMin(value = "0.00", message = "El monto recibido no puede ser negativo")
        BigDecimal montoRecibido) {
}
