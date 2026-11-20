package pe.edu.upn.bodegacontrol.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.upn.bodegacontrol.dto.IngresoRequest;
import pe.edu.upn.bodegacontrol.dto.IngresoResponse;
import pe.edu.upn.bodegacontrol.service.IngresoStockService;

import java.util.List;

/** Reposición de stock (solo ADMIN, restringido en SecurityConfig). */
@RestController
@RequestMapping("/api/ingresos")
@RequiredArgsConstructor
public class IngresoStockController {

    private final IngresoStockService ingresoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngresoResponse registrar(@Valid @RequestBody IngresoRequest request, Authentication auth) {
        return ingresoService.registrar(request, auth.getName());
    }

    @GetMapping
    public List<IngresoResponse> listar() {
        return ingresoService.listar();
    }
}
