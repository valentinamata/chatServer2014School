/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flow3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentina
 */
//String[] gameStringArray;
public class FileHandling {
    /**
     *
     */
    
  
     public ArrayList<Word> load(String filename) {
       Scanner imput = null;
       Word word;
      ArrayList<Word> wordArray=new ArrayList();
      
        try {
            imput = new Scanner (new File(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
           Scanner scan;
           scan = new Scanner(imput.nextLine());
        while(imput.hasNextLine())
        {
            scan = new Scanner(imput.nextLine());
            //System.out.println("lo");
            //System.out.println(" INside while loop printing imput.nextline" + imput.nextLine());
            scan.useDelimiter(",");
           
            if (scan.hasNext()) {
                String firstWord = scan.next();
                //System.out.println(firstWord);
                String secondWord=scan.next();
                //System.out.println(secondWord);
                word = new Word(firstWord, secondWord);
                wordArray.add(word);
                //System.out.println(students);
                
            }
        }
        imput.close();
        return wordArray;
    
        
    }
     //don't use
     //loas methid for the gameNames File.
     public String[] load_Quizz(String filename) {
        Scanner imput = null;
        String line = null;
        ArrayList<String> gameArray = new ArrayList();
        String[] gameStringArray;
        gameStringArray = new String[gameArray.size()];

        try {
            imput = new Scanner(new File(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        while (imput.hasNextLine()) {


            Scanner scan = new Scanner(imput.nextLine());


            if (scan.hasNext()) {

                line = scan.next();

            }
            gameArray.add(line);
        }
        imput.close();
         for (int i = 0; i < gameArray.size(); i++) {
             for (int j = 0; j < gameStringArray.length; j++) {
                 gameStringArray[j] = gameArray.get(i);
             }
         }
        return gameStringArray;


    }
     
     public ArrayList<String> loadQuizz(String filename) {
        Scanner imput = null;
        String line = null;
        ArrayList<String> gameArray = new ArrayList();

        try {
            imput = new Scanner(new File(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        while (imput.hasNextLine()) {


            Scanner scan = new Scanner(imput.nextLine());


            if (scan.hasNext()) {

                line = scan.next();

            }
            gameArray.add(line);
        }
        imput.close();
        return gameArray;


    }
     
    
    
     
     public boolean saveTitle(String filename, ArrayList<String> titleStringArray ) {
        FileWriter output;
        
        try
        {
        output = new FileWriter(new File(filename));
        
        for (int i = 0; i < titleStringArray.size(); i++) 
        {
            
            String wordForm = titleStringArray.get(i);
            //System.out.println(wordsDatabase.toString());
            output.write(   wordForm+System.getProperty("line.separator")); 
        }
        output.close();
    }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            ex.printStackTrace();
            return false;
        }
        
            return true;
        
    }
     
     
     
}
