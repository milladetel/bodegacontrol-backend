package pe.edu.upn.bodegacontrol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/** Reposición de mercadería: deja trazabilidad de quién, cuándo y cuánto stock ingresó. */
@Entity
@Table(name = "ingresos_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngresoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "stock_anterior", nullable = false)
    private Integer stockAnterior;

    @Column(name = "stock_nuevo", nullable = false)
    private Integer stockNuevo;

    @Column(length = 150)
    private String observacion;

    @Column(nullable = false)
    private LocalDateTime fecha;
}
