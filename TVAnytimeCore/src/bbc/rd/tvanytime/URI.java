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

/**
 * URI: Represents a URI
 *
 * Java 1.1 does not contain java.net.URI, so we need to write our own
 * (very simple) for compatability with this java version.
 *
 * @author Tim Sargeant, BBC Research & Development, October 2002
 * @version 1.0
 */

public class URI implements Cloneable
{
	private String uri;

	/**
	 * Constructor for objects of type URI
	 */
	public URI()
	{

	}

	/**
	 * Constructor for objects of type URI with required fields.
	 * Tests for validity of "*://" portion of uri, and throws a TVAnytimeException if invalid.
	 * Does not currently test any other part of the URI.
	 *
	 * @param	uri	string representation of the URI (e.g. "ftp://bbc.co.uk/21837")
	 * @throws	TVAnytimeException thrown when URI starts with invalid pattern
	 */
	public URI(String uri) throws TVAnytimeException
	{
		setURI(uri);
	}

	/**
	 * Get the URI
	 *
	 * @return     the String representation of the URI (e.g. "ftp://bbc.co.uk/21837")
	 * or null if uri is undefined
	 */
	public String getURI()
	{
		return uri;
	}

	/**
	 * Are these URIs equal?
	 *
	 * @param uri The possibly null URI to be compared
	 * @return     true if URI is equal to this URI; false otherwise
	 */
	public boolean equals(Object obj)
	{
    URI testURI = null;
		if (obj == null) return false;
    if (obj instanceof URI) testURI = (URI)obj;
    else return false;
		if ((testURI.getURI()).equals(uri)) return true;
		else return false;
	}

	/**
	 * Set the URI. Tests it is valid URI format "<scheme>:<scheme specific part>
	 * and throws a TVAnytimeException if invalid.
	 *
	 * @param	uri	the URI as a String
	 * @return	TVAnytimeException thrown if uri is invalid
	 */
	public void setURI(String uri) throws TVAnytimeException
	{
		if (uri == null)
			throw new TVAnytimeException ("Invalid URI; Cannot create a URI from a null value");
		else {
  		int colonAt = uri.indexOf(':');
      if ( (colonAt > 0) && (colonAt < uri.length()-1) ) {
        // Checks if valid by checking that at least 1 character before and after :
  			// set the crid
    		this.uri = uri;
      }
      else throw new TVAnytimeException ("Invalid URI; Must be of the form <scheme>:<scheme specific part>, you gave me '" + uri + "'");
		}
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

		sysOut += "URI: " + uri;

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    URI clone = new URI();
    try {
      if (uri != null) clone.setURI(new String(uri));
    } catch (TVAnytimeException tvae) { }
    return clone;
  }

}