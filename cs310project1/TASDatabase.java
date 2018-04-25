package cs310project1;

import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TASDatabase {
    
    private final String username = "tasbot";
    private final String password = "teamgroup5";
    private final String url = "jdbc:mysql://localhost/tas";
    private Connection conn;
  
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
        
        String badge_id = b.getBadge_id();
        try {
            //Get the shift id from the badge
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM employee WHERE badgeid = '" + badge_id + "'");
            if(result != null){
                result.next();
                int shift_id = Integer.parseInt(result.getString("shiftid"));
                return getShift(shift_id);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
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
                b = getBadge(badge_id);
                Punch p = new Punch(b, term_id, event_id);
                p.setEventdata(event_data);
                p.setOriginalTimeStamp(ts);
                p.setPunchid(punch_id);
                return p;
            }
            else
                return new Punch(b,0,0);
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
    
    
    //Inserts a punch object into the database and returns its id in the database
    public int insertPunch(Punch p){
        
        int key = 0, result = 0;
        String badgeid = p.getBadgeid();
        GregorianCalendar originaltimestamp = p.getOriginaltimestamp();
        int terminalid = p.getTerminalid();
        int eventtypeid = p.getPunchtypeid();
        ResultSet keys;
        String sql = "INSERT INTO event (badgeid, originaltimestamp, terminalid, eventtypeid) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, badgeid);
            ps.setString(2, (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(originaltimestamp.getTime()));
            ps.setInt(3, terminalid);
            ps.setInt(4, eventtypeid);
            result = ps.executeUpdate();
            if (result == 1) {
                keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    key = keys.getInt(1);
                }
            }   
        } catch (Exception e) {
            System.err.println(e);
        }
        return key;
    }
    
    //Returns all punches by a particular person on a particular day
    public ArrayList getDailyPunchList(Badge b, GregorianCalendar ts){
        
        ArrayList<Punch> punches = new ArrayList<>(); 
        String badge_id = b.getBadge_id();
        Date d = ts.getTime();
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT *, UNIX_TIMESTAMP(originaltimestamp) * 1000 AS 'timestamp' FROM event WHERE badgeid = '" + badge_id + "'");
            while(result != null && result.next()){      
                Date original_ts = new Date(Long.parseLong(result.getString("timestamp")));
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(d);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(original_ts);
                
                if(cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                   cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                   cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)){  
                    Shift s = getShift(b);
                    int punch_id = Integer.parseInt(result.getString("id"));
                    int term_id = Integer.parseInt(result.getString("terminalid"));
                    int event_id = Integer.parseInt(result.getString("eventtypeid"));
                    String eventdata = result.getString("eventdata");
                    Punch p = new Punch(new Badge(badge_id),term_id,event_id);
                    p.setPunchid(punch_id);
                    p.setEventdata(eventdata);
                    p.setOriginalTimeStamp(original_ts.getTime());
                    p.adjust(s);
                    punches.add(p);                    
                }
            }
            //Check for same # of punch ins and outs
            if(punches.size() % 2 != 0){
                //Get the first time stamp from the next day and add to punches
                ResultSet rs = stmt.executeQuery("SELECT *, UNIX_TIMESTAMP(originaltimestamp) * 1000 AS 'timestamp' FROM event WHERE badgeid = '" + badge_id + "'");
                boolean flag = false;
                while(rs != null && rs.next()){      
                    long new_ts = Long.parseLong(rs.getString("timestamp")) ;
                    long next_day = d.getTime() + (60 * 24 * 1000);
                    long original_day = d.getTime();
                    
                    if(new_ts < next_day && new_ts > original_day && !flag){     
                        int punch_id = Integer.parseInt(result.getString("id"));
                        int term_id = Integer.parseInt(rs.getString("terminalid"));
                        int event_id = Integer.parseInt(rs.getString("eventtypeid"));
                        Punch p = new Punch(new Badge(badge_id),term_id,event_id);
                        p.setEventdata(result.getString("eventdata"));
                        p.setPunchid(punch_id);
                        p.setOriginalTimeStamp(new_ts);
                        punches.add(p);               
                        flag = true;
                    }
                }                
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return punches;
    } 
}