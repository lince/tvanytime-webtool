/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tvanytime.persistence.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lince
 */
public abstract class AbstractDAO {

     private Connection connection = null;
    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(AbstractDAO.class.getName());


    static final String database="jdbc:mysql://localhost:3306/tvAnytime";
    static final String username="root";
    static final String password="root";

    /**
     * Initialisation of the database connection
     * and of the logging engine
     */
    public AbstractDAO() {

        // Set the logger level for this class
        logger.setLevel(Level.ALL);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(database, username, password);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistTvAnytime.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersistTvAnytime.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
