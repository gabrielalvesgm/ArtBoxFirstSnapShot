package ArtBoxSnapShot.ArtboxSnapshot.repository;


import ArtBoxSnapShot.ArtboxSnapshot.model.SalesHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository interface for SalesHistory entity
 *  Extends JpaRepository to provide CRUD operations.
 */
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {

    /**
     *  Retrieve a list of sales history records associated with a specific client.
     *
     * @param clientId the Id of the Client.
     * @return a list of SalesHistory records linked to the given client.
     */
    List<SalesHistory> findByClientId(Long clientId);
}
