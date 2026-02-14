package agenda.seguridad;

import agenda.usuario.Rol;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class JWTAuthenticationConfig {

    // Cambiamos el tipo de parámetro de String a Rol
    public String getJWTToken(String username, Rol rol) {

        // Convertimos el Enum a String y añadimos el prefijo ROLE_ que exige Spring
        String authority = "ROLE_" + rol.name();

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(authority);

        String token = Jwts.builder()
                .setId("espinozajgeJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constans.TOKEN_EXPIRATION_TIME))
                .signWith(Constans.getSigningKey(Constans.SUPER_SECRET_KEY), SignatureAlgorithm.HS512)
                .compact();

        return Constans.TOKEN_BEARER_PREFIX + token;
    }
}