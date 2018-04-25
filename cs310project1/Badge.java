package cs310project1;

public class Badge {
    
    private String badge_id;
    private String first_name;
    private String middle_name;
    private String last_name; 
    private int employee_type_id;
    private int department_id;
    private int shift_id;
    private boolean active;

    public Badge(){
        badge_id = "";
        first_name = "";
        middle_name = "";
        last_name = "";
        employee_type_id = 0;
        department_id = 0;
        shift_id = 0;
    }
    
    public Badge(String badge_id){
        this.badge_id = badge_id;
        first_name = "";
        middle_name = "";
        last_name = "";
        employee_type_id = 0;
        department_id = 0;
        shift_id = 0;
    }   
    
    public String getBadge_id() {
        return badge_id;
    }

    public void setBadge_id(String badge_id) {
        this.badge_id = badge_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getEmployee_type_id() {
        return employee_type_id;
    }

    public void setEmployee_type_id(int employee_type_id) {
        this.employee_type_id = employee_type_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString(){
        return "#" + badge_id + " (" + last_name + ", " + first_name + " " + middle_name.charAt(0) + ")";
    }
  
}