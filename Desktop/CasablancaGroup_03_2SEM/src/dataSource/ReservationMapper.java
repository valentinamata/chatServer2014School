/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;

import entity.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Desting
 */
public class ReservationMapper {
   

    //Number
    public int getNextRegistrationNo(Connection dBConnection) {
        int nextOrderNumber = 0;
        String SQLString
                = "select reservation_no_seq.nextval  "
                + "from dual";
        PreparedStatement statement = null;
        try {
            statement = dBConnection.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextOrderNumber = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Fail in OrderMapper - getNextOrderNo");
            System.out.println(e.getMessage());
        }
        return nextOrderNumber;
    }
    
     //SQL Lock
    public boolean sqlLockingReservationMapper(Connection DBConnect) {
        int rowsInserted = 0;
        String lock = "LOCK TABLE Reservation_TBL in exclusive mode";
        PreparedStatement statement = null;
        try {

            statement = DBConnect.prepareStatement(lock);
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in sqlLockingFieldMapper");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Save
    public boolean saveInformationIntoReservationTable(ArrayList<Reservation> reservationList, Connection dBConnection) {
        int rowsInserted = 0;
        String sqlStringInsert
                = "insert into Reservation_TBL "
                + "values (?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        
        try {
            
            for (int i = 0; i < reservationList.size(); i++) {
                Reservation reservation = reservationList.get(i);
                statement = dBConnection.prepareStatement(sqlStringInsert);
                statement.setInt(1, reservation.getReservation_no());
                statement.setDate(2, reservation.getClient_arrival());
                statement.setDate(3, reservation.getClient_departure());
                statement.setInt(4, reservation.getClient_no());
                statement.setInt(5, reservation.getRoom_no());
                statement.setString(6, reservation.getDeposit());
                statement.setDate(7, reservation.getResercation_date());
                
                rowsInserted = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Fail in OrderMapper - addNewReservation324");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in OrderMapper - addNewReservation");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Load 
    //Single reservation
    public Reservation getInformationFromReservationTable(int reservation_no, Connection dBConnection) {
        
        Reservation reservation = null;
        String SQLString1 = // get order
                "select * "
                + "from Reservation_TBL "
                + "where reservation_no = ?";
        
        PreparedStatement statement = null;
        try {
            statement = dBConnection.prepareStatement(SQLString1);
            statement.setInt(1, reservation_no);     // primary key value
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                
                reservation = new Reservation(reservation_no,
                        rs.getDate(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getDate(7));
            }
        } catch (SQLException e) {
            System.out.println("getReservationInfo");
            System.out.println(e.getMessage());
        } finally
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("getReservationInfo");
                System.out.println(e.getMessage());
            }
        }
        return reservation;
    }

    //All Reservations
    public ArrayList<Reservation> getAllInformationFromReservationTable(Connection dBConnection) {
        Reservation reservation = null;
        ArrayList<Reservation> reservationsMap = new ArrayList<Reservation>();
        String SQLString1 = // get order
                "SELECT * "
                + "FROM Reservation_TBL ";
        PreparedStatement statement = null;
        try {
            statement = dBConnection.prepareStatement(SQLString1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                
                reservation = new Reservation(rs.getInt(1),
                        rs.getDate(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getDate(7));
                reservationsMap.add(reservation);
                
            }
        } catch (SQLException e) {
            System.out.println("getRoomsfromType");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("getRoomsfromType");
                System.out.println(e.getMessage());
            }
        }
        return reservationsMap;
    }

    //Update
    public boolean updateInformationIntoReservationTable(ArrayList<Reservation> reserveUpdateList, Connection dBConnection) {
        boolean updated = true;
        int rowsUpdated = 0;
        String SQLString
                = "update Reservation_TBL "
                + "set client_arrival=?, client_departure=?, client_no=?, room_no=?, deposit_paid=?, reservation_date=? "
                + "where reservation_no=?";
        PreparedStatement statement = null;
        try {
            for (int i = 0; i < reserveUpdateList.size(); i++) {
                Reservation reserve = reserveUpdateList.get(i);
                
                statement = dBConnection.prepareStatement(SQLString);
                statement.setDate(1, reserve.getClient_arrival());
                statement.setDate(2, reserve.getClient_departure());
                statement.setInt(3, reserve.getClient_no());
                statement.setInt(4, reserve.getRoom_no());
                statement.setString(5, reserve.getDeposit());
                statement.setInt(7, reserve.getReservation_no());
                statement.setDate(6, reserve.getResercation_date());
                rowsUpdated = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Fail in updateReservationInformation");
            System.out.println(e.getMessage());
            updated = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in updateReservationInformation");
                System.out.println(e.getMessage());
                updated = false;
            }
        }
        return updated;
    }

    //Delete
    public boolean deleteReservation(ArrayList<Integer> reservationArray, Connection dBConnection) {
        int rowsDeleted = 0;
        for (int i = 0; i < reservationArray.size(); i++) {
            
            Reservation reservation = getInformationFromReservationTable(reservationArray.get(i), dBConnection);
            String SQLString1 = "delete from Reservation_TBL "
                    + "where reservation_no= ?";
            PreparedStatement statement = null;
            
            try {
                statement = dBConnection.prepareStatement(SQLString1);
                statement.setInt(1, reservation.getReservation_no());
                
                rowsDeleted = statement.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println("Fail in DeleteReservationOrderMapper - deleteReservation");
                System.out.println(e.getMessage());
            } finally // must close statement
            {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Fail in DeleteReservationOrderMapper, closing the connection - deleteReservation");
                    System.out.println(e.getMessage());
                }
            }
        }
        return rowsDeleted == reservationArray.size();
    }
    
}
