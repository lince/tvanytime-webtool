/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lince.tvanytime.core;

import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.contentReferencing.ContentReferencingTable;
import bbc.rd.tvanytime.contentReferencing.Result;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

/**
 *
 * @author lince
 */
public class Toolbox {

    /**
     * Defining a logger for the class
     */
    static final Logger logger = Logger.getLogger(Toolbox.class.getName());

    /**
     * Fills the given combobox with all the genres defined in
     * TVAnytimeGenreToolboxExtended and returns the full combobox
     *
     * @param       cbGenre         Combobox to fill
     * @return                      Combobox filled with genres
     */
    public static Combobox fillGenre(Combobox cbGenre) {
        Iterator it = new TVAnytimeGenreToolboxExtended().getGenres().iterator();
        while (it.hasNext()) {
            cbGenre.appendItem((String) it.next());
        }
        return cbGenre;
    }

    /**
     * Fills the given combobox with all the how related information
     * TVAnytimeHowRelatedToolboxExtended and returns the full combobox
     *
     * @param       cbHowRelated    Combobox to fill
     * @return                      Combobox fill with how related-information
     */
    public static Combobox fillHowRelated(Combobox cbHowRelated) {
        Iterator it = new TVAnytimeHowRelatedToolboxExtended().getGenres().iterator();
        while (it.hasNext()) {
            cbHowRelated.appendItem((String) it.next());
        }

        return cbHowRelated;
    }

    public Toolbox() {
        logger.setLevel(Level.ALL);
    }

    /**
     * Method extracts all CRIDs from a ContentReferencingTable
     *
     * @param       contentReferencingTable     the ContentReferenceTable to be searched in
     * @return      Collection<ContentReference>    Collection of CRIDs,
     *                                              returns an empty collection if no crid was found
     */
    public static Collection<ContentReference> getAllCrid(ContentReferencingTable contentReferencingTable) {
        Collection<ContentReference> crids = new ArrayList();

        // Go through all results of the table
        for (int i = 0; i < contentReferencingTable.getNumResults(); i++) {
            Result result = contentReferencingTable.getResult(i);

            // If the result contains CRIDs
            if (result.getCRIDResult() != null) {
                for (int i2 = 0; i2 < result.getCRIDResult().getNumCRIDs(); i2++) {
                    crids.add(result.getCRIDResult().getCRID(i2));
                }
            }
        }
        return crids;
    }

    /**
     * Calculates the seconds out of a string
     *
     * @param String of the format hh:mm:ss
     * @return int value in seconds of the time
     */
    public static int stringToSeconds(String time) {

        int hoursSec = Integer.parseInt(time.substring(0, 2)) * 3600;
        int minutesSec = Integer.parseInt(time.substring(3, 5)) * 60;
        int secondsSec = Integer.parseInt(time.substring(6, 8));

        int seconds = hoursSec + minutesSec + secondsSec;
        logger.log(Level.FINEST, "Timestring: " + time + " calculated to: " + Integer.toString(seconds));

        return seconds;
    }

    /**
     * Constructs the timestring out of the hour, minutes and seconds
     * s: seconds
     * m: minutes
     * h: hours
     * Timestring-format:       hh:mm:ss
     * @param       sec         time in seconds
     * @return      String      hh:mm:ss
     */
    public static String secondsToString(int sec) {

        String strTime = "";

        Double hours = Math.floor(sec / 3600);
        double divisor_for_minutes = sec % 3600;
        Double minutes = Math.floor(divisor_for_minutes / 60);
        double divisor_for_seconds = divisor_for_minutes % 60;
        Double seconds = Math.ceil(divisor_for_seconds);

        NumberFormat format = NumberFormat.getIntegerInstance();

        format.setMinimumIntegerDigits(2);
        format.setMaximumIntegerDigits(2);

        strTime = format.format(hours) + ":" + format.format(minutes) + ":" + format.format(seconds);
        return strTime;
    }

    /**
     * Selects the combobox item with the corresponding label
     * of the given combobox,
     * If there is no item with the wanted label, then
     * nothing gets selected
     * If there are more then one comboboxitem with the same label, then the
     * first one gets selected
     *
     * @param cb        The combobox to manipulate
     * @param label     The label of the combobox item to select
     * @return          The combobox with the correct combobox item selected
     */
    public static Combobox selectCombobox(Combobox cb, String label) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (cb.getItemAtIndex(i).getLabel().equals(label)) {
                cb.setSelectedIndex(i);
                break;
            }
        }
        return cb;
    }

    /**
     * Creates of a collection of zul-componentens a listitem
     *
     * @param       index              Integer of the index which the components
     *                                  get added to grant uniqunes in the namespace
     *                                  The id is builded: old Id + "_index"
     *                                 e.g.: tbName_1
     * @param       colComponent       Collection of zul-components
     * @return      Listitem
     */
    public static Listitem createListItem(int index, Collection<Component> colComponent) {
        Listitem listitem = new Listitem();
        Iterator it = colComponent.iterator();

        while (it.hasNext()) {
            Component comp = (Component) it.next();
            comp.setId(comp.getId() + "_" + Integer.toString(index));
            Listcell cell = new Listcell();
            cell.appendChild(comp);
            listitem.appendChild(cell);
        }
        return listitem;
    }
}
