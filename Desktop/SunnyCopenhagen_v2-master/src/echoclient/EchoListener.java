/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package echoclient;

/**
 *
 * @author Peter
 */
public interface EchoListener {
    
    void messageArrived(String data);
//    void newUsers(List<String> users);
    
}
