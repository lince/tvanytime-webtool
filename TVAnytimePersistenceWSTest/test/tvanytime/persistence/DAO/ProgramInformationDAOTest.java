/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tvanytime.persistence.DAO;

import bbc.rd.tvanytime.BasicDescription;
import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.TVAnytimeException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tvanytime.persistence.ws.ProgramInformationTableResult;
import tvanytime.persistence.ws.TVAnytimePersistence;
import tvanytime.persistence.ws.TVAnytimePersistenceService;
import static org.junit.Assert.*;
import bbc.rd.tvanytime.programInformation.ProgramInformation;
import bbc.rd.tvanytime.programInformation.ProgramInformationTable;

/**
 *
 * @author Lince
 */
public class ProgramInformationDAOTest extends AbstractDAOTest {

    static ProgramInformationTable programInformationTable = new ProgramInformationTable();

    public ProgramInformationDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        createProgramInformationTable();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void persistProgramInformation() {
        TVAnytimePersistenceService service = new TVAnytimePersistenceService();
        TVAnytimePersistence port = service.getTVAnytimePersistencePort();
        final long newTime = 1112322;
        final String strTableName = "best table2";

        ArrayList<ProgramInformationTableResult> collection = (ArrayList) port.getAllProgramInformationTable();

        for (int j = 0; j < collection.size(); j++) {
            //assertEquals(collection.get(j).getTableName(), strTableName);
            //assertEquals(collection.get(j).getTime(), newTime);
        }

        if (!port.persistProgramInformationTable("table1", programInformationTable.toXML())) {
            assertTrue(false);
        }
    }

    @Test
    public void updateProgramInformation() {
        // Update the table in the database

        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        ArrayList pitr = (ArrayList) port.getAllProgramInformationTable();
        String newTableName = "test table";
        String newProgInfoValue = "info";

        final long newTime = 22332322;
        if (pitr.size() > 0) {
            ProgramInformationTableResult programInformationTableResult = (ProgramInformationTableResult) pitr.get(1);
          //  assertEquals(programInformationTableResult.getTableName(), newTableName);
            //assertEquals(programInformationTableResult.getTime(), newTime);
            if (!port.updateProgramInformationTable(((ProgramInformationTableResult) pitr.get(1)).getId(), newProgInfoValue, programInformationTable.toXML())) {
                assertTrue(false);
            }
        }
    }

    @Test
    public void getAllProgramInformation() {
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        Collection programInformation = port.getAllProgramInformationTable();
        if (programInformation == null) {
            assertTrue(false);
        }
    }

    @Test
    public void getProgramInformation() {
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        ArrayList pitr = (ArrayList) port.getAllProgramInformationTable();
        String programInfo = null;
        for (int i = 1; i < pitr.size(); i++) {
            programInfo = port.getProgramInformationTable(((ProgramInformationTableResult) pitr.get(i)).getId());
            if (programInfo.equals("")) {
                assertTrue(false);
            }
        }
    }

    @Test
    public void deleteProgramInformation() {

        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        List pitr = port.getAllProgramInformationTable();
        if (pitr.size() > 0) {
            if (!port.deleteSegmentInformationTable(((ProgramInformationTableResult) pitr.get(1)).getId())) {
                assertTrue(false);
            }
        }
    }

    static void createProgramInformationTable() {
        // TODO Construct a dummy segment information table
        ContentReference crid = null;
        try {
            crid = new ContentReference("crid://bbc.co.uk/21837");
        } catch (TVAnytimeException ex) {
            Logger.getLogger(ProgramInformationDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProgramInformationTable programInformationTable = new ProgramInformationTable();
        ProgramInformation programInformation = new ProgramInformation();
        BasicDescription bsd = new BasicDescription();
        // Appending the basic description data to the programinformation
        programInformation.setBasicDescription(bsd);
        programInformation.setProgramID(crid);
        programInformationTable.addProgramInformation(programInformation);

        // Persisting the program information table through the webservice
        TVAnytimePersistenceService service = new TVAnytimePersistenceService();
        TVAnytimePersistence port = service.getTVAnytimePersistencePort();
        port.persistProgramInformationTable("table1", programInformationTable.toXML());
        programInformationTable.toXML();
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
