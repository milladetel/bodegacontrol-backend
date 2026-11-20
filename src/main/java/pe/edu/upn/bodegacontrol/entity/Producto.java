package pe.edu.upn.bodegacontrol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 15)
    private String codigo;

    @Column(nullable = false, length = 60)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Categoria categoria;

    @Column(name = "precio_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(nullable = false)
    @Builder.Default
    private boolean activo = true;

    /** Regla de negocio: el estado se deriva del stock actual frente al stock mínimo. */
    public EstadoStock getEstado() {
        if (stock == null || stock <= 0) {
            return EstadoStock.AGOTADO;
        }
        if (stock <= stockMinimo) {
            return EstadoStock.STOCK_BAJO;
        }
        return EstadoStock.DISPONIBLE;
    }

    /** Vence dentro de los próximos {@code dias} días (o ya venció). */
    public boolean venceEn(int dias) {
        return fechaVencimiento != null && !fechaVencimiento.isAfter(LocalDate.now().plusDays(dias));
    }
}
