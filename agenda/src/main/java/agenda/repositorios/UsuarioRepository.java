package agenda.repositorios;

import agenda.usuario.Usuario;
import agenda.usuario.Rol;
import agenda.seguridad.PasswordEncryptor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    public List<Usuario> getUsuarios() throws Exception {

        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(new Usuario(
                "aitor",
                PasswordEncryptor.encrypt("1234"),
                Rol.ADMIN));

        usuarios.add(new Usuario(
                "alicia",
                PasswordEncryptor.encrypt("1111"),
                Rol.USER));

        // 👇 NUEVO USUARIO VIEWER
        usuarios.add(new Usuario(
                "victor",
                PasswordEncryptor.encrypt("2222"),
                Rol.VIEWER));

        return usuarios;
    }
}