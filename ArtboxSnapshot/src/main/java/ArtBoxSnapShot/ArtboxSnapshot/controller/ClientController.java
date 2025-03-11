//ClientController.java
package ArtBoxSnapShot.ArtboxSnapshot.controller;


import ArtBoxSnapShot.ArtboxSnapshot.dto.ClientDTO;
import ArtBoxSnapShot.ArtboxSnapshot.model.Client;
import ArtBoxSnapShot.ArtboxSnapshot.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //Endpoint to create a new Client
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO clientDTO) {
        Client client = clientService.createClient(clientDTO);
        return ResponseEntity.ok(client);
    }

    //Endpoint to get a client by his CPF
    @GetMapping("/cpfCnpj/{cpfCnpj}")
    public ResponseEntity<Client> getClientByCpfCnpj(@PathVariable String cpfCnpj) {
        Optional<Client> client = clientService.getClientByCpfCnpj(cpfCnpj);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
