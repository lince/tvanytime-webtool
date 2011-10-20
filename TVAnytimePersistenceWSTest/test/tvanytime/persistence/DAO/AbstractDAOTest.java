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
 * @author Lince
 */
public class AbstractDAOTest {

    static final String database="jdbc:mysql://localhost:3306/tvAnytime";
    static final String username="root";
    static final String password="root";

    protected Connection connection=null;

    public AbstractDAOTest(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(database, username, password);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SegmentInformationDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SegmentInformationDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
