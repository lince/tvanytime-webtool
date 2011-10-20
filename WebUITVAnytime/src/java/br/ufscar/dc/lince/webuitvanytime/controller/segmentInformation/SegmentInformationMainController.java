/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lince.webuitvanytime.controller.segmentInformation;

import bbc.rd.mpeg7.MPEG7MediaIncrDuration;
import bbc.rd.mpeg7.MPEG7MediaRelIncrTimePoint;
import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.tvanytime.segmentInformation.BasicSegmentDescription;
import bbc.rd.tvanytime.segmentInformation.SegmentInformation;
import bbc.rd.tvanytime.segmentInformation.SegmentInformationTable;
import bbc.rd.tvanytime.segmentInformation.SegmentList;
import bbc.rd.tvanytime.segmentInformation.SegmentLocator;
import bbc.rd.tvanytime.xml.SAXXMLParser;
import br.ufscar.dc.lince.tvanytime.core.Toolbox;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import tvanytime.persistence.ws.SegmentInformationTableResult;
import tvanytime.persistence.ws.TVAnytimePersistence;
import tvanytime.persistence.ws.TVAnytimePersistenceService;

/**
 *
 * @author lince
 */
public class SegmentInformationMainController extends Window {

    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(SegmentInformationMainController.class.getName());

    public SegmentInformationMainController() {
        logger.setLevel(Level.ALL);
    }

    /**
     * Opening a new page to add a new segment information table
     */
    public void add() {
        Executions.createComponents("segmentInformation.zul", null, null);
        this.detach();
    }

    public void delete() {

        logger.log(Level.FINEST, "Method delete is getting executed");

        Listbox lbCrid = (Listbox) getFellow("lbSegmentInformationTable");
        Listitem liSelected = lbCrid.getSelectedItem();
        SegmentInformationTableResult sitr = (SegmentInformationTableResult) liSelected.getAttribute("itemID");

        // Retrieve the port of the webservice
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        if (port.deleteSegmentInformationTable(sitr.getId())) {
            lbCrid.removeItemAt(liSelected.getIndex());
            ((Button) getFellow("deleteSegment")).setDisabled(true);
            ((Button) getFellow("editSegment")).setDisabled(true);
        } else {
            try {
                Messagebox.show("Content Reference Table could not be deleted", "Error", Messagebox.OK, Messagebox.ERROR);
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    public void edit() {

        Listbox lbSegmentInformationTable = (Listbox) getFellow("lbSegmentInformationTable");
        Listitem liSelected = lbSegmentInformationTable.getSelectedItem();

        SegmentInformationTableResult sitr = (SegmentInformationTableResult) liSelected.getAttribute("itemID");

        // Retrieve the port of the webservice
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        String strSegmentInformationTable = port.getSegmentInformationTable(sitr.getId());

        // Load the components of new window but set it invisilbe until its completly loaded
        Window winSegmentInformation = (Window) Executions.createComponents("segmentInformation.zul", null, null);
        winSegmentInformation.setVisible(false);

        Listbox lbSegment = (Listbox) winSegmentInformation.getFellow("lbSegment");
        lbSegment.setAttribute("segmentInformationTableResult", sitr);

        ((Textbox) winSegmentInformation.getFellow("tbFileName")).setValue(sitr.getFilename());
        ((Textbox) winSegmentInformation.getFellow("tbTableName")).setValue(sitr.getTableName());

        // Sets the label of the save-button to be an "update-button"
        ((Button) winSegmentInformation.getFellow("btSave")).setLabel("Update Segment Table");

        logger.log(Level.FINEST, "Segment Information Table with id=" + sitr.getId() + " to edit: " + strSegmentInformationTable);

        // If the table was found in the database
        if (!strSegmentInformationTable.equals("")) {

            SAXXMLParser parser = null;
            try {
                parser = new SAXXMLParser();
                parser.setParseProfile(SAXXMLParser.STANDARD);
                parser.parse(new ByteArrayInputStream(strSegmentInformationTable.getBytes()));
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            } catch (TVAnytimeException ex) {
                logger.log(Level.SEVERE, null, ex);
            }

            SegmentInformationTable segmentInformationTable = parser.getSegmentInformationTable();
            SegmentList segmentList = segmentInformationTable.getSegmentList();

            /*
             * Sets the crid-box to the crid of the crid-information
             * of the first segment of the table, if there is one and the crid
             * is set
             */
            if (segmentList.getNumSegmentInformations() > 0) {
                if (segmentList.getSegmentInformation(0).getProgramRef() != null) {
                    ((Combobox) winSegmentInformation.getFellow("cbCrid")).setValue(segmentList.getSegmentInformation(0).getProgramRef().getCRID());
                }
            }

            for (int i = 0; i < segmentList.getNumSegmentInformations(); i++) {
                SegmentInformation segmentInformation = segmentList.getSegmentInformation(i);

                Textbox tbSegmentId = new Textbox();
                Textbox tbBegin = new Textbox();
                Textbox tbEnd = new Textbox(segmentInformation.getSegmentID());
                Vbox vbox = new Vbox();
                Checkbox cbxDescription = new Checkbox();

                // Set the appearance of the textboxes
                tbSegmentId.setWidth("98%");
                tbBegin.setWidth("98%");
                tbEnd.setWidth("98%");

                // Set the appearance of the vbox
                vbox.setWidth("100%");
                vbox.setAlign("center");
                vbox.appendChild(cbxDescription);

                // Set the appearance of the checkbox
                cbxDescription.setDisabled(true);

                SegmentLocator segmentLocator = segmentInformation.getSegmentLocator();
                MPEG7MediaRelIncrTimePoint mpeg7Start = segmentLocator.getMPEG7MediaRelIncrTimePoint();
                MPEG7MediaIncrDuration mpeg7Duration = segmentLocator.getMPEG7MediaIncrDuration();

                tbSegmentId.setValue(segmentInformation.getSegmentID());
                tbBegin.setValue(Toolbox.secondsToString((int) mpeg7Start.getTime()));
                tbEnd.setValue(Toolbox.secondsToString((int) (mpeg7Start.getTime() + mpeg7Duration.getTime())));

                // A listitem gets added to a list
                // A listitem is constructed out of listcells
                // One listcell is one field in the table and can have varios controlls
                Listitem li = new Listitem();
                Listcell cell1 = new Listcell();
                Listcell cell2 = new Listcell();
                Listcell cell3 = new Listcell();
                Listcell cell4 = new Listcell();
                Listcell cell5 = new Listcell();

                // getIndex return the index of the next listitem
                Integer index = getIndex(lbSegment);

                cell1.appendChild(new Label(new Integer(index).toString()));
                cell2.appendChild(tbSegmentId);
                cell3.appendChild(tbBegin);
                cell4.appendChild(tbEnd);
                cell5.appendChild(cbxDescription);
                li.appendChild(cell1);
                li.appendChild(cell2);
                li.appendChild(cell3);
                li.appendChild(cell4);
                li.appendChild(cell5);

                // A Basic Segment Description exists
                if (segmentInformation.getDescription() != null) {
                    cbxDescription.setChecked(true);
                    // Add the basic description to the list-item
                    li.setAttribute("segmentDescriptionItem", segmentInformation.getDescription());
                }

                lbSegment.appendChild(li);
            }

            // Close this window
            this.detach();
            // Show the window to edit the segment edition table
            winSegmentInformation.setVisible(true);

            // The table couldnt be read from the database
        } else {
        }
    }

    /**
     * Enablse the buttons to delete or edit a crid-table
     */
    public void selectCrid() {
        ((Button) getFellow("deleteSegment")).setDisabled(false);
        ((Button) getFellow("editSegment")).setDisabled(false);
    }

    /**
     * Event is fired when all child components of the window have been created
     *
     * Fills the Crid-listbox with the information of the available crid-tables
     * in the database
     *
     * @param event
     */
    public void onCreate(org.zkoss.zk.ui.event.Event event) {
        fillSegmentListbox();
    }

    /**
     * Fills the listbox with the information of all
     * available Segment Information Tables of the database
     */
    private void fillSegmentListbox() {

        Listbox lbCrid = (Listbox) getFellow("lbSegmentInformationTable");

        // Retrieve the port of the webservice
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();

        Collection lsSegment = port.getAllSegmentInformationTable();
        Iterator it = lsSegment.iterator();
        int i = 0;

        while (it.hasNext()) {
            SegmentInformationTableResult sitr = (SegmentInformationTableResult) it.next();
            i++;

            Listitem li = new Listitem();

            Listcell cell1 = new Listcell();
            Listcell cell2 = new Listcell();
            Listcell cell3 = new Listcell();
            Listcell cell4 = new Listcell();

            Label lbIndex = new Label(Integer.toString(i));

            Label lbTimestamp = new Label(new Timestamp(sitr.getTime()).toString());
            Label lbFileName = new Label(sitr.getFilename());
            Label lbName = new Label(sitr.getTableName());
            li.setAttribute("itemID", sitr);

            cell1.appendChild(lbIndex);
            cell2.appendChild(lbTimestamp);
            cell3.appendChild(lbFileName);
            cell4.appendChild(lbName);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);
            li.appendChild(cell4);

            lbCrid.appendChild(li);
        }
    }

    /*
     * Gets the highest number of the indizes field of listbox
     *
     * @param listbox reference of the listbox-object
     *
     * @return indizes of the next item of the listbox
     */
    private int getIndex(Listbox lb) {
        int retvalue = 0;

        // The header of a listbox is alreay a listitem, so that means
        // an empty list has (where there is only the header) already has
        // an itemCount of 1, so if there are more then 1 item in the list then
        // there are actually items in the list
        if (lb.getItemCount() > 1) {
            Listitem li = (Listitem) lb.getItemAtIndex(lb.getItemCount() - 1);
            retvalue = Integer.parseInt(((Label) ((Listcell) li.getChildren().get(0)).getFirstChild()).getValue()) + 1;
        } else {
            // The list is empty, but the indizes start with one,
            // so the return value is one
            retvalue = 1;
        }

        return retvalue;
    }
}
