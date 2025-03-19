package ArtBoxSnapShot.ArtboxSnapshot.dto;



/**
 * Data transfer Object (DTO) for transferring client data
 */
public class ClientDTO {
    private Long id;
    private String cpfCnpj;
    private String name;
    private String email;
    private String address;
    private String phone_number;

    //Necessary constructor for mapping of Client to ClientDTO in service
    public ClientDTO(Long id, String cpfCnpj, String name, String email, String address, String phone_number) {
        this.id = id;
        this.cpfCnpj = cpfCnpj;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
    }

    //DEFAULT BUILDER TO JACKSON (TESTS)
    public ClientDTO() {}


//Getter and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
