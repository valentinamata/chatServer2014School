/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import entity.Client;
import entity.Field;
import entity.Employee;
import entity.Reservation;
import entity.Room;
import entity.Sport;
import entity.SportBooking;
import entity.SportInstructor;
import entity.User;
import entity.Email;
import facade.DatabaseFacade;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Desting
 */
public class Controller {

    private Client currectClient;
    private Reservation currentReservation;
    private SportBooking currentSportBooking;
    private Field currentField;
    private Employee currentEmployee;
    private Sport currentSport;
    private SportInstructor currentSportInstructor;
    private User currentUser;
    private ArrayList freeRooms;
    private ArrayList allClients;
    private ArrayList allReservations;
    private ArrayList allSportReservations;
    private ArrayList allFields;
    private ArrayList allInstructors;
    private ArrayList allSports;
    private ArrayList allSportInstructors;
    private ArrayList allEmployeeAccounts;
    private ArrayList allGuestsAccounts;
    private DatabaseFacade dbf;
    private Email eMail;
    private int reservationNo = 0;

    public Controller() {
        currectClient = null;
        currentReservation = null;
        currentSportBooking = null;
        currentField = null;
        currentEmployee = null;
        currentSportInstructor = null;
        freeRooms = null;
        allClients = null;
        allSportReservations = null;
        allFields = null;
        allReservations = null;
        allInstructors = null;
        allSports = null;
        allEmployeeAccounts = null;
        allGuestsAccounts = null;
        dbf = DatabaseFacade.getInstance();
        eMail = new Email();
    }

    //Shuts down the program
    public void shutdownConnection() {
        dbf.onShutdown();
    }

    //Sending e-Mails
    public void sendingEmail(ArrayList<Client> clientInformation, ArrayList<User> clientUsers, Reservation reservation) {
        eMail.eMailSender(clientInformation, clientUsers, reservation);
    }

    //Creating
    public Client creatingNewClient(String client_name, String client_surname, String client_address) {

        int newClientNo = dbf.getNextClientNo();
        if (newClientNo != 0) {
            currectClient = new Client(newClientNo, client_name, client_surname, client_address);
            dbf.registerNewClientifUnitOfWorkIsInitialized(currectClient);
            reservationNo = newClientNo;
        } else {
            currectClient = null;
        }
        return currectClient;
    }

    public Client creatingSecondaryClients(String client_name, String client_surname, String client_address) {
        int newClientNo = dbf.getNextClientNo();
        if (newClientNo != 0) {
            currectClient = new Client(newClientNo, client_name, client_surname, client_address, reservationNo);
            dbf.registerNewClientifUnitOfWorkIsInitialized(currectClient);
        } else {
            currectClient = null;
        }
        return currectClient;
    }

    public Client creatingNewClientPrvInf(int client_passport, String client_country, int client_phone, String client_email, String client_agency) {
        if (currectClient.getClient_no() != 0) {
            currectClient = new Client(currectClient.getClient_no(), client_passport, client_country, client_phone, client_email, client_agency);
            dbf.registerNewClientPrvInfIfUnitOfWorkIsInitialized(currectClient);
        } else {
            currectClient = null;
        }
        return currectClient;
    }

    public Reservation creatingNewReservation(Date client_arrival, Date client_departure, int room_no, String deposit_paid, Date reservation_date) {
        int newReservationNo = dbf.getReservationNo();
        int currClientNo = dbf.getCurrentClientNo();
        if (newReservationNo != 0) {
            currentReservation = new Reservation(newReservationNo, client_arrival, client_departure, currClientNo, room_no, deposit_paid, reservation_date);
            dbf.registerNewReservationIfUnitOfWorkIsInitialized(currentReservation);
        } else {
            currentReservation = null;
        }
        return currentReservation;
    }

    public SportBooking creatingNewSportBooking(int client_no, int field_no, Date reservation_date, String reservation_hour, int instructor_no) {

        int newBookingNo = dbf.getNextBookingNo();
        //System.out.println("newbookingno" + newBookingNo);
        if (newBookingNo != 0) {
            currentSportBooking = new SportBooking(newBookingNo, client_no, field_no, reservation_date, reservation_hour, instructor_no);
            dbf.registerNewSPortBookingIfUnitOfWorkIsInitialized(currentSportBooking);
        } else {
            currentSportBooking = null;
        }
        return currentSportBooking;
    }

    public Field creatingNewField(String sport_name) {
        int newFieldNo = dbf.getNextFieldNo();
        if (newFieldNo != 0) {
            currentField = new Field(newFieldNo, sport_name);
            dbf.registerNewFieldIfUnitOfWorkIsInitialized(currentField);
        } else {
            currentField = null;
        }
        return currentField;
    }

    public Employee creatingNewEmployee(String employee_name, String employee_possition) {
        int newEmployeeNo = dbf.getNextInstructorNo();
        if (newEmployeeNo != 0) {
            currentEmployee = new Employee(newEmployeeNo, employee_name, employee_possition);
            dbf.registerNewInstructorIfUnitOfWorkIsInitialized(currentEmployee);
        } else {
            currentEmployee = null;
        }
        return currentEmployee;

    }

    public Sport creatingNewSport(String sport_name, int min_players, int max_players, int price_instructor) {
        if (sport_name != null) {
            currentSport = new Sport(sport_name, min_players, max_players, price_instructor);
            dbf.registerNewSportIfUnitOfWorkIsInitialized(currentSport);
        } else {
            currentSport = null;
        }
        return currentSport;

    }

    public SportInstructor creatingNewSportInstructorTBLConnection(int instructor_no, String sport_name) {
        int newSequenceNo = dbf.getNextSportInstructorNo();
        if (newSequenceNo != 0) {
            currentSportInstructor = new SportInstructor(newSequenceNo, instructor_no, sport_name);
            dbf.registerNewInstructorOfSportsIfUnitOfWorkIsInitialized(currentSportInstructor);
        } else {
            currentSportInstructor = null;
        }
        return currentSportInstructor;
    }

    public Reservation creatingAReservationOfAClientWhoHadAlreadyBeenInTheHotel(Date client_arrival, Date client_departure, int room_no, String deposit_paid, Date reservation_date, int passport) {
        int newReservationNo = dbf.getReservationNo();
        int currClientNo = dbf.getPassportClientNo(passport);
        reservationNo = dbf.getPassportClientNo(passport);
        if (newReservationNo != 0) {
            currentReservation = new Reservation(newReservationNo, client_arrival, client_departure, currClientNo, room_no, deposit_paid, reservation_date);
            dbf.registerNewReservationIfUnitOfWorkIsInitialized(currentReservation);
        } else {
            currentReservation = null;
        }
        return currentReservation;
    }

    public User creatingNewLogin(String username, String password, int employee_no, boolean access) {
        if (employee_no != 0) {
            currentUser = new User(username, password, employee_no);
            dbf.registerNewLoginIfUnitOfWorkISInitialized(currentUser);
        } else if (access && employee_no == 0) {
            int newClientNo = currectClient.getClient_no();
            currentUser = new User(username, password, newClientNo);
            dbf.registerNewLoginIfUnitOfWorkISInitialized(currentUser);
        } else if (!access && employee_no == 0) {
            int newEmployeeNo = currentEmployee.getEmployee_no();
            currentUser = new User(username, password, newEmployeeNo);
            dbf.registerNewLoginIfUnitOfWorkISInitialized(currentUser);
        }
        return currentUser;
    }

    //Loading
    public Client getParticularClient(int client_no) {
        currectClient = dbf.getParticularClient(client_no);

        return currectClient;
    }

    public Client getParticularClientPrivateInf(int client_no) {
        currectClient = dbf.getParticularClientPrivateInf(client_no);

        return currectClient;
    }

    public Reservation getReservationInfo(int reservation_no) {
        currentReservation = dbf.getReservationInfo(reservation_no);
        return currentReservation;
    }

    public SportBooking getSportBookingInfo(int booking_no) {

        currentSportBooking = dbf.getSportBookingInfo(booking_no);
        return currentSportBooking;
    }

    public Field getFieldInfo(int field_no) {
        currentField = dbf.getFieldInfo(field_no);
        return currentField;
    }

    public Employee getEmployeeInfo(int employee_no) {
        currentEmployee = dbf.getEmployeeInfo(employee_no);
        return currentEmployee;

    }

    public Sport getSportPrice(String sport_name) {
        currentSport = dbf.getSportinstructorPriceFromSportTBL(sport_name);
        return currentSport;
    }

    public SportInstructor getConnectionBetweenEmployeeandSportName(int employee_no) {
        currentSportInstructor = dbf.getInstructorNoAndSportTypeFromSportInstructorTBL(employee_no);
        return currentSportInstructor;
    }

    public User getUserInformation(String username) {
        currentUser = dbf.getUserAccountInformationFromLoginTables(username);
        return currentUser;
    }

    public int getCurrentClientNo() {
        return currectClient.getClient_no();
    }

    public ArrayList<Reservation> getAllReservations() {
        allReservations = dbf.getAllReservations();
        return allReservations;
    }

    public ArrayList<SportInstructor> getAllSportInstructors() {
        allSportInstructors = dbf.getAllInstructorsAndSports();
        return allSportInstructors;
    }

    public ArrayList<Client> getAllClients() {
        allClients = dbf.getAllInformationFromClientTableAndFromClientPrvInfTable();
        return allClients;
    }

    public ArrayList<Room> getFreeRoomsAtDate(String room_type, Date start, Date ending) {
        freeRooms = dbf.getFreeRoomaAtDate(room_type, start, ending);
        return freeRooms;
    }

    public ArrayList<SportBooking> getAllSportBookings() {
        allSportReservations = dbf.getAllInformationFromSportBookingTable();
        return allSportReservations;
    }

    public ArrayList<SportBooking> getAllSportBookingsByClient() {
        allSportReservations = dbf.getAllInformationFroMSportBookingTableByClient(currectClient.getClient_no());
        return allSportReservations;
    }

    public ArrayList<Field> getAllFields() {
        allFields = dbf.getAllFields();
        return allFields;
    }

    public ArrayList<Employee> getAllEmployees() {
        allInstructors = dbf.getAllEmployees();
        return allInstructors;
    }

    public ArrayList<Sport> getAllSports() {
        allSports = dbf.getAllSports();
        return allSports;
    }

    public ArrayList<User> getAllEmployeeAccounts() {
        allEmployeeAccounts = dbf.getAllEmployeeAccounts();
        return allEmployeeAccounts;
    }

    public ArrayList<User> getAllGuestAccounts() {
        allGuestsAccounts = dbf.getAllGuestAccounts();
        return allGuestsAccounts;
    }

    //Update
    public void updateParticularClient(int client_no, String client_name, String client_surname, String client_address) {
        currectClient = dbf.getParticularClient(client_no);
        Client updateClient = new Client(currectClient.getClient_no(), client_name, client_surname, client_address, currectClient.getRepresentative_no());
        dbf.updateParticularClient(updateClient);
    }

    public void updateRepresentativeNo(int passport) {
        int client_no = dbf.getPassportClientNo(passport);
        currectClient = dbf.getParticularClient(client_no);
        Client updateClient = new Client(currectClient.getClient_no(), currectClient.getClient_name(), currectClient.getClient_surname(), currectClient.getClient_address(), reservationNo);
        dbf.updateParticularClient(updateClient);
    }

    public void updateClientPersonalInformation(int client_no, int client_passport, String client_country, int client_phone, String client_email, String client_agency) {
        currectClient = dbf.getParticularClientPrivateInf(client_no);
        Client updatePrivateClient = new Client(currectClient.getClient_no(), client_passport, client_country, client_phone, client_email, client_agency);
        dbf.updateClientPersonalInformation(updatePrivateClient);
    }

    public void updateReservationInformation(int reservation_no, Date client_arrival, Date client_departure, int client_no, int room_no, String deposit_paid, Date reservation_date) {
        currentReservation = dbf.getReservationInfo(reservation_no);
        Reservation updateReservation = new Reservation(currentReservation.getReservation_no(), client_arrival, client_departure, client_no, room_no, deposit_paid, reservation_date);
        dbf.updateReservationInformation(updateReservation);
    }

    public void updateSportBookingInformation(int booking_no, int client_no, int field_no, Date reservation_date, String reservation_hour, int instructor_no) {
        currentSportBooking = dbf.getSportBookingInfo(booking_no);
        SportBooking updateSportBooking = new SportBooking(currentSportBooking.getSportBooking_no(), client_no, field_no, reservation_date, reservation_hour, instructor_no);
        dbf.updateSportBookingInformation(updateSportBooking);
    }

    public void updateFieldInformation(int field_no, String field_name) {
        currentField = dbf.getFieldInfo(field_no);
        Field updateField = new Field(currentField.getField_no(), field_name);
        dbf.updateFieldInformation(updateField);
    }

    public void updateEmployeeInformation(int employee_no, String employee_name, String employee_possition) {
        currentEmployee = dbf.getEmployeeInfo(employee_no);
        Employee updateEmployee = new Employee(currentEmployee.getEmployee_no(), employee_name, employee_possition);
        dbf.updateEmployeeInformation(updateEmployee);
    }

    public void updateInstructorPricefromSportTBL(String sport_name, int min_players, int max_players, int price_instructor) {
        currentSport = dbf.getSportinstructorPriceFromSportTBL(sport_name);
        Sport updateSport = new Sport(currentSport.getSport_name(), min_players, max_players, price_instructor);
        dbf.updateSportInstructorPrice(updateSport);
    }

    public void updateSportInstructorTBL(int sequence_no, int employee_no, String sport_name) {
        currentSportInstructor = dbf.getInstructorNoAndSportTypeFromSportInstructorTBL(sequence_no);
        SportInstructor sportinstructor = new SportInstructor(currentSportInstructor.getSequence_no(), employee_no, sport_name);
        dbf.updateSportInstructorTable(sportinstructor);
    }

    //Delete
    public void deleteReservation(int reservation_no) {
        dbf.deleteReservation(reservation_no);
    }

    public void deleteSportBooking(int booking_no) {
        dbf.deleteSportBooking(booking_no);
    }

    public void deleteSportField(int field_no) {
        dbf.deleteSportFields(field_no);
    }

    public void deleteEmployee(int instructor_no) {
        dbf.deleteInstructor(instructor_no);
    }

    public void deleteActualSportAndInstructorPrice(String sport_name) {
        dbf.deleteActualSportandInstructorPrice(sport_name);
    }

    public void deleteInstructorOfSports(int sequence_no) {
        dbf.deleteInstructorOfSports(sequence_no);
    }

    public void deleteUserAccount(String user_name) {
        dbf.deleteUserAccount(user_name);
    }

    //Locking
    public boolean lockingFieldMapper() {
        return dbf.lockingFieldTable();
    }

    public boolean lockingSportMapper() {
        return dbf.lockingSportTable();
    }

    public boolean lockingEmployeeMapper() {
        return dbf.lockingEmployeeTable();
    }

    public boolean lockingSportInstructorMapper() {
        return dbf.lockingSportInstructorTable();
    }

    public boolean lockingReservationMapper() {
        return dbf.lockingReservationTable();
    }

    public boolean lockingClientMapper() {
        return dbf.lockingClientTable();
    }

    public boolean lockingSportBookingMapper() {
        return dbf.lockingSportBookingTable();
    }

    //Commit methods
    public void startTheProcessOfEditingDB() {
        dbf.initializeUnitOfWorkWithOrderMapper();
    }

    public void endTheProcessOfEditingDB() {
        dbf.startTheProcessOfCommitting();

    }

    public int checkPassportClientNo(int passport) {
        return dbf.getPassportClientNo(passport);
    }

    public String checkPassportName(int passport) {
        return dbf.checkPassport(passport);

    }

    public String checkPassportSurname(int passport) {
        return dbf.getPassportSurname(passport);
    }

    public String checkPassportAgency(int passport) {
        return dbf.getPassportAgency(passport);
    }

    public String checkPassportAddress(int passport) {
        return dbf.getPassportAddress(passport);
    }

    public String checkPassportCountry(int passport) {
        return dbf.getPassportCountry(passport);
    }

    public String checkPassportEmail(int passport) {
        return dbf.getPassportEmail(passport);
    }

    public int checkPassportPhone(int passport) {
        return dbf.getPassportPhone(passport);
    }

    //Passport checking
    public boolean checkIfaParticularPassportNoExistsInTheDataBase(int passport) {
        return dbf.checkIfParticularPassportExistsInClientPrivateInformationTBL(passport);
    }

}
