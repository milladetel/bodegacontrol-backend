package pe.edu.upn.bodegacontrol.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pe.edu.upn.bodegacontrol.dto.ProductoRequest;
import pe.edu.upn.bodegacontrol.dto.ProductoResponse;
import pe.edu.upn.bodegacontrol.entity.Categoria;
import pe.edu.upn.bodegacontrol.entity.EstadoStock;
import pe.edu.upn.bodegacontrol.service.ProductoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    /** GET /api/productos?buscar=&categoria=&estado= */
    @GetMapping
    public List<ProductoResponse> listar(@RequestParam(required = false) String buscar,
                                         @RequestParam(required = false) Categoria categoria,
                                         @RequestParam(required = false) EstadoStock estado) {
        return productoService.listar(buscar, categoria, estado);
    }

    /** GET /api/productos/alertas: agotados, stock bajo o por vencer. */
    @GetMapping("/alertas")
    public List<ProductoResponse> alertas() {
        return productoService.alertas();
    }

    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable Long id) {
        return productoService.obtener(id);
    }

    /** POST /api/productos (solo ADMIN): responde 201 Created con la cabecera Location. */
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse creado = productoService.crear(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(creado.id()).toUri();
        return ResponseEntity.created(location).body(creado);
    }

    /** PUT /api/productos/{id} (solo ADMIN) */
    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        return productoService.actualizar(id, request);
    }

    /** DELETE /api/productos/{id} (solo ADMIN): baja lógica, responde 204 No Content. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}
