package cs310project1;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.*;

public class TASLogic {
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist){
        
        /* Create ArrayList Object */
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();

        for(Punch punch : dailypunchlist){
            
            /* Create HashMap Object*/
            HashMap<String, String>  punchData = new HashMap<>();

            /* Add Punch Data to HashMap */
            punchData.put("id", String.valueOf(punch.getPunchid()));
            punchData.put("badgeid", String.valueOf(punch.getBadgeid()));
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("eventtypeid", String.valueOf(punch.getPunchtypeid()));
            punchData.put("eventdata", String.valueOf(punch.getEventdata()));
            punchData.put("originaltimestamp", String.valueOf(punch.getOriginaltimestamp().getTimeInMillis()));

            /* Append HashMap to ArrayList */
            jsonData.add(punchData);            
            
        }
        String json = JSONValue.toJSONString(jsonData);
        return json;
    }
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        return 0;
    }

}
