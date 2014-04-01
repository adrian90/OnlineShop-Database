/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author adria_000
 */
public class Database {
    
    String url = "jdbc:mysql://localhost:3306/AS"; 
    String usuario = "root"; 
    String contraseña = "****";
    Connection con=null;
    public void connect(){
        try {
            con = (Connection) DriverManager.getConnection(url,usuario,contraseña);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection(){
        return con;
    }
}

