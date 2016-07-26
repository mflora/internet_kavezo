package internet_kvzo.persistence;

public class CustomerInfo {
    
    String username;
    String pw;
    String name;
    String address;
    String id;

    public CustomerInfo(String name, String address, String id, String username, String pw) {
        this.username = username;
        this.pw = pw;
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPw() {
        return pw;
    }

    public String getUsername() {
        return username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
    
    
    
    
}
