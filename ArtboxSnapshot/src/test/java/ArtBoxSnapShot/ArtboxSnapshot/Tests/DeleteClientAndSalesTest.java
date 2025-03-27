package ArtBoxSnapShot.ArtboxSnapshot.Tests;

import ArtBoxSnapShot.ArtboxSnapshot.dto.AuthRequest;
import ArtBoxSnapShot.ArtboxSnapshot.dto.AuthResponse;
import ArtBoxSnapShot.ArtboxSnapshot.dto.ClientDTO;
import ArtBoxSnapShot.ArtboxSnapshot.dto.SalesHistoryDTO;
import ArtBoxSnapShot.ArtboxSnapshot.repository.ClientRepository;
import ArtBoxSnapShot.ArtboxSnapshot.repository.SalesHistoryRepository;
import ArtBoxSnapShot.ArtboxSnapshot.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeleteClientAndSalesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private SalesHistoryRepository salesHistoryRepository;

    @SpyBean
    private ClientService clientService;

    @Test
    public void deleteClientAndSalesTest() throws Exception {
        //Login to get the JWT token
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin");

        String authResponseContent = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(authResponseContent, AuthResponse.class);
        String token = authResponse.getJwt();

        //Preparing the client to be deleted
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setCpfCnpj("12312312301");

        SalesHistoryDTO saleDTO = new SalesHistoryDTO();
        saleDTO.setCpfCnpj("12312312301");  // use the same CPF/CNPJ as the client
        saleDTO.setDescription("Test Sale");
        saleDTO.setPrice(new BigDecimal("100.00"));
        saleDTO.setDate(LocalDateTime.now());


        //Perform the DELETE request and assert HTTP 204 No Content
        mockMvc.perform(delete("/clients/deleteClient/{cpfCnpj}", clientDTO.getCpfCnpj())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());


        //Checks if the methods have been called one time in the run
        verify(clientService, times(1)).deleteClientByCpfCnpj("12312312301");
        verify(clientService, times(1)).deleteAllSalesByClientCpfCnpj("12312312301");

        //Checks the mocked repository for the mock client
        if (!clientRepository.existsByCpfCnpj("12312312301")){
            System.out.println("This client is no longer present in the database");
        }else {
            System.out.println("The Client is still present in the database");
        }

    }
}

