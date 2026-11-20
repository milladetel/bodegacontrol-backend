package pe.edu.upn.bodegacontrol.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upn.bodegacontrol.entity.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @EntityGraph(attributePaths = {"usuario"})
    List<Venta> findByFechaBetweenOrderByFechaDesc(LocalDateTime desde, LocalDateTime hasta);

    @EntityGraph(attributePaths = {"usuario"})
    List<Venta> findAllByOrderByFechaDesc();

    @EntityGraph(attributePaths = {"usuario"})
    List<Venta> findTop5ByOrderByFechaDesc();

    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.fecha BETWEEN :desde AND :hasta")
    BigDecimal sumarTotal(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    long countByFechaBetween(LocalDateTime desde, LocalDateTime hasta);

    @Query("SELECT COALESCE(MAX(v.id), 0) FROM Venta v")
    Long maxId();
}
