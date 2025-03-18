package ArtBoxSnapShot.ArtboxSnapshot.security;

import ArtBoxSnapShot.ArtboxSnapshot.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Configures HTTP security for JWT-based authentication.
     * All endpoints require authentication except /login.
     * Sessions are set to stateless, as JWT tokens are used.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection (adequate for REST APIs)
                .csrf(csrf -> csrf.disable())
                // Define endpoint access rules:
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll() // Allow unauthenticated access to the login endpoint
                        .anyRequest().authenticated()            // All other endpoints require authentication
                )
                // Set session management to stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add the JWT filter to validate tokens with every request before UsernamePasswordAuthenticationFilter runs
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Exposes the AuthenticationManager bean for use in the authentication process (e.g., in AuthController).
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
