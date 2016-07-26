package internet_kvzo.util;

import internet_kvzo.persistence.CustomerInfo;

public class ModifyValidationService implements IValidationService {
    @Override
    public boolean isValid(Object obj){
        CustomerInfo in = (CustomerInfo)obj;
        
        if (in.getUsername() == null || in.getUsername().isEmpty() || !in.getUsername().matches("[A-Za-z0-9]+")){
            return false;
        }
        
        if (in.getName() != null && !in.getName().isEmpty() && !in.getName().matches("[A-Za-z ]+")){
            return false;
        }
        
        if (in.getId() != null && !in.getId().isEmpty() && (in.getId().length() != 8 || !in.getId().substring(0, 5).matches("[0-9]+") || !in.getId().substring(6,7).matches("[A-Z]+"))){
            return false;
        }
		
        if (in.getPw() != null && !in.getPw().isEmpty() && (in.getPw().length() < 6 || in.getPw().length() > 13)){
            return false;
        }
        
        return true;
    }
}
