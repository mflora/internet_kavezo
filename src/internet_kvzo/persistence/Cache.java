package internet_kvzo.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


public class Cache implements ICache{
    private List<CustomerInfo> customer;
    private List<ComputerInfo> computer;
    private List<UsageInfo> usage;
    private List<BillInfo> bill;
    private Connection connection;
    
    public Cache(String serverName, int portNumber, String username, String password) throws SQLException{
        connection = null;
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", username);
        connectionProperties.put("password", password);
        connection = DriverManager.getConnection("jdbc:derby://"+serverName+":"+portNumber+"/internet_kvzo", connectionProperties);
        
        customer = new LinkedList<CustomerInfo>();
        computer = new LinkedList<ComputerInfo>();
        usage = new LinkedList<UsageInfo>();
        bill = new LinkedList<BillInfo>();
        
        update();
    }
    
    @Override
    public void update(){
        try{
            String query;
            Statement stmt = null;
            ResultSet rs;
            
            query = "SELECT * FROM customers";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String id = rs.getString("id");
                String username = rs.getString("username");
                String pw = rs.getString("pw");
                
                customer.add(new CustomerInfo(name, address, id, username, pw));
            }
            
            query = "SELECT * FROM computers";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String hw = rs.getString("hw");
                String os = rs.getString("os");
                Boolean inUse = rs.getBoolean("inuse");
                
                computer.add(new ComputerInfo(id, hw, os, inUse));
            }
            
            query = "SELECT * FROM usages";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("compid");
                String username = rs.getString("username");
                Date in = rs.getDate("indate");
                Date out = rs.getDate("outdate");
                
                usage.add(new UsageInfo(username, id, in, out));
            }
            
            query = "SELECT * FROM bills";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                int price = rs.getInt("price");
                
                bill.add(new BillInfo(username, price));
            }
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void insert(CustomerInfo customerInfo){
        customer.add(customerInfo);
        
        try{
            PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO customers " +
                "VALUES (?, ?, ?, ?, ?)");
                
            pstmt.setString( 1, customerInfo.getName() );
            pstmt.setString( 2, customerInfo.getAddress() );
            pstmt.setString( 3, customerInfo.getId() );
            pstmt.setString( 4, customerInfo.getUsername() );
            pstmt.setString( 5, customerInfo.getPw() );
                
            pstmt.executeUpdate();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modify(CustomerInfo customerInfo){        
        try{
            PreparedStatement pstmt = connection.prepareStatement(
                "UPDATE customers " +
                "SET name=?, address=?, id=?, pw=?" +
                "WHERE username=?");
                
            pstmt.setString( 1, customerInfo.getName() );
            pstmt.setString( 2, customerInfo.getAddress() );
            pstmt.setString( 3, customerInfo.getId() );
            pstmt.setString( 4, customerInfo.getPw() );
            pstmt.setString( 5, customerInfo.getUsername() );
                
            pstmt.executeUpdate();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void addPay(String name, int price){
	bill.add(new BillInfo(name, price));
        
        try{
            PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO bills " +
                "VALUES (?, ?)");
                
            pstmt.setString( 1, name );
            pstmt.setInt( 2, price );
                
            pstmt.executeUpdate();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void addUsage(String name, int compID, Date in, Date out){
        if (out == null){
            usage.add(new UsageInfo(name, compID, in, out));

            try{
                PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO usages ( username, compid, indate ) " +
                    " values (?, ?, ?)");
                
                pstmt.setString( 1, name );
                pstmt.setInt( 2, compID );
                pstmt.setDate( 3, new java.sql.Date(in.getTime()) ); 
                
                pstmt.executeUpdate();
                
                
                pstmt = connection.prepareStatement(
                    "UPDATE computers SET inuse=?" +
                    "WHERE id=?");
                
                pstmt.setBoolean(1, true);
                pstmt.setInt(2, compID);
                
                pstmt.executeUpdate();
                
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }else{
            try{
                PreparedStatement pstmt = connection.prepareStatement(
                    "UPDATE usages SET outdate=?" +
                    "WHERE compid=? AND username=? AND indate=? AND outdate IS NULL");
                
                pstmt.setDate( 1, new java.sql.Date(out.getTime()) ); 
                pstmt.setString( 3, name );
                pstmt.setInt( 2, compID );
                pstmt.setDate( 4, new java.sql.Date(in.getTime()) ); 
                
                pstmt.executeUpdate();
                
                
                pstmt = connection.prepareStatement(
                    "UPDATE computers SET inuse=?" +
                    "WHERE id=?");
                
                pstmt.setBoolean(1, false);
                pstmt.setInt(2, compID);
                
                pstmt.executeUpdate();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<CustomerInfo> getCustomer() {
        return customer;
    }

    @Override
    public List<UsageInfo> getUsage() {
        return usage;
    }

    @Override
    public List<ComputerInfo> getComputer() {
        return computer;
    }   
    
    @Override
    public List<BillInfo> getBill() {
        return bill;
    } 
    
}
