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
    //...To do implement Delete Client, Update Client, findById

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
                                       @RequestParam(defaultValue = "asc") String direction
                                       ){
        return clientService.findClients(cpfCnpj, name, email, address, phone_number, page, size, sortBy, direction);
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    //DeleteClient endpoint
    @DeleteMapping("/deleteClient/{cpfCnpj}")
    public ResponseEntity<Void> deleteByCpfCnpj(@PathVariable String cpfCnpj){
        clientService.deleteAllSalesByClientCpfCnpj(cpfCnpj);
        clientService.deleteClientByCpfCnpj(cpfCnpj);
        return ResponseEntity.noContent().build();
    }


}
