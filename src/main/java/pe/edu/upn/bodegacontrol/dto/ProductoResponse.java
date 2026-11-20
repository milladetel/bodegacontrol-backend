package pe.edu.upn.bodegacontrol.dto;

import pe.edu.upn.bodegacontrol.entity.Producto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductoResponse(
        Long id,
        String codigo,
        String nombre,
        String categoria,
        String categoriaEtiqueta,
        BigDecimal precioCompra,
        BigDecimal precioVenta,
        Integer stock,
        Integer stockMinimo,
        LocalDate fechaVencimiento,
        String estado,
        boolean porVencer) {

    public static ProductoResponse de(Producto p) {
        return new ProductoResponse(p.getId(), p.getCodigo(), p.getNombre(), p.getCategoria().name(),
                p.getCategoria().getEtiqueta(), p.getPrecioCompra(), p.getPrecioVenta(), p.getStock(),
                p.getStockMinimo(), p.getFechaVencimiento(), p.getEstado().name(), p.venceEn(30));
    }
}
