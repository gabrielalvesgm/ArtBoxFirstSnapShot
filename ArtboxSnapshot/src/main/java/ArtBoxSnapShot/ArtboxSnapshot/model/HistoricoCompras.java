//HistoricoCompras class
package ArtBoxSnapShot.ArtboxSnapshot.model;

//Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;


// HistoricoCompras entity mapped to the ''historico_compras" table in the database.
// ''purchases'' are linked to a "Cliente" a purchase can only exist if its associated to a valid Cliente.
@Entity
@Table(name = "historico_compras")
public class HistoricoCompras {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // "ManyToOne" defines the relationship with Cliente, ensures that purchase records is associated to a valid Cliente.
    @NotNull(message = "Cliente não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;



    // @Lob annotation + columnDefinition = "TEXT" defines the column type as TEXT (exceeds 255 characters)
    @Lob
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;


    @NotNull(message = "O valor não pode ser nulo")
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;



    @NotNull(message = "A data não pode ser nula")
    @Column(name = "data", nullable = false)
    private LocalDateTime data;



    //GETTER AND SETTERS

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
