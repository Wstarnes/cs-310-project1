package cs310project1;

import java.util.GregorianCalendar;

public class Punch {
    private String badgeid;
    private int terminalid;
    private int punchtypeid;
    private int punchid;
    private GregorianCalendar originalTimeStmap;
    private GregorianCalendar adjustedTimeStamp;

    SimpleDateFormat fmt = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
    fmt.setCalendar(originalTimeStmap);

    private String stamp = fmt.format(originalTimeStmap.getTime());

    public Punch(Badge badge, int terminalid, int punchtypeid){
        this.badgeid = badge.getBadge_id();
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        originalTimeStamp = new GregorianCalendar();
        adjustedTimeStamp = null;
    }

    public void setBadgeid(Badge badge) {
        this.badge = badge.getBadge_id();
    }

    public String getBadgeid() {
        return badgeid;
    }

    public int getPunchid() {
        return punchid;
    }

    public void setPunchid(int punchid) {
        this.punchid = punchid;
    }

    public int getPunchtypeid() {
        return punchtypeid;
    }

    public void setPunchtypeid(int punchtypeid) {
        this.punchtypeid = punchtypeid;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(int terminalid) {
        this.terminalid = terminalid;
    }

    public GregorianCalendar getOriginalTimeStamp() {
        return originalTimeStamp;

    }
    public void setOriginalTimeStamp(GregorianCalendar stamp) {
        this.originalTimeStmap = stamp;
    }

    public String printOriginalTimeStamp{
        switch (punchtypeid){
            case 0:
                return badgeid + " CLOCKED IN: " + stamp;
                break;
            case 1:
                return badgeid + " CLOCKED OUT: " + stamp;
                break;
            case 2:
                return badgeid + " TIMED OUT: " + stamp;
                break;
            default:
                break;
        }
    }
}
