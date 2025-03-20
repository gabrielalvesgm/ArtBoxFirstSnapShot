//ClientService.Java
package ArtBoxSnapShot.ArtboxSnapshot.service;


import ArtBoxSnapShot.ArtboxSnapshot.dto.ClientDTO;
import ArtBoxSnapShot.ArtboxSnapshot.exception.DuplicateResourceException;
import ArtBoxSnapShot.ArtboxSnapshot.model.Client;
import ArtBoxSnapShot.ArtboxSnapshot.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //Convert Client to ClientDTO
    private ClientDTO mapToDTO(Client client){
        return new ClientDTO(client.getId(),
                client.getCpfCnpj(),
                client.getName(),
                client.getEmail(),
                client.getAddress(),
                client.getPhone_number());
    }

    //Create a new client method with a check for duplicate CPF/CNPJ
    public Client createClient(ClientDTO clientDTO) {

        Optional<Client> existingClient = clientRepository.findByCpfCnpj(clientDTO.getCpfCnpj());
        if (existingClient.isPresent()) {
            throw new DuplicateResourceException("Cliente com o CPF/CNPJ " + clientDTO.getCpfCnpj() + " já existe.");
        }

        Client client = new Client();
        client.setCpfCnpj(clientDTO.getCpfCnpj());
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setAddress(clientDTO.getAddress());
        client.setPhone_number(clientDTO.getPhone_number());
        return clientRepository.save(client);
    }

    // Get clients by their CPF/CNPJ, name, email, address, or phone number.
    // Any combination of parameters can be used for filtering, or none to get all clients.
    // Pagination uses default values set in the endpoints, but can be customized using 'page', 'size', 'sortBy', and 'direction' parameters.
    public Page<ClientDTO> findClients(String cpfCnpj,
                                       String name,
                                       String email,
                                       String address,
                                       String phone_number,
                                       int page, int size, String sortBy, String direction){

        //Ternary operator testing if sorting order is ascending or descending
        Sort sort = direction.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        //Storing values of pagination config
        Pageable pageable = PageRequest.of(page, size, sort);
        //Storing the Clients filtered by findClients in a page
        Page<Client> clients = clientRepository.findClients(cpfCnpj, name, email, address, phone_number, pageable);
        //Returning stored clients to be converted
        return clients.map(this::mapToDTO);
    }

    //Method to delete all sales of a client by provided CPF/CNPJ
    @Transactional
    public String deleteAllSalesByClientCpfCnpj(String cpfCnpj) {
        Optional<Client> clientOpt = clientRepository.findByCpfCnpj(cpfCnpj);
        if (clientOpt.isEmpty()) {
            return "Client não encontrado";
        }
        Client client = clientOpt.get();
        if (client.getSales().isEmpty()) {
            return "Este cliente não possui nenhuma venda";
        }
        client.getSales().clear();
        clientRepository.save(client);
        return "Todas as vendas deste cliente foram deletadas com sucesso.";
    }

}
