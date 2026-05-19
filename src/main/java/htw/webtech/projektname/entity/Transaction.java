package htw.webtech.projektname.entity;

public class Transaction {
    private Long id;
    private String title;
    private double amount ;
    private String category;

    public Transaction(Long id, String title, double amount, String category) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
}