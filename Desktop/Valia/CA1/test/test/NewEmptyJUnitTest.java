/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import ChatClient.ChatClient;
import ChatClient.ChatListener;
import ChatServer.Server;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Pranit Anand
 */
public class NewEmptyJUnitTest {
    ChatClient c1 = new ChatClient();
    ChatClient c2 = new ChatClient();
    public NewEmptyJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Server.getInstance().start();
                    
                } catch (IOException ex) {
                    Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    
    @AfterClass
    public static void tearDownClass() {
        Server.stopServer();
    }
    
    @Before
    public void setUp() throws IOException, InterruptedException {
       
    }
    
    private String testResult="";
    private CountDownLatch lock;
    private String name = "Maria";
    
    @Test
    public void connectClientTest() throws IOException, InterruptedException{
        lock= new CountDownLatch(1);
        testResult= "";
        
        ChatClient tester = new ChatClient();
      
      
        tester.connect("localhost", 9090);
      tester.registerChatListener(new ChatListener() {

          @Override
    public void messageArrived(String data) {
              System.out.println(data);
              
    }

    @Override
    public void updateNewClient(String[] users) {
        for (String string : users) {
            testResult+=string+",";
            testResult = testResult.substring(0, testResult.length()-1);
        }
    }
      }) ;
        tester.connectClient("Maria");
        
        Thread.sleep(1000);
        String people = "Maria";
        assertEquals(people, testResult);
        tester.stopClient();
    }
    
    @Test
    public void sendMessaege() throws IOException, InterruptedException{
        lock= new CountDownLatch(1);
        testResult= "";
        ChatClient client = new ChatClient();
        client.connect("localhost", 9090);
        client.connectClient("Maria");
        System.out.println("todayIsFriday");
        client.registerChatListener(new ChatListener(){

            @Override
            public void messageArrived(String data) {
                testResult= data;
                lock.countDown();
            }

            @Override
            public void updateNewClient(String[] users) {
                for (String string : users) {
            System.out.println(string);
            lock.countDown();
        }
            }
        
    });
        client.send("*", "hello");
        Thread.sleep(1000); 
        assertEquals("hello", testResult);
        client.stopClient();
    }
    
    @Rule
  public ExpectedException thrown= ExpectedException.none();
  
    @Test
  public void testStopClient() throws IOException, InterruptedException{
 //   thrown.expect(IOException.class);
//    thrown.expectMessage("Outbound socket is closed");
    ChatClient tester = new ChatClient();
    tester.connect("localhost", 9090);
    tester.connectClient("vala");
    tester.stopClient();
    //Calling send after a stopClient() should throw an IOException
    tester.send("*", "hello");
     
   
  }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
