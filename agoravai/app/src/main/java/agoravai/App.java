/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package agoravai;

import java.sql.Connection;
import util.ConnectionFactory;



public class App {
    

    public static void main(String[] args) {
        
        ConnectionFactory c = ConnectionFactory.getConnection();
        
        ConnectionFactory.closeConnection((Connection) c);
        
    }
}