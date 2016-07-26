package internet_kvzo.persistence;

public class BillInfo {
    
    private String username;
    private int price;

    public BillInfo(String username, int price) {
        this.username = username;
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public int getPrice() {
        return price;
    }
    
}
