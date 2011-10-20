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

import java.util.Vector;

/**
 * SegmentList: Represents a list of segments
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 */

public class SegmentList implements Cloneable
{
	private Vector segmentInformation;
	private boolean segmentInformationExists = false;

	/**
	 * Constructor for objects of class SegmentList
	 */
	public SegmentList()
	{
		segmentInformation = new Vector(0,1);
	}

	/**
	 * Constructor for objects of class SegmentList with required fields
	 *
	 * @param   segInfo a SegmentInformation object
	 */
	public SegmentList(SegmentInformation segInfo)
	{
		this();
		segmentInformation.addElement(segInfo);
		segmentInformationExists = true;
	}

	/**
	 * getNumSegmentInformations - gets the number of SegmentInformation objects belonging to this SegmentList
	 *
	 * @return     the number of SegmentInformation objects in this SegmentList
	 */
	public int getNumSegmentInformations()
	{
		if (segmentInformationExists)
			return segmentInformation.size();
		else
			return 0;
	}

	/**
	 * getSegmentInformations - gets the SegmentInformation objects specified by index
	 *
	 * @param      index the index to the SegmentInformation object
	 * @return     the SegmentInformation object
	 */
	public SegmentInformation getSegmentInformation(int index)
	{
		if (segmentInformationExists)
			return (SegmentInformation)segmentInformation.elementAt(index);
		else
			return null;
	}

	/**
	 * getSegmentInformation - gets the SegmentInformation object for specified segID. Returns
	 * 'null' if no match
	 *
	 * @param      segID the segID
	 * @return     the SegmentInformation object
	 */
	public SegmentInformation getSegmentInformation(String segID)
	{
		if (segmentInformationExists)
		{
			for (int i=0; i<segmentInformation.size(); i++)
			{
				if (((SegmentInformation)segmentInformation.elementAt(i)).getSegmentID().equals(segID))
				{
					return (SegmentInformation)segmentInformation.elementAt(i);
				}
			}
			return null;
		}
		else
			return null;
	}

	/**
	 * addSegmentInformation - adds a SegmentInformation object
	 *
	 * @param     segInfo the SegmentInformation object
	 */
	public void addSegmentInformation(SegmentInformation segInfo)
	{
		segmentInformation.addElement(segInfo);
		segmentInformationExists = true;
	}

	/**
	 * removeAll - removes all SegmentInformation objects
	 */
	public void removeAll()
	{
		segmentInformation.removeAllElements();
		segmentInformationExists = false;
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
		xmlBuf.append("<SegmentList>");
                //xml += "<SegmentList>";

		// Indent and call children
		indent++;

		for (int i=0; i<segmentInformation.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((SegmentInformation)segmentInformation.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((SegmentInformation)segmentInformation.elementAt(i)).toXML(indent);
                }
		xmlBuf.append("\n");
                //xml += "\n";
		for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
		}
		xmlBuf.append("</SegmentList>");
                //xml += "</SegmentList>";
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
	 * @param  indent number of tabs to put before the string.
	 * @return  string representation of this object.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		sysOut += "\n";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "SegmentList:";

		indent++;

		for (int i=0; i<segmentInformation.size();i++)
			sysOut += ((SegmentInformation)segmentInformation.elementAt(i)).toString(indent);

		return sysOut;
	}

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		SegmentList clone = new SegmentList();

		for (int i=0; i<segmentInformation.size();i++)
			clone.addSegmentInformation((SegmentInformation)((SegmentInformation)segmentInformation.elementAt(i)).clone());

		return clone;
	}
}

