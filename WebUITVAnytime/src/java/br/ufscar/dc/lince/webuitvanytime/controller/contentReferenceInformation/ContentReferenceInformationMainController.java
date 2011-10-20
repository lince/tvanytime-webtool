/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lince.webuitvanytime.controller.contentReferenceInformation;

import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.tvanytime.contentReferencing.CRIDResult;
import bbc.rd.tvanytime.contentReferencing.ContentReferencingTable;
import bbc.rd.tvanytime.contentReferencing.LocationsResult;
import bbc.rd.tvanytime.contentReferencing.Locator;
import bbc.rd.tvanytime.contentReferencing.Result;
import bbc.rd.tvanytime.xml.SAXXMLParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;
import tvanytime.persistence.ws.ContentReferencingTableResult;
import tvanytime.persistence.ws.TVAnytimePersistence;
import tvanytime.persistence.ws.TVAnytimePersistenceService;

/**
 *
 * @author lince
 */
public class ContentReferenceInformationMainController extends Window {

    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(ContentReferenceInformationMainController.class.getName());

    public ContentReferenceInformationMainController() {
        // Set the logger level for this class
        logger.setLevel(Level.ALL);
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
        fillCridListbox();
    }

    /**
     * Opening the window to add a new content reference table
     */
    public void add() {

        Executions.createComponents("contentReferenceInformation.zul", null, null);
        this.detach();
    }

    public void edit() {

        Listbox lbCrid = (Listbox) getFellow("lbContentReferenceTable");
        Listitem liSelected = lbCrid.getSelectedItem();

        ContentReferencingTableResult crtr = (ContentReferencingTableResult) liSelected.getAttribute("itemID");

        // Retrieve the port of the webservice
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();

        String strContentReferenceTable = port.getContentReferencingTable(crtr.getId());

        Window winContentReference = (Window) Executions.createComponents("contentReferenceInformation.zul", null, null);
        winContentReference.setVisible(false);

        /*
         *  Attaches the ContentReferencingTableResult to the treebox
         *  This "trick" is used to pass not to the user visible data to the
         *  controller of the other window
         */
        winContentReference.getFellow("trCrid").setAttribute("contentReferenceTableResult", crtr);

        // Sets the label of the save-button to be an "update-button"
        ((Button) winContentReference.getFellow("btSave")).setLabel("Update Content Reference Table");

        logger.log(Level.FINEST, "Content Reference Table with id=" + crtr.getId() + " to edit: " + strContentReferenceTable);

        // If the table was found in the database
        if (!strContentReferenceTable.equals("")) {

            SAXXMLParser parser = null;
            try {
                parser = new SAXXMLParser();
                parser.setParseProfile(SAXXMLParser.STANDARD);
                parser.parse(new ByteArrayInputStream(strContentReferenceTable.getBytes()));
            } catch (IOException ex) {
                Logger.getLogger(ContentReferenceInformationMainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TVAnytimeException ex) {
                Logger.getLogger(ContentReferenceInformationMainController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ContentReferencingTable contenReferncingTable = parser.getContentReferencingTable();

            Tree trCrid = (Tree) winContentReference.getFellow("trCrid");
            // Set the textbox-value to the table name
            ((Textbox) winContentReference.getFellow("tbCridName")).setValue(crtr.getName());

            for (int i = 0; i < contenReferncingTable.getNumResults(); i++) {
                Result result = contenReferncingTable.getResult(i);

                Textbox tbAuthorityResult = new Textbox();
                Textbox tbDataResult = new Textbox();
                Checkbox cbxComplete = new Checkbox();
                Combobox cbAcquire = (Combobox) winContentReference.getFellow("cbAcquire").clone();
                Combobox cbStatus = (Combobox) winContentReference.getFellow("cbStatus").clone();
                Datebox dbReresolverDate = new Datebox();

                tbAuthorityResult.setValue(result.getCRID().getAuthority());
                tbDataResult.setValue(result.getCRID().getData());
                cbxComplete.setChecked(result.isComplete());

                // Selects the corresponding item, otherwise it selects nothing
                if (result.getAcquire().equals("all")) {
                    cbAcquire.setSelectedIndex(0);
                } else if (result.getAcquire().equals("any")) {
                    cbAcquire.setSelectedIndex(1);
                } else {
                    // The aquired status was not all nor any, so nothing gets selected
                }

                // If the status was defined, select the corresponding item in the combobox
                if (!result.getStatus().trim().equals("")) {
                    List it = cbStatus.getItems();
                    for (int i2 = 0; i2 < it.size(); i2++) {
                        if (((Comboitem) it.get(i2)).getLabel().equals(result.getStatus())) {
                            cbStatus.setSelectedItem((Comboitem) it.get(i2));
                        }
                    }
                }

                dbReresolverDate.setValue(result.getReresolveDate());

                /*
                 * Add the components to the tree
                 */

                // getIndex return the index of the next treeitem
                Integer index = getIndex(trCrid);

                // Change the ID of all the items, to not have duplicated IDs in the
                // namespace of the page
                // Namingconvention: newId = oldId + "_" + number of Listitems
                // e.g.: tbNewTitle = tbNewTitle_1
                // e.g.: cbTitleLanguage = cbTitleLanguage_1
                tbAuthorityResult.setId(tbAuthorityResult.getId() + "_" + index.toString());
                tbDataResult.setId(tbDataResult.getId() + "_" + index.toString());
                cbxComplete.setId(cbxComplete.getId() + "_" + index.toString());
                cbAcquire.setId(cbAcquire.getId() + "_" + index.toString());
                cbStatus.setId(cbStatus.getId() + "_" + index.toString());
                dbReresolverDate.setId(dbReresolverDate.getId() + "_" + index.toString());

                // Construction of the CRID-String
                // "CRID://" + Authority name "/" + Content ID;
                // e.g.: crid://bbc.co.uk/21837
                String strCridResult = "crid://" + tbAuthorityResult.getValue() + "/" + tbDataResult.getValue();

                Treeitem trItem = new Treeitem();
                Treerow trRow = new Treerow();
                Treecell trc1 = new Treecell();
                Treecell trc2 = new Treecell();

                // Adds an empty result object as the customer attribute of
                // this node in the tree, to make it possible to identify the
                // type of the tree node easily
                trItem.setAttribute("treeItem", new Result());

                trc1.setLabel(strCridResult);

                /*
                 * Construction of the rowcell of the result-object
                 */
                trc2.appendChild(new Label("Complete: "));
                trc2.appendChild(cbxComplete);
                trc2.appendChild(new Label("  Acquire: "));
                cbAcquire.setCols(5);
                trc2.appendChild(cbAcquire);
                trc2.appendChild(new Label("  Status: "));
                trc2.appendChild(cbStatus);
                trc2.appendChild(new Label("  Reresolver Date: "));
                trc2.appendChild(dbReresolverDate);

                trRow.appendChild(trc1);
                trRow.appendChild(trc2);
                trItem.appendChild(trRow);
                // Adds an empty result object as the customer attribute of
                // this node in the tree, to make it possible to identify the
                // type of the tree node easily
                trItem.setAttribute("treeItem", new Result());

                trCrid.getTreechildren().appendChild(trItem);

                // The Result is of the type CRIDResult
                if (result.getCRIDResult() != null) {

                    CRIDResult cridResult = result.getCRIDResult();
                    Treechildren trChildren = new Treechildren();
                    trItem.appendChild(trChildren);

                    for (int i3 = 0; i3 < cridResult.getNumCRIDs(); i3++) {
                        ContentReference crid = cridResult.getCRID(i3);

                        String strCridCRID = "crid://" + crid.getAuthority() + "/" + crid.getData();

                        Treeitem trItemCrid = new Treeitem();
                        Treerow trRowCrid = new Treerow();
                        Treecell trc1Crid = new Treecell();
                        Treecell trc2Crid = new Treecell();

                        trc1Crid.setLabel(strCridCRID);
                        trc2Crid.appendChild(new Label("Type: Content Reference"));

                        trRowCrid.appendChild(trc1Crid);
                        trRowCrid.appendChild(trc2Crid);
                        trItemCrid.appendChild(trRowCrid);

                        // Adds an empty contentreference-object as the customer attribute of
                        // this node in the tree, to make it possible to identify the
                        // type of the tree node easily
                        trItemCrid.setAttribute("treeItem", new ContentReference());

                        trItemCrid.setParent(trChildren);
                    }

                    // The Result is of the type LocationResult
                } else if (result.getLocationsResult() != null) {

                    LocationsResult locationsResult = result.getLocationsResult();
                    Treechildren trChildren = new Treechildren();
                    trItem.appendChild(trChildren);

                    for (int i4 = 0; i4 < locationsResult.getNumLocators(); i4++) {
                        Locator locator = locationsResult.getLocator(i4);

                        Treeitem trItemLocator = new Treeitem();
                        Treerow trRowLocator = new Treerow();
                        Treecell trc1Locator = new Treecell();
                        Treecell trc2Locator = new Treecell();

                        trc1Locator.setLabel(locator.getLocator());
                        trc2Locator.setLabel("Type: Locator");

                        trRowLocator.appendChild(trc1Locator);
                        trRowLocator.appendChild(trc2Locator);
                        trItemLocator.appendChild(trRowLocator);

                        // Adds an empty locator-object as the customer attribute of
                        // this node in the tree, to make it possible to identify the
                        // type of the tree node easily
                        trItemLocator.setAttribute("treeItem", new Locator());

                        trItemLocator.setParent(trChildren);
                    }

                } // The Result has no Children
                else {
                }

            }


        } // The table wasnt found in the database
        else {
        }

        // Close this window
        this.detach();
        // Show the new one
        winContentReference.setVisible(true);
    }

    /**
     * Deletes the selected Content Reference Table from the database
     */
    public void delete() {

        logger.log(Level.FINEST, "Method delete is getting executed");

        Listbox lbCrid = (Listbox) getFellow("lbContentReferenceTable");
        Listitem liSelected = lbCrid.getSelectedItem();
        ContentReferencingTableResult crtr = (ContentReferencingTableResult) liSelected.getAttribute("itemID");

        // Retrieve the port of the webservice
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        if (port.deleteContentReferenceTable(crtr.getId())) {
            lbCrid.removeItemAt(liSelected.getIndex());
            ((Button) getFellow("deleteCrid")).setDisabled(true);
            ((Button) getFellow("editCrid")).setDisabled(true);
        } else {
            try {
                Messagebox.show("Content Reference Table could not be deleted", "Error", Messagebox.OK, Messagebox.ERROR);
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Enablse the buttons to delete or edit a crid-table
     */
    public void selectCrid() {
        ((Button) getFellow("deleteCrid")).setDisabled(false);
        ((Button) getFellow("editCrid")).setDisabled(false);
    }

    /**
     * Fills the listbox with the information of all
     * available Content Reference Information Tables of the database
     */
    private void fillCridListbox() {

        Listbox lbCrid = (Listbox) getFellow("lbContentReferenceTable");

        // Retrieve the port of the webservice
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();

        Collection lsCrid = port.getAllContentReferencingTable();
        Iterator it = lsCrid.iterator();
        int i = 0;

        while (it.hasNext()) {
            ContentReferencingTableResult crtr = (ContentReferencingTableResult) it.next();
            i++;

            Listitem li = new Listitem();

            Listcell cell1 = new Listcell();
            Listcell cell2 = new Listcell();
            Listcell cell3 = new Listcell();

            Label lbIndex = new Label(Integer.toString(i));

            Label lbTimestamp = new Label(new Timestamp(crtr.getTime()).toString());
            Label lbName = new Label(crtr.getName());
            li.setAttribute("itemID", crtr);

            cell1.appendChild(lbIndex);
            cell2.appendChild(lbTimestamp);
            cell3.appendChild(lbName);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);

            lbCrid.appendChild(li);
        }
    }

    /*
     * Gets the number of items in a tree
     *
     * @param   tree    Object of the tree too measure
     *
     * @return  indizes of the next item to add in tree
     */
    private int getIndex(Tree tree) {
        int retvalue = 0;

        retvalue = tree.getItemCount() + 1;

        return retvalue;

    }
}
