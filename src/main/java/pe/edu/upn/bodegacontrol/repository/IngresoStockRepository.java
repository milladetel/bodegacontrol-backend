package pe.edu.upn.bodegacontrol.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upn.bodegacontrol.entity.IngresoStock;

import java.util.List;

public interface IngresoStockRepository extends JpaRepository<IngresoStock, Long> {

    @EntityGraph(attributePaths = {"producto", "usuario"})
    List<IngresoStock> findAllByOrderByFechaDesc();
}
