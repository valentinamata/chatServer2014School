/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flow3;

import java.util.ArrayList;

/**
 *
 * @author Valentina
 */
public interface PersonalInterface extends QuizzControlInterface {
    
    
    //checks a String array to get the name of the chosen title, I used it for a method in the given array, which was never used, but you won't let me delete that one, so this one has to exist too :(
    public String[] getGamesTitles(String fileName);
    
    //adds score to the existing score
    public int scores();
    
    //loads files that look like the title file, with only one word/line
    boolean loadQuizz(String filename);
    
    //gets the arrayLast which contains only one "word/line"
    public ArrayList<String> getGames();
    
    // checks if the title already exists
    public String checkTitle(String title);
    
    //delets from an arrayList a certain chosen variable; 
    public ArrayList<String> delete(String deleteThis);
    
    //gets the best score from the file and returns the name and the score
    public String bestScore();
    
    public int size2();
    
    
    //public void init();
    
//    public void removeLine(String fileName, String line);
//    
//    public String getLine(String question);
    
}
