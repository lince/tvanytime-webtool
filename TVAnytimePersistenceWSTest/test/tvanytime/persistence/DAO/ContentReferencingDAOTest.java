/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tvanytime.persistence.DAO;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tvanytime.persistence.ws.ContentReferencingTableResult;
import tvanytime.persistence.ws.TVAnytimePersistence;
import tvanytime.persistence.ws.TVAnytimePersistenceService;
import static org.junit.Assert.*;
import bbc.rd.tvanytime.contentReferencing.CRIDResult;
import bbc.rd.tvanytime.contentReferencing.ContentReferencingTable;
import bbc.rd.tvanytime.contentReferencing.LocationsResult;
import bbc.rd.tvanytime.contentReferencing.Locator;
import bbc.rd.tvanytime.contentReferencing.Result;

/**
 *
 * @author Lince
 */
public class ContentReferencingDAOTest extends AbstractDAOTest {

    //private Connection connection;
    static ContentReferencingTable contentReferencingTable = new ContentReferencingTable();

    public ContentReferencingDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        createContentRefTable();
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
    public void persistContentReferencing() {
        // To retrieve the port-object of the webservice
        String tbCridName = "crid://bbc.co.uk/21837";

        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        if (port.persistContentReferencingTable(tbCridName.trim(), contentReferencingTable.toXML())) {
            //Content Referencing Table was saved successfully
            assertTrue(true);
        } else {
            //Content Referencing Table could not get saved!
            assertTrue(false);
        }
    }

    @Test
    public void updateContentReferencing() {
        // Update the table in the database

        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        ContentReferencingTableResult crtr = (ContentReferencingTableResult) port.getAllContentReferencingTable().get(1);
        String newContRefValue = "test";
        if (port.updateContentReferencingTable(crtr.getId(), newContRefValue.trim(), contentReferencingTable.toXML())) {
            //Content Referencing Table was updated successfully!;
            assertTrue(true);
        } else {
            //Content Referencing Table could not get updated!;
            assertTrue(false);
        }
    }

    @Test
    public void getAllContentReferencingInformation() {
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        Collection contentInformation = port.getAllContentReferencingTable();
        if (contentInformation != null) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void getContentReferencingInformation() {
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        ArrayList<ContentReferencingTableResult> contentReferencingInformationTable = (ArrayList<ContentReferencingTableResult>) port.getAllContentReferencingTable();
        String contentRefInfo = null;
        for (int i = 0; i < contentReferencingInformationTable.size(); i++) {
            contentRefInfo = port.getContentReferencingTable(contentReferencingInformationTable.get(i).getId());
            if (!contentRefInfo.equals("")) {
                assertTrue(true);
            }
        }
    }

    @Test
    public void deleteContentReferencingInformation() {

        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        ArrayList<ContentReferencingTableResult> contentReferencingTable = (ArrayList<ContentReferencingTableResult>) port.getAllContentReferencingTable();
        if (contentReferencingTable.size() > 0) {
            if (!port.deleteSegmentInformationTable(contentReferencingTable.get(0).getId())) {
                assertTrue(false);
            } else {
                assertTrue(true);
            }
        }
    }

    static void createContentRefTable() {
        // TODO Construct a dummy segment information table
        LocationsResult lResults = new LocationsResult();
        Locator locator = new Locator();

        CRIDResult cRIDResult = new CRIDResult();
        Result result = new Result();
        result.setCRIDResult(cRIDResult);
        lResults.addLocator(locator);
        contentReferencingTable.setVersion((float) 1.2);
    }
}