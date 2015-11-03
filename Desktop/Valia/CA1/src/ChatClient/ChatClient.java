

package ChatClient;


import ChatClient.ChatListener;
import ChatClient.ChatListener;
import ChatServer.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class ChatClient extends Thread{

  static Socket socket;
  private int port;
  private static InetAddress serverAddress;
  private static Scanner input;  
  private static PrintWriter output;
  static List<ChatListener> listeners = new ArrayList();
  private ArrayList arry;

  public void connect(String address, int port) throws UnknownHostException, IOException {
    this.port = port;
    //serverAddress= this.serverAddress;
    serverAddress = InetAddress.getByName(address);
//String ip="localhost";
    socket = new Socket(serverAddress, port);
    //input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    input = new Scanner (new InputStreamReader(socket.getInputStream()));
    output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
    start();
    
  }

  public void send(String names, String msg) throws IOException {
    if(socket.isOutputShutdown()){
      throw new IOException("Outbound socket is closed");
    }
      System.out.println("whatever");
    output.println(ProtocolStrings.SEND +"#"+ names + "#" +msg);
    
  }

  
  
  public  void stopClient()  {
    output.println(ProtocolStrings.CLOSE+"#");
    //socket.shutdownOutput(); 
  }
  
    public void connectClient(String name)  {
      output.println(ProtocolStrings.CONNECT+"#"+name);
  }
    
    


  public void registerChatListener(ChatListener l) {
    listeners.add(l);
  }

  public static void unRegisterChatListener(ChatListener l) {
    listeners.remove(l);
  }

 private void hadlerProtocol(String msg){
     String[] protoParts = msg.split("#");
     if(protoParts[0].equals(ProtocolStrings.ONLINE)){
         String[] users = protoParts[1].split(",");
         for (ChatListener l : listeners) {
             l.updateNewClient(users);
         }
     }
     else if(protoParts[0].equals(ProtocolStrings.MESSAGE)){
         String message = protoParts[2];
         for (ChatListener l : listeners) {
             l.messageArrived(message);
             System.out.println("wwwwwww");
         }
     }
 }
  
  private void notifyListeners(String[] users) {
    for (ChatListener listener : listeners) {
      listener.updateNewClient(users);
    }
  }
  
  

  @Override
  public void run() {
      String msg = input.nextLine();
      System.out.println("xx':"+msg);
      while (!msg.equals(ProtocolStrings.CLOSE+"#")) {
          System.out.println("xx':"+msg);
          hadlerProtocol(msg);
          msg = input.nextLine();
      }
      try {
          socket.close();
          input.close();
          input = null;
      } catch (IOException ex) {
          Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
      }
  }

 
  
  
  public String receive() throws IOException  {
    String msg = input.nextLine();
    if (msg.equals(ProtocolStrings.CLOSE)) {
      try {
        socket.close();
      } catch (IOException ex) {
        Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return msg;
  }

  
   
}
