/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

public class Reservation {

    private int reservation_no;
    private Date client_arrival;
    private Date client_departure;
    private int client_no;
    private int room_no;
    private String deposit;
    private Date reservation_date;

    public Reservation(int reservation_no, Date client_arrival, Date client_departure, int client_no, int room_no, String deposit, Date reservation_date) {
        this.reservation_no = reservation_no;
        this.client_arrival = client_arrival;
        this.client_departure = client_departure;
        this.client_no = client_no;
        this.room_no = room_no;
        this.deposit = deposit;
        this.reservation_date = reservation_date;
    }
    //for emails
    public Reservation(Date client_arrival, Date client_departure, int room_no, String deposit, Date reservation_date) {
        this.client_arrival = client_arrival;
        this.client_departure = client_departure;
        this.room_no = room_no;
        this.deposit = deposit;
        this.reservation_date = reservation_date;
    }

    public Date getResercation_date() {
        return reservation_date;
    }

    public void setResercation_date(Date resercation_date) {
        this.reservation_date = resercation_date;
    }

    public Date getClient_arrival() {
        return client_arrival;
    }

    public void setClient_arrival(Date client_arrival) {
        this.client_arrival = client_arrival;
    }

    public Date getClient_departure() {
        return client_departure;
    }

    public void setClient_departure(Date client_departure) {
        this.client_departure = client_departure;
    }

    public int getReservation_no() {
        return reservation_no;
    }

    public void setReservation_no(int reservation_no) {
        this.reservation_no = reservation_no;
    }

    public int getClient_no() {
        return client_no;
    }

    public void setClient_no(int client_no) {
        this.client_no = client_no;
    }

    public int getRoom_no() {
        return room_no;
    }

    public void setRoom_no(int room_no) {
        this.room_no = room_no;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return reservation_no + ", " + client_arrival + ", " + client_departure + ", " + client_no + ", " + room_no + ", " + deposit;
    }
}
