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
    private String eventdata;

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
        
        //Set all calanders to milliseconds
        long originalTimeStampMillis = originalTimeStamp.getTimeInMillis();
        long shiftStartMillis = shiftStart.getTimeInMillis();
        long startDockMillis = startDock.getTimeInMillis();
        long startIntervalMillis = startInterval.getTimeInMillis();
        long shiftStopMillis = shiftStop.getTimeInMillis();
        long stopDockMillis = stopDock.getTimeInMillis();
        long lunchStartMillis = lunchStart.getTimeInMillis();
        long lunchStopMillis = lunchStop.getTimeInMillis();
        
         
        //check-in punches
        if (punchtypeid == 1) {
            //check in too early or before the upper grace period bound
            if(originalTimeStampMillis > (shiftStartMillis - interval * 60000) && originalTimeStampMillis < shiftStartMillis + (gracePeriod * 60000)){
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
            
            else{
                //Minute is between 0 and 8
                if(originalTimeStamp.MINUTE >= 0 && originalTimeStamp.MINUTE < 8){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 0);
                    eventdata = "Interval Round";
                }
                
                //Minute is between 8 and 23 so rounds to 15
                else if(originalTimeStamp.MINUTE >= 8 && originalTimeStamp.MINUTE < 23){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 15);
                    eventdata = "Interval Round";
                }
                
                //Minute is between 24 and 37
                else if(originalTimeStamp.MINUTE >= 24 && originalTimeStamp.MINUTE < 37){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 30);
                    eventdata = "Interval Round";
                }
                
                //Minute is between 38 and 52
                else if(originalTimeStamp.MINUTE >= 38 && originalTimeStamp.MINUTE < 52){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 45);
                    eventdata = "Interval Round";
                }
                
                //Minute is after 52 and needs to round to the next hour
                else if(originalTimeStamp.MINUTE >= 53){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 0);
                    originalTimeStamp.add(originalTimeStamp.HOUR, 1);
                    eventdata = "Interval Round";
                }
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
            
            //Check ouy is after the interval
            else{
                //Minute is between 0 and 8
                if(originalTimeStamp.MINUTE >= 0 && originalTimeStamp.MINUTE < 8){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 0);
                    eventdata = "Interval Round";
                }
                
                //Minute is between 8 and 23 so rounds to 15
                else if(originalTimeStamp.MINUTE >= 8 && originalTimeStamp.MINUTE < 23){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 15);
                    eventdata = "Interval Round";
                }
                
                //Minute is between 24 and 37
                else if(originalTimeStamp.MINUTE >= 24 && originalTimeStamp.MINUTE < 37){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 30);
                    eventdata = "Interval Round";
                }
                
                //Minute is between 38 and 52
                else if(originalTimeStamp.MINUTE >= 38 && originalTimeStamp.MINUTE < 52){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 45);
                    eventdata = "Interval Round";
                }
                
                //Minute is after 52 and needs to round to the next hour
                else if(originalTimeStamp.MINUTE >= 53){
                    originalTimeStamp.set(originalTimeStamp.MINUTE, 0);
                    originalTimeStamp.add(originalTimeStamp.HOUR, 1);
                    eventdata = "Interval Round";
                }
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