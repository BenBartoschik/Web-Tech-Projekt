package htw.webtech.projektname.controller;

import htw.webtech.projektname.entity.Transaction;
import htw.webtech.projektname.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TransactionController {

    private final TransactionRepository repository;

    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping({"/transactions", "/"})
    public List<Transaction> getTransactions(
            @RequestParam(name = "owner", defaultValue = "") String owner
    ) {
        if(owner.isBlank()){
            return List.of();
    }
        return repository.findByOwner(owner);
    }

    @PostMapping("/transactions")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return repository.save(transaction);
    }
}