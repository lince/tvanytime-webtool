package br.ufscar.dc.lince.webuitvanytime.controller.segmentInformation;

import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.TVAnytimeException;
import bbc.rd.tvanytime.contentReferencing.ContentReferencingTable;
import bbc.rd.tvanytime.xml.SAXXMLParser;
import br.ufscar.dc.lince.tvanytime.core.Toolbox;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import tvanytime.persistence.ws.ContentReferencingTableResult;
import tvanytime.persistence.ws.TVAnytimePersistence;
import tvanytime.persistence.ws.TVAnytimePersistenceService;

/**
 *
 * @author lince
 */
public class LoadCridController extends Window {

    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(LoadCridController.class.getName());

    public LoadCridController() {

        // Set the logger level for this class
        logger.setLevel(Level.ALL);
    }

    /**
     *  Event is fired when all child components of the window, this class
     * was assigned to, have been created
     *
     * @param event
     */
    public void onCreate(org.zkoss.zk.ui.event.Event event) {

        Listbox lbCrid = (Listbox) getFellow("lbCrid");

        // Retrieve the program
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

    public void selectCrid() {
        // Activate the button to load a crid table
        ((Button) getFellow("btLoadCrid")).setDisabled(false);
    }

    public void loadCrid() {

        Listitem selectedItem = (Listitem) ((Listbox) getFellow("lbCrid")).getSelectedItem();
        Combobox cbCrid = (Combobox) getDesktop().getPage("segmentInformationMain").getFellow("winSegmentInformation").getFellow("cbCrid");

        ContentReferencingTableResult crtr = (ContentReferencingTableResult) selectedItem.getAttribute("itemID");

        // Retrieve the program
        TVAnytimePersistence port = new TVAnytimePersistenceService().getTVAnytimePersistencePort();
        String contentReferenceTable = port.getContentReferencingTable(crtr.getId());
        logger.log(Level.FINEST, contentReferenceTable);

        SAXXMLParser parser = null;
        try {
            parser = new SAXXMLParser();
            parser.setParseProfile(SAXXMLParser.STANDARD);
            parser.parse(new ByteArrayInputStream(contentReferenceTable.getBytes()));
        } catch (IOException ex) {
            Logger.getLogger(LoadCridController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TVAnytimeException ex) {
            Logger.getLogger(LoadCridController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ContentReferencingTable contentReferencingTable = parser.getContentReferencingTable();

        // Extract all CRIDs from the table
        Collection<ContentReference> crids = Toolbox.getAllCrid(contentReferencingTable);
        Iterator it = crids.iterator();

        // Add the CRIDs to the combobox
        while (it.hasNext()) {
            cbCrid.appendItem(((ContentReference) it.next()).getCRID());
        }
        // Select the first item of the box
        if (cbCrid.getItemCount() > 0) {
            cbCrid.setSelectedIndex(0);
        } 
        // No CRIDs have been in the table
        else {
            try {
                Messagebox.show("No CRID has been found in the table!", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
            } catch (InterruptedException ex) {
                Logger.getLogger(LoadCridController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Close the modal window
        Window winNewCredit = (Window) getFellow("winLoadCrid");
        winNewCredit.detach();

    }

    public void cancel() {
        Window winNewCredit = (Window) getFellow("winLoadCrid");
        winNewCredit.detach();
    }
}
