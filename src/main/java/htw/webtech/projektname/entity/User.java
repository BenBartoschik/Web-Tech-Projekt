package htw.webtech.projektname.entity;
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private double income;
    private double hardExpenses;

    public User(Long id, String firstName, String lastName, double income, double hardExpenses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.income = income;
        this.hardExpenses = hardExpenses;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getIncome() { return income; }
    public double getHardExpenses() { return hardExpenses; }
}