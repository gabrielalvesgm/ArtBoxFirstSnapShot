package ArtBoxSnapShot.ArtboxSnapshot.service;


import ArtBoxSnapShot.ArtboxSnapshot.dto.SalesHistoryDTO;
import ArtBoxSnapShot.ArtboxSnapshot.model.Client;
import ArtBoxSnapShot.ArtboxSnapshot.model.SalesHistory;
import ArtBoxSnapShot.ArtboxSnapshot.repository.ClientRepository;
import ArtBoxSnapShot.ArtboxSnapshot.repository.SalesHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalesHistoryService {

    private final SalesHistoryRepository salesHistoryRepository;
    private final ClientRepository clientRepository;

    public SalesHistoryService(SalesHistoryRepository salesHistoryRepository, ClientRepository clientRepository) {
        this.salesHistoryRepository = salesHistoryRepository;
        this.clientRepository = clientRepository;
    }

    // Method to create a new sale for an valid client
    public SalesHistory createSale(SalesHistoryDTO salesHistoryDTO) {
        Client client = clientRepository.findByCpfCnpj(salesHistoryDTO.getCpfCnpj())
                .orElseThrow(() -> new RuntimeException("O Client com este cpf não existe: " + salesHistoryDTO.getCpfCnpj()));

        SalesHistory sale = new SalesHistory();
        sale.setClient(client);
        sale.setDescription(salesHistoryDTO.getDescription());
        sale.setPrice(salesHistoryDTO.getPrice());
        sale.setDate(salesHistoryDTO.getDate());

        return salesHistoryRepository.save(sale);
    }
}