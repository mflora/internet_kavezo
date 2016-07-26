package internet_kvzo;

import internet_kvzo.controller.Controller;
import internet_kvzo.persistence.*;
import internet_kvzo.util.EncryptService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

class MockCache implements ICache{
    private final List<CustomerInfo> customer;
    private final List<ComputerInfo> computer;
    private final List<UsageInfo> usage;
    private final List<BillInfo> bill;
    
    public MockCache(){
        customer = new LinkedList<CustomerInfo>();
        computer = new LinkedList<ComputerInfo>();
        usage = new LinkedList<UsageInfo>();
        bill = new LinkedList<BillInfo>();
        
        computer.add(new ComputerInfo(0, "very fast", "much win", false));
        computer.add(new ComputerInfo(1, "very fast not", "much mac", false));
        
        customer.add(new CustomerInfo("Pista", "Yolo street","123567AB", "YOLO", EncryptService.passwordToMD5("123456")));
        customer.add(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista", EncryptService.passwordToMD5("asdqwe123")));
    }
    
    @Override
    public void update(){}
	
    @Override
    public void insert(CustomerInfo customerInfo){
        customer.add(customerInfo);
    }
    
    @Override
    public void modify(CustomerInfo customerInfo){
    }

    @Override
    public void addPay(String name, int price){
	bill.add(new BillInfo(name, price));
    }
    
    @Override
    public void addUsage(String name, int compID, Date in, Date out){
	usage.add(new UsageInfo(name, compID, in, out));
    }
    
    @Override
    public List<CustomerInfo> getCustomer() {
        return customer;
    }

    @Override
    public List<UsageInfo> getUsage() {
        return usage;
    }

    @Override
    public List<ComputerInfo> getComputer() {
        return computer;
    }   
    
    @Override
    public List<BillInfo> getBill(){
        return bill;
    }
}

public class ControllerTest{
    private Controller controller;
    
    @Before
    public void setUp(){        
        controller = new Controller(new MockCache());
    }
    
    @Test
    public void addNewCustomerNameTest(){	
        Assert.assertEquals("Nullpointer név", false, controller.addNewCustomer(new CustomerInfo(null, "street", "123456AB", "kisPista", "asdqwe123")));
        
        Assert.assertEquals("Üres név", false, controller.addNewCustomer(new CustomerInfo("", "street", "123456AB", "kisPista", "asdqwe123")));
        
        Assert.assertEquals("Hibás név", false, controller.addNewCustomer(new CustomerInfo("KisPista89", "street", "123456AB", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás név", false, controller.addNewCustomer(new CustomerInfo("Kis_Pista", "street", "123456AB", "kisPista", "asdqwe123")));
    }
	
    @Test
    public void addNewCustomerAddressTest(){
        Assert.assertEquals("Nullpointer utca", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", null, "123456AB", "kisPista", "asdqwe123")));
        Assert.assertEquals("Üres utca", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "", "123456AB", "kisPista", "asdqwe123")));
    }
    
    @Test
    public void addNewCustomerIdTest(){
        Assert.assertEquals("Nullpointer személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", null, "kisPista", "asdqwe123")));
        
        Assert.assertEquals("Üres személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "", "kisPista", "asdqwe123")));
        
        Assert.assertEquals("Hibás személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456A", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "12345AB", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "ABCDEF12", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456ABC", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "1234567A", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "1234567AB", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456 AB", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456_AB", "kisPista", "asdqwe123")));    
    }
    
    @Test
    public void addNewCustomerUsernameTest(){
        Assert.assertEquals("Nullpointer felhasználó név", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", null, "asdqwe123")));
        
        Assert.assertEquals("Üres felhasználó név", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "", "asdqwe123")));
        
        Assert.assertEquals("Hibás felhasználónév", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista*", "asdqwe123")));
        Assert.assertEquals("Hibás felhasználónév", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kis_Pista", "asdqwe123")));
        Assert.assertEquals("Hibás felhasználónév", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", ".kisPista", "asdqwe123"))); 
    }
    
    @Test
    public void addNewCustomerPwTest(){
        Assert.assertEquals("Nullpointer jelszó", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista", null)));
        
        Assert.assertEquals("Üres jelszó", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista", "")));
        
        Assert.assertEquals("Hibás jelszó", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista", "asdqw")));
        Assert.assertEquals("Hibás jelszó", false, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista", "asdqwe12345678")));
    }
    
    @Test
    public void addNewCustomerValidTest(){
        Assert.assertEquals("Nem sikerült beszúrni.", true, controller.addNewCustomer(new CustomerInfo("Pista", "Yolo street","123567AB", "YOLO2", "123456")));
        Assert.assertEquals("Nem sikerült beszúrni.", true, controller.addNewCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista2", "asdqwe123")));
    }
    
    @Test
    public void addNewCustomerInvalidTest(){
        Assert.assertEquals("Sikerült ismét beszúrni.", false, controller.addNewCustomer(new CustomerInfo("Pista", "Yolo street","123567AB", "YOLO", "123456")));
    }
            
    @Test
    public void modifyCustomerUsernameTest(){
        Assert.assertEquals("Nullpointer felhasználó név", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", null, "asdqwe123")));
        Assert.assertEquals("Üres felhasználó név", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "", "asdqwe123")));
		
        Assert.assertEquals("Hibás felhasználónév", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista*", "asdqwe123")));
        Assert.assertEquals("Hibás felhasználónév", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kis_Pista", "asdqwe123")));
        Assert.assertEquals("Hibás felhasználónév", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", ".kisPista", "asdqwe123")));   
    }
	
    @Test
    public void modifyCustomerPwTest(){
        Assert.assertEquals("Hibás jelszó", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista", "asdqw")));
        Assert.assertEquals("Hibás jelszó", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista", "asdqwe12345678")));
    }
    
    @Test
    public void modifyCustomerNameTest(){
        Assert.assertEquals("Hibás név", false, controller.modifyCustomer(new CustomerInfo("KisPista89", "street", "123456AB", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás név", false, controller.modifyCustomer(new CustomerInfo("Kis_Pista", "street", "123456AB", "kisPista", "asdqwe123")));
    }
    
    @Test
    public void modifyCustomerIdTest(){
        Assert.assertEquals("Hibás személyi", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456A", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "12345AB", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "ABCDEF12", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456ABC", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "1234567A", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "1234567AB", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456 AB", "kisPista", "asdqwe123")));
        Assert.assertEquals("Hibás személyi", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456_AB", "kisPista", "asdqwe123"))); 
    }
    
    @Test
    public void modifyCustomerInvalidTest(){
        Assert.assertEquals("Nem létező személy.", false, controller.modifyCustomer(new CustomerInfo("Kis Pista", "street", "123456AB", "kisPista3", "asdqwe123")));
    }
    
    @Test
    public void modifyCustomerValidTest(){		
        Assert.assertEquals("Nem sikerült módosítani1", true, controller.modifyCustomer(new CustomerInfo("Kis Joska", null, null, "kisPista", null)));
        Assert.assertEquals("Nem sikerült módosítani2", true, controller.modifyCustomer(new CustomerInfo("Kis Pista", "", "", "kisPista", "")));
        Assert.assertEquals("Nem sikerült módosítani3", true, controller.modifyCustomer(new CustomerInfo(null, "utca", null, "kisPista", null)));
        Assert.assertEquals("Nem sikerült módosítani4", true, controller.modifyCustomer(new CustomerInfo("", "utca", "", "kisPista", "")));
        Assert.assertEquals("Nem sikerült módosítani5", true, controller.modifyCustomer(new CustomerInfo(null, null, "123567AC", "kisPista", null)));
        Assert.assertEquals("Nem sikerült módosítani6", true, controller.modifyCustomer(new CustomerInfo("", "", "123567AC", "kisPista", "")));
        Assert.assertEquals("Nem sikerült módosítani7", true, controller.modifyCustomer(new CustomerInfo(null, null, null, "kisPista", "asd1234asd")));
        Assert.assertEquals("Nem sikerült módosítani8", true, controller.modifyCustomer(new CustomerInfo("", "", "", "kisPista", "asd1234asd")));
    }
    
    @Test
    public void loginUsernameTest(){
        Assert.assertEquals("Nullpointer felhasználó név", false, controller.login(null, "asdqwe123"));
		
        Assert.assertEquals("Nullpointer jelszó", false, controller.login("YOLO", null));	
    }
    
    @Test
    public void loginPwTest(){
        Assert.assertEquals("Üres felhasználó név", false, controller.login("", "asdqwe123"));
        
        Assert.assertEquals("Üres jelszó", false, controller.login("YOLO", ""));
    }
	
    @Test
    public void loginValidTest(){        
        Assert.assertEquals("Sikertelen belépés", true, controller.login("YOLO", "123456"));
        Assert.assertEquals("Sikertelen belépés", true, controller.login("kisPista", "asdqwe123"));
    }
    
    @Test
    public void loginInvalidTest(){
        Assert.assertEquals("Sikeres belépés", false, controller.login("YOLO123", "123456"));
        Assert.assertEquals("Sikeres belépés", false, controller.login("YOLO", "123456123"));
    }
    
    @Test
    public void addNewPayUsernameTest(){
        Assert.assertEquals("Nullpointer felhasználó név", false, controller.addNewPay(null, 500));
    }
    
    @Test
    public void addNewPayValueTest(){
        Assert.assertEquals("Negatív befizetés", false, controller.addNewPay("Pista", -100));
    }
    
    @Test
    public void addNewPayValidTest(){        
        Assert.assertEquals("Sikeres befizetés", false, controller.addNewPay("Pista", 1000));
    }
    
    @Test
    public void addNewPayInvalidTest(){        
        Assert.assertEquals("Sikertelen befizetés", true, controller.addNewPay("kisPista", 1000));
    }
    
    @Test
    public void addNewUsageUsernameTest(){
        Assert.assertEquals("Nullpointer felhasználó név", false, controller.addNewUsage(null, 5));

        Assert.assertEquals("Rossz név", false, controller.addNewUsage("Swagyolo", 0));
    }
    
    @Test
    public void addNewUsageCompIDTest(){
        Assert.assertEquals("Rossz compID", false, controller.addNewUsage("kisPista", 5));
    }
    
    @Test
    public void addNewUsageValidTest(){
        Assert.assertEquals("Sikertelen foglalás", true, controller.addNewUsage("kisPista", 0));
        Assert.assertEquals("Sikertelen foglalás", true, controller.addNewUsage("YOLO", 1));
    }
    
    @Test
    public void addNewUsageInvalidTest(){
        controller.addNewUsage("kisPista", 0);
        Assert.assertEquals("Sikeres foglalás", false, controller.addNewUsage("YOLO", 0));
    }
	
    @Test
    public void addFinishedUsageUsernameTest(){
        Assert.assertEquals("Nullpointer felhasználó név", false, controller.addFinishedUsage(null, 5));
        Assert.assertEquals("Nullpointer felhasználó név", false, controller.addFinishedUsage("kisPista5", 5));
    }
    
    @Test
    public void addFinishedUsageValidTest(){
        controller.addNewUsage("kisPista", 0);
        controller.addNewUsage("YOLO", 1);
        
        Assert.assertEquals("Sikertelen foglalás", true, controller.addFinishedUsage("YOLO", 1));
        Assert.assertEquals("Sikertelen foglalás", true, controller.addFinishedUsage("kisPista", 0));
        
    }
    
    @Test
    public void addFinishedUsageInvalidTest(){
        controller.addNewUsage("kisPista", 0);
        
        Assert.assertEquals("Sikeres befejezés", false, controller.addFinishedUsage("kisPista", 1));
        
        controller.addFinishedUsage("kisPista", 0);
        
        Assert.assertEquals("Sikeres befejezés", false, controller.addFinishedUsage("kisPista", 0));
    }
}
