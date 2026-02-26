package agenda.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth

                        // 🔓 Login libre
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        // 🟢 GET → ADMIN, USER, VIEWER
                        .requestMatchers(HttpMethod.GET, "/**")
                        .hasAnyRole("ADMIN", "USER", "VIEWER")

                        // 🟡 POST → ADMIN, USER
                        .requestMatchers(HttpMethod.POST, "/**")
                        .hasAnyRole("ADMIN", "USER")

                        // 🔵 PUT → solo ADMIN
                        .requestMatchers(HttpMethod.PUT, "/**")
                        .hasRole("ADMIN")

                        // 🔴 DELETE → solo ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                );

        return http.build();
    }
}