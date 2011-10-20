/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tvanytime.persistence.ws;

import br.ufscar.dc.lince.tvanytime.core.ContentReferencingTableResult;
import br.ufscar.dc.lince.tvanytime.core.ProgramInformationTableResult;
import br.ufscar.dc.lince.tvanytime.core.SegmentInformationTableResult;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import tvanytime.persistence.DAO.ContentReferencingDAO;
import tvanytime.persistence.DAO.PersistTvAnytime;
import tvanytime.persistence.DAO.ProgramInformationDAO;
import tvanytime.persistence.DAO.SegmentInformationDAO;

/**
 *
 * @author lince
 */
@WebService()
public class TVAnytimePersistence {

    /**
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(TVAnytimePersistence.class.getName());

    /**
     * Operação de serviço web
     * @deprecated
     */
    @WebMethod(operationName = "persistTvAnytimeMetaData")
    public String persisteTvAnytimeMetaData(@WebParam(name = "contentRef") String contentRef, @WebParam(name = "programInf") String programInf, @WebParam(name = "programLoc") String programLoc) {


        PersistTvAnytime per = new PersistTvAnytime();
        try {
            per.persist(contentRef, programInf, programLoc);
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return "0";
    }

    /***************************************************************************
     *
     *
     * Webservice operations for all data threatment of the Content Reference Table
     *
     *
     **************************************************************************/
    /**
     * Webservice operation to persist a Content Referencing Table
     *
     * @param       name        name of the Content Referencing Table,
     *                          this name is not part of the TVAnytime-standard,
     *                          it was implemented for better usability
     * @param       contentReferencingTableXML
     *                          the XML-interpretation of an contentReferencingTable object
     * @return      boolean     true if successful, otherwise false
     */
    @WebMethod(operationName = "persistContentReferencingTable")
    public boolean persistContentReferencingTable(@WebParam(name = "name") String name, @WebParam(name = "contentReferencingTableXML") String contentReferencingTableXML) {

        boolean result=false;

        try {
            ContentReferencingDAO cont=new ContentReferencingDAO();
            cont.persistContentReferencingTable(name, contentReferencingTableXML);
            result=true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            result=false;
        }

        return result;
    }

    /**
     * Webservice operation to update a Content Referencing Table
     *
     * @param       id          id of the table to update = key
     *
     * @param       name        name of the Content Referencing Table,
     *                          this name is not part of the TVAnytime-standard,
     *                          it was implemented for better usability
     *       
     * @param       contentReferencingTable  the XML-interpretation of an contentReferencingTable object
     * @return      boolean     true if successful, otherwise false
     */
    @WebMethod(operationName = "updateContentReferencingTable")
    public boolean updateContentReferencingTable(@WebParam(name = "index") int index, @WebParam(name = "tableName") String tableName, @WebParam(name = "contentReferencingTable") String contentReferencingTable) {

        boolean result=false;

        try {
            ContentReferencingDAO contentReferencingDAO=new ContentReferencingDAO();
            contentReferencingDAO.updateContentReferencingTable(index, tableName, contentReferencingTable);
            result=true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            result=false;
        }
        return result;
    }

    /**
     * Webservice operation to retrieve all the Content Referencing Tables of the database
     *
     * @return      Collection of ContentReferencingTableResult-objects if no exception was thrown,
     *              returns null, if an exception was thrown
     */
    @WebMethod(operationName = "getAllContentReferencingTable")
    public Collection<ContentReferencingTableResult> getAllContentReferencingTable() {

        Collection result = null;

        try {
            ContentReferencingDAO contentReferencingDAO=new ContentReferencingDAO();
            result=contentReferencingDAO.getAllContentReferencingTable();
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Webservice operation to retrieve a table with a specific ID from the database
     *
     * @param       id      id-number of the table, this is the primary-key in the table
     * @return              xml-string of the table, if there has been an error,
     *                      it returns an empty string
     */
    @WebMethod(operationName = "getContentReferencingTable")
    public String getContentReferencingTable(@WebParam(name = "id") int id) {

        String result = "";

        try {
            ContentReferencingDAO contentReferencingDAO=new ContentReferencingDAO();
            result=contentReferencingDAO.getContentReferencingTable(id);
        } catch (SQLException ex) {
            Logger.getLogger(TVAnytimePersistence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Webservice operation to delete one Content Reference Table from the database
     *
     * @param       contentReferenceTableIndex              primary key as integer of the table to delete
     * @return      true if successfully otherwise false
     */
    @WebMethod(operationName = "deleteContentReferenceTable")
    public boolean deleteContentReferenceTable(@WebParam(name = "contentReferenceTableIndex") int contentReferenceTableIndex) {
        try {
            ContentReferencingDAO contentReferencingDAO=new ContentReferencingDAO();
            contentReferencingDAO.deleteContentReferenceTable(contentReferenceTableIndex);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /***************************************************************************
     * 
     * 
     * Webservice operations for all data threatment of the Segment Information Table
     * 
     * 
     **************************************************************************/
    /**
     * Webservice operation to persist a Segment Information Table
     *
     * @param       segmentInformationTable
     *                          the XML-interpretation of an contentReferencingTable object
     * @return      boolean     true if persisting the table was succesfull, otherwhise false
     */
    @WebMethod(operationName = "persistSegmentInformationTable")
    public boolean persistSegmentInformationTable(@WebParam(name = "filename") String filename, @WebParam(name = "tableName") String tableName, @WebParam(name = "segmentInformationTableXML") String segmentInformationTable) {

        boolean result = false;

        try {
            SegmentInformationDAO segmentInformationDAO=new SegmentInformationDAO();
            segmentInformationDAO.persistSegmentInformationTable(filename, tableName, segmentInformationTable);
            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Webservice operation to update a Segment Information Table
     *
     * @param       id          id of the table to update = key
     *
     * @param       name        name of the Content Referencing Table,
     *                          this name is not part of the TVAnytime-standard,
     *                          it was implemented for better usability
     *
     * @param       segmentInformationTable  the XML-interpretation of an contentReferencingTable object
     * @return      boolean     true if successful, otherwise false
     */
    @WebMethod(operationName = "updateSegmentInformationTable")
    public boolean updateSegmentInformationTable(@WebParam(name = "index") int index, @WebParam(name = "tableName") String tableName, @WebParam(name = "SegmentInformationTable") String segmentInformationTable) {
        boolean result=false;
        try {
            SegmentInformationDAO segmentInformationDAO=new SegmentInformationDAO();
            segmentInformationDAO.updateSegmentInformationTable(index, tableName, segmentInformationTable);
            result=true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            result=false;
        }
        return result;
    }

    /**
     * Webservice operation to retrieve all the Segment Information Tables of the database
     *
     * @return      Collection of SegmentInformationTableResult-objects if no exception was thrown,
     *              returns null, if an exception was thrown
     */
    @WebMethod(operationName = "getAllSegmentInformationTable")
    public Collection<SegmentInformationTableResult> getAllSegmentInformationTable() {

        Collection result = null;
        try {
            SegmentInformationDAO segmentInformationDAO=new SegmentInformationDAO();
            result = segmentInformationDAO.getAllSegmentInformationTable();
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Webservice operation to delete one Segment Information Table from the database
     *
     * @param       segmentInformationTableIndex              primary key as integer of the table to delete
     * @return      true if successfully otherwise false
     */
    @WebMethod(operationName = "deleteSegmentInformationTable")
    public boolean deleteSegmentInformationTable(@WebParam(name = "segmentInformationTableIndex") int segmentInformationTableIndex) {

        boolean result = false;

        try {
            SegmentInformationDAO segmentInformationDAO=new SegmentInformationDAO();
            segmentInformationDAO.deleteSegmentInformationTable(segmentInformationTableIndex);
            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            result = false;
        }

        return result;
    }

    /**
     * Webservice operation to retrieve a table with a specific ID from the database
     *
     * @param       id      id-number of the table, this is the primary-key in the table
     * @return              xml-string of the table, if there has been an error,
     *                      it returns an empty string
     */
    @WebMethod(operationName = "getSegmentInformationTable")
    public String getSegmentInformationTable(@WebParam(name = "id") int id) {

        String result = "";

        try {
            SegmentInformationDAO segmentInformationDAO=new SegmentInformationDAO();
            result = segmentInformationDAO.getSegmentInformationTable(id);
        } catch (SQLException ex) {
            Logger.getLogger(TVAnytimePersistence.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    
    /***************************************************************************
     * 
     * 
     * Webservice operations for all data threatment of the Program Information Table
     * 
     * 
     **************************************************************************/

    /**
     * Webservice operation persist a Program Information Table in database
     *
     * @param       tableName   This name is no part of the TvAnytime standard,
     *                          It got implemented for better usability
     *              programInformationTableXML      xml-string of the table
     * @return      boolean    true, if succesfull, otherwise false
     */
    @WebMethod(operationName = "persistProgramInformationTable")
    public boolean persistProgramInformationTable(@WebParam(name = "tableName") String tableName, @WebParam(name = "persistProgramInformationTableXML") String programInformationTableXML) {

        boolean result = false;

        try {
            ProgramInformationDAO programInformationDAO=new ProgramInformationDAO();
            programInformationDAO.persistProgramInformationTable(tableName, programInformationTableXML);
            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            result = false;
        }

        return result;
    }

    /**
     * Webservice operation to update a Segment Information Table
     *
     * @param       id          id of the table to update = key
     *
     * @param       name        name of the Content Referencing Table,
     *                          this name is not part of the TVAnytime-standard,
     *                          it was implemented for better usability
     *
     * @param       programInformationTable  the XML-interpretation of an contentReferencingTable object
     * @return      boolean     true if successful, otherwise false
     */
    @WebMethod(operationName = "updateProgramInformationTable")
    public boolean updateProgramInformationTable(@WebParam(name = "index") int index, @WebParam(name = "tableName") String tableName, @WebParam(name = "programInformationTable") String programInformationTable) {
        boolean result=false;
        try {
            ProgramInformationDAO programInformationDAO=new ProgramInformationDAO();
            programInformationDAO.updateProgramInformationTable(index, tableName, programInformationTable);
            result=true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            result=false;
        }
        return result;
    }

    /**
     * Webservice operation to retrieve all the Segment Information Tables of the database
     *
     * @return      Collection of SegmentInformationTableResult-objects if no exception was thrown,
     *              returns null, if an exception was thrown
     */
    @WebMethod(operationName = "getAllProgramInformationTable")
    public Collection<ProgramInformationTableResult> getAllProgramInformationTable() {

        Collection result = null;

        try {
            ProgramInformationDAO programInformationDAO=new ProgramInformationDAO();
            result = programInformationDAO.getAllProgramInformationTable();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /**
     * Webservice operation to delete one Segment Information Table from the database
     *
     * @param       segmentInformationTableIndex              primary key as integer of the table to delete
     * @return      true if successfully otherwise false
     */
    @WebMethod(operationName = "deleteProgramInformationTable")
    public boolean deleteProgramInformationTable(@WebParam(name = "index") int tableIndex) {

        boolean result = false;

        try {
            ProgramInformationDAO programInformationDAO=new ProgramInformationDAO();
            programInformationDAO.deleteProgramInformationTable(tableIndex);
            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            result = false;
        }

        return result;
    }

    /**
     * Webservice operation to retrieve a table with a specific ID from the database
     *
     * @param       id      id-number of the table, this is the primary-key in the table
     * @return              xml-string of the table, if there has been an error,
     *                      it returns an empty string
     */
    @WebMethod(operationName = "getProgramInformationTable")
    public String getProgramInformationTable(@WebParam(name = "id") int id) {

        String result = "";

        try {
            ProgramInformationDAO programInformationDAO=new ProgramInformationDAO();
            result = programInformationDAO.getProgramInformationTable(id);
        } catch (SQLException ex) {
            Logger.getLogger(TVAnytimePersistence.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
