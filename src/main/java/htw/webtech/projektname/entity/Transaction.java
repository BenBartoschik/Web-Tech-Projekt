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
    private String owner;

    public Transaction() {}

    public Transaction(String title, double amount, String category, String owner) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getOwner(){return owner; }
}