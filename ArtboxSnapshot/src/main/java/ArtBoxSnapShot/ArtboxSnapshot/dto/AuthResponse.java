package ArtBoxSnapShot.ArtboxSnapshot.dto;

public class AuthResponse {

    private String jwt;

    //DEFAULT BUILDER FOR JACKSON
    public AuthResponse() {}

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    // Getters e setters
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
