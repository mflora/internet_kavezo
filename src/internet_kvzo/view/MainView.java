package internet_kvzo.view;


import internet_kvzo.controller.Controller;
import internet_kvzo.util.CustomObservable;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainView extends JFrame{
    
    private JButton computerListButton;
    private JButton customerListButton;
    private JButton newCustomerButton;
    private JButton modifyButton;
    private JButton billListButton;
    private JButton actionButton;
    
    private AbstractAction buttonPressed;
    
    public CustomObservable finished;
    
    private Controller controller;
    
    public MainView(Controller controller){
        this.controller = controller;
        
        finished = new CustomObservable();
        
        buttonPressed = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent event){
                if(event.getSource() == computerListButton){
                    finished.notifyObservers("computers");
                }
                
                if(event.getSource() == customerListButton){
                    finished.notifyObservers("customers");
                }
                
                if(event.getSource() == newCustomerButton){
                    finished.notifyObservers("new");
                }
                
                if(event.getSource() == modifyButton){
                    finished.notifyObservers("modify");
                }
                
                if(event.getSource() == billListButton){
                    finished.notifyObservers("bills");
                }
                
                if(event.getSource() == actionButton){
                    finished.notifyObservers("action");
                }
            }
        };
        
        computerListButton = new JButton(buttonPressed);
        computerListButton.setMinimumSize(new Dimension(200, 40));
        computerListButton.setMaximumSize(new Dimension(200, 40));
        computerListButton.setText("Számítógépek listázása");
        
        customerListButton = new JButton(buttonPressed);
        customerListButton.setMinimumSize(new Dimension(200, 40));
        customerListButton.setMaximumSize(new Dimension(200, 40));
        customerListButton.setText("Felhasználók listázása");
        
        newCustomerButton = new JButton(buttonPressed);
        newCustomerButton.setMinimumSize(new Dimension(200, 40));
        newCustomerButton.setMaximumSize(new Dimension(200, 40));
        newCustomerButton.setText("Új felhasználó felvétele");
        
        modifyButton = new JButton(buttonPressed);
        modifyButton.setMinimumSize(new Dimension(200, 40));
        modifyButton.setMaximumSize(new Dimension(200, 40));
        modifyButton.setText("Felhasználó módosítása");
        
        billListButton = new JButton(buttonPressed);
        billListButton.setMinimumSize(new Dimension(200, 40));
        billListButton.setMaximumSize(new Dimension(200, 40));
        billListButton.setText("Számlák listázása");
        
        actionButton = new JButton(buttonPressed);
        actionButton.setMinimumSize(new Dimension(200, 40));
        actionButton.setMaximumSize(new Dimension(200, 40));
        actionButton.setText("Műveletek");
        
        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(6,1));
        
        pan.add(computerListButton);
        pan.add(customerListButton);
        pan.add(newCustomerButton);
        pan.add(modifyButton);
        pan.add(billListButton);
        pan.add(actionButton);
        
        Container contentPane = getContentPane();
        contentPane.add(pan, BorderLayout.CENTER);
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
}
