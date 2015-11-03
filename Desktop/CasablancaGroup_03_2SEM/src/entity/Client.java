/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class Client {

    private int client_no;
    private String client_name;
    private String client_surname;
    private String client_address;
    private int representative_no;
    private int client_passport;
    private String client_country;
    private int client_phone;
    private String client_email;
    private String client_agency;

    //Combining all info from both sql tables.
    public Client(int client_no, String client_name, String client_surname, String client_address, int representative_no, int client_passport, String client_country, int client_phone, String client_email, String client_agency) {
        this.client_no = client_no;
        this.client_name = client_name;
        this.client_surname = client_surname;
        this.client_address = client_address;
        this.representative_no = representative_no;
        this.client_passport = client_passport;
        this.client_country = client_country;
        this.client_phone = client_phone;
        this.client_email = client_email;
        this.client_agency = client_agency;
    }

    //General information
    public Client(int client_no, String client_name, String client_surname, String client_address) {
        this.client_no = client_no;
        this.client_name = client_name;
        this.client_surname = client_surname;
        this.client_address = client_address;
    }

    //Secondary client
    public Client(int client_no, String client_name, String client_surname, String client_address, int representative_no) {
        this.client_no = client_no;
        this.client_name = client_name;
        this.client_surname = client_surname;
        this.client_address = client_address;
        this.representative_no = representative_no;
    }

    //Secondary information
    public Client(int client_no, int client_passport, String client_country, int client_phone, String client_email, String client_agency) {
        this.client_no = client_no;
        this.client_passport = client_passport;
        this.client_country = client_country;
        this.client_phone = client_phone;
        this.client_email = client_email;
        this.client_agency = client_agency;
    }

    //For The Email
    public Client(String client_name, String client_surname, String client_address, int client_passport, String client_country, int client_phone, String client_email) {
        this.client_name = client_name;
        this.client_surname = client_surname;
        this.client_address = client_address;
        this.client_passport = client_passport;
        this.client_country = client_country;
        this.client_phone = client_phone;
        this.client_email = client_email;
    }

    public int getClient_no() {
        return client_no;
    }

    public void setClient_no(int client_no) {
        this.client_no = client_no;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_surname() {
        return client_surname;
    }

    public void setClient_surname(String client_surname) {
        this.client_surname = client_surname;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public int getClient_passport() {
        return client_passport;
    }

    public void setClient_passport(int client_passport) {
        this.client_passport = client_passport;
    }

    public String getClient_country() {
        return client_country;
    }

    public void setClient_country(String client_country) {
        this.client_country = client_country;
    }

    public int getClient_phone() {
        return client_phone;
    }

    public void setClient_phone(int client_phone) {
        this.client_phone = client_phone;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_agency() {
        return client_agency;
    }

    public void setClient_agency(String client_agency) {
        this.client_agency = client_agency;
    }

    public int getRepresentative_no() {
        return representative_no;
    }

    public void setRepresentative_no(int representative_no) {
        this.representative_no = representative_no;
    }

    @Override
    public String toString() {
        return "User ID" + client_no + "\n"
                + "Name: " + client_name + "\n"
                + "Surname: " + client_surname + "\n"
                + "Adress: " + client_address;
    }

    public String toStringNames() {
        return client_name + " " + client_surname;
    }

    public String toString2() {
        return "Passport: " + client_passport + "\n"
                + "Country: " + client_country + "\n"
                + "Phone: " + client_phone + "\n"
                + "Email: " + client_email + "\n"
                + "Agency: " + client_agency;
    }

    public String toString3() {
        return "User_no" + client_no + "\n"
                + "Name: " + client_name + "\n"
                + "Surname: " + client_surname + "\n"
                + "Adress: " + client_address + "\n"
                + "Representative_no" + representative_no;
    }

    public String toString4() {
        return "User ID: " + client_no + ", Name: " + client_name + ", Surname: " + client_surname + ", Address: " + client_address + ", Representative: " + representative_no + ", Passport: " + client_passport + ", Country: " + client_country + ", Phone: " + client_phone + ", Email:" + client_email + ", Agency: " + client_agency;
    }
}
