/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lince.tvanytime.core.converter;

import java.sql.Timestamp;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Label;

/**
 *
 * @author lince
 */
public class TimeConverter implements TypeConverter {

    public Object coerceToUi(Object o, Component cmpnt) {
        ((Label) cmpnt).setValue(new Timestamp(((Long) o).longValue()).toString());
        return cmpnt;
    }

    public Object coerceToBean(Object o, Component cmpnt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}