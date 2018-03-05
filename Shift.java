package feature1;

/*

Contains information from the database about a single shift ruleset

*/

/*
TODO:
Look into how to store time objects(start,stop,interval,grace_period...etc)
Finish toString()
*/
public class Shift {
    
    private String description;
    private String start;
    private String stop;
    private String interval;
    private String grace_period;
    private int dock;
    private String lunch_start;
    private String lunch_stop;
    private int lunch_deduct;
    private int max_time;
    private String over_time_threshold;

    public Shift() {
        description = "";
        start = "";
        stop = "";
        interval = "";
        grace_period = "";
        dock = 0;
        lunch_start = "";
        lunch_stop = "";
        lunch_deduct = 0;
        max_time = 0;
        over_time_threshold = "";
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getGrace_period() {
        return grace_period;
    }

    public void setGrace_period(String grace_period) {
        this.grace_period = grace_period;
    }

    public int getDock() {
        return dock;
    }

    public void setDock(int dock) {
        this.dock = dock;
    }

    public String getLunch_start() {
        return lunch_start;
    }

    public void setLunch_start(String lunch_start) {
        this.lunch_start = lunch_start;
    }

    public String getLunch_stop() {
        return lunch_stop;
    }

    public void setLunch_stop(String lunch_stop) {
        this.lunch_stop = lunch_stop;
    }

    public int getLunch_deduct() {
        return lunch_deduct;
    }

    public void setLunch_deduct(int lunch_deduct) {
        this.lunch_deduct = lunch_deduct;
    }

    public int getMax_time() {
        return max_time;
    }

    public void setMax_time(int max_time) {
        this.max_time = max_time;
    }

    public String getOver_time_threshold() {
        return over_time_threshold;
    }

    public void setOver_time_threshold(String over_time_threshold) {
        this.over_time_threshold = over_time_threshold;
    }
    
    @Override
    public String toString(){
        return description + " " + start + " - " + stop + " ";
    }
    
}
