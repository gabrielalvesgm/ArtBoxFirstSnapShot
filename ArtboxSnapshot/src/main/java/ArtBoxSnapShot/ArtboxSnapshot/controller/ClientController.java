//ClientController.java
package ArtBoxSnapShot.ArtboxSnapshot.controller;


import ArtBoxSnapShot.ArtboxSnapshot.dto.ClientDTO;
import ArtBoxSnapShot.ArtboxSnapshot.model.Client;
import ArtBoxSnapShot.ArtboxSnapshot.service.ClientService;
import org.springframework.data.domain.Page;
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
    //To do implement Delete Client, Update Client, findById

    //To do remove this
    //Endpoint to get a client by his CPF
    @GetMapping("/cpfCnpj/{cpfCnpj}")
    public ResponseEntity<Client> getClientByCpfCnpj(@PathVariable String cpfCnpj) {
        Optional<Client> client = clientService.getClientByCpfCnpj(cpfCnpj);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Endpoint to get a page of filtered clients
    @GetMapping("/findClients")
    public Page<ClientDTO> findClients(@RequestParam(required = false) String cpfCnpj,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) String email,
                                       @RequestParam(required = false) String address,
                                       @RequestParam(required = false) String phone_number,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "name") String sortBy,
                                       @RequestParam(defaultValue = "asac") String direction
                                       ){
        return clientService.findClients(cpfCnpj, name, email, address, phone_number, page, size, sortBy, direction);
    }



}
