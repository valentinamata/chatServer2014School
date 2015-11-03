package Tests;

import entity.Reservation;
import dataSource.ReservationMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ReservationMapperTest {
    
   public ReservationMapperTest() {
    }
  
        Connection con;
	private final String id = "SEM2_TEST_GR23";
	private final String pw = "SEM2_TEST_GR23";
        ReservationMapper reserationMapper;
        
    
    @Before
	public void setUp() throws Exception {
		getConnection();
		reserationMapper=new ReservationMapper();
                
	}

    @After
	public void tearDown() throws Exception {
		releaseConnection();
	}
        
        @Test
	public void testGetReservationMatch() {
	
	    Reservation reservation= reserationMapper.getInformationFromReservationTable(10001,con);        
	   assertTrue("GetReservationMatch failed2",reservation.getReservation_no()== 10001);  
	  
	}
    
        
    @Test
	public void testGetReservationNoMatch() {
	{
	    Reservation reservation= reserationMapper.getInformationFromReservationTable(56,con);
	    assertTrue("GetReservationNoMatch failed1", reservation == null);         
	  }
	}
        
    @Test
    public void testSaveReservationTrue() throws ParseException{
      
    DateFormat into = new SimpleDateFormat("DD-MMM-YY");
    ArrayList<Reservation> list = new ArrayList();
    Date df = into.parse("06-MAR-14"); 
    Date df1 = into.parse("10-MAR-14"); 
    Date today = new Date();
    java.sql.Date sqlRes = new java.sql.Date(today.getTime());
    java.sql.Date sqlArrival = new java.sql.Date(df.getTime());
    java.sql.Date sqlDeparture = new java.sql.Date(df1.getTime());
    int reservationNo = reserationMapper.getNextRegistrationNo(con);
    Reservation newReservation = new Reservation(reservationNo, sqlArrival, sqlDeparture, 10001, 5, "Y", sqlRes);
    
      list.add(newReservation);
        boolean blob = reserationMapper.saveInformationIntoReservationTable(list, con);
        
        assertTrue("SaveReservationTrue faild1", blob);
        
        Reservation res = reserationMapper.getInformationFromReservationTable(reservationNo,con);
        assertTrue("SaveReservationTrue faild2", "Y".equals(res.getDeposit()));
        
        
        
    }
    
    @Test
    public void testUpdateReservation() throws ParseException{
        ArrayList<Reservation> listOfReservations = reserationMapper.getAllInformationFromReservationTable(con);
        boolean blob;
        DateFormat into = new SimpleDateFormat("DD-MMM-YY");
        Date today = new Date();
         Date df = into.parse("06-MAR-14"); 
    Date df1 = into.parse("10-MAR-14"); 
    java.sql.Date sqlRes = new java.sql.Date(today.getTime());
    java.sql.Date sqlArrival = new java.sql.Date(df.getTime());
    java.sql.Date sqlDeparture = new java.sql.Date(df1.getTime());
    Reservation reserv ;
            reserv= reserationMapper.getInformationFromReservationTable(10001, con);
        
        Reservation updateRes = new Reservation(reserv.getReservation_no(), sqlArrival, sqlDeparture, 10001, 8, "Y", sqlRes);
        if(!listOfReservations.contains(updateRes))
        {
            listOfReservations.add(updateRes);
        }
        blob = reserationMapper.updateInformationIntoReservationTable(listOfReservations, con);
        assertTrue("testUpdateReservation faild", blob);
        
    }
    
       
    @Test
    public void testDeletingReservation(){
        ArrayList<Integer> listOfReservations = new ArrayList();
        boolean blob;
        Reservation reserv ;
        int reservationNo = reserationMapper.getNextRegistrationNo(con)-1;
            reserv= reserationMapper.getInformationFromReservationTable(reservationNo, con);
        if(!listOfReservations.contains(reserv.getReservation_no()))
        {
           listOfReservations.add(reserv.getReservation_no());
        }
        blob=reserationMapper.deleteReservation(listOfReservations, con);
        
        assertTrue("testDeletingReservation faild", blob);
    }
        
        private void getConnection()
	  {
	    try 
	    { 
	      con = DriverManager.getConnection("jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat", id, pw );  
	    }
	    catch (SQLException e) 
	    {   System.out.println("fail in getConnection() - Did you add your Username and Password");
	        System.out.println(e); }    
	  }
        
        public void releaseConnection()
	  {
	      try{
	          con.close();
	      }
	      catch (Exception e)
	      { System.err.println(e);}
	  }
        
}