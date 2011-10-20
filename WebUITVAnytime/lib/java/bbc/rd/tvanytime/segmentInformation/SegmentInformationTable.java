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

/**
 * SegmentInformationTable: Represents a SegmentInformation table
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 */

public class SegmentInformationTable implements Cloneable
{
	private String timeUnit;
	private boolean timeUnitSet = false;

	private SegmentList segmentList;
	private SegmentGroupList segmentGroupList;

	/**
	 * Constructor for objects of class SegmentInformationTable.
	 */
	public SegmentInformationTable()
	{

	}

	/**
	 * Constructor for objects of class SegmentInformationTable with required fields.
	 *
	 * @param  segList a SegmentList object
	 * @param  segGroupList a SegmentGroupList object
	 */
	public SegmentInformationTable(SegmentList segmentList, SegmentGroupList segmentGroupList)
	{
		this();
		this.segmentList = segmentList;
		this.segmentGroupList = segmentGroupList;
	}

	/**
	 * getSegmentList - gets the SegmentList objects belonging to this SegmentInformationTable
	 *
	 * @return     the SegmentList object in this SegmentInformationTable
	 */
	public SegmentList getSegmentList()
	{
		return segmentList;
	}

	/**
	 * setSegmentList - sets the SegmentList objects belonging to this SegmentInformationTable
	 *
	 * @param     the SegmentList object in this SegmentInformationTable
	 */
	public void setSegmentList(SegmentList segmentList)
	{
		this.segmentList = segmentList;
	}

	/**
	 * getSegmentGroupList - gets the SegmentGroupList objects belonging to this SegmentInformationTable
	 *
	 * @return     the SegmentGroupList object in this SegmentInformationTable
	 */
	public SegmentGroupList getSegmentGroupList()
	{
		return segmentGroupList;
	}

	/**
	 * setSegmentGroupList - sets the SegmentGroupList objects belonging to this SegmentInformationTable
	 *
	 * @param     the SegmentGroupList object in this SegmentInformationTable
	 */
	public void setSegmentGroupList(SegmentGroupList segmentGroupList)
	{
		this.segmentGroupList = segmentGroupList;
	}

	/**
	 * setTimeUnit - sets the timeUnit
	 *
	 * @param     timeUnit the time unit
	 */
	public void setTimeUnit(String timeUnit)
	{
		this.timeUnit = timeUnit;
		timeUnitSet = true;
	}

	/**
	 * getTimeUnit - gets the timeUnit
	 *
	 * @return      the timeUnit
	 */
	public String getTimeUnit()
	{
		return timeUnit;
	}

	/**
	* Returns XML representation	of this	table.
	*
	* @return  XML representation of	this table.
	*/
	public String	toXML()	{
		return toXML(0);
	}

	/**
	 * Returns XML representation	of this	table.
	 *
	 * @param	  indent  Number of	tabs with which	to indent the string.
	 * @return  XML representation of	the	table.
	 */
	public String	toXML(int indent) {
		StringBuffer xmlBuf = new StringBuffer();
		
		synchronized(xmlBuf) {
			// Do tabs
			for (int i=0;i<indent;i++) {
				xmlBuf.append("\t");
			}
			xmlBuf.append("<SegmentInformationTable ");
			
			if (timeUnitSet) {
				xmlBuf.append("timeUnit=\"");
				xmlBuf.append(timeUnit);
				xmlBuf.append("\">");
			}
			else {
				xmlBuf.append(">");
			}
			// Indent and call children
			indent++;
			
			if(segmentList != null) {
				xmlBuf.append("\n");
				xmlBuf.append(segmentList.toXML(indent));
			}
			if(segmentGroupList != null) {
				xmlBuf.append("\n");
				xmlBuf.append(segmentGroupList.toXML(indent));
			}
			xmlBuf.append("\n");
			for (int i=0;i<indent-1;i++) {
				xmlBuf.append("\t");
			}
			xmlBuf.append("</SegmentInformationTable>");
			return xmlBuf.toString();
		}
	}

	/**
	 * Returns string representation of this table.
	 *
	 * @return  string representation of this table.
	 */
	public String toString()
	{
		return toString(0);
	}

	/**
	 * Returns string representation of this table.
	 *
	 * @param  indent number of tabs to put before the string.
	 * @return  string representation of this table.
	 */
	public String toString(int indent)
	{
		String sysOut = "";
		
		for (int i=0;i<indent;i++)
			sysOut += "\t";
		
		sysOut += "\nSegmentInformationTable:";
		
		indent++;
		
		if(timeUnit != null) {
			sysOut += "\n";
			
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			
			if (timeUnitSet)
				sysOut += "TimeUnit: " + timeUnit;
		}
		
		if(segmentList != null) {
			sysOut += "\n";
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "SegmentList: " + segmentList;
		}
		
		if(segmentGroupList != null) {
			sysOut += "\n";
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "SegmentGroupList: " + segmentGroupList;
		}
		
		return sysOut;
	}


	/**
	* Removes everything.
	*/
	public void removeAll() {
		if (segmentGroupList != null) segmentGroupList.removeAll();
		if (segmentList != null) segmentList.removeAll();
		timeUnit = null;
		timeUnitSet = false;
	}


	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		SegmentInformationTable clone = new SegmentInformationTable();

		clone.setTimeUnit(new String (""+timeUnit));
		if(segmentList != null) clone.setSegmentList((SegmentList)segmentList.clone());
		if(segmentGroupList != null) clone.setSegmentGroupList((SegmentGroupList)segmentGroupList.clone());

		return clone;
	}
}

