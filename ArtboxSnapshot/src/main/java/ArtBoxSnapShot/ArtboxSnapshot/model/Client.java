package ArtBoxSnapShot.ArtboxSnapshot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Client entity mapped to the "clients" table in the database.
 */
@Entity
@Table(name = "clients", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpfCnpj"),    // Ensures the CPF/CNPJ column is unique
        @UniqueConstraint(columnNames = "email"),       // Ensures the email column is unique
        @UniqueConstraint(columnNames = "phone_number")  // Ensures the phoneNumber column is unique (Note: field not declared in this class)
})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CPF/CNPJ must not be null and must follow the pattern of 11 or 14 digits
    @NotNull(message = "CPF/CNPJ cannot be null")
    @Pattern(regexp = "\\d{11}|\\d{14}", message = "CPF/CNPJ must be valid.")
    @Column(name = "cpfCnpj", nullable = false, unique = true, length = 14)
    private String cpfCnpj;

    // Client name must not be blank
    @NotBlank(message = "O nome não pode estar vazio")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Email must be valid and unique
    @Email(message = "Email deve ser válido")
    @Column(name = "email", unique = true, length = 100)
    private String email;

    // Address column (optional)
    @Column(name = "address", length = 255)
    private String address;

    // Phone Number (optional)
    @NotBlank(message = "Número de telefone deve ser válido")
    @Column(name = "phone_number", length = 16)
    private String phone_number;


    //
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesHistory> sales = new ArrayList<>();

    // Getters and Setters

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

    public List<SalesHistory> getSales() {
        return sales;
    }

    public void setSales(List<SalesHistory> sales) {
        this.sales = sales;
    }
}
