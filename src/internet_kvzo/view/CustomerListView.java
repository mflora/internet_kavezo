package internet_kvzo.view;

import javax.swing.JTable;

import internet_kvzo.controller.Controller;
import internet_kvzo.model.CustomerTableModel;
import internet_kvzo.persistence.CustomerInfo;
import internet_kvzo.util.CustomObservable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class CustomerListView extends JFrame {
    private Controller controller;
    private JTable table;
    private CustomerTableModel customerTableModel;
    
    private JButton saveButton;
    private JButton backButton;
    
    private AbstractAction buttonPressed;
    
    public CustomObservable finished;
    
    public CustomerListView(Controller controller) {
        this.controller = controller;
        
        finished = new CustomObservable();
        
                buttonPressed = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent event){
                if (event.getSource() == backButton){
                    finished.notifyObservers(null);
                }
                if (event.getSource() == saveButton){
                    controller.currCustomer = (String)table.getValueAt(table.getSelectedRow(), 3);
                    finished.notifyObservers("done");
                }
            }
        };
        
        table = new JTable();
        
        this.setMinimumSize(new Dimension(550, 400));
        
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        
        saveButton = new JButton(buttonPressed);
        saveButton.setMinimumSize(new Dimension(100, 40));
        saveButton.setMaximumSize(new Dimension(100, 40));
        saveButton.setText("Kiválaszt");
        
        backButton = new JButton(buttonPressed);
        backButton.setMinimumSize(new Dimension(100, 40));
        backButton.setMaximumSize(new Dimension(100, 40));
        backButton.setText("Vissza");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        panel.add(scrollPane);
        panel.add(buttonPanel);
        
        add(panel);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void setTableModel(CustomerTableModel customerTableModel){
        this.customerTableModel = customerTableModel;
        
        table.setModel(this.customerTableModel);
        table.getColumnModel().getColumn(0).setMinWidth(100);
        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMinWidth(100);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
}
