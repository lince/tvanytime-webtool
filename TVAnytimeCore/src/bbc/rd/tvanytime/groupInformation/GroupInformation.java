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


package bbc.rd.tvanytime.groupInformation;

import bbc.rd.tvanytime.*;
import java.util.Vector;

/**
 * GroupInformation: Represents a TV-Anytime GroupInformation object
 *
 * @author Chris Akanbi, BBC Research & Development, April 2002
 * @version 1.0
 */

public class GroupInformation
{

  /**
   * Valid group types.
   */
	public static final int SERIES = 0;
	public static final int SHOW = 1;
	public static final int PROGRAM_CONCEPT = 2;
	public static final int PROGRAM_COMPILATION = 3;
	public static final int OTHER_COLLECTION = 4;
	public static final int MAGAZINE = 5;
	public static final int OTHER_CHOICE = 6;

  /**
   * Strings for group types.
   */
  public static final String[] groupTypeStrings = {"series", "show", "programConcept",
    "programCompilation", "otherCollection", "magazine", "otherChoice"};

	private int groupType = -1;
	private ContentReference groupId = null;
	private Boolean ordered = null;
	private BasicDescription basicDescription = null;
	private Vector memberOfs = null;



	/**
	 * Constructor for objects of class GroupInformation
	 */
	public GroupInformation()
	{
		memberOfs = new Vector(0,1);
	}

	/**
	 * Constructor for objects of class GroupInformation
	 *
	 * @param	groupId		groupId for this object
	 * @param 	basicDescription	the BasicDescription object for this GroupInformation object
	 * @param 	groupType 	the group type GroupInformation object
	 */
	public GroupInformation(ContentReference groupId, BasicDescription basicDescription, int groupType) throws TVAnytimeException
	{
		this();
		setGroupId(groupId);
		setGroupType(groupType);
		setBasicDescription(basicDescription);
	}

	/**
	 * getBasicDescription - gets the BasicDesciption object for this GroupInformation object
	 *
	 * @return     the BasicDesciption object
	 */
	public BasicDescription getBasicDescription()
	{
		return basicDescription;
	}

	/**
	 * getGroupId - gets the groupId (a ContentReference object) for this GroupInformation object
	 *
	 *
	 * @return     the groupId
	 */
	public ContentReference getGroupId()
	{
		return groupId;
	}

	/**
	 * getGroupType - gets the group type for this GroupInformation object
	 * @return     the group type
	 */
	public int getGroupType()
	{
		return groupType;
	}


	/**
	 * isOrdered - gets the ordered variable(a Boolean object) for this GroupInformation object
	 *
	 * @return      the ordered variable
	 */
	public Boolean isOrdered()
	{
		return ordered;
	}

	/**
	 * setBasicDescription - sets the BasicDescription object for this GroupInformation object
	 *
	 * @param	basicDescription	the BasicDescription object
	 */
	public void setBasicDescription(BasicDescription basicDescription)
	{
		this.basicDescription = basicDescription;
	}

	/**
	 * setGroupId - sets the groupId for this GroupInformation object
	 *
	 * @param	groupId		the groupId(a ContentReference object)
	 */
	public void setGroupId(ContentReference groupId)
	{
		this.groupId = groupId;
	}

	/**
	 * setGroupType - sets the group type for this GroupInformation object
	 *
	 * @param	groupType		the group type
	 */
	public void setGroupType(int groupType) throws TVAnytimeException
	{
    if (groupType>=0 && groupType<=6) {
      this.groupType = groupType;
    }
    else throw new TVAnytimeException("GroupInformation: "+groupType+" is not a valid group type");
	}


	/**
	 * setOrdered - sets the ordered variable to true, false or undefined for this GroupInformation object
	 *
	 * @param	ordered		the ordered variable(a Boolean object)
	 */
	public void setOrdered(Boolean ordered)
	{
		this.ordered = ordered;
	}

	/**
	 * getNumMemberOfs - gets the number of MemberOf objects belonging to this GroupInformation object
	 *
	 * @return 		the number of MemberOf objects in this GroupInformation object
	 */
	public int getNumMemberOfs()
	{
		return memberOfs.size();
	}

	/**
	 * getMemberOf - gets the MemberOf object at the specified index
	 *
	 * @param	index	the index of the required MemberOf object
	 * @return 		the MemberOf object at the specified index
	 */
	public MemberOf getMemberOf(int index)
	{
		return (MemberOf)memberOfs.elementAt(index);
	}

	/**
	 * addMemberOf - adds a MemberOf object to the GroupInformation object
	 *
	 * @param	memberOf	the MemberOf object
	 */
	public void addMemberOf(MemberOf memberOf)
	{
		memberOfs.addElement(memberOf);
	}

	/**
	 * removeMemberOf - Removes a MemberOf object from the GroupInformation object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeMemberOf(int index)
	{
		memberOfs.removeElementAt(index);
	}


	/**
	 * removeAll - removes all MemberOf objects
	 */
	public void removeAll()
	{
		memberOfs.removeAllElements();
	}

	/**
	 * toString - returns a String representation of this GroupInformation object
	 *
	 * @return		the String representation of this GroupInformation object
	 */
	public String toString()
	{
		return toString(0);
	}

	/**
	 * toString - returns a String representation of this GroupInformation object with the specified number of tab indentations
	 *
	 * @return 		the String representation of the GroupInformation object
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		//add required number of tabs
		for (int i=0; i<indent;i++)
			sysOut += "\t";

		sysOut += "GroupInformation:\n";

		//increment tab level for child toString methods
		indent++;

		//if basicDescription has been set add string
		if (null != basicDescription)
			sysOut +=  basicDescription.toString(indent) + "\n";

		//add required number of tabs and output groupId
		for (int i=0; i<indent;i++)
			sysOut += "\t";
		sysOut += "groupId: " + groupId.toString() + "\n";

		//add required number of tabs and add the type variable
		for (int i=0; i<indent;i++)
			sysOut += "\t";
		if (groupType > -1) sysOut += "group type: " + groupTypeStrings[groupType];
    else sysOut += "group type: <not set>";


		//add required number of tabs and add the ordered variable
		for (int i=0; i<indent;i++)
			sysOut += "\t";
		sysOut += "ordered: " + ordered ;

		//add any MemberOf strings
		for (int i=0;i<memberOfs.size();i++)
		{
			sysOut += "\n";
			for (int j=0; j<indent;j++)
				sysOut += "\t";
			sysOut += "memberOf: " + ((MemberOf)memberOfs.elementAt(i)).toString();
		}

		return sysOut;
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
	public String toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";

            // Do tabs
            synchronized(xmlBuf) {
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("<GroupInformation groupId=\"");
                xmlBuf.append(groupId.getCRID());
                xmlBuf.append("\"");
                //xml += "<GroupInformation groupId=\""+groupId.getCRID()+"\"";
                if (ordered!=null) {
                    xmlBuf.append(" ordered=\"");
                    //xml +=" ordered=\"";
                    if (ordered.booleanValue()) 
                        xmlBuf.append("true");
                        //xml += "true";
                    else 
                        xmlBuf.append("false");
                        //xml += "false";
                    xmlBuf.append("\"");
                    //xml += "\"";
                }
                xmlBuf.append(">");
                //xml += ">";

                // Indent and call children
                indent++;

                xmlBuf.append("\n");
                //xml += "\n";
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                if (groupType > -1) {
                    xmlBuf.append("<GroupType xsi:type=\"ProgramGroupTypeType\" value=\"");
                    xmlBuf.append(groupTypeStrings[groupType]);
                    xmlBuf.append("\"/>");
                    //xml += "<GroupType xsi:type=\"ProgramGroupTypeType\" value=\""+groupTypeStrings[groupType]+"\"/>";
                } else {
                    xmlBuf.append("<GroupType xsi:type=\"ProgramGroupTypeType\" value=\"invalid\"/>");
                    //xml += "<GroupType xsi:type=\"ProgramGroupTypeType\" value=\"invalid\"/>";
                }
                if (basicDescription != null) {
                    xmlBuf.append("\n");
                    xmlBuf.append(basicDescription.toXML(indent));
                    //xml += "\n" + basicDescription.toXML(indent);
                }

                for(int i=0;i<memberOfs.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((MemberOf)memberOfs.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((MemberOf)memberOfs.elementAt(i)).toXML(indent);
                }
                xmlBuf.append("\n");
                //xml += "\n";
                for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("</GroupInformation>");
                //xml += "</GroupInformation>";
                return xmlBuf.toString();
            }
        }

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		GroupInformation clone = new GroupInformation();

		for (int i=0; i<memberOfs.size();i++)
			clone.addMemberOf((MemberOf)((MemberOf)memberOfs.elementAt(i)).clone());

		clone.setBasicDescription((BasicDescription)basicDescription.clone());
		clone.setGroupId((ContentReference)groupId.clone());
		if (ordered != null) clone.setOrdered(new Boolean(ordered.booleanValue()));
    try {
      clone.setGroupType(getGroupType());
    }
    catch (TVAnytimeException tvae) { System.out.println(tvae.getMessage()); }

		return clone;
	}
}