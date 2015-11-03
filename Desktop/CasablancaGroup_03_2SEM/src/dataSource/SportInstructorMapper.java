package dataSource;

import entity.SportInstructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SportInstructorMapper {

    //Number
    public int getNextSportInstructorNo(Connection DBConnection) {
        int nextOrderNumber = 0;
        String SQLString
                = "select sport_instructor_no_seq.nextval  "
                + "from dual";
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextOrderNumber = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("getNextSportInstructorNo - getNextSeqNo");
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return nextOrderNumber;
    }

    //SQL Lock
    public boolean sqlLockingSportInstructorMapper(Connection DBConnect) {
        int rowsInserted = 0;
        String lock = "LOCK TABLE Sport_Instructor in exclusive mode";
        PreparedStatement statement = null;
        try {

            statement = DBConnect.prepareStatement(lock);
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in sqlLockingSportInstructorMapper");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Save
    public boolean saveInformationIntoSportInstructorTable(ArrayList<SportInstructor> sportInstructorList, Connection DBFacade) {
        int rowsInserted = 0;

        String sqlStringInsert
                = "insert into Sport_instructor "
                + "values (?,?,?)";
        PreparedStatement statement = null;

        try {

            for (int i = 0; i < sportInstructorList.size(); i++) {
                SportInstructor sportInstructor = sportInstructorList.get(i);
                statement = DBFacade.prepareStatement(sqlStringInsert);
                statement.setInt(1, sportInstructor.getSequence_no());
                statement.setInt(2, sportInstructor.getInstructor_no());
                statement.setString(3, sportInstructor.getSport_name());

                rowsInserted = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("saveInformationIntoSportInstructorTable(ArrayList<SportInstructor> sportInstructorList)");
            System.out.println(e.getMessage());
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("saveInformationIntoSportInstructorTable(ArrayList<SportInstructor> sportInstructorList)");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Load 
    //Single reservation
    public SportInstructor getInformationFromSportInstructorTable(int sequence_no, Connection DBConnection) {

        SportInstructor sportInstructor = null;
        String SQLString1 = // get order
                "select * "
                + "from Sport_instructor "
                + "where sequence_no = ?";

        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepareStatement(SQLString1);
            statement.setInt(1, sequence_no);     // primary key value
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                sportInstructor = new SportInstructor(sequence_no,
                        rs.getInt(2),
                        rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("SportInstructor getInformationFromReservationTable(int employee_no)");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("SportInstructor getInformationFromReservationTable(int employee_no)");
                System.out.println(e.getMessage());
            }
        }
        return sportInstructor;
    }

    //All Reservations
    public ArrayList<SportInstructor> getAllInformationFromSportInstructorTable(Connection DBConnection) {
        SportInstructor sportInstuctor = null;
        ArrayList<SportInstructor> instructorsOfSports = new ArrayList<SportInstructor>();
        String SQLString1
                = "SELECT * "
                + "FROM Sport_instructor ";
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepareStatement(SQLString1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                sportInstuctor = new SportInstructor(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3));
                instructorsOfSports.add(sportInstuctor);

            }
        } catch (SQLException e) {
            System.out.println("getAllInformationFromSportInstructorTable()");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("getAllInformationFromSportInstructorTable()");
                System.out.println(e.getMessage());
            }
        }
        return instructorsOfSports;
    }
    
    //Update
    public boolean updateInformationIntoFieldTable(ArrayList<SportInstructor> sportInstructorUpdateList, Connection DBConnection) {
        boolean updated = true;
        int rowsUpdated = 0;
        String SQLString
                = "update Sport_instructor "
                + "set employee_no = ?, sport_name = ? "
                + "where sequence_no = ?";
        PreparedStatement statement = null;
        try {
            for (int i = 0; i < sportInstructorUpdateList.size(); i++) {
                SportInstructor actualSportInstructor = sportInstructorUpdateList.get(i);

                statement = DBConnection.prepareStatement(SQLString);
                statement.setInt(3, actualSportInstructor.getSequence_no());
                statement.setInt(1, actualSportInstructor.getInstructor_no());
                statement.setString(2, actualSportInstructor.getSport_name());
                rowsUpdated = statement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Fail in updateInformationIntoFieldTable");
            System.out.println(e.getMessage());
            updated = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in updateInformationIntoFieldTable");
                System.out.println(e.getMessage());
                updated = false;
            }
        }
        return updated;
    }

    //Delete
    public boolean deleteInstructorAndSport(ArrayList<Integer> instructorNoArray, Connection DBConnection) {
        int rowsDeleted = 0;
        for (int i = 0; i < instructorNoArray.size(); i++) {
            SportInstructor sportInstructor = getInformationFromSportInstructorTable(instructorNoArray.get(i), DBConnection);
            String SQLString1 = "delete from Sport_instructor "
                    + "where sequence_no = ? ";
            PreparedStatement statement = null;
            try {
                statement = DBConnection.prepareStatement(SQLString1);
                statement.setInt(1, sportInstructor.getSequence_no());
                rowsDeleted = statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("deleteInstructorAndSport");
                System.out.println(e.getMessage());
            } finally // must close statement
            {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("deleteInstructorAndSport");
                    System.out.println(e.getMessage());
                }
            }
        }
        return rowsDeleted == instructorNoArray.size();
    }
}
