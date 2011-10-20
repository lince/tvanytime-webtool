/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tvanytime.persistence.DAO;

import br.ufscar.dc.lince.tvanytime.core.SegmentInformationTableResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author lince
 */
public class SegmentInformationDAO extends AbstractDAO{

    /**
     *  Perists a program information table as an xml-string
     *
     * @param       filename     name of the content the segment information table refers to
     *              segInf       xml-string of the segment information table to persist
     * @throws SQLException     database problem
     */
    public void persistSegmentInformationTable(String filename, String tablename, String segInf) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("INSERT INTO tvanytime.tvAnyTime_SegmentInformationTable VALUES (DEFAULT,DEFAULT,?,?,?)");
        pst.setString(1, filename);
        pst.setString(2, tablename);
        pst.setString(3, segInf);
        pst.execute();
        getConnection().commit();
    }

    public Collection<SegmentInformationTableResult> getAllSegmentInformationTable() throws ClassNotFoundException, SQLException {

        Collection result = new ArrayList();
        SegmentInformationTableResult sitr = null;

        PreparedStatement pst = getConnection().prepareStatement("SELECT * FROM tvanytime.tvAnyTime_SegmentInformationTable");
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

        getConnection().commit();

        return result;
    }

    public String getSegmentInformationTable(int id) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("SELECT * FROM tvanytime.tvAnyTime_SegmentInformationTable WHERE ID=?");
        pst.setInt(1, id);
        ResultSet res = pst.executeQuery();
        res.first();
        return res.getString(5);
    }

     public void deleteSegmentInformationTable(int segmentInformationTableIndex) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("DELETE FROM tvanytime.tvAnyTime_SegmentInformationTable WHERE ID=?");
        pst.setInt(1, segmentInformationTableIndex);
        pst.execute();
    }

     public void updateSegmentInformationTable(int index, String tableName, String segmentInformationTable) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("UPDATE tvanytime.tvAnyTime_segmentInformationTable SET tablename=?, segmentInformationTable=? WHERE ID=?");
        pst.setString(1, tableName);
        pst.setString(2, segmentInformationTable);
        pst.setInt(3, index);
        pst.execute();
        getConnection().commit();
    }

}
