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

import bbc.rd.tvanytime.TVAnytimeException;

/**
 * SegmentGroupType: Represents a segment group type object
 *
 * @author Tim Sargeant, BBC Research & Development, May 2002
 * @version 1.0
 */

public class SegmentGroupType implements Cloneable
{
	public static final int HIGHLIGHTS = 0;
	public static final int HIGHLIGHTS_OBJECTS = 1;
	public static final int HIGHLIGHTS_EVENTS = 2;
	public static final int BOOKMARKS = 3;
	public static final int BOOKMARKS_OBJECTS = 4;
	public static final int BOOKMARKS_EVENTS = 5;
	public static final int THEME_GROUP = 6;
	public static final int PREVIEW = 7;
	public static final int PREVIEW_TITLE = 8;
	public static final int PREVIEW_SLIDESHOW = 9;
	public static final int TABLE_OF_CONTENTS = 10;
	public static final int SYNOPSIS = 11;
	public static final int SHOTS = 12;
	public static final int INSERTION_POINTS = 13;
	public static final int ALTERNATIVE_GROUPS = 14;
	public static final int OTHER = 15;

	private int groupType;

	/**
	 * Constructor for objects of type SegmentGroupType
	 */
	public SegmentGroupType()
	{

	}

	/**
	 * Constructor for objects of type SegmentGroupType with required fields
	 *
	 * @param groupType the group type
	 */
	public SegmentGroupType(int groupType) throws TVAnytimeException
	{
		setType(groupType);
	}

	/**
	 * Get the type of the title (BOOKMARKS, PREVIEW, THEME_GROUP, etc.)
	 *
	 * @return the group type
	 */
	public int getType()
	{
		return groupType;
	}

	/**
	 * Sets the group type
	 *
	 * @param groupType the group type
	 * @throws TVAnytimeException thrown when the group type is not valid
	 */
	public void setType(int groupType) throws TVAnytimeException
	{
		if ((groupType >= 0) && (groupType <= 15))
		{
			this.groupType = groupType;
		}

		else throw new TVAnytimeException("SegmentGroupType: Not a valid group type.");
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
		xmlBuf.append("<GroupType xsi:type=\"SegmentGroupTypeType\" value=\"");
                //xml += "<GroupType xsi:type=\"SegmentGroupTypeType\" value=\"";

		String groupTypeAsString = "";

		switch(groupType)
		{
			case ALTERNATIVE_GROUPS:
				groupTypeAsString = "alternativeGroups";
				break;
			case BOOKMARKS:
				groupTypeAsString = "bookmarks";
				break;
			case BOOKMARKS_EVENTS:
				groupTypeAsString = "bookmarks/events";
				break;
			case BOOKMARKS_OBJECTS:
				groupTypeAsString = "bookmarks/objects";
				break;
			case HIGHLIGHTS:
				groupTypeAsString = "highlights";
				break;
			case HIGHLIGHTS_EVENTS:
				groupTypeAsString = "highlights/events";
				break;
			case HIGHLIGHTS_OBJECTS:
				groupTypeAsString = "highlights/objects";
				break;
			case INSERTION_POINTS:
				groupTypeAsString = "insertionPoints";
				break;
			case OTHER:
				groupTypeAsString = "other";
				break;
			case PREVIEW:
				groupTypeAsString = "preview";
				break;
			case PREVIEW_SLIDESHOW:
				groupTypeAsString = "preview/slideshow";
				break;
			case PREVIEW_TITLE:
				groupTypeAsString = "preview/title";
				break;
			case SHOTS:
				groupTypeAsString = "shots";
				break;
			case SYNOPSIS:
				groupTypeAsString = "synopsis";
				break;
			case TABLE_OF_CONTENTS:
				groupTypeAsString = "tableOfContents";
				break;
			case THEME_GROUP:
				groupTypeAsString = "themeGroup";
				break;
		}

		xmlBuf.append(groupTypeAsString);
                xmlBuf.append("\"/>");
                //xml += groupTypeAsString+"\"/>";
		return xmlBuf.toString();
            }
        }

	/**
	 * Return a string representation of this object.
	 *
	 * @return  string representation of this object.
	 */
	public String toString()
	{
		return toString(0);
	}

	/**
	 * Return a string representation of this object.
	 *
	 * @param indent number of tabs to put before the string.
	 * @return  string representation of this object.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		String groupTypeAsString = "";

		switch(groupType)
		{
			case ALTERNATIVE_GROUPS:
				groupTypeAsString = "alternativeGroups";
				break;
			case BOOKMARKS:
				groupTypeAsString = "bookmarks";
				break;
			case BOOKMARKS_EVENTS:
				groupTypeAsString = "bookmarksEvents";
				break;
			case BOOKMARKS_OBJECTS:
				groupTypeAsString = "bookmarksObjects";
				break;
			case HIGHLIGHTS:
				groupTypeAsString = "highlights";
				break;
			case HIGHLIGHTS_EVENTS:
				groupTypeAsString = "highlightsEvents";
				break;
			case HIGHLIGHTS_OBJECTS:
				groupTypeAsString = "highlightsObjects";
				break;
			case INSERTION_POINTS:
				groupTypeAsString = "insertionPoints";
				break;
			case OTHER:
				groupTypeAsString = "other";
				break;
			case PREVIEW:
				groupTypeAsString = "preview";
				break;
			case PREVIEW_SLIDESHOW:
				groupTypeAsString = "previewSlideshow";
				break;
			case PREVIEW_TITLE:
				groupTypeAsString = "previewTitle";
				break;
			case SHOTS:
				groupTypeAsString = "shots";
				break;
			case SYNOPSIS:
				groupTypeAsString = "synopsis";
				break;
			case TABLE_OF_CONTENTS:
				groupTypeAsString = "tableOfContents";
				break;
			case THEME_GROUP:
				groupTypeAsString = "themeGroup";
				break;
		}

		sysOut += "GroupType = ";
		sysOut += groupTypeAsString;

		return sysOut;
	}

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		SegmentGroupType clone = new SegmentGroupType();

		try {
			clone.setType(groupType);
		}
		catch (TVAnytimeException tvae) {
			/* Need to trap this exception, but it will never be thrown, since
			 * we're just copying a groupType that's already been set & therefore
			 * is within bounds. */
		}

		return clone;
	}
}
