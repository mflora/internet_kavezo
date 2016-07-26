package internet_kvzo.model;

import internet_kvzo.persistence.CustomerInfo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CustomerTableModel extends AbstractTableModel {

    private List<CustomerInfo> entries = new ArrayList<>();
    private static final List<String> columnNames = new ArrayList<>();
    private static final String COLUMN_NAME = "Név";
    private static final String COLUMN_ADDRESS = "Cím";
    private static final String COLUMN_ID = "Személyi";
    private static final String COLUMN_USERNAME = "Felhasználó";
    private static final String COLUMN_PW = "Jelszó";

    static {
        columnNames.add(COLUMN_NAME);
        columnNames.add(COLUMN_ADDRESS);
        columnNames.add(COLUMN_ID);
        columnNames.add(COLUMN_USERNAME);
        columnNames.add(COLUMN_PW);
    }

    @Override
    public int getRowCount() {
        return entries.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CustomerInfo customerInfo = entries.get(rowIndex);
        String coloumn = columnNames.get(columnIndex);
        switch(coloumn) {
            case COLUMN_NAME: return customerInfo.getName();
            case COLUMN_ADDRESS: return customerInfo.getAddress();
            case COLUMN_ID: return customerInfo.getId();
            case COLUMN_USERNAME: return customerInfo.getUsername();
            case COLUMN_PW: return "***";
            default: throw new IllegalArgumentException(coloumn + " does not exists!!");
        }
    }
    
    public CustomerTableModel(List<CustomerInfo> entries){
        this.entries = entries;
    }
}
