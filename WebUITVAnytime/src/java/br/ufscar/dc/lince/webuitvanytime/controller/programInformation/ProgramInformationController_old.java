package br.ufscar.dc.lince.webuitvanytime.controller.programInformation;

import br.ufscar.dc.lince.webuitvanytime.controller.segmentInformation.NewSegmentDescriptionController;
import bbc.rd.tvanytime.BasicDescription;
import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.Synopsis;
import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.tvanytime.Title;
import bbc.rd.tvanytime.programInformation.ProgramInformation;
import bbc.rd.tvanytime.programInformation.ProgramInformationTable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Comboitem;
import tvanytime.persistence.ws.TVAnytimePersistence;
import tvanytime.persistence.ws.TVAnytimePersistenceService;
import br.ufscar.dc.lince.tvanytime.core.TVAnytimeGenreToolboxExtended;

/**
 *
 * @author lince
 */
public class ProgramInformationController_old extends Window {

    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(NewSegmentDescriptionController.class.getName());

    public ProgramInformationController_old() {
        
        // Set the logger level for this class
        logger.setLevel(Level.ALL);
    }

    public Object clone() {
        return super.clone();
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

    public void addTitle() {

        Listbox lbTitle = (Listbox) getFellow("lbTitle");

        Textbox tbNewTitle = (Textbox) getFellow("tbNewTitle").clone();
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
    }

    public void addSynopsis() {
        Listbox lbSynopsis = (Listbox) getFellow("lbSynopsis");

        Textbox tbNewSynopsis = (Textbox) getFellow("tbNewSynopsis").clone();
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
    }

    public void addRelatedMaterial() {
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

    public void addPromotionalInformation() {

        Listbox lbProInfo = (Listbox) getFellow("lbPromotionalInformation");
        Textbox tbProInfo = (Textbox) ((Textbox) getFellow("tbPromotionalInformation")).clone();
        Integer index = (lbProInfo.getItemCount());

        Listitem li = new Listitem();
        Listcell cell1 = new Listcell();
        Listcell cell2 = new Listcell();

        // Change the ID of all the items, to not have duplicated IDs in the
        // namespace of the page
        // Namingconvention: newId = oldId + "_" + number of Listitems
        // e.g.: tbNewTitle = tbNewTitle_1
        // e.g.: cbTitleLanguage = cbTitleLanguage_1
        tbProInfo.setId(tbProInfo.getId() + "_" + index.toString());

        cell1.appendChild(new Label(new Integer(index).toString()));
        cell2.appendChild(tbProInfo);
        li.appendChild(cell1);
        li.appendChild(cell2);
        li.setParent(lbProInfo);

    }

    // Event is fired when all child components of the window, this class
    // was assigned to, have been created
    public void onCreate(org.zkoss.zk.ui.event.Event event) {

        Combobox cbGenre = (Combobox) getFellow("cbGenre");
        Iterator it = new TVAnytimeGenreToolboxExtended().getGenres().iterator();
        while (it.hasNext()) {
            cbGenre.appendItem((String) it.next());
        }
    }

    public void saveProgramInformation() {

        logger.log(Level.FINE, "saveProgramInformation started");

        Textbox tbCrid = (Textbox) getFellow("tbCrid");

        // Only for development
        tbCrid.setValue("crid://bbc.co.uk/302020517");

        ProgramInformationTable programInformationTable = new ProgramInformationTable();
        ProgramInformation programInformation = new ProgramInformation();
        BasicDescription bsd = new BasicDescription();

        bsd = getTitle(bsd);
        bsd = getSynopsis(bsd);

        // Appending the basic description data to the programinformation
        programInformation.setBasicDescription(bsd);
        try {
            programInformation.setProgramID(new ContentReference(tbCrid.getValue()));
        } catch (TVAnytimeException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        // Appending the programinformation to the programinformationtable
        programInformationTable.addProgramInformation(programInformation);

        // Persisting the program information table through the webservice
        TVAnytimePersistenceService service = new TVAnytimePersistenceService();
        TVAnytimePersistence port = service.getTVAnytimePersistencePort();
        // TODO
        //port.persistProgramInformationTable(programInformationTable.toXML());

        logger.log(Level.FINE, "ProgramInformationTable saved:" + programInformationTable.toXML());
        logger.log(Level.FINE, "saveProgramInformation finished");
    }

    private BasicDescription getTitle(BasicDescription bsd) {

        Listbox lbTitle = (Listbox) getFellow("lbTitle");

        /**
         * Collection the Information of the titles
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

        logger.log(Level.FINEST, "Titles of Programinformation saved");
        return bsd;
    }

    private BasicDescription getSynopsis(BasicDescription bsd) {

        Listbox lbSynopsis = (Listbox) getFellow("lbSynopsis");

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
                synopsis.setLength(((Combobox) ((Listcell) children.get(3)).getFirstChild()).getSelectedIndex()-1);
            } catch (TVAnytimeException ex) {
                logger.log(Level.SEVERE, null, ex);
            }

            bsd.addSynopsis(synopsis);
        }
        logger.log(Level.FINER, "Synopsis of Programinformation saved");

        return bsd;

    }
}
