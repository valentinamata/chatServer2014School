/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import echoclient.EchoClient;
import echoclient.EchoListener;
import echoserver.EchoServer;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import shared.ProtocolStrings;

/**
 * @author Lars Mortensen
 */
public class TestClient
{

    String msg;

    public TestClient()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                EchoServer.main(null);
            }
        }).start();
    }

// comment: our HTTPServer & ChatServer keep running, so we don't stop the server.
//    @AfterClass
//    public static void tearDownClass()
//    {
//        EchoServer.stopServer();
//    }
    @Before
    public void setUp()
    {
    }

    @Test
    public void sendConnectProtocol() throws IOException, InterruptedException
    {

        EchoClient client = new EchoClient();
        EchoListener listener = new EchoListener()
        {

            @Override
            public void messageArrived(String data)
            {
                msg = data;
            }
        };
        client.registerEchoListener(listener);

        client.connect("137.135.176.204", 9999);
        client.start();

        client.send(ProtocolStrings.CONNECT + "TestUser");
        Thread.sleep(500);
        Assert.assertTrue(msg.startsWith(ProtocolStrings.ONLINE));
        Thread.sleep(50);

        client.send(ProtocolStrings.CLOSE);
        Thread.sleep(500);
    }

    @Test
    public void sendSendProtocol() throws IOException, InterruptedException
    {

        EchoClient client = new EchoClient();
        EchoListener listener = new EchoListener()
        {

            @Override
            public void messageArrived(String data)
            {
                msg = data;
            }
        };
        client.registerEchoListener(listener);

        client.connect("137.135.176.204", 9999);
        client.start();
        client.send(ProtocolStrings.CONNECT + "TestUser");
        Thread.sleep(100);

        client.send(ProtocolStrings.SEND + "*#bye");
        Thread.sleep(100);
        assertEquals("MESSAGE#TestUser#bye", msg);
        client.send(ProtocolStrings.CLOSE);
        Thread.sleep(1000);
    }

    @Test
    public void sendWithoutProtocol() throws IOException, InterruptedException
    {

        EchoClient client = new EchoClient();
        EchoListener listener = new EchoListener()
        {

            @Override
            public void messageArrived(String data)
            {
                msg = data;
            }
        };
        client.registerEchoListener(listener);

        client.connect("137.135.176.204", 9999);
        client.start();
        client.send("blablabla");
        Thread.sleep(1000);
        assertEquals(ProtocolStrings.CLOSE, msg);
    }

    @Test
    public void sendCloseProtocol() throws IOException, InterruptedException
    {

        EchoClient client = new EchoClient();
        EchoListener listener = new EchoListener()
        {

            @Override
            public void messageArrived(String data)
            {
                msg = data;
            }
        };
        client.registerEchoListener(listener);

        client.connect("137.135.176.204", 9999);
        client.start();
        client.send(ProtocolStrings.CONNECT + "TestUser");
        Thread.sleep(100);

        client.send(ProtocolStrings.CLOSE);

        Thread.sleep(100);
        assertEquals("CLOSE#", msg);
        Thread.sleep(500);

    }

    @Test
    public void sendOnlyProtocol() throws IOException, InterruptedException
    {

        EchoClient client = new EchoClient();
        EchoListener listener = new EchoListener()
        {

            @Override
            public void messageArrived(String data)
            {
                msg = data;
            }
        };

        client.registerEchoListener(listener);

        client.connect("137.135.176.204", 9999);
        client.start();
        client.send(ProtocolStrings.CONNECT);
        Thread.sleep(1000);
        assertEquals(ProtocolStrings.CLOSE, msg);

    }
}
