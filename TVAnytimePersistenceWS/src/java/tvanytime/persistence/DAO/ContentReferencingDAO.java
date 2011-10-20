/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tvanytime.persistence.DAO;

import br.ufscar.dc.lince.tvanytime.core.ContentReferencingTableResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

/**
 *
 * @author lince
 */
public class ContentReferencingDAO extends AbstractDAO {

    public void persistContentReferencingTable(String name, String contentReferencingTable) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("INSERT INTO tvanytime.tvAnyTime_contentreferencingtable VALUES (DEFAULT,DEFAULT,?,?)");
        pst.setString(1, name);
        pst.setString(2, contentReferencingTable);
        pst.execute();
        getConnection().close();
    }

    public String getContentReferencingTable(int id) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("SELECT * FROM tvanytime.tvAnyTime_ContentReferencingTable WHERE ID=?");
        pst.setInt(1, id);
        ResultSet res = pst.executeQuery();
        res.first();
        return res.getString(4);
    }

    public Collection<ContentReferencingTableResult> getAllContentReferencingTable() throws ClassNotFoundException, SQLException {

        Collection result = new ArrayList();
        ContentReferencingTableResult crtr = null;

        PreparedStatement pst = getConnection().prepareStatement("SELECT * FROM tvanytime.tvAnyTime_ContentReferencingTable");
        ResultSet res = pst.executeQuery();

        // The index of the columns of the table start with 1 !!!!
        while (res.next()) {

            crtr = new ContentReferencingTableResult();

            crtr.setId(res.getInt(1));
            crtr.setTime(res.getTimestamp(2).getTime());
            crtr.setName(res.getString(3));
            result.add(crtr);
        }
        getConnection().close();
        return result;
    }

    /**
     * Deletes the content reference-table with the given id from the datatable
     *
     * @param           id          primary key of the table to delete
     * @throws      SQLException    exception to be thrown, if something does work out
     */
    public void deleteContentReferenceTable(int id) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("DELETE FROM tvanytime.tvAnyTime_ContentReferencingTable WHERE ID=?");
        pst.setInt(1, id);
        pst.execute();

        logger.log(Level.FINEST, "DELETE FROM tvanytime.tvAnyTime_ContentReferencingTable WHERE ID=" + Integer.toString(id));
    }

    public void updateContentReferencingTable(int index, String tableName, String contentReferencingTableXML) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("UPDATE tvanytime.tvAnyTime_contentreferencingtable SET tablename=?, contentReferencingTable=? WHERE ID=?");
        pst.setString(1, tableName);
        pst.setString(2, contentReferencingTableXML);
        pst.setInt(3, index);
        pst.execute();
        getConnection().close();
    }
}
