/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;

import entity.SportBooking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Desting
 */
public class SportBookingMapper
{

    //Number
    public int getNextSportBookingNo(Connection dBConnection)
    {
        int nextOrderNumber = 0;
        String SQLString
                = "select sport_booking_no_seq.nextval  "
                + "from dual";
        PreparedStatement statement = null;
        try
        {
            statement = dBConnection.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
            {
                nextOrderNumber = rs.getInt(1);
            }
        } catch (Exception e)
        {
            System.out.println("Fail in SportBookingMapper - getNextSeqNo");
            System.out.println(e.getMessage());
        }
        return nextOrderNumber;
    }
    
    //SQL Lock
    public boolean sqlLockingSportBookingMapper(Connection DBConnect) {
        int rowsInserted = 0;
        String lock = "LOCK TABLE Sport_booking in exclusive mode";
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
                System.out.println("Fail in sqlLockingSportBookingMapper");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Save
    public boolean saveInformationIntoSportBookingTable(ArrayList<SportBooking> sportBookingList, Connection dBConnection)
    {
        int rowsInserted = 0;

        String sqlStringInsert
                = "insert into Sport_booking "
                + "values (?,?,?,?,?,?)";
        PreparedStatement statement = null;

        try
        {

            for (int i = 0; i < sportBookingList.size(); i++)
            {
                SportBooking sportBooking = sportBookingList.get(i);
                statement = dBConnection.prepareStatement(sqlStringInsert);
                statement.setInt(1, sportBooking.getSportBooking_no());
                statement.setInt(2, sportBooking.getClient_no());
                statement.setInt(3, sportBooking.getField_no());
                statement.setDate(4, sportBooking.getSportBooking_Date());
                statement.setString(5, sportBooking.getSportBooking_Hour());
                statement.setInt(6, sportBooking.getInstructor_no());

                rowsInserted = statement.executeUpdate();
            }
        } catch (Exception e)
        {
            System.out.println("Fail in SportBookingMapper - addNewSportBooking");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try
            {
                statement.close();
            } catch (SQLException e)
            {
                System.out.println("Fail in SportBookingMapper - addNewSportBooking");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Load 
    //Single show Single Booking
    public SportBooking getInformationFromSportBookingTable(int booking_no, Connection dBConnection)
    {
        SportBooking sportBooking = null;
        String SQLString1 = // get order
                "select * "
                + "from Sport_booking "
                + "where Booking_no = ?";

        PreparedStatement statement = null;
        try
        {

            statement = dBConnection.prepareStatement(SQLString1);
            statement.setInt(1, booking_no);    
            ResultSet rs = statement.executeQuery();
            if (rs.next())
            {
                sportBooking = new SportBooking(booking_no,
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getInt(6));
            }

        } catch (SQLException e)
        {
            System.out.println("getInformationFromSportBookingTable");
            System.out.println(e.getMessage());
        } finally 
        {
            try
            {
                if (statement != null)
                {
                    statement.close();
                }
            } catch (SQLException e)
            {
                System.out.println("getInformationFromSportBookingTable");
                System.out.println(e.getMessage());
            }
        }
        return sportBooking;
    }
    

    //All SportBookings
    public ArrayList<SportBooking> getAllInformationFromSportReservationTable( Connection dBConnection) 
    {
        SportBooking sportBooking = null;
        ArrayList<SportBooking> sportBookingAL = new ArrayList<SportBooking>();
        String SQLString1 = // get order
                "SELECT * "
                + "FROM Sport_booking ";
        PreparedStatement statement = null;
        try
        {
            statement = dBConnection.prepareStatement(SQLString1);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {

                sportBooking = new SportBooking(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getInt(6));
                sportBookingAL.add(sportBooking);

            }
        } catch (SQLException e)
        {
            System.out.println("Fail in getRoomsfromType");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try
            {
                if (statement != null)
                {
                    statement.close();
                }
            } catch (SQLException e)
            {
                System.out.println("Fail in getRoomsfromType");
                System.out.println(e.getMessage());
            }
        }
        return sportBookingAL;
    }
    
    //All SportBookings
    public ArrayList<SportBooking> getAllInformationFromSportReservationTableByClient(int client_no, Connection dBConnection) 
    {
        SportBooking sportBooking = null;
        ArrayList<SportBooking> sportBookingAL = new ArrayList<SportBooking>();
        String SQLString1 = // get order
                "SELECT * "
                + "FROM Sport_booking "
                + "where client_no = ?";
        PreparedStatement statement = null;
        try
        {
            statement = dBConnection.prepareStatement(SQLString1);
            statement.setInt(1, client_no);   
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {

                sportBooking = new SportBooking(rs.getInt(1),
                        client_no,
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getInt(6));
                sportBookingAL.add(sportBooking);

            }
        } catch (SQLException e)
        {
            System.out.println("Fail in getRoomsfromType");
            System.out.println(e.getMessage());
        } finally 
        {
            try
            {
                if (statement != null)
                {
                    statement.close();
                }
            } catch (SQLException e)
            {
                System.out.println("Fail in getRoomsfromType");
                System.out.println(e.getMessage());
            }
        }
        return sportBookingAL;
    }

    //Update
    public boolean updateInformationIntoSportBookingTable(ArrayList<SportBooking> sportsBookingUpdateList, Connection dBConnection)
    {
        boolean updated = true;
        int rowsUpdated = 0;
        String SQLString
                = "update Sport_booking "
                + "set Client_no = ?, Field_no = ?, Date_reservation = ?, Start_hour = ? , employee_no = ?"
                + "where Booking_no = ?";
        PreparedStatement statement = null;
        try
        {
            for (int i = 0; i < sportsBookingUpdateList.size(); i++)
            {
                SportBooking actualBooking = sportsBookingUpdateList.get(i);

                statement = dBConnection.prepareStatement(SQLString);
                statement.setInt(6, actualBooking.getSportBooking_no());
                statement.setInt(1, actualBooking.getClient_no());
                statement.setInt(2, actualBooking.getField_no());
                statement.setDate(3, actualBooking.getSportBooking_Date());
                statement.setString(4, actualBooking.getSportBooking_Hour());
                statement.setInt(5, actualBooking.getInstructor_no());
                rowsUpdated = statement.executeUpdate();
            }
        } catch (Exception e)
        {
            System.out.println("Fail in updateInformationIntoSportBookingTable");
            System.out.println(e.getMessage());
            updated = false;
        } finally
        {
            try
            {
                statement.close();
            } catch (SQLException e)
            {
                System.out.println("Fail in updateInformationIntoSportBookingTable");
                System.out.println(e.getMessage());
                updated = false;
            }
        }
        return updated;
    }

    //Delete
    public boolean deleteSportBooking(ArrayList<Integer> sportBookingArray, Connection dBConnection)
    {
        int rowsDeleted = 0;
        for (int i = 0; i < sportBookingArray.size(); i++)
        {

            SportBooking sportBooking = getInformationFromSportBookingTable(sportBookingArray.get(i), dBConnection);
            String SQLString1 = "delete from Sport_Booking "
                    + "where booking_no= ?";
            PreparedStatement statement = null;

            try
            {
                statement = dBConnection.prepareStatement(SQLString1);
                statement.setInt(1, sportBooking.getSportBooking_no());

                rowsDeleted = statement.executeUpdate();

            } catch (Exception e)
            {
                System.out.println("Fail in DeleteSportBookingMapper - deleteSportBooking");
                System.out.println(e.getMessage());
            } finally // must close statement
            {
                try
                {
                    statement.close();
                } catch (SQLException e)
                {
                    System.out.println("Fail in DeleteSportBookingMapper, closing the connection - deleteSportBooking");
                    System.out.println(e.getMessage());
                }
            }
        }
        return rowsDeleted == sportBookingArray.size();
    }
}
