package htw.webtech.projektname.controller;

import htw.webtech.projektname.entity.Transaction;
import htw.webtech.projektname.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Der Controller ist nur für HTTP zuständig: Routing, Eingabevalidierung, Statuscodes.
// Geschäftslogik und Datenbankzugriff liegen im TransactionService.
@RestController
@CrossOrigin
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping({"/transactions", "/"})
    public List<Transaction> getTransactions(
            @RequestParam(name = "owner", defaultValue = "") String owner
    ) {
        return service.getByOwner(owner);
    }

    @GetMapping("/transactions/sum")
    public ResponseEntity<?> getSum(
            @RequestParam(name = "owner", defaultValue = "") String owner
    ) {
        if (owner.isBlank()) {
            return ResponseEntity.badRequest().body("owner darf nicht leer sein");
        }
        return ResponseEntity.ok(service.sumByOwner(owner));
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        if (transaction.getTitle() == null || transaction.getTitle().isBlank()) {
            return ResponseEntity.badRequest().body("title darf nicht leer sein");
        }
        if (transaction.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("amount muss positiv sein");
        }
        return ResponseEntity.ok(service.update(id, transaction));
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        if (transaction.getTitle() == null || transaction.getTitle().isBlank()) {
            return ResponseEntity.badRequest().body("title darf nicht leer sein");
        }
        if (transaction.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("amount muss positiv sein");
        }
        return ResponseEntity.ok(service.create(transaction));
    }
}