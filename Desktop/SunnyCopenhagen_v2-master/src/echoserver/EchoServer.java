package echoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;
import utils.Utils;

public class EchoServer {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private static final Properties properties = Utils.initProperties("server.properties");
    private static List<ClientHandler> clientHandlerList;
    private static ArrayList<String> nameList;
    private static EchoServer instance = null;
    private static boolean working = false;

    public static EchoServer getInstance() {
        if (instance == null) {
            instance = new EchoServer();
        }
        return instance;
    }

    private EchoServer() {

        int port = Integer.parseInt(properties.getProperty("port"));
        String ip = properties.getProperty("serverIp");
        String logFile = properties.getProperty("logFile");
        clientHandlerList = new ArrayList();
        nameList = new ArrayList();

        Utils.setLogFile(logFile, EchoServer.class.getName());

        Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, "Sever started");
        if (working == false) {
            try {
                serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress(ip, port));
                working = true;
                do {
                    Socket socket = serverSocket.accept(); //Important Blocking call
                    Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, "Connected to a client");
                    ClientHandler clientHandler = new ClientHandler(socket, this);
                    clientHandler.start();
                    clientHandlerList.add(clientHandler);

                } while (keepRunning);
            } catch (IOException ex) {
                Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                working = false;
                Utils.closeLogger(EchoServer.class.getName());
            }
        }
    }

    public static void stopServer() {
        keepRunning = false;
    }

    public static void main(String[] args) {
        EchoServer server = EchoServer.getInstance();

    }

    public void removeHandler(ClientHandler ch) {
        nameList.remove(ch.getNickName());
        clientHandlerList.remove(ch);
        updateOnlineList();
    }
    public void removeillegalHandler(ClientHandler ch) {
        nameList.remove(ch.getNickName());
        clientHandlerList.remove(ch);
         }

    public void sendToALL(String msg, ClientHandler ch) {
        if (ch.getNickName() != null) {
            for (ClientHandler clientHandler : clientHandlerList) {
                clientHandler.send(ProtocolStrings.MESSAGE + ch.getNickName() + msg.substring(msg.lastIndexOf("#")));

                Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, msg + " " + ch.getNickName() + " sent to " + clientHandler.getNickName() + " messege :" + msg.substring(msg.lastIndexOf("#") + 1));

            }
        }
        else
        Logger.getLogger(EchoServer.class.getName()).log(Level.WARNING, "no name found");
    }

    public void sendToSomeone(String msg, ClientHandler ch, ArrayList<String> namesForSending) {
        if (ch.getNickName() != null) {
            for (ClientHandler clientHandler : clientHandlerList) {
                clientHandler.getNickName();
                if (namesForSending.contains(clientHandler.getNickName())) {
                    clientHandler.send(ProtocolStrings.MESSAGE + ch.getNickName() + msg.substring(msg.lastIndexOf("#")));

                    Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, msg + " " + ch.getNickName() + " sent to " + clientHandler.getNickName() + " messege :" + msg.substring(msg.lastIndexOf("#")));
                }

            }
        }
        else
        Logger.getLogger(EchoServer.class.getName()).log(Level.WARNING, "no name found");
    }

    public void send(String msg, ClientHandler ch) {
         if (ch.getNickName() != null) {
        for (ClientHandler clientHandler : clientHandlerList) {
            if (clientHandler != ch) {
                clientHandler.send(ProtocolStrings.MESSAGE + ch.getNickName() + msg.toUpperCase());
            }
            
            
        }}else
        Logger.getLogger(EchoServer.class.getName()).log(Level.WARNING, "no name found");
    }

    public void sendOnlineList(String name) {
        String onlineList = ProtocolStrings.ONLINE;

        nameList.add(name);

        for (int i = 0; i < nameList.size(); i++) {
            String user = nameList.get(i);
            if (i == nameList.size() - 1) {
                onlineList += user;
            }
            else {
                onlineList += user + ",";
            }

        }
        Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, name + " has logged in");

        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.send(onlineList);
        }

    }

    public void updateOnlineList() {
        String onlineList = ProtocolStrings.ONLINE;

        for (int i = 0; i < nameList.size(); i++) {
            String user = nameList.get(i);
            if (i == nameList.size() - 1) {
                onlineList += user;
            }
            else {
                onlineList += user + ",";
            }

        }

        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.send(onlineList);
        }

    }

    public void addToJlist() {

    }

    static public ArrayList<String> getNameList() {
        return nameList;
    }
}
