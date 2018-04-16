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
    private String eventdata = null;

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
        
<<<<<<< HEAD
        //set all calendar objects to millisecond
=======
        //Set all calanders to milliseconds
>>>>>>> Progress on Feat 3
        long originalTimeStampMillis = originalTimeStamp.getTimeInMillis();
        long shiftStartMillis = shiftStart.getTimeInMillis();
        long startDockMillis = startDock.getTimeInMillis();
        long startIntervalMillis = startInterval.getTimeInMillis();
        long shiftStopMillis = shiftStop.getTimeInMillis();
        long stopDockMillis = stopDock.getTimeInMillis();
        long lunchStartMillis = lunchStart.getTimeInMillis();
        long lunchStopMillis = lunchStop.getTimeInMillis();
        
<<<<<<< HEAD
        //in-punch for start of day
        if(punchtypeid == 1){
            
            //Within Start Interval and Start grace period
            if(originalTimeStampMillis >= (shiftStartMillis - (interval *60000)) && originalTimeStampMillis <= (shiftStartMillis + (gracePeriod * 60000))){
                adjustedTimeStamp.setTimeInMillis(shiftStartMillis);
                eventdata = "Shift Start";
            }
            
            //After grace period
            else if(originalTimeStampMillis > (shiftStartMillis + (gracePeriod * 60000))){
                adjustedTimeStamp.setTimeInMillis(originalTimeStampMillis + startDockMillis);
                eventdata = "Shift Dock";
            }
            
            //Before Start Interval
            else{
                //Placeholder for tonight
            }
        }
        
        else if(punchtypeid == 2){
            //Within Stop Interval and Stop Grace Period
            if(originalTimeStampMillis <= (shiftStopMillis + (interval * 60000)) && originalTimeStampMillis >= (shiftStopMillis - (gracePeriod * 60000))){
                adjustedTimeStamp.setTimeInMillis(shiftStopMillis);
                eventdata = "Shift Stop";
            }
            
            //Before the grace period
            else if(originalTimeStampMillis < (shiftStopMillis - (gracePeriod * 60000))){
                adjustedTimeStamp.setTimeInMillis(originalTimeStampMillis - stopDockMillis);
                eventdata = "Shift Dock";
            }
            
            //After Stop Interval
            else{
                //placeholder for tonight
            }
        }
        
        //Lunch out
        else if(punchtypeid == 3){
            
        }
        
        
        
        /*startDock.setTimeInMillis(shiftStart.getTimeInMillis());
        startDock.add(Calendar.MINUTE, gracePeriod);
        startInterval.setTimeInMillis(shiftStart.getTimeInMillis());
        startInterval.add(Calendar.MINUTE, -interval);
        stopDock.setTimeInMillis(shiftStop.getTimeInMillis());
        stopDock.add(Calendar.MINUTE, gracePeriod);
        
        
        
        if (punchtypeid == 1) {//Clocked in
            //Punch is late
            if (originalTimeStamp.getTimeInMillis() > startDock.getTimeInMillis()) {
                //Adjust time stamp forward to nearest interval
                adjustedTimeStamp = startInterval;
            }
            //Punch is early
            else if (originalTimeStamp.getTimeInMillis() < shiftStart.getTimeInMillis()) {
                //Adjust time stamp forward to shift start time
                adjustedTimeStamp = shiftStart;
=======
        
        //in-punch for start of day
        if (punchtypeid == 1) {
            //check in too early or before the upper grace period bound
            if(originalTimeStampMillis > (shiftStartMillis - interval * 60000) && originalTimeStampMillis < shiftStartMillis + (gracePeriod * 60000))
                adjustedTimeStamp.setTimeInMillis(shiftStartMillis);
            //check in too late
            else if(originalTimeStampMillis > shiftStartMillis + (gracePeriod * 60000))
                adjustedTimeStamp.setTimeInMillis(originalTimeStampMillis + (startDockMillis * 60000));
            //check in is passed grace period
            else if(originalTimeStampMillis > shiftStartMillis + (gracePeriod * 60000)){
                adjustedTimeStamp.setTimeInMillis(originalTimeStampMillis + (startDockMillis + startDockMillis));
            }
            else{
                //THIS IS A PLACEHOLDER! I CAN'T THINK RIGHT NOW HOW TO DO THIS
            }
        }
        else if(punchtypeid == 2){
            //Check out is within grace period and interval
            if(originalTimeStampMillis > shiftStopMillis - (gracePeriod * 60000) && originalTimeStampMillis < shiftStopMillis + (interval * 60000)){
                adjustedTimeStamp.setTimeInMillis(shiftStopMillis);
>>>>>>> Progress on Feat 3
            }
            //check out is before grace period
            else if(originalTimeStampMillis < shiftStopMillis - (gracePeriod * 60000))
                adjustedTimeStamp.setTimeInMillis(originalTimeStampMillis -(stopDockMillis * 60000));
        }
        
        else if(punchtypeid == 0){//Clocked out
            //Punch is late
            if(originalTimeStamp.getTimeInMillis() > stopDock.getTimeInMillis()){
                
                
            }
            //Punch is early
            else if(originalTimeStamp.getTimeInMillis() < shiftStop.getTimeInMillis()){
                
                adjustedTimeStamp = shiftStop;
            }
        }*/
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
    
    public String printAdjustedTimestamp(){
        //Incomplete
        SimpleDateFormat fmt = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        fmt.setCalendar(adjustedTimeStamp);
        String stamp = fmt.format(adjustedTimeStamp.getTime()).toUpperCase();
        
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