/**
 * NegativeCasesTest
 *
 * 1. Log in and obtain a JWT token.
 *
 * 2. Create a test client with a valid CPF/CNPJ.
 *
 * 3. Attempt to create another client with the same CPF/CNPJ.
 *
 *    - Expected result: DuplicatedResourceException (HTTP 409 Conflict).
 *
 * 4. Attempt to create a sale for an unregistered client (invalid CPF/CNPJ).
 *    - Expected result: ClientNotFoundException (HTTP 404 Not Found).
 *
 * 5. Attempt to delete a non-existent sale ID from a valid client.
 *    - Expected result: SaleNotFoundException (HTTP 404 Not Found).
 *
 * Observations:
 * - For this test to function correctly, duplicate entry handling and exception handling
 *   for non-existent clients and sales must be properly implemented in the service layer
 *   and Global Exception Handler.
 * - The application must have a DELETE endpoint for sales removal (e.g., "/sales/{saleId}"
 *   with a "cpfCnpj" parameter).
**/
package ArtBoxSnapShot.ArtboxSnapshot.Tests;


import ArtBoxSnapShot.ArtboxSnapshot.controller.AuthController;
import ArtBoxSnapShot.ArtboxSnapshot.dto.AuthRequest;
import ArtBoxSnapShot.ArtboxSnapshot.dto.AuthResponse;
import ArtBoxSnapShot.ArtboxSnapshot.dto.ClientDTO;
import ArtBoxSnapShot.ArtboxSnapshot.dto.SalesHistoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class NegativeCasesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //Helper method to perform login and retrieve a valid JWT token
    private String obtainToken() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin");

        String authResponseContent = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        AuthResponse authResponse = objectMapper.readValue(authResponseContent, AuthResponse.class);
        return authResponse.getJwt();
    }

    @Test
    public void testNegativoScenarios() throws Exception {
        String token = obtainToken();

        //1° STEP: CREATE A NEW CLIENT WITH A SPECIFIC CPF/CNPJ
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setCpfCnpj("11111111111");
        clientDTO.setName("Test Client");
        clientDTO.setEmail("testclient@example.com");
        clientDTO.setAddress("Test Adress");
        clientDTO.setPhone_number("123456789");

        //Creates the client.
        mockMvc.perform(post("/clients")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk());

        //2° STEP: ATTEMP TO CREATE THE SAME CLIENT AGAIN (should trigger a duplicate error)
        mockMvc.perform(post("/clients")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isConflict()); //EXPECT HTTP 409 (conflict)

        //3° STEP: ATTEMP TO CREATE A SALE FOR A CLIENT THAT DON'T EXIST (invalid CPF/CNPJ)
        SalesHistoryDTO invalidSaleDTO = new SalesHistoryDTO();
        invalidSaleDTO.setCpfCnpj("00000000000"); //Invalid cpf
        invalidSaleDTO.setDescription("Invalid Sale Test");
        invalidSaleDTO.setPrice(new BigDecimal("00.00"));
        invalidSaleDTO.setDate(LocalDateTime.now());

        mockMvc.perform(post("/sales")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSaleDTO)))
                .andExpect(status().isNotFound()); //EXPECT HTTP 404 (ClientNotFound)

        //4° STEP: ATTEMP TO DELETE A SALE THAT DON'T EXIST FOR A VALID CLIENT.
        mockMvc.perform(delete("/sales/{saleId}", 99999)
                .header("Authorization", "Bearer " + token)
                .param("cpfCnpj", "11111111111"))
                .andExpect(status().isNotFound());

    }
}