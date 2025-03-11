package ArtBoxSnapShot.ArtboxSnapshot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desativa o CSRF se não for utilizado (importante para APIs REST)
                .csrf(csrf -> csrf.disable())
                // Configura o acesso a todos os endpoints sem autenticação
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // Aplica configurações padrão (opcional)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
