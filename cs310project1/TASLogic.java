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
            punchData.put("adjustedtimestamp", String.valueOf(punch.getAdjustedTimeStamp().getTimeInMillis()));

            /* Append HashMap to ArrayList */
            jsonData.add(punchData);            
            
        }
        String json = JSONValue.toJSONString(jsonData);
        return json;
    }
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        
        int total_minutes = 0;
        
        for(int i = 0; i < dailypunchlist.size() - 1; i += 2){
            
            Punch current = dailypunchlist.get(i);
            Punch next = dailypunchlist.get(i + 1);
            if(current.getPunchtypeid() != 2 && next.getPunchtypeid() != 2){
                long punch_diff = next.getAdjustedTimeStamp().getTimeInMillis() - current.getAdjustedTimeStamp().getTimeInMillis();
                total_minutes += (punch_diff / 60000);
            }
        }
        //Skipped lunch
        if(dailypunchlist.size() == 2){
            if(total_minutes > shift.getLunch_deduct())
                return total_minutes - 30;
            else
                return total_minutes;
        }
        
        else
            return total_minutes;
    }
}
