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
import bbc.rd.tvanytime.search.*;
import java.util.*;

/**
 * GroupInformationTable: Represents a TV-Anytime Group Information Table
 *
 * @author Chris Akanbi, BBC Research & Development, April 2002
 * @version 1.0
 * 
 * Modified T.Ferne, September 2004: Fixed hashtables and remove...() methods. 
 */

public class GroupInformationTable implements MetadataSearch, Cloneable
{
	Vector groupInformations = null;

	// hashtable added (TJS, May 2003) so we can easily search on the objects
	private Hashtable groupInformationList;

	/**
	* Enumeration used to list contents of table.
	*/
	private Enumeration enumeration;

	/**
	 * Constructor for objects of class GroupInformationTable
	 */
	public GroupInformationTable()
	{
		groupInformations = new Vector(0,1);
		groupInformationList = new Hashtable();
	}

 /**
   * Unsupported method - always throws exception.
   *
   * @param   crid  the CRID of the ProgramLocation object to be searched for.
   * @return Vector  containing ProgramLocation objects. Empty if none found.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getProgramLocation(ContentReference crid) throws SearchInterfaceNotSupportedException {
    throw new SearchInterfaceNotSupportedException("");
  }

  /**
   * Unsupported method - always throws exception.
   *
   * @param   crid  the CRID of the ProgramInformation object to be searched for.
   * @return Vector  containing ProgramInformation objects. Empty if none found.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getProgramInformation(ContentReference crid) throws SearchInterfaceNotSupportedException {
    throw new SearchInterfaceNotSupportedException("");
  }

  /**
   * Unsupported method - always throws exception.
   *
   * @param   crid  the CRID of the SegmentInformation object to be searched for.
   * @return Vector  containing SegmentInformation objects. Empty if none found.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getSegmentInformation(ContentReference crid) throws SearchInterfaceNotSupportedException {
    throw new SearchInterfaceNotSupportedException("");
  }

  /**
   * Get group information for the specified CRID.
   *
   * @param   crid  the CRID of the GroupInformation object to be searched for.
   * @return Vector  containing GroupInformation objects. Empty if none found.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getGroupInformation(ContentReference crid) throws SearchInterfaceNotSupportedException {
    Vector temp = new Vector();
    //Object object = groupInformationList.get((crid.getCRID()).toLowerCase());
    Object object = groupInformationList.get(crid);
    if (object != null) temp.addElement(object);
    return temp;
  }


	/**
	 * getNumInformation- gets the number of GroupInformation objects belonging to this GroupInformationTable
	 *
	 * @return     the number of GroupInformation objects in this GroupInformationTable
	 */
	public int getNumGroupInformations()
	{
		return groupInformations.size();
	}

	/**
	 * getGroupInformation - gets the GroupInformation object for this GroupInformationTable
	 *
	 * @param	index	the index to the GroupInformation object
	 * @return     the GroupInformation object
	 */
	public GroupInformation getGroupInformation(int index)
	{
		return (GroupInformation)groupInformations.elementAt(index);
	}

	/**
	 * addGroupInformation - adds a GroupInformation object to this GroupInformationTable
	 *
	 * @param	groupInformation	the GroupInformation object
	 */
	public void addGroupInformation(GroupInformation groupInformation)
	{
		groupInformations.addElement(groupInformation);
	  //groupInformationList.put((groupInformation.getGroupId().getCRID()).toLowerCase(), groupInformation);
		groupInformationList.put(groupInformation.getGroupId(), groupInformation);
	}

	/**
	 * Removes a GroupInformation object from this GroupInformationTable
	 *
	 * @param	 index	 The index to the GroupInformation object
	 */
	public void removeGroupInformation(int index)
	{
    // First remove from hashtable
    groupInformationList.remove(groupInformations.elementAt(index));
    // Then from list
 		groupInformations.removeElementAt(index);
	}

	/**
	 * Removes a GroupInformation object from this GroupInformationTable
	 *
	 * @param	 groupInformation	 The GroupInformation object to remove
	 */
	public void removeGroupInformation(GroupInformation groupInformation)
	{
		/*
    // First remove from hashtable
    groupInformationList.remove(groupInformation.getGroupId());
    // Then from list
    for (int ct=0; ct<groupInformations.size(); ct++) {
      if (((GroupInformation)groupInformations.elementAt(ct)).getGroupId().equals(groupInformation.getGroupId())) {
        groupInformations.removeElementAt(ct);
        break;
      }
    }
    */
		removeGroupInformation(groupInformation.getGroupId());
	}

	/**
	 * Removes a GroupInformation object from this GroupInformationTable
	 *
	 * @param	 crid	 The CRID of the GroupInformation object to remove
	 */
	public void removeGroupInformation(ContentReference crid)
	{
    // First remove from hashtable
    groupInformationList.remove(crid);
    // Then from list
    for (int ct=0; ct<groupInformations.size(); ct++) {
      if (((GroupInformation)groupInformations.elementAt(ct)).getGroupId().equals(crid)) {
        groupInformations.removeElementAt(ct);
        break;
      }
    }
	}


	/**
	 * toString - returns a String representation of this GroupInformationTable
	 *
	 * @return 		String representation of this GroupInformationTable
	 */
	public String toString()
	{
		return toString(0);
	}

	/**
	 * toString - returns a String representation of this GroupInformationTable with the specified number of tab indentations
	 *
	 * @return 		String representation of this GroupInformationTable
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		//add required number of tabs
		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "GroupInformationTable:";

		//increment tab level for child toString methods
		indent++;

		//add any groupInformation strings
		for( int i=0;i<groupInformations.size();i++)
			sysOut += "\n" + ((GroupInformation)groupInformations.elementAt(i)).toString(indent);

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
	public String	toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";

            synchronized(xmlBuf) {
                // Do tabs
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("<GroupInformationTable>");
                //xml += "<GroupInformationTable>";

                // Indent and call children
                indent++;

                //add any groupInformation strings
                for( int i=0;i<groupInformations.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((GroupInformation)groupInformations.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((GroupInformation)groupInformations.elementAt(i)).toXML(indent);
                }    
                   
                xmlBuf.append("\n");
                //xml += "\n";
                for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("</GroupInformationTable>");
                //xml += "</GroupInformationTable>";
                return xmlBuf.toString();
            }
	}

	/**
	 * removeAll - removes all GroupInformation objects
	 */
	public void removeAll()
	{
		groupInformations.removeAllElements();
    groupInformationList.clear();
	}

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		GroupInformationTable clone = new GroupInformationTable();

		for (int i=0; i<groupInformations.size();i++)
			clone.addGroupInformation((GroupInformation)((GroupInformation)groupInformations.elementAt(i)).clone());

		return clone;
	}
}