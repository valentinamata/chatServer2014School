package entity;

public class Employee {
    int employee_no;
    String employee_name;
    String employee_possition;

    public Employee(int employee_no, String employee_name, String employee_possition) {
        this.employee_no = employee_no;
        this.employee_name = employee_name;
        this.employee_possition = employee_possition;
    }

    public int getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(int instructor_no) {
        this.employee_no = instructor_no;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String instructor_name) {
        this.employee_name = instructor_name;
    }
    
        public String getEmployee_position() {
        return employee_possition;
    }

    public void setEmployee_possition(String employee_possition) {
        this.employee_possition = employee_possition;
    }
    
        @Override
    public String toString() {
        return employee_name + ", working as " + employee_possition + ",";
    }
    
}
