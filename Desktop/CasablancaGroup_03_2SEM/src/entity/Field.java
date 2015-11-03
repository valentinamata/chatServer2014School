package entity;

public class Field {
    int field_no;
    String sport_name;

    public Field(int field_no, String sport_name) {
        this.field_no = field_no;
        this.sport_name = sport_name;
    }

    public int getField_no() {
        return field_no;
    }

    public void setField_no(int field_no) {
        this.field_no = field_no;
    }

    public String getSport_name() {
        return sport_name;
    }

    public void setSport_name(String sport_name) {
        this.sport_name = sport_name;
    }
    
        @Override
    public String toString() {
        return "Field_no: " + field_no + ", Sport name:" + sport_name;
    }
}
