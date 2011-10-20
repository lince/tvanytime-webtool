package tvanytime.persistence.DAO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import br.ufscar.dc.lince.tvanytime.core.ContentReferencingTableResult;
import br.ufscar.dc.lince.tvanytime.core.ProgramInformationTableResult;
import br.ufscar.dc.lince.tvanytime.core.SegmentInformationTableResult;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author lince
 * @deprecated
 */
public class PersistTvAnytime {

    private Connection conn = null;
    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(PersistTvAnytime.class.getName());

    /**
     * Initialisation of the database connection
     * and of the logging engine
     */
    public PersistTvAnytime() {

        // Set the logger level for this class
        logger.setLevel(Level.ALL);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tvAnytime", "root", "root");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistTvAnytime.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersistTvAnytime.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @deprecate
     */
    public void persist(String contentR, String progInf, String progLoc) throws ClassNotFoundException, SQLException {

        PreparedStatement pst = conn.prepareStatement("INSERT INTO tvanytime.tvAnyTime_MetaInformation VALUES (DEFAULT,?,?,?,?)");
        java.util.Date d1 = new java.util.Date();
        pst.setDate(1, new java.sql.Date(d1.getTime()));
        pst.setString(2, contentR);
        pst.setString(3, progInf);
        pst.setString(4, progLoc);
        pst.execute();
        conn.close();
    }

    /**
     *  Perists a program information table as an xml-string
     *
     * @param   tableName       name of the table, this name is no part of the TvAnytime standard!
     *          progInf       xml-string of the program information table to persist
     * @throws SQLException     database problem
     */
    public void persistProgramInformationTable(String tableName, String progInf) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("INSERT INTO tvanytime.tvAnyTime_ProgramInformationTable VALUES (DEFAULT, DEFAULT, ?, ?)");
        pst.setString(1, tableName);
        pst.setString(2, progInf);
        pst.execute();
        conn.close();
    }

    /**
     *  Perists a program information table as an xml-string
     *
     * @param       filename     name of the content the segment information table refers to
     *              segInf       xml-string of the segment information table to persist
     * @throws SQLException     database problem
     */
    public void persistSegmentInformationTable(String filename, String tablename, String segInf) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("INSERT INTO tvanytime.tvAnyTime_SegmentInformationTable VALUES (DEFAULT,DEFAULT,?,?,?)");
        pst.setString(1, filename);
        pst.setString(2, tablename);
        pst.setString(3, segInf);
        pst.execute();
        conn.close();
    }

    public void persistContentReferencingTable(String name, String contentReferencingTable) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("INSERT INTO tvanytime.tvAnyTime_contentreferencingtable VALUES (DEFAULT,DEFAULT,?,?)");
        pst.setString(1, name);
        pst.setString(2, contentReferencingTable);
        pst.execute();
        conn.close();
    }

    public String getProgramInformationTable(String id) throws ClassNotFoundException, SQLException {

        String strXml = null;

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM tvanytime.tvAnyTime_ProgramInformationTable WHERE ID=1");

        ResultSet res = pst.executeQuery();

        // The index of the columns of the table start with 1 !!!!
        while (res.next()) {
            strXml = res.getString(3);
        }

        conn.close();

        System.out.println(strXml);
        return strXml;
    }

    public String getContentReferencingTable(int id) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM tvanytime.tvAnyTime_ContentReferencingTable WHERE ID=?");
        pst.setInt(1, id);
        ResultSet res = pst.executeQuery();
        res.first();
        return res.getString(4);

    }

    public Collection<ContentReferencingTableResult> getAllContentReferencingTable() throws ClassNotFoundException, SQLException {

        Collection result = new ArrayList();
        ContentReferencingTableResult crtr = null;

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM tvanytime.tvAnyTime_ContentReferencingTable");
        ResultSet res = pst.executeQuery();

        // The index of the columns of the table start with 1 !!!!
        while (res.next()) {

            crtr = new ContentReferencingTableResult();

            crtr.setId(res.getInt(1));
            crtr.setTime(res.getTimestamp(2).getTime());
            crtr.setName(res.getString(3));
            result.add(crtr);
        }

        conn.close();

        return result;
    }

    /**
     * Deletes the content reference-table with the given id from the datatable
     *
     * @param           id          primary key of the table to delete
     * @throws      SQLException    exception to be thrown, if something does work out
     */
    public void deleteContentReferenceTable(int id) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("DELETE FROM tvanytime.tvAnyTime_ContentReferencingTable WHERE ID=?");
        pst.setInt(1, id);
        pst.execute();

        logger.log(Level.FINEST, "DELETE FROM tvanytime.tvAnyTime_ContentReferencingTable WHERE ID=" + Integer.toString(id));
    }

    public Collection<SegmentInformationTableResult> getAllSegmentInformationTable() throws ClassNotFoundException, SQLException {

        Collection result = new ArrayList();
        SegmentInformationTableResult sitr = null;

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM tvanytime.tvAnyTime_SegmentInformationTable");
        ResultSet res = pst.executeQuery();

        // The index of the columns of the table start with 1 !!!!
        while (res.next()) {

            sitr = new SegmentInformationTableResult();

            sitr.setId(res.getInt(1));
            sitr.setTime(res.getTimestamp(2).getTime());
            sitr.setFilename(res.getString(3));
            sitr.setTableName(res.getString(4));
            result.add(sitr);
        }

        conn.close();

        return result;
    }

    public void deleteSegmentInformationTable(int segmentInformationTableIndex) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("DELETE FROM tvanytime.tvAnyTime_SegmentInformationTable WHERE ID=?");
        pst.setInt(1, segmentInformationTableIndex);
        pst.execute();
    }

    public String getSegmentInformationTable(int id) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM tvanytime.tvAnyTime_SegmentInformationTable WHERE ID=?");
        pst.setInt(1, id);
        ResultSet res = pst.executeQuery();
        res.first();
        return res.getString(4);
    }

    public Collection<ProgramInformationTableResult> getAllProgramInformationTable() throws SQLException {

        Collection result = new ArrayList();
        ProgramInformationTableResult pitr = null;

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM tvanytime.tvAnyTime_ProgramInformationTable");
        ResultSet res = pst.executeQuery();

        // The index of the columns of the table start with 1 !!!!
        while (res.next()) {

            pitr = new ProgramInformationTableResult();

            pitr.setId(res.getInt(1));
            pitr.setTime(res.getTimestamp(2).getTime());
            pitr.setTableName(res.getString(3));
            result.add(pitr);
        }
        conn.close();
        return result;
    }

    public void deleteProgramInformationTable(int tableIndex) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("DELETE FROM tvanytime.tvAnyTime_ProgramInformationTable WHERE ID=?");
        pst.setInt(1, tableIndex);
        pst.execute();
    }

    public String getProgramInformationTable(int id) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("SELECT * FROM tvanytime.tvAnyTime_SegmentInformationTable WHERE ID=?");
        pst.setInt(1, id);
        ResultSet res = pst.executeQuery();
        res.first();
        return res.getString(4);

    }

    public void updateContentReferencingTable(int index, String tableName, String contentReferencingTableXML) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("UPDATE tvanytime.tvAnyTime_contentreferencingtable SET tablename=?, contentReferencingTable=? WHERE ID=?");
        pst.setString(1, tableName);
        pst.setString(2, contentReferencingTableXML);
        pst.setInt(3, index);
        pst.execute();
        conn.close();
    }

    public void updateSegmentInformationTable(int index, String tableName, String segmentInformationTable) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("UPDATE tvanytime.tvAnyTime_segmentInformationTable SET tablename=?, segmentInformationTable=? WHERE ID=?");
        pst.setString(1, tableName);
        pst.setString(2, segmentInformationTable);
        pst.setInt(3, index);
        pst.execute();
        conn.close();
    }

    public void updateProgramInformationTable(int index, String tableName, String programInformationTable) throws SQLException {

        PreparedStatement pst = conn.prepareStatement("UPDATE tvanytime.tvAnyTime_segmentInformationTable SET tablename=?, segmentInformationTable=? WHERE ID=?");
        pst.setString(1, tableName);
        pst.setString(2, programInformationTable);
        pst.setInt(3, index);
        pst.execute();
        conn.close();
    }

}
