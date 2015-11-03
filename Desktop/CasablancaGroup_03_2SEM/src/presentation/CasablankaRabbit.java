/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import control.Controller;
import java.util.Calendar;

/**
 *
 * @author Desting
 */
public class CasablankaRabbit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller nik = new Controller();
        nik.startTheProcessOfEditingDB();
//       nik.creatingNewClient("Nik", "Des", "Skodvej");
//       nik.creatingNewClientPrvInf(666, "DK", 777, "Lol", "DetSeje");
//      nik.creatingNewReservation("01-MAR-14", "02-MAR-14", 1);
//       nik.creatingSecondaryClients("Teo", "Dum", "Madafaka");
//       nik.creatingNewClientPrvInf(234, "DK", 234, "JegerSej", "Duforlækker");       
//        System.out.println(nik.getParticularClient(10000));
//        nik.updateParticularClient(10001, "Boyko", "Eats", "Dick");
//        nik.updateClientPersonalInformation(10001, 453, "Vala Hates us", 9876, "But Weeeeee Don't", " give a Shiiiiiit");
//       nik.updateReservationInformation(10000, "01-FEB-14", "02-FEB-14", 10000, 1);
//        System.out.println(nik.getAllReservations());
//        System.out.println(nik.getFreeRooms("Single Room"));
//       nik.deleteReservation(10000);
//        System.out.println(nik.getParticularClientPrivateInf(10003).toString2());
//       nik.getAllClients();        
//        Calendar cal = Calendar.getInstance();
//        cal.clear();
//        int year = 2006;
//        int month = 10;
//        int date = 05;
//        cal.set(year, month, date);
//        java.util.Date arrival = new java.util.Date(cal.getTimeInMillis());      
//        int nights = 3;
//        cal.add(Calendar.DATE, nights);
//        java.util.Date departure = new java.util.Date(cal.getTimeInMillis());
//        java.sql.Date sqlArrival = new java.sql.Date(arrival.getTime());
//        java.sql.Date sqlDeparture = new java.sql.Date(departure.getTime());
//        nik.creatingNewSportBooking(10001, 10001, sqlArrival, sqlDeparture, 10001);
//        nik.updateSportBookingInformation(10003,10001, 10001, sqlArrival, "15:00", 10001);            
//        System.out.println(":: "+nik.getSportBookingInfo(10001));
//        nik.deleteSportBooking(10001);
//        System.out.println(nik.getAllSportBookings().toString());
//          nik.creatingNewField("Football");
//        System.out.println(nik.getFieldInfo(10002));
//        System.out.println(nik.getAllFields());
//         nik.updateFieldInformation(10002, "Football");
//         nik.deletingSportField(10002);
//        nik.creatingNewInstructor("Madalina");
//        System.out.println(nik.getInstructorInfo(10001));
//        nik.updateInstructorInformation(10003, "Valentina");
//        nik.deletingInstructor(10003);
//        System.out.println(nik.getAllInstructors().toString());
//        nik.creatingNewSport("Volleyball", 2000);
//        System.out.println(nik.getSportPrice("Volleyball"));
//        System.out.println(nik.getAllSports().toString());
//        nik.updateInstructorPricefromSportTBL("Volleyball", 500);
//        nik.deleteActualSportAndInstructorPrice("Volleyball");        
//        nik.creatingNewSportInstructorTBLConnection(10001, "Football"); 
//        System.out.println(nik.getConnectionBetweenEmployeeandSportName(10001) + "");
//        nik.creatingNewSportBooking(10001, 10001, sqlArrival, "08:30", 10001);
//         System.out.println(nik.getAllSportInstructors());
//        nik.deleteInstructorOfSports(10001);
//         nik.endTheProcessOfCreatingDB();     
//        nik.deleteInstructorOfSports(10002);
//        nik.endTheProcessOfDeletingDB();
//       nik.endTheProcessOfUpdatingDB();
//        nik.creatingNewLogin("cl_admin", "admin", 10001);
//        System.out.println("hello");
//        System.out.println(nik.getUserInformation("emp_admin") + " <<< ");
//        System.out.println("ALL>>>>"+ nik.getAllEmployeeAccounts());
//        System.out.println("ALL>>>>"+ nik.getAllGuestAccounts());
//        nik.deleteUserAccount("cl_admin23");
        nik.endTheProcessOfEditingDB();
//        System.out.println("hello2");

    }

}
