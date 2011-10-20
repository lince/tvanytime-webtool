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


package bbc.rd.tvanytime.segmentInformation;

import bbc.rd.tvanytime.*;

/**
 * SegmentInformation: Represents a SegmentInformation object
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 */

public class SegmentInformation implements Cloneable
{
	private String segmentID;						// mandatory

	private ContentReference programRef;			// mandatory

	private BasicSegmentDescription description;	// optional (0..1)

	private SegmentLocator segmentLocator;			// mandatory

	/**
	 * Constructor for objects of class SegmentInformation
	 */
	public SegmentInformation()
	{

	}

	/**
	 * Constructor for objects of class SegmentInformation with all required fields
	 *
	 * @param  segmentID the segment ID
	 * @param  programRef a ContentReference object
	 * @param  segmentLocator a SegmentLocator object
	 *
	 */
	public SegmentInformation(String segmentID, ContentReference programRef, SegmentLocator segmentLocator)
	{
		this();

		this.segmentID = segmentID;
		this.programRef = programRef;
		this.segmentLocator = segmentLocator;
	}

	/**
	 * getDescription - gets the Description
	 *
	 * @return     the BasicSegmentDescription object or null if it doesn't exist
	 */
	public BasicSegmentDescription getDescription()
	{
		return description;
	}

	/**
	 * setDescription - set the Description
	 *
	 * @param description the BasicSegmentDescription object
	 */
	public void setDescription(BasicSegmentDescription description)
	{
		this.description = description;
	}

	/**
	 * getSegmentLocator - gets the SegmentLocator
	 *
	 * @return     the SegmentLocator object
	 */
	public SegmentLocator getSegmentLocator()
	{
		return segmentLocator;
	}

	/**
	 * setSegmentLocator - set the SegmentLocator
	 *
	 * @param segmentLocator the SegmentLocator object
	 */
	public void setSegmentLocator(SegmentLocator segmentLocator)
	{
		this.segmentLocator = segmentLocator;
	}

	/**
	 * getSegmentID - gets the segmentID
	 *
	 * @return     the segmentID
	 */
	public String getSegmentID()
	{
		return segmentID;
	}

	/**
	 * getProgramRef - gets the programRef
	 *
	 * @return     the programRef
	 */
	public ContentReference getProgramRef()
	{
		return programRef;
	}

	/**
	 * setSegmentID - sets the segmentID
	 *
	 * @param     segmentID the segmentID
	 */
	public void setSegmentID(String segmentID)
	{
		this.segmentID = segmentID;
	}

	/**
	 * setProgramRef - sets the programRef
	 *
	 * @param     programRef the programRef (a ContentReference object)
	 */
	public void setProgramRef(ContentReference programRef)
	{
		this.programRef = programRef;
	}

	/**
	* Returns XML representation of this table.
	*
	* @return  XML representation of this table.
	*/
	public String	toXML()	{
		return toXML(0);
	}

	/**
	* Returns XML representation of this	table.
	*
	* @param	indent  Number of tabs with which to indent the string.
	* @return 	XML representation of the table.
	*/
	public String	toXML(int indent) {
		StringBuffer xmlBuf = new StringBuffer();
		//String xml = "";
		
		synchronized(xmlBuf) {
			// Do tabs
			for (int i=0;i<indent;i++) {
				xmlBuf.append("\t");
				//xml += "\t";
			}
			xmlBuf.append("<SegmentInformation segmentId=\"");
			xmlBuf.append(segmentID);
			xmlBuf.append("\">");
			//xml += "<SegmentInformation segmentId=\""+segmentID+"\">";
			
			// Indent and call children
			indent++;
			
			if(programRef != null) {
				xmlBuf.append("\n");
				//xml += "\n";
				// Do tabs
				for (int i=0;i<indent;i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<ProgramRef crid=\"");
				xmlBuf.append(programRef.toXML(indent));
				xmlBuf.append("\"/>");
				//xml += "<ProgramRef crid=\""+ programRef.toXML(indent) +"\"/>";
			}
			if(description != null) {
				xmlBuf.append("\n");
				xmlBuf.append(description.toXML(indent));
				//xml += "\n" + description.toXML(indent);
			}
			if(segmentLocator != null) {
				xmlBuf.append("\n");
				xmlBuf.append(segmentLocator.toXML(indent));
				//xml += "\n" + segmentLocator.toXML(indent);
			}
			
			xmlBuf.append("\n");
			//xml += "\n";
			for (int i=0;i<indent-1;i++) {
				xmlBuf.append("\t");
				//xml += "\t";
			}
			xmlBuf.append("</SegmentInformation>");
			//xml += "</SegmentInformation>";
			return xmlBuf.toString();
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
	 * @param   indent number of tabs to put before the string.
	 * @return  string representation of this object.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		sysOut += "\n";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "SegmentInformation:";

		indent++;

		sysOut += "\n";
		for (int i=0;i<indent;i++)
			sysOut += "\t";
		sysOut += "SegmentID: " + segmentID;

		sysOut += "\n";
		sysOut += programRef.toString(indent);

		sysOut += "\n";
		sysOut += description.toString(indent);

		if (segmentLocator != null) sysOut += segmentLocator.toString(indent);

		return sysOut;
	}

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		SegmentInformation clone = new SegmentInformation();

		clone.setDescription((BasicSegmentDescription)description.clone());
		clone.setProgramRef((ContentReference)programRef.clone());
		clone.setSegmentID(new String(""+segmentID));
		clone.setSegmentLocator((SegmentLocator)segmentLocator.clone());

		return clone;
	}
}

