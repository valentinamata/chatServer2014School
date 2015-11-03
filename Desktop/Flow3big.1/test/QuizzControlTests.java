/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import flow3.QuizzControlInterface;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Valentina
 */
 

public class QuizzControlTests {
    
    QuizzControlInterface tryThis;
    
    public QuizzControlTests() {
    }
    
    @Before
    public void setUp() {
        tryThis = new flow3.Controller();
        tryThis.add("title1");      // trust add()
        tryThis.add("title1");
        tryThis.add("title3");
        
    }
    
    @Test
    public void gameName()
    {
        tryThis.getGameNames();
        
        assertTrue(tryThis.size() == 3);
    }
    
    @Test
    public void select(){
      tryThis.selectGame("title1");
      assertTrue(tryThis.size() == 0);
    }
    
    @Test
    public void selGameNa()
    {
        tryThis.selectGame("title1");
        assertTrue(tryThis.getSelectedGameName().equals("title1"));
    }
    
    @Test
    public void addG()
    {
        tryThis.add("title4");
    
    assertTrue(tryThis.size() == 4);
   
    }
    
    // I would have put a green dance but we don't have time
}