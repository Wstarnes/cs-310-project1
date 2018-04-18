package cs310project1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Punch {
    
    private String badgeid;
    private int terminalid;
    private int punchtypeid;
    private int punchid;
    private GregorianCalendar originalTimeStamp;
    private GregorianCalendar adjustedTimeStamp;
    private String eventdata;

    public Punch(Badge badge, int terminalid, int punchtypeid){
        this.badgeid = badge.getBadge_id();
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        this.originalTimeStamp = new GregorianCalendar();
        this.adjustedTimeStamp = new GregorianCalendar();
        this.eventdata = "";
        
    }
    
    public Punch(String badge_id, int terminalid, int punchtypeid){
        this.badgeid = badge_id;
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        this.originalTimeStamp = new GregorianCalendar();
        this.adjustedTimeStamp = new GregorianCalendar();
        this.eventdata = "";
    }

    public void adjust(Shift s) {
        GregorianCalendar shiftStart = s.getStart();
        GregorianCalendar startDock = new GregorianCalendar();
        GregorianCalendar startInterval = new GregorianCalendar();
        GregorianCalendar startGrace = new GregorianCalendar();
        GregorianCalendar shiftStop = s.getStop();
        GregorianCalendar stopDock = new GregorianCalendar();
        GregorianCalendar stopInterval = new GregorianCalendar();
        GregorianCalendar stopGrace = new GregorianCalendar();
        GregorianCalendar lunchStart = s.getLunch_start();
        GregorianCalendar lunchStop = s.getLunch_stop();
        int interval = s.getInterval();
        int gracePeriod = s.getGrace_period();
        
        //Set all calanders to milliseconds
        long originalTimeStampMillis = originalTimeStamp.getTimeInMillis();
        shiftStart.setTimeInMillis(originalTimeStampMillis);
        shiftStart.set(Calendar.HOUR, s.getStart().get(Calendar.HOUR));
        shiftStart.set(Calendar.MINUTE, s.getStart().get(Calendar.MINUTE));
        shiftStart.set(Calendar.SECOND, 0);
        long shiftStartMillis = shiftStart.getTimeInMillis();

        startDock.setTimeInMillis(shiftStartMillis);
        startDock.add(Calendar.MINUTE, s.getDock());
        long startDockMillis = startDock.getTimeInMillis();

        startInterval.setTimeInMillis(shiftStartMillis);
        startInterval.add(Calendar.MINUTE, -s.getInterval());
        long startIntervalMillis = startInterval.getTimeInMillis();

        startGrace.setTimeInMillis(shiftStartMillis);
        startGrace.add(Calendar.MINUTE, s.getGrace_period());
        long startGraceMillis = startGrace.getTimeInMillis();

        shiftStop.setTimeInMillis(originalTimeStampMillis);
        shiftStop.set(Calendar.HOUR, s.getStop().get(Calendar.HOUR));
        shiftStop.set(Calendar.MINUTE, s.getStop().get(Calendar.MINUTE));
        shiftStop.set(Calendar.SECOND, 0);
        long shiftStopMillis = shiftStop.getTimeInMillis();

        stopDock.setTimeInMillis(shiftStopMillis);
        stopDock.add(Calendar.MINUTE, s.getDock());
        long stopDockMillis = stopDock.getTimeInMillis();

        stopInterval.setTimeInMillis(shiftStopMillis);
        stopInterval.add(Calendar.MINUTE, -s.getInterval());
        long stopIntervalMillis = stopInterval.getTimeInMillis();

        stopGrace.setTimeInMillis(shiftStopMillis);
        stopGrace.add(Calendar.MINUTE, s.getGrace_period());
        long stopGraceMillis = stopGrace.getTimeInMillis();

        long lunchStartMillis = lunchStart.getTimeInMillis();
        long lunchStopMillis = lunchStop.getTimeInMillis();
        
         
        //check-in punches
        if (punchtypeid == 1) {
            //check in too early or before the upper grace period bound
            if(originalTimeStampMillis >= startIntervalMillis && originalTimeStampMillis <= shiftStartMillis){
                adjustedTimeStamp.setTimeInMillis(shiftStartMillis);
                eventdata = "Shift Start";
            }
            
            //check in too late
            else if(originalTimeStampMillis > shiftStartMillis + (gracePeriod * 60000)){
                adjustedTimeStamp.setTimeInMillis(originalTimeStampMillis + (startDockMillis * 60000));
                eventdata = "Shift Dock";
            }
            
            //check in is passed grace period
            else if(originalTimeStampMillis > shiftStartMillis + (gracePeriod * 60000)){
                adjustedTimeStamp.setTimeInMillis(originalTimeStampMillis + (startDockMillis + startDockMillis));
                eventdata = "Dock";
            }
            
            //end of lunch
            else if(originalTimeStampMillis > lunchStartMillis && originalTimeStampMillis <= lunchStopMillis){
                adjustedTimeStamp.setTimeInMillis(lunchStopMillis);
                eventdata = "Lunch Stop";
            }
        }
        
        //check-out punches
        else if(punchtypeid == 2){
            //Check out is within grace period and interval
            if(originalTimeStampMillis > shiftStopMillis - (gracePeriod * 60000) && originalTimeStampMillis < shiftStopMillis + (interval * 60000)){
                adjustedTimeStamp.setTimeInMillis(shiftStopMillis);
                eventdata = "Shift Stop";
            }
            //check out is before grace period
            else if(originalTimeStampMillis < shiftStopMillis - (gracePeriod * 60000)){
                adjustedTimeStamp.setTimeInMillis(originalTimeStampMillis -(stopDockMillis * 60000));
                eventdata = "Shift Dock";
            }
            
            //start of lunch
            else if(originalTimeStampMillis >= lunchStartMillis && originalTimeStampMillis < lunchStopMillis){
                adjustedTimeStamp.setTimeInMillis(lunchStartMillis);
                eventdata = "Lunch Start";
            }
        }
    }

    public String getEventdata(){
        return eventdata;
    }
    
    public void setEventdata(String eventdata){
        this.eventdata = eventdata;
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

    public GregorianCalendar getOriginaltimestamp() {
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
    
    public String printAdjustedTimestamp(){
        
        SimpleDateFormat fmt = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        fmt.setCalendar(adjustedTimeStamp);
        String stamp = fmt.format(adjustedTimeStamp.getTime()).toUpperCase();
        
        switch (punchtypeid){
            case 0:
                return "#" + badgeid + " CLOCKED OUT: " + stamp + " (" + eventdata + ")";
                
            case 1:
                return "#" + badgeid + " CLOCKED IN: " + stamp + " (" + eventdata + ")";
                 
            default:
                return "#" + badgeid + " TIMED OUT: " + stamp + " (" + eventdata + ")";
        }         
        
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