package pe.edu.upn.bodegacontrol.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.upn.bodegacontrol.dto.VentaRequest;
import pe.edu.upn.bodegacontrol.dto.VentaResponse;
import pe.edu.upn.bodegacontrol.service.VentaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    /** POST /api/ventas: registra la venta del usuario autenticado y descuenta stock. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VentaResponse registrar(@Valid @RequestBody VentaRequest request, Authentication auth) {
        return ventaService.registrar(request, auth.getName());
    }

    /** GET /api/ventas?desde=2026-11-01&hasta=2026-11-20 */
    @GetMapping
    public List<VentaResponse> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ventaService.listar(desde, hasta);
    }

    @GetMapping("/{id}")
    public VentaResponse obtener(@PathVariable Long id) {
        return ventaService.obtener(id);
    }
}
