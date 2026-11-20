package pe.edu.upn.bodegacontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upn.bodegacontrol.dto.ProductoRequest;
import pe.edu.upn.bodegacontrol.dto.ProductoResponse;
import pe.edu.upn.bodegacontrol.entity.Categoria;
import pe.edu.upn.bodegacontrol.entity.EstadoStock;
import pe.edu.upn.bodegacontrol.entity.Producto;
import pe.edu.upn.bodegacontrol.exception.RecursoNoEncontradoException;
import pe.edu.upn.bodegacontrol.exception.ReglaNegocioException;
import pe.edu.upn.bodegacontrol.repository.ProductoRepository;

import java.util.Comparator;
import java.util.List;

/** Lógica del catálogo de productos: búsqueda, alta, edición, baja lógica y alertas. */
@Service
@RequiredArgsConstructor
public class ProductoService {

    public static final int DIAS_ALERTA_VENCIMIENTO = 30;

    private final ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String buscar, Categoria categoria, EstadoStock estado) {
        String texto = (buscar == null || buscar.isBlank()) ? null : buscar.trim();
        return productoRepository.buscar(texto, categoria).stream()
                .filter(p -> estado == null || p.getEstado() == estado)
                .map(ProductoResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtener(Long id) {
        return ProductoResponse.de(buscarActivo(id));
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest req) {
        validarPrecios(req);
        if (productoRepository.existsByCodigoIgnoreCase(req.codigo())) {
            throw new ReglaNegocioException("Ya existe un producto con el código " + req.codigo().toUpperCase());
        }
        Producto p = new Producto();
        copiar(req, p);
        return ProductoResponse.de(productoRepository.save(p));
    }

    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest req) {
        validarPrecios(req);
        Producto p = buscarActivo(id);
        if (productoRepository.existsByCodigoIgnoreCaseAndIdNot(req.codigo(), id)) {
            throw new ReglaNegocioException("Ya existe otro producto con el código " + req.codigo().toUpperCase());
        }
        copiar(req, p);
        return ProductoResponse.de(productoRepository.save(p));
    }

    /** Baja lógica: el producto deja de listarse pero se conserva para el historial de ventas. */
    @Transactional
    public void eliminar(Long id) {
        Producto p = buscarActivo(id);
        p.setActivo(false);
        productoRepository.save(p);
    }

    /** Productos agotados, con stock bajo o que vencen en los próximos 30 días. */
    @Transactional(readOnly = true)
    public List<ProductoResponse> alertas() {
        return productoRepository.findByActivoTrueOrderByCodigoAsc().stream()
                .filter(p -> p.getEstado() != EstadoStock.DISPONIBLE || p.venceEn(DIAS_ALERTA_VENCIMIENTO))
                .sorted(Comparator.comparing(Producto::getStock))
                .map(ProductoResponse::de)
                .toList();
    }

    public Producto buscarActivo(Long id) {
        return productoRepository.findById(id)
                .filter(Producto::isActivo)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el producto con id " + id));
    }

    private void validarPrecios(ProductoRequest req) {
        if (req.precioVenta().compareTo(req.precioCompra()) < 0) {
            throw new ReglaNegocioException("El precio de venta no puede ser menor al precio de compra");
        }
    }

    private void copiar(ProductoRequest req, Producto p) {
        p.setCodigo(req.codigo().trim().toUpperCase());
        p.setNombre(req.nombre().trim());
        p.setCategoria(req.categoria());
        p.setPrecioCompra(req.precioCompra());
        p.setPrecioVenta(req.precioVenta());
        p.setStock(req.stock());
        p.setStockMinimo(req.stockMinimo());
        p.setFechaVencimiento(req.fechaVencimiento());
    }
}
