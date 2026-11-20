package pe.edu.upn.bodegacontrol.dto;

import jakarta.validation.constraints.*;
import pe.edu.upn.bodegacontrol.entity.Categoria;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductoRequest(
        @NotBlank(message = "El código es obligatorio")
        @Pattern(regexp = "^[A-Za-z0-9-]{3,15}$", message = "El código admite letras, números y guion (3 a 15 caracteres)")
        String codigo,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
        String nombre,

        @NotNull(message = "La categoría es obligatoria")
        Categoria categoria,

        @NotNull(message = "El precio de compra es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio de compra debe ser mayor que 0")
        @Digits(integer = 8, fraction = 2, message = "El precio de compra admite hasta 2 decimales")
        BigDecimal precioCompra,

        @NotNull(message = "El precio de venta es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio de venta debe ser mayor que 0")
        @Digits(integer = 8, fraction = 2, message = "El precio de venta admite hasta 2 decimales")
        BigDecimal precioVenta,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "El stock mínimo es obligatorio")
        @Min(value = 0, message = "El stock mínimo no puede ser negativo")
        Integer stockMinimo,

        @FutureOrPresent(message = "La fecha de vencimiento no puede ser una fecha pasada")
        LocalDate fechaVencimiento) {
}
