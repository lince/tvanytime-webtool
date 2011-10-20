/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tvanytime.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author lince
 */
public class PersistTvAnytime {

    public void persist(String contentR, String progInf, String progLoc) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tvAnytime", "root", "root");
        PreparedStatement pst = conn.prepareStatement("INSERT INTO tvanytime.tvAnyTime_MetaInformation VALUES (DEFAULT,?,?,?,?)");
        java.util.Date d1 = new java.util.Date();
        pst.setDate(1, new java.sql.Date(d1.getTime()));
        pst.setString(2, contentR);
        pst.setString(3, progInf);
        pst.setString(4, progLoc);
        pst.execute();
        conn.close();
    }
}
