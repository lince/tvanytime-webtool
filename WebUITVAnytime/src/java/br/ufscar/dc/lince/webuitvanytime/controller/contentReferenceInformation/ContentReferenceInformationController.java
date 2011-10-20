/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lince.webuitvanytime.controller.contentReferenceInformation;

import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.MemberOf;
import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.tvanytime.contentReferencing.CRIDResult;
import bbc.rd.tvanytime.contentReferencing.ContentReferencingTable;
import bbc.rd.tvanytime.contentReferencing.LocationsResult;
import bbc.rd.tvanytime.contentReferencing.Locator;
import bbc.rd.tvanytime.contentReferencing.Result;
import tvanytime.persistence.ws.ContentReferencingTableResult;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import tvanytime.persistence.ws.TVAnytimePersistence;
import tvanytime.persistence.ws.TVAnytimePersistenceService;

/**
 *
 * @author lince
 */
public class ContentReferenceInformationController extends Window {

    /*
     * Initialisation of the logging engine
     */
    static final Logger logger = Logger.getLogger(ContentReferenceInformationController.class.getName());

    public ContentReferenceInformationController() {
        // Set the level of the logger engine
        logger.setLevel(Level.ALL);
    }

    /**
     * Adds a new resultnode to the tree component
     * The node is added on the root level
     *
     * Structure of the tree:
     *
     *      tree
     *          -> treecols
     *          -> treechildren
     *              -> treeitem
     *                  -> treerow
     *                      -> treecell
     */
    public void addResult() {

        logger.log(Level.FINE, "Method addResult started");

        Tree trCrid = (Tree) getFellow("trCrid");
        Textbox tbAuthorityResult = (Textbox) getFellow("tbAuthorityResult").clone();
        Textbox tbDataResult = (Textbox) getFellow("tbDataResult").clone();
        Checkbox cbxComplete = (Checkbox) getFellow("cbxComplete").clone();
        Combobox cbAcquire = (Combobox) getFellow("cbAcquire").clone();
        Combobox cbStatus = (Combobox) getFellow("cbStatus").clone();
        Datebox dbReresolverDate = (Datebox) getFellow("dbReresolverDate").clone();

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
        String strCrid = "crid://" + tbAuthorityResult.getValue() + "/" + tbDataResult.getValue();

        Treeitem trItem = new Treeitem();
        Treerow trRow = new Treerow();
        Treecell trc1 = new Treecell();
        Treecell trc2 = new Treecell();

        // Adds an empty result object as the customer attribute of
        // this node in the tree, to make it possible to identify the
        // type of the tree node easily
        trItem.setAttribute("treeItem", new Result());

        trc1.setLabel(strCrid);

        /*
         * Construction of the rowcell of the resultobject
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
        trCrid.getTreechildren().appendChild(trItem);

        logger.log(Level.FINEST, "Parent of the added item: " + trItem.getParent());
        logger.log(Level.FINEST, "Children of the added item: " + trItem.getChildren());
        logger.log(Level.FINEST, "Attribute treeItem: " + trItem.getAttribute("treeItem").getClass().getName());

        logger.log(Level.FINE, "Method addResult finished");
    }

    /**
     * Adds a crid-item to the content referencing tree
     */
    public void addCrid() {

        logger.log(Level.FINE, "Method addCrid started");

        Tree trCrid = (Tree) getFellow("trCrid");
        Textbox tbAuthority = (Textbox) getFellow("tbAuthority");
        Textbox tbData = (Textbox) getFellow("tbData");

        /*
         * The memberOf-part of the TvAnytime-standard hasnt been implemented 
         * because of a bug in the BBC-Api, when this bug got fixed, 
         * then it can be get activad by just uncommenting the corresponding parts of the code
         */
        //Textbox tbMemberOf = (Textbox) ((Textbox) getFellow("tbMemberOf")).clone();

        // getIndex return the index of the next treeitem
        Integer index = getIndex(trCrid);

        // Change the ID of all the items, to not have duplicated IDs in the
        // namespace of the page
        // Namingconvention: newId = oldId + "_" + number of Listitems
        // e.g.: tbNewTitle = tbNewTitle_1
        // e.g.: cbTitleLanguage = cbTitleLanguage_1

        /*
         * The memberOf-part of the TvAnytime-standard hasnt been implemented
         * because of a bug in the BBC-Api, when this bug got fixed,
         * then it can be get activad by just uncommenting the corresponding parts of the code
         */
        // tbMemberOf.setId(tbMemberOf.getId() + "_" + index.toString());

        // Construction of the CRID-String
        // "CRID://" + Authority name "/" + Content ID;
        // e.g.: crid://bbc.co.uk/21837
        String strCrid = "crid://" + tbAuthority.getValue() + "/" + tbData.getValue();

        Treeitem selectedItem = trCrid.getSelectedItem();

        Treeitem trItem = new Treeitem();
        Treerow trRow = new Treerow();
        Treecell trc1 = new Treecell();
        Treecell trc2 = new Treecell();

        trc1.setLabel(strCrid);
        trc2.appendChild(new Label("Type: Content Reference"));

        /*
         * The memberOf-part of the TvAnytime-standard hasnt been implemented
         * because of a bug in the BBC-Api, when this bug got fixed,
         * then it can be get activad by just uncommenting the corresponding parts of the code
         */
        // If the value of the memberOf-textbox is not an empty string
        // then the textbox gets added to the tree
        // The number of components of the cell is then: 3

//        if (!tbMemberOf.getValue().trim().equals("")) {
//            trc2.appendChild(new Label(", Member of: "));
//            trc2.appendChild(new Label(tbMemberOf.getValue().trim()));
//        }

        trRow.appendChild(trc1);
        trRow.appendChild(trc2);
        trItem.appendChild(trRow);

        // Adds an empty contentreference-object as the customer attribute of
        // this node in the tree, to make it possible to identify the
        // type of the tree node easily
        trItem.setAttribute("treeItem", new ContentReference());

        if (selectedItem.getAttribute("treeItem").getClass().getName().equals(Result.class.getName())
                && selectedItem.getTreechildren() == null) {

            logger.log(Level.FINEST, "Selected items is of type Result and has no children");

            Treechildren trChildren = new Treechildren();
            trChildren.appendChild(trItem);
            trChildren.setParent(selectedItem);

            logger.log(Level.FINEST, "Item added as child to selected item");
        } else if (selectedItem.getAttribute("treeItem").getClass().getName().equals(Result.class.getName())) {

            logger.log(Level.FINEST, "Selected items is of type Result and has children");
            selectedItem.getTreechildren().appendChild(trItem);

            // The selected item is of type Locator
        } else {
            logger.log(Level.FINEST, "Selected items is of type ContentReference");
            selectedItem.getParent().appendChild(trItem);
        }

        try {
            setGroupboxes(2);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        logger.log(Level.FINEST, "Parent of the selected item: " + selectedItem.getParent());
        logger.log(Level.FINEST, "Children of the selected item: " + selectedItem.getChildren());
        logger.log(Level.FINEST, "Attribute treeItem: " + selectedItem.getAttribute("treeItem").getClass().getName());

        logger.log(Level.FINEST, "Parent of the added item: " + trItem.getParent());
        logger.log(Level.FINEST, "Children of the added item: " + trItem.getChildren());
        logger.log(Level.FINEST, "Attribute treeItem: " + trItem.getAttribute("treeItem").getClass().getName());

        logger.log(Level.FINE, "Method addCrid finished");
    }

    /**
     * Adds a locator-item to the content referencing tree
     */
    public void addLocator() {

        logger.log(Level.FINE, "Method addLocator started");

        Tree trCrid = (Tree) getFellow("trCrid");
        Textbox tbLocator = (Textbox) getFellow("tbLocator");

        Treeitem selectedItem = trCrid.getSelectedItem();

        Treeitem trItem = new Treeitem();
        Treerow trRow = new Treerow();
        Treecell trc1 = new Treecell();
        Treecell trc2 = new Treecell();

        trc1.setLabel(tbLocator.getValue());
        trc2.setLabel("Type: Locator");

        trRow.appendChild(trc1);
        trRow.appendChild(trc2);
        trItem.appendChild(trRow);

        // Adds an empty locator-object as the customer attribute of
        // this node in the tree, to make it possible to identify the
        // type of the tree node easily
        trItem.setAttribute("treeItem", new Locator());


        // The selected item is of type Result and has no children
        if (selectedItem.getAttribute("treeItem").getClass().getName().equals(Result.class.getName())
                && selectedItem.getTreechildren() == null) {
            logger.log(Level.FINEST, "Selected items is of type Result and has no children");

            Treechildren trChildren = new Treechildren();
            trChildren.appendChild(trItem);
            trChildren.setParent(selectedItem);

            logger.log(Level.FINEST, "Item added as child to selected item");

            // The selected item is of type Result and has children
        } else if (selectedItem.getAttribute("treeItem").getClass().getName().equals(Result.class.getName())) {

            logger.log(Level.FINEST, "Selected items is of type Result and has children");
            selectedItem.getTreechildren().appendChild(trItem);

            // The selected item is of type Locator
        } else {
            logger.log(Level.FINEST, "Selected items is of type Locator");
            selectedItem.getParent().appendChild(trItem);
        }

        try {
            // The locator-groupbox is shown
            setGroupboxes(3);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        logger.log(Level.FINE, "Method addCrid finished");
    }

    /**
     * Methode is executed when a treeitem gets selected
     */
    public void onSelectCridTree() {
        logger.log(Level.FINE, "onSelectCridTree raised");

        Tree trCrid = (Tree) getFellow("trCrid");
        Treeitem selectedItem = trCrid.getSelectedItem();

        // Activate the delete-button
        ((Button) getFellow("btDelete")).setDisabled(false);

        // Get the typ object of the customer attribute "treeItem"
        // to identify the type of the node
        Object selectedType = selectedItem.getAttribute("treeItem");
        String strSelectedType = selectedType.getClass().getName();
        logger.log(Level.FINEST, "Selected type: " + strSelectedType);

        try {
            // Selected item type is of type "Result"
            if (strSelectedType.equals(Result.class.getName())) {

                // The result item has no children nodes (only one empty one)
                if (selectedItem.getChildren().size() <= 1) {
                    // The locator and the crid groupbox are shown
                    setGroupboxes(1);

                    /* The first child of the result is a contentreference,
                     * so all other children have to be of the type contentreference.
                     * This will be assured by making only the contentreference-combobox visible.
                     */
                } else if (ContentReference.class.getName().equals(selectedItem.getChildren().get(0).getClass())) {
                    // The contentreference-groupbox is shown
                    setGroupboxes(2);

                    /* The first child of the result is a locator,
                     * so all other children have to be of the type locator.
                     * This will be assured by making only the locator-combobox visible.
                     */
                } else if (Locator.class.getName().equals(selectedItem.getChildren().get(0).getClass())) {
                    // The locator-groupbox is shown
                    setGroupboxes(3);
                }

                /*
                 * The selected node is a locator.
                 */
            } else if (strSelectedType.equals(Locator.class.getName())) {
                // The locator groupbox is shown
                setGroupboxes(3);

                /*
                 * The selected node is a content reference (CRID).
                 */
            } else if (strSelectedType.equals(ContentReference.class.getName())) {
                // The contentreference-groupbox is shown
                setGroupboxes(2);
            }
        } catch (Exception ex) {
            // A wrong value for the method setGroupboxes was set
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method constructs the ContentReferenceTable out of the userinterface
     * and saves the xml-string through a webservice method to the database
     */
    public void saveContentReferenceTable() {

        logger.log(Level.FINE, "Method saveContentReferenceTable started");

        Tree trCrid = (Tree) getFellow("trCrid");
        Textbox tbCridName = (Textbox) getFellow("tbCridName");

        Treechildren trChildren = trCrid.getTreechildren();
        Collection children = trChildren.getItems();
        Iterator it = children.iterator();
        ContentReferencingTable contRefTable = new ContentReferencingTable();

        /*
         * Setting the content reference table to version 1.2, as described by:
         *
         * SP004 Content Referencing (Normative), version 1.2
         * SP004 Content Referencing - Corrigenda 1 to SP004 v.12
         */
        contRefTable.setVersion((float) 1.2);

        logger.log(Level.FINEST, "Children of the tree: " + children);
        it.next();

        // Iteration through all the items of the tree component
        while (it.hasNext()) {

            Treeitem item = (Treeitem) it.next();

            logger.log(Level.FINEST, "Item in tree found: " + item);
            logger.log(Level.FINEST, "Item attribute \"treeItem\": " + item.getAttribute("treeItem"));
            logger.log(Level.FINEST, "Children of the item: " + item.getChildren());

            // Treeitem is of type Result
            if (item.getAttribute("treeItem").getClass().getName().equals(Result.class.getName())) {

                Result result = new Result();

                // Extracting the Result components
                List resultComponents = ((Treecell) ((Treerow) item.getChildren().get(0)).getChildren().get(1)).getChildren();
                Checkbox cbxComplete = (Checkbox) resultComponents.get(1);
                Combobox cbAcquire = (Combobox) resultComponents.get(3);
                Combobox cbStatus = (Combobox) resultComponents.get(5);
                Datebox dbReresolverDate = (Datebox) resultComponents.get(7);

                // Setting the Result object attribute
                try {
                    result.setCRID(new ContentReference(item.getLabel()));
                    result.setComplete(cbxComplete.isChecked());
                    result.setAcquire(cbAcquire.getSelectedItem().getLabel());
                    result.setStatus(cbStatus.getSelectedItem().getLabel());
                    result.setReresolveDate(dbReresolverDate.getValue());

                } catch (TVAnytimeException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }

                // Threat the children of the Result (if there are any)
                Treechildren treeChildren = item.getTreechildren();

                // The Result item has children
                if (treeChildren != null) {

                    List resultChildren = treeChildren.getChildren();

                    // Get the first child of the Result item
                    Treeitem childItem = (Treeitem) resultChildren.get(0);

                    // The children of the result item are of the type ContentReference
                    // If the first children of the result item is of the type ContentReference
                    // then all the children are of the type ContentReference
                    if (childItem.getAttribute("treeItem").getClass().getName().equals(ContentReference.class.getName())) {

                        CRIDResult cridResult = new CRIDResult();
                        logger.log(Level.FINEST, "Children of the Content Reference-item: " + childItem.getChildren());

                        for (int i = 0; i < resultChildren.size(); i++) {

                            childItem = (Treeitem) resultChildren.get(i);
                            List cridComponents = ((Treecell) ((Treerow) childItem.getChildren().get(0)).getChildren().get(1)).getChildren();

                            // Construction of the CRID-String
                            // "CRID://" + Authority name "/" + Content ID;
                            // e.g.: crid://bbc.co.uk/21837
                            String strCrid = ((Treecell) ((Treerow) childItem.getChildren().get(0)).getChildren().get(0)).getLabel();

                            try {

                                // The tree item of the type ContentReference is of the extended type MemberOf
                                // If the number of labels (components) of the cell is more than 1, then its a MemberOf type
                                if (cridComponents.size() > 2) {
                                    MemberOf member = new MemberOf();
                                    String strIndex = ((Label) cridComponents.get(2)).getValue();
                                    member.setIndex(Integer.parseInt(strIndex));
                                    member.setCRID(strCrid.trim());

                                    cridResult.addCRID(member);
                                    logger.log(Level.FINEST, "MemberOf got added to cridResult: " + member);

                                    // The tree item is a normal Content Reference item
                                } else {
                                    ContentReference crid = new ContentReference();
                                    crid.setCRID(strCrid);

                                    cridResult.addCRID(crid);
                                }

                                // The children of the result item are of the type Locator
                            } catch (TVAnytimeException ex) {
                                Logger.getLogger(ContentReferenceInformationController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }

                        // Append the CRIDResult to the Result object
                        result.setCRIDResult(cridResult);

                        // The children of the result item are of the type Location
                        // If the first children of the result item is of the type Location
                        // then all the children are of the type Location
                    } else {

                        LocationsResult locationsResult = new LocationsResult();

                        for (int i = 0; i < resultChildren.size(); i++) {

                            childItem = (Treeitem) resultChildren.get(i);
                            String strLocator = ((Treecell) ((Treerow) (childItem.getChildren().get(0))).getChildren().get(0)).getLabel();
                            locationsResult.addLocator(new Locator(strLocator));
                        }

                        // Append the CRIDResult to the Result object
                        result.setLocationsResult(locationsResult);
                    }
                }

                contRefTable.addResult(result);
            }
        }

        logger.log(Level.INFO, "Content Reference Table to be saved:" + contRefTable.toXML());

        // To retrieve the port-object of the webservice
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();


        if (((Button) getFellow("btSave")).getLabel().equals("Save Content Reference Table")) {
            // Persisting the xml-string of the segment information table through the wegservice
            if (port.persistContentReferencingTable(tbCridName.getValue().trim(), contRefTable.toXML())) {
                try {
                    Messagebox.show("Content Referencing Table was saved successfully!", "Information", Messagebox.OK, Messagebox.INFORMATION);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ContentReferenceInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Messagebox.show("Content Referencing Table could not get saved!", "Error", Messagebox.OK, Messagebox.ERROR);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ContentReferenceInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            // Update the table in the database
            ContentReferencingTableResult crtr = (ContentReferencingTableResult) getFellow("trCrid").getAttribute("contentReferenceTableResult");
            if (port.updateContentReferencingTable(crtr.getId(), tbCridName.getValue().trim(), contRefTable.toXML())) {
                try {
                    Messagebox.show("Content Referencing Table was updated successfully!", "Information", Messagebox.OK, Messagebox.INFORMATION);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ContentReferenceInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Messagebox.show("Content Referencing Table could not get updated!", "Error", Messagebox.OK, Messagebox.ERROR);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ContentReferenceInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        logger.log(Level.FINE, "Method saveContentReferenceTable finished");

        // Close the window
        this.detach();
        // Open the main window
        Window winContentReference = (Window) Executions.createComponents("contentReferenceInformationMain.zul", null, null);
        winContentReference.setVisible(true);
    }

    /**
     * Unselects all items of the tree
     * and sets the view to state 0,
     * so only the result-groupbox is shown
     */
    public void unselect() {
        Tree trCrid = (Tree) getFellow("trCrid");
        try {
            // Shows the result-groupbox
            setGroupboxes(0);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        // The selected item of the tree view gets unselected
        trCrid.setSelectedItem(null);

        // Deactivate the delete-button
        ((Button) getFellow("btDelete")).setDisabled(true);
    }

    /**
     * Deletes the selected item from the tree
     * and deactivates the delete-button
     */
    public void delete() {
        ((Tree) getFellow("trCrid")).getSelectedItem().setParent(null);
        ((Button) getFellow("btDelete")).setDisabled(true);
    }

    /**
     * Sets the visual appearance of the groupboxes
     *
     *      state 0: groupbox result visible
     *      state 1: groupbox crid and locator visible
     *      state 2: groupbox crid visible
     *      state 3: groupbox locator visible
     *
     * @param   state   int-value of the state  (0 < state =< 3)
     */
    private void setGroupboxes(int state) throws Exception {

        Groupbox gbCrid = (Groupbox) getFellow("gbCrid");
        Groupbox gbLocator = (Groupbox) getFellow("gbLocator");
        Groupbox gbResult = (Groupbox) getFellow("gbResult");
        Vbox vbCridLocator = (Vbox) getFellow("vbCridLocator");

        switch (state) {

            case 0:
                // The result-groupbox is shown
                gbResult.setVisible(true);
                vbCridLocator.setVisible(false);
                gbLocator.setVisible(false);
                gbCrid.setVisible(false);
                break;

            case 1:
                // The groupboxes crid and locator are shown
                gbResult.setVisible(false);
                vbCridLocator.setVisible(true);
                gbLocator.setVisible(true);
                gbCrid.setVisible(true);
                break;

            case 2:
                // The groupbox crid is shown
                gbResult.setVisible(false);
                vbCridLocator.setVisible(true);
                gbLocator.setVisible(false);
                gbCrid.setVisible(true);
                break;

            case 3:
                // The groupbox crid is shown
                gbResult.setVisible(false);
                vbCridLocator.setVisible(true);
                gbLocator.setVisible(true);
                gbCrid.setVisible(false);
                break;
            default:
                // A value which wasnt between 0 and 3 was set. This raises an exception.
                throw new Exception("State of setGroupboxes has to be between 0 and 3\n It was set " + state);
        }

        logger.log(Level.FINEST, "Groupboxes state was set to " + Integer.toString(state));
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
