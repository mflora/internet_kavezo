package internet_kvzo.view;

import internet_kvzo.controller.Controller;
import internet_kvzo.util.CustomObservable;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ActionView extends JFrame{
    
    private JButton payButton;
    private JButton loginButton;
    private JButton logoutButton;
    private JButton backButton;
    
    private AbstractAction buttonPressed;
    
    public CustomObservable finished;
    
    private Controller controller;
    
    public ActionView(Controller controller){
        this.controller = controller;
        
        finished = new CustomObservable();
        
        buttonPressed = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent event){
                if(event.getSource() == payButton){
                    int price = Integer.parseInt(JOptionPane.showInputDialog("Összeg?"));
                    
                    if(controller.addNewPay(controller.currCustomer, price)){
                        JOptionPane.showMessageDialog(null, "Befizetés sikeres.");
                    }else{
                        JOptionPane.showMessageDialog(null, "Befizetés sikertelen.");
                    }

                    controller.currCustomer = null;
                    controller.currComputer = -1;
                }
                
                if(event.getSource() == loginButton){
                    if(controller.addNewUsage(controller.currCustomer, controller.currComputer)){
                        JOptionPane.showMessageDialog(null, "Belépés sikeres.");
                    }else{
                        JOptionPane.showMessageDialog(null, "Belépés sikertelen.");
                    }
                    
                    controller.currCustomer = null;
                    controller.currComputer = -1;
                }
                
                if(event.getSource() == logoutButton){
                    if(controller.addFinishedUsage(controller.currCustomer, controller.currComputer)){
                        JOptionPane.showMessageDialog(null, "Kiléptetés sikeres.");
                    }else{
                        JOptionPane.showMessageDialog(null, "Kiléptetés sikertelen.");
                    }
                    
                    controller.currCustomer = null;
                    controller.currComputer = -1;
                }
                
                finished.notifyObservers("");
            }
        };
        
        payButton = new JButton(buttonPressed);
        payButton.setMinimumSize(new Dimension(200, 40));
        payButton.setMaximumSize(new Dimension(200, 40));
        payButton.setText("Befizetés");
        
        loginButton = new JButton(buttonPressed);
        loginButton.setMinimumSize(new Dimension(200, 40));
        loginButton.setMaximumSize(new Dimension(200, 40));
        loginButton.setText("Használat kezdése");
        
        logoutButton = new JButton(buttonPressed);
        logoutButton.setMinimumSize(new Dimension(200, 40));
        logoutButton.setMaximumSize(new Dimension(200, 40));
        logoutButton.setText("Használat befejezése");
        
        backButton = new JButton(buttonPressed);
        backButton.setMinimumSize(new Dimension(200, 40));
        backButton.setMaximumSize(new Dimension(200, 40));
        backButton.setText("Vissza");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,1));
        
        panel.add(payButton);
        panel.add(loginButton);
        panel.add(logoutButton);
        panel.add(backButton);
        
        Container contentPane = getContentPane();
        contentPane.add(panel, BorderLayout.CENTER);
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
}
