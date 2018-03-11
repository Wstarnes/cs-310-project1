package cs310project1;

public class CS310Project1 {

    public static void main(String[] args){
       
        TASDatabase db = new TASDatabase();
        
        Badge b1 = db.getBadge("12565C60");
        Badge b2 = db.getBadge("08D01475");
        Badge b3 = db.getBadge("D2CC71D4");
        
        Shift s1 = db.getShift(1);
        Shift s2 = db.getShift(2);
        Shift s3 = db.getShift(3);
        
        Punch p1 = db.getPunch(3433);
        Punch p2 = db.getPunch(3325);
        Punch p3 = db.getPunch(1963);
        
        Punch p4 = db.getPunch(5702);
        Punch p5 = db.getPunch(4976);
        Punch p6 = db.getPunch(2193);
        
        Punch p7 = db.getPunch(954);
        Punch p8 = db.getPunch(258);
        Punch p9 = db.getPunch(717);
		        
        System.out.println(s1.toString());
        System.out.println(s2.toString());
        System.out.println(s3.toString());
        
        System.out.println(b1.toString());
        System.out.println(b2.toString());
        System.out.println(b3.toString());
        
        System.out.println(p1.printOriginalTimestamp());
        System.out.println(p2.printOriginalTimestamp());
        System.out.println(p3.printOriginalTimestamp());
        
        System.out.println(p4.printOriginalTimestamp());
        System.out.println(p5.printOriginalTimestamp());
        System.out.println(p6.printOriginalTimestamp());
        
        System.out.println(p7.printOriginalTimestamp());
        System.out.println(p8.printOriginalTimestamp());
        System.out.println(p9.printOriginalTimestamp());

        db.close();
    }
    
}
