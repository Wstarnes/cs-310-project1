package cs310project1;

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Punch {
    private Badge badge;
    private int punchid;
    private int terminalid;
    private int punchtypeid;
    private GregorianCalendar stamp;

    public Punch(Badge badge, int terminalid, int punchtypeid){
        this.badge = badge;
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        this.stamp = new GregorianCalendar(TimeZone.getDefault());
    }

<<<<<<< HEAD
    public GregorianCalendar printOriginalTimeStampp(){
        if (punchtypeid == 0)
=======
    Object printOriginalTimestamp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
>>>>>>> c1bdee4993c71b52cca6fc64ffa73fbde8a64e42
}
