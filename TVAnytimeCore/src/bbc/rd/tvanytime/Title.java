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
 * Title: Represents a title object
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 *
 * Modified 26/4/04 T.Ferne: Added language attribute.
 */

public class Title implements Cloneable
{
	public static final int MAIN = 0;
	public static final int SECONDARY = 1;
	public static final int ALTERNATIVE = 2;
	public static final int ORIGINAL = 3;
	public static final int POPULAR = 4;
	public static final int OPUSNUMBER = 5;
	public static final int SONGTITLE = 6;
	public static final int ALBUMTITLE = 7;
	public static final int SERIESTITLE = 8;
	public static final int EPISODETITLE = 9;

	private String text;
	private String language;  
	private int titleType = MAIN;

	/**
	 * Constructor for objects of type Title
	 */
	public Title()
	{

	}

	/**
	 * Constructor for objects of type Title with required fields
	 *
	 * @param text the title text
	 */
	public Title(String text)
	{
		this.text = text;
	}

	/**
	 * Get the text of the title.
	 *
	 * @return the String representation of the Title or null
	 * if text is undefined
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Get the title language,
	 *
	 * @return the String representation of the title language or null
	 * if language is undefined.
	 */
	public String getLanguage()
	{
		return language;
	}
  
	/**
	 * Get the type of the title (MAIN, POPULAR, EPISODETITLE, etc.)
	 *
	 * @return the Title type (MAIN as default)
	 */
	public int getType()
	{
		return titleType;
	}

	/**
	 * Set the title text.
	 *
	 * @param text the String representation of the Title
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Set the title language
	 *
	 * @param  language  The String representation of the Title language
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * Sets the title type
	 *
	 * @param titleType the Title type
	 * @throws TVAnytimeException thrown when the title type is not valid
	 */
	public void setType(int titleType) throws TVAnytimeException
	{
		if ((titleType >= 0) && (titleType <= 9))
		{
			this.titleType = titleType;
		}

		else throw new TVAnytimeException("Title: Not a valid title type.");
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
		for (int i=0;i<indent;i++)
                    xmlBuf.append("\t");
                    //xml += "\t";

                String titleTypeAsString;

		switch(titleType)
		{
			case MAIN:
				titleTypeAsString = "main";
				break;
			case SECONDARY:
				titleTypeAsString = "secondary";
				break;
			case ALTERNATIVE:
				titleTypeAsString = "alternative";
				break;
			case ORIGINAL:
				titleTypeAsString = "original";
				break;
			case POPULAR:
				titleTypeAsString = "popular";
				break;
			case OPUSNUMBER:
				titleTypeAsString = "opusNumber";
				break;
			case SONGTITLE:
				titleTypeAsString = "songTitle";
				break;
			case ALBUMTITLE:
				titleTypeAsString = "albumTitle";
				break;
			case SERIESTITLE:
				titleTypeAsString = "seriesTitle";
				break;
			case EPISODETITLE:
				titleTypeAsString = "episodeTitle";
				break;
			default:
				titleTypeAsString = "main";
				break;
		}

		xmlBuf.append("<Title type=\"");
                xmlBuf.append(titleTypeAsString);
                xmlBuf.append("\"");
                //xml += "<Title type=\""+ titleTypeAsString + "\"";
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
                xmlBuf.append("</Title>");
                //xml += "</Title>";

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

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		String titleTypeAsString;

		switch(titleType)
		{
			case MAIN:
				titleTypeAsString = "main";
				break;
			case SECONDARY:
				titleTypeAsString = "secondary";
				break;
			case ALTERNATIVE:
				titleTypeAsString = "alternative";
				break;
			case ORIGINAL:
				titleTypeAsString = "original";
				break;
			case POPULAR:
				titleTypeAsString = "popular";
				break;
			case OPUSNUMBER:
				titleTypeAsString = "opusNumber";
				break;
			case SONGTITLE:
				titleTypeAsString = "songTitle";
				break;
			case ALBUMTITLE:
				titleTypeAsString = "albumTitle";
				break;
			case SERIESTITLE:
				titleTypeAsString = "seriesTitle";
				break;
			case EPISODETITLE:
				titleTypeAsString = "episodeTitle";
				break;
			default:
				titleTypeAsString = "main";
				break;
		}

		sysOut += "Title: " + text + ", type = "+ titleTypeAsString + ", language = " + language;

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    Title clone = new Title();
    try {
      if (text != null) clone.setText(new String(text));
      if (language != null) clone.setLanguage(new String(language));
      clone.setType(titleType);
    } catch (TVAnytimeException tvae) { }
    return clone;
  }


}
