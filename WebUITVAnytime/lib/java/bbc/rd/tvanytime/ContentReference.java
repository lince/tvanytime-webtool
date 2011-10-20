/**
 * Copyright 2003 British Broadcasting Corporation
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
 * ContentReference: Represents a TV-Anytime CRID
 *
 * NOTE: Doesn't seem to work within a hashtable as a key, even though hashCode()
 * method has been implemented. Solution is to use CRID.getCRID() (i.e. a string)
 * as the key.
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 * 
 * Modified T.Ferne, September 2004
 */

public class ContentReference implements Cloneable, Serializable
{
	private String crid;		// the whole crid, e.g. "crid://bbc.co.uk/123456"
	private String data;		// the data part of the crid, e.g. "123456"
	private String authority;	// the authority part of the crid, e.g. "bbc.co.uk"

	/**
	 * Constructor for objects of type ContentReference
	 */
	public ContentReference()
	{

	}

	/**
	 * Constructor for objects of type ContentReference with required fields.
	 * Tests for validity of "crid://"
	 * portion of crid, and throws a TVAnytimeException if invalid.
	 * Does not test for valid DNS name.
	 *
	 * @param	crid	string representation of the CRID (e.g. "crid://bbc.co.uk/21837")
	 * @throws	TVAnytimeException thrown when crid starts with invalid pattern
	 */
	public ContentReference(String crid) throws TVAnytimeException
	{
		setCRID(crid);
	}

	/**
	 * Get the authority part of this CRID
	 *
	 * @return     the resolving authority for this CRID (e.g. "bbc.co.uk") or null
	 * if crid is undefined
	 */
	public String getAuthority()
	{
		if(crid != null) {
			return authority;
		}
		else return null;
	}

	/**
	 * Get the data part of this CRID
	 *
	 * @return     the unique data part of this CRID (e.g. "21837") or null
	 * if crid is undefined
	 */
	public String getData()
	{
		if(crid != null) {
			return data;
		}
		else return null;
	}

	/**
	 * Get the whole of the CRID
	 *
	 * @return     the String representation of the CRID (e.g. "crid://bbc.co.uk/21837")
	 * or null if crid is undefined
	 */
	public String getCRID()
	{
		return crid;
	}

	/**
	 * Are these CRIDs equal?
	 *
	 * @param crid The possibly null ContentReference to be compared
	 * @return     true if ContentReference is equal to this ContentReference; false otherwise
	 */
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		if (obj instanceof ContentReference) {
			if ((((ContentReference)obj).getCRID()).equalsIgnoreCase(crid)) {
				return true;
			}
		}
	  return false;
	}

  /**
   * Used in Hashtable. Just returns has code of CRID string.
   * NOTE: Doesn't seem to work correctly.
   * @return  Hashcode for CRID.
   */
  public int hashCode() {
    return crid.hashCode();
  }


	/**
	 * Set the CRID  Tests for validity of "crid://"
	 * portion of crid, and throws a TVAnytimeException if invalid.
	 * Does not test for valid DNS name.
	 *
	 * @param	crid	the CRID as a String
	 * @return	TVAnytimeException thrown when start of the crid is invalid
	 */
	public void setCRID(String crid) throws TVAnytimeException
	{
    if (crid == null) throw new TVAnytimeException ("Invalid CRID; String was null");
		if (crid.length() < 10) {
			throw new TVAnytimeException ("Invalid CRID; the minimum length CRID is 'crid://<authority>/<value>', you gave me something shorter: '" + crid + "'");
    }
		if (crid.substring(0,7).equalsIgnoreCase("crid://")) {
			// set the crid
			this.crid = crid;
			if (crid.indexOf("/", 7) > -1) {
  			// set authority and data
    		authority = crid.substring(7, crid.indexOf("/", 7));
      	data = crid.substring(crid.indexOf("/", 7) + 1);
      }
      else {
        throw new TVAnytimeException ("Invalid CRID; should be 'crid://<authority>/<value>', you gave me '" + crid + "'");
      }
		}
		else throw new TVAnytimeException ("Invalid CRID; must start with 'crid://', you gave me '" + crid + "'");
	}

	/**
	 * Returns XML representation of this object.
	 *
	 * @return  XML representation of this object.
	 */
	public String toXML()
	{
		return toXML(0);
	}

	/**
	 * Returns XML representation of this object.
	 *
	 * @param	indent number of tabs to put before the string.
	 * @return  XML representation of this object.
	 */
	public String toXML(int indent)
	{
		String xml = crid;
		return xml;
	}


	/**
	 * Returns string representation of this object.
	 *
	 * @return  string representation of this object.
	 */
	public String toString()
	{
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

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "CRID: " + crid;

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    ContentReference clone = new ContentReference();
    try {
      if (crid != null) clone.setCRID(new String(crid));
    } catch (TVAnytimeException tvae) { }
    return clone;
  }



}