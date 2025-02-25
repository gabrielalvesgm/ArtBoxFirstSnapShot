//Cliente class
package ArtBoxSnapShot.ArtboxSnapshot.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.processing.Pattern;

//Cliente entity mapped on the "clientes" table in database.
@Entity
@Table(name = "clientes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpfCnpj"), //enforces cpf/cnpj column to be unique
        @UniqueConstraint(columnNames = "email"), //enforces email column to be unique
        @UniqueConstraint(columnNames = "numeroTelefone") //enforces numeroTelefone column to be unique
})



public class Cliente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "CPF/CPNJ não pode ser nulo")
    @Pattern(regexp = "\\d{11}|\\d{14}", message = "O CPF/CNPJ deve ser válido.")
    @Column(name = "cpjCnpj", nullable = false, unique = true, length = 14)
    private String cpfCnpj;



    @NotBlank(message = "O nome do cliente não pode estar vazio")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;


    @Email(message = "O Email deve ser válido")
    @Column(name = "email", unique = true, length = 100)
    private String email;



    @Column(name = "Endereço", length = 255)
    private String endereco;
}
