package cs310project1;

public class CS310Project1 {

    public static void main(String[] args){
       
        TASDatabase db = new TASDatabase();
        
        Shift s2 = db.getShift(2);

        Punch p1 = db.getPunch(4943);
        Punch p2 = db.getPunch(5004);
		
        /* Adjust Punches According to Shift Rulesets */
        
        p1.adjust(s2);
        p2.adjust(s2);
        	
        /* Compare Adjusted Timestamps to Expected Values */
        
        System.out.println(p1.printOriginalTimestamp());
        System.out.println(p1.printAdjustedTimestamp());
        
        System.out.println(p2.printOriginalTimestamp());
        System.out.println(p2.printAdjustedTimestamp());
        /*
        System.out.println(p3.printOriginalTimestamp());
        System.out.println(p3.printAdjustedTimestamp());
        
        System.out.println(p4.printOriginalTimestamp());
        System.out.println(p4.printAdjustedTimestamp()); 
        
        System.out.println(p5.printOriginalTimestamp());
        System.out.println(p5.printAdjustedTimestamp());

        System.out.println(p6.printOriginalTimestamp());
        System.out.println(p6.printAdjustedTimestamp());

        System.out.println(p7.printOriginalTimestamp());
        System.out.println(p7.printAdjustedTimestamp());     
*/
        db.close();
    }
    
}
