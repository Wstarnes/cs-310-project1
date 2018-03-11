package cs310project1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Punch {
    
    private String badgeid;
    private int terminalid;
    private int punchtypeid;
    private int punchid;
    private GregorianCalendar originalTimeStamp;
    private GregorianCalendar adjustedTimeStamp;

    public Punch(Badge badge, int terminalid, int punchtypeid){
        this.badgeid = badge.getBadge_id();
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        originalTimeStamp = new GregorianCalendar();
        adjustedTimeStamp = new GregorianCalendar();
    }
    
    public Punch(String badge_id, int terminalid, int punchtypeid){
        this.badgeid = badge_id;
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        originalTimeStamp = new GregorianCalendar();
        adjustedTimeStamp = new GregorianCalendar();
    }

    public String getBadgeid() {
        return badgeid;
    }

    public void setBadgeid(String badgeid) {
        this.badgeid = badgeid;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(int terminalid) {
        this.terminalid = terminalid;
    }

    public int getPunchtypeid() {
        return punchtypeid;
    }

    public void setPunchtypeid(int punchtypeid) {
        this.punchtypeid = punchtypeid;
    }

    public int getPunchid() {
        return punchid;
    }

    public void setPunchid(int punchid) {
        this.punchid = punchid;
    }

    public GregorianCalendar getOriginalTimeStamp() {
        return originalTimeStamp;
    }

    public void setOriginalTimeStamp(long originalTimeStamp) {
        this.originalTimeStamp.setTimeInMillis(originalTimeStamp);
    }

    public GregorianCalendar getAdjustedTimeStamp() {
        return adjustedTimeStamp;
    }

    public void setAdjustedTimeStamp(GregorianCalendar adjustedTimeStamp) {
        this.adjustedTimeStamp = adjustedTimeStamp;
    }
    
    public String printOriginalTimestamp(){
        
        SimpleDateFormat fmt = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        fmt.setCalendar(originalTimeStamp);
        String stamp = fmt.format(originalTimeStamp.getTime()).toUpperCase();
        
        switch (punchtypeid){
            case 0:
                return "#" + badgeid + " CLOCKED OUT: " + stamp;
                
            case 1:
                return "#" + badgeid + " CLOCKED IN: " + stamp;
                 
            default:
                return "#" + badgeid + " TIMED OUT: " + stamp;
        }     
    }
    
}
