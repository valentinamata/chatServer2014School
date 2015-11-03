package dataSource;

import entity.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeMapper {
    
     public boolean sqlLockingEmployeeMapper(Connection DBConnect) {
        int rowsInserted = 0;
        String lock = "LOCK TABLE Employee in exclusive mode";
        PreparedStatement statement = null;
        try {
            
            statement = DBConnect.prepareStatement(lock);
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally
        {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in sqlLockingEmployeeMapper");
                System.out.println(e.getMessage());
            }
        }
        return rowsInserted == 1;
    }

    //Number
    public int getNextEmployeeNo(Connection DBConnection) {
        int nextOrderNumber = 0;
        String SQLString
                = "select employee_no_seq.nextval  "
                + "from dual";
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextOrderNumber = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Fail in getNextEmployeeNo - getNextSeqNo");
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return nextOrderNumber;
    }

    //Save
    public boolean saveInformationEmployeeTable(ArrayList<Employee> instructorList, Connection DBConnection) {
        int rowsInserted = 0;
        
        String sqlStringInsert
                = "insert into Employee "
                + "values (?,?,?)";
        PreparedStatement statement = null;
        
        try {
            
            for (int i = 0; i < instructorList.size(); i++) {
                Employee employeeSaving = instructorList.get(i);
                statement = DBConnection.prepareStatement(sqlStringInsert);
                statement.setInt(1, employeeSaving.getEmployee_no());
                statement.setString(2, employeeSaving.getEmployee_name());
                statement.setString(3, employeeSaving.getEmployee_position());
                
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
    //Single show Field
    public Employee getInformationFromEmployeeTable(int instructor_no, Connection DBConnection) {
        Employee instructor = null;
        String SQLString1 = // get order
                "select * "
                + "from Employee "
                + "where employee_no = ?";
        
        PreparedStatement statement = null;
        try {
            
            statement = DBConnection.prepareStatement(SQLString1);
            statement.setInt(1, instructor_no);     // primary key value
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                instructor = new Employee(instructor_no,
                        rs.getString(2),
                        rs.getString(3));
            }
            
        } catch (SQLException e) {
            System.out.println("getInformationFromInstructorTable");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("getInformationFromInstructorTable");
                System.out.println(e.getMessage());
            }
        }
        return instructor;
    }

    //All Field
    public ArrayList<Employee> getAllInformationFromEmployeeTable(Connection DBConnection) {
        Employee instructor = null;
        ArrayList<Employee> instructorAL = new ArrayList<Employee>();
        String SQLString1 = // get order
                "SELECT * "
                + "FROM Employee ";
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepareStatement(SQLString1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                
                instructor = new Employee(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
                instructorAL.add(instructor);
                
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
        return instructorAL;
    }

    //Update
    public boolean updateInformationIntoEmployeeTableWherePossitionIsInstructor(ArrayList<Employee> instructorsUpdateList, Connection DBConnection) {
        boolean updated = true;
        int rowsUpdated = 0;
        String SQLString
                = "update Employee "
                + "set employee_name = ?, employee_position = ?"
                + "where employee_no = ?";
        PreparedStatement statement = null;
        try {
            for (int i = 0; i < instructorsUpdateList.size(); i++) {
                Employee actualInstructor = instructorsUpdateList.get(i);
                
                statement = DBConnection.prepareStatement(SQLString);
                statement.setInt(3, actualInstructor.getEmployee_no());
                statement.setString(1, actualInstructor.getEmployee_name());
                statement.setString(2, actualInstructor.getEmployee_position());
                rowsUpdated = statement.executeUpdate();
            }
        } catch (SQLException e) {
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
    public boolean deleteInstructorFromEmployeeTable(ArrayList<Integer> instructorArray, Connection DBConnection) {
        int rowsDeleted = 0;
        for (int i = 0; i < instructorArray.size(); i++) {
            
            Employee instructor = getInformationFromEmployeeTable(instructorArray.get(i), DBConnection);
            String SQLString1 = "delete from Employee "
                    + "where employee_no = ? ";
            PreparedStatement statement = null;
            
            try {
                statement = DBConnection.prepareStatement(SQLString1);
                statement.setInt(1, instructor.getEmployee_no());
                
                rowsDeleted = statement.executeUpdate();
                
            } catch (SQLException e) {
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
        return rowsDeleted == instructorArray.size();
    }
}
