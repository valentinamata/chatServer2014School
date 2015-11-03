/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

public class SportBooking {

    private int sportBooking_no;
    private int client_no;
    private int field_no;
    private Date sportBooking_Date;
    private String sportBooking_Hour;
    private int instructor_no;

    public SportBooking(int sportBooking_no, int client_no, int field_no, Date sportBooking_Date, String sportBooking_Hour, int instructor_no) {
        this.sportBooking_no = sportBooking_no;
        this.client_no = client_no;
        this.field_no = field_no;
        this.sportBooking_Date = sportBooking_Date;
        this.sportBooking_Hour = sportBooking_Hour;
        this.instructor_no = instructor_no;
    }

    public int getSportBooking_no() {
        return sportBooking_no;
    }

    public void setSportBooking_no(int sportBooking_no) {
        this.sportBooking_no = sportBooking_no;
    }

    public int getClient_no() {
        return client_no;
    }

    public void setClient_no(int client_no) {
        this.client_no = client_no;
    }

    public int getField_no() {
        return field_no;
    }

    public void setField_no(int field_no) {
        this.field_no = field_no;
    }

    public int getInstructor_no() {
        return instructor_no;
    }

    public void setInstructor_no(int instructor_no) {
        this.instructor_no = instructor_no;
    }

    public Date getSportBooking_Date() {
        return sportBooking_Date;
    }

    public void setSportBooking_Date(Date sportBooking_Date) {
        this.sportBooking_Date = sportBooking_Date;
    }

    public String getSportBooking_Hour() {
        return sportBooking_Hour;
    }

    public void setSportBooking_Hour(String sportBooking_Hour) {
        this.sportBooking_Hour = sportBooking_Hour;
    }

        @Override
    public String toString() {
        return sportBooking_no + ", " + client_no + ", " + field_no + ", " + sportBooking_Date + ", " + sportBooking_Hour + ", " + instructor_no;
    }
}
