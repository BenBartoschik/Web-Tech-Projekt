package htw.webtech.projektname.service;

import htw.webtech.projektname.entity.Transaction;
import htw.webtech.projektname.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Der Service enthält die Geschäftslogik und kennt das Repository.
// Vorteile der Schichtentrennung (Controller → Service → Repository):
// - Controller bleibt schlank: nur HTTP-Mapping und Eingabevalidierung
// - Geschäftsregeln (z. B. Validierung, Berechnungen) lassen sich im Service
//   unabhängig von HTTP testen (z. B. Unit-Tests ohne MockMvc)
// - Das Repository lässt sich austauschen, ohne den Controller anzufassen
@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getByOwner(String owner) {
        if (owner.isBlank()) {
            return List.of();
        }
        return repository.findByOwner(owner);
    }

    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    public double sumByOwner(String owner) {
        return repository.findByOwner(owner).stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Transaction update(Long id, Transaction data) {
        Transaction existing = repository.findById(id).orElseThrow();
        existing.setTitle(data.getTitle());
        existing.setAmount(data.getAmount());
        existing.setCategory(data.getCategory());
        return repository.save(existing);
    }
}
