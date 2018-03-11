package cs310project1;

import java.text.SimpleDateFormat;

public class CS310Project1 {

    public static void main(String[] args){
       
        TASDatabase db = new TASDatabase();
        
        /* Create New Punch Object */

        Punch p1 = new Punch("021890C0", 101, 1);
		
        /* Get Punch Properties */
        
        String badgeid = p1.getBadgeid();
        String originaltimestamp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(p1.getOriginalTimeStamp().getTime());
        int terminalid = p1.getTerminalid();
        int eventtypeid = p1.getPunchtypeid();
		
        /* Insert Punch Into Database */
        
        int punchid = db.insertPunch(p1);
		
        /* Retrieve New Punch */
        
        Punch p2 = db.getPunch(punchid);        
        
        System.out.println(p2.getBadgeid());
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(p2.getOriginalTimeStamp().getTime()));
        System.out.println(p2.getTerminalid());
        System.out.println(p2.getPunchtypeid());
        
        db.close();
    }
    
}
