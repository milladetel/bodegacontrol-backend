package pe.edu.upn.bodegacontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upn.bodegacontrol.dto.DashboardResponse;
import pe.edu.upn.bodegacontrol.dto.ProductoResponse;
import pe.edu.upn.bodegacontrol.dto.VentaResponse;
import pe.edu.upn.bodegacontrol.entity.EstadoStock;
import pe.edu.upn.bodegacontrol.entity.Producto;
import pe.edu.upn.bodegacontrol.repository.ProductoRepository;
import pe.edu.upn.bodegacontrol.repository.VentaRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/** Indicadores del día y del mes para la pantalla principal. */
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final ProductoService productoService;

    @Transactional(readOnly = true)
    public DashboardResponse resumen() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioHoy = hoy.atStartOfDay();
        LocalDateTime finHoy = hoy.atTime(LocalTime.MAX);
        LocalDateTime inicioMes = hoy.withDayOfMonth(1).atStartOfDay();

        BigDecimal ventasHoy = ventaRepository.sumarTotal(inicioHoy, finHoy);
        long operacionesHoy = ventaRepository.countByFechaBetween(inicioHoy, finHoy);
        BigDecimal ticket = operacionesHoy == 0 ? BigDecimal.ZERO
                : ventasHoy.divide(BigDecimal.valueOf(operacionesHoy), 2, RoundingMode.HALF_UP);
        BigDecimal ventasMes = ventaRepository.sumarTotal(inicioMes, finHoy);

        List<Producto> activos = productoRepository.findByActivoTrueOrderByCodigoAsc();
        BigDecimal valorInventario = activos.stream()
                .map(p -> p.getPrecioCompra().multiply(BigDecimal.valueOf(p.getStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        long bajo = activos.stream().filter(p -> p.getEstado() == EstadoStock.STOCK_BAJO).count();
        long agotados = activos.stream().filter(p -> p.getEstado() == EstadoStock.AGOTADO).count();
        long porVencer = activos.stream().filter(p -> p.venceEn(ProductoService.DIAS_ALERTA_VENCIMIENTO)).count();

        List<ProductoResponse> alertas = productoService.alertas();
        List<VentaResponse> ultimas = ventaRepository.findTop5ByOrderByFechaDesc().stream()
                .map(VentaResponse::resumen).toList();

        return new DashboardResponse(ventasHoy.setScale(2, RoundingMode.HALF_UP), operacionesHoy, ticket,
                ventasMes.setScale(2, RoundingMode.HALF_UP), valorInventario, activos.size(), bajo, agotados,
                porVencer, alertas, ultimas);
    }
}
