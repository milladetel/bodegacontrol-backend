package pe.edu.upn.bodegacontrol.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import pe.edu.upn.bodegacontrol.entity.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record VentaResponse(Long id, String numero, LocalDateTime fecha, String cajero, String metodoPago,
                            BigDecimal total, BigDecimal montoRecibido, BigDecimal vuelto, int items,
                            List<DetalleVentaResponse> detalles) {

    /** Versión completa, con el detalle de productos. */
    public static VentaResponse de(Venta v) {
        return new VentaResponse(v.getId(), v.getNumero(), v.getFecha(), v.getUsuario().getNombre(),
                v.getMetodoPago().name(), v.getTotal(), v.getMontoRecibido(), v.getVuelto(),
                v.getDetalles().size(), v.getDetalles().stream().map(DetalleVentaResponse::de).toList());
    }

    /** Versión resumida para listados (sin detalle). */
    public static VentaResponse resumen(Venta v) {
        return new VentaResponse(v.getId(), v.getNumero(), v.getFecha(), v.getUsuario().getNombre(),
                v.getMetodoPago().name(), v.getTotal(), v.getMontoRecibido(), v.getVuelto(),
                v.getDetalles().size(), null);
    }
}
