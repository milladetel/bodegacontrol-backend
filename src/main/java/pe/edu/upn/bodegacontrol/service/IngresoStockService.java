package pe.edu.upn.bodegacontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upn.bodegacontrol.dto.IngresoRequest;
import pe.edu.upn.bodegacontrol.dto.IngresoResponse;
import pe.edu.upn.bodegacontrol.entity.IngresoStock;
import pe.edu.upn.bodegacontrol.entity.Producto;
import pe.edu.upn.bodegacontrol.repository.IngresoStockRepository;

import java.time.LocalDateTime;
import java.util.List;

/** Reposición de mercadería con trazabilidad (stock anterior, nuevo, usuario y fecha). */
@Service
@RequiredArgsConstructor
public class IngresoStockService {

    private final IngresoStockRepository ingresoRepository;
    private final ProductoService productoService;
    private final AuthService authService;

    @Transactional
    public IngresoResponse registrar(IngresoRequest req, String username) {
        Producto producto = productoService.buscarActivo(req.productoId());
        int anterior = producto.getStock();
        producto.setStock(anterior + req.cantidad());

        IngresoStock ingreso = IngresoStock.builder()
                .producto(producto)
                .usuario(authService.buscar(username))
                .cantidad(req.cantidad())
                .stockAnterior(anterior)
                .stockNuevo(producto.getStock())
                .observacion(req.observacion())
                .fecha(LocalDateTime.now())
                .build();
        return IngresoResponse.de(ingresoRepository.save(ingreso));
    }

    @Transactional(readOnly = true)
    public List<IngresoResponse> listar() {
        return ingresoRepository.findAllByOrderByFechaDesc().stream().map(IngresoResponse::de).toList();
    }
}
