/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;

import entity.Client;
import entity.Field;
import entity.Employee;
import entity.Reservation;
import entity.Sport;
import entity.SportBooking;
import entity.SportInstructor;
import entity.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Desting
 */
public class UnitofWork {

    private ArrayList resultsList;
    private ClientMapper clientMapper;
    private ReservationMapper reservationMapper;
    private SportBookingMapper sportBookingMapper;
    private FieldMapper fieldMapper;
    private EmployeeMapper employeeMapper;
    private SportMapper sportMapper;
    private UserMapper userMapper;
    private SportInstructorMapper sportInstructorMapper;
    private final ArrayList<Client> newClientAL;
    private final ArrayList<Client> newClientPrvInfAL;
    private final ArrayList<Reservation> newReservationAL;
    private final ArrayList<SportBooking> newSportBookingAL;
    private final ArrayList<Field> newFieldAL;
    private final ArrayList<Employee> newInstructorAL;
    private final ArrayList<Sport> newSportAL;
    private final ArrayList<SportInstructor> newSportInstructorAL;
    private final ArrayList<User> newAccountAL;
    private final ArrayList<Client> updateCLientAL;
    private final ArrayList<Client> updateClientPrvInfAL;
    private final ArrayList<Reservation> updateReservationAL;
    private final ArrayList<SportBooking> updateSportBookingAL;
    private final ArrayList<Field> updateFieldAL;
    private final ArrayList<Employee> updateEmployeeAL;
    private final ArrayList<Sport> updateSportAL;
    private final ArrayList<SportInstructor> updateSportInstructorAL;
    private final ArrayList<String> deletingActualSport;
    private final ArrayList<Integer> deletingReservations;
    private final ArrayList<Integer> deletingSportBooking;
    private final ArrayList<Integer> deletingFields;
    private final ArrayList<Integer> deletingInstructor;
    private final ArrayList<Integer> deletingSportInstructors;
    private final ArrayList<String> deletingAccountAL;
    private Connection dbConnection;

    public UnitofWork(ClientMapper clientMapper, ReservationMapper reservationMapper, SportBookingMapper sportBookingMapper, FieldMapper fieldMapper, EmployeeMapper instructorMapper, SportMapper sportMapper, SportInstructorMapper sportInstructorMapper, UserMapper userMapper, Connection DBconnect) {
        this.resultsList = new ArrayList<>();
        this.clientMapper = clientMapper;
        this.reservationMapper = reservationMapper;
        this.sportBookingMapper = sportBookingMapper;
        this.fieldMapper = fieldMapper;
        this.employeeMapper = instructorMapper;
        this.sportMapper = sportMapper;
        this.sportInstructorMapper = sportInstructorMapper;
        this.userMapper = userMapper;
        newClientAL = new ArrayList<>();
        newClientPrvInfAL = new ArrayList<>();
        newReservationAL = new ArrayList<>();
        newSportBookingAL = new ArrayList<>();
        newInstructorAL = new ArrayList<>();
        newFieldAL = new ArrayList<>();
        newSportAL = new ArrayList<>();
        newSportInstructorAL = new ArrayList<>();
        newAccountAL = new ArrayList<>();
        updateClientPrvInfAL = new ArrayList<>();
        updateCLientAL = new ArrayList<>();
        updateReservationAL = new ArrayList<>();
        updateSportBookingAL = new ArrayList<>();
        updateFieldAL = new ArrayList<>();
        updateEmployeeAL = new ArrayList<>();
        updateSportAL = new ArrayList<>();
        updateSportInstructorAL = new ArrayList<>();
        deletingReservations = new ArrayList<>();
        deletingSportBooking = new ArrayList<>();
        deletingFields = new ArrayList<>();
        deletingInstructor = new ArrayList<>();
        deletingActualSport = new ArrayList<>();
        deletingSportInstructors = new ArrayList<>();
        deletingAccountAL = new ArrayList<>();
        this.dbConnection = DBconnect;
    }

    //Add to ArrayLists
    public void addingNewClientToArrayListOfClientsForClientTBL(Client client) {
        if (!newClientAL.contains(client)) {
            newClientAL.add(client);
            resultsList.add(client);
        }
    }

    public void addingNewClientPrvInfToArrayListOfClientPrvInfForCLientPrvTBL(Client client) {
        if (!newClientPrvInfAL.contains(client)) {
            newClientPrvInfAL.add(client);
        }
    }

    public void addingNewReservationToArrayListOfReservationForReservationTBL(Reservation reservation) {
        if (!newReservationAL.contains(reservation)) {
            newReservationAL.add(reservation);
        }
    }

    public void addingNewBookingToArrayListOfBookingSportForBookingSportTBL(SportBooking sportBooking) {
        if (!newSportBookingAL.contains(sportBooking)) {
            newSportBookingAL.add(sportBooking);
        }
    }

    public void addingNewFieldToArrayListOfFieldsForFieldTable(Field field) {
        if (!newFieldAL.contains(field)) {
            newFieldAL.add(field);
        }
    }

    public void addingNewInstructorToArrayListofInstructorsForInstructorTable(Employee instructor) {
        if (!newInstructorAL.contains(instructor)) {
            newInstructorAL.add(instructor);
        }
    }

    public void addingNewSportToArrayListofSportsForSportTBL(Sport sport) {
        if (!newSportAL.contains(sport)) {
            newSportAL.add(sport);
        }
    }

    public void addingNewSportInstructorToArrayListofSportInstructorsForSportInstructorTBL(SportInstructor spi) {
        if (!newSportInstructorAL.contains(spi)) {
            newSportInstructorAL.add(spi);
        }
    }

    public void addingNewAccountInfoToArrayListOfLoginInformationForLoginTBLs(User user) {
        if (!newAccountAL.contains(user)) {
            newAccountAL.add(user);
        }
    }

    //Updating
    public void updatingClientToArrayListOfClientsForClientTBL(Client client) {
        if (!updateCLientAL.contains(client)) {
            updateCLientAL.add(client);
        }
    }

    public void updatingClientPrvInfToArrayListOfClientsForClientPrvInfTBL(Client client) {
        if (!updateClientPrvInfAL.contains(client)) {
            updateClientPrvInfAL.add(client);
        }
    }

    public void updatingReservationToArrayListOfReservationsForReservationTBL(Reservation reservation) {
        if (!updateReservationAL.contains(reservation)) {
            updateReservationAL.add(reservation);
        }
    }

    public void updatingSportBookingToArrayListOfBookingsForSportBookingTBL(SportBooking actualBooking) {
        if (!updateSportBookingAL.contains(actualBooking)) {
            updateSportBookingAL.add(actualBooking);
        }
    }

    public void updatingFieldToArrayListofFieldsForFieldsTBL(Field field) {
        if (!updateFieldAL.contains(field)) {
            updateFieldAL.add(field);
        }
    }

    public void updateInstructorToArrayListofInstructorsForInstructorTBL(Employee instructor) {
        if (!updateEmployeeAL.contains(instructor)) {
            updateEmployeeAL.add(instructor);
        }
    }

    public void updateSportToArrayListOfSportsForSportTBL(Sport sport) {
        if (!updateSportAL.contains(sport)) {
            updateSportAL.add(sport);
        }
    }

    public void updateSportInstructorToArrayListOfSportInstructorsForSportInstructorTBL(SportInstructor spi) {
        if (!updateSportInstructorAL.contains(spi)) {
            updateSportInstructorAL.add(spi);
        }
    }

//Deleting
    public void deletingReservationbyaddingResNoNoToArrayListOfInt(int reservation_no) {
        if (!deletingReservations.contains(reservation_no)) {
            deletingReservations.add(reservation_no);
        }
    }

    public void deletingSportBookingbyaddingBookNoToArrayListOfInt(int booking_no) {
        if (!deletingSportBooking.contains(booking_no)) {
            deletingSportBooking.add(booking_no);
        }
    }

    public void deletingFieldsbyAddingNoToArrayListOfInt(int field_no) {
        if (!deletingFields.contains(field_no)) {
            deletingFields.add(field_no);
        }

    }

    public void deletingInstructorsByAddingNoToArrayListOfInt(int instructor_no) {
        if (!deletingInstructor.contains(instructor_no)) {
            deletingInstructor.add(instructor_no);
        }
    }

    public void deletingActualSportsAndInstructorPricesByAddingSportNamesToArrayListOfStrings(String sport_name) {
        if (!deletingActualSport.contains(sport_name)) {
            deletingActualSport.add(sport_name);
        }
    }

    public void deletingEmployeeNoandSportName(int sequence_no) {
        if (!deletingSportInstructors.contains(sequence_no)) {
            deletingSportInstructors.add(sequence_no);
        }
    }

    public void deletingUserAccount(String user_name) {
        if (!deletingAccountAL.contains(user_name)) {
            deletingAccountAL.add(user_name);
        }
    }

    public boolean commit(Connection connection) {
        boolean status = true;  // will be set false if any part of transaction fails    
        try {

            if (!newClientAL.isEmpty()) {
                status = status && clientMapper.saveInformationIntoClientTable(newClientAL, dbConnection);
            }
            if (!newClientPrvInfAL.isEmpty()) {
                status = status && clientMapper.saveInformationIntoClientPrivateInformationTable(newClientPrvInfAL, dbConnection);
            }
            if (!newReservationAL.isEmpty()) {
                status = status && reservationMapper.saveInformationIntoReservationTable(newReservationAL, dbConnection);
            }
            if (!newSportBookingAL.isEmpty()) {
                status = status && sportBookingMapper.saveInformationIntoSportBookingTable(newSportBookingAL, dbConnection);
            }
            if (!newFieldAL.isEmpty()) {
                status = status && fieldMapper.saveInformationIntoFieldTable(newFieldAL, dbConnection);
            }
            if (!newInstructorAL.isEmpty()) {
                status = status && employeeMapper.saveInformationEmployeeTable(newInstructorAL, dbConnection);
            }
            if (!newSportAL.isEmpty()) {
                status = status && sportMapper.saveInformationIntoSportTable(newSportAL, dbConnection);
            }
            if (!newSportInstructorAL.isEmpty()) {
                status = status && sportInstructorMapper.saveInformationIntoSportInstructorTable(newSportInstructorAL, dbConnection);
            }
            if (!newAccountAL.isEmpty()) {
                status = status && userMapper.saveAccountInformationIntoLoginTables(newAccountAL, dbConnection);
            }
            if (!updateCLientAL.isEmpty()) {
                status = status && clientMapper.updateInformationIntoClientTable(updateCLientAL, dbConnection);
            }
            if (!updateClientPrvInfAL.isEmpty()) {
                status = status && clientMapper.updateInformationIntoClientPrivateInformationTable(updateClientPrvInfAL, dbConnection);
            }
            if (!updateReservationAL.isEmpty()) {
                status = status && reservationMapper.updateInformationIntoReservationTable(updateReservationAL, dbConnection);
            }
            if (!updateSportBookingAL.isEmpty()) {
                status = status && sportBookingMapper.updateInformationIntoSportBookingTable(updateSportBookingAL, dbConnection);
            }
            if (!updateFieldAL.isEmpty()) {
                status = status && fieldMapper.updateInformationIntoFieldTable(updateFieldAL, dbConnection);
            }
            if (!updateEmployeeAL.isEmpty()) {
                status = status && employeeMapper.updateInformationIntoEmployeeTableWherePossitionIsInstructor(updateEmployeeAL, dbConnection);
            }
            if (!updateSportAL.isEmpty()) {
                status = status && sportMapper.updateInformationIntoSportTable(updateSportAL, dbConnection);
            }
            if (!updateSportInstructorAL.isEmpty()) {
                status = status && sportInstructorMapper.updateInformationIntoFieldTable(updateSportInstructorAL, dbConnection);
            }
            if (!deletingReservations.isEmpty()) {
                status = status && reservationMapper.deleteReservation(deletingReservations, dbConnection);
            }
            if (!deletingSportBooking.isEmpty()) {
                status = status && sportBookingMapper.deleteSportBooking(deletingSportBooking, dbConnection);
            }
            if (!deletingFields.isEmpty()) {
                status = status && fieldMapper.deleteSportFacilityField(deletingFields, dbConnection);
            }
            if (!deletingSportInstructors.isEmpty()) {
                status = status && sportInstructorMapper.deleteInstructorAndSport(deletingSportInstructors, dbConnection);
            }
            if (!deletingInstructor.isEmpty()) {
                status = status && employeeMapper.deleteInstructorFromEmployeeTable(deletingInstructor, dbConnection);
            }
            if (!deletingActualSport.isEmpty()) {
                status = status && sportMapper.deleteActualSport(deletingActualSport, dbConnection);
            }
            if (!deletingAccountAL.isEmpty()) {
                status = status && userMapper.deleteEmployeeAccount(deletingAccountAL, dbConnection);
            }
            if (!status) {
                throw new Exception("Process Order Business Transaction aborted");
            }

            if (!status) {
                throw new Exception("Process Order Business Transaction aborted");
            }
            connection.commit();
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            status = false;
        }

        return status;
    }
}
