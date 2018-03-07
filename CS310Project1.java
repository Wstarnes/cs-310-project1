package cs310project1;

import java.sql.SQLException;

public class CS310Project1 {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
       
        TASDatabase db = new TASDatabase();
        
        Badge b1 = db.getBadge("12565C60");
        Badge b2 = db.getBadge("08D01475");
        Badge b3 = db.getBadge("D2CC71D4");
        
        Shift s1 = db.getShift(1);
        Shift s2 = db.getShift(2);
        Shift s3 = db.getShift(3);
		        
        System.out.println(s1.toString());
        System.out.println(s2.toString());
        System.out.println(s3.toString());
        
        System.out.println(b1.toString());
        System.out.println(b2.toString());
        System.out.println(b3.toString());

        db.close();
    }
    
}
