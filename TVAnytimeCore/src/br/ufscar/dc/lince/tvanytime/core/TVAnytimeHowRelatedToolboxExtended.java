/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lince.tvanytime.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author lince
 */
public class TVAnytimeHowRelatedToolboxExtended {
    
    private static Hashtable table;

    public TVAnytimeHowRelatedToolboxExtended() {
    }

    public static Collection getGenres() {
        Collection col = table.values();
        Vector data=new Vector();
        data.addAll(col);
        Collections.sort(data);
        return data;
    }

    //called when initializing class
    static {
        //table to store TVAnytime HowRelated
		table = new Hashtable(20);

		// HowRelated table
		table.put("1", "Trailer");
		table.put("2", "Group trailer");
		table.put("3", "Sibling");
		table.put("4", "Alternative");
		table.put("5", "Parent");
		table.put("6", "Recommendation");
		table.put("7", "Group recommendation");
		table.put("8", "Commercial Advert");
		table.put("9", "Direct product purchase");
		table.put("10", "For more information");
		table.put("11", "Programme review information");
		table.put("12", "Recap");
		table.put("13", "The making of");
		table.put("14", "Support");

    }

}
