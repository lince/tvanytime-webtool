package br.ufscar.dc.lince.webuitvanytime.controller.segmentInformation;

import bbc.rd.mpeg7.MPEG7MediaLocator;
import bbc.rd.tvanytime.RelatedMaterial;
import bbc.rd.tvanytime.Synopsis;
import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.tvanytime.Title;
import bbc.rd.tvanytime.URI;
import bbc.rd.tvanytime.segmentInformation.BasicSegmentDescription;
import bbc.rd.tvanytime.segmentInformation.SegmentReference;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import br.ufscar.dc.lince.tvanytime.core.TVAnytimeHowRelatedToolboxExtended;
import br.ufscar.dc.lince.tvanytime.core.Toolbox;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author lince
 *
 */
public class NewSegmentDescriptionController extends Window {

    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(NewSegmentDescriptionController.class.getName());

    /*
     * Adds the controls of the userinput to the list
     */
    public void addTitle() {

        Listbox lbTitle = (Listbox) getFellow("lbTitle");

        // Get the references to all the controls, which will be used,
        // in the userinterface
        Textbox tbNewTitle = (Textbox) getFellow("tbNewTitle").clone();
        Include incl = (Include) getFellow("cbTitleLanguage");
        Combobox cbTitleLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
        cbTitleLanguage.setId("cbTitleLanguage");
        Combobox cbTitleTyp = (Combobox) (getFellow("cbTitleTyp")).clone();

        // getIndex return the index of the next listitem
        Integer index = getIndex("lbTitle");

        // A listitem gets added to a list
        // A listitem is constructed out of listcells
        // One listcell is one field in the table and can have varios controlls
        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();
        Listcell cell3 = new Listcell();
        Listcell cell4 = new Listcell();

        // Change the ID of all the items, to not have duplicated IDs in the
        // namespace of the page
        // Namingconvention: newId = oldId + "_" + number of Listitems
        // e.g.: tbNewTitle = tbNewTitle_1
        // e.g.: cbTitleLanguage = cbTitleLanguage_1
        tbNewTitle.setId(tbNewTitle.getId() + "_" + index.toString());
        cbTitleLanguage.setId(cbTitleLanguage.getId() + "_" + index.toString());
        cbTitleTyp.setId(cbTitleTyp.getId() + "_" + index.toString());

        // The controlls get appended to the cells and the cells get appended
        // to the listitem and then the parent-pointer of the listitem is set
        // to the listbox where it should be included
        // The order of the appending of the cells is essential how the
        // controlls get mapped to the different columns of the list
        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(tbNewTitle);
        cell3.appendChild(cbTitleLanguage);
        cell4.appendChild(cbTitleTyp);
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.appendChild(cell3);
        li.appendChild(cell4);
        li.setParent(lbTitle);
    }

    public void addSynopsis() {
        Listbox lbSynopsis = (Listbox) getFellow("lbSynopsis");

        Textbox tbNewSynopsis = (Textbox) getFellow("tbNewSynopsis").clone();
        Include incl = (Include) getFellow("cbSynopsisLanguage");
        Combobox cbSynopsisLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
        cbSynopsisLanguage.setId("cbSynopsisLanguage");
        Combobox cbSynopsisLength = (Combobox) (getFellow("cbSynopsisLength")).clone();

        Integer index = getIndex("lbSynopsis");

        // A listitem gets added to a list
        // A listitem is constructed out of listcells
        // One listcell is one field in the table and can have varios controlls
        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();
        Listcell cell3 = new Listcell();
        Listcell cell4 = new Listcell();

        // Change the ID of all the items, to not have duplicated IDs in the
        // namespace of the page
        // Namingconvention: newId = oldId + "_" + number of Listitems
        // e.g.: tbNewTitle = tbNewTitle_1
        // e.g.: cbTitleLanguage = cbTitleLanguage_1
        tbNewSynopsis.setId(tbNewSynopsis.getId() + "_" + index.toString());
        cbSynopsisLanguage.setId(cbSynopsisLanguage.getId() + "_" + index.toString());
        cbSynopsisLength.setId(cbSynopsisLength.getId() + "_" + index.toString());

        // The controlls get appended to the cells and the cells get appended
        // to the listitem and then the parent-pointer of the listitem is set
        // to the listbox where it should be included
        // The order of the appending of the cells is essential how the
        // controlls get mapped to the different columns of the list
        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(tbNewSynopsis);
        cell3.appendChild(cbSynopsisLanguage);
        cell4.appendChild(cbSynopsisLength);
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.appendChild(cell3);
        li.appendChild(cell4);
        li.setParent(lbSynopsis);
    }

    public void addRelatedMaterial() {
        Listbox lbRelated = (Listbox) getFellow("lbRelatedMaterial");

        Combobox cbHowRelated = (Combobox) getFellow("cbHowRelated").clone();
        Textbox tbRelationName = (Textbox) getFellow("tbRelationName").clone();
        Combobox cbSegmentType = (Combobox) getFellow("cbSegmentType").clone();
        Textbox tbSourceLocator = (Textbox) getFellow("tbSourcelocator").clone();
        Radiogroup rbLocatorType = (Radiogroup) getFellow("rbLocatorType");
        Textbox tbLocator = (Textbox) getFellow("tbLocator").clone();
        Textbox tbPromotionalText = (Textbox) getFellow("tbPromotionalText").clone();

        // getIndex return the index of the next listitem
        Integer index = getIndex("lbRelatedMaterial");

        // A listitem gets added to a list
        // A listitem is constructed out of listcells
        // One listcell is one field in the table and can have varios controlls
        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();
        Listcell cell3 = new Listcell();
        Listcell cell4 = new Listcell();
        Listcell cell5 = new Listcell();
        Listcell cell6 = new Listcell();
        Listcell cell7 = new Listcell();
        Listcell cell8 = new Listcell();

        // Change the ID of all the items, to not have duplicated IDs in the
        // namespace of the page
        // Namingconvention: newId = oldId + "_" + number of Listitems
        // e.g.: tbNewTitle = tbNewTitle_1
        // e.g.: cbTitleLanguage = cbTitleLanguage_1
        cbHowRelated.setId(cbHowRelated.getId() + "_" + index.toString());
        tbRelationName.setId(tbRelationName.getId() + "_" + index.toString());
        cbSegmentType.setId(cbSegmentType.getId() + "_" + index.toString());
        tbSourceLocator.setId(tbSourceLocator.getId() + "_" + index.toString());
        tbLocator.setId(tbLocator.getId() + "_" + index.toString());
        tbPromotionalText.setId(tbPromotionalText.getId() + "_" + index.toString());

        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(cbHowRelated);
        cell3.appendChild(tbRelationName);
        cell4.appendChild(tbSourceLocator);
        cell6.appendChild(cbSegmentType);
        

        // The locator can be a medialocator or sourcemedialocator
        // depending on the rardiobutton, the right label is seleted
        Label lbLocatorTyp = null;
        if (rbLocatorType.getSelectedIndex() == 0) {
            lbLocatorTyp = new Label("Medialocator");
        } else {
            lbLocatorTyp = new Label("Segment Reference");
        }
        cell5.appendChild(lbLocatorTyp);
        cell7.appendChild(tbLocator);
        cell8.appendChild(tbPromotionalText);
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.appendChild(cell3);
        li.appendChild(cell4);
        li.appendChild(cell5);
        li.appendChild(cell6);
        li.appendChild(cell7);
        li.appendChild(cell8);
        li.setParent(lbRelated);
    }

    /*
     * Gets the highest number of the indizes field of listbox
     *
     * @param listbox id-string of a listbox
     *
     * @return indizes of the next item of the listbox
     */
    private int getIndex(String listbox) {
        int retvalue = 0;
        Listbox lb = (Listbox) getFellow(listbox);

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


    /*
     * Saves the information of the controls in the window to an object
     * and adds a new item with the object data to window segmentInformation
     */
    public void save() {

        // Set the log-level
        logger.setLevel(Level.ALL);
        logger.log(Level.FINE, "Save method started");

        BasicSegmentDescription bsd = new BasicSegmentDescription();

        Listbox lbTitle = (Listbox) getFellow("lbTitle");
        Listbox lbSynopsis = (Listbox) getFellow("lbSynopsis");
        Listbox lbRelatedMaterial = (Listbox) getFellow("lbRelatedMaterial");

        /**
         * Collecting the information of the titlelistbox
         */
        // The items of the listbox start with an index of 1 !!!!
        // The element lbPerson.getItemAtIndex(0) exists, but its empty
        // listboxItem[]={[]} = this information is important for all listboxes
        for (int i = 1; i < lbTitle.getItemCount(); i++) {
            Listitem it = (Listitem) lbTitle.getItemAtIndex(i);
            List children = it.getChildren();

            Title title = new Title();

            title.setText(((Textbox) ((Listcell) children.get(1)).getFirstChild()).getValue());

            // A language-item of the combobox has to be selected
            Comboitem cbi = ((Combobox) ((Listcell) children.get(2)).getFirstChild()).getSelectedItem();
            if (cbi != null) {
                title.setLanguage(cbi.getLabel());
            }

            try {
                // A combobox-item has to be selected
                int iTitle = ((Combobox) ((Listcell) children.get(3)).getFirstChild()).getSelectedIndex();
                if (iTitle > 0) {
                    title.setType(iTitle);
                }
            } catch (TVAnytimeException ex) {
                logger.log(Level.SEVERE, null, ex);
            }

            bsd.addTitle(title);
        }
        logger.log(Level.FINER, "Titles of SegmentDescription saved");

        /**
         * Collecting the information of the synopsislistbox
         */
        // The items of the listbox start with an index of 1 !!!!
        // The element lbPerson.getItemAtIndex(0) exists, but its empty
        // listboxItem[]={[]} = this information is important for all listboxes
        for (int i = 1; i < lbSynopsis.getItemCount(); i++) {
            Listitem it = (Listitem) lbSynopsis.getItemAtIndex(i);
            List children = it.getChildren();

            Synopsis synopsis = new Synopsis();

            synopsis.setText(((Textbox) ((Listcell) children.get(1)).getFirstChild()).getValue());

            // A combobox-item has to be selected
            Comboitem cbi = ((Combobox) ((Listcell) children.get(2)).getFirstChild()).getSelectedItem();
            if (cbi != null) {
                synopsis.setLanguage(cbi.getLabel());
            }

            try {
                logger.log(Level.FINEST,"Synopsis-length: "+((Combobox) ((Listcell) children.get(3)).getFirstChild()).getSelectedIndex());
                synopsis.setLength(((Combobox) ((Listcell) children.get(3)).getFirstChild()).getSelectedIndex());
            } catch (TVAnytimeException ex) {
                logger.log(Level.SEVERE, null, ex);
            }

            bsd.addSynopsis(synopsis);
        }
        logger.log(Level.FINER, "Synopsis of SegmentDescription saved");

        /**
         * Collecting the information of the relatedmaterial-listbox
         */
        // The items of the listbox start with an index of 1 !!!!
        // The element lbPerson.getItemAtIndex(0) exists, but its empty
        // listboxItem[]={[]} = this information is important for all listboxes
        /**
         *  Final prefix of a HowRelated-HREF:
         *      This String is defined in
         *          bbc.rd.tvanytime.RelatedMaterial: String hrefCS
         */
        final String howRelatedCS = "urn:tva:metadata:cs:HowRelatedCS:2002:";

        for (int i = 1; i < lbRelatedMaterial.getItemCount(); i++) {
            logger.log(Level.FINEST, "Start saving of related material");
            try {
                Listitem it = (Listitem) lbRelatedMaterial.getItemAtIndex(i);
                List children = it.getChildren();
                RelatedMaterial related = new RelatedMaterial();

                Comboitem cbi = ((Combobox) (((Listcell) children.get(1)).getFirstChild())).getSelectedItem();
                if (cbi != null) {
                    related.setHowRelatedHREF(howRelatedCS + cbi.getLabel());
                }
                related.setName(((Textbox) ((Listcell) children.get(2)).getFirstChild()).getValue());
                SegmentReference segref = new SegmentReference();

                // If the selected item is the second one of the comboxbox (index=1),
                // then segment-group was selected -> Combobox sepcification see "newSegmentDescription.zul"
                if (((Combobox) ((Listcell) children.get(5)).getFirstChild()).getSelectedIndex() == 1) {
                    segref.setSegmentType(segref.SEGMENT_GROUP);
                } else {
                    segref.setSegmentType(segref.SEGMENT);
                }

                segref.setRef(((Textbox) ((Listcell) children.get(6)).getFirstChild()).getValue());
                related.setSegmentReference(segref);

                // The MediaLocator or SourceMediaLocator - property is set, with the same data
                // -> an URI object created out of the textbox information
                // the uri has to be formed as following: <text> : <text>
                // This is specifief by  bbc.rd.tvanytime.URI
                if (!((Textbox) (((Listcell) children.get(6)).getFirstChild())).getValue().trim().equals("")) {
                    if (((Label) (((Listcell) children.get(4)).getFirstChild())).getValue().equals("Medialocator") == true) {
                        related.setMediaLocator(new MPEG7MediaLocator(new URI(((Textbox) (((Listcell) children.get(6)).getFirstChild())).getValue())));
                    } else {                        
                        related.setSegmentReference(new SegmentReference(0, ((Textbox) (((Listcell) children.get(6)).getFirstChild())).getValue()));
                    }
                }
                related.setSourceMediaLocator(new MPEG7MediaLocator(new URI(((Textbox) (((Listcell) children.get(3)).getFirstChild())).getValue())));
                related.setPromotionalText(((Textbox) (((Listcell) children.get(7)).getFirstChild())).getValue());

                bsd.addRelatedMaterial(related);
                logger.log(Level.FINER, "Related material of SegmentDescription saved");

            } catch (TVAnytimeException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }


        // To add the created segment description-object to the table of the main window
        addBasicDescription(bsd);

        logger.log(Level.FINE, "Save method finished");

        // Close the window
        Window winNewCredit = (Window) getFellow(super.getId());
        winNewCredit.detach();

    }

    /*
     * Adds the constructed basic description to the window "SegmentInformation"
     * The data object is added to the list as attribute named "segmentDescriptionItem"
     *
     * @param   BasicSegmentDescription     the datastructure for a description of the segment
     */
    private void addBasicDescription(BasicSegmentDescription bsd) {
        Listbox lbSegmentAdd = (Listbox) getDesktop().getPage("segmentInformationMain").getFellow("winSegmentInformation").getFellow("lbSegmentAdd");
        Listitem it = (Listitem) lbSegmentAdd.getItemAtIndex(0);
        List children = it.getChildren();
        ((Checkbox) ((Vbox) ((Listcell) children.get(3)).getFirstChild()).getChildren().get(0)).setChecked(true);
        it.setAttribute("segmentDescriptionItem", bsd.clone());

        logger.log(Level.FINER, ((BasicSegmentDescription) it.getAttribute("segmentDescriptionItem")).toXML(0));
    }

    /**
     * Event is fired when all child components of the window, this class
     * was assigned to, have been created
     * 
     * @param event
     */
    public void onCreate(org.zkoss.zk.ui.event.Event event) {

        fillCbHowRelated();
    }

    /**
     * Method to close the modal window
     */
    public void cancel() {
        this.detach();
    }

    public void edit(BasicSegmentDescription bsd) {

        // Adds the title information to edit
        editTitle(bsd);
        // Adds the synopsis information to edit
        editSynopsis(bsd);
        // Adds the related material information to edit
        editRelatedMaterial(bsd);

    }

    private void editTitle(BasicSegmentDescription bsd) {

        logger.log(Level.FINER, "editTitle started");

        Listbox lbTitle = (Listbox) getFellow("lbTitle");

        for (int i = 0; i < bsd.getNumTitles(); i++) {

            Textbox tbNewTitle = new Textbox();
            Include incl = (Include) getFellow("cbTitleLanguage");
            Combobox cbTitleLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
            cbTitleLanguage.setId("cbTitleLanguage");
            Combobox cbTitleTyp = (Combobox) (getFellow("cbTitleTyp")).clone();

            // Set the values of the title object
            Title title = bsd.getTitle(i);
            tbNewTitle.setValue(title.getText());

            // Select the corresponding items of the comboboxes
            cbTitleLanguage = Toolbox.selectCombobox(cbTitleLanguage, title.getLanguage());
            cbTitleTyp.setSelectedIndex(title.getType());

            // getIndex return the index of the next listitem
            Integer index = getIndex("lbTitle");

            // A listitem gets added to a list
            // A listitem is constructed out of listcells
            // One listcell is one field in the table and can have varios controlls
            Listitem li = new Listitem();
            Listcell cell1 = new Listcell();
            Listcell cell2 = new Listcell();
            Listcell cell3 = new Listcell();
            Listcell cell4 = new Listcell();

            // Change the ID of all the items, to not have duplicated IDs in the
            // namespace of the page
            // Namingconvention: newId = oldId + "_" + number of Listitems
            // e.g.: tbNewTitle = tbNewTitle_1
            // e.g.: cbTitleLanguage = cbTitleLanguage_1
            tbNewTitle.setId(tbNewTitle.getId() + "_" + index.toString());
            cbTitleLanguage.setId(cbTitleLanguage.getId() + "_" + index.toString());
            cbTitleTyp.setId(cbTitleTyp.getId() + "_" + index.toString());

            // The controlls get appended to the cells and the cells get appended
            // to the listitem and then the parent-pointer of the listitem is set
            // to the listbox where it should be included
            // The order of the appending of the cells is essential how the
            // controlls get mapped to the different columns of the list
            cell1.appendChild(new Label(new Integer(index).toString()));
            cell2.appendChild(tbNewTitle);
            cell3.appendChild(cbTitleLanguage);
            cell4.appendChild(cbTitleTyp);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);
            li.appendChild(cell4);
            li.setParent(lbTitle);
        }
    }

    private void editSynopsis(BasicSegmentDescription bsd) {

        logger.log(Level.FINER, "editSynopsis started");

        Listbox lbSynopsis = (Listbox) getFellow("lbSynopsis");

        for (int i = 0; i < bsd.getNumSynopses(); i++) {

            Textbox tbNewSynopsis = new Textbox();
            tbNewSynopsis.setId("tbNewSynopis");
            Include incl = (Include) getFellow("cbSynopsisLanguage").clone();
            Combobox cbSynopsisLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
            cbSynopsisLanguage.setId("cbSynopsisLanguage");
            Combobox cbSynopsisLength = (Combobox) (getFellow("cbSynopsisLength")).clone();

            logger.log(Level.FINEST, "editSynopsis: componets created");

            // Set the values of the components corresponding to the description object
            Synopsis synopsis = bsd.getSynopsis(i);
            tbNewSynopsis.setValue(synopsis.getText());
            cbSynopsisLanguage = Toolbox.selectCombobox(cbSynopsisLanguage, synopsis.getLanguage());
            cbSynopsisLength.setSelectedIndex(synopsis.getLength());

            Integer index = getIndex("lbSynopsis");

            // A listitem gets added to a list
            // A listitem is constructed out of listcells
            // One listcell is one field in the table and can have varios controlls
            Listitem li = new Listitem();
            Listcell cell1 = new Listcell();
            Listcell cell2 = new Listcell();
            Listcell cell3 = new Listcell();
            Listcell cell4 = new Listcell();

            // Change the ID of all the items, to not have duplicated IDs in the
            // namespace of the page
            // Namingconvention: newId = oldId + "_" + number of Listitems
            // e.g.: tbNewTitle = tbNewTitle_1
            // e.g.: cbTitleLanguage = cbTitleLanguage_1
            tbNewSynopsis.setId(tbNewSynopsis.getId() + "_" + index.toString());
            cbSynopsisLanguage.setId(cbSynopsisLanguage.getId() + "_" + index.toString());
            cbSynopsisLength.setId(cbSynopsisLength.getId() + "_" + index.toString());

            // The controlls get appended to the cells and the cells get appended
            // to the listitem and then the parent-pointer of the listitem is set
            // to the listbox where it should be included
            // The order of the appending of the cells is essential how the
            // controlls get mapped to the different columns of the list
            cell1.appendChild(new Label(new Integer(index).toString()));
            cell2.appendChild(tbNewSynopsis);
            cell3.appendChild(cbSynopsisLanguage);
            cell4.appendChild(cbSynopsisLength);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);
            li.appendChild(cell4);
            li.setParent(lbSynopsis);
        }

        logger.log(Level.FINER, "editSynopsis finished");
    }


    private void editRelatedMaterial(BasicSegmentDescription bsd) {

        logger.log(Level.FINER, "editRelated started");

        Listbox lbRelated = (Listbox) getFellow("lbRelatedMaterial");

        for (int i = 0; i < bsd.getNumRelatedMaterials(); i++) {

            Combobox cbHowRelated = (Combobox) getFellow("cbHowRelated").clone();
            Textbox tbRelationName = (Textbox) getFellow("tbRelationName").clone();
            Combobox cbSegmentType = (Combobox) getFellow("cbSegmentType").clone();
            Textbox tbSourcelocator = (Textbox) getFellow("tbSourcelocator").clone();
            Textbox tbLocator = (Textbox) getFellow("tbLocator").clone();
            Textbox tbPromotionalText = (Textbox) getFellow("tbPromotionalText").clone();

            //// Set the values of the components corresponding to the description object
            RelatedMaterial rm = bsd.getRelatedMaterial(i);

            // Fill the combobox with the related information
            fillCbHowRelated(cbHowRelated);
            cbHowRelated = Toolbox.selectCombobox(cbHowRelated, rm.getHowRelated());
            tbRelationName.setValue(rm.getName());
            // The locator can be a medialocator or sourcemedialocator
            // depending on the rardiobutton, the right label is seleted
            Label lbLocatorTyp = new Label("Locator type not set");
            if (rm.getSegmentReference() != null) {
                cbSegmentType.setSelectedIndex(rm.getSegmentReference().getSegmentType());
                tbSourcelocator.setValue(rm.getSegmentReference().getRef());
                // Segment reference is of type medialocator == 0
                if (rm.getSegmentReference().getSegmentType() == 0) {
                    lbLocatorTyp = new Label("Medialocator");
                    tbLocator.setValue(rm.getMediaLocator().getMediaURI().getURI());
                } else { // Segment reference is of type sourcelocator == 1
                    lbLocatorTyp = new Label("Sourcemedialocator");
                    tbLocator.setValue(rm.getSourceMediaLocator().getMediaURI().getURI());
                }
            }
            tbPromotionalText.setValue(rm.getPromotionalText());

            // getIndex return the index of the next listitem
            Integer index = getIndex("lbRelatedMaterial");

            // A listitem gets added to a list
            // A listitem is constructed out of listcells
            // One listcell is one field in the table and can have varios controlls
            Listitem li = new Listitem();
            Listcell cell1 = new Listcell();
            Listcell cell2 = new Listcell();
            Listcell cell3 = new Listcell();
            Listcell cell4 = new Listcell();
            Listcell cell5 = new Listcell();
            Listcell cell6 = new Listcell();
            Listcell cell7 = new Listcell();
            Listcell cell8 = new Listcell();

            // Change the ID of all the items, to not have duplicated IDs in the
            // namespace of the page
            // Namingconvention: newId = oldId + "_" + number of Listitems
            // e.g.: tbNewTitle = tbNewTitle_1
            // e.g.: cbTitleLanguage = cbTitleLanguage_1
            cbHowRelated.setId(cbHowRelated.getId() + "_" + index.toString());
            tbRelationName.setId(tbRelationName.getId() + "_" + index.toString());
            cbSegmentType.setId(cbSegmentType.getId() + "_" + index.toString());
            tbSourcelocator.setId(tbSourcelocator.getId() + "_" + index.toString());
            tbLocator.setId(tbLocator.getId() + "_" + index.toString());
            tbPromotionalText.setId(tbPromotionalText.getId() + "_" + index.toString());

            cell1.appendChild(new Label(new Integer(index).toString()));
            cell2.appendChild(cbHowRelated);
            cell3.appendChild(tbRelationName);
            cell4.appendChild(tbSourcelocator);            
            cell5.appendChild(lbLocatorTyp);
            cell6.appendChild(cbSegmentType);
            cell7.appendChild(tbLocator);
            cell8.appendChild(tbPromotionalText);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);
            li.appendChild(cell4);
            li.appendChild(cell5);
            li.appendChild(cell6);
            li.appendChild(cell7);
            li.appendChild(cell8);
            li.setParent(lbRelated);
        }
    }

    private void fillCbHowRelated() {

        Combobox cbGenre = (Combobox) getFellow("cbHowRelated");
        Iterator it = new TVAnytimeHowRelatedToolboxExtended().getGenres().iterator();
        while (it.hasNext()) {
            cbGenre.appendItem((String) it.next());
        }
    }

    private void fillCbHowRelated(Combobox cb) {

        Iterator it = new TVAnytimeHowRelatedToolboxExtended().getGenres().iterator();
        while (it.hasNext()) {
            cb.appendItem((String) it.next());
        }
    }
}
