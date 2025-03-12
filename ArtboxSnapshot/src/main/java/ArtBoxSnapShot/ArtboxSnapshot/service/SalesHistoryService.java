package ArtBoxSnapShot.ArtboxSnapshot.service;


import ArtBoxSnapShot.ArtboxSnapshot.dto.SalesHistoryDTO;
import ArtBoxSnapShot.ArtboxSnapshot.model.Client;
import ArtBoxSnapShot.ArtboxSnapshot.model.SalesHistory;
import ArtBoxSnapShot.ArtboxSnapshot.repository.ClientRepository;
import ArtBoxSnapShot.ArtboxSnapshot.repository.SalesHistoryRepository;
import jakarta.transaction.Transactional;
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

    /**Update sale verifying if client (of the provided CPF) is the ''owner'' of the sale
     * Can only update description, price and date fields
     * Cannot update Id, client_Id or Cpf/Cnpj field that are linked with this sale.
     */
    @Transactional
    public SalesHistory updateSale(Long saleId, String cpfCnpj, SalesHistoryDTO saleDTO) {
        //Verifying the Client identity by Cpf/Cnpj
        Client client = clientRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new RuntimeException("Cliente com este CPF/CNPJ não existe:" + cpfCnpj));

        //Check that the sale belong to this Client
        SalesHistory sale = salesHistoryRepository.findByIdAndClient_id(saleId, client.getId())
                .orElseThrow(() -> new RuntimeException("Este cliente não possui uma venda com este id:" + saleId));


        //Update allowed fields
        sale.setDescription(saleDTO.getDescription());
        sale.setPrice(saleDTO.getPrice());
        sale.setDate(saleDTO.getDate());

        return salesHistoryRepository.save(sale);
    }

    //Delete all the sales associated to the provided client CPF/CNPJ
    @Transactional
    public void deleteAllSalesByClientCPFCNPJ(String cpfCnpj) {
        Client client = clientRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new RuntimeException("O Cliente com este cpf não existe:" + cpfCnpj));

        salesHistoryRepository.deleteByClient(client);
    }

    /**
     * Delete an specific sale (ensuring it belongs to the provided client CPF/CNPJ
     */
    @Transactional
    public void deleteSaleByClientIDandSaleID(String cpfCnpj, Long saleId) {
        Client client = clientRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new RuntimeException("O Cliente com este cpf não existe:" + cpfCnpj));

        SalesHistory sale = salesHistoryRepository.findByIdAndClient_id(saleId, client.getId())
                .orElseThrow(() -> new RuntimeException("A venda de id: " +saleId + " não pertence a este cliente"));

                salesHistoryRepository.delete(sale);
    }

}