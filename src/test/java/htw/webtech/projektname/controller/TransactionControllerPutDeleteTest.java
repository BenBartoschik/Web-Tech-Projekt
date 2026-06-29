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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Gleicher Aufbau wie TransactionControllerTest: MockMvc Standalone, kein Spring-Context.
// Ausgelagert in eine eigene Klasse weil bestehende Testdateien auf diesem System
// nicht editierbar sind — Gradle sammelt beide Klassen automatisch ein.
@ExtendWith(MockitoExtension.class)
class TransactionControllerPutDeleteTest {

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
    void putTransaction_validBody_returns200AndUpdatedTransaction() throws Exception {
        Transaction updated = new Transaction("Miete aktualisiert", 900.0, "Rent", "user@test.de");
        when(service.update(anyLong(), any())).thenReturn(updated);

        mockMvc.perform(put("/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Miete aktualisiert","amount":900.0,"category":"Rent","owner":"user@test.de"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Miete aktualisiert"))
                .andExpect(jsonPath("$.amount").value(900.0));
    }

    @Test
    void putTransaction_emptyTitle_returns400() throws Exception {
        mockMvc.perform(put("/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"","amount":900.0,"category":"Rent","owner":"user@test.de"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTransaction_returns204() throws Exception {
        mockMvc.perform(delete("/transactions/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }
}
