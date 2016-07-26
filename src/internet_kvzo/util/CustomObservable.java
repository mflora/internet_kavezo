package internet_kvzo.util;

import java.util.Observable;

public class CustomObservable extends Observable {
    @Override
    public void notifyObservers(Object arg){
        setChanged();
        super.notifyObservers(arg);
    }
}
