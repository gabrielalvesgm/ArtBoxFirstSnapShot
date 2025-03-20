/**
 * CreateClientAndSaleTest
 *
 * This is an integrations test who performs the following operations:
 * 1. Log in and get an JWT token
 * 2. Creates a new client using the "/clients" endpoint.
 * 3. Creates a new sale (sales history) associated with an specificated client using the "/sales" endpoint.
 *
 * Observations:
 * - This test uses @Transactional so all changes in the database are reverted at the end of the test.
 * - Its expected 200 HTTP status (OK) from all requests.
 * - client and sale responses are printed at the end of the test in the console.
 *
 */




package ArtBoxSnapShot.ArtboxSnapshot.Tests;


import ArtBoxSnapShot.ArtboxSnapshot.controller.AuthController;
import ArtBoxSnapShot.ArtboxSnapshot.dto.AuthRequest;
import ArtBoxSnapShot.ArtboxSnapshot.dto.AuthResponse;
import ArtBoxSnapShot.ArtboxSnapshot.dto.ClientDTO;
import ArtBoxSnapShot.ArtboxSnapshot.dto.SalesHistoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CreateClientAndSaleTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void createClientAndSaleTest() throws Exception {
        //1° STEP: LOGIN TO GET THE JWT TOKEN
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

        //2° STEP: CREATE AN NEW CLIENT WITH A PAYLOAD
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setCpfCnpj("12312312301");
        clientDTO.setName("Test Client");
        clientDTO.setEmail("testclient1@test.com");
        clientDTO.setAddress("Test Adress");
        clientDTO.setPhone_number("81 01234-5678");

        //METHOD TO CAPTURE CLIENT RESPONSE
        String clientResponse = mockMvc.perform(post("/clients")
                 .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        //CLIENT RESPONSE CAN BE CONVERTED TO AN CLIENT_DTO OBJECT IF NECESSARY.

        //3° STEP: CREATE AN SALE TO THE PAYLOAD CLIENT.
        SalesHistoryDTO saleDTO = new SalesHistoryDTO();
        saleDTO.setCpfCnpj("12312312300");
        saleDTO.setDescription("Description TEST TEST TEST");
        saleDTO.setPrice(new BigDecimal("999.999"));
        saleDTO.setDate(LocalDateTime.now());

        //METHOD TO CAPTURE SALERESPONSE
        String saleResponse = mockMvc.perform(post("/sales")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        //Client response with the created client payload
        System.out.println("CLIENT RESPONSE: " + clientResponse);

        //Sale response with the created sale payload
        System.out.println("SALE HISTORY RESPONSE: " + saleResponse);
    }
}
