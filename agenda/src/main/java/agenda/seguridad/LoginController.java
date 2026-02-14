package agenda.seguridad;

import agenda.repositorios.UsuarioRepository;
import agenda.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public String login(
            @RequestParam("user") String username,
            @RequestParam("encryptedPass") String encryptedPass) throws Exception {

        List<Usuario> Usuarios = usuarioRepository.getUsuarios();

        Usuario usuarioEncontrado = null;

        for (Usuario usuario : Usuarios) {
            if (usuario.getUsername().equals(username) &&
                    PasswordEncryptor.decrypt(usuario.getEncryptedPass())
                            .equals(encryptedPass)) {

                usuarioEncontrado = usuario;
                break;
            }
        }

        if (usuarioEncontrado == null) {
            return "Usuario o contraseña incorrectos";
        }

        return jwtAuthenticationConfig.getJWTToken(
                usuarioEncontrado.getUsername(),
                usuarioEncontrado.getRol());
    }
}
