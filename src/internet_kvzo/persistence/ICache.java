package internet_kvzo.persistence;


import java.util.Date;
import java.util.List;


public interface ICache {
    public void update();
    public void insert(CustomerInfo customerInfo);
    public void modify(CustomerInfo customerInfo);
    public void addPay(String name, int price);
    public void addUsage(String name, int compID, Date in, Date out);
    public List<CustomerInfo> getCustomer();
    public List<UsageInfo> getUsage();
    public List<ComputerInfo> getComputer();
    public List<BillInfo> getBill();
}
