package pe.edu.upn.bodegacontrol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upn.bodegacontrol.dto.DashboardResponse;
import pe.edu.upn.bodegacontrol.dto.OpcionResponse;
import pe.edu.upn.bodegacontrol.entity.Categoria;
import pe.edu.upn.bodegacontrol.service.DashboardService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GeneralController {

    private final DashboardService dashboardService;

    /** GET /api/health: comprobación pública de que la API está en línea. */
    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of("estado", "UP", "aplicacion", "BodegaControl API", "fecha", LocalDateTime.now());
    }

    /** GET /api/dashboard: indicadores para la pantalla principal. */
    @GetMapping("/dashboard")
    public DashboardResponse dashboard() {
        return dashboardService.resumen();
    }

    /** GET /api/categorias: valores para la lista desplegable de categorías. */
    @GetMapping("/categorias")
    public List<OpcionResponse> categorias() {
        return Arrays.stream(Categoria.values())
                .map(c -> new OpcionResponse(c.name(), c.getEtiqueta()))
                .toList();
    }
}
