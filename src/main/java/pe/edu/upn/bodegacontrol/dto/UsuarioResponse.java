package pe.edu.upn.bodegacontrol.dto;

import pe.edu.upn.bodegacontrol.entity.Usuario;

public record UsuarioResponse(Long id, String nombre, String username, String email, String rol) {

    public static UsuarioResponse de(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombre(), u.getUsername(), u.getEmail(), u.getRol().name());
    }
}
