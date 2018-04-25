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
        
        GregorianCalendar shiftStart = new GregorianCalendar();
        GregorianCalendar startDock = new GregorianCalendar();
        GregorianCalendar startInterval = new GregorianCalendar();
        GregorianCalendar startGrace = new GregorianCalendar();
        GregorianCalendar shiftStop = new GregorianCalendar();
        GregorianCalendar stopDock = new GregorianCalendar();
        GregorianCalendar stopInterval = new GregorianCalendar();
        GregorianCalendar stopGrace = new GregorianCalendar();
        GregorianCalendar lunchStart = new GregorianCalendar();
        GregorianCalendar lunchStop = new GregorianCalendar();
        
        //Set all calanders to milliseconds
        long originalTimeStampMillis = originalTimeStamp.getTimeInMillis();
        
        adjustedTimeStamp.setTimeInMillis(originalTimeStampMillis);
        
        shiftStart.setTimeInMillis(originalTimeStampMillis);
        shiftStart.set(Calendar.HOUR_OF_DAY, s.getStart().get(Calendar.HOUR_OF_DAY));
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
        shiftStop.set(Calendar.HOUR_OF_DAY, s.getStop().get(Calendar.HOUR_OF_DAY));
        shiftStop.set(Calendar.MINUTE, s.getStop().get(Calendar.MINUTE));
        shiftStop.set(Calendar.SECOND, 0);
        long shiftStopMillis = shiftStop.getTimeInMillis();

        stopDock.setTimeInMillis(shiftStopMillis);
        stopDock.add(Calendar.MINUTE, -s.getDock());
        long stopDockMillis = stopDock.getTimeInMillis();

        stopInterval.setTimeInMillis(shiftStopMillis);
        stopInterval.add(Calendar.MINUTE, s.getInterval());
        long stopIntervalMillis = stopInterval.getTimeInMillis();

        stopGrace.setTimeInMillis(shiftStopMillis);
        stopGrace.add(Calendar.MINUTE, -s.getGrace_period());
        long stopGraceMillis = stopGrace.getTimeInMillis();
        
        lunchStart.setTimeInMillis(originalTimeStampMillis);
        lunchStart.set(Calendar.HOUR_OF_DAY, s.getLunch_start().get(Calendar.HOUR_OF_DAY));
        lunchStart.set(Calendar.MINUTE, s.getLunch_start().get(Calendar.MINUTE));
        lunchStart.set(Calendar.SECOND, 0);
        long lunchStartMillis = lunchStart.getTimeInMillis();

        lunchStop.setTimeInMillis(originalTimeStampMillis);
        lunchStop.set(Calendar.HOUR_OF_DAY, s.getLunch_stop().get(Calendar.HOUR_OF_DAY));
        lunchStop.set(Calendar.MINUTE, s.getLunch_stop().get(Calendar.MINUTE));
        lunchStop.set(Calendar.SECOND, 0);        
        long lunchStopMillis = lunchStop.getTimeInMillis();
        
        int interval = s.getInterval();

        
        //On a weekend
        if(shiftStart.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || shiftStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            //Check-in
            if(punchtypeid == 1){
                if(originalTimeStampMillis >= startIntervalMillis && originalTimeStampMillis <= shiftStartMillis + (s.getInterval() * 60000) ){
                    eventdata = "None";
                }
                else{
                    if(originalTimeStamp.get(Calendar.MINUTE) % interval <= interval / 2){
                        adjustedTimeStamp.set(Calendar.MINUTE, originalTimeStamp.get(Calendar.MINUTE) - (originalTimeStamp.get(Calendar.MINUTE) % interval));
                        adjustedTimeStamp.set(Calendar.SECOND, 0);
                    }
                    else{
                        adjustedTimeStamp.set(Calendar.MINUTE, originalTimeStamp.get(Calendar.MINUTE) + (interval - (originalTimeStamp.get(Calendar.MINUTE) % interval)));
                        adjustedTimeStamp.set(Calendar.SECOND, 0);
                    }
                    eventdata = "Interval Round";
                }
            }
            else if(punchtypeid == 0){
                if(originalTimeStampMillis <= stopIntervalMillis && originalTimeStampMillis >= shiftStopMillis + (s.getInterval() * 60000)){
                    eventdata = "None";
                }
                else{
                    if(originalTimeStamp.get(Calendar.MINUTE) % interval >= interval / 2){
                        adjustedTimeStamp.set(Calendar.MINUTE, originalTimeStamp.get(Calendar.MINUTE) + (interval - (originalTimeStamp.get(Calendar.MINUTE) % interval)));
                        adjustedTimeStamp.set(Calendar.SECOND, 0);
                    }
                    else{
                        adjustedTimeStamp.set(Calendar.MINUTE, originalTimeStamp.get(Calendar.MINUTE) - (originalTimeStamp.get(Calendar.MINUTE) % interval));
                        adjustedTimeStamp.set(Calendar.SECOND, 0);
                    }
                    eventdata = "Interval Round";
                }
            }        
        }
        
        else{
            //check-in punches
            if (punchtypeid == 1) {
                if(originalTimeStampMillis <= shiftStartMillis && originalTimeStampMillis >= startIntervalMillis){
                    adjustedTimeStamp.setTimeInMillis(shiftStartMillis);
                    eventdata = "Shift Start";
                }
                else if(originalTimeStampMillis >= shiftStartMillis && originalTimeStampMillis <= startGraceMillis){
                    adjustedTimeStamp.setTimeInMillis(shiftStartMillis);
                    eventdata = "Shift Start";
                }
                else if(originalTimeStampMillis >= lunchStartMillis && originalTimeStampMillis <= lunchStopMillis){
                    adjustedTimeStamp.setTimeInMillis(lunchStopMillis);
                    eventdata = "Lunch Stop";
                }
                else if(originalTimeStampMillis > startGraceMillis && originalTimeStamp.get(Calendar.MINUTE) % interval > interval/2){
                    adjustedTimeStamp.setTimeInMillis(startDockMillis);
                    eventdata = "Shift Dock";
                }   
                else if(originalTimeStamp.get(Calendar.HOUR_OF_DAY) == shiftStart.get(Calendar.HOUR_OF_DAY) + 1 && originalTimeStamp.get(Calendar.MINUTE) == shiftStart.get(Calendar.MINUTE)){
                    eventdata = "None";
                }
                else{
                    if(originalTimeStamp.get(Calendar.MINUTE) % interval <= interval / 2){
                        adjustedTimeStamp.set(Calendar.MINUTE, originalTimeStamp.get(Calendar.MINUTE) - (originalTimeStamp.get(Calendar.MINUTE) % interval));
                        adjustedTimeStamp.set(Calendar.SECOND, 0);
                    }
                    else{
                        adjustedTimeStamp.set(Calendar.MINUTE, originalTimeStamp.get(Calendar.MINUTE) + (originalTimeStamp.get(Calendar.MINUTE) % interval));
                        adjustedTimeStamp.set(Calendar.SECOND, 0);
                    }
                    eventdata = "Interval Round";
                }
            }
            //check-out punches
            else if(punchtypeid == 0){
                //Check out is within grace period and interval
                if(originalTimeStampMillis >= stopGraceMillis && originalTimeStampMillis <= shiftStopMillis){
                    adjustedTimeStamp.setTimeInMillis(shiftStopMillis);
                    eventdata = "Shift Stop";
                }
                else if(originalTimeStampMillis <= stopIntervalMillis && originalTimeStampMillis >= shiftStopMillis){
                    adjustedTimeStamp.setTimeInMillis(shiftStopMillis);
                    eventdata = "Shift Stop";
                }
                //start of lunch
                else if(originalTimeStampMillis >= lunchStartMillis && originalTimeStampMillis < lunchStopMillis){
                    adjustedTimeStamp.setTimeInMillis(lunchStartMillis);
                    eventdata = "Lunch Start";
                }
                //check out is before grace period
                else if(originalTimeStampMillis < stopGraceMillis && originalTimeStamp.get(Calendar.MINUTE) % interval < interval/2){
                    adjustedTimeStamp.setTimeInMillis(stopDockMillis);
                    eventdata = "Shift Dock";
                } 
                else if(originalTimeStamp.get(Calendar.HOUR_OF_DAY) == shiftStop.get(Calendar.HOUR_OF_DAY) + 1 && originalTimeStamp.get(Calendar.MINUTE) == shiftStop.get(Calendar.MINUTE)){
                    eventdata = "None";
                }
                else{
                    if(originalTimeStamp.get(Calendar.MINUTE) % interval >= interval / 2){
                        adjustedTimeStamp.set(Calendar.MINUTE, originalTimeStamp.get(Calendar.MINUTE) + (originalTimeStamp.get(Calendar.MINUTE) % interval) + 1);
                        adjustedTimeStamp.set(Calendar.SECOND, 0);
                    }
                    else{
                        adjustedTimeStamp.set(Calendar.MINUTE, originalTimeStamp.get(Calendar.MINUTE) - (originalTimeStamp.get(Calendar.MINUTE) % interval));
                        adjustedTimeStamp.set(Calendar.SECOND, 0);
                    }
                    eventdata = "Interval Round";
                }
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