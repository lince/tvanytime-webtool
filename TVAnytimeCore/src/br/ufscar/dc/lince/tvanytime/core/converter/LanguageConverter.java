/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lince.tvanytime.core.converter;

import java.sql.Timestamp;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;

/**
 *
 * @author lince
 */
public class LanguageConverter implements TypeConverter {

    public Object coerceToUi(Object o, Component cmpnt) {
        String strLanguage=(String) o;
        Combobox cbx=((Combobox) cmpnt);
        int selectIndex=-1; //Nothing selected

        for(int i=0;i<cbx.getItemCount();i++)
        {
            if(cbx.getItemAtIndex(i).getLabel().equals(strLanguage))
            {
                selectIndex=i;
                break;
            }
        }
        return selectIndex;
    }

    public Object coerceToBean(Object o, Component cmpnt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}