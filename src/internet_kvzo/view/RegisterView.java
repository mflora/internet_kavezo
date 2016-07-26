package internet_kvzo.view;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTextField;
import internet_kvzo.controller.Controller;
import internet_kvzo.persistence.CustomerInfo;
import internet_kvzo.util.CustomObservable;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class RegisterView extends JFrame {
    
    private JButton saveButton;
    private JButton backButton;
    
    private JTextField inName;
    private JTextField inAddress;
    private JTextField inID;
    private JTextField inUsername;
    private JPasswordField inPw;
    
    private Controller controller;
    
    private AbstractAction buttonPressed;
    
    public CustomObservable finished;
    
    public RegisterView(Controller controller){
        this.controller = controller;
        
        finished = new CustomObservable();
        
        inName = new JTextField();
        inName.setMinimumSize(new Dimension(100, 40));
        inName.setMaximumSize(new Dimension(100, 40));
        
        inAddress = new JTextField();
        inAddress.setMinimumSize(new Dimension(100, 40));
        inAddress.setMaximumSize(new Dimension(100, 40));
        
        inID = new JTextField();
        inID.setMinimumSize(new Dimension(100, 40));
        inID.setMaximumSize(new Dimension(100, 40));
        
        inUsername = new JTextField();
        inUsername.setMinimumSize(new Dimension(100, 40));
        inUsername.setMaximumSize(new Dimension(100, 40));
        
        inPw = new JPasswordField();
        inPw.setMinimumSize(new Dimension(100, 40));
        inPw.setMaximumSize(new Dimension(100, 40));
        
        buttonPressed = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent event){
                if (event.getSource() == backButton){
                    finished.notifyObservers("close");
                }
                if (event.getSource() == saveButton){
                    CustomerInfo customerInfo = new CustomerInfo(inName.getText(), inAddress.getText(), inID.getText(), inUsername.getText(), new String(inPw.getPassword()));
                    if(controller.addNewCustomer(customerInfo)){
                        JOptionPane.showMessageDialog(null, "Mentés sikeres.");  
                        finished.notifyObservers("close");

                        inName.setText("");
                        inAddress.setText("");
                        inID.setText("");
                        inUsername.setText("");
                        inPw.setText("");
                    }else{
                        JOptionPane.showMessageDialog(null, "Hibás adatok!");
                    }
                }
            }
        };
        
        saveButton = new JButton(buttonPressed);
        saveButton.setMinimumSize(new Dimension(100, 40));
        saveButton.setMaximumSize(new Dimension(100, 40));
        saveButton.setText("Mentés");
        
        backButton = new JButton(buttonPressed);
        backButton.setMinimumSize(new Dimension(100, 40));
        backButton.setMaximumSize(new Dimension(100, 40));
        backButton.setText("Vissza");
        
        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(6, 2));
        
        pan.add(new Label("Név:"));
        pan.add(inName);
        pan.add(new Label("Cím:"));
        pan.add(inAddress);
        pan.add(new Label("Személyi:"));
        pan.add(inID);
        pan.add(new Label("Felhasználó:"));
        pan.add(inUsername);
        pan.add(new Label("Jelszó:"));
        pan.add(inPw);
        pan.add(backButton);
        pan.add(saveButton);
        
        Container contentPane = getContentPane();
        contentPane.add(pan, BorderLayout.CENTER);
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
}
