package internet_kvzo.model;

import internet_kvzo.persistence.ComputerInfo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ComputerTableModel extends AbstractTableModel {

    private List<ComputerInfo> entries = new ArrayList<>();
    private static final List<String> columnNames = new ArrayList<>();
    private static final String COLUMN_ID = "Azonosító";
    private static final String COLUMN_HW = "Hardver";
    private static final String COLUMN_OS = "Operációs rendszer";
    private static final String COLUMN_INUSE = "Használatban van-e";

    static {
        columnNames.add(COLUMN_ID);
        columnNames.add(COLUMN_HW);
        columnNames.add(COLUMN_OS);
        columnNames.add(COLUMN_INUSE);
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
        ComputerInfo computerInfo = entries.get(rowIndex);
        String coloumn = columnNames.get(columnIndex);
        switch(coloumn) {
            case COLUMN_ID: return computerInfo.getCompID();
            case COLUMN_HW: return computerInfo.getHw();
            case COLUMN_OS: return computerInfo.getOs();
            case COLUMN_INUSE: return computerInfo.getInUse();
            default: throw new IllegalArgumentException(coloumn + " does not exists!!");
        }
    }
    
    public ComputerTableModel(List<ComputerInfo> entries){
        this.entries = entries;
    }
}
