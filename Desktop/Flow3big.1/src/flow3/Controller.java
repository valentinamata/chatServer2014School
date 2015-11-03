/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flow3;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Valentina
 */
public class Controller implements PersonalInterface {
    
    private ArrayList<Word> wordsDatabase;
    private ArrayList<String> firstLineDatabase;
    private ArrayList<String> deleted;
    private FileHandling loadSave;
    private String[] gameTitles;
    int score;
    private String titleFile;
    private String line;
    
    public Controller() {
        wordsDatabase = new ArrayList();
        firstLineDatabase = new ArrayList();
        deleted = new ArrayList();
        loadSave = new FileHandling();
        gameTitles = new String[100];
        score = 0;
        titleFile = "titles.txt";
    }
    
    @Override
    public int size() {
        return firstLineDatabase.size();
    }
    
    @Override
    public int size2() {
        return wordsDatabase.size();
    }
    
    @Override
    public String getRandomQuestion() {
        Word randomWord;
        Random number = new Random();
        
        randomWord = wordsDatabase.get(number.nextInt(wordsDatabase.size()));
        return randomWord.getFirstWord();
        
    }
    
    @Override
    public boolean checkGuess(String question, String quess) {
        for (Word words : wordsDatabase) {
            if (question.equals(words.getFirstWord())) {
                if (quess.equals(words.getSecondWord())) {
                    return true;
                }
            }
        }
        return false;
        
        
    }
    
    @Override
    public String lookup(String question) {
        
        for (int i = 0; i < wordsDatabase.size(); i++) {
            if (wordsDatabase.get(i).getFirstWord().equals(question)) {
                return wordsDatabase.get(i).getSecondWord();
            } else if (wordsDatabase.get(i).getSecondWord().equals(question)) {
                return wordsDatabase.get(i).getFirstWord();
            }
        }
        
        
        return null;
    }
    
    @Override
    public boolean load(String filename) {
        
        wordsDatabase = loadSave.load(filename);
        if (wordsDatabase == null) {
            return false;
        }
        return true;
        
    }
    
    @Override
    public boolean save(String filename) {
        if (loadSave.saveTitle(filename, firstLineDatabase)) {
            return true;
        }
        return false;        
    }
    
    @Override
    public void clear() {
        
        firstLineDatabase = new ArrayList();
    }
    
    @Override
    public String[] getGameNames() {
        return gameTitles;
    }
    
    @Override
    public void selectGame(String gameName) {
        for (int i = 0; i < firstLineDatabase.size(); i++) {
            if (gameName.equals(firstLineDatabase.get(i))) {
                line = firstLineDatabase.get(i);
                clear();
            }
            
        }
    }
    
    @Override
    public String getSelectedGameName() {
        return line;
    }
    
    @Override
    public void addGame(String gameName) {
        
        firstLineDatabase.add(gameName);
        
    }
    
    @Override
    public String[] getGamesTitles(String fileName) {
        gameTitles = loadSave.load_Quizz(fileName);
        return loadSave.load_Quizz(fileName);
    }
    
    @Override
    public int scores() {
        
        return score += 5;
        
    }
    
    @Override
    public boolean loadQuizz(String filename) {
        firstLineDatabase = loadSave.loadQuizz(filename);
        if (firstLineDatabase == null) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public ArrayList<String> getGames() {
        return firstLineDatabase;        
    }
    
    @Override
    public String checkTitle(String title) {
        
        for (int i = 0; i < firstLineDatabase.size(); i++) {
            if (firstLineDatabase.get(i).equals(title)) {
                return firstLineDatabase.get(i);
            }
            
        }
        
        return null;
        
    }
    
    @Override
    public ArrayList<String> delete(String deleteThis) {
        for (int i = 0; i < firstLineDatabase.size(); i++) {
            
            if (firstLineDatabase.get(i).equals(deleteThis)) {
                firstLineDatabase.remove(i);
            }
            
            
        }
        
        
        return firstLineDatabase;
    }
    
    @Override
    public String bestScore() {
        int aux = 0;
        for (int i = 0; i < wordsDatabase.size(); i++) {
            int bestS = Integer.parseInt(lookup(wordsDatabase.get(i).getFirstWord()));
            if (bestS > aux) {
                aux = bestS;
            }
            
        }
        String bestSc = Integer.toString(aux);
        
        return lookup(bestSc) + "-" + bestSc;
    }
    
    @Override
    public void add(String fileName) {
        firstLineDatabase.add(fileName);
    }
}