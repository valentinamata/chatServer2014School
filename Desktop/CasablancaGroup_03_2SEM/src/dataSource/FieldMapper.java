package dataSource;

import entity.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FieldMapper {

    //Number
    public int getNextFieldNo(Connection DBConnection) {
        int nextOrderNumber = 0;
        String SQLString
                = "select field_no_seq.nextval  "
                + "from dual";
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextOrderNumber = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Fail in SportBookingMapper - getNextSeqNo");
            System.out.println(e.getMessage());
        }
        return nextOrderNumber;
    }
    //SQL Lock
    public boolean sqlLockingFieldMapper(Connection DBConnect) {
        int rowsInserted = 0;
        String lock = "LOCK TABLE Field in exclusive mode";
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
    public boolean saveInformationIntoFieldTable(ArrayList<Field> fieldMarshallList, Connection DBConnect) {
        int rowsInserted = 0;

        String sqlStringInsert
                = "insert into Field "
                + "values (?,?)";
        PreparedStatement statement = null;

        try {

            for (int i = 0; i < fieldMarshallList.size(); i++) {
                Field fieldSaving = fieldMarshallList.get(i);
                statement = DBConnect.prepareStatement(sqlStringInsert);
                statement.setInt(1, fieldSaving.getField_no());
                statement.setString(2, fieldSaving.getSport_name());

                rowsInserted = statement.executeUpdate();
            }
        } catch (Exception e) {
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
    //Single show Field
    public Field getInformationFromFieldTable(int field_no, Connection DBConnection) {
        Field fieldBooking = null;
        String SQLString1 = // get order
                "select * "
                + "from Field "
                + "where field_no = ?";

        PreparedStatement statement = null;
        try {

            statement = DBConnection.prepareStatement(SQLString1);
            statement.setInt(1, field_no);     // primary key value
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                fieldBooking = new Field(field_no,
                        rs.getString(2));
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
        return fieldBooking;
    }

    //All Field
    public ArrayList<Field> getAllInformationFromFieldTable(Connection DBConnection) {
        Field field = null;
        ArrayList<Field> fieldAL = new ArrayList<Field>();
        String SQLString1 = // get order
                "SELECT * "
                + "FROM Field ";
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepareStatement(SQLString1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                field = new Field(rs.getInt(1),
                        rs.getString(2));
                fieldAL.add(field);

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
        return fieldAL;
    }

    //Update
    public boolean updateInformationIntoFieldTable(ArrayList<Field> fieldUpdateList, Connection DBConnection) {
        boolean updated = true;
        int rowsUpdated = 0;
        String SQLString
                = "update Field "
                + "set sport_name = ? "
                + "where field_no = ?";
        PreparedStatement statement = null;
        try {
            for (int i = 0; i < fieldUpdateList.size(); i++) {
                Field actualField = fieldUpdateList.get(i);

                statement = DBConnection.prepareStatement(SQLString);
                statement.setInt(2, actualField.getField_no());
                statement.setString(1, actualField.getSport_name());
                rowsUpdated = statement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Fail in updateInformationIntoFieldTable(ArrayList<Field> fieldUpdateList)");
            System.out.println(e.getMessage());
            updated = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in updateInformationIntoFieldTable(ArrayList<Field> fieldUpdateList)");
                System.out.println(e.getMessage());
                updated = false;
            }
        }
        return updated;
    }

    //Delete
    public boolean deleteSportFacilityField(ArrayList<Integer> fieldArray, Connection DBConnection) {
        int rowsDeleted = 0;
        for (int i = 0; i < fieldArray.size(); i++) {

            Field field = getInformationFromFieldTable(fieldArray.get(i), DBConnection);
            String SQLString1 = "delete from Field "
                    + "where field_no = ? ";
            PreparedStatement statement = null;

            try {
                statement = DBConnection.prepareStatement(SQLString1);
                statement.setInt(1, field.getField_no());

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
        return rowsDeleted == fieldArray.size();
    }
}
