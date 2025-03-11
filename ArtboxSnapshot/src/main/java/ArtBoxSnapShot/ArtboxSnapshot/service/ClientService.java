package ArtBoxSnapShot.ArtboxSnapshot.service;


import ArtBoxSnapShot.ArtboxSnapshot.dto.ClientDTO;
import ArtBoxSnapShot.ArtboxSnapshot.model.Client;
import ArtBoxSnapShot.ArtboxSnapshot.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    //Create an new client method
    public Client createClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setCpfCnpj(clientDTO.getCpfCnpj());
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setAddress(clientDTO.getAddress());
        client.setPhone_number(clientDTO.getPhone_number());
        return clientRepository.save(client);
    }


    //Get client by his CPF/CNPJ method
    public Optional<Client> getClientByCpfCnpj(String cpfCnpj) {
        return clientRepository.findByCpfCnpj(cpfCnpj);
    }
}
