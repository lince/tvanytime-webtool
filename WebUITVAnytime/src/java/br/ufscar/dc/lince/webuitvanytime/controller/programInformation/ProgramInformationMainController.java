/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lince.webuitvanytime.controller.programInformation;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;
import tvanytime.persistence.ws.ProgramInformationTableResult;

/**
 *
 * @author lince
 */
public class ProgramInformationMainController extends Window {

    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(ProgramInformationMainController.class.getName());

    public ProgramInformationMainController() {
        logger.setLevel(Level.ALL);
    }

    /**
     * Opening a new page to add a new segment information table
     */
    public void add() {
        Executions.createComponents("programInformation.zul", null, null);
        this.detach();
    }

    public void delete() {
//        logger.log(Level.FINEST, "Method delete is getting executed");
//
//        Listbox lbCrid = (Listbox) getFellow("lbSegmentInformationTable");
//        Listitem liSelected = lbCrid.getSelectedItem();
//        SegmentInformationTableResult sitr = (SegmentInformationTableResult) liSelected.getAttribute("itemID");
//
//        // Retrieve the port of the webservice
//        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
//        if (port.deleteSegmentInformationTable(sitr.getId())) {
//            lbCrid.removeItemAt(liSelected.getIndex());
//            ((Button) getFellow("deleteSegment")).setDisabled(true);
//            ((Button) getFellow("editSegment")).setDisabled(true);
//        } else {
//            try {
//                Messagebox.show("Content Reference Table could not be deleted", "Error", Messagebox.OK, Messagebox.ERROR);
//            } catch (InterruptedException ex) {
//                logger.log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public void edit() {

        Listbox lbProgramnInformationTable = (Listbox) getFellow("lbProgramInformationTable");
        Listitem liSelected = lbProgramnInformationTable.getSelectedItem();

        ProgramInformationTableResult pitr = (ProgramInformationTableResult) liSelected.getAttribute("programItem");

        // Important!!: To typecast the return type of createComponents to NewSegmentDescriptionController (not to Window!!!)
        // So the methods of the controller class are available
        ProgramInformationController pic = (ProgramInformationController) Executions.createComponents("programInformation.zul", null, null);
        pic.edit(pitr);

        // Close this window
        this.detach();
    }

    /**
     * Enablse the buttons to delete or edit a crid-table
     */
    public void selectCrid() {
        ((Button) getFellow("deleteProgram")).setDisabled(false);
        ((Button) getFellow("editProgram")).setDisabled(false);
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
        //fillSegmentListbox();
    }
}
