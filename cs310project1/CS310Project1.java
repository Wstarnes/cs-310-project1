package cs310project1;

import java.util.ArrayList;

public class CS310Project1 {

    public static void main(String[] args){
       
        TASDatabase db = new TASDatabase();
        
        Punch p = db.getPunch(4943);
        Badge b = db.getBadge(p.getBadgeid());
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginaltimestamp());
        
        System.out.println(TASLogic.getPunchListAsJSON(dailypunchlist));

        db.close();
    }
    
}
