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
import bbc.rd.tvanytime.*;
import java.util.*;

/**
 * SegmentGroupInformation: Represents segment group information object.
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 */

public class SegmentGroupInformation implements Cloneable
{
	private String groupID;
	private boolean ordered;

	private ContentReference programRef;
	private String segmentRefList;
	private String groupRefList;

	private Vector description;
	private boolean descriptionExists = false;

	private Vector groupTypes;
	private boolean groupTypeExists = false;

	private int numberOfSegments;
	private int numberOfKeyFrames;
	private int duration;
	private int topLevel;

	/**
	 * Constructor for objects of class SegmentGroupInformation.
	 */
	public SegmentGroupInformation()
	{
		description = new Vector(0,1);
		groupTypes = new Vector(0,1);
	}

	/**
	 * Constructor for objects of class SegmentGroupInformation with required fields.
	 *
	 * @param  groupID the group ID
	 * @param  groupType the group type (SHOTS, OTHER, BOOKMARKS, THEME_GROUP, etc)
	 * @param  programRef the program ref (a ContentReference object)
	 * @param  description the description
	 */
	public SegmentGroupInformation(String groupID, SegmentGroupType groupType, ContentReference programRef, BasicSegmentDescription description)
	{
		this();
		this.groupID = groupID;
		this.groupTypes.addElement(groupType);
		this.programRef = programRef;
		this.description.addElement(description);
		descriptionExists = true;
		groupTypeExists = true;
	}

	/**
	 * Gets the SegmentRefList as a String. (e.g. "seg1 seg2 seg3")
	 *
	 * @return     the segmentRefList
	 */
	public String getSegmentRefList()
	{
		return segmentRefList.trim();
	}

	/**
	 * Gets the SegmentRefList as a vector of Segment refs. (e.g. elementAt(0)=seg1, elementAt(1)=seg2, elementAt(2)=seg3)
	 * @return	the segmentRefList as a Vector (of strings)
	 */
	public Vector getSegmentRefListAsVector()
	{
		Vector result = new Vector(0,1);
		String s = segmentRefList.trim();
		int offset = 0;

		StringTokenizer tok = new StringTokenizer(s, " ");
		try {
			while (tok.hasMoreTokens()) {
				result.addElement(tok.nextToken());
			}
		} catch (NoSuchElementException e) {
			System.out.println(e);
		}

		return result;
	}

	/**
	 * Gets the GroupRefList.
	 *
	 * @return     the groupRefList
	 */
	public String getGroupRefList()
	{
		return groupRefList;
	}

	/**
	 * Gets the GroupRefList as a vector of Group refs. (e.g. elementAt(0)=group1, elementAt(1)=group2, elementAt(2)=group3)
	 * @return	the groupRefList as a Vector (of strings)
	 */
	public Vector getGroupRefListAsVector()
	{
		Vector result = new Vector(0,1);
		String s = groupRefList.trim();
		int offset = 0;

		System.out.println("s= "+s);

		StringTokenizer tok = new StringTokenizer(s, " ");
		try {
			while (tok.hasMoreTokens()) {
				result.addElement(tok.nextToken());
			}
		} catch (NoSuchElementException e) {
			System.out.println(e);
		}

		return result;
	}

	/**
	 * Gets the SegmentGroupInformation ProgramRef.
	 *
	 * @return     the programRef
	 */
	public ContentReference getProgramRef()
	{
		return programRef;
	}

	/**
	 * Is the group ordered?
	 *
	 * @return     the SegmentGroupInformation ordered flag
	 */
	public boolean isOrdered()
	{
		return ordered;
	}

	/**
	 * Gets the SegmentGroupInformation groupID.
	 *
	 * @return     the groupID
	 */
	public String getGroupID()
	{
		return groupID;
	}

	/**
	 * Sets the SegmentRefList.
	 * Since groupRefList and segmentRefList are a choice (ie. they cannot exist together,
	 * this method sets groupRefList to null.
	 *
	 * @param   segmentRefList  the segmentRefList
	 */
	public void setSegmentRefList(String segmentRefList)
	{
		this.segmentRefList = segmentRefList;
		groupRefList = null;
	}

	/**
	 * Sets the GroupRefList.
	 * Since groupRefList and segmentRefList are a choice (ie. they cannot exist together,
	 * this method sets segmentRefList to null.
	 *
	 * @param   groupRefList  the groupRefList
	 */
	public void setGroupRefList(String groupRefList)
	{
		this.groupRefList = groupRefList;
		segmentRefList = null;
	}

	/**
	 * Sets the SegmentGroupInformation ProgramRef.
	 *
	 * @param    programRef the programRef
	 */
	public void setProgramRef(ContentReference programRef)
	{
		this.programRef = programRef;
	}

	/**
	 * Set the group ordered flag.
	 *
	 * @param     ordered the SegmentGroupInformation ordered flag
	 */
	public void setOrdered(boolean ordered)
	{
		this.ordered = ordered;
	}

	/**
	 * Set the SegmentGroupInformation groupID.
	 *
	 * @param  groupID   the groupID
	 */
	public void setGroupID(String groupID)
	{
		this.groupID = groupID;
	}

	/**
	 * Get the number of BasicSegmentDescription objects belonging to this SegmentGroupInformation object.
	 *
	 * @return     the number of BasicSegmentDescription objects in this SegmentGroupInformation
	 */
	public int getNumDescriptions()
	{
		if (descriptionExists)
			return description.size();
		else
			return 0;
	}

	/**
	 * Get the number of GroupTypes belonging to this SegmentGroupInformation object.
	 *
	 * @return     the number of groupTypes in this SegmentGroupInformation
	 */
	public int getNumGroupTypes()
	{
		if (groupTypeExists)
			return groupTypes.size();
		else
			return 0;
	}

	/**
	 * Gets the groupType specified by index.
	 *
	 * @param     index the index to the groupType
	 * @return     the groupType (-1 is returned if the groupType at index has not been defined)
	 */
	public SegmentGroupType getGroupType(int index)
	{
		if (groupTypeExists)
			return (SegmentGroupType)groupTypes.elementAt(index);
		else
			return null;
	}

	/**
	 * Adds a groupType.
	 *
	 * @param     groupType the groupType object
	 */
	public void addGroupType(SegmentGroupType groupType)
	{
		this.groupTypes.addElement(groupType);
		groupTypeExists = true;
	}

	/**
	 * Gets the BasicSegmentDescription object specified by index.
	 *
	 * @param     index the index to the Description object
	 * @return     the BasicSegmentDescription object
	 */
	public BasicSegmentDescription getDescription(int index)
	{
		if (descriptionExists)
			return (BasicSegmentDescription)description.elementAt(index);
		else
			return null;
	}

	/**
	 * Adds a BasicSegmentDescription object.
	 *
	 * @param     description the BasicSegmentDescription object
	 */
	public void addDescription(BasicSegmentDescription description)
	{
		this.description.addElement(description);
		descriptionExists = true;
	}

	/**
	 * Removes all Description objects and groupTypes.
	 */
	public void removeAll()
	{
		description.removeAllElements();
		descriptionExists = false;

		groupTypes.removeAllElements();
		groupTypeExists = false;
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
                xmlBuf.append("<SegmentGroupInformation groupId=\"");
                xmlBuf.append(groupID);
                xmlBuf.append("\" ordered=\"");
                xmlBuf.append(ordered);
                xmlBuf.append("\">");
                //xml += "<SegmentGroupInformation groupId=\""+groupID+"\" ordered=\""+ordered+"\">";

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

                for (int i=0; i<groupTypes.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((SegmentGroupType)groupTypes.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((SegmentGroupType)groupTypes.elementAt(i)).toXML(indent);
                }
                for (int i=0; i<description.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((BasicSegmentDescription)description.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((BasicSegmentDescription)description.elementAt(i)).toXML(indent);
                }
                if (groupRefList != null) {
                    xmlBuf.append("\n");
                    //xml += "\n";
                    // Do tabs
                    for (int i=0;i<indent;i++) {
                        xmlBuf.append("\t");
                        //xml += "\t";
                    }
                    xmlBuf.append("<Groups refList=\"");
                    xmlBuf.append(groupRefList);
                    xmlBuf.append("\"/>");
                    //xml += "<Groups refList=\""+groupRefList+"\"/>";
                }

                if (segmentRefList != null) {
                    xmlBuf.append("\n");
                    //xml += "\n";
                    // Do tabs
                    for (int i=0;i<indent;i++) {
                        xmlBuf.append("\t");
                        //xml += "\t";
                    }
                    xmlBuf.append("<Segments refList=\"");
                    xmlBuf.append(segmentRefList);
                    xmlBuf.append("\"/>");
                    //xml += "<Segments refList=\""+segmentRefList+"\"/>";
                }

                xmlBuf.append("\n");
                //xml += "\n";
                
                for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("</SegmentGroupInformation>");
                //xml += "</SegmentGroupInformation>";
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

		sysOut += "SegmentGroupInformation:";

		indent++;

		sysOut += "\n";
		for (int i=0;i<indent;i++)
			sysOut += "\t";
		sysOut += "Group ID: " + groupID;

		sysOut += "\n";
		for (int i=0;i<indent;i++)
			sysOut += "\t";
		sysOut += "Ordered: " + ordered;

		sysOut += "\n";
		if (programRef != null)
		{
			sysOut += programRef.toString(indent);
		}
		sysOut += "\n";

		for (int i=0; i<groupTypes.size();i++)
		{
			sysOut += ((SegmentGroupType)groupTypes.elementAt(i)).toString(indent);
			sysOut += "\n";
		}

		for (int i=0; i<description.size();i++)
		{
			sysOut += ((BasicSegmentDescription)description.elementAt(i)).toString(indent);
			sysOut += "\n";
		}

		for (int i=0;i<indent;i++)
			sysOut += "\t";
		if (segmentRefList != null)
			sysOut += "SegmentRefList: " + segmentRefList;
		if (groupRefList != null)
			sysOut += "GroupRefList: " + groupRefList;

		return sysOut;
	}

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		SegmentGroupInformation clone = new SegmentGroupInformation();

		clone.setGroupID(new String(""+groupID));
		clone.setGroupRefList(new String(""+groupRefList));
		clone.setOrdered(ordered);
		clone.setProgramRef((ContentReference)programRef.clone());
		clone.setSegmentRefList(new String(""+segmentRefList));

		for (int i=0; i<groupTypes.size();i++)
			clone.addGroupType((SegmentGroupType)((SegmentGroupType)groupTypes.elementAt(i)).clone());

		for (int i=0; i<description.size();i++)
			clone.addDescription((BasicSegmentDescription)((BasicSegmentDescription)description.elementAt(i)).clone());

		return clone;
	}
}

