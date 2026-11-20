package pe.edu.upn.bodegacontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upn.bodegacontrol.dto.ItemVentaRequest;
import pe.edu.upn.bodegacontrol.dto.VentaRequest;
import pe.edu.upn.bodegacontrol.dto.VentaResponse;
import pe.edu.upn.bodegacontrol.entity.*;
import pe.edu.upn.bodegacontrol.exception.RecursoNoEncontradoException;
import pe.edu.upn.bodegacontrol.exception.ReglaNegocioException;
import pe.edu.upn.bodegacontrol.repository.VentaRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Registro de ventas. Toda la operación es transaccional: si un producto no tiene stock
 * suficiente, no se guarda la venta ni se descuenta nada del inventario.
 */
@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoService productoService;
    private final AuthService authService;

    @Transactional
    public VentaResponse registrar(VentaRequest req, String username) {
        Usuario cajero = authService.buscar(username);

        // Si el mismo producto llega dos veces se suman las cantidades en una sola línea.
        Map<Long, Integer> cantidades = new LinkedHashMap<>();
        for (ItemVentaRequest item : req.items()) {
            cantidades.merge(item.productoId(), item.cantidad(), Integer::sum);
        }

        Venta venta = Venta.builder()
                .fecha(LocalDateTime.now())
                .usuario(cajero)
                .metodoPago(req.metodoPago())
                .build();

        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> e : cantidades.entrySet()) {
            Producto producto = productoService.buscarActivo(e.getKey());
            int cantidad = e.getValue();
            if (producto.getStock() < cantidad) {
                throw new ReglaNegocioException("Stock insuficiente para " + producto.getNombre()
                        + ": disponible " + producto.getStock() + ", solicitado " + cantidad);
            }
            BigDecimal subtotal = producto.getPrecioVenta().multiply(BigDecimal.valueOf(cantidad));
            venta.agregarDetalle(DetalleVenta.builder()
                    .producto(producto)
                    .cantidad(cantidad)
                    .precioUnitario(producto.getPrecioVenta())
                    .subtotal(subtotal)
                    .build());
            producto.setStock(producto.getStock() - cantidad); // descuento automático de stock
            total = total.add(subtotal);
        }
        total = total.setScale(2, RoundingMode.HALF_UP);
        venta.setTotal(total);

        if (req.metodoPago() == MetodoPago.EFECTIVO) {
            if (req.montoRecibido() == null) {
                throw new ReglaNegocioException("Para pagos en efectivo debe indicar el monto recibido");
            }
            if (req.montoRecibido().compareTo(total) < 0) {
                throw new ReglaNegocioException("El monto recibido (S/ " + req.montoRecibido()
                        + ") es menor al total de la venta (S/ " + total + ")");
            }
            venta.setMontoRecibido(req.montoRecibido().setScale(2, RoundingMode.HALF_UP));
            venta.setVuelto(req.montoRecibido().subtract(total).setScale(2, RoundingMode.HALF_UP));
        } else {
            venta.setMontoRecibido(total);
            venta.setVuelto(BigDecimal.ZERO.setScale(2));
        }

        venta.setNumero(String.format("V-%05d", ventaRepository.maxId() + 1));
        return VentaResponse.de(ventaRepository.save(venta));
    }

    @Transactional(readOnly = true)
    public List<VentaResponse> listar(LocalDate desde, LocalDate hasta) {
        List<Venta> ventas;
        if (desde == null && hasta == null) {
            ventas = ventaRepository.findAllByOrderByFechaDesc();
        } else {
            LocalDate d = desde != null ? desde : LocalDate.of(2000, 1, 1);
            LocalDate h = hasta != null ? hasta : LocalDate.now();
            if (d.isAfter(h)) {
                throw new ReglaNegocioException("La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
            }
            ventas = ventaRepository.findByFechaBetweenOrderByFechaDesc(d.atStartOfDay(), h.atTime(LocalTime.MAX));
        }
        return ventas.stream().map(VentaResponse::resumen).toList();
    }

    @Transactional(readOnly = true)
    public VentaResponse obtener(Long id) {
        return ventaRepository.findById(id)
                .map(VentaResponse::de)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la venta con id " + id));
    }
}
