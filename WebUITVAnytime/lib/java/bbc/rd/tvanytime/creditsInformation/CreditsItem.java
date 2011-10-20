/**
 * Copyright 2003 BBC Research & Development
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


package bbc.rd.tvanytime.creditsInformation;

import java.util.*;

import bbc.rd.tvanytime.*;

/**
 * Represents a TV-Anytime CreditsItem object.
 *
 * @author Tristan Ferne, BBC Research & Development, April 2003
 *
 * @version 1.0
 */
public class CreditsItem  implements Cloneable  {

  /**
   * List of PersonNames.
   */
  private Vector personNames;
  /**
   * List of Characters.
   */
  private Vector characters;
  /**
   * List of OrganizationNames.
   */
  private Vector organizationNames;
  /**
   * Role of this CreditsItem.
   */
  private String role = "";

  /**
   * Valid starts for role CS string.
   */
  private static final String hrefTags[] = {
    "urn:tva:metadata:cs:TVARoleCS:2002:",
    "urn:mpeg:mpeg7:cs:MPEG7RoleCS:",
		"urn:mpeg:mpeg7:cs:RoleCS:2001",
		"urn:tva:metadata:cs:TVARoleCS:2004"};

  /**
   * Some useful values for role.
   */
  public static String[] roleStrings = {"Key talent", "Key character", "Writer", "Director", "Provider", "Actor"};
  public static String[] roles = {"urn:tva:metadata:cs:TVARoleCS:2002:V106", "urn:tva:metadata:cs:TVARoleCS:2002:V709", "urn:mpeg:mpeg7:cs:MPEG7RoleCS:WRITER", "urn:mpeg:mpeg7:cs:MPEG7RoleCS:DIRECTOR", "urn:mpeg:mpeg7:cs:MPEG7RoleCS:PROVIDER", "urn:mpeg:mpeg7:cs:RoleCS:2001:ACTOR"};


  /**
   * Constructor.
   */
  public CreditsItem() {
    personNames = new Vector();
    characters = new Vector();
    organizationNames = new Vector();
  }

 	/**
 	 * Sets the role for this CreditsItem. Note: must have prefix of
 	 * "urn:tva:metadata:cs:TVARoleCS:2002:" or "urn:mpeg:mpeg7:cs:MPEG7RoleCS:"
   * to be valid.
 	 *
 	 * @param 	role		the role required for this CreditsItem
 	 * @throws	TVAnytimeException if the role string has the wrong format
 	 */
 	public void setRole(String role) throws TVAnytimeException {
 		boolean foundRole = false;
 		for (int i=0; i<hrefTags.length;i++) {
 			if (role.indexOf(hrefTags[i]) >= 0)	{
        this.role = role;
 				foundRole = true;
 			}
 		}
 		if (!foundRole) throw new TVAnytimeException("Invalid role: " + role);
 	}

  /**
   * Get role for this CreditsItem.
   *
   * @return  Role string.
   */
  public String getRole() {
    return role;
  }

  /**
   * Return number of PersonNames in list.
   *
   * @return  Number of PersonNames in list.
   */
  public int getNumPersonNames() {
    return personNames.size();
  }

	/**
	 * Gets the PersonName object at the specified index
	 *
	 * @param	 index	the index of the required PersonName object
	 * @return 		the PersonName object at the specified index
	 */
  public PersonName getPersonName(int index) {
    return (PersonName)personNames.elementAt(index);
  }

	/**
	 * Adds a PersonName object to add to the listt
	 *
	 * @param	 personName	the PersonName object
	 */
	public void addPersonName(PersonName personName)	{
		personNames.addElement(personName);
	}

	/**
	 * Removes a PersonName object from the list
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removePersonName(int index) {
		personNames.removeElementAt(index);
	}


  /**
   * Return number of characters in list.
   *
   * @return  Number of characters in list.
   */
  public int getNumCharacters() {
    return characters.size();
  }

	/**
	 * Gets the character at the specified index
	 *
	 * @param	 index	the index of the required characters
	 * @return 		the characters at the specified index
	 */
  public Character getCharacter(int index) {
    return (Character)characters.elementAt(index);
  }

	/**
	 * Adds a character object to add to the listt
	 *
	 * @param	 character	the character
	 */
	public void addCharacter(Character character)	{
		characters.addElement(character);
	}

	/**
	 * Removes a character from the list
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeCharacter(int index) {
		characters.removeElementAt(index);
	}


  /**
   * Return number of OrganizationNames in list.
   *
   * @return  Number of OrganizationNames in list.
   */
  public int getNumOrganizationNames() {
    return organizationNames.size();
  }

	/**
	 * Gets the OrganizationName at the specified index
	 *
	 * @param	 index	the index of the required OrganizationName
	 * @return 		the OrganizationName at the specified index
	 */
  public OrganizationName getOrganizationName(int index) {
    return (OrganizationName)organizationNames.elementAt(index);
  }

	/**
	 * Adds a OrganizationName object to add to the listt
	 *
	 * @param	 organizationName	 the OrganizationName
	 */
	public void addOrganizationName(OrganizationName organizationName)	{
		organizationNames.addElement(organizationName);
	}

	/**
	 * Removes an OrganizationName from the list
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeOrganizationName(int index) {
		organizationNames.removeElementAt(index);
	}


	/**
	 * removeAll - removes all CreditsItems.
	 */
	public void removeAll()	{
		personNames.removeAllElements();
		characters.removeAllElements();
		organizationNames.removeAllElements();
	}




	/**
	 * Returns a XML representation of this object with the specified number of tab indentations
	 *
	 * @return 		the XML representation of the object
	 */
	public String toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";

            synchronized(xmlBuf) {
                //add required number of tabs
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("<CreditsItem role=\"");
                xmlBuf.append(role);
                xmlBuf.append("\">\n");
                //xml += "<CreditsItem role=\""+role+"\">\n";

                for (int ct=0; ct<getNumPersonNames(); ct++) {
                    xmlBuf.append(getPersonName(ct).toXML(indent+1));
                    xmlBuf.append("\n");
                    //xml = xml + getPersonName(ct).toXML(indent+1) + "\n";
                }
                for (int ct=0; ct<getNumOrganizationNames(); ct++) {
                    xmlBuf.append(getOrganizationName(ct).toXML(indent+1));
                    xmlBuf.append("\n");
                    //xml = xml + getOrganizationName(ct).toXML(indent+1) + "\n";
                }
                for (int ct=0; ct<getNumCharacters(); ct++) {
                    xmlBuf.append(getCharacter(ct).toXML(indent+1));
                    xmlBuf.append("\n");
                    //xml = xml + getCharacter(ct).toXML(indent+1) + "\n";
                }

                //add required number of tabs
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("</CreditsItem>");
                //xml += "</CreditsItem>";
                return xmlBuf.toString();
            }
        }

	/**
	 * Returns a XML representation of this object with the specified number of tab indentations
	 *
	 * @return 		the XML representation of the object
	 */
	public String toXML() {
    return toXML(0);
  }

	/**
	 * toString - returns a String representation of this object with the specified number of tab indentations
	 *
	 * @return 		the String representation of the object
	 */
	public String toString(int indent) {
		String sysOut = "";

		//add required number of tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
    }

		sysOut += "CreditsItem: role = "+role;

		//increment tab level for child toString methods
		indent++;

    for (int ct=0; ct<getNumPersonNames(); ct++) {
      sysOut = sysOut + "\n" + getPersonName(ct).toString(indent);
    }
    for (int ct=0; ct<getNumOrganizationNames(); ct++) {
      sysOut = sysOut + "\n" + getOrganizationName(ct).toString(indent);
    }
    for (int ct=0; ct<getNumCharacters(); ct++) {
      sysOut = sysOut + "\n" + getCharacter(ct).toString(indent);
    }

    return sysOut;
  }

	/**
	 * toString - returns a String representation of this object with the specified number of tab indentations
	 *
	 * @return 		the String representation of the object
	 */
	public String toString() {
    return toString(0);
  }

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    CreditsItem clone = new CreditsItem();
    try {
      if (role != null) clone.setRole(new String(role));
    } catch (TVAnytimeException tvae) { System.out.println("Error cloning CreditsItem: "+tvae.getMessage()); }

		for(int i=0;i<personNames.size();i++) {
      clone.addPersonName((PersonName)((PersonName)personNames.elementAt(i)).clone());
    }
		for(int i=0;i<characters.size();i++) {
      clone.addCharacter((Character)((Character)characters.elementAt(i)).clone());
    }
		for(int i=0;i<organizationNames.size();i++) {
      clone.addOrganizationName((OrganizationName)((OrganizationName)organizationNames.elementAt(i)).clone());
    }
    return clone;
  }


  /**
   * Tests for equality between CreditsItems.
   *
   * @return  True if equal.
   * @param  creditsItem  Item to test.
   */
  public boolean equals(CreditsItem creditsItem) {
    if (!role.equals(creditsItem.getRole())) return false;
    if (personNames.size() != creditsItem.getNumPersonNames()) return false;
    else {
      for(int i=0;i<personNames.size();i++) {
        if (!getPersonName(i).getName().equals(creditsItem.getPersonName(i).getName())) return false;
      }
    }
    if (characters.size() != creditsItem.getNumCharacters()) return false;
    else {
      for(int i=0;i<characters.size();i++) {
        if (!getCharacter(i).getName().equals(creditsItem.getCharacter(i).getName())) return false;
      }
    }
    if (organizationNames.size() != creditsItem.getNumOrganizationNames()) return false;
    else {
      for(int i=0;i<organizationNames.size();i++) {
        if (!getOrganizationName(i).getOrganizationName().equals(creditsItem.getOrganizationName(i).getOrganizationName())) return false;
      }
    }
    return true;
  }

}