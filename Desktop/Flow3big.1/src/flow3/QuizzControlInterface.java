/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flow3;

public interface QuizzControlInterface extends WordPairControlInterface
{
// rev chu 18-04-2013
// rev pelo 25-11-2013


/**
* Pre: 
* Post: 
* Returns a list of names of selectable games 
*/
String[] getGameNames();


/**
* Pre: The name passed corresponds to a selectable game
* Post: The existing collection of word pairs is cleared.
*/
void selectGame(String gameName);


/**
* Pre: 
* Post: 
* Returns the name of the game presently selected. If no game is selected it returns null.
*/
String getSelectedGameName();


  /**
   * Pre: Post: A new game is added to the existing collection of games
   */
void addGame(String gameName);



}
