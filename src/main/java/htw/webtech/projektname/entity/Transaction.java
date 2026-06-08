package htw.webtech.projektname.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private double amount;
    private String category;

    public Transaction() {}

    public Transaction(String title, double amount, String category) {
        this.title = title;
        this.amount = amount;
        this.category = category;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
}