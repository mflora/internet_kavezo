package internet_kvzo;


import internet_kvzo.controller.Controller;
import internet_kvzo.model.*;
import internet_kvzo.persistence.Cache;
import internet_kvzo.view.*;

import java.awt.Dimension;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;


public class Internet_Kvzo {

    private static Observer registerViewFinished;
    private static Observer modifyViewFinished;
    private static Observer customerViewFinsihed;
    private static Observer mainViewFinished;
    private static Observer computerViewFinsihed;
    private static Observer billViewFinsihed;
    private static Observer actionViewFinished;
    
    private static RegisterView registerView;
    private static ModifyView modifyView;
    private static CustomerListView customerView;
    private static ComputerListView computerView;
    private static BillListView billView;
    private static MainView mainView;
    private static ActionView actionView;
    
    private static Controller controller;
    private static Cache cache;

    public static void main(String[] args) {
        try{
            cache = new Cache("localhost", 1527, "root", "root");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Az adatbázis nem elérhető!\n" + ex.getMessage());
            System.exit(1);
        }
        controller = new Controller(cache);
        
        
        mainViewFinished = new Observer(){
            @Override
            public void update(Observable o, Object arg){
                String msg = (String)arg;
                if(msg.equals("new")){
                    mainView.hide();
                    registerView.show();
                    registerView.setLocationRelativeTo(null); 
                }
                if(msg.equals("modify")){
                    mainView.hide();
                    modifyView.show();
                    modifyView.setLocationRelativeTo(null); 
                }
                if(msg.equals("customers")){
                    mainView.hide();
                    customerView.setTableModel(new CustomerTableModel(cache.getCustomer()));
                    customerView.show();
                    customerView.setLocationRelativeTo(null);
                }
                if(msg.equals("computers")){
                    mainView.hide();
                    computerView.setTableModel(new ComputerTableModel(cache.getComputer()));
                    computerView.show();
                    computerView.setLocationRelativeTo(null);
                }
                if(msg.equals("bills")){
                    mainView.hide();
                    billView.setTableModel(new BillTableModel(cache.getBill()));
                    billView.show();
                    billView.setLocationRelativeTo(null);
                }
                if(msg.equals("action")){
                    mainView.hide();
                    actionView.show();
                    actionView.setLocationRelativeTo(null);
                }
            }
        };
        mainView = new MainView(controller);
        mainView.setSize(new Dimension(300, 300));
        mainView.finished.addObserver(mainViewFinished);
        
        
        registerViewFinished = new Observer(){
            @Override
            public void update(Observable o, Object arg){
                registerView.hide();
                mainView.show();
                mainView.setLocationRelativeTo(null); 
            }
        };
        registerView = new RegisterView(controller);
        registerView.setSize(new Dimension(300, 300));
        registerView.finished.addObserver(registerViewFinished);
        
        
        modifyViewFinished = new Observer(){
            @Override
            public void update(Observable o, Object arg){
                modifyView.hide();
                mainView.show();
                mainView.setLocationRelativeTo(null); 
            }
        };
        modifyView = new ModifyView(controller);
        modifyView.setSize(new Dimension(300, 300));
        modifyView.finished.addObserver(modifyViewFinished);
        
        
        actionViewFinished = new Observer(){
            @Override
            public void update(Observable o, Object arg){
                actionView.hide();
                mainView.show();
                mainView.setLocationRelativeTo(null); 
            }
        };
        actionView = new ActionView(controller);
        actionView.setSize(new Dimension(300, 300));
        actionView.finished.addObserver(actionViewFinished);
        
        
        customerViewFinsihed = new Observer(){
            @Override
            public void update(Observable o, Object arg){
                customerView.hide();
                mainView.show();
                mainView.setLocationRelativeTo(null);
            }
        };
        customerView = new CustomerListView(controller);
        customerView.setSize(new Dimension(300, 300));
        customerView.finished.addObserver(customerViewFinsihed);
        
        
        computerViewFinsihed = new Observer(){
            @Override
            public void update(Observable o, Object arg){
                computerView.hide();
                mainView.show();
                mainView.setLocationRelativeTo(null);
            }
        };
        computerView = new ComputerListView(controller);
        computerView.setSize(new Dimension(300, 300));
        computerView.finished.addObserver(computerViewFinsihed);
        
        
        billViewFinsihed = new Observer(){
            @Override
            public void update(Observable o, Object arg){
                billView.hide();
                mainView.show();
                mainView.setLocationRelativeTo(null);
            }
        };
        billView = new BillListView(controller);
        billView.setSize(new Dimension(300, 300));
        billView.finished.addObserver(billViewFinsihed);
        
        
        mainView.show();
        mainView.setLocationRelativeTo(null); 
    }
    
}
