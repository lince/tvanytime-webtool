package br.ufscar.dc.lince.webuitvanytime.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import tvanytime.persistence.ws.TVAnytimePersistence;
import tvanytime.persistence.ws.TVAnytimePersistenceService;

/**
 *
 * @author lince
 */
public class XmlInterfaceController extends Window {

    public void onOK() {
        try {
            System.out.println("Button clicked: onOK");
            String contentR = ((Textbox) getFellow("contentR")).getValue();
            String informationT = ((Textbox) getFellow("informationT")).getValue();
            String locationT = ((Textbox) getFellow("locationT")).getValue();

            // Persisting the program information table through the webservice
            TVAnytimePersistenceService service = new TVAnytimePersistenceService();
            TVAnytimePersistence port = service.getTVAnytimePersistencePort();
            port.persistTvAnytimeMetaData(contentR, informationT, locationT);

            new Messagebox().show("The TV-Anytime metadata has successfully been saved to the data base", "Information", Messagebox.OK, Messagebox.INFORMATION);

        } catch (InterruptedException ex) {
            Logger.getLogger(XmlInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
