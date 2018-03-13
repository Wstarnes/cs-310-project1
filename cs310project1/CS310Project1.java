package cs310project1;

import java.util.ArrayList;

public class CS310Project1 {

    public static void main(String[] args){
       
        TASDatabase db = new TASDatabase();
        
        Punch p = db.getPunch(3634);
        Badge b = db.getBadge(p.getBadgeid());
        Shift s = db.getShift(b);
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimeStamp());
        
        for(Punch x : dailypunchlist){
            System.out.println(x.printOriginalTimestamp());
        }
        
        db.close();
    }
    
}
