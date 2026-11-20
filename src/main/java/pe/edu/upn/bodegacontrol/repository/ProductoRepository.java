package pe.edu.upn.bodegacontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upn.bodegacontrol.entity.Categoria;
import pe.edu.upn.bodegacontrol.entity.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);

    List<Producto> findByActivoTrueOrderByCodigoAsc();

    long countByActivoTrue();

    /** Búsqueda por nombre o código y filtro opcional por categoría (solo productos activos). */
    @Query("""
            SELECT p FROM Producto p
            WHERE p.activo = true
              AND (:texto IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
                                  OR LOWER(p.codigo) LIKE LOWER(CONCAT('%', :texto, '%')))
              AND (:categoria IS NULL OR p.categoria = :categoria)
            ORDER BY p.codigo
            """)
    List<Producto> buscar(@Param("texto") String texto, @Param("categoria") Categoria categoria);
}
