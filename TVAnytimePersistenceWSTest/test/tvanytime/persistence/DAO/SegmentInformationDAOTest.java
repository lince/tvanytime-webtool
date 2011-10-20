/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tvanytime.persistence.DAO;

import bbc.rd.mpeg7.MPEG7MediaIncrDuration;
import bbc.rd.mpeg7.MPEG7MediaRelIncrTimePoint;
import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.tvanytime.segmentInformation.SegmentLocator;
import java.util.ArrayList;
import tvanytime.persistence.ws.SegmentInformationTableResult;
import java.util.List;
import bbc.rd.tvanytime.segmentInformation.BasicSegmentDescription;
import bbc.rd.tvanytime.segmentInformation.SegmentInformation;
import bbc.rd.tvanytime.segmentInformation.SegmentList;
import java.util.Iterator;
import java.util.Collection;

import bbc.rd.tvanytime.segmentInformation.SegmentInformationTable;
import tvanytime.persistence.ws.TVAnytimePersistenceService;
import tvanytime.persistence.ws.TVAnytimePersistence;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lince
 */
public class SegmentInformationDAOTest extends AbstractDAOTest {

    static SegmentInformationTable segmentInformationTable = new SegmentInformationTable();

    @BeforeClass
    public static void setUpClass() throws Exception {

        createSegmentInformationTable();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws SQLException {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void persistSegmentInformationTest() {

        int i = 0;
        final String strFileName = "test4.xml";
        final String strTableName = "best table245";
        final String strSegmentInformationXml = segmentInformationTable.toXML();
        boolean flag = true;
        String strSegmentInformationTable = "";
        List<SegmentInformationTableResult> colSitr = null;
        Collection<String> colSegmentInformation = null;

        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();

        ArrayList<SegmentInformationTableResult> collection = (ArrayList) port.getAllSegmentInformationTable();

        for (int j = 0; j < collection.size(); j++) {
            assertEquals(collection.get(j).getFilename(), strFileName);
            assertEquals(collection.get(j).getTableName(), strTableName);
        }

        if (!port.persistSegmentInformationTable(strFileName, strTableName, strSegmentInformationXml)) {
            assertTrue(false);
        }

        colSitr = port.getAllSegmentInformationTable();

        while (flag) {

            strSegmentInformationTable = port.getSegmentInformationTable(i);

            if (strSegmentInformationTable.equals("")) {
                flag = false;
            } else {
                colSegmentInformation.add(strSegmentInformationTable);
            }
        }

        boolean flag2 = false;
        Iterator it = colSegmentInformation.iterator();
        while (it.hasNext()) {
            String strSegment = (String) it.next();

            if (strSegment.equals(strSegmentInformationXml)) {
                flag2 = true;
            }
        }

        // The segment information table exists in the database
        assertTrue(flag2);

        Iterator it2 = colSitr.iterator();

        boolean flag3 = false;
        SegmentInformationTableResult s = new SegmentInformationTableResult();
        while (it2.hasNext()) {
            s = (SegmentInformationTableResult) it2.next();
            if (s.getFilename().equals(strFileName) && s.getTableName().equals(strTableName)) {
                if (port.getSegmentInformationTable(s.getId()).equals(strSegmentInformationXml)) {
                    flag3 = true;
                }
            }
        }

        // The table was save with the right filename and tablename information
        assertTrue(flag3);
    }

    @Test
    public void getAllSegmentInformation() {
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        ArrayList segmentInformation = (ArrayList) port.getAllSegmentInformationTable();
        for (int i = 1; i < segmentInformation.size(); i++) {
            if (segmentInformation.get(i) == null) {
                assertTrue(false);
            }
        }
    }

    @Test
    public void getSegmentInformation() {
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        ArrayList<SegmentInformationTableResult> segmentInformationTable = (ArrayList<SegmentInformationTableResult>) port.getAllSegmentInformationTable();
        String segmentInfo = null;
        for (int i = 0; i < segmentInformationTable.size(); i++) {
            segmentInfo = port.getSegmentInformationTable(segmentInformationTable.get(i).getId());
            if (segmentInfo.equals("")) {
                assertTrue(false);
            }
        }
    }

    /*@Test
    public void deleteSegmentInformation() {

    TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
    ArrayList<SegmentInformationTableResult> segmentInformationTable = (ArrayList<SegmentInformationTableResult>) port.getAllSegmentInformationTable();
    if (segmentInformationTable.size() > 0) {
    if (!port.deleteSegmentInformationTable(segmentInformationTable.get(0).getId())) {
    assertTrue(false);
    } else {
    assertTrue(true);
    }
    }
    }*/
    @Test
    public void updateSegmentInformation() {
        String newTableName = "best tabl5e";
        String newFileName = "00098";
        long newTime = 111112222;
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        ArrayList<SegmentInformationTableResult> segmentInformationTable = (ArrayList<SegmentInformationTableResult>) port.getAllSegmentInformationTable();
        boolean flag = false;
        if (segmentInformationTable.size() > 0) {
            SegmentInformationTableResult segmentInformationTableResult = segmentInformationTable.get(1);
            assertEquals(segmentInformationTableResult.getTableName(), newTableName);
            assertEquals(segmentInformationTableResult.getFilename(), newFileName);
            assertEquals(segmentInformationTableResult.getTime(), newTime);

            if (!port.updateSegmentInformationTable(segmentInformationTableResult.getId(), newTableName, newFileName)) {
                assertTrue(false);
            }
        }
    }

    static void createSegmentInformationTable() {
        // TODO Construct a dummy segment information table
        SegmentList segList = new SegmentList();
        SegmentInformation si = new SegmentInformation();
        si.setDescription(new BasicSegmentDescription());
        segList.addSegmentInformation(si);
        segmentInformationTable.setSegmentList(segList);

        SegmentInformation segment = new SegmentInformation();
        SegmentLocator segLocator = new SegmentLocator();
        MPEG7MediaRelIncrTimePoint mpeg7Start = new MPEG7MediaRelIncrTimePoint();
        MPEG7MediaIncrDuration mpeg7Duration = new MPEG7MediaIncrDuration();

        segment.setSegmentID("segmentId");

        // Extract the basic description from the list and add it to the segment
        BasicSegmentDescription bsd = new BasicSegmentDescription();
        segment.setDescription(bsd);

        mpeg7Start.setTime(System.currentTimeMillis());
        mpeg7Start.setTimeUnit("Seconds");
        // The duration is set by the difference between start and end-stamp
        // The endvalue has to be higher
        mpeg7Duration.setTime(System.currentTimeMillis());
        mpeg7Duration.setTimeUnit("Seconds");

        // Start and duration are aded to the segment locator
        segLocator.addMPEG7MediaRelIncrTimePoint(mpeg7Start);
        segLocator.addMPEG7MediaIncrDuration(mpeg7Duration);
        segment.setSegmentLocator(segLocator);
        try {
            segment.setProgramRef(new ContentReference("crid://bbc.co.uk/123456"));
        } catch (TVAnytimeException ex) {
            ex.printStackTrace();
        }

        // Add the segment to the segment-list
        segList.addSegmentInformation(segment);
        segmentInformationTable.setSegmentList(segList);
    }
}
