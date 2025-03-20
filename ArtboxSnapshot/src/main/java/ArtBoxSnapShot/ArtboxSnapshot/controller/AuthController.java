package ArtBoxSnapShot.ArtboxSnapshot.controller;

import ArtBoxSnapShot.ArtboxSnapshot.dto.AuthRequest;
import ArtBoxSnapShot.ArtboxSnapshot.dto.AuthResponse;
import ArtBoxSnapShot.ArtboxSnapshot.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login") //BASE ENDPOINT FOR AUTH REQUESTS
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    /**
     * Constructor-based dependency injection to improve testability and maintainability.
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        //Spring security authentication to the user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            //Return HTTP 401 Unauthorized if authentication fails
            return ResponseEntity.status(401).body("Usuário ou senha incorreto");
        }
        //Retrieve user details after successful authentication
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        //Generate a JWT token for the authenticated user
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        //Return the generated token in the response
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
