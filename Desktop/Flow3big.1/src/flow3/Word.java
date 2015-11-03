/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flow3;

/**
 *
 * @author Valentina
 */
public class Word {
    private String firstWord;
    private String secondWord;

    public Word(String firstWord, String secondWord) {
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }

    @Override
   public String toString() {
        
        return firstWord + "," + secondWord + System.getProperty("line.separator");
       
   }
   
        
        
//    public String toFile1() {
//        String output=firstWord+",";
//       output += secondWord+"\n";
//       return output;
//    }
//    
}