package pe.edu.upn.bodegacontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pe.edu.upn.bodegacontrol.dto.LoginRequest;
import pe.edu.upn.bodegacontrol.dto.LoginResponse;
import pe.edu.upn.bodegacontrol.dto.UsuarioResponse;
import pe.edu.upn.bodegacontrol.entity.Usuario;
import pe.edu.upn.bodegacontrol.exception.RecursoNoEncontradoException;
import pe.edu.upn.bodegacontrol.repository.UsuarioRepository;
import pe.edu.upn.bodegacontrol.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    /** Valida usuario y contraseña (BCrypt) y devuelve un token JWT con el rol del usuario. */
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        Usuario usuario = buscar(request.username());
        return new LoginResponse(jwtService.generarToken(usuario), "Bearer",
                jwtService.getExpiracionSegundos(), UsuarioResponse.de(usuario));
    }

    public UsuarioResponse perfil(String username) {
        return UsuarioResponse.de(buscar(username));
    }

    public Usuario buscar(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + username));
    }
}
