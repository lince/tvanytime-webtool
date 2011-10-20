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


package bbc.rd.tvanytime;

/**
 * Synopsis: Represents a Synopsis object
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 */

public class Synopsis implements Cloneable
{
	public static final int UNDEFINED = -1;
	public static final int SHORT = 0;
	public static final int MEDIUM = 1;
	public static final int LONG = 2;
	
	private String text;
	private String language;
	private int length = UNDEFINED;

	private boolean synopsisLengthSet = false;


	/**
	 * Constructor for objects of type Synopsis
	 */
	public Synopsis()
	{

	}

	/**
	 * Constructor for objects of type Synopsis with required fields
	 *
	 * @param text the synopsis text
	 */
	public Synopsis(String text)
	{
		this.text = text;
	}

	/**
	 * Get the synopsis language
	 *
	 * @return the String representation of the Synopsis language or null
	 * if language is undefined
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * Get the synopsis length
	 *
	 * @return the length (SHORT, MEDIUM, LONG or UNDEFINED)
	 */
	public int getLength()
	{
		if (synopsisLengthSet)
		{
			return length;
		}
		else return UNDEFINED;
	}

	/**
	 * Get the textual synopsis
	 *
	 * @return the String representation of the Synopsis or null if undefined
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Set the synopsis language
	 *
	 * @param language the String representation of the Synopsis language
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * Set the synopsis length.  Throws a TVAnytimeException if
	 * the length is invalid.
	 *
	 * @param length SHORT, MEDIUM or LONG. UNDEFINED if not set.
	 * @throws TVAnytimeException thrown when the synopsis length is invalid
	 */
	public void setLength(int length) throws TVAnytimeException
	{
		if ((length >= 0) && (length < 3)) {
			this.length = length;
			synopsisLengthSet = true;
		}
    else if (length == UNDEFINED) {
      this.length = length;
			synopsisLengthSet = false;
    }
		else throw new TVAnytimeException("Synopsis: Not a valid Synopsis length.");
	}

	/**
	 * Sets the textual synopsis
	 *
	 * @param text the String representation of the Synopsis
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Returns XML representation of this object.
	 *
	 * @return a XML representation of this object.
	 */
	public String toXML()
	{
		return toXML(0);
	}

	/**
	 * Returns XML representation of this object.
	 *
	 * @param indent the number of tabs to put before the string.
	 * @return a XML representation of this object.
	 */
	public String toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";
            
            synchronized(xmlBuf) {
		for (int i=0;i<indent;i++)
                    xmlBuf.append("\t");
                    //xml += "\t";

		String synopsisLengthAsString;

		switch(length)
		{
			case UNDEFINED:
				synopsisLengthAsString = "undefined";
				break;
			case SHORT:
				synopsisLengthAsString = "short";
				break;
			case MEDIUM:
				synopsisLengthAsString = "medium";
				break;
			case LONG:
				synopsisLengthAsString = "long";
				break;
			default:
				synopsisLengthAsString = "undefined";
				break;
		}

		xmlBuf.append("<Synopsis");
                //xml += "<Synopsis";
                if (length!=UNDEFINED) {
                    xmlBuf.append(" length=\"");
                    xmlBuf.append(synopsisLengthAsString);
                    xmlBuf.append("\"");
                    //xml = xml + " length=\""+ synopsisLengthAsString + "\"";
                }
                if (language!=null) {
                    xmlBuf.append(" xml:lang=\"");
                    xmlBuf.append(language);
                    xmlBuf.append("\"");
                    //xml = xml + " xml:lang=\"" + language + "\"";
                }
                xmlBuf.append(">");
                //xml += ">";
		xmlBuf.append("<![CDATA[");
                xmlBuf.append(text);
                xmlBuf.append("]]>");
                //xml = xml + "<![CDATA[" + text + "]]>";
                xmlBuf.append("</Synopsis>");
                //xml += "</Synopsis>";

		return xmlBuf.toString();
            }
	}


	/**
	 * Returns string representation of this object.
	 *
	 * @return a string representation of this object.
	 */
	public String toString()
	{
		return toString(0);
	}

	/**
	 * Returns string representation of this object.
	 *
	 * @param indent the number of tabs to put before the string.
	 * @return a string representation of this object.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		String synopsisLengthAsString;

		switch(length)
		{
			case -1:
				synopsisLengthAsString = "undefined";
				break;
			case 0:
				synopsisLengthAsString = "short";
				break;
			case 1:
				synopsisLengthAsString = "medium";
				break;
			case 2:
				synopsisLengthAsString = "long";
				break;
			default:
				synopsisLengthAsString = "undefined";
				break;
		}

		sysOut += "Synopsis: " + text + ", length = "+ synopsisLengthAsString + ", language = " + language;
		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    Synopsis clone = new Synopsis();
    try {
      if (text != null) clone.setText(new String(text));
      if (language != null) clone.setLanguage(new String(language));
      clone.setLength(length);
    } catch (TVAnytimeException tvae) { }
    return clone;
  }

}
