package dataSource;

import entity.Sport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SportMapper {
    
    public boolean sqlLockingSportMapper(Connection DBConnect) {
        int rowsInserted = 0;
        String lock = "LOCK TABLE Sport in exclusive mode";
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
                System.out.println("Fail in sqlLockingSportMapper");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Save
    public boolean saveInformationIntoSportTable(ArrayList<Sport> sportList, Connection DBConnection) {
        int rowsInserted = 0;
        
        String sqlStringInsert
                = "insert into Sport "
                + "values (?,?,?,?)";
        PreparedStatement statement = null;
        
        try {
            
            for (int i = 0; i < sportList.size(); i++) {
                Sport sportSaving = sportList.get(i);
                statement = DBConnection.prepareStatement(sqlStringInsert);
                statement.setString(1, sportSaving.getSport_name());
                statement.setInt(2, sportSaving.getMin_players());
                statement.setInt(3, sportSaving.getMax_players());
                statement.setInt(4, sportSaving.getPrice_instructor());
                
                rowsInserted = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Fail in SportBookingMapper - addNewSportBooking");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in SportBookingMapper - addNewSportBooking");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Load 
    //Single show Sport
    public Sport getInformationFromSportTable(String sport_name, Connection DBConnection) {
        Sport sport = null;
        String SQLString1
                = "select * "
                + "from Sport "
                + "where sport_name = ?";
        
        PreparedStatement statement = null;
        try {
            
            statement = DBConnection.prepareStatement(SQLString1);
            statement.setString(1, sport_name);     // primary key value
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                sport = new Sport(sport_name,
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4));
            }
            
        } catch (SQLException e) {
            System.out.println("Fail in OrderMapper - getReservationInfo");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Fail in OrderMapper - getReservationInfo");
                System.out.println(e.getMessage());
            }
        }
        return sport;
    }

    //All Sport
    public ArrayList<Sport> getAllInformationFromSportTable(Connection DBConnection) {
        Sport sport = null;
        ArrayList<Sport> sportAL = new ArrayList<Sport>();
        String SQLString1 = // get order
                "SELECT * "
                + "FROM Sport ";
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepareStatement(SQLString1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                
                sport = new Sport(rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4));
                sportAL.add(sport);
                
            }
            
        } catch (SQLException e) {
            System.out.println("Fail in OrderMapper - getRoomsfromType");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Fail in OrderMapper - getRoomsfromType");
                System.out.println(e.getMessage());
            }
        }
        return sportAL;
    }

    //Update
    public boolean updateInformationIntoSportTable(ArrayList<Sport> sportList, Connection DBConnection) {
        boolean updated = true;
        int rowsUpdated = 0;
        String SQLString
                = "update Sport "
                + "set min_players = ?, max_players = ?, price = ? "
                + "where sport_name = ?";
        PreparedStatement statement = null;
        try {
            for (int i = 0; i < sportList.size(); i++) {
                Sport sport = sportList.get(i);
                
                statement = DBConnection.prepareStatement(SQLString);
                statement.setString(4, sport.getSport_name());
                statement.setInt(1, sport.getMin_players());
                statement.setInt(2, sport.getMax_players());
                statement.setInt(3, sport.getPrice_instructor());
                rowsUpdated = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("updateInformationIntoSportTable(ArrayList<Sport> sportList)");
            System.out.println(e.getMessage());
            updated = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("updateInformationIntoSportTable(ArrayList<Sport> sportList)");
                System.out.println(e.getMessage());
                updated = false;
            }
        }
        return updated;
    }

    //Delete
    public boolean deleteActualSport(ArrayList<String> sportArray, Connection DBConnection) {
        int rowsDeleted = 0;
        for (int i = 0; i < sportArray.size(); i++) {
            
            Sport sport = getInformationFromSportTable(sportArray.get(i), DBConnection);
            String SQLString1 = "delete from Sport "
                    + "where sport_name = ?";
            PreparedStatement statement = null;
            
            try {
                statement = DBConnection.prepareStatement(SQLString1);
                statement.setString(1, sport.getSport_name());
                
                rowsDeleted = statement.executeUpdate();
                
            } catch (Exception e) {
                System.out.println("Fail in DeleteSportBookingMapper - deleteSportBooking");
                System.out.println(e.getMessage());
            } finally // must close statement
            {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Fail in DeleteSportBookingMapper, closing the connection - deleteSportBooking");
                    System.out.println(e.getMessage());
                }
            }
        }
        return rowsDeleted == sportArray.size();
    }
}
