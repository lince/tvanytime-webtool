/**
 * Copyright 2004 British Broadcasting Corporation
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

package bbc.rd.tvanytime;

import java.io.Serializable;

/**
 * Represents an InstanceMetadataId. This is an optional identifier that shall 
 * identify a particular location related to a CRID (i.e. a programme). This
 * identifier shall be unique within the CRID domain
 * 
 * @author Tristan Ferne, BBC Research & Development, August 2004
 * @version 1.0
 */
public class InstanceMetadataId implements Serializable {

  /**
   * Instance metadata ID string
   */
  private String imi;

  /**
   * Constructor.
   */
  public InstanceMetadataId() {
  }
  
  /**
   * Constructor.
   * 
   * @param  imi  Instance metadata ID. Should be a URI in the form "imi:<data>"
	 * @throws	TVAnytimeException thrown when IMI starts with invalid pattern
   */
  public InstanceMetadataId(String imi) throws TVAnytimeException {
    setInstanceMetadataId(imi);
  }
  
	/**
	 * Are these IMIs equal?
	 *
	 * @param  testIMI  The InstanceMetadataID to be compared.
	 * @return  true if InstanceMetadataID is equal to this InstanceMetadataID; false otherwise
	 */
	public boolean equals(InstanceMetadataId testIMI)	{
		if (testIMI == null) return false;
		else if ((testIMI.getInstanceMetadataId()).equalsIgnoreCase(imi))	return true;
		else return false;
	}

	/**
	 * Set the InstanceMetadataId. Tests for validity of "imi:"
	 * portion of IMI, and throws a TVAnytimeException if invalid.
	 *
	 * @param	imi	 The IMI as a String
	 * @return	TVAnytimeException thrown when start of the IMI is invalid
	 */
	public void setInstanceMetadataId(String imi) throws TVAnytimeException
	{
    if (imi == null) throw new TVAnytimeException ("Invalid IMI; String was null");
		if (imi.length() < 5) {
			throw new TVAnytimeException ("Invalid IMI; the minimum length CRID is 'imi:<value>', you gave me something shorter: '" + imi + "'");
    }
		if (imi.substring(0,4).equalsIgnoreCase("imi:")) {
			// set the IMI
			this.imi = imi;
		}
		else throw new TVAnytimeException ("Invalid IMI; must start with 'imi:', you gave me '" + imi + "'");
	}

	/**
	 * Get the IMI.
	 *
	 * @return  The String representation of the IMI (e.g. "imi:bbc.co.uk/21837")
	 * or null if crid is undefined
	 */
	public String getInstanceMetadataId()	{
		return imi;
	}


	/**
	 * Returns XML representation of this object.
	 *
	 * @return  XML representation of this object.
	 */
	public String toXML()	{
		return toXML(0);
	}

	/**
	 * Returns XML representation of this object.
	 *
	 * @param	indent number of tabs to put before the string.
	 * @return  XML representation of this object.
	 */
        public String toXML(int indent)	{
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";
            
            synchronized(xmlBuf) {
                for (int ct=0; ct<indent; ct++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append(imi);
                //xml += imi;
                return xmlBuf.toString();
            }
	}


	/**
	 * Returns string representation of this object.
	 *
	 * @return  string representation of this object.
	 */
	public String toString() {
		return toString(0);
	}

	/**
	 * Returns string representation of this object.
	 *
	 * @param	indent number of tabs to put before the string.
	 * @return  string representation of this object.
	 */
	public String toString(int indent)
	{
		String sysOut = "";
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
		}
		sysOut += "IMI: " + imi;

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    InstanceMetadataId clone = new InstanceMetadataId();
    try {
      if (imi != null) clone.setInstanceMetadataId(new String(imi));
    } catch (TVAnytimeException tvae) { }
    return clone;
  }
  
  
}