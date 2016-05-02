public class Player {

    private double balance;
    private String name, username, password;

    public Player(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
