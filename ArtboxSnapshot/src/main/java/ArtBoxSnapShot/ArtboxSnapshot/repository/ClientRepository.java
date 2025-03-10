package ArtBoxSnapShot.ArtboxSnapshot.repository;


import ArtBoxSnapShot.ArtboxSnapshot.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for Client entity.
 * Extends JpaRepository to provide CRUD operations.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Find a Client by its CPF/CNPJ.
     *
     * @param cpfCnpj the CPF or CNPJ of the client.
     * @return an Optional containing the found Client or empty if not found.
     */
    Optional<Client> findByCpfCnpj(String cpfCnpj);

    /**
     * Find a Client by its email.
     *
     * @param email the email of the client.
     *
     * @return an Optional containing the found Client or empty if not found.
     */
    Optional<Client> findByEmail(String email);
}
