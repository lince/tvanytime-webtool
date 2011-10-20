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
 * SegmentGroupList: Represents a SegmentGroupList object
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 */

public class SegmentGroupList implements Cloneable
{
	private Vector segmentGroupInformation;
	private boolean segmentGroupInformationExists = false;

	/**
	 * Constructor for objects of class SegmentGroupList
	 */
	public SegmentGroupList()
	{
		segmentGroupInformation = new Vector(0,1);
	}

	/**
	 * getNumSegmentGroupInformations - gets the number of SegmentGroupInformation objects belonging to this SegmentGroupList
	 *
	 * @return     the number of SegmentGroupInformation objects in this SegmentGroupList
	 */
	public int getNumSegmentGroupInformations()
	{
		if (segmentGroupInformationExists)
			return segmentGroupInformation.size();
		else
			return 0;
	}

	/**
	 * getSegmentGroupInformations - gets the SegmentGroupInformation objects specified by index
	 *
	 * @param      index the index to the SegmentGroupInformation object
	 * @return     the SegmentGroupInformation object
	 */
	public SegmentGroupInformation getSegmentGroupInformation(int index)
	{
		if (segmentGroupInformationExists)
			return (SegmentGroupInformation)segmentGroupInformation.elementAt(index);
		else
			return null;
	}

	/**
	 * addSegmentGroupInformation - adds a SegmentGroupInformation object
	 *
	 * @param     segGroupInfo the SegmentGroupInformation object
	 */
	public void addSegmentGroupInformation(SegmentGroupInformation segGroupInfo)
	{
		segmentGroupInformation.addElement(segGroupInfo);
		segmentGroupInformationExists = true;
	}

	/**
	 * removeAll - removes all SegmentGroupInformation objects
	 */
	public void removeAll()
	{
		segmentGroupInformation.removeAllElements();
		segmentGroupInformationExists = false;
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
                xmlBuf.append("<SegmentGroupList>");
                //xml += "<SegmentGroupList>";

                // Indent and call children
                indent++;

                for (int i=0; i<segmentGroupInformation.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((SegmentGroupInformation)segmentGroupInformation.elementAt(i)).toXML(indent));
                    //xml += "\n"+((SegmentGroupInformation)segmentGroupInformation.elementAt(i)).toXML(indent);
                }
                xmlBuf.append("\n");
                //xml += "\n";
                for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("</SegmentGroupList>");
                //xml += "</SegmentGroupList>";
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

		sysOut += "SegmentGroupList:";

		indent++;

		for (int i=0; i<segmentGroupInformation.size();i++)
			sysOut += ((SegmentGroupInformation)segmentGroupInformation.elementAt(i)).toString(indent);

		return sysOut;
	}

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		SegmentGroupList clone = new SegmentGroupList();

		for (int i=0; i<segmentGroupInformation.size();i++)
			clone.addSegmentGroupInformation((SegmentGroupInformation)((SegmentGroupInformation)segmentGroupInformation.elementAt(i)).clone());

		return clone;
	}
}
