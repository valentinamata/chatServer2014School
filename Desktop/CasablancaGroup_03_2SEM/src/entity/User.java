/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Boyko
 */
public class User {

    private String username;
    private String password;
    private int personal_no;

    public User(String username, String password, int personal_no) {
        this.username = username;
        this.password = password;
        this.personal_no = personal_no;
    }
    //For E-mails
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPersonal_no() {
        return personal_no;
    }

    public void setPersonal_no(int personal_no) {
        this.personal_no = personal_no;
    }

    @Override
    public String toString() {
        return "Username :" + username + ", Password: " + password + ", ID :" + personal_no + '}';
    }
}
