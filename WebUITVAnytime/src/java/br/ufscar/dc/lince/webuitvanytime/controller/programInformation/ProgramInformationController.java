package br.ufscar.dc.lince.webuitvanytime.controller.programInformation;

import bbc.rd.mpeg7.MPEG7MediaLocator;
import bbc.rd.tvanytime.BasicDescription;
import bbc.rd.tvanytime.CaptionLanguage;
import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.Genre;
import bbc.rd.tvanytime.Keyword;
import bbc.rd.tvanytime.PromotionalInformation;
import bbc.rd.tvanytime.RelatedMaterial;
import bbc.rd.tvanytime.SignLanguage;
import bbc.rd.tvanytime.Synopsis;
import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.tvanytime.Title;
import bbc.rd.tvanytime.URI;
import bbc.rd.tvanytime.creditsInformation.CreditsItem;
import bbc.rd.tvanytime.creditsInformation.CreditsList;
import bbc.rd.tvanytime.programInformation.ProgramInformation;
import bbc.rd.tvanytime.programInformation.ProgramInformationTable;
import bbc.rd.tvanytime.segmentInformation.SegmentReference;
import bbc.rd.tvanytime.xml.SAXXMLParser;
import br.ufscar.dc.lince.tvanytime.core.TVAnytimeGenreToolboxExtended;
import br.ufscar.dc.lince.tvanytime.core.TVAnytimeHowRelatedToolboxExtended;
import br.ufscar.dc.lince.tvanytime.core.Toolbox;
import br.ufscar.dc.lince.webuitvanytime.controller.segmentInformation.NewSegmentDescriptionController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import tvanytime.persistence.ws.ProgramInformationTableResult;
import tvanytime.persistence.ws.TVAnytimePersistence;
import tvanytime.persistence.ws.TVAnytimePersistenceService;

/**
 *
 * @author lince
 */
public class ProgramInformationController extends Window {

    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(NewSegmentDescriptionController.class.getName());

    public ProgramInformationController() {
        // Set the logger level for this class
        logger.setLevel(Level.ALL);
    }

    public void synopsis() {
        setStateGroup(1);
    }

    public void relatedMaterial() {
        setStateGroup(2);
    }

    public void captionLanguage() {
        setStateGroup(3);
    }

    public void audioSignLanguage() {
        setStateGroup(4);
    }

    public void keyword() {
        setStateGroup(5);
    }

    public void genre() {
        setStateGroup(6);
    }

    public void promotionalInformation() {
        setStateGroup(7);
    }

    public void credits() {
        setStateGroup(8);
    }

    /**
     * Sets the visibility of the groupboxes to the given state
     *
     * @param       i       int of the state to be set,
     *                      if i is below 1 or higher then 8, all groupboxes are set
     *                      invisible
     */
    private void setStateGroup(int i) {

        Vbox vbSynopsis = (Vbox) getFellow("vbSynopsis");
        Vbox vbRelatedMaterial = (Vbox) getFellow("vbRelatedMaterial");
        Vbox vbCaptionLanguage = (Vbox) getFellow("vbCaptionLanguage");
        Vbox vbAudioSignLanguage = (Vbox) getFellow("vbAudioSignLanguage");
        Vbox vbKeyword = (Vbox) getFellow("vbKeyword");
        Vbox vbGenre = (Vbox) getFellow("vbGenre");
        Vbox vbPromotionalInformation = (Vbox) getFellow("vbPromotionalInformation");
        Vbox vbCredits = (Vbox) getFellow("vbCredits");


        // All boxes are set invisible
        setAllInvisible();

        // Now only the groupbox corresponding to state is set visible
        switch (i) {

            case 1:
                vbSynopsis.setVisible(true);
                break;

            case 2:
                vbRelatedMaterial.setVisible(true);
                break;

            case 3:
                vbCaptionLanguage.setVisible(true);
                break;

            case 4:
                vbAudioSignLanguage.setVisible(true);
                break;

            case 5:
                vbKeyword.setVisible(true);
                break;
            case 6:
                vbGenre.setVisible(true);
                break;
            case 7:
                vbPromotionalInformation.setVisible(true);
                break;
            case 8:
                vbCredits.setVisible(true);
                break;

        }
    }

    /**
     * Sets all groupboxes invisible
     */
    private void setAllInvisible() {
        Vbox vbSynopsis = (Vbox) getFellow("vbSynopsis");
        Vbox vbRelatedMaterial = (Vbox) getFellow("vbRelatedMaterial");
        Vbox vbCaptionLanguage = (Vbox) getFellow("vbCaptionLanguage");
        Vbox vbAudioSignLanguage = (Vbox) getFellow("vbAudioSignLanguage");
        Vbox vbKeyword = (Vbox) getFellow("vbKeyword");
        Vbox vbGenre = (Vbox) getFellow("vbGenre");
        Vbox vbPromotionalInformation = (Vbox) getFellow("vbPromotionalInformation");
        Vbox vbCredits = (Vbox) getFellow("vbCredits");

        vbSynopsis.setVisible(false);
        vbRelatedMaterial.setVisible(false);
        vbCaptionLanguage.setVisible(false);
        vbAudioSignLanguage.setVisible(false);
        vbKeyword.setVisible(false);
        vbGenre.setVisible(false);
        vbPromotionalInformation.setVisible(false);
        vbCredits.setVisible(false);
    }

    public void addKeyword() {

        Textbox tbNewKeywordOld = (Textbox) ((Textbox) getFellow("tbNewKeyword"));
        Textbox tbNewKeyword = (Textbox) ((Textbox) getFellow("tbNewKeyword")).clone();
        Combobox cbKeywordType = (Combobox) ((Combobox) getFellow("cbKeywordType")).clone();
        Listbox lbKeyword = (Listbox) getFellow("lbKeyword");

        if (!tbNewKeyword.getValue().trim().equals("")) {

            Listitem li = new Listitem();

            Integer index = (lbKeyword.getItemCount());

            Listcell cell1 = new Listcell();
            Listcell cell2 = new Listcell();
            Listcell cell3 = new Listcell();

            // Change the ID of all the items, to not have duplicated IDs in the
            // namespace of the page
            // Namingconvention: newId = oldId + "_" + number of Listitems
            // e.g.: tbNewTitle = tbNewTitle_1
            // e.g.: cbTitleLanguage = cbTitleLanguage_1
            tbNewKeyword.setId(tbNewKeyword.getId() + "_" + index.toString());
            cbKeywordType.setId(cbKeywordType.getId() + "_" + index.toString());

            cell1.appendChild(new Label(new Integer(index).toString()));
            cell2.appendChild(tbNewKeyword);
            cell3.appendChild(cbKeywordType);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);
            li.setParent(lbKeyword);

            tbNewKeywordOld.setValue("");
        }

    }

    /**
     * Adds a new title to the title-listbox
     */
    public void addTitle() {

        Listbox lbTitle = (Listbox) getFellow("lbTitle");
        Textbox tbNewTitle = (Textbox) getFellow("tbNewTitle").clone();

        // If the title-string is not an empty string
        if (!tbNewTitle.getValue().trim().equals("")) {
            Include incl = (Include) getFellow("cbTitleLanguage");
            Combobox cbTitleLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
            cbTitleLanguage.setId("cbTitleLanguage");
            Combobox cbTitleTyp = (Combobox) (getFellow("cbTitleTyp")).clone();

            Integer index = (lbTitle.getItemCount());

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

            cell1.appendChild(new Label(new Integer(index).toString()));
            cell2.appendChild(tbNewTitle);
            cell3.appendChild(cbTitleLanguage);
            cell4.appendChild(cbTitleTyp);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);
            li.appendChild(cell4);
            li.setParent(lbTitle);

            // Set the textbox blank to avoid unwanted repetition
            ((Textbox) getFellow("tbNewTitle")).setValue("");
        }
    }

    /**
     * Adds a new synopsis to the synopsis-listbox
     */
    public void addSynopsis() {
        Listbox lbSynopsis = (Listbox) getFellow("lbSynopsis");
        Textbox tbNewSynopsis = (Textbox) getFellow("tbSynopsis").clone();

        // If the synopsis-string is not an empty string
        if (!tbNewSynopsis.getValue().trim().equals("")) {
            Include incl = (Include) getFellow("cbSynopsisLanguage");
            Combobox cbSynopsisLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
            cbSynopsisLanguage.setId("cbSynopsisLanguage");
            Combobox cbSynopsisLength = (Combobox) (getFellow("cbSynopsisLength")).clone();

            Integer index = (lbSynopsis.getItemCount());

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

            cell1.appendChild(new Label(new Integer(index).toString()));
            cell2.appendChild(tbNewSynopsis);
            cell3.appendChild(cbSynopsisLanguage);
            cell4.appendChild(cbSynopsisLength);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);
            li.appendChild(cell4);
            li.setParent(lbSynopsis);

            // Set the textbox blank to avoid unwanted repetition
            ((Textbox) getFellow("tbSynopsis")).setValue("");
        }
    }

    /**
     * Adds new related material to the related material-listbox
     */
    public void addRelatedMaterial() {
        Listbox lbRelated = (Listbox) getFellow("lbRelatedMaterial");

        Combobox cbHowRelated = (Combobox) getFellow("cbHowRelated").clone();
        Textbox tbRelationName = (Textbox) getFellow("tbRelationName").clone();
        Combobox cbSegmentType = (Combobox) getFellow("cbSegmentType").clone();
        Textbox tbSourceLocator = (Textbox) getFellow("tbSourceLocator").clone();
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

    public void addCaptionLanguage() {
        Listbox lbCaptionLanguage = (Listbox) getFellow("lbCaptionLanguage");

        // Get the combobox control language
        Include incl = (Include) getFellow("cbCaptionLanguage");
        Combobox cbCaptionLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
        cbCaptionLanguage.setId("cbCaptionLanguage");

        Checkbox cxIsClosed = (Checkbox) getFellow("cxIsClosed").clone();
        Checkbox cxIsSupplemental = (Checkbox) getFellow("cxIsSupplemental").clone();

        Integer index = (lbCaptionLanguage.getItemCount());

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
        cbCaptionLanguage.setId(cbCaptionLanguage.getId() + "_" + index.toString());
        cxIsClosed.setId(cxIsClosed.getId() + "_" + index.toString());
        cxIsSupplemental.setId(cxIsSupplemental.getId() + "_" + index.toString());

        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(cbCaptionLanguage);
        cell3.appendChild(cxIsClosed);
        cell4.appendChild(cxIsSupplemental);
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.appendChild(cell3);
        li.appendChild(cell4);
        li.setParent(lbCaptionLanguage);
    }

    public void addAudioSignLanguage() {
        Listbox lbAudioSignLanguage = (Listbox) getFellow("lbAudioSignLanguage");

        // Get the combobox control language
        Include incl = (Include) getFellow("cbAudioSignLanguage");
        Combobox cbAudioSignLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
        cbAudioSignLanguage.setId("cbAugioSignLanguage");

        Checkbox cxIsPrimary = (Checkbox) getFellow("cxIsPrimary").clone();
        Checkbox cxIsTranslation = (Checkbox) getFellow("cxIsTranslation").clone();
        Textbox tbAudioSignLanuageTyp = (Textbox) getFellow("tbAudioSignLanguageTyp").clone();

        Integer index = (lbAudioSignLanguage.getItemCount());

        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();
        Listcell cell3 = new Listcell();
        Listcell cell4 = new Listcell();
        Listcell cell5 = new Listcell();

        // Change the ID of all the items, to not have duplicated IDs in the
        // namespace of the page
        // Namingconvention: newId = oldId + "_" + number of Listitems
        // e.g.: tbNewTitle = tbNewTitle_1
        // e.g.: cbTitleLanguage = cbTitleLanguage_1
        cbAudioSignLanguage.setId(cbAudioSignLanguage.getId() + "_" + index.toString());
        tbAudioSignLanuageTyp.setId(tbAudioSignLanuageTyp + "_" + index.toString());
        cxIsPrimary.setId(cxIsPrimary.getId() + "_" + index.toString());
        cxIsTranslation.setId(cxIsTranslation.getId() + "_" + index.toString());

        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(cbAudioSignLanguage);
        cell3.appendChild(tbAudioSignLanuageTyp);
        cell4.appendChild(cxIsPrimary);
        cell5.appendChild(cxIsTranslation);
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.appendChild(cell3);
        li.appendChild(cell4);
        li.appendChild(cell5);
        li.setParent(lbAudioSignLanguage);


    }

    public void addGenre() {
        Listbox lbGenre = (Listbox) getFellow("lbGenre");

        Combobox cbGenre = (Combobox) ((Combobox) getFellow("cbGenre")).clone();
        Combobox cbGenreType = (Combobox) ((Combobox) getFellow("cbGenreType")).clone();
        cbGenreType.setId("cbGenreType");

        Integer index = (lbGenre.getItemCount());

        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();
        Listcell cell3 = new Listcell();

        // Change the ID of all the items, to not have duplicated IDs in the
        // namespace of the page
        // Namingconvention: newId = oldId + "_" + number of Listitems
        // e.g.: tbNewTitle = tbNewTitle_1
        // e.g.: cbTitleLanguage = cbTitleLanguage_1
        cbGenre.setId(cbGenre.getId() + "_" + index.toString());
        cbGenreType.setId(cbGenreType.getId() + "_" + index.toString());

        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(cbGenre);
        cell3.appendChild(cbGenreType);
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.appendChild(cell3);
        li.setParent(lbGenre);


    }

    /**
     * Adds promotional information to the promotional information-listbox
     */
    public void addPromotionalInformation() {

        Listbox lbProInfo = (Listbox) getFellow("lbPromotionalInformation");
        Textbox tbProInfo_old = (Textbox) getFellow("tbPromotionalInformation");
        Textbox tbProInfo = (Textbox) tbProInfo_old.clone();

        // If the promotional information-string is not empty


        if (!tbProInfo_old.getValue().trim().equals("")) {
            Integer index = (lbProInfo.getItemCount());

            Listitem li = new Listitem();
            Listcell cell1 = new Listcell();
            Listcell cell2 = new Listcell();

            /*
             * Change the ID of all the items, to not have duplicated IDs in the
             * namespace of the page
             * Namingconvention: newId = oldId + "_" + number of Listitems
             * e.g.: tbNewTitle = tbNewTitle_1
             * e.g.: cbTitleLanguage = cbTitleLanguage_1
             */
            tbProInfo.setId(tbProInfo.getId() + "_" + index.toString());

            cell1.appendChild(new Label(new Integer(index).toString()));
            cell2.appendChild(tbProInfo);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.setParent(lbProInfo);



        }
    }

    /**
     * Event is fired when all child components of the window have been created
     *
     * onCreate adds all genres to the genre-combobox
     * and adds all howRelated-items to the item-combobox
     *
     * @param event
     */
    public void onCreate(org.zkoss.zk.ui.event.Event event) {

        // Fill the genre-combobox
        Combobox cbGenre = (Combobox) getFellow("cbGenre");
        Iterator it = new TVAnytimeGenreToolboxExtended().getGenres().iterator();


        while (it.hasNext()) {
            cbGenre.appendItem((String) it.next());


        }

        // Fill the howRelated-combobox
        Combobox cbHowRelated = (Combobox) getFellow("cbHowRelated");
        Iterator it2 = new TVAnytimeHowRelatedToolboxExtended().getGenres().iterator();


        while (it2.hasNext()) {
            cbHowRelated.appendItem((String) it2.next());


        }
    }

    /**
     * Constructs the program information-object out of the components and
     * saves the generated xml-file to the database
     */
    public void saveProgramInformation() {

        logger.log(Level.FINE, "saveProgramInformation started");
        Combobox tbCrid = (Combobox) getFellow("cbCrid");
        ContentReference crid = null;



        try {

            crid = new ContentReference(tbCrid.getValue().trim());

            ProgramInformationTable programInformationTable = new ProgramInformationTable();
            ProgramInformation programInformation = new ProgramInformation();
            BasicDescription bsd = new BasicDescription();
            bsd = getTitle(bsd);
            bsd = getSynopsis(bsd);
            bsd = getRelatedMaterial(bsd);
            bsd = getCaptionLanguage(bsd);
            bsd = getAudioSignLanguage(bsd);
            bsd = getGenre(bsd);
            bsd = getKeyword(bsd);
            bsd = getPromotionalInformation(bsd);
            bsd = getCredits(bsd);

            // Appending the basic description data to the programinformation
            programInformation.setBasicDescription(bsd);
            programInformation.setProgramID(crid);

            programInformationTable.addProgramInformation(programInformation);
            // Persisting the program information table through the webservice
            TVAnytimePersistenceService service = new TVAnytimePersistenceService();
            TVAnytimePersistence port = service.getTVAnytimePersistencePort();
            port.persistProgramInformationTable("table1", programInformationTable.toXML());

            Messagebox.show("The program informatin was succesfully saved", "Information", Messagebox.OK, Messagebox.INFORMATION);
            logger.log(Level.FINE, "ProgramInformationTable saved:" + programInformationTable.toXML());
            logger.log(Level.FINE, "saveProgramInformation finished");




        } catch (InterruptedException ex) {
            Logger.getLogger(ProgramInformationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TVAnytimeException ex) {
            logger.log(Level.SEVERE, null, ex);


            try {
                Messagebox.show("A wrong Content Reference was given!" + "\n"
                        + "The Content Reference has to be of the format: "
                        + "'crid://<authority>/<value>'", "Error", Messagebox.OK, Messagebox.ERROR);




            } catch (InterruptedException ex1) {
                Logger.getLogger(ProgramInformationController.class.getName()).log(Level.SEVERE, null, ex1);
            }


        }
    }

    private BasicDescription getTitle(BasicDescription bsd) {

        Listbox lbTitle = (Listbox) getFellow("lbTitle");

        /**
         * Collection the Information of the titles
         */
        // The items of the listbox start with an index of 1 !!!!
        // The element lbPerson.getItemAtIndex(0) exists, but its empty
        // listboxItem[]={[]} = this information is important for all listboxes
        for (int i = 1; i
                < lbTitle.getItemCount(); i++) {
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

        logger.log(Level.FINEST, "Titles of Programinformation saved");


        return bsd;


    }

    /**
     * Constructs the BasicDescription-object out of the informtion of the controlls
     * @param bsd
     * @return
     */
    private BasicDescription getSynopsis(BasicDescription bsd) {

        Listbox lbSynopsis = (Listbox) getFellow("lbSynopsis");

        /**
         * Collecting the information of the synopsislistbox
         */
        // The items of the listbox start with an index of 1 !!!!
        // The element lbPerson.getItemAtIndex(0) exists, but its empty
        // listboxItem[]={[]} = this information is important for all listboxes
        for (int i = 1; i
                < lbSynopsis.getItemCount(); i++) {
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
                synopsis.setLength(((Combobox) ((Listcell) children.get(3)).getFirstChild()).getSelectedIndex() - 1);
            } catch (TVAnytimeException ex) {
                logger.log(Level.SEVERE, null, ex);
            }

            bsd.addSynopsis(synopsis);
        }
        logger.log(Level.FINER, "Synopsis of Programinformation saved");

        return bsd;
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

    /**
     * Collecting the information of the relatedmaterial-listbox
     * 
     * @param bsd
     * @return
     */
    private BasicDescription getRelatedMaterial(BasicDescription bsd) {

        Listbox lbRelatedMaterial = (Listbox) getFellow("lbRelatedMaterial");

        // The items of the listbox start with an index of 1 !!!!
        // The element lbPerson.getItemAtIndex(0) exists, but its empty
        // listboxItem[]={[]} = this information is important for all listboxes
        /**
         *  Final prefix of a HowRelated-HREF:
         *      This String is defined in
         *          bbc.rd.tvanytime.RelatedMaterial: String hrefCS
         */
        final String howRelatedCS = "urn:tva:metadata:cs:HowRelatedCS:2002:";

        for (int i = 1; i
                < lbRelatedMaterial.getItemCount(); i++) {
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
                // The MediaLocator or SegmentReference - property is set, with the same data
                // -> an URI object created out of the textbox information
                // the uri has to be formed as following: <text> : <text>
                // This is specifief by  bbc.rd.tvanytime.URI
                if (!((Textbox) (((Listcell) children.get(6)).getFirstChild())).getValue().trim().equals("")) {
                    if (((Label) (((Listcell) children.get(4)).getFirstChild())).getValue().equals("Medialocator") == true) {
                        related.setMediaLocator(new MPEG7MediaLocator(new URI(((Textbox) (((Listcell) children.get(6)).getFirstChild())).getValue())));
                    } else {
                        // If the selected item is the second one of the comboxbox (index=1),
                        // then segment-group was selected -> Combobox sepcification see "newSegmentDescription.zul"
                        if (((Combobox) ((Listcell) children.get(5)).getFirstChild()).getSelectedIndex() == 1) {
                            segref.setSegmentType(segref.SEGMENT_GROUP);
                        } else {
                            segref.setSegmentType(segref.SEGMENT);
                        }
                        segref.setRef(((Textbox) ((Listcell) children.get(6)).getFirstChild()).getValue());
                        related.setSegmentReference(segref);
                    }
                }
                related.setSourceMediaLocator(new MPEG7MediaLocator(new URI(((Textbox) (((Listcell) children.get(3)).getFirstChild())).getValue())));
                related.setPromotionalText(((Textbox) (((Listcell) children.get(7)).getFirstChild())).getValue());

                bsd.addRelatedMaterial(related);
                logger.log(Level.FINEST, "Related material of SegmentDescription added");

            } catch (TVAnytimeException ex) {
                Logger.getLogger(ProgramInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bsd;
    }

    /**
     * Collecting the information of the captionlanguage-listbox
     *
     * @param bsd
     * @return
     */
    private BasicDescription getCaptionLanguage(BasicDescription bsd) {

        Listbox lbCaptionLanguage = (Listbox) getFellow("lbCaptionLanguage");

        /**
         * Collecting the information of the CaptionLanguage-listbox
         */
        /*
         * The items of the listbox start with an index of 1 !!!!
         * The element lbPerson.getItemAtIndex(0) exists, but its empty
         * listboxItem[]={[]} = this information is important for all listboxes
         */
        for (int i = 1; i
                < lbCaptionLanguage.getItemCount(); i++) {
            Listitem it = (Listitem) lbCaptionLanguage.getItemAtIndex(i);
            List children = it.getChildren();

            CaptionLanguage cl = new CaptionLanguage();

            cl.setLanguage(((Combobox) ((Listcell) children.get(1)).getFirstChild()).getValue());
            cl.setClosed(((Checkbox) ((Listcell) children.get(2)).getFirstChild()).isChecked());
            cl.setSupplemental(((Checkbox) ((Listcell) children.get(3)).getFirstChild()).isChecked());

            bsd.addCaptionLanguage(cl);


        }
        logger.log(Level.FINEST, "CaptionLanguage of Programinformation added");



        return bsd;


    }

    /**
     * Collecting the information of the audiosignlanguage-listbox
     *
     * @param       bsd                 BasicDescription where the new
     *                                  AudioSignLanguage-information gets added to
     * @return      BasicDescription    BasicDescription extended by the captionlanguage-information
     */
    private BasicDescription getAudioSignLanguage(BasicDescription bsd) {

        Listbox lbAudioSignLanguage = (Listbox) getFellow("lbAudioSignLanguage");

        /**
         * Collecting the information of the CaptionLanguage-listbox
         */
        /*
         * The items of the listbox start with an index of 1 !!!!
         * The element lbPerson.getItemAtIndex(0) exists, but its empty
         * listboxItem[]={[]} = this information is important for all listboxes
         */
        for (int i = 1; i
                < lbAudioSignLanguage.getItemCount(); i++) {
            Listitem it = (Listitem) lbAudioSignLanguage.getItemAtIndex(i);
            List children = it.getChildren();

            SignLanguage audioSignLanguage = new SignLanguage();

            audioSignLanguage.setLanguage(((Combobox) ((Listcell) children.get(1)).getFirstChild()).getValue());
            audioSignLanguage.setType(((Textbox) ((Listcell) children.get(2)).getFirstChild()).getValue());
            audioSignLanguage.setPrimary(((Checkbox) ((Listcell) children.get(3)).getFirstChild()).isChecked());
            audioSignLanguage.setTranslation(((Checkbox) ((Listcell) children.get(4)).getFirstChild()).isChecked());

            bsd.addSignLanguage(audioSignLanguage);


        }
        logger.log(Level.FINEST, "AudioSignLanguage of Programinformation added");



        return bsd;


    }

    /**
     * Collecting the information of the keyword-listbox
     *
     * @param       bsd                 BasicDescription where the new
     *                                  Keyword-information gets added to
     * @return      BasicDescription    BasicDescription extended by the keyword-information
     */
    private BasicDescription getKeyword(BasicDescription bsd) {

        Listbox lbAudioSignLanguage = (Listbox) getFellow("lbAudioSignLanguage");

        /**
         * Collecting the information of the CaptionLanguage-listbox
         */
        /*
         * The items of the listbox start with an index of 1 !!!!
         * The element lbPerson.getItemAtIndex(0) exists, but its empty
         * listboxItem[]={[]} = this information is important for all listboxes
         */
        for (int i = 1; i
                < lbAudioSignLanguage.getItemCount(); i++) {
            Listitem it = (Listitem) lbAudioSignLanguage.getItemAtIndex(i);
            List children = it.getChildren();

            Keyword keyword = new Keyword();

            keyword.setKeyword(((Textbox) ((Listcell) children.get(1)).getFirstChild()).getValue());



            try {
                keyword.setType(((Combobox) ((Listcell) children.get(1)).getFirstChild()).getSelectedIndex());
                bsd.addKeyword(keyword);



            } catch (TVAnytimeException ex) {
                Logger.getLogger(ProgramInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
        logger.log(Level.FINEST, "Keyword of Programinformation added");



        return bsd;



    }

    /**
     * Collecting the information of the genre-listbox
     *
     * @param       bsd                 BasicDescription where the new
     *                                  genre-information gets added to
     * @return      BasicDescription    BasicDescription extended by the genre-information
     */
    private BasicDescription getGenre(BasicDescription bsd) {

        Listbox lbGenre = (Listbox) getFellow("lbGenre");

        /**
         * Collecting the information of the CaptionLanguage-listbox
         */
        /*
         * The items of the listbox start with an index of 1 !!!!
         * The element lbPerson.getItemAtIndex(0) exists, but its empty
         * listboxItem[]={[]} = this information is important for all listboxes
         */
        for (int i = 1; i
                < lbGenre.getItemCount(); i++) {
            Listitem it = (Listitem) lbGenre.getItemAtIndex(i);
            List children = it.getChildren();
            Genre genre = new Genre();



            try {
                genre.setHref(((Combobox) ((Listcell) children.get(1)).getFirstChild()).getValue());
                genre.setType(((Combobox) ((Listcell) children.get(2)).getFirstChild()).getSelectedIndex());
                bsd.addGenre(genre);



            } catch (TVAnytimeException ex) {
                Logger.getLogger(ProgramInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
        logger.log(Level.FINEST, "Genre of Programinformation added");



        return bsd;


    }

    /**
     * Collecting the information of the promotional information-listbox
     *
     * @param       bsd                 BasicDescription where the new
     *                                  promotional information-information gets added to
     * @return      BasicDescription    BasicDescription extended by the promotional information-information
     */
    private BasicDescription getPromotionalInformation(BasicDescription bsd) {

        Listbox lbPromotionalInformation = (Listbox) getFellow("lbPromotionalInformation");

        /**
         * Collecting the information of the CaptionLanguage-listbox
         */
        /*
         * The items of the listbox start with an index of 1 !!!!
         * The element lbPerson.getItemAtIndex(0) exists, but its empty
         * listboxItem[]={[]} = this information is important for all listboxes
         */
        for (int i = 1; i
                < lbPromotionalInformation.getItemCount(); i++) {
            Listitem it = (Listitem) lbPromotionalInformation.getItemAtIndex(i);
            List children = it.getChildren();

            PromotionalInformation proInf = new PromotionalInformation();
            proInf.setPromotionalInformation(((Textbox) ((Listcell) children.get(1)).getFirstChild()).getValue());

            bsd.addPromotionalInformation(proInf);


        }
        logger.log(Level.FINEST, "Promotional information of Programinformation added");



        return bsd;


    }

    /**
     * Collecting the information of the promotional information-listbox
     *
     * @param       bsd                 BasicDescription where the new
     *                                  credits-information gets added to
     * @return      BasicDescription    BasicDescription extended by the credits-information
     */
    private BasicDescription getCredits(BasicDescription bsd) {

        Listbox lbCredits = (Listbox) getFellow("lbCredits");
        CreditsList creditsList = new CreditsList();

        /**
         * Collecting the information of the CaptionLanguage-listbox
         */
        /*
         * The items of the listbox start with an index of 1 !!!!
         * The element lbPerson.getItemAtIndex(0) exists, but its empty
         * listboxItem[]={[]} = this information is important for all listboxes
         */
        for (int i = 1; i
                < lbCredits.getItemCount(); i++) {
            Listitem it = (Listitem) lbCredits.getItemAtIndex(i);

            CreditsItem creditsItem = (CreditsItem) it.getAttribute("creditItem");
            creditsList.addCreditsItem(creditsItem);


        }
        bsd.setCreditsList(creditsList);
        logger.log(Level.FINEST, "Credits of Programinformation added");



        return bsd;


    }

    void edit(ProgramInformationTableResult pitr) {

        // TODO
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();

        logger.log(Level.FINEST, "Program information table to edit with id: " + pitr.getId());
        String strPit = port.getProgramInformationTable(pitr.getId());



        if (!strPit.equals("")) {

            SAXXMLParser parser = null;


            try {
                parser = new SAXXMLParser();
                parser.setParseProfile(SAXXMLParser.STANDARD);
                parser.parse(new ByteArrayInputStream(strPit.getBytes()));


            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);


            } catch (TVAnytimeException ex) {
                logger.log(Level.SEVERE, null, ex);


            }

            ProgramInformationTable pit = parser.getProgramInformationTable();
            ProgramInformation pi = pit.getProgramInformation(0);

            BasicDescription bd = pi.getBasicDescription();

            editTitle(bd);
            editSynopsis(bd);
            editRelatedMaterial(bd);
//            editCaptionLanguage(bd);
//            editAudioSignLanguage(bd);
//            editKeyword(bd);
//            editGenre(bd);
//            editPromotionalInformation(bd);
//            editCredits(bd);


        }
    }

    private void editTitle(BasicDescription basicDescription) {
        Listbox lbTitle = (Listbox) getFellow("lbTitle");




        for (int i = 0; i
                < basicDescription.getNumTitles(); i++) {
            Title title = basicDescription.getTitle(i);
            Textbox tbTitle = (Textbox) getFellow("tbNewTitle").clone();
            Include incl = (Include) getFellow("cbTitleLanguage").clone();
            Combobox cbTitleLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
            cbTitleLanguage.setId("cbTitleLanguage");
            Combobox cbTitleTyp = (Combobox) (getFellow("cbTitleTyp")).clone();


            tbTitle.setValue(title.getText());
            cbTitleLanguage = Toolbox.selectCombobox(cbTitleLanguage, title.getLanguage());
            cbTitleTyp.setSelectedIndex(title.getType());

            Integer index = (lbTitle.getItemCount());
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
            tbTitle.setId(tbTitle.getId() + "_" + index.toString());
            cbTitleLanguage.setId(cbTitleLanguage.getId() + "_" + index.toString());
            cbTitleTyp.setId(cbTitleTyp.getId() + "_" + index.toString());

            cell1.appendChild(new Label(new Integer(index).toString()));
            cell2.appendChild(tbTitle);
            cell3.appendChild(cbTitleLanguage);
            cell4.appendChild(cbTitleTyp);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);
            li.appendChild(cell4);
            li.setParent(lbTitle);
        }

    }

    private void editSynopsis(BasicDescription bd) {

        Listbox lbSynopsis = (Listbox) getFellow("lbSynopsis");



        for (int i = 0; i
                < bd.getNumSynopses(); i++) {
            Synopsis synopsis = bd.getSynopsis(i);
            Textbox tbSynopsis = (Textbox) getFellow("tbSynopsis").clone();
            Include incl = (Include) getFellow("cbSynopsisLanguage").clone();
            Combobox cbSynopsisLanguage = (Combobox) ((Combobox) incl.getFellow("cbLanguage")).clone();
            cbSynopsisLanguage.setId("cbSynopsisLanguage");
            Combobox cbSynopsisLength = (Combobox) (getFellow("cbSynopsisLength")).clone();

            tbSynopsis.setValue(synopsis.getText());
            cbSynopsisLanguage = Toolbox.selectCombobox(cbSynopsisLanguage, synopsis.getLanguage());
            cbSynopsisLength.setSelectedIndex(synopsis.getLength());

            Integer index = (lbSynopsis.getItemCount());
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
            tbSynopsis.setId(tbSynopsis.getId() + "_" + index.toString());
            cbSynopsisLanguage.setId(cbSynopsisLanguage.getId() + "_" + index.toString());
            cbSynopsisLength.setId(cbSynopsisLength.getId() + "_" + index.toString());

            cell1.appendChild(new Label(new Integer(index).toString()));
            cell2.appendChild(tbSynopsis);
            cell3.appendChild(cbSynopsisLanguage);
            cell4.appendChild(cbSynopsisLength);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);
            li.appendChild(cell4);
            li.setParent(lbSynopsis);


        }
    }

    private void editRelatedMaterial(BasicDescription bd) {

        Listbox lbRelated = (Listbox) getFellow("lbRelatedMaterial");

        for (int i = 0; i < bd.getNumRelatedMaterials(); i++) {

            RelatedMaterial rm = bd.getRelatedMaterial(i);
            Collection colComponent = new ArrayList();

            Combobox cbHowRelated = (Combobox) getFellow("cbHowRelated").clone();
            Textbox tbRelationName = (Textbox) getFellow("tbRelationName").clone();
            Combobox cbSegmentType = (Combobox) getFellow("cbSegmentType").clone();
            Textbox tbSourceLocator = (Textbox) getFellow("tbSourceLocator").clone();
            Textbox tbLocator = (Textbox) getFellow("tbLocator").clone();
            Textbox tbPromotionalText = (Textbox) getFellow("tbPromotionalText").clone();

            // getIndex return the index of the next listitem
            Integer index = getIndex("lbRelatedMaterial");

            cbHowRelated = Toolbox.selectCombobox(cbHowRelated, rm.getHowRelated());
            tbRelationName.setValue(rm.getName());
            // If the segment reference was set
            if(rm.getSegmentReference()!=null)
            {
            cbSegmentType.setSelectedIndex(rm.getSegmentReference().getSegmentType());
            }
            tbSourceLocator.setValue(rm.getSourceMediaLocator().getMediaURI().getURI());
            tbPromotionalText.setValue(rm.getPromotionalText());

            // The locator can be a medialocator or sourcemedialocator
            // depending on the rardiobutton, the right label is seleted
            Label lbLocatorTyp = null;
            if (rm.getMediaLocator() != null) {
                lbLocatorTyp = new Label("Medialocator");
                tbLocator.setValue(rm.getMediaLocator().getMediaURI().getURI());
            } else {
                lbLocatorTyp = new Label("Segment Reference");
                tbLocator.setValue(rm.getSegmentReference().getRef());
            }

            /*
             * Add the components to the collection, create a listitem and add
             * it to the list
             */
            colComponent.add(new Label(new Integer(index).toString()));
            colComponent.add(cbHowRelated);
            colComponent.add(tbRelationName);
            colComponent.add(tbSourceLocator);
            colComponent.add(cbSegmentType);
            colComponent.add(lbLocatorTyp);
            colComponent.add(tbLocator);
            colComponent.add(tbPromotionalText);
            Toolbox.createListItem(index, colComponent).setParent(lbRelated);
        }

    }

    private void editCaptionLanguage(BasicDescription bd) {
        throw new UnsupportedOperationException("Not yet implemented");


    }

    private void editAudioSignLanguage(BasicDescription bd) {
        throw new UnsupportedOperationException("Not yet implemented");


    }

    private void editKeyword(BasicDescription bd) {
        throw new UnsupportedOperationException("Not yet implemented");


    }

    private void editGenre(BasicDescription bd) {
        throw new UnsupportedOperationException("Not yet implemented");


    }

    private void editPromotionalInformation(BasicDescription bd) {
        throw new UnsupportedOperationException("Not yet implemented");


    }

    private void editCredits(BasicDescription bd) {
        throw new UnsupportedOperationException("Not yet implemented");

    }
}
