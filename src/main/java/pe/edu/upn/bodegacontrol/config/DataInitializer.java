package pe.edu.upn.bodegacontrol.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upn.bodegacontrol.entity.*;
import pe.edu.upn.bodegacontrol.repository.ProductoRepository;
import pe.edu.upn.bodegacontrol.repository.UsuarioRepository;
import pe.edu.upn.bodegacontrol.repository.VentaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Carga datos de ejemplo de la Bodega Santa Rosa la primera vez que arranca la aplicación
 * (solo si la tabla de usuarios está vacía).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return;
        }
        Usuario admin = usuarioRepository.save(Usuario.builder().nombre("Carlos Mendoza").username("admin")
                .email("admin@bodegacontrol.pe").password(passwordEncoder.encode("admin123")).rol(Rol.ADMIN).build());
        Usuario cajera = usuarioRepository.save(Usuario.builder().nombre("María Quispe").username("cajero")
                .email("cajero@bodegacontrol.pe").password(passwordEncoder.encode("cajero123")).rol(Rol.CAJERO).build());

        LocalDate hoy = LocalDate.now();
        List<Producto> productos = productoRepository.saveAll(List.of(
                p("AB-001", "Arroz Costeño Extra 750 g", Categoria.ABARROTES, "3.20", "4.20", 48, 15, hoy.plusMonths(9)),
                p("AB-002", "Azúcar Rubia Cartavio 1 kg", Categoria.ABARROTES, "3.60", "4.50", 36, 12, hoy.plusMonths(12)),
                p("AB-003", "Aceite Primor Vegetal 1 L", Categoria.ABARROTES, "8.90", "10.50", 9, 10, hoy.plusMonths(6)),
                p("AB-004", "Fideos Don Vittorio Spaghetti 500 g", Categoria.ABARROTES, "2.40", "3.20", 40, 15, hoy.plusMonths(11)),
                p("AB-005", "Atún Florida Filete 170 g", Categoria.ABARROTES, "4.80", "6.00", 4, 12, hoy.plusMonths(18)),
                p("BE-001", "Inca Kola 1.5 L", Categoria.BEBIDAS, "5.20", "6.50", 24, 12, hoy.plusMonths(4)),
                p("BE-002", "Coca Cola 500 ml", Categoria.BEBIDAS, "2.30", "3.00", 30, 12, hoy.plusMonths(4)),
                p("BE-003", "Agua San Luis 625 ml", Categoria.BEBIDAS, "1.10", "1.50", 0, 12, hoy.plusMonths(10)),
                p("BE-004", "Cerveza Pilsen 630 ml", Categoria.BEBIDAS, "5.50", "7.00", 18, 10, hoy.plusMonths(5)),
                p("LA-001", "Leche Gloria Azul 400 g", Categoria.LACTEOS, "3.60", "4.30", 60, 20, hoy.plusMonths(7)),
                p("LA-002", "Yogurt Gloria Fresa 1 L", Categoria.LACTEOS, "5.40", "6.80", 7, 8, hoy.plusDays(12)),
                p("LA-003", "Queso Fresco Bonlé 250 g", Categoria.LACTEOS, "7.20", "9.00", 10, 5, hoy.plusDays(6)),
                p("LI-001", "Detergente Bolívar 900 g", Categoria.LIMPIEZA, "8.70", "10.90", 14, 6, null),
                p("LI-002", "Lejía Clorox 1 L", Categoria.LIMPIEZA, "2.90", "3.80", 3, 6, null),
                p("LI-003", "Papel Higiénico Elite x4", Categoria.LIMPIEZA, "4.20", "5.50", 22, 8, null),
                p("SN-001", "Papas Lay's Clásicas 42 g", Categoria.SNACKS, "1.40", "2.00", 35, 12, hoy.plusMonths(3)),
                p("SN-002", "Galletas Oreo 6 pack", Categoria.SNACKS, "3.50", "4.50", 26, 10, hoy.plusMonths(8)),
                p("SN-003", "Chocolate Sublime 30 g", Categoria.SNACKS, "1.50", "2.20", 0, 15, hoy.plusMonths(6))));

        // Ventas de los últimos días para que el historial y el dashboard tengan información.
        Random r = new Random(7);
        List<Producto> vendibles = productos.stream().filter(x -> x.getStock() > 15).toList();
        int n = 1;
        for (int d = 6; d >= 0; d--) {
            int ventasDia = d == 0 ? 6 : 3 + r.nextInt(4);
            for (int i = 0; i < ventasDia; i++) {
                LocalDateTime fecha = hoy.minusDays(d).atTime(8 + r.nextInt(5) + i, r.nextInt(60));
                if (fecha.isAfter(LocalDateTime.now())) {
                    fecha = LocalDateTime.now().minusMinutes(10L * (ventasDia - i));
                }
                Venta v = Venta.builder().numero(String.format("V-%05d", n++)).fecha(fecha)
                        .usuario(i % 3 == 2 ? admin : cajera)
                        .metodoPago(i % 4 == 3 ? MetodoPago.BILLETERA_DIGITAL : MetodoPago.EFECTIVO).build();
                BigDecimal total = BigDecimal.ZERO;
                int lineas = 1 + r.nextInt(3);
                for (int l = 0; l < lineas; l++) {
                    Producto pr = vendibles.get((n + l * 5) % vendibles.size());
                    if (v.getDetalles().stream().anyMatch(dv -> dv.getProducto() == pr)) {
                        continue;
                    }
                    int cant = 1 + r.nextInt(3);
                    BigDecimal sub = pr.getPrecioVenta().multiply(BigDecimal.valueOf(cant));
                    v.agregarDetalle(DetalleVenta.builder().producto(pr).cantidad(cant)
                            .precioUnitario(pr.getPrecioVenta()).subtotal(sub).build());
                    pr.setStock(pr.getStock() - cant);
                    total = total.add(sub);
                }
                v.setTotal(total.setScale(2, RoundingMode.HALF_UP));
                if (v.getMetodoPago() == MetodoPago.EFECTIVO) {
                    BigDecimal recibido = total.divide(BigDecimal.TEN, 0, RoundingMode.UP).multiply(BigDecimal.TEN);
                    v.setMontoRecibido(recibido.setScale(2));
                    v.setVuelto(recibido.subtract(total).setScale(2, RoundingMode.HALF_UP));
                } else {
                    v.setMontoRecibido(v.getTotal());
                    v.setVuelto(BigDecimal.ZERO.setScale(2));
                }
                ventaRepository.save(v);
            }
        }
        log.info("Datos de ejemplo cargados: {} usuarios, {} productos, {} ventas",
                usuarioRepository.count(), productoRepository.count(), ventaRepository.count());
    }

    private static Producto p(String codigo, String nombre, Categoria cat, String compra, String venta,
                              int stock, int minimo, LocalDate vence) {
        return Producto.builder().codigo(codigo).nombre(nombre).categoria(cat)
                .precioCompra(new BigDecimal(compra)).precioVenta(new BigDecimal(venta))
                .stock(stock).stockMinimo(minimo).fechaVencimiento(vence).build();
    }
}
