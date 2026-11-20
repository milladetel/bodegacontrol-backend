package pe.edu.upn.bodegacontrol.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        BigDecimal ventasHoy,
        long operacionesHoy,
        BigDecimal ticketPromedio,
        BigDecimal ventasMes,
        BigDecimal valorInventario,
        long totalProductos,
        long productosStockBajo,
        long productosAgotados,
        long productosPorVencer,
        List<ProductoResponse> alertas,
        List<VentaResponse> ultimasVentas) {
}
