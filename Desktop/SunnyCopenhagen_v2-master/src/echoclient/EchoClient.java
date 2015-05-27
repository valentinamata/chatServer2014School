package echoclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class EchoClient extends Thread
{

    Socket socket;
    private int port;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    private List<EchoListener> listeners = new ArrayList();
    private String nick;

    public boolean connect(String address, int port) throws UnknownHostException, IOException
    {
        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket();
        try 
        {
            socket.connect(new InetSocketAddress(serverAddress, port), 2000);
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
            return true;
        }
        catch (SocketTimeoutException se)
        {
            return false;
        }
    }

    public void send(String msg)
    {
        output.println(msg);
    }

    public void stopp() throws IOException
    {
        output.println(ProtocolStrings.CLOSE);
    }

    public void run()
    {
        String msg = input.nextLine();
        while (!msg.equals(ProtocolStrings.CLOSE))
        {
            notifyListeners(msg);
            msg = input.nextLine();
            Logger.getLogger(EchoClient.class.getName()).log(Level.INFO, "Client received message :" + msg);

        }
        try
        {notifyListeners(msg);
            stopp();
            socket.close();

        }
        catch (IOException ex)
        {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args)
    {
        int port = 9090;
        String ip = "localhost";
        if (args.length == 2)
        {
            port = Integer.parseInt(args[0]);
            ip = args[1];
        }
        try
        {

            EchoClient tester = new EchoClient();
            EchoListener echoListner = new EchoListener()
            {

                @Override
                public void messageArrived(String data)
                {
                }
            };
            tester.registerEchoListener(echoListner);

            tester.connect(ip, port);
            tester.start();
            tester.send("bye bye Birdy");

            tester.stopp();
            //System.in.read();      
        }
        catch (UnknownHostException ex)
        {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerEchoListener(EchoListener l)
    {
        listeners.add(l);
    }

    public void unRegisterEchoListener(EchoListener l)
    {
        listeners.remove(l);
    }

    private void notifyListeners(String msg)
    {

        for (EchoListener echoListener : listeners)
        {
            echoListener.messageArrived(msg);
        }
    }

    public String getNick()
    {
        return nick;
    }

    public List getNicks()
    {

        List listsOfNicks = new ArrayList();
        return listsOfNicks;
    }

}
