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


/**
 *
 * LocationsResult:	A TVAnytime LocationsResult object.
 * The Locators inside this element can be generic bbc.rd.tvanytime.contentReferencing.Locator objects,
 * or transport-specific Locators, such as bbc.rd.tvanytime.contentReferencing.DVBLocator objecys
 * (which represent DVB locators)
 *
 * @author Tim Sargeant, BBC Research &	Development, February 2003
 * @version	1.0
 * @see bbc.rd.tvanytime.contentReferencing.Locator
 * @see bbc.rd.tvanytime.contentReferencing.DVBLocator
 */

public class LocationsResult implements	Cloneable, Serializable
{
	private Vector locators = new Vector(0,1);

	/**
	* Constructor for objects of type LocationsResult.
	*
	*/
	public LocationsResult() {

	}

	/**
	*
	* Add a Locator object.
	*
	* @param	 The Locator object to add to this table.
	*/
	public void addLocator(Locator locator) {
		this.locators.addElement(locator);
	}

	/**
	*
	* Get a Locator object.
	*
	* @param	index The index of the Locator object to access.
	* @return 	Specified Locator object.
	*/
	public Locator getLocator(int index) {

		return (Locator)locators.elementAt(index);
	}

	/**
	*
	* Get the number of Locator objects.
	*
	* @return  The number of Locator objects contained in this table.
	*/
	public int getNumLocators() {

		return locators.size();
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

            synchronized(xmlBuf) {
                // Do tabs
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }

                xmlBuf.append("<LocationsResult>\n");
                //xml += "<LocationsResult>\n";

                // Indent and call children
                indent++;

                // locators
                for (int i=0; i<locators.size();i++) {
                    // Do tabs
                    for (int j=0;j<indent;j++) {
                        xmlBuf.append("\t");
                        //xml += "\t";
                    }
                    xmlBuf.append(((Locator)locators.elementAt(i)).toXML());
                    xmlBuf.append("\n");
                    //xml = xml + ((Locator)locators.elementAt(i)).toXML() + "\n";
                }

                for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }

                xmlBuf.append("</LocationsResult>");
                //xml += "</LocationsResult>";
                return xmlBuf.toString();
            }
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
		sysOut += "LocationsResult:\n";

		// Indent and call children
		indent++;
		/*
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
		}
		*/

    // locators
		for (int i=0; i<locators.size();i++) {
			sysOut = sysOut + "\n" + ((Locator)locators.elementAt(i)).toString(indent);
			/*
			for (int j=0;j<indent;j++) {
				sysOut += "\t";
			}
			*/
		}

		return sysOut;
	}

	/**
	* Clones itself.
	* @return A	copy of	itself.
	*/
	public Object	clone()	{
		LocationsResult clone =	new	LocationsResult();
		for (int ct=0; ct<getNumLocators(); ct++) {
			clone.addLocator((Locator)getLocator(ct).clone());
		}
		return clone;
	}
}