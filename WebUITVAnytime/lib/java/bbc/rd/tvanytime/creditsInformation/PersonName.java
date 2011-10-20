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

/**
 * Represents a TV-Anytime PersonName object.
 *
 * @author Tristan Ferne, BBC Research & Development, April 2003
 *
 * @version 1.1
 */
public class PersonName  implements Cloneable  {
  /**
   * Given name.
   */
  protected Name givenName = null;
  /**
   * Family name.
   */
  protected Name familyName = null;

  /**
   * Constructor.
   */
  public PersonName() {
  }

  /**
   * Constructor.
   *
   * @param  givenName  Given name of person.
   */
  public PersonName(Name givenName) {
    setGivenName(givenName);
  }

  /**
   * Returns whole name.
   */
  public String getName() {
    String name = "";
    if (givenName != null) name += givenName.getName();
    if (familyName != null) {
      if (name.length()>0) name += " "; // Separator of names
      name += familyName.getName();
    }
    return name;
  }

  /**
   * Set given name of person.
   *
   * @param  givenName  Given name of person.
   */
  public void setGivenName(Name givenName)  {
    this.givenName = givenName;
  }

  /**
   * Get given name of person.
   *
   * @return  Given name of person.
   */
  public Name getGivenName()  {
    return givenName;
  }

  /**
   * Set family name of person.
   *
   * @param  familyName  Family name of person.
   */
  public void setFamilyName(Name familyName)  {
    this.familyName = familyName;
  }

  /**
   * Get family name of person.
   *
   * @return  Family name of person.
   */
  public Name getFamilyName()  {
    return familyName;
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
                xmlBuf.append("<PersonName>\n");
                //xml += "<PersonName>\n";

                if (givenName != null) {
                    for (int i=0;i<indent+1;i++) {
                        xmlBuf.append("\t");
                        //xml += "\t";
                    }
                    xmlBuf.append("<mpeg7:GivenName>");
                    xmlBuf.append(givenName.getName());
                    xmlBuf.append("</mpeg7:GivenName>\n");
                    //xml = xml + "<mpeg7:GivenName>"+givenName.getName() + "</mpeg7:GivenName>\n";
                }
                if (familyName != null) {
                    for (int i=0;i<indent+1;i++) {
                        xmlBuf.append("\t");
                        //xml += "\t";
                    }
                    xmlBuf.append("<mpeg7:FamilyName>");
                    xmlBuf.append(familyName.getName());
                    xmlBuf.append("</mpeg7:FamilyName>\n");
                    //xml = xml + "<mpeg7:FamilyName>"+familyName.getName() + "</mpeg7:FamilyName>\n";
                }

                //add required number of tabs
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("</PersonName>");
                //xml += "</PersonName>";
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
		sysOut += "PersonName:";

    if (givenName != null) {
      sysOut += "\n";
      for (int i=0;i<indent+1;i++) {
        sysOut += "\t";
      }
      sysOut = sysOut + "Given name: "+givenName.getName();
    }
    if (familyName != null) {
      sysOut += "\n";
      for (int i=0;i<indent+1;i++) {
        sysOut += "\t";
      }
      sysOut = sysOut + "Family name: "+familyName.getName();
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
    PersonName clone = new PersonName();

    if (givenName != null) clone.setGivenName((Name)givenName.clone());
    if (familyName != null) clone.setFamilyName((Name)familyName.clone());

    return clone;
  }




}