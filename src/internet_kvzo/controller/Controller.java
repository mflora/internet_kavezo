package internet_kvzo.controller;


import internet_kvzo.persistence.*;
import internet_kvzo.util.*;

import java.util.Date;
import java.util.List;


public class Controller {
    private ICache cache;
    
    public String currCustomer;
    public int currComputer;
    
    private boolean lock;
    
    public Controller(ICache cache){        
        this.cache = cache;
        
        this.currCustomer = null;
        this.currComputer = -1;
        this.lock = false;
    }
    
    public boolean addNewCustomer(CustomerInfo customerInfo){
        IValidationService service = new AddValidationService();
        
        if(!service.isValid(customerInfo)){
            return false;
        }
        
        List<CustomerInfo> customers = cache.getCustomer();
        for (CustomerInfo customer : customers){
            if (customer.getUsername().equals(customerInfo.getUsername())){
                return false;
            }
        }
        
        customerInfo.setPw(EncryptService.passwordToMD5(customerInfo.getPw()));
        
        cache.insert(customerInfo);
        return true;
    }
    
    public boolean modifyCustomer(CustomerInfo customerInfo){
        IValidationService service = new ModifyValidationService();
        
        if(!service.isValid(customerInfo)){
            return false;
        }
		
	List<CustomerInfo> customers = cache.getCustomer();
        for (CustomerInfo customer : customers){
			
            if (customer.getUsername().equals(customerInfo.getUsername())){
				
                if (customerInfo.getName() != null && !customerInfo.getName().isEmpty()){
                    customer.setName(customerInfo.getName());
		}
                if (customerInfo.getAddress() != null && !customerInfo.getAddress().isEmpty()){
                    customer.setAddress(customerInfo.getAddress());
		}
                if (customerInfo.getId() != null && !customerInfo.getId().isEmpty()){
                    customer.setId(customerInfo.getId());
		}
                if (customerInfo.getPw() != null && !customerInfo.getPw().isEmpty()){
                    customer.setPw(EncryptService.passwordToMD5(customerInfo.getPw()));
		}
				
                cache.modify(customerInfo);
		return true;
            }
        }
		
        return false;
    }
    
    public boolean login(String username, String pw){
		
	if (username == null || pw == null){
            return false;
	}
		
        String encryptedPw = EncryptService.passwordToMD5(pw);
        
	List<CustomerInfo> customers = cache.getCustomer();
        for (CustomerInfo customer : customers){		
            if (customer.getUsername().equals(username) && customer.getPw().equals(encryptedPw)){
		return true;
            }
        }
		
	return false;
    }
    
    public boolean addNewPay(String username, int price){
		
	if (username == null || price < 0)
	{
		return false;
	}
		
	List<CustomerInfo> customers = cache.getCustomer();
        for (CustomerInfo customer : customers){	
            if (customer.getUsername().equals(username)){		
		cache.addPay(username, price);
		return true;
            }
        }
		
        return false;
    }
    
    public boolean addNewUsage(String username, int compID){
		
	if (username == null)
	{
            return false;
	}
                
	boolean l = false;
	List<CustomerInfo> customers = cache.getCustomer();
        for (CustomerInfo customer : customers){		
            if (customer.getUsername().equals(username)){		
		l = true;
            }
        }
		
	if (!l){
		return false;
	}
	
        l = false;
	List<UsageInfo> UsageInfo = cache.getUsage();
        for (UsageInfo usage : UsageInfo){		
            if(usage.getUsername() == username && usage.getOut() == null){
                l = true;
            }
        }
		
	if (l){
            return false;
	}
        
	List<ComputerInfo> computers = cache.getComputer();
        for (ComputerInfo computer : computers){
            if (computer.getCompID() == compID && !computer.getInUse()){		
		computer.setInUse(true);
		cache.addUsage(username, compID, new Date(), null);
		return true;
            }
        }

        return false;
    }
    
    public boolean addFinishedUsage(String username, int compID){
        while(lock){
        }
        lock = true;
        
	if (username == null)
	{
            return false;
	}
		
	boolean l = false;
	List<UsageInfo> usages = cache.getUsage();
        for (UsageInfo usage : usages){
            if (usage.getCompID() == compID && usage.getUsername().equals(username) && usage.getOut() == null){	
		usage.setOut(new Date());
                cache.addUsage(username, compID, usage.getIn(), usage.getOut());
                
                long diff = usage.getOut().getTime() - usage.getIn().getTime();
                int hours = (int)Math.ceil(diff/(60.0*60.0*1000.0));
                double discount = calculateDiscount(username);
                cache.addPay(username, (int)(-hours * 100 * discount));
		l = true;
            }
        }
        
	if (!l){
            return false;
	}

	List<ComputerInfo> computers = cache.getComputer();
        for (ComputerInfo computer : computers){
            if (computer.getCompID() == compID && computer.getInUse()){
		computer.setInUse(false);
            }
        }
        
        lock = false;
        return true;
    }  
    
    private double calculateDiscount(String username){
        int points = 0;
        
        for (UsageInfo usageInfo: cache.getUsage()){
            if (usageInfo.getUsername().equals(username)){

                if (usageInfo.getIn().getHours() < 16 && usageInfo.getOut().getHours() <= 16){
                    long diff = usageInfo.getOut().getTime() - usageInfo.getIn().getTime();
                    int hours = (int)Math.ceil(diff/(60.0*60.0*1000.0));
                    
                    points += 2*hours;
                }else if (usageInfo.getIn().getHours() >= 21 && usageInfo.getOut().getHours() > 21){
                    long diff = usageInfo.getOut().getTime() - usageInfo.getIn().getTime();
                    int hours = (int)Math.ceil(diff/(60.0*60.0*1000.0));
                    
                    points += 2*hours;
                }else{
                    long diff = usageInfo.getOut().getTime() - usageInfo.getIn().getTime();
                    int hours = (int)Math.ceil(diff/(60.0*60.0*1000.0));
                    
                    points += hours;
                }
                
            }
        }
        
        int discount = points / 150;
        discount = Math.min(discount, 15);
        
        return (100 - discount) / 100.0;
    }
}
