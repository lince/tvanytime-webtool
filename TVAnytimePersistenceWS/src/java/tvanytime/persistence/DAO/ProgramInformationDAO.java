/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tvanytime.persistence.DAO;

import br.ufscar.dc.lince.tvanytime.core.ProgramInformationTableResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author lince
 */
public class ProgramInformationDAO extends AbstractDAO{

    /**
     *  Perists a program information table as an xml-string
     *
     * @param   tableName       name of the table, this name is no part of the TvAnytime standard!
     *          progInf       xml-string of the program information table to persist
     * @throws SQLException     database problem
     */
    public void persistProgramInformationTable(String tableName, String progInf) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("INSERT INTO tvanytime.tvAnyTime_ProgramInformationTable VALUES (DEFAULT, DEFAULT, ?, ?)");
        pst.setString(1, tableName);
        pst.setString(2, progInf);
        pst.execute();
        getConnection().close();
    }

    public Collection<ProgramInformationTableResult> getAllProgramInformationTable() throws SQLException {

        Collection result = new ArrayList();
        ProgramInformationTableResult pitr = null;

        PreparedStatement pst = getConnection().prepareStatement("SELECT * FROM tvanytime.tvAnyTime_ProgramInformationTable");
        ResultSet res = pst.executeQuery();

        // The index of the columns of the table start with 1 !!!!
        while (res.next()) {

            pitr = new ProgramInformationTableResult();

            pitr.setId(res.getInt(1));
            pitr.setTime(res.getTimestamp(2).getTime());
            pitr.setTableName(res.getString(3));
            result.add(pitr);
        }
        getConnection().close();
        return result;
    }

    public String getProgramInformationTable(int id) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("SELECT * FROM tvanytime.tvAnyTime_ProgramInformationTable WHERE ID=?");
        pst.setInt(1, id);
        ResultSet res = pst.executeQuery();
        res.first();
        return res.getString(4);
    }

    public void deleteProgramInformationTable(int tableIndex) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("DELETE FROM tvanytime.tvAnyTime_ProgramInformationTable WHERE ID=?");
        pst.setInt(1, tableIndex);
        pst.execute();
    }

    public void updateProgramInformationTable(int index, String tableName, String programInformationTable) throws SQLException {

        PreparedStatement pst = getConnection().prepareStatement("UPDATE tvanytime.tvAnyTime_segmentInformationTable SET tablename=?, segmentInformationTable=? WHERE ID=?");
        pst.setString(1, tableName);
        pst.setString(2, programInformationTable);
        pst.setInt(3, index);
        pst.execute();
        getConnection().close();
    }

}
