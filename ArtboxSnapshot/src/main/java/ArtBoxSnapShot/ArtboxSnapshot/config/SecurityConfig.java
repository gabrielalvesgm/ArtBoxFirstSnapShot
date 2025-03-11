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
                // Desativa o CSRF, o que é recomendado para APIs REST que não utilizam sessões
                .csrf(csrf -> csrf.disable())
                // Permite acesso a todas as requisições sem exigir autenticação
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // Configura o HTTP Basic de forma padrão (opcional, pode ser removido se não for necessário)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
