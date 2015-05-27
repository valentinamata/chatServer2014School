package echoserver;

import echoclient.EchoClient;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author Peter
 */
public class ClientHandler extends Thread
{

    Scanner input;
    PrintWriter writer;
    Socket socket;
    EchoServer server;
    String nickName;

    public ClientHandler(Socket socket, EchoServer server) throws IOException
    {
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run()
    {

        String message = input.nextLine(); //IMPORTANT blocking call

        if (!message.contains(ProtocolStrings.CONNECT) || message.equals(ProtocolStrings.CONNECT))
        {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.WARNING, "illegal input");

            message = ProtocolStrings.CLOSE;
            try
        {
            writer.println(ProtocolStrings.CLOSE);//Echo the stop message back to the client for a nice closedown
            server.removeHandler(this);
            socket.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        else if (message.startsWith(ProtocolStrings.CONNECT))

        {
            nickName = message.substring(8);
            server.sendOnlineList(nickName);

        }

//Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message));
        while (!message.equals(ProtocolStrings.CLOSE))
        {
            if (message.startsWith(ProtocolStrings.CONNECT))
            {
            }
            else if (message.startsWith(ProtocolStrings.SEND))
            {

                InputStream is = new ByteArrayInputStream(message.getBytes());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = "";
                String[] words = null;
                try
                {
                    while ((line = br.readLine()) != null)
                    {

                        words = line.split("#");

                    }
                }
                catch (IOException ex)
                {
                    Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (words[1].equals("*"))
                {
                    server.sendToALL(message, this);
                }
                else if (words[1].contains(","))
                {
                    line = "";
                    String names[] = null;
                    is = new ByteArrayInputStream(words[1].getBytes());
                    br = new BufferedReader(new InputStreamReader(is));
                    try
                    {
                        while ((line = br.readLine()) != null)
                        {

                            names = line.split(",");

                        }
                        ArrayList<String> nameListForSending = new ArrayList();
                        for (int i = 0; i < names.length; i++)
                        {
                            nameListForSending.add(names[i]);

                        }
                        server.sendToSomeone(message, this, nameListForSending);

                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                else
                {
                    ArrayList<String> nameListForSending = new ArrayList();
                    nameListForSending.add(words[1]);
                    server.sendToSomeone(message, this, nameListForSending);
                }

            }
            else
            {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.WARNING, "illegal input");

                message = ProtocolStrings.CLOSE;
                break;
            }

            Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message.toUpperCase()));
            message = input.nextLine(); //IMPORTANT blocking call
        }

        try
        {
            writer.println(ProtocolStrings.CLOSE);//Echo the stop message back to the client for a nice closedown
            server.removeHandler(this);
            socket.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, nickName + " has closed a Connection");
    }

    public void send(String msg)
    {
        writer.println(msg);

    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

}
