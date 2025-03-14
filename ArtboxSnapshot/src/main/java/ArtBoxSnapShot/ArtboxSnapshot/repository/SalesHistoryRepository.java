//SalesHistoryRepository.java
package ArtBoxSnapShot.ArtboxSnapshot.repository;


import ArtBoxSnapShot.ArtboxSnapshot.model.Client;
import ArtBoxSnapShot.ArtboxSnapshot.model.SalesHistory;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository interface for SalesHistory entity
 *  Extends JpaRepository to provide CRUD operations.
 */
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {

    List<SalesHistory> findByClientId(Long clientId);


    //Get sale by id (that belongs to the provided client)
    Optional<SalesHistory> findByIdAndClient_id(Long saleId, Long clientId);


    //Delete a sale associated to a specific client
    void deleteByClient(Client client);


    long countByClient(Client client);
}
