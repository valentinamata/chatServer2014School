/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Client;
import entity.Field;
import entity.Employee;
import entity.Reservation;
import entity.Room;
import entity.Sport;
import entity.SportBooking;
import entity.SportInstructor;
import entity.User;
import dataSource.ClientMapper;
import dataSource.DatabaseConnection;
import dataSource.FieldMapper;
import dataSource.EmployeeMapper;
import dataSource.ReservationMapper;
import dataSource.RoomMapper;
import dataSource.SportBookingMapper;
import dataSource.SportInstructorMapper;
import dataSource.SportMapper;
import dataSource.UnitofWork;
import dataSource.UserMapper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Desting
 */
public class DatabaseFacade {

    private static ClientMapper clientMapper;
    private static ReservationMapper reservationMapper;
    private static RoomMapper roomMapper;
    private static SportBookingMapper sportBookingMapper;
    private static FieldMapper fieldMapper;
    private static EmployeeMapper employeeMapper;
    private static SportMapper sportMapper;
    private static SportInstructorMapper sportInstructorMapper;
    private static UserMapper userMapper;
    private UnitofWork unitOfWork;
    private final Connection dbConnection;
    private boolean isInitialised = false;
    private static DatabaseFacade dbf;

    private DatabaseFacade() {
        clientMapper = new ClientMapper();
        reservationMapper = new ReservationMapper();
        roomMapper = new RoomMapper(DatabaseConnection.getConnection());
        sportBookingMapper = new SportBookingMapper();
        fieldMapper = new FieldMapper();
        employeeMapper = new EmployeeMapper();
        sportMapper = new SportMapper();
        sportInstructorMapper = new SportInstructorMapper();
        userMapper = new UserMapper();
        dbConnection = DatabaseConnection.getConnection();
    }

    public static DatabaseFacade getInstance() {

        if (dbf == null) {
            dbf = new DatabaseFacade();
        }
        return dbf;
    }
    //shuts down the program
    public void onShutdown()
    {
        DatabaseConnection.releaseConnection(dbConnection);
    }
    

    //Numbers
    public int getNextClientNo() {
        return clientMapper.getNextClientNo(dbConnection);
    }

    public int getReservationNo() {
        return reservationMapper.getNextRegistrationNo(dbConnection);
    }

    public int getCurrentClientNo() {
        return clientMapper.getCurrentClientNo(dbConnection);
    }

    public int getNextBookingNo() {
        return sportBookingMapper.getNextSportBookingNo(dbConnection);
    }

    public int getNextFieldNo() {
        return fieldMapper.getNextFieldNo(dbConnection);
    }

    public int getNextInstructorNo() {
        return employeeMapper.getNextEmployeeNo(dbConnection);
    }

    public int getNextSportInstructorNo() {
        return sportInstructorMapper.getNextSportInstructorNo(dbConnection);
    }

    //Creating
    public void registerNewClientifUnitOfWorkIsInitialized(Client client) {
        if (unitOfWork != null) {
            unitOfWork.addingNewClientToArrayListOfClientsForClientTBL(client);
        }
    }

    public void registerNewClientPrvInfIfUnitOfWorkIsInitialized(Client client) {
        if (unitOfWork != null) {
            unitOfWork.addingNewClientPrvInfToArrayListOfClientPrvInfForCLientPrvTBL(client);
        }
    }

    public void registerNewReservationIfUnitOfWorkIsInitialized(Reservation reservation) {
        if (unitOfWork != null) {
            unitOfWork.addingNewReservationToArrayListOfReservationForReservationTBL(reservation);
        }
    }

    public void registerNewSPortBookingIfUnitOfWorkIsInitialized(SportBooking sportBooking) {
        if (unitOfWork != null) {
            unitOfWork.addingNewBookingToArrayListOfBookingSportForBookingSportTBL(sportBooking);
        }

    }

    public void registerNewFieldIfUnitOfWorkIsInitialized(Field field) {
        if (unitOfWork != null) {
            unitOfWork.addingNewFieldToArrayListOfFieldsForFieldTable(field);
        }
    }

    public void registerNewInstructorIfUnitOfWorkIsInitialized(Employee instructor) {
        if (unitOfWork != null) {
            unitOfWork.addingNewInstructorToArrayListofInstructorsForInstructorTable(instructor);
        }
    }

    public void registerNewSportIfUnitOfWorkIsInitialized(Sport sport) {
        if (unitOfWork != null) {
            unitOfWork.addingNewSportToArrayListofSportsForSportTBL(sport);
        }
    }

    public void registerNewInstructorOfSportsIfUnitOfWorkIsInitialized(SportInstructor sportinstructor) {
        if (unitOfWork != null) {
            unitOfWork.addingNewSportInstructorToArrayListofSportInstructorsForSportInstructorTBL(sportinstructor);
        }
    }

    public void registerNewLoginIfUnitOfWorkISInitialized(User user) {
        if (unitOfWork != null) {
            unitOfWork.addingNewAccountInfoToArrayListOfLoginInformationForLoginTBLs(user);

        }
    }

    //Retrieving
    public Client getParticularClient(int client_no) {
        return clientMapper.getInformationFromClientTable(client_no, dbConnection);
    }

    public Client getParticularClientPrivateInf(int client_no) {
        return clientMapper.getInformationFromClientPrivateInformationTable(client_no, dbConnection);
    }

    public Reservation getReservationInfo(int reservation_no) {
        return reservationMapper.getInformationFromReservationTable(reservation_no, dbConnection);
    }

    public SportBooking getSportBookingInfo(int booking_no) {

        return sportBookingMapper.getInformationFromSportBookingTable(booking_no, dbConnection);
    }
    

    public Field getFieldInfo(int field_no) {
        return fieldMapper.getInformationFromFieldTable(field_no, dbConnection);
    }

    public Employee getEmployeeInfo(int instructor_no) {
        return employeeMapper.getInformationFromEmployeeTable(instructor_no, dbConnection);
    }

    public Sport getSportinstructorPriceFromSportTBL(String sport_name) {
        return sportMapper.getInformationFromSportTable(sport_name, dbConnection);
    }

    public SportInstructor getInstructorNoAndSportTypeFromSportInstructorTBL(int sequence_no) {
        return sportInstructorMapper.getInformationFromSportInstructorTable(sequence_no, dbConnection);
    }
    
    public User getUserAccountInformationFromLoginTables(String username){
        return userMapper.getAccountInformationFromLoginTables(username, dbConnection);
    }

    public ArrayList<Reservation> getAllReservations() {
        return reservationMapper.getAllInformationFromReservationTable(dbConnection);
    }

    public ArrayList<SportInstructor> getAllInstructorsAndSports() {
        return sportInstructorMapper.getAllInformationFromSportInstructorTable(dbConnection);
    }

    public ArrayList<Room> getFreeRoomaAtDate(String room_type, Date start, Date ending) {
        return roomMapper.getFreeRoomsAtDate(room_type, start, ending);
    }

    public ArrayList<Client> getAllInformationFromClientTableAndFromClientPrvInfTable() {;
        return clientMapper.getAllClientInformationForAllClients(dbConnection);
    }

    public ArrayList<SportBooking> getAllInformationFromSportBookingTable() {
        return sportBookingMapper.getAllInformationFromSportReservationTable(dbConnection);
    }
    
    public ArrayList<SportBooking> getAllInformationFroMSportBookingTableByClient(int client_no) {
        return sportBookingMapper.getAllInformationFromSportReservationTableByClient(client_no, dbConnection);
    }

    public ArrayList<Field> getAllFields() {
        return fieldMapper.getAllInformationFromFieldTable(dbConnection);
    }

    public ArrayList<Employee> getAllEmployees() {
        return employeeMapper.getAllInformationFromEmployeeTable(dbConnection);
    }

    public ArrayList<Sport> getAllSports() {
        return sportMapper.getAllInformationFromSportTable(dbConnection);
    }
    
    public ArrayList<User> getAllEmployeeAccounts() {
        return userMapper.getAllInformationFromEmployeeLoginTable(dbConnection);
    }
    
    public ArrayList<User> getAllGuestAccounts() {
        return userMapper.getAllInformationFromGuestLoginTable(dbConnection);
    }

    //Update
    public void updateParticularClient(Client client) {
        unitOfWork.updatingClientToArrayListOfClientsForClientTBL(client);
    }

    public void updateClientPersonalInformation(Client client) {
        unitOfWork.updatingClientPrvInfToArrayListOfClientsForClientPrvInfTBL(client);
    }

    public void updateReservationInformation(Reservation reserve) {
        unitOfWork.updatingReservationToArrayListOfReservationsForReservationTBL(reserve);
    }

    public void updateSportBookingInformation(SportBooking booksport) {
        unitOfWork.updatingSportBookingToArrayListOfBookingsForSportBookingTBL(booksport);
    }

    public void updateFieldInformation(Field field) {
        unitOfWork.updatingFieldToArrayListofFieldsForFieldsTBL(field);
    }

    public void updateEmployeeInformation(Employee instructor) {
        unitOfWork.updateInstructorToArrayListofInstructorsForInstructorTBL(instructor);
    }

    public void updateSportInstructorPrice(Sport sport) {
        unitOfWork.updateSportToArrayListOfSportsForSportTBL(sport);
    }

    public void updateSportInstructorTable(SportInstructor spi) {
        unitOfWork.updateSportInstructorToArrayListOfSportInstructorsForSportInstructorTBL(spi);
    }

    //Delete
    public void deleteReservation(int reservation_no) {
        unitOfWork.deletingReservationbyaddingResNoNoToArrayListOfInt(reservation_no);
    }

    public void deleteSportBooking(int booking_no) {
        unitOfWork.deletingSportBookingbyaddingBookNoToArrayListOfInt(booking_no);
    }

    public void deleteSportFields(int field_no) {
        unitOfWork.deletingFieldsbyAddingNoToArrayListOfInt(field_no);
    }

    public void deleteInstructor(int instructor_no) {
        unitOfWork.deletingInstructorsByAddingNoToArrayListOfInt(instructor_no);
    }

    public void deleteActualSportandInstructorPrice(String sport_name) {
        unitOfWork.deletingActualSportsAndInstructorPricesByAddingSportNamesToArrayListOfStrings(sport_name);
    }

    public void deleteInstructorOfSports(int sequence_no) {
        unitOfWork.deletingEmployeeNoandSportName(sequence_no);
    }
    
    public void deleteUserAccount(String user_name) {
        unitOfWork.deletingUserAccount(user_name);
    }
    //Passport checking methods
    public String checkPassport(int passport) {
        return clientMapper.checkPassportNo(passport, dbConnection);
    }

    public boolean checkIfParticularPassportExistsInClientPrivateInformationTBL(int passport) {
        return clientMapper.checkPassportBoolean(passport, dbConnection);
    }

    public String getPassportSurname(int passport) {
        return clientMapper.getPassportSurname(passport, dbConnection);
    }

    public String getPassportAddress(int passport) {
        return clientMapper.getPassportAddress(passport, dbConnection);
    }

    public String getPassportAgency(int passport) {
        return clientMapper.getPassportAgency(passport, dbConnection);
    }

    public String getPassportCountry(int passport) {
        return clientMapper.getPassportCountry(passport, dbConnection);
    }

    public String getPassportEmail(int passport) {
        return clientMapper.getPassportEmail(passport, dbConnection);
    }

    public int getPassportPhone(int passport) {
        return clientMapper.getPassportPhone(passport, dbConnection);
    }

    public int getPassportNo(int passport) {
        return clientMapper.getPassportNom(passport, dbConnection);
    }

    public int getPassportClientNo(int passport) {
        return clientMapper.getPassportClientNo(getPassportNo(passport), dbConnection);
    }

    //Locks
    public boolean lockingFieldTable() {
        return fieldMapper.sqlLockingFieldMapper(dbConnection);
    }

    public boolean lockingSportTable() {
        return sportMapper.sqlLockingSportMapper(dbConnection);
    }

    public boolean lockingEmployeeTable() {
        return employeeMapper.sqlLockingEmployeeMapper(dbConnection);
    }

    public boolean lockingSportInstructorTable() {
        return sportInstructorMapper.sqlLockingSportInstructorMapper(dbConnection);
    }

    public boolean lockingReservationTable() {
        return reservationMapper.sqlLockingReservationMapper(dbConnection);
    }

    public boolean lockingClientTable() {
        return clientMapper.sqlLockingClientMapper(dbConnection);
    }

    public boolean lockingSportBookingTable() {
        return sportBookingMapper.sqlLockingSportBookingMapper(dbConnection);
    }

    //CheckifUnitOfWorkIsInitialised
    public boolean isIsInitialised() {
        return isInitialised;
    }

    //Pass all changes to Unitofwork
    public void initializeUnitOfWorkWithOrderMapper() {
        unitOfWork = new UnitofWork(clientMapper, reservationMapper, sportBookingMapper, fieldMapper, employeeMapper, sportMapper, sportInstructorMapper, userMapper, dbConnection);
        try {
            dbConnection.setAutoCommit(false);

        } catch (SQLException ex) {
            System.out.println("AUTO-COMMIT WENT WRONG (: " + ex);
        }

        isInitialised = true;
    }

    public boolean startTheProcessOfCommitting() {
        boolean status = false;
        if (unitOfWork != null) {
            status = unitOfWork.commit(dbConnection);
            unitOfWork = null;
            isInitialised = false;
        }
        return status;
    }

}
