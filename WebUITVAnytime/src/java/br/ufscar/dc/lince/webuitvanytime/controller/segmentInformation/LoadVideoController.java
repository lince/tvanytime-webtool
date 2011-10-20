package br.ufscar.dc.lince.webuitvanytime.controller.segmentInformation;

import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author lince
 */
public class LoadVideoController extends Window {

    /*
     * Relative definition of the folder where the video files of the server
     * are located
     * 
     * The video folder is defined as the a subfolder of the main web-folder named "video"
     *
     */
    final String strVideoPath = "C:\\Users\\lince\\Documents\\NetBeansProjects\\WebUITVAnytime\\web\\videos";

    /*
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(LoadVideoController.class.getName());

    /**
     * Load a new video with the divx-webplayer
     *
     *
     */
    public LoadVideoController() {

        // Set the logger level for this class
        logger.setLevel(Level.ALL);
    }

    public void loadVideo() {
        Listbox lbFile = (Listbox) getFellow("lbFile");
        Listitem it = lbFile.getSelectedItem();
        Textbox tbFileName = (Textbox) getDesktop().getPage("segmentInformationMain").getFellow("winSegmentInformation").getFellow("tbFileName");

        // Set the value of the textbox
        String strFileName = ((Label) ((Listcell) (it.getChildren().get(0))).getFirstChild()).getValue();
        tbFileName.setValue(strFileName);

        // Call the client-java-script "setDivxSource" to set the content path
        // of the divx-plugin of the video which should be played
        Clients.evalJavaScript("setDivxSource('../../videos/" + strFileName + "')");

        // Close the modal window
        Window winNewCredit = (Window) getFellow(super.getId());
        winNewCredit.detach();
    }

    /**
     *  Event is fired when all child components of the window, this class
     * was assigned to, have been created
     *
     * @param event
     */
    public void onCreate(org.zkoss.zk.ui.event.Event event) {

        logger.log(Level.FINE, "LoadVideoController: onCreate Event fired");

        Listbox lbFile = (Listbox) getFellow("lbFile");

        File folder = new File(strVideoPath);
        logger.log(Level.FINE, "Video source folder: " + folder.getAbsolutePath());

        File[] listOfFiles = folder.listFiles();

        // Add every file of the list to the file list
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            logger.log(Level.FINEST, "File found: " + file.getName());

            Listitem li = new Listitem();
            Listcell cell1 = new Listcell();
            Listcell cell2 = new Listcell();
            Listcell cell3 = new Listcell();

            Label lbName = new Label(file.getName());
            Label lbSize = new Label(Long.toString(file.length()));
            Label lbDate = new Label(new Date(file.lastModified()).toString());

            cell1.appendChild(lbName);
            cell2.appendChild(lbSize);
            cell3.appendChild(lbDate);
            li.appendChild(cell1);
            li.appendChild(cell2);
            li.appendChild(cell3);

            lbFile.appendChild(li);
        }
    }

    /**
     * Method to close the modal window
     */
    public void cancel() {

        Window winNewCredit = (Window) getFellow(super.getId());
        winNewCredit.detach();
    }
}
