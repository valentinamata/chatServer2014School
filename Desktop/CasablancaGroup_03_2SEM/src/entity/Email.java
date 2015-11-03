/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Boyko
 */
public class Email {

    public void eMailSender(ArrayList<Client> clientsInfo, ArrayList<User> clientUsers, Reservation reservation) {
        try {
            String clients = "";
            for (int i = 0; i < clientsInfo.size(); i++) {
                clients += "Name: " + clientsInfo.get(i).getClient_name() + ", Surname: " + clientsInfo.get(i).getClient_surname()
                        + ", Address: " + clientsInfo.get(i).getClient_address() + ", Passport: " + clientsInfo.get(i).getClient_passport()
                        + ", Country: " + clientsInfo.get(i).getClient_country() + ", PhoneNo: " + clientsInfo.get(i).getClient_phone()
                        + ", eMail: " + clientsInfo.get(i).getClient_email() + ".\n";
                
            }
            System.out.println("All " + clients);
            String accounts = "";
            for (int i = 0; i < clientUsers.size(); i++) {
                accounts += "Username: " + clientUsers.get(i).getUsername()
                        + ", Password: " + clientUsers.get(i).getPassword() + ".\n";
            }
            String reserve = "Arrival date: " + reservation.getClient_arrival().toString()
                    + ", Departure date: " + reservation.getClient_departure().toString() + ", Room no:" + reservation.getRoom_no()
                    + ", Deposit status: " + reservation.getDeposit() + ", Reservation date: " + reservation.getResercation_date().toString();
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // for gmail use smtp.gmail.com
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("CasablancaMadaFaka@gmail.com", "valentina94");
                }
            });

            mailSession.setDebug(true); // Enable the debug mode

            Message msg = new MimeMessage(mailSession);

            //--[ Set the FROM, TO, DATE and SUBJECT fields
            msg.setFrom(new InternetAddress("CasablancaMadaFaka@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("boyko.surlev@gmail.com"));
            msg.setSentDate(new Date());
            msg.setSubject("Hotel Reservation in Casablanca Hotel Morocco");
            //--[ Create the body of the mail
            msg.setText("Welcome to our Hotel, dear friend."
                    + "\n\t Hope that we will see each other soon =)\n"
                    + "Your reservation: " + reserve + "\n"
                    + "Guests information: " + clients + "\n"
                    + "Guest accounts information: " + accounts );

            //--[ Ask the Transport class to send our mail message
            Transport.send(msg);

        } catch (MessagingException E) {
            System.out.println("Oops something has gone pearshaped!");
            System.out.println(E);
        }
    }

}
