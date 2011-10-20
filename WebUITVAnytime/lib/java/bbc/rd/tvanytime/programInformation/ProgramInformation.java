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


package bbc.rd.tvanytime.programInformation;

import bbc.rd.tvanytime.ContentReference;
import bbc.rd.tvanytime.BasicDescription;
import bbc.rd.tvanytime.MemberOf;

import java.util.*;

/**
 * ProgramInformation: Represents the descriptive information of a program.
 *
 * @author Tristan Ferne, BBC Research & Development, April 2002
 * @version 1.0
 */

public class ProgramInformation implements Cloneable {

  /**
   * CRID for this Program.
   */
  private ContentReference programID;

  /**
   * BasicDescription for this program.
   */
  private BasicDescription basicDescription = null;

  /**
   * AVAttributes for this program.
   *
   * @association <bbc.rd.tvanytime.programInformation.JavaAssociation3> bbc.rd.tvanytime.programInformation.AVAttributes
   */
  private AVAttributes avAttributes = null;

  /**
   * List of MemberOf objects.
   */
  private Vector memberOfs = null;


  /**
   * Constructor for ProgramInformation.
   */
  public ProgramInformation() {
    memberOfs = new Vector(0,1);
  }

  /**
   * Constructor for ProgramInformation.
   *
   * @param  programID  CRID for this program.
   * @param  basicDescription  Description of this program.
   */
  public ProgramInformation(ContentReference programID, BasicDescription basicDescription) {
    this();
    setProgramID(programID);
    setBasicDescription(basicDescription);
  }

  /**
   * Constructor for ProgramInformation.
   *
   * @param  programID  CRID for this program.
   */
  public ProgramInformation(ContentReference programID) {
    this();
    setProgramID(programID);
  }

  /**
   * Returns CRID for this program.
   *
   * @return  CRID for this program.
   */
  public ContentReference getProgramID() {
    return programID;
  }

  /**
   * Set CRID for this program.
   *
   * @param  programID  CRID for this program.
   */
  public void setProgramID(ContentReference programID) {
    this.programID = programID;
  }

  /**
   * Get description of this program.
   *
   * @return  Description of this program.
   */
  public BasicDescription getBasicDescription() {
    return basicDescription;
  }

  /**
   * Set description of this program.
   *
   * @param  basicDescription  Description of this program.
   */
  public void setBasicDescription(BasicDescription basicDescription) {
    this.basicDescription = basicDescription;
  }

  /**
   * Get audio-visual attributes object for this program.
   *
   * @return  Audio-visual attributes of this program.
   */
  public AVAttributes getAVAttributes() {
    return avAttributes;
  }

  /**
   * Set audio-visual attributes object for this program.
   *
   * @param  avAttributes  Audio-visual attributes of this program.
   */
  public void setAVAttributes(AVAttributes avAttributes) {
    this.avAttributes = avAttributes;
  }

  /**
   * Get number of MemberOf objects contained in this program.
   *
   * @return  Number of MemberOf objects contained in this program.
   */
  public int getNumMemberOfs() {
    return memberOfs.size();
  }

  /**
   * Get MemberOf object for this program.
   *
   * @param  index The index of the MemberOf object to access.
   * @return  Specified MemberOf object.
   */
  public MemberOf getMemberOf(int index) {
    if ( (index>=0) && (index<memberOfs.size()) ) {
      return (MemberOf)memberOfs.elementAt(index);
    }
    else return null;
  }

  /**
   * Add MemberOf object to this program.
   *
   * @param  memberOf  The MemberOf object to add to this program.
   */
  public void addMemberOf(MemberOf memberOf) {
    memberOfs.addElement(memberOf);
  }

	/**
	 * removeMemberOf - Removes a MemberOf object from the Description object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeMemberOf(int index)
	{
		memberOfs.removeElementAt(index);
	}


  /**
   * Remove all MemberOf and Keyword objects.
   */
  public void removeAll() {
    memberOfs.removeAllElements();
  }

  /**
   * Returns string representation of this program.
   *
   * @return  XML representation of this program.
   */
  public String toXML() {
    return toXML(0);
  }

  /**
   * Returns string representation of this program.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  XML representation of the program.
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

            xmlBuf.append("<ProgramInformation");
            //xml += "<ProgramInformation";

            // programID
            xmlBuf.append(" programId=\"");
            xmlBuf.append(programID.toXML());
            xmlBuf.append("\">");
            //xml = xml + " programId=\""+programID.toXML() + "\">";

            // Indent and call children
            indent++;

            // basic description
            if (basicDescription != null) {
		xmlBuf.append("\n");
                xmlBuf.append(basicDescription.toXML(indent));
                //xml = xml + "\n" + basicDescription.toXML(indent);
            }

            // avattributes
            if (avAttributes != null) {
                xmlBuf.append("\n");
                xmlBuf.append(avAttributes.toXML(indent));
                //xml = xml + "\n" + avAttributes.toXML(indent);
            }

            // memberOfs
            for (int i=0; i<memberOfs.size();i++) {
                xmlBuf.append("\n");
                xmlBuf.append(((MemberOf)memberOfs.elementAt(i)).toXML(indent));
                //xml = xml + "\n" + ((MemberOf)memberOfs.elementAt(i)).toXML(indent);
            }

            xmlBuf.append("\n");
            //xml = xml + "\n";
            for (int i=0;i<indent-1;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }
            xmlBuf.append("</ProgramInformation>");
            //xml += "</ProgramInformation>";
            return xmlBuf.toString();
        }
    }

  /**
   * Returns string representation of this program.
   *
   * @return  string representation of this program.
   */
  public String toString() {
    return toString(0);
  }

  /**
   * Returns string representation of this program.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  string representation of the program.
   */
  public String toString(int indent) {
		String sysOut = "";

		// Do tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut += "ProgramInformation: \n";

	    // Indent and call children
		indent++;

	    // programID
	    for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
	    sysOut = sysOut + programID.toString() + "\n";

	    // basic description
	    if (basicDescription != null) {
	      sysOut = sysOut + "\n" + basicDescription.toString(indent);
	    }

	    // avattributes
	    if (avAttributes != null) {
	      sysOut = sysOut + "\n" + avAttributes.toString(indent);
	    }

	    // memberOfs
		for (int i=0; i<memberOfs.size();i++) {
			sysOut = sysOut + "\n" + ((MemberOf)memberOfs.elementAt(i)).toString(indent);
	    }

		return sysOut;
  }

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    ProgramInformation clone = new ProgramInformation();
    if (programID != null) clone.setProgramID((ContentReference)programID.clone());
    if (basicDescription != null) clone.setBasicDescription((BasicDescription)basicDescription.clone());
    if (avAttributes != null) clone.setAVAttributes((AVAttributes)avAttributes.clone());
		for (int i=0; i<memberOfs.size();i++) {
      clone.addMemberOf((MemberOf) (((MemberOf)memberOfs.elementAt(i)).clone()) );
    }
    return clone;
  }

}