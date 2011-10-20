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


package bbc.rd.mpeg7;

import bbc.rd.tvanytime.URI;

/**
 * MPEG7MediaLocator: Represents an mpeg7:MediaLocator object
 *
 * @author Tristan Ferne, BBC Research & Development, September 2002
 * @version 1.0
 */

public class MPEG7MediaLocator implements Cloneable
{
	private URI mediaURI;

	/**
	 * Constructor for objects of type MPEG7MediaLocator
	 */
	public MPEG7MediaLocator()
	{

	}
	/**
	 * Constructor for objects of type MPEG7MediaLocator
	 */
	public MPEG7MediaLocator(URI mediaURI)
	{
		this.mediaURI = mediaURI;
	}

	/**
	 * Set the MPEG7MediaLocator MediaURI
	 *
	 * @param      mediaURI the MPEG7MediaLocator MediaURI
	 */
	public void setMediaURI(URI mediaURI)
	{
		this.mediaURI = mediaURI;
	}

	/**
	 * Get the MPEG7MediaLocator MediaURI
	 *
	 * @return     the MPEG7MediaLocator MediaURI
	 */
	public URI getMediaURI()
	{
		return mediaURI;
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
	 * @param   number of tabs to put before the string.
	 * @return  XML representation of this object.
	 */
	public String toXML(int indent)
	{
		String xml = "";

		for (int i=0;i<indent;i++) {
			xml += "\t";
    }

		xml += "<MediaLocator>";
      indent++;

    if (mediaURI != null) {
      xml += "\n";
  		for (int i=0;i<indent;i++) {
    		xml += "\t";
      }
      xml = xml + "<mpeg7:MediaUri><![CDATA[" + mediaURI.getURI() + "]]></mpeg7:MediaUri>";
    }
    xml += "\n";
		for (int i=0;i<indent-1;i++) {
			xml += "\t";
    }
		xml += "</MediaLocator>";

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
	 * @param   number of tabs to put before the string.
	 * @return  string representation of this object.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "MPEG7MediaLocator: MediaURI = " + mediaURI;

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    MPEG7MediaLocator clone = new MPEG7MediaLocator();
    if (mediaURI != null) clone.setMediaURI((URI)mediaURI.clone());
    return clone;
  }

}

