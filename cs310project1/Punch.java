package cs310project1;

import java.util.GregorianCalendar;

public class Punch {
    private String badgeid;
    private int terminalid;
    private int punchtypeid;
    private int punchid;
    private GregorianCalendar originalTimeStmap;
    private GregorianCalendar adjustedTimeStamp;

    public Punch(Badge badge, int terminalid, int punchtypeid){
        this.badge = badge.getBadge_id();
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        originalTimeStamp = new GregorianCalendar();
        adjustedTimeStamp - null;
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

    public GregorianCalendar getStamp() {
        return stamp;
    }

    public void setStamp(GregorianCalendar stamp) {
        this.stamp = stamp;
    }

    public void printOriginalTimeStamp{
        SimpleDateFormat fmt = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        fmt.setCalendar(originalTimeStmap);
        String stamp = fmt.format(originalTimeStmap.getTime());
        switch (punchtypeid){
            case 0:
                System.out.println(badgeid + " CLOCKED IN: " + stamp);
                break;
            case 1:
                System.out.println(badgeid + " CLODKED OUT: " + stamp);
                break;
            case 2:
                System.out.println(badgeid + " TIMED OUT: " + stamp);
                break;
            default:
                break;
        }
    }
}
