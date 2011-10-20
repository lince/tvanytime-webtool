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
 * PromotionalInformation: Represents a TV-Anytime PromotionalInformation object.
 *
 * @author Tristan Ferne, BBC Research & Development, February 2003
 * @version 1.0
 */
public class PromotionalInformation  {

  /**
   * Promotional information.
   */
  private String text;

  /**
   * Default constructor.
   */
  public PromotionalInformation() {
  }

  /**
   * Constructor that sets promotional information.
   *
   * @param  text  Promotional information.
   */
  public PromotionalInformation(String text) {
    setPromotionalInformation(text);
  }

  /**
   * Returns promotional information.
   *
   * @return  Promotional information.
   */
  public String getPromotionalInformation() {
    return text;
  }

  /**
   * Sets promotional information.
   *
   * @param  text  Promotional information.
   */
  public void setPromotionalInformation(String text) {
    this.text = text;
  }


	/**
	 * Return a XML representation of this object.
	 *
	 * @return  XML representation of this object.
	 */
	public String toXML()
	{
		return toString(0);
	}

	/**
	 * Return a XML representation of this object.
	 *
	 * @param indent number of tabs to put before the string.
	 * @return  XML representation of this object.
	 */
	public String toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";
            
            synchronized(xmlBuf) {
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("<PromotionalInformation>");
                //xml += "<PromotionalInformation>";
                if (text != null) {
                    xmlBuf.append("<![CDATA[");
                    xmlBuf.append(text);
                    xmlBuf.append("]]>");
                    //xml = xml + "<![CDATA[" + text + "]]>";
                }
                xmlBuf.append("</PromotionalInformation>");
                //xml += "</PromotionalInformation>";
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
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
    }
		sysOut += "PromotionalInformation: ";
    if (text != null) sysOut += text;
		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    PromotionalInformation clone = new PromotionalInformation();
    if (text != null) clone.setPromotionalInformation(new String(text));
    return clone;
  }


}