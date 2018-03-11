package cs310project1;

import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class TASDatabase {
    
    private final String username = "Tasbot";
    private final String password = "teamgroup5";
    private final String url = "jdbc:mysql://localhost/tas";
    private Connection conn;

    /*
    TODO:
    Finish getPunch()
    */
    
    public TASDatabase(){ 
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, username,password);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void close(){
        try {
            conn.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    
    public Badge getBadge(String badge_id){
        
        Badge b = new Badge();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM badge WHERE id = '" + badge_id + "'");

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
        } catch (Exception e) {
            System.err.println(e);
        }
        return b;
    }
    
    public Shift getShift(int shift_id){
        
        Shift s = new Shift();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM shift WHERE id=" + shift_id);
            if(result != null){
                result.next();
                String desc = result.getString("description");
                Date start = sdf.parse(result.getString("start"));
                Date stop = sdf.parse(result.getString("stop"));
                int interval = Integer.parseInt(result.getString("interval"));
                int grace_period = Integer.parseInt(result.getString("graceperiod"));
                int dock = Integer.parseInt(result.getString("dock"));
                Date lunch_start = sdf.parse(result.getString("lunchstart"));
                Date lunch_stop = sdf.parse(result.getString("lunchstop"));
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
        } catch (Exception e) {
            System.err.println(e);
        }
        return s;
    }
    
    public Shift getShift(Badge b){
        return new Shift();
    }
    
    public Punch getPunch(int punch_id){
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT *, UNIX_TIMESTAMP(originaltimestamp) * 1000 AS 'timestamp' FROM event WHERE id = '" + punch_id + "'");
            Badge b = new Badge();
            
            if(result != null){
                result.next();
                int term_id = Integer.parseInt(result.getString("terminalid"));
                int event_id = Integer.parseInt(result.getString("eventtypeid"));
                String badge_id = result.getString("badgeid");
                long ts = Long.parseLong(result.getString("timestamp"));
                String event_data = result.getString("eventdata");
                //String adjusted_time_stamp = result.getString("adjustedtimestamp");
                b = getBadge(badge_id);
                Punch p = new Punch(b, term_id, event_id);
                p.setOriginalTimeStamp(ts);
                return p;
            }
            else
                return new Punch(b,0,0);
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
}