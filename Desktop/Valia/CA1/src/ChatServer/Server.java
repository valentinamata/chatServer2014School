/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;
import utils.Utils;

/**
 *
 * @author mady and pranit
 */
public class Server {

    private static boolean keepRunning = true;
    private static ServerSocket server;
    private static final Properties properties = Utils.initializeProperties("server.properties");
    private static ClientHandler clientHandler;
    private static final Map<String, ClientHandler> listConnected = new HashMap<String, ClientHandler>();
    private static String clientName;
    private static BufferedReader in;
    private static String connectMessage;
    //private static Socket socket;
    private static PrintWriter output;
    public static Server instance;

    public Server(){
        
    }
    
public static Server getInstance(){
    if (instance== null){
        instance = new Server();
    }
    return instance;
}
    public String send(String nameFrom, String nameTo, String message) {
        return null;

    }

   public static void stopServer(){
       keepRunning=false;
   }

    public void onlineClients() {
        for (String name : listConnected.keySet()) {
            output.println(ProtocolStrings.ONLINE + name);
        }
    }

    public String outputOCC() {
        String string = null;
        for (String name : listConnected.keySet()) {
            string += name + ",";
        }
        return string;
    }

    public String outputOCC2() {
        String string = null;
        for (String name : listConnected.keySet()) {
            string = name + "\n";
        }
        return string;
    }

    public void addToMap(String name, ClientHandler ch) {
    listConnected.put(name, ch);
        updateUsers();
    }

    private void updateUsers() {
        String msg=ProtocolStrings.ONLINE+"#";
        for (String user: listConnected.keySet())
        {
            msg+=user+",";
        }
        msg=msg.substring(0, msg.length()-1);
        for (ClientHandler h: listConnected.values())
        {
            h.send(msg);
            System.out.println("tryThis");
        }
    }
    
    public void sendToAll(String mess, String sender )
    {
        String msg=ProtocolStrings.MESSAGE+"#"+sender+"#"+mess;
        
            for (String name:listConnected.keySet())
            {
                ClientHandler h = listConnected.get(name);
                 h.send(msg);  
            }
       
    }
   
    
    
    public void sendMessage( String mess, String sender, String reciever) {
        String msg=ProtocolStrings.MESSAGE+"#"+sender+"#"+mess;
           
        if(listConnected.keySet().contains(reciever)){
            System.out.println("testServ");
            ClientHandler h = listConnected.get(reciever);
            h.send(msg);   
        }
        
    }
    public void removeFromMap(String name) {
        System.out.println(name);
        listConnected.remove(name);
        updateUsers();
    }

    /*
    public static void callOutputConnected() throws IOException {
        for (int a = 0; a < listConnected.size(); a++) {
            clientHandler.outputConnected(extract());
        }
    }
*/
    public static String outputNumber()
{
    listConnected.size();
 String result = Integer.toString(listConnected.size());
        return result;
}   
    public static void start() throws IOException {
        new Server().handleNewClients();
        

    }

    private void handleNewClients() throws NumberFormatException {
        // TODO code application logic here
        int port = Integer.parseInt(properties.getProperty("port"));
        String ip = properties.getProperty("serverIp");
        String logFile = properties.getProperty("logFile");
        Utils.setLogFile(logFile, Server.class.getName());
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Server started");

        try {
            server = new ServerSocket();

            server.bind(new InetSocketAddress(ip, port));
            do {
                Socket socket = server.accept(); //accept locks the server until it gets a response, then it sends a socket
                Logger.getLogger(Server.class.getName()).log(Level.INFO, "Connected to a client");

                clientHandler = new ClientHandler(socket, this);
                //addToMap(extract(), clientHandler);

                clientHandler.start();
                //callOutputConnected();
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.closeLogger(Server.class.getName());
    }

    
    
}
