package htw.webtech.projektname.controller;

import htw.webtech.projektname.entity.Transaction;
import htw.webtech.projektname.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest existiert in Spring Boot 4.x nicht mehr (wurde entfernt).
// @SpringBootTest würde den vollständigen Application-Context hochfahren (inkl. DB) —
// für reine Controller-Tests zu schwer und ohne In-Memory-DB hier nicht nutzbar.
//
// Stattdessen: MockMvc im Standalone-Modus.
// MockMvcBuilders.standaloneSetup() registriert nur den einen Controller, kein Spring-Context.
// Das entspricht dem Konzept von @WebMvcTest (nur Web-Schicht, Service gemockt),
// funktioniert aber ohne Spring-Boot-Test-Autoconfigure.
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService service;

    @InjectMocks
    private TransactionController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void postTransaction_validBody_returns200AndSavedTransaction() throws Exception {
        Transaction saved = new Transaction("Miete", 850.0, "Rent", "user@test.de");
        when(service.create(any())).thenReturn(saved);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Miete","amount":850.0,"category":"Rent","owner":"user@test.de"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Miete"))
                .andExpect(jsonPath("$.amount").value(850.0))
                .andExpect(jsonPath("$.owner").value("user@test.de"));
    }

    @Test
    void postTransaction_emptyTitle_returns400() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"","amount":100.0,"category":"Income","owner":"user@test.de"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postTransaction_negativeAmount_returns400() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Test","amount":-50.0,"category":"Income","owner":"user@test.de"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTransactions_withOwner_returnsOnlyMatchingTransactions() throws Exception {
        Transaction t1 = new Transaction("Gehalt", 2000.0, "Income", "user@test.de");
        when(service.getByOwner("user@test.de")).thenReturn(List.of(t1));

        mockMvc.perform(get("/transactions").param("owner", "user@test.de"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].owner").value("user@test.de"));
    }

    @Test
    void getTransactions_withoutOwner_returnsEmptyList() throws Exception {
        when(service.getByOwner("")).thenReturn(List.of());

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
