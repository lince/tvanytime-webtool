/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lince.webuitvanytime.controller.segmentInformation;

import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.mpeg7.MPEG7MediaIncrDuration;
import bbc.rd.mpeg7.MPEG7MediaRelIncrTimePoint;
import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.segmentInformation.BasicSegmentDescription;
import bbc.rd.tvanytime.segmentInformation.SegmentInformation;
import bbc.rd.tvanytime.segmentInformation.SegmentInformationTable;
import bbc.rd.tvanytime.segmentInformation.SegmentList;
import bbc.rd.tvanytime.segmentInformation.SegmentLocator;
import br.ufscar.dc.lince.tvanytime.core.Toolbox;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
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
public class SegmentInformationController extends Window {

    /**
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(NewSegmentDescriptionController.class.getName());
    /**
     *Current position in time of the video plugin
     *  The time is measured in seconds
     */
    private int intCurrentTime = 0;
    Properties properties = new Properties();

    public SegmentInformationController() {
//        try {
//            properties.load(new FileInputStream("conf/logging.properties"));
//            // Set the level of the logger engine
//            //logger.setLevel(Level.parse(properties.getProperty("logLevel")));
        logger.setLevel(Level.ALL);
//        } catch (IOException ex) {
//            Logger.getLogger(SegmentInformationController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * Getter for the current timestamp
     * of the video content in seconds
     */
    private int getIntCurrentTime() {
        return intCurrentTime;
    }

    /**
     * Setter for the current timestamp
     * of the video content in seconds
     */
    private void setIntCurrentTime(int current) {
        intCurrentTime = current;
    }

    public void setBeginTime() {
        Textbox tbBeginTime = (Textbox) getFellow("tbBeginTime");
        tbBeginTime.setValue(Toolbox.secondsToString(getIntCurrentTime()));
    }

    public void setEndTime() {
        Textbox tbBeginTime = (Textbox) getFellow("tbEndTime");
        tbBeginTime.setValue(Toolbox.secondsToString(getIntCurrentTime()));
    }

    /**
     * Handels the event onUser which is raised by the client-engine on purpose
     *
     * onUser is no system-event, its raised manualy
     */
    public void onUser(org.zkoss.zk.ui.event.Event event) {
        logger.log(Level.FINE, "onUser-Event received, following data received: ",
                (String) (event.getData()));

        // To set the current timestamp of the video for the server
        setIntCurrentTime(Integer.parseInt((String) event.getData()));

        // Set the value of the label
        Label lbTime = (Label) getFellow("lbCurrentTime");
        lbTime.setValue("Time: " + Toolbox.secondsToString(getIntCurrentTime()));
    }

    public void delDescription() {
        Listbox lbSegmentAdd = (Listbox) getDesktop().getPage("segmentInformationMain").getFellow("winSegmentInformation").getFellow("lbSegmentAdd");
        Listitem it = (Listitem) lbSegmentAdd.getItemAtIndex(0);
        List children = it.getChildren();
        ((Checkbox) ((Vbox) ((Listcell) children.get(3)).getFirstChild()).getChildren().get(0)).setChecked(false);
        it.setAttribute("segmentDescriptionItem", null);
    }

    public void addSegment() {
        Listbox lbSegmentAdd = (Listbox) getDesktop().getPage("segmentInformationMain").getFellow("winSegmentInformation").getFellow("lbSegmentAdd");
        Listbox lbSegment = (Listbox) getFellow("lbSegment");
        Listitem it = (Listitem) lbSegmentAdd.getItemAtIndex(0);
        List children = it.getChildren();
        Textbox tbSegmentId = (Textbox) ((Listcell) children.get(0)).getFirstChild().clone();
        Textbox tbBegin = (Textbox) ((Listcell) children.get(1)).getFirstChild().clone();
        Textbox tbEnd = (Textbox) ((Listcell) children.get(2)).getFirstChild().clone();
        Checkbox cbxDescription = (Checkbox) ((Checkbox) ((Vbox) ((Listcell) children.get(3)).getFirstChild()).getChildren().get(0)).clone();

        Vbox vbox = new Vbox();
        // Set the appearance of the vbox
        vbox.setWidth("100%");
        vbox.setAlign("center");
        vbox.appendChild(cbxDescription);

        // getIndex return the index of the next listitem
        Integer index = getIndex(lbSegment);

        // Change the ID of all the items, to not have duplicated IDs in the
        // namespace of the page
        // Namingconvention: newId = oldId + "_" + number of Listitems
        // e.g.: tbNewTitle = tbNewTitle_1
        // e.g.: cbTitleLanguage = cbTitleLanguage_1
        tbSegmentId.setId(tbSegmentId.getId() + "_" + index.toString());
        tbBegin.setId(tbBegin.getId() + "_" + index.toString());
        tbEnd.setId(tbEnd.getId() + "_" + index.toString());
        cbxDescription.setId(cbxDescription.getId() + "_" + index.toString());

        // A listitem gets added to a list
        // A listitem is constructed out of listcells
        // One listcell is one field in the table and can have varios controlls
        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();
        Listcell cell3 = new Listcell();
        Listcell cell4 = new Listcell();
        Listcell cell5 = new Listcell();

        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(tbSegmentId);
        cell3.appendChild(tbBegin);
        cell4.appendChild(tbEnd);
        cell5.appendChild(vbox);
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.appendChild(cell3);
        li.appendChild(cell4);
        li.appendChild(cell5);

        // If an description was added
        if (cbxDescription.isChecked()) {
            // Copy the segment description object from the SegmentAdd-listbox to Segment-listbox
            li.setAttribute("segmentDescriptionItem", ((BasicSegmentDescription) it.getAttribute("segmentDescriptionItem")).clone());
        }

        li.setParent(lbSegment);
    }

    public void delSegment() {
        Listbox lbSegment = (Listbox) getFellow("lbSegment");
        lbSegment.removeItemAt(lbSegment.getSelectedIndex());
    }

    /**
     * Method is executed when an item in the segment listbox was selected
     * The button to delete the segment from the list gets enabled
     */
    public void selectSegmentItem() {
        Button btDelSegment = (Button) getFellow("btDelSegment");
        btDelSegment.setDisabled(false);
    }

    /**
     * Method saves the segment table to the database through the webservice
     */
    public void saveSegmentTable() {
        Listbox lbSegment = (Listbox) getFellow("lbSegment");
        Combobox cbCrid = (Combobox) getFellow("cbCrid");
        String filename = ((Textbox) getFellow("tbFileName")).getValue();
        SegmentInformationTable segInformationTable = new SegmentInformationTable();
        SegmentList segList = new SegmentList();

        // Set the log-level
        logger.log(Level.FINE, "saveSegmentTable started");

        /**
         * Collecting the information of the titlelistbox
         */
        // The items of the listbox start with an index of 1 !!!!
        // The element lbPerson.getItemAtIndex(0) exists, but its empty
        // listboxItem[]={[]} = this information is important for all listboxes
        for (int i = 1; i < lbSegment.getItemCount(); i++) {
            Listitem it = (Listitem) lbSegment.getItemAtIndex(i);
            List children = it.getChildren();

            SegmentInformation segment = new SegmentInformation();
            SegmentLocator segLocator = new SegmentLocator();
            MPEG7MediaRelIncrTimePoint mpeg7Start = new MPEG7MediaRelIncrTimePoint();
            MPEG7MediaIncrDuration mpeg7Duration = new MPEG7MediaIncrDuration();

            segment.setSegmentID(((Textbox) ((Listcell) children.get(1)).getFirstChild()).getValue());

            if (it.getAttribute("segmentDescriptionItem") != null) {
                // Extract the basic description from the list and add it to the segment
                BasicSegmentDescription bsd = (BasicSegmentDescription) ((BasicSegmentDescription) it.getAttribute("segmentDescriptionItem")).clone();
                segment.setDescription(bsd);
            }

            int start = Toolbox.stringToSeconds(((Textbox) ((Listcell) children.get(2)).getFirstChild()).getValue());
            int end = Toolbox.stringToSeconds(((Textbox) ((Listcell) children.get(3)).getFirstChild()).getValue());
            mpeg7Start.setTime(start);
            mpeg7Start.setTimeUnit("Seconds");
            // The duration is set by the difference between start and end-stamp
            // The endvalue has to be higher
            mpeg7Duration.setTime(end - start);
            mpeg7Duration.setTimeUnit("Seconds");

            // Start and duration are aded to the segment locator
            segLocator.addMPEG7MediaRelIncrTimePoint(mpeg7Start);
            segLocator.addMPEG7MediaIncrDuration(mpeg7Duration);
            segment.setSegmentLocator(segLocator);
            try {
                segment.setProgramRef(new ContentReference(cbCrid.getValue()));
            } catch (TVAnytimeException ex) {
                Logger.getLogger(SegmentInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Add the segment to the segment-list
            segList.addSegmentInformation(segment);
        }

        segInformationTable.setSegmentList(segList);

        // The logged xml-string will getting persistet in the database
        logger.log(Level.FINER, segInformationTable.toXML());

        // To retrieve the port-object of the webservice
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();

        // Persisting the xml-string of the segment information table through the wegservice
        String strTableName = ((Textbox) getFellow("tbTableName")).getValue();

        if (((Button) getFellow("btSave")).getLabel().equals("Save Segment Table")) {
            // Persisting the xml-string of the segment information table through the wegservice
            if (port.persistSegmentInformationTable(filename, strTableName, segInformationTable.toXML())) {
                try {
                    // Table was saved successfuly
                    Messagebox.show("Segment Information Table was saved successfully!", "Information", Messagebox.OK, Messagebox.INFORMATION);
                    logger.log(Level.FINEST, "Segment Information Table saved: " + segInformationTable.toXML());
                } catch (InterruptedException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Messagebox.show("Segment Information Table could get saved!", "Error", Messagebox.OK, Messagebox.ERROR);
                } catch (InterruptedException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }

        } else {
            // Update the table in the database            
            SegmentInformationTableResult sitr = (SegmentInformationTableResult) lbSegment.getAttribute("segmentInformationTableResult");

            if (port.updateSegmentInformationTable(sitr.getId(), strTableName, segInformationTable.toXML())) {
                try {
                    Messagebox.show("Segment Information Table was updated successfully!", "Information", Messagebox.OK, Messagebox.INFORMATION);
                    logger.log(Level.FINEST, "Segment Information Table updated: " + segInformationTable.toXML());
                } catch (InterruptedException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Messagebox.show("Segment Information Table could not get updated!", "Error", Messagebox.OK, Messagebox.ERROR);
                } catch (InterruptedException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }

        // Close the window
        this.detach();
        // Open the main window
        Window winContentReference = (Window) Executions.createComponents("segmentInformationMain.zul", null, null);
        winContentReference.setVisible(true);

        logger.log(Level.FINE, "saveSegmentTable finished");
    }

    /**
     * A item of the listbox lbSegment has been doubleclicked,
     * and now the segment description can be edited
     */
    public void onDoubleClickSegment() {
        logger.log(Level.FINER, "Listbox double clicked");

        Listitem it = ((Listbox) getFellow("lbSegment")).getSelectedItem();

        // There is a ddescription to edit
        if (it.getAttribute("segmentDescriptionItem") != null) {

            // Important!!: To typecast the return type of createComponents to NewSegmentDescriptionController (not to Window!!!)
            // So the methods of the controller class are available
            NewSegmentDescriptionController nsdc = (NewSegmentDescriptionController) Executions.createComponents("newSegmentDescription.zul", null, null);
            nsdc.setMaximizable(false);

            logger.log(Level.FINEST, "New Segment Description Window created");

            // Fill the window with the information which will be edited
            nsdc.edit((BasicSegmentDescription) it.getAttribute("segmentDescriptionItem"));

            try {
                //nsdc.setParent(this);
                logger.log(Level.FINEST, "New Segment Description parent set");
                // Show the window in modal style                
                nsdc.doModal();
                // Detach the window
                //nsdc.detach();
                //BasicSegmentDescription basicSegmentDescription = (BasicSegmentDescription) it.getAttribute("segmentDescriptionItem");
            } catch (InterruptedException ex) {
                Logger.getLogger(SegmentInformationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SuspendNotAllowedException ex) {
                Logger.getLogger(SegmentInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
