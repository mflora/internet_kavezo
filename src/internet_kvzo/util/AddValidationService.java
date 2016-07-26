package internet_kvzo.util;

import internet_kvzo.persistence.CustomerInfo;

public class AddValidationService implements IValidationService{
    @Override
    public boolean isValid(Object obj){
        CustomerInfo in = (CustomerInfo)obj;
        
        //None of them is null
        if(in.getName() == null || in.getId() == null || in.getUsername() == null || in.getPw() == null || in.getAddress() == null){
            return false;
        }
        
        //None of them is empty
        if (in.getName().isEmpty() || in.getAddress().isEmpty() || in.getId().isEmpty() || in.getUsername().isEmpty() || in.getPw().isEmpty()){
            return false;
        }
        
        //Contains special character 3.2.3.7
        if (!in.getUsername().matches("[A-Za-z0-9]+")){
            return false;
        }
        
        //Password between 6 and 13 characters 3.2.3.8
        if (in.getPw().length() < 6 || in.getPw().length() > 13){
            return false;
        }
        
        //Valid name 3.2.3.9
        if (!in.getName().matches("[A-Za-z ]+")){
            return false;
        }
        
        //Valid ID 3.2.3.10
        if (in.getId().length() != 8 || !in.getId().substring(0, 5).matches("[0-9]+") || !in.getId().substring(6,7).matches("[A-Z]+")){
            return false;
        }
        
        return true;
    }
}
