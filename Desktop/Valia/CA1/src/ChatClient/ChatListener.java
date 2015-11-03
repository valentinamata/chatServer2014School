/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChatClient;

/**
 *
 * @author Valentina
 */
public interface ChatListener {
     void messageArrived(String data);
     void updateNewClient(String[] users);
}
