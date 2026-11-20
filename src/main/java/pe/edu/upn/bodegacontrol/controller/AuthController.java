package pe.edu.upn.bodegacontrol.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.upn.bodegacontrol.dto.LoginRequest;
import pe.edu.upn.bodegacontrol.dto.LoginResponse;
import pe.edu.upn.bodegacontrol.dto.UsuarioResponse;
import pe.edu.upn.bodegacontrol.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /** POST /api/auth/login: devuelve el token JWT y los datos del usuario. */
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /** GET /api/auth/me: datos del usuario dueño del token. */
    @GetMapping("/me")
    public UsuarioResponse me(Authentication auth) {
        return authService.perfil(auth.getName());
    }
}
