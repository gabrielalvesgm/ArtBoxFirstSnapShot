package ArtBoxSnapShot.ArtboxSnapshot.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {

    @NotBlank(message = "Usuário inválido")
    private String username;

    @NotBlank(message = "Senha inválida")
    private String password;

    //DEFAULT BUILDER FOR JACKSON
    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters e setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
