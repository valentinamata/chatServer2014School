/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;

import entity.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Desting
 */
public class ClientMapper {



    //Numbers
    public int getNextClientNo(Connection dBConnection) {
        int nextClientNo = 0;
        String SQLString
                = "select client_no_seq.nextval  "
                + "from dual";
        PreparedStatement statement = null;
        try {
            statement = dBConnection.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextClientNo = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Fail in OrderMapper - getNextOrderNo");
            System.out.println(e.getMessage());
        }
        return nextClientNo;
    }

    public int getCurrentClientNo(Connection dBConnection) {
        int nextClientNo = 0;
        String SQLString
                = "select client_no_seq.currval  "
                + "from dual";
        PreparedStatement statement = null;
        try {
            statement = dBConnection.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextClientNo = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Fail in OrderMapper - getNextOrderNo");
            System.out.println(e.getMessage());
        }
        return nextClientNo;
    }
    
    //SQL Lock
    public boolean sqlLockingClientMapper(Connection DBConnect) {
        int rowsInserted = 0;
        String lock = "LOCK TABLE Client_TBL in exclusive mode";
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

    //Saving information
    public boolean saveInformationIntoClientTable(ArrayList<Client> clientList, Connection DBConnect) {
        int rowsInserted = 0;
        String SQLString
                = "insert into Client_TBL "
                + "values (?,?,?,?,?)";
        PreparedStatement statement = null;
        for (int i = 0; i < clientList.size(); i++) {
            try {
                Client client = clientList.get(i);
                statement = DBConnect.prepareStatement(SQLString);
                statement.setInt(1, client.getClient_no());
                statement.setString(2, client.getClient_name());
                statement.setString(3, client.getClient_surname());
                statement.setString(4, client.getClient_address());
                statement.setInt(5, client.getRepresentative_no());
                rowsInserted += statement.executeUpdate();

            } catch (Exception e) {
                System.out.println("Fail in OrderMapper - saveNewClient HAHAHAAHAHHAHAAH");
                System.out.println(e.getMessage());
            } finally // must close statement
            {
                System.out.println("insertOrders(): " + (rowsInserted == clientList.size()));
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Fail in OrderMapper - saveNewClient");
                    System.out.println(e.getMessage());
                }
            }
        }
        return rowsInserted == clientList.size();
    }

    public boolean saveInformationIntoClientPrivateInformationTable(ArrayList<Client> clientList, Connection dBConnect) {
        int rowsInserted = 0;
        String SQLString
                = "insert into CLIENT_PRIVATEINF_TBL "
                + "values (?,?,?,?,?,?)";
        PreparedStatement statement = null;

        for (int i = 0; i < clientList.size(); i++) {
            try {
                Client client = clientList.get(i);
                statement = dBConnect.prepareStatement(SQLString);
                statement.setInt(1, client.getClient_no());
                statement.setInt(2, client.getClient_passport());
                statement.setString(3, client.getClient_country());
                statement.setInt(4, client.getClient_phone());
                statement.setString(5, client.getClient_email());
                statement.setString(6, client.getClient_agency());
                rowsInserted = statement.executeUpdate();

            } catch (Exception e) {
                System.out.println("Fail in OrderMapper - addPrivateInfo");
                System.out.println(e.getMessage());
            } finally // must close statement
            {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Fail in OrderMapper - addPrivateInfo");
                    System.out.println(e.getMessage());
                }
            }
        }
        return rowsInserted == 1;
    }

    // Loading information
    public Client getInformationFromClientTable(int client_no, Connection dBConnect) { //CHANGEZZZ
        Client client = null;
        String SQLString1 = // get order
                "select * "
                + "from Client_TBL "
                + "where client_no = ?";

        PreparedStatement statement = null;
        try {
            //=== get order
            statement = dBConnect.prepareStatement(SQLString1);
            statement.setInt(1, client_no);     // primary key value
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                client = new Client(client_no,
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5));
            }
        } catch (SQLException e) {
            System.out.println("Fail in OrderMapper - getOrder");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Fail in OrderMapper - getOrder");
                System.out.println(e.getMessage());
            }
        }
        return client;
    }

    public Client getInformationFromClientPrivateInformationTable(int client_no, Connection dBConnect) {
        Client client = null;
        String SQLString1 = // get order
                "select * "
                + "from Client_PrivateInf_TBL "
                + "where client_no = ?";
        PreparedStatement statement = null;
        try {
            //=== get order
            statement = dBConnect.prepareStatement(SQLString1);
            statement.setInt(1, client_no);     // primary key value
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                client = new Client(client_no,
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6));
            }
        } catch (SQLException e) {
            System.out.println("Fail in OrderMapper - getOrder");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Fail in OrderMapper - getOrder");
                System.out.println(e.getMessage());
            }
        }
        return client;
    }

    public ArrayList<Client> getAllClientInformationForAllClients(Connection dBConnect) {
        Client particularClient = null;
        ArrayList<Client> allClientsList = new ArrayList<Client>();
        String SQLString1 = "SELECT basicInfo.CLIENT_NO ,basicInfo.CLIENT_NAME, basicInfo.CLIENT_SURNAME, "
                + "basicInfo.CLIENT_ADDRESS, basicInfo.REPRESENTATIVE_NO ,privateInfo.CLIENT_PASSPORT, "
                + "privateInfo.CLIENT_COUNTRY ,privateInfo.CLIENT_PHONE, "
                + "privateInfo.CLIENT_EMAIL, privateInfo.CLIENT_AGENCY "
                + "FROM CLIENT_TBL basicInfo, CLIENT_PRIVATEINF_TBL privateInfo "
                + "WHERE basicInfo.client_no = privateInfo.client_no";
        PreparedStatement statement = null;

        try {
            statement = dBConnect.prepareStatement(SQLString1);
            //statement.setInt(1, particularClient.getClient_no() );
                //System.out.println("!sasdsdsdssd" + Integer.MAX_VALUE);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                particularClient = new Client(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10));
                allClientsList.add(particularClient);
            }
        } catch (SQLException e) {
            System.out.println("Fail in OrderMapper -getAllClientInformationForAllClients()");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Fail in OrderMapper - getAllClientInformationForAllClients()");
                System.out.println(e.getMessage());
            }

        }
        return allClientsList;
    }

    //Update
    public boolean updateInformationIntoClientTable(ArrayList<Client> clientUpdateList, Connection dBConnect) {
        boolean updated = true;

        String SQLString1
                = "update Client_TBL "
                + "set client_name = ?, client_surname = ?, client_address = ?, representative_no = ?"
                + "where client_no = ?";
        PreparedStatement statement = null;
        try {
            for (int i = 0; i < clientUpdateList.size(); i++) {
                Client client = clientUpdateList.get(i);

                statement = dBConnect.prepareStatement(SQLString1);

                statement.setInt(5, client.getClient_no());
                statement.setString(1, client.getClient_name());
                statement.setString(2, client.getClient_surname());
                statement.setString(3, client.getClient_address());
                statement.setInt(4, client.getRepresentative_no());

                statement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("Fail in OrderMapper - updateClientTBLinfo");
            System.out.println(e.getMessage());
            updated = false;
        } finally // must close statement
        {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in OrderMapper - updateClientTBLinfo");
                System.out.println(e.getMessage());
                updated = false;
            }
        }
        return updated;
    }

    public boolean updateInformationIntoClientPrivateInformationTable(ArrayList<Client> clientUpdateList, Connection dBConnect) {
        int rowsUpdated = 0;
        boolean updated = true;
        String SQLString
                = "update Client_PrivateInf_TBL "
                + "set client_passport=?, client_country=?, client_phone=?, client_email=?, client_agency=? "
                + "where client_no=?";
        PreparedStatement statement = null;
        try {
            for (int i = 0; i < clientUpdateList.size(); i++) {
                Client client = clientUpdateList.get(i);
                statement = dBConnect.prepareStatement(SQLString);
                statement.setInt(1, client.getClient_passport());
                statement.setString(2, client.getClient_country());
                statement.setInt(3, client.getClient_phone());
                statement.setString(4, client.getClient_email());
                statement.setString(5, client.getClient_agency());
                statement.setInt(6, client.getClient_no());
                rowsUpdated = statement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Fail in UpdateOrderMapper - updateClientPersonalInformation");
            System.out.println(e.getMessage());
            updated = false;
        } finally // must close statement
        {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in OrderMapper - updateClientPersonalInformation");
                System.out.println(e.getMessage());
                updated = false;
            }
        }
        return updated;
    }
    
    public String checkPassportNo(int passport, Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        String client_name = null;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client_name = clientList.get(i).getClient_name();
            }
        }
        return client_name;
    }
    
    public String getPassportSurname(int passport,Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        String client_name = null;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client_name = clientList.get(i).getClient_surname();
            }
        }
        return client_name;
    }
    
    public String getPassportAddress(int passport, Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        String client_name = null;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client_name = clientList.get(i).getClient_address();
            }
        }
        return client_name;
    }
    
    public String getPassportAgency(int passport, Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        String client_name = null;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client_name = clientList.get(i).getClient_agency();
            }
        }
        return client_name;
    }
    
    public String getPassportCountry(int passport, Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        String client_name = null;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client_name = clientList.get(i).getClient_country();
            }
        }
        return client_name;
    }
    
    public int getPassportNom(int passport, Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        int client_pass = 0;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client_pass = passport;
            }
        }
        return client_pass;
    }
    
    public int getPassportClientNo(int passport, Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        int client_no = 0;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client_no = clientList.get(i).getClient_no();
            }
        }
        return client_no;
    }
    
    public String getPassportEmail(int passport, Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        String client_mail = null;
        String firstPart = null;
        int j=1;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client_mail = clientList.get(i).getClient_email();
                
            }
        }
        
        return client_mail;
    }
    
    public int getPassportPhone(int passport, Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        int client_phone = 0;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client_phone = clientList.get(i).getClient_phone();
            }
        }
        return client_phone;
    }
    
    public Boolean checkPassportBoolean(int passport, Connection dBConnect) {
        ArrayList <Client> clientList = getAllClientInformationForAllClients(dBConnect);
        boolean client = false;
        for(int i=0; i< clientList.size(); i++){
            if(clientList.get(i).getClient_passport()==passport)
            {
                client = true;
            }
        }
        return client;
    }

}
