package pe.edu.upn.bodegacontrol.dto;

public record LoginResponse(String token, String tipo, long expiraEnSegundos, UsuarioResponse usuario) {
}
