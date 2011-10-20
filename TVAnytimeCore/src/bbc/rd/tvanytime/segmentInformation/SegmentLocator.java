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

import bbc.rd.mpeg7.*;

/**
 * SegmentLocator: Represents a SegmentLocator object
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 */

public class SegmentLocator implements Cloneable
{
	private MPEG7MediaRelIncrTimePoint mpeg7MediaRelIncrTimePoint;
	private boolean mpeg7MediaRelIncrTimePointExists = false;

	private MPEG7MediaIncrDuration mpeg7MediaIncrDuration;
	private boolean mpeg7MediaIncrDurationExists = false;

	/**
	 * Constructor for objects of class SegmentLocator
	 */
	public SegmentLocator()
	{

	}

	/**
	 * addMPEG7MediaRelIncrPoint - adds a MPEG7MediaRelIncrPoint object
	 *
	 * @param     mediaPoint the MPEG7MediaRelIncrTimePoint object
	 */
	public void addMPEG7MediaRelIncrTimePoint(MPEG7MediaRelIncrTimePoint mediaPoint)
	{
		mpeg7MediaRelIncrTimePoint = mediaPoint;
		mpeg7MediaRelIncrTimePointExists = true;
	}

	/**
	 * addMPEG7MediaIncrDuration - adds a MPEG7MediaIncrDuration object
	 *
	 * @param     mediaIncrDuration the MPEG7MediaIncrDuration object
	 */
	public void addMPEG7MediaIncrDuration(MPEG7MediaIncrDuration mediaIncrDuration)
	{
		mpeg7MediaIncrDuration = mediaIncrDuration;
		mpeg7MediaIncrDurationExists = true;
	}

	/**
	 * getMPEG7MediaTimePoint - gets the MPEG7MediaRelIncrTimePoint object
	 *
	 * @return     the MPEG7MediaRelIncrTimePoint object
	 */
	public MPEG7MediaRelIncrTimePoint getMPEG7MediaRelIncrTimePoint()
	{
		if (mpeg7MediaRelIncrTimePointExists)
			return mpeg7MediaRelIncrTimePoint;
		else
			return null;
	}

	/**
	 * getMPEG7MediaIncrDuration - gets the MPEG7MediaIncrDuration object
	 *
	 * @return     the MPEG7MediaIncrDuration object
	 */
	public MPEG7MediaIncrDuration getMPEG7MediaIncrDuration()
	{
		if (mpeg7MediaIncrDurationExists)
			return mpeg7MediaIncrDuration;
		else
			return null;
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
		xmlBuf.append("<SegmentLocator>");
                //xml += "<SegmentLocator>";

		// Indent and call children
		indent++;

		if(mpeg7MediaRelIncrTimePointExists) {
                    xmlBuf.append("\n");
                    xmlBuf.append(mpeg7MediaRelIncrTimePoint.toXML(indent));
                    //xml += "\n" + mpeg7MediaRelIncrTimePoint.toXML(indent);
                }
		if(mpeg7MediaIncrDurationExists) {
                    xmlBuf.append("\n");
                    xmlBuf.append(mpeg7MediaIncrDuration.toXML(indent));
                    //xml += "\n" + mpeg7MediaIncrDuration.toXML(indent);
                }
		xmlBuf.append("\n");
                //xml += "\n";
		for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
		}
		xmlBuf.append("</SegmentLocator>");
                //xml += "</SegmentLocator>";
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
		
		sysOut += "SegmentLocator:";
		
		indent++;
		
		if(mpeg7MediaRelIncrTimePointExists) {
			//for (int i=0;i<indent;i++)
			//	sysOut += "\t";
			sysOut = sysOut + "\n" + mpeg7MediaRelIncrTimePoint.toString(indent);
		}
		
		if(mpeg7MediaIncrDurationExists) {
			//for (int i=0;i<indent;i++)
			//	sysOut += "\t";
			sysOut = sysOut + "\n" + mpeg7MediaIncrDuration.toString(indent);
		}
		
		return sysOut;
	}

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		SegmentLocator clone = new SegmentLocator();

		clone.addMPEG7MediaRelIncrTimePoint(mpeg7MediaRelIncrTimePoint);
		clone.addMPEG7MediaIncrDuration(mpeg7MediaIncrDuration);

		return clone;
	}
}

