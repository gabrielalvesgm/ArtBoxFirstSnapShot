package ArtBoxSnapShot.ArtboxSnapshot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SalesHistory entity mapped to the "sales_history" table in the database.
 * Each sale is linked to a Client; a sale can only exist if associated with a valid Client.
 */
@Entity
@Table(name = "sales_history")
public class SalesHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-One relationship with Client. Each sale must be associated with a valid client.
    @NotNull(message = "Client cannot be null")
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // The description field can store long text (using TEXT column type)
    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Price of the sale; must not be null
    @NotNull(message = "Price cannot be null")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    // Date and time when the sale occurred; must not be null
    @NotNull(message = "Date cannot be null")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
