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
 * Represents a TV-Anytime Character object. Extends PersonName by changing XML
 * and string representation.
 *
 * @author Tristan Ferne, BBC Research & Development, April 2003
 *
 * @version 1.0
 */
public class OrganizationName  implements Cloneable  {

  /**
   * Organization name.
   */
  private String organizationName = "";

  /**
   * Constructor.
   */
  public OrganizationName() {
  }

  /**
   * Constructor.
   *
   * @param  organizationName  Given name of person.
   */
  public OrganizationName(String organizationName) {
    setOrganizationName(organizationName);
  }

  /**
   * Set name of organization.
   *
   * @param  organizationName  Name of organization.
   */
  public void setOrganizationName(String organizationName)  {
    this.organizationName = organizationName;
  }

  /**
   * Get name of organization.
   *
   * @return  Name of organization.
   */
  public String getOrganizationName()  {
    return organizationName;
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
                xmlBuf.append("<OrganizationName>");
                //xml += "<OrganizationName>";
                if (organizationName != null) 
                    xmlBuf.append(organizationName);
                    //xml += organizationName;
                
                xmlBuf.append("</OrganizationName>");
                //xml += "</OrganizationName>";
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
		sysOut += "OrganizationName: ";

    if (organizationName != null) sysOut = sysOut + organizationName;

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
    OrganizationName clone = new OrganizationName();

    if (organizationName != null) clone.setOrganizationName(new String(organizationName));

    return clone;
  }




}