/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;

import entity.Room;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Desting
 */
public class RoomMapper
{
    private final Connection connect;

    public RoomMapper(Connection con)
    {
        this.connect = con;
    }
    
    public ArrayList<Room> getFreeRoomsAtDate(String room_type , Date start, Date ending) {
        Room room = null;
        ArrayList<Room> freerooms = new ArrayList<>();
        String SQLString1 = "SELECT * FROM ROOM_TBL "
                + "WHERE room_type = ? AND ROOM_TBL.ROOM_NO NOT IN ("
                + "Select ROOM_TBL.ROOM_NO from ROOM_TBL "
                +"join RESERVATION_TBL "
                + "on "
                +"ROOM_TBL.ROOM_NO = RESERVATION_TBL.ROOM_NO "
                + "where client_arrival between ? and ? "
                + "OR client_departure between ? and ? "
                + ") "
                + "ORDER BY ROOM_TBL.ROOM_NO";
                


        PreparedStatement statement = null;
        try {
            statement = connect.prepareStatement(SQLString1);
            statement.setString(1, room_type);
            statement.setDate(2, start);
            statement.setDate(3, ending);
            statement.setDate(4, start);
            statement.setDate(5, ending);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                room = new Room(rs.getInt(1),
                        rs.getString(2));
                freerooms.add(room);

            }
        } catch (SQLException e) {
            System.out.println("getFreeRoomsAtDate(String room_type , Date start, Date ending)");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("getFreeRoomsAtDate(String room_type , Date start, Date ending)");
                System.out.println(e.getMessage());
            }
        }
        
        return freerooms;
    }
}
