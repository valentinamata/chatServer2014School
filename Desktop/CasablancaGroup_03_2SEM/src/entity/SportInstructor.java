package entity;

public class SportInstructor {

    int instructor_no;
    String sport_name;
    int sequence_no;

    public SportInstructor(int sequence_no, int instructor_no, String sport_name) {
        this.sequence_no = sequence_no;
        this.instructor_no = instructor_no;
        this.sport_name = sport_name;
    }

    public int getSequence_no() {
        return sequence_no;
    }

    public void setSequence_no(int sequence_no) {
        this.sequence_no = sequence_no;
    }

    public int getInstructor_no() {
        return instructor_no;
    }

    public void setInstructor_no(int instructor_no) {
        this.instructor_no = instructor_no;
    }

    public String getSport_name() {
        return sport_name;
    }

    public void setSport_name(String sport_name) {
        this.sport_name = sport_name;
    }

    @Override
    public String toString() {
        return "Sequence no : " +  sequence_no +", Instructor : " + instructor_no + ", Sport : " + sport_name;
    }
}
