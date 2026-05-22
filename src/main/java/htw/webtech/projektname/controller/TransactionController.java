package htw.webtech.projektname.controller;

import htw.webtech.projektname.entity.Transaction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @CrossOrigin
    @GetMapping("/")
    public List<Transaction> getTransactions() {
        return List.of(
                new Transaction(1L, "Miete", -800, "Wohnen"),
                new Transaction(2L, "Gehalt", 1500, "Einnahmen"),
                new Transaction(3L, "Restaurant", -120, "Essen")
                );
    }
}