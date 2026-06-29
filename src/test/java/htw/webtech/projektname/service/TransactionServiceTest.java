package htw.webtech.projektname.service;

import htw.webtech.projektname.entity.Transaction;
import htw.webtech.projektname.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Reiner Unit-Test ohne Spring-Context: @Mock ersetzt das Repository durch ein Mockito-Dummy,
// @InjectMocks erstellt den Service und injiziert das Mock automatisch.
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    // update() soll title, amount und category überschreiben,
    // aber id und owner der bestehenden Transaktion NICHT verändern.
    @Test
    void update_overwritesTitleAmountCategory_butKeepsIdAndOwner() {
        Transaction existing = new Transaction("Alt", 100.0, "Food", "owner@test.de");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Transaction data = new Transaction("Neu", 250.0, "Rent", "anderer@test.de");
        Transaction result = service.update(1L, data);

        assertThat(result.getTitle()).isEqualTo("Neu");
        assertThat(result.getAmount()).isEqualTo(250.0);
        assertThat(result.getCategory()).isEqualTo("Rent");
        // id bleibt null (kein Setter, kein Konstruktor-Argument) — d.h. update() setzt sie nicht
        assertThat(result.getId()).isNull();
        // owner darf nicht durch den data-Parameter überschrieben werden
        assertThat(result.getOwner()).isEqualTo("owner@test.de");
    }

    // delete() soll deleteById() mit genau der übergebenen ID aufrufen — nichts anderes.
    @Test
    void delete_callsDeleteByIdWithCorrectId() {
        service.delete(42L);

        verify(repository).deleteById(42L);
    }

    // sumByOwner() soll nur die Beträge des angegebenen Owners summieren.
    // Das Repository liefert bereits gefilterte Daten — der Test stellt sicher,
    // dass der Service die Summe korrekt berechnet, auch wenn andere Owner existieren würden.
    @Test
    void sumByOwner_sumsOnlyAmountsOfGivenOwner() {
        List<Transaction> owner1Transactions = List.of(
            new Transaction("Gehalt",  2000.0, "Income", "owner1@test.de"),
            new Transaction("Miete",    850.0, "Rent",   "owner1@test.de"),
            new Transaction("Lebensmittel", 150.0, "Food", "owner1@test.de")
        );
        // Repository gibt nur owner1-Transaktionen zurück (wie im echten Betrieb)
        when(repository.findByOwner("owner1@test.de")).thenReturn(owner1Transactions);

        double sum = service.sumByOwner("owner1@test.de");

        assertThat(sum).isEqualTo(3000.0);
    }
}
