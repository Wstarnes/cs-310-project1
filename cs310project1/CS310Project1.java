package cs310project1;

public class CS310Project1 {

    public static void main(String[] args){
       
        TASDatabase db = new TASDatabase();
        
        Shift s1 = db.getShift(1);

        Punch p1 = db.getPunch(1087);
        Punch p2 = db.getPunch(1162);
		
        /* Adjust Punches According to Shift Rulesets */
        
        p1.adjust(s1);
        p2.adjust(s1);
        
        System.out.println(p1.printOriginalTimestamp());
        System.out.println(p1.printAdjustedTimestamp());
        
        System.out.println(p2.printOriginalTimestamp());
        System.out.println(p2.printAdjustedTimestamp());

        db.close();
    }
    
}
