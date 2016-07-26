package internet_kvzo.persistence;


import java.util.Date;


public class UsageInfo {
    
    private String username;
    private int compID;
    private Date in;
    private Date out;

    public UsageInfo(String username, int compID, Date in, Date out) {
        this.username = username;
        this.compID = compID;
        this.in = in;
        this.out = out;
    }

    public int getCompID() {
        return compID;
    }

    public Date getIn() {
        return in;
    }

    public Date getOut() {
        return out;
    }

    public String getUsername() {
        return username;
    }

    public void setOut(Date out) {
        this.out = out;
    }
    
    
    
}
