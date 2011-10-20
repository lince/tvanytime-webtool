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


package bbc.rd.tvanytime;
/**
 * Represents a TV-Anytime Character object. Extends PersonName by changing XML
 * and string representation.
 * 
 * Note that strictly a Keyword is an NMTOKEN which must begin with a letter or 
 * underscore. Subsequent letters may include letters, digits, underscores, 
 * hyphens and periods only. They cannot include whitescape. However I have not
 * added these restrictions to keep backwards-compatibility.
 *
 *
 * @author Tristan Ferne, BBC Research & Development, April 2003
 * @version 1.0
 */
public class Keyword implements Cloneable {

  /**
   * Possible keyword types, default is Main.
   */
 	public static final int MAIN = 0;
 	public static final int SECONDARY = 1;
 	public static final int OTHER = 2;

  /**
   * Type of this keyword.
   */
  private int type = MAIN;
  /**
   * Keyword.
   */
  private String keyword;

  /**
   * Constructor.
   */
  public Keyword() {
  }

  /**
   * Constructor.
   *
   * @param  keyword  Keyword.
   */
  public Keyword(String keyword) {
    setKeyword(keyword);
  }

  /**
   * Get keyword.
   *
   * @return  Keyword.
   */
  public String getKeyword() {
    return keyword;
  }

  /**
   * Set keyword.
   *
   * @param  keyword  Keyword.
   */
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

 	/**
 	 * Sets the type for this keyword.
 	 *
 	 * @param 	type		the type required for this Keyword
 	 * @throws	TVAnytimeException if the type is invalid
 	 */
 	public void setType(int type) throws TVAnytimeException	{
 		if (type >= 0 && type <= 2)	this.type = type;
 		else throw new TVAnytimeException("Keyword type out of bounds");
 	}

 	/**
 	 * Returns the type for this Keyword object
 	 *
 	 * @return	the type for this Keyword object
 	 */
 	public int getType() {
 		return type;
 	}

 	/**
 	 * Returns a XML representation of this object
 	 *
 	 * @return	the XML representation of this object
 	 */
 	public String toXML()	{
 		return toXML(0);
 	}

 	/**
 	 * Returns a XML representation of this object with the specified number of tab indentations
 	 *
 	 * @return 	the XML representation of this object
 	 */
 	public String toXML(int indent)	{
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";

            synchronized(xmlBuf) {
 		String titleType = "";
 		switch (type)	{
 			case 0:
 				titleType = "main";
 				break;
 			case 1:
 				titleType = "secondary";
 				break;
 			case 2:
 				titleType = "other";
 				break;
 			default:
 				titleType = "main";
 				break;
 		}
 		//add the required number of tabs
 		for (int i=0; i<indent; i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
 		}
 		xmlBuf.append("<Keyword type=\"");
                xmlBuf.append(titleType);
                xmlBuf.append("\"><![CDATA[");
                xmlBuf.append(keyword);
                xmlBuf.append("]]></Keyword>");
                //xml += "<Keyword type=\"" + titleType + "\"><![CDATA["+keyword+"]]></Keyword>";
 		return xmlBuf.toString();
            }
 	}


 	/**
 	 * toString - returns a String representation of this object
 	 *
 	 * @return	the String representation of this object
 	 */
 	public String toString() {
 		return toString(0);
 	}

 	/**
 	 * toString - returns a String representation of this object with the specified number of tab indentations
 	 *
 	 * @return 	the String representation of this object
 	 */
 	public String toString(int indent) {
 		String sysOut = "";

 		String titleType = "";
 		switch (type)	{
 			case 0:
 				titleType = "main";
 				break;
 			case 1:
 				titleType = "secondary";
 				break;
 			case 2:
 				titleType = "other";
 				break;
 			default:
 				titleType = "main";
 				break;
 		}
 		//add the required number of tabs
 		for (int i=0; i<indent; i++) {
 			sysOut += "\t";
    }
 		sysOut += "Keyword type = " + titleType + ": "+keyword;
 		return sysOut;
 	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    Keyword clone = new Keyword();
    try {
      if (keyword != null) clone.setKeyword(new String(keyword));
      clone.setType(type);
    } catch (TVAnytimeException tvae) { System.out.println("Error cloning Keyword: "+tvae.getMessage()); }
    return clone;
  }

}