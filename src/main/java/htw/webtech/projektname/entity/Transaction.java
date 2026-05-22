package htw.webtech.projektname.entity;

public class Transaction {
    private Long id;
    private String title;
    private double amount ;
    private String category;
    private String owner;

    public Transaction() {}

    public Transaction(Long id, String title, double amount, String category) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getOwner() { return owner; }
}