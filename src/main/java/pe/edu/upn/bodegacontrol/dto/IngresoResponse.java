package pe.edu.upn.bodegacontrol.dto;

import pe.edu.upn.bodegacontrol.entity.IngresoStock;

import java.time.LocalDateTime;

public record IngresoResponse(Long id, Long productoId, String codigo, String producto, Integer cantidad,
                              Integer stockAnterior, Integer stockNuevo, String observacion,
                              String usuario, LocalDateTime fecha) {

    public static IngresoResponse de(IngresoStock i) {
        return new IngresoResponse(i.getId(), i.getProducto().getId(), i.getProducto().getCodigo(),
                i.getProducto().getNombre(), i.getCantidad(), i.getStockAnterior(), i.getStockNuevo(),
                i.getObservacion(), i.getUsuario().getNombre(), i.getFecha());
    }
}
