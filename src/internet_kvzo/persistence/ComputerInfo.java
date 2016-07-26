package internet_kvzo.persistence;

public class ComputerInfo {
    private int compID;
    private String hw;
    private String os;
    private boolean inUse;

    public ComputerInfo(int compID, String hw, String os, boolean inUse) {
        this.compID = compID;
        this.hw = hw;
        this.os = os;
        this.inUse = inUse;
    }

    public int getCompID() {
        return compID;
    }

    public String getHw() {
        return hw;
    }

    public String getOs() {
        return os;
    }

    public boolean getInUse() {
        return inUse;
    }    

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
    
    
}
