package cs310project1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public void adjust(Shift s) {
        GregorianCalendar shiftStart = s.getStart();
        GregorianCalendar startDock = new GregorianCalendar();
        GregorianCalendar startInterval = new GregorianCalendar();
        GregorianCalendar shiftStop = s.getStop();
        GregorianCalendar stopDock = new GregorianCalendar();
        GregorianCalendar lunchStart = s.getLunch_start();
        GregorianCalendar lunchStop = s.getLunch_stop();
        int interval = s.getInterval();
        int gracePeriod = s.getGrace_period();
        startDock.setTimeInMillis(shiftStart.getTimeInMillis());
        startDock.add(Calendar.MINUTE, gracePeriod);
        startInterval.setTimeInMillis(shiftStart.getTimeInMillis());
        startInterval.add(Calendar.MINUTE, -interval);
        stopDock.setTimeInMillis(shiftStop.getTimeInMillis());
        stopDock.add(Calendar.MINUTE, gracePeriod);
        if (punchtypeid == 1) {
            // Punch is late
            if (originalTimeStamp.getTimeInMillis() > startDock.getTimeInMillis()) {
                // TODO: Adjust time stamp forward to nearest interval
            }

            // Punch is early
            else if (originalTimeStamp.getTimeInMillis() < shiftStart.getTimeInMillis()) {
                // TODO: Adjust time stamp forward to shift start time
            }
        }
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