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

import bbc.rd.tvanytime.InstanceMetadataId;

/**
 *
 * Locator:	A TVAnytime Locator object.
 *
 * This is a generic Locator object.  The bbc.rd.tvanytime.util package contains a utility class
 * offering various useful methods on DVB locators.
 *
 * @author Tim Sargeant, BBC Research &	Development, May 2003
 * @version	1.0
 * 
 * Modified T.Ferne 20/9/04
 */

public class Locator implements	Cloneable, Serializable
{
	/**
	 * The Locator
	 */
	private String locator;
	/**
	 * IMI for location.
	 */
  private InstanceMetadataId imi = null;
  
	/**
	* Default Constructor for objects of type Locator.
	*
	*/
	public Locator() {

	}

	/**
	* Constructor for objects of type Locator.
	*
	*/
	public Locator(String locator) {
		setLocator(locator);
	}

	/**
	 * sets the locator
	 */
	public void setLocator(String locator) {
		this.locator = locator;
	}

	/**
	 * gets the locator as a String
	 */
	public String getLocator() {
		return locator;
	}

	/**
	 * Get the InstanceMetadataId.
	 *
	 * @return  InstanceMetadataId, or null if not set
	 */
	public InstanceMetadataId getInstanceMetadataId()	{
		return imi;
	}

	/**
	 * Set the InstanceMetadataId of this ScheduleEvent.
	 *
	 * @param  imi  An InstanceMetadataId object
	 */
	public void setInstanceMetadataId(InstanceMetadataId imi)	{
		this.imi = imi;
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

      if (imi != null) {
      	xmlBuf.append("<Locator instanceMetadataId=\"");
      	xmlBuf.append(imi.getInstanceMetadataId());
      	xmlBuf.append("\">");
      }
      else {
  	    xmlBuf.append("<Locator>");
      }
	    xmlBuf.append(locator);
	    xmlBuf.append("</Locator>");
	    //xml += "<Locator>" + locator + "</Locator>";
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
		sysOut += "Locator: "+locator;
	  if (imi != null) sysOut = sysOut + "\n" + imi.toString(indent+1);
		return sysOut;
	}

	/**
	* Clones itself.
	* @return A	copy of	itself.
	*/
	public Object	clone()	{
		Locator clone = new Locator();
		if (locator != null) clone.setLocator(new String(locator));
    if (imi != null) clone.setInstanceMetadataId((InstanceMetadataId)imi.clone());
		return clone;
	}
}