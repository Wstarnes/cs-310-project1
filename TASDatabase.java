package feature1;

import java.sql.*;

public class TASDatabase {
    
    private final String username = "Tasbot";
    private final String password = "teamgroup5";
    private Connection conn;
    
    TASDatabase() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost/tas";
        conn = DriverManager.getConnection(url, username,password);
        
    }
    
    Badge getBadge(){
        return new Badge();
    }
    
    Shift getShift(){
        return new Shift();
    }
    
    Punch getPunch(int punch_id){
        return new Punch(new Badge(),0,0);
    }
    
}