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

/**
 * Represents a TV-Anytime CreditsList object.
 *
 * @author Tristan Ferne, BBC Research & Development, April 2003
 *
 * @version 1.0
 */
public class CreditsList  implements Cloneable  {

  /**
   * List of CreditsItems.
   */
  private Vector creditsItems;

  /**
   * Constructor.
   */
  public CreditsList() {
    creditsItems = new Vector();
  }

  /**
   * Return number of CreditsItems in list.
   *
   * @return  Number of CreditsItems in list.
   */
  public int getNumCreditsItems() {
    return creditsItems.size();
  }


	/**
	 * Gets the CreditsItem object at the specified index
	 *
	 * @param	 index	the index of the required CreditsItem object
	 * @return 		the CreditsItem object at the specified index
	 */
  public CreditsItem getCreditsItem(int index) {
    return (CreditsItem)creditsItems.elementAt(index);
  }

	/**
	 * Adds a CreditsItem object to add to the listt
	 *
	 * @param	 creditsItem	the CreditsItem object
	 */
	public void addCreditsItem(CreditsItem creditsItem)	{
		creditsItems.addElement(creditsItem);
	}

	/**
	 * Removes a CreditsItem object from the list
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeCreditsItem(int index) {
		creditsItems.removeElementAt(index);
	}

	/**
	 * removeAll - removes all CreditsItems.
	 */
	public void removeAll()	{
		creditsItems.removeAllElements();
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
                xmlBuf.append("<CreditsList>\n");
                //xml += "<CreditsList>\n";

                for (int ct=0; ct<getNumCreditsItems(); ct++) {
                    xmlBuf.append(getCreditsItem(ct).toXML(indent+1));
                    xmlBuf.append("\n");
                    //xml = xml + getCreditsItem(ct).toXML(indent+1) + "\n";
                }

                //add required number of tabs
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("</CreditsList>");
                //xml += "</CreditsList>";
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

		sysOut += "CreditsList:";

		//increment tab level for child toString methods
		indent++;

		//title
		for(int i=0;i<getNumCreditsItems();i++) {
			sysOut += "\n" + getCreditsItem(i).toString(indent);
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
    CreditsList clone = new CreditsList();

		for(int i=0;i<creditsItems.size();i++) {
      clone.addCreditsItem((CreditsItem)((CreditsItem)creditsItems.elementAt(i)).clone());
    }
    return clone;
  }


}