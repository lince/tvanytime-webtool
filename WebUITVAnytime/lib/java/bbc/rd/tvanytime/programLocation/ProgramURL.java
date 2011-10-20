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


package	bbc.rd.tvanytime.programLocation;

/**
 *
 * ProgramURL:	A TVAnytime ProgramURL object.
 *
 * This represents the URL of a programme. It may be a URL (i.e. for a streamed 
 * programme), a DVB locator or something else. Appropriate toolboxes, for each
 * transport mechanism, that extract information from the URLs are provided in 
 * bbc.rd.tvanytime.util
 *
 * @author Tim Sargeant, BBC Research &	Development, May 2003
 * @version	1.0
 */

public class ProgramURL implements Cloneable
{
	/**
	 * The ProgramURL
	 */
	String programURL;

	/**
	* Default Constructor for objects of type ProgramURL.
	*
	*/
	public ProgramURL() {

	}

	/**
	* Constructor for objects of type ProgramURL.
	*
	*/
	public ProgramURL(String programURL) {
		setProgramURL(programURL);
	}

	/**
	 * sets the programURL
	 * Could throw an exception (but never does here).  This means that extensions of this
	 * class can do validity checking on the programURL string and throw an exception if appropriate.
	 */
	public void setProgramURL(String programURL) {
		this.programURL = programURL;
	}

	/**
	 * gets the programURL as a String
	 */
	public String getProgramURL() {
		return programURL;
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

		synchronized (xmlBuf) {
			// Do tabs
			for (int i = 0; i < indent; i++) {
				xmlBuf.append("\t");
			}

			xmlBuf.append("<ProgramURL>");
			xmlBuf.append(programURL);
			xmlBuf.append("</ProgramURL>");
			return xmlBuf.toString();
		}
	}


	/**
	 * Returns string representation of this object.
	 * 
	 * @return String representation of this object.
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
		sysOut += "ProgramURL: "+programURL+"\n";
		return sysOut;
	}

	/**
	* Clones itself.
	* @return A	copy of	itself.
	*/
	public Object	clone()	{
		ProgramURL clone = new ProgramURL();
    if (programURL != null) clone.setProgramURL(new String(programURL));

		return clone;
	}
}