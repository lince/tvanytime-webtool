/**
 * Copyright 2003 British Broadcasting Corporation
 *
 * This	file is	part of	the	BBC	R&D	TV-Anytime Java	API.
 *
 * The BBC R&D TV-Anytime Java API is free software; you can redistribute it
 * and/or modify it	under the terms	of the GNU Lesser General Public License as
 * published by	the	Free Software Foundation; either version 2 of the
 * License,	or (at your	option)	any	later version.
 *
 * The BBC R&D TV-Anytime Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A	PARTICULAR PURPOSE.	 See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received	a copy of the GNU Lesser General Public	License
 * along with the BBC R&D TV-Anytime Java API; if not, write to	the	Free
 * Software	Foundation,	Inc., 59 Temple	Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */


package	bbc.rd.tvanytime.programInformation;

import java.util.*;
import bbc.rd.tvanytime.*;
import bbc.rd.tvanytime.search.*;

/**
 *
 * ProgramInformationTable:	Represents a table that	contains ProgramInformation
 * objects.
 *
 * Notes:
 * 1) This implementation uses a hashtable and will	only store one object per
 * CRID. Adding	updated/new	objects	with the same CRID as a	previously existing
 * object will overwrite the previous objects.
 * 2) Because the CRID is used as a	key	to the hashtable a ProgramInformation
 * object must have	a CRID before it can be	added.
 *
 * @author Tristan Ferne, BBC Research & Development, April	2002
 * @version	1.0
 * 
 * Modified 26/4/04 T.Ferne: Added removeProgramInformation() method.
 * Modified 23/9/04 T.Ferne: Returned to using ContentReference as key to hashtable
 * and fixed remove...() methods. 
 */

public class ProgramInformationTable implements	MetadataSearch,	Cloneable
{
  /**
   * List of ProgramInformation	objects.
   */
  private Hashtable	programInformationList;

  /**
   * Enumeration used to list contents of table.
   */
  private Enumeration enumeration;

  /**
   * Constructor for objects of	type ProgramInformationTable
   */
  public ProgramInformationTable() {
	programInformationList = new Hashtable();
  }


  /**
   * Remove	all	ProgramInformation objects.
   */
  public void removeAll() {
    programInformationList.clear();
  }

	/**
	 * Removes a ProgramInformation object from this ProgramInformationTable
	 *
	 * @param	 programInformation	 The ProgramInformation object to remove
	 */
	public void removeProgramInformation(ProgramInformation programInformation)
	{
		removeProgramInformation(programInformation.getProgramID());
	}

	/**
	 * Removes a ProgramInformation object from this ProgramInformationTable
	 *
	 * @param	 crid	 The ContentReference for this ProgramInformation.
	 */
	public void removeProgramInformation(ContentReference crid)
	{
    programInformationList.remove(crid);
	}

  /**
   *
   * Add a ProgramInformation object. Note that	the	ProgramInformation object
   * must have a CRID and it will overwrite	any	existing objects with the same
   * CRID.
   *
   * @param	 The ProgramInformation	object to add to this table.
   */
  public void addProgramInformation(ProgramInformation programInformation) {
  	// Use CRID	as key
  	//programInformationList.put((programInformation.getProgramID().getCRID()).toLowerCase(),programInformation);
  	programInformationList.put(programInformation.getProgramID(),programInformation);
  }

  /**
   *
   * Get a ProgramInformation object.
   *
   * @param	 index The index of	the	ProgramInformation to access.
   * @return  Specified	ProgramInformation object.
   */
  public ProgramInformation	getProgramInformation(int index) {
	if ( (index>=0)	&& (index<programInformationList.size()) ) {
	  enumeration =	programInformationList.elements();
	  for (int ct=0; ct<index; ct++) {
      enumeration.nextElement();
	  }
	  return (ProgramInformation)(enumeration.nextElement());
	}
	else return	null;
  }

 /**
   * Unsupported method	- always throws	exception.
   *
   * @param	  crid	the	CRID of	the	ProgramLocation	object to be searched for.
   * @return Vector	 containing	ProgramLocation	objects. Empty if none found.
   * @throws  SearchInterfaceNotSupportedException	 If	the	implementation doesn't support this	function.
   */
  public Vector	getProgramLocation(ContentReference	crid) throws SearchInterfaceNotSupportedException {
  	throw new SearchInterfaceNotSupportedException("");
  }

  /**
   * Get program information for the specified CRID.
   *
   * @param	  crid	the	CRID of	the	ProgramInformation object to be	searched for.
   * @return Vector	 containing	ProgramInformation objects.	Empty if none found.
   * @throws  SearchInterfaceNotSupportedException	 If	the	implementation doesn't support this	function.
   */
  public Vector	getProgramInformation(ContentReference crid) throws	SearchInterfaceNotSupportedException {
  	Vector temp	= new Vector();
  	//Object object =	programInformationList.get((crid.getCRID()).toLowerCase());
  	Object object =	programInformationList.get(crid);
  	if (object != null)	temp.addElement(object);
  	return temp;
  }

  /**
   * Unsupported method	- always throws	exception.
   *
   * @param	  crid	the	CRID of	the	SegmentInformation object to be	searched for.
   * @return Vector	 containing	SegmentInformation objects.	Empty if none found.
   * @throws  SearchInterfaceNotSupportedException	 If	the	implementation doesn't support this	function.
   */
  public Vector	getSegmentInformation(ContentReference crid) throws	SearchInterfaceNotSupportedException {
	throw new SearchInterfaceNotSupportedException("");
  }

  /**
   * Unsupported method	- always throws	exception.
   *
   * @param	  crid	the	CRID of	the	GroupInformation object	to be searched for.
   * @return Vector	 containing	GroupInformation objects. Empty	if none	found.
   * @throws  SearchInterfaceNotSupportedException	 If	the	implementation doesn't support this	function.
   */
  public Vector	getGroupInformation(ContentReference crid) throws SearchInterfaceNotSupportedException {
	throw new SearchInterfaceNotSupportedException("");
  }


  /**
   *
   * Get the number	of ProgramInformation objects.
   *
   * @return  The number of	ProgramInformation objects contained in	this table.
   */
  public int getNumProgramInformations() {
	return programInformationList.size();
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
    public String toXML(int indent) {
        StringBuffer xmlBuf = new StringBuffer();
        //String xml = "";
        
        synchronized(xmlBuf) {
            // Do tabs
            for (int i=0;i<indent;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }
            xmlBuf.append("<ProgramInformationTable>");
            //xml += "<ProgramInformationTable>";

            // Indent and call children
            indent++;

            enumeration = programInformationList.elements();
            while (enumeration.hasMoreElements()) {
                xmlBuf.append("\n");
                xmlBuf.append(((ProgramInformation)enumeration.nextElement()).toXML(indent));
                //xml = xml + "\n" + ((ProgramInformation)enumeration.nextElement()).toXML(indent);
            }
            xmlBuf.append("\n");
            //xml += "\n";
            for (int i=0;i<indent-1;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }
            xmlBuf.append("</ProgramInformationTable>");
            //xml += "</ProgramInformationTable>";
            return xmlBuf.toString();
        }
    }


  /**
   * Returns string	representation of this table.
   *
   * @return  String representation	of this	table.
   */
  public String	toString() {
	return toString(0);
  }

  /**
   * Returns string	representation of this table.
   *
   * @param	  indent  Number of	tabs with which	to indent the string.
   * @return  string representation	of the table.
   */
  public String	toString(int indent) {
		String sysOut = "";

		// Do tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
		}
		sysOut += "ProgramInformationTable:\n";

		// Indent and call children
		indent++;
		enumeration	= programInformationList.elements();
		while (enumeration.hasMoreElements()) {
			sysOut = sysOut + "\n" + ((ProgramInformation)enumeration.nextElement()).toString(indent);
		}

		return sysOut;
  }

  /**
   * Clones	itself.
   * @return  A	copy of	itself.
   */
  public Object	clone()	{
	ProgramInformationTable	clone =	new	ProgramInformationTable();
	// Add all ProgramInformation objects
	enumeration	= programInformationList.elements();
	while (enumeration.hasMoreElements()) {
		clone.addProgramInformation((ProgramInformation) (((ProgramInformation)enumeration.nextElement()).clone()) );
	}
	return clone;
  }

}