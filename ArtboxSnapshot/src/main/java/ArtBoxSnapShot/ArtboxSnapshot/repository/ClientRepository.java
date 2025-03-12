//ClientRepository.java
package ArtBoxSnapShot.ArtboxSnapshot.repository;


import ArtBoxSnapShot.ArtboxSnapshot.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository interface for Client entity.
 * Extends JpaRepository to provide CRUD operations.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {


    //To do Remove this
    Optional<Client> findByCpfCnpj(String cpfCnpj);

    //Mysql coded query for a getMapping request of a page of clients
    @Query("SELECT c FROM Client c WHERE " +
            "(:cpfCnpj IS NULL OR c.cpfCnpj LIKE CONCAT('%', :cpfCnpj, '%')) AND " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:address IS NULL OR LOWER(c.address) LIKE LOWER(CONCAT('%', :address, '%'))) AND " +
            "(:phone_number IS NULL OR c.phone_number LIKE CONCAT('%', :phone_number, '%'))")
    Page<Client>findClients(@Param("cpfCnpj") String cpfCnpj,
                            @Param("name") String name,
                            @Param("email") String email,
                            @Param("address") String address,
                            @Param("phone_number") String phone_number,
                            Pageable pageable);

}
