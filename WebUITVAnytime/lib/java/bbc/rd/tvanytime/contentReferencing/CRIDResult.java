/**
 * Copyright 2003 BBC Research & Development
 *
 * This file is part of the BBC R&D TV-Anytime Java API.
 *
 * The BBC R&D TV-Anytime Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * The BBC R&D TV-Anytime Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the BBC R&D TV-Anytime Java API; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */


package	bbc.rd.tvanytime.contentReferencing;

import java.io.Serializable;
import java.util.*;
import bbc.rd.tvanytime.*;

/**
 *
 * CRIDResult:	A TVAnytime CRIDResult object.
 *
 * @author Tim Sargeant, BBC Research &	Development, February 2003
 * @version	1.0
 */

public class CRIDResult implements Cloneable, Serializable
{
	private Vector crids = new Vector(0,1);

	/**
	* Constructor for objects of type CRIDResult.
	*
	*/
	public CRIDResult() {

	}

 /**
	*
	* Add a ContentReference object.
	*
	* @param	 The ContentReference object to add to this table.
	*/
	public void addCRID(ContentReference crid) {
		this.crids.addElement(crid);
	}

 /**
	*
	* Remove a ContentReference object.
	*
	* @param	 The ContentReference object to remove from this table.
  * @return  Whether the ContentReference object was present and was successfully
  * removed.
	*/
	public boolean removeCRID(ContentReference crid) {
		return this.crids.removeElement(crid);
	}

	/**
	*
	* Get a ContentReference object.
	*
	* @param	index The index of the ContentReference object to access.
	* @return 	Specified ContentReference object.
	*/
	public ContentReference getCRID(int index) {

		return (ContentReference)crids.elementAt(index);
	}

	/**
	*
	* Get the number of ContentReference objects.
	*
	* @return  The number of ContentReference objects contained in this table.
	*/
	public int getNumCRIDs() {

		return crids.size();
	}

	/**
	* Returns XML representation of this object.
	*
	* @return XML representation of	this object.
	*/
	public String toXML()	{
		return toXML(0);
	}

	/**
	* Returns XML representation of this object.
	*
	* @param	  indent  Number of	tabs with which	to indent the string.
	* @return  XML representation of the object.
	*/
	public String toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";

            // Do tabs
            for (int i=0;i<indent;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }

            xmlBuf.append("<CRIDResult>\n");
            //xml += "<CRIDResult>\n";

            // Indent and call children
            indent++;

            // crids
            for (int i=0; i<crids.size();i++) {
                // Do tabs
                for (int j=0;j<indent;j++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
		xmlBuf.append("<Crid>");
                xmlBuf.append(((ContentReference)crids.elementAt(i)).toXML(indent));
                xmlBuf.append("</Crid>\n");
                //xml = xml + "<Crid>" + ((ContentReference)crids.elementAt(i)).toXML(indent) + "</Crid>\n";
            }

            for (int i=0;i<indent-1;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }

            xmlBuf.append("</CRIDResult>");
            //xml += "</CRIDResult>";
            return xmlBuf.toString();
        }


	/**
	* Returns string representation of this object.
	*
	* @return  String representation of this object.
	*/
	public String toString() {
		return toString(0);
	}

	/**
	* Returns string representation of this object.
	*
	* @param	  indent  Number of	tabs with which	to indent the string.
	* @return  string representation of the object.
	*/
	public String toString(int indent) {
		String sysOut = "";

		// Do tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
		}
		sysOut += "CRIDResult:\n";

		// Indent and call children
		indent++;
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
		}

	    // crids
		for (int i=0; i<crids.size();i++) {
			sysOut = sysOut + "\n" + ((ContentReference)crids.elementAt(i)).toString(indent);
		}

		return sysOut;
	}

	/**
	* Clones itself.
	* @return A	copy of	itself.
	*/
	public Object	clone()	{
		CRIDResult clone = new CRIDResult();
		for (int ct=0; ct<getNumCRIDs(); ct++) {
			clone.addCRID((ContentReference)getCRID(ct).clone());
		}
		return clone;
	}
}
