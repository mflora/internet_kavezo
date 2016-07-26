package internet_kvzo.model;

import internet_kvzo.persistence.BillInfo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class BillTableModel extends AbstractTableModel {

    private List<BillInfo> entries = new ArrayList<>();
    private static final List<String> columnNames = new ArrayList<>();
    private static final String COLUMN_USER = "Felhasználó";
    private static final String COLUMN_PRICE = "Összeg";

    static {
        columnNames.add(COLUMN_USER);
        columnNames.add(COLUMN_PRICE);
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
        BillInfo billInfo = entries.get(rowIndex);
        String coloumn = columnNames.get(columnIndex);
        switch(coloumn) {
            case COLUMN_USER: return billInfo.getUsername();
            case COLUMN_PRICE: return billInfo.getPrice();
            default: throw new IllegalArgumentException(coloumn + " does not exists!!");
        }
    }
    
    public BillTableModel(List<BillInfo> entries){
        this.entries = entries;
    }
}
