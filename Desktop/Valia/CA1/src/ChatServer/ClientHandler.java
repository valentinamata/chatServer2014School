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
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author mady
 */
public class ClientHandler extends Thread {

    Scanner input;
    PrintWriter output;
    Socket socket;
    ArrayList<String> clientsNames;
    String clientName;
    Server server = Server.getInstance();
    String msgFromCl;

    public String getClientName() { 
        return clientName;
    }

   

    public ClientHandler(Socket socket, Server s) throws IOException {
        this.server=s;
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        this.socket = socket;
        clientsNames= new ArrayList<>();
    }
    
    public void stopClient() throws IOException {
    output.println(ProtocolStrings.CLOSE);
    socket.shutdownOutput(); 
  }
  
    public void outputConnected(String name) throws IOException {
    if(socket.isOutputShutdown()){
      throw new IOException("Outbound socket is closed");
    }
     
    output.println(ProtocolStrings.CONNECT + name+",");
    //needs to work on it
  }
    public void message(String name, String msg) throws IOException {
    if(socket.isOutputShutdown()){
      throw new IOException("Outbound socket is closed");
    }
    output.println(ProtocolStrings.MESSAGE + name + "#" +msg);
    //needs to work on it
  }
    
    
    
//    public ArrayList<String> whoIsOnline(ArrayList clientsNames){
//        if (message.equals(ProtocolStrings.ONLINE)) {
//                clientsNames.add(clientName);
//            }
//    }

    
    public void send(String message){
        System.out.println("send");
        output.println(message);
    }
    public void run() {
        String message = input.nextLine();
        String[] protocolParts;
        Logger.getLogger(Server.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message));
        do{
            protocolParts = message.split("#");
            int counter=1;
           if(!protocolParts[0].equals(ProtocolStrings.CLOSE)){
         System.out.println(protocolParts[0]);
           if (protocolParts[0].equals(ProtocolStrings.CONNECT)){
               
               System.out.println("i5yoi6");
               clientName = protocolParts[1];
               server.addToMap(clientName, this);
               counter++;
           }
           if(protocolParts[0].equals(ProtocolStrings.SEND)){
               
              
               String[] listNames = protocolParts[1].split(",");
               for (int i=0; i<listNames.length; i++)
               System.out.println(listNames[i]);
               msgFromCl=protocolParts[2];
               
               for (int i = 0; i < listNames.length; i++) {
                 server.sendMessage(msgFromCl, clientName, listNames[i]);
               }
               
           }
           
           if((protocolParts[0].equals(ProtocolStrings.SEND))&&
                   (protocolParts[1].equals("*"))){
           server.sendToAll(msgFromCl, clientName);
           }
            Logger.getLogger(Server.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message.toUpperCase()));
            message = input.nextLine();
           }         
        }
        while (!protocolParts[0].equals(ProtocolStrings.CLOSE));
        System.out.println("Closing...");
        output.println(ProtocolStrings.CLOSE + "#") ;//Echo the stop message back to the client for a nice closedown
        System.out.println("name removed top "+clientName);
        server.removeFromMap(clientName);
        System.out.println("name removed below "+clientName);
        try {
            socket.close();
            
            //server.removeFromMap(server.extract());
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Closed a Connection");
    }
}
