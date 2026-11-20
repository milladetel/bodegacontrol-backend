package pe.edu.upn.bodegacontrol.dto;

import pe.edu.upn.bodegacontrol.entity.DetalleVenta;

import java.math.BigDecimal;

public record DetalleVentaResponse(Long productoId, String codigo, String producto, Integer cantidad,
                                   BigDecimal precioUnitario, BigDecimal subtotal) {

    public static DetalleVentaResponse de(DetalleVenta d) {
        return new DetalleVentaResponse(d.getProducto().getId(), d.getProducto().getCodigo(),
                d.getProducto().getNombre(), d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal());
    }
}
