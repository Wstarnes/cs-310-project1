package cs310project1;

import java.sql.*;

public class TASDatabase {
    
    private final String username = "Tasbot";
    private final String password = "teamgroup5";
    private final Connection conn;
    
    /*
    TODO:
    Finish getPunch()
    */
    
    public TASDatabase() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost/tas";
        conn = DriverManager.getConnection(url, username,password);
        
    }
    
    public void close() throws SQLException{
        conn.close();
    }
    
    
    public Badge getBadge(String badge_id) throws SQLException{
        
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM badge WHERE id = '" + badge_id + "'");
        Badge b = new Badge();
        if(result != null){
            result.next();
            String id = result.getString("id");
            String desc = result.getString("description");
            b.setBadge_id(id);
            String[] desc_components = desc.split(" ");
            b.setLast_name(desc_components[0].substring(0,desc_components[0].length() - 1));
            b.setFirst_name(desc_components[1]);
            b.setMiddle_name(desc_components[2]);
       } 
        return b;
    }
    
    public Shift getShift(int shift_id) throws SQLException{
        
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM shift WHERE id=" + shift_id);
        Shift s = new Shift();
        if(result != null){
            result.next();
            String desc = result.getString("description");
            String start = result.getString("start");
            String stop = result.getString("stop");
            String interval = result.getString("interval");
            String grace_period = result.getString("graceperiod");
            int dock = Integer.parseInt(result.getString("dock"));
            String lunch_start = result.getString("lunchstart");
            String lunch_stop = result.getString("lunchstop");
            int lunch_deduct = Integer.parseInt(result.getString("lunchdeduct"));
            
            s.setDescription(desc);
            s.setStart(start);
            s.setStop(stop);
            s.setInterval(interval);
            s.setGrace_period(grace_period);
            s.setDock(dock);
            s.setLunch_start(lunch_start);
            s.setLunch_stop(lunch_stop);
            s.setLunch_deduct(lunch_deduct);
        }  
        return s;   
    }
    
    public Shift getShift(Badge b){
        return new Shift();
    }
    
    public Punch getPunch(int punch_id) throws SQLException{
        
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM event WHERE id=" + punch_id);
        
        if(result != null){
            result.next();
            
        }
        return new Punch(new Badge(),0,0);
        
    }
    
}