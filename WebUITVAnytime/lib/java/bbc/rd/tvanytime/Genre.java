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
 * Genre: Represents a TV-Anytime Genre object
 *
 * @author Chris Akanbi, BBC Research & Development, April 2002
 * @version 1.0
 */

 import java.util.*;

 public class Genre implements Cloneable
 {
 	private static final int GENRE_LEVELS = 4;
 	public static final int MAIN = 0;
 	public static final int SECONDARY = 1;
 	public static final int OTHER = 2;
 	public static final int GENRE_CS_NUM = 8;

 	//Despite the setHref method there can only be one form of href (shown below).
 	//To vary the format of href would cause difficult implementations of methods.
 	private static final String hrefTags[] = {
    "urn:tva:metadata:cs:IntentionCS:2002:",
    "urn:tva:metadata:cs:FormatCS:2002:",
    "urn:tva:metadata:cs:ContentCS:2002:",
    "urn:tva:metadata:cs:OriginationCS:2002:",
    "urn:tva:metadata:cs:IntendedAudienceCS:2002:",
    "urn:tva:metadata:cs:ContentAlertCS:2002:",
    "urn:tva:metadata:cs:MediaTypeCS:2002:",
    "urn:tva:metadata:cs:AtmosphereCS:2002:"
  };
  // CS number for above schemes
  private static final int[] csNumbers = {1,2,3,5,4,6,7,8};

 	private String href = null;
 	private String numberedHierarchy;
 	private int genreValue[] = new int[GENRE_LEVELS];
 	private Vector MPEG7Names = null;
 	private int type = MAIN;
 	private int genreCsNum = 0; // Index in hrefTags and csNumbers.


 	/**
 	 * Constructor for Genre class
 	 */
 	public Genre()
 	{
 		MPEG7Names = new Vector(0,1);
 	}

 	/**
 	 * Constructor for Genre class
 	 *
 	 * @param	href	a String representing the href attribute for this Genre (including
 	 *	the numbered Genre hierarchy
 	 * @throws	TVAnytimeException if the string passed is of the wrong format
 	 */
 	public Genre(String href) throws TVAnytimeException
 	{
 		this();
 		setHref(href);

 	}

 	/**
 	 * setNumberedHierarchy - sets the numbered hierarchy of this Genre and also updates
 	 * the href String and CS number and clears MPEG7Names.
 	 *
 	 * @param	numberedHierarchy	String representation of dot-separated genre number
 	 * @throws	TVAnytimeException if the hierarchy string is of the wrong format
 	 */
 	public void setNumberedHierarchy(String numberedHierarchy) throws TVAnytimeException
 	{
 		String nameSubstring = numberedHierarchy;
 		int dot = -1;
 		int genreNum = 0;
 		boolean endOfNameFound = false;


 		//if the hierarchy is being set to unknown then the Genre level values are 0
 		if (numberedHierarchy.equals("Unknown"))
 		{
 			for (int i=0;i<GENRE_LEVELS; i++)
 			{
 				genreValue[i] = 0;
 			}

 		}

 		else
 		{
 			for (int i=0;i<GENRE_LEVELS; i++)
 			{
 				genreNum = 0;

 				//has the whole string been processed
 				if (!endOfNameFound)
 				{
 					nameSubstring = nameSubstring.substring(dot+1);

 		    		dot = nameSubstring.indexOf(".");
 		    		//if there are no more dots the string has ended
 		   	 		if (-1 ==dot)
 		    		{
 		    			endOfNameFound = true;
	 		    		//length of number field in string is the whole string now
 			    		dot = nameSubstring.length();
 			   		}

 					try
 					{
 						//get value of genre at this level
 						genreNum = Integer.parseInt(nameSubstring.substring(0, dot));
 					}
 					catch (NumberFormatException exp)
 					{
 						throw new TVAnytimeException("Incorrectly formatted Genre number hierarchy:"
 							+ numberedHierarchy);
 					}

 				}

 				genreValue[i] = genreNum;

	 		}
 		}
 		this.numberedHierarchy = numberedHierarchy;
    // Find CS, first number specifices CS type
    int ct=0;
    for (ct=0; ct<csNumbers.length; ct++) {
      if (csNumbers[ct]==genreValue[0]) break;
    }
    genreCsNum = ct;
    MPEG7Names.removeAllElements();
 		href = hrefTags[genreCsNum] + numberedHierarchy;
 	}

 	/**
 	 * getNumberedHierarchy - returns the String representation of the Genre dot-separated number
 	 *
 	 * @return		String representation of the Genre dot-separated number
 	 */
 	public String getNumberedHierarchy()
 	{
 		return numberedHierarchy;
 	}

 	/**
 	 * setLevel - sets the specified genre name level to the value passed as a parameter
 	 *
 	 * @param	genreLevel 	the genre level to be set
 	 * @param	genreValue	the value the genre level is to be set to
 	 * @throws 	TVAnytimeException if there is a format error
 	 */
 	public void setLevel(int genreLevel, int genreValue) throws TVAnytimeException
 	{
 		String newHierarchy = "";
 		this.genreValue[genreLevel] = genreValue;
 		for (int i=0; i<GENRE_LEVELS; i++)
 		{
 			newHierarchy += String.valueOf(this.genreValue[i])+".";
 		}
 		newHierarchy = newHierarchy.substring(0, newHierarchy.length()-1);
 		//eliminate trailing zeros
 		for(int i=newHierarchy.length()-2; i>0; i=i-2)
		{
			if ((newHierarchy.substring(i, i+2)).equals(".0"))
			{
				newHierarchy = newHierarchy.substring(0,i);

			}

			else
			{
				i=0;
			}

		}

 		setNumberedHierarchy(newHierarchy);

 	}

 	/**
 	 * getGenreValue - returns the value of the Genre at the specified level
 	 *
 	 * @param	genreLevel	the level of the genre value required
 	 * @return 	genreValue	the value of the genre at the required level
 	 */
 	public int getGenreValue(int genreLevel)
 	{
 		return genreValue[genreLevel];
 	}

 	/**
 	 * setType - sets the genre type for this Genre
 	 *
 	 * @param 	type		the genre type required for this Genre
 	 * @throws	TVAnytimeException if the type is invalid
 	 */
 	public void setType(int type) throws TVAnytimeException
 	{
 		if (type >= 0 && type <= 2)
 			this.type = type;

 		else throw new TVAnytimeException("Genre type out of bounds");
 	}

 	/**
 	 * getType - returns the Genre type for this Genre object
 	 *
 	 * @return	the Genre type for this Genre object
 	 */
 	public int getType()
 	{
 		return type;
 	}

 	/**
 	 * setHref - sets the href variable for this Genre. Note: must have prefix of
 	 *	 "urn:tva:metadata:cs:xxxCS:2002:" to be valid. Also sets CS number.
 	 *
 	 *
 	 * @param 	href		the href required for this Genre
 	 * @throws	TVAnytimeException if the href string has the wrong format
 	 */
 	public void setHref(String href) throws TVAnytimeException
 	{
 		boolean foundHref = false;
 		for (int i=0; i< GENRE_CS_NUM;i++)
 		{
 			if (href.indexOf(hrefTags[i]) >= 0)
 			{
 				genreCsNum = i;
 				//the below method also sets the href string
 				setNumberedHierarchy(href.substring(hrefTags[i].length()));
 				foundHref = true;
 			}
 		}

 		if (!foundHref) {
      // CHANGE FOR IBC, T.FERNE 9/9/03
      //setNumberedHierarchy("3"); // DUMMY - Content
      this.href = href;
      throw new TVAnytimeException("Invalid href: " + href);
    }
 	}

 	/**
 	 * getHref - returns the href variable for this Genre object as a String
 	 *
 	 * @return	the href variable for this Genre object
 	 */
 	public String getHref()
 	{
 		return href;
 	}

 	/**
 	 * Returns a XML representation of this Genre object
 	 *
 	 * @return	the XML representation of this Genre object
 	 */
 	public String toXML()
 	{
 		return toXML(0);
 	}

 	/**
 	 * Returns a XML representation of this Genre object with the specified number of tab indentations
 	 *
 	 * @return 	the XML representation of this Genre object
 	 */
 	public String toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";

            synchronized(xmlBuf) {
                String titleType = "";
 		switch (type)
 		{
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
 		xmlBuf.append("<Genre href=\"");
                xmlBuf.append(href);
                xmlBuf.append("\" type=\"");
                xmlBuf.append(titleType);
                xmlBuf.append("\">");
                
                //xml += "<Genre href=\""+ href + "\" type=\"" + titleType + "\">";
 		indent++;
 		for (int i=0; i<MPEG7Names.size(); i++) {
                    xmlBuf.append("\n");
                    //xml += "\n";
                    for (int j=0; j<indent; j++) {
                        xmlBuf.append("\t");
                        //xml += "\t";
                    }
                    xmlBuf.append("<Name>");
                    //xml += "<Name>";
                    xmlBuf.append("<![CDATA[");
                    xmlBuf.append((String)MPEG7Names.elementAt(i));
                    xmlBuf.append("]]>");
                    //xml += "<![CDATA[" + (String)MPEG7Names.elementAt(i) + "]]>";
                    xmlBuf.append("</Name>");
                    //xml += "</Name>";
		}
		xmlBuf.append("\n");
                //xml += "\n";
 		for (int i=0; i<indent-1; i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
 		}
 		
                xmlBuf.append("</Genre>");
                //xml += "</Genre>";
 		return xmlBuf.toString();
            }
 	}


 	/**
 	 * toString - returns a String representation of this Genre object
 	 *
 	 * @return	the String representation of this Genre object
 	 */
 	public String toString()
 	{
 		return toString(0);
 	}

 	/**
 	 * toString - returns a String representation of this Genre object with the specified number of tab indentations
 	 *
 	 * @return 	the String representation of this String object
 	 */
 	public String toString(int indent)
 	{
 		String sysOut = "";

 		String titleType = "";
 		switch (type)
 		{
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
 		for (int i=0; i<indent; i++)
 			sysOut += "\t";

 		sysOut += "Genre href = "+ href + ", Genre type = " + titleType;

 		for (int i=0; i<MPEG7Names.size(); i++)
 		{
 			sysOut += "\n";
 			for (int j=0; j<indent+1; j++)
 				sysOut += "\t";
 			sysOut += "MPEG7Name: " + (String)MPEG7Names.elementAt(i);
 		}

 		return sysOut;
 	}

 	/**
 	 * addMPEG7Name - adds a MPEG7Name to this Genre object
 	 *
 	 * @param	MPEG7Name	the MPEG7Name String to be added
 	 */
 	public void addMPEG7Name(String MPEG7Name)
 	{
 		MPEG7Names.addElement(MPEG7Name);
 	}

    /**
     * getMPEG7Name - returns the MPEG7Name String at the specified index
     *
     * @param 	index	the index of the desired MPEG7Name String
     * @return	the MPEG7Name String at the specified index
     */
    public String getMPEG7Name(int index)
    {
    	return (String)MPEG7Names.elementAt(index);
    }

   	/**
   	 * getNumMPEG7Names - returns the number of MPEG7Name Strings in this Genre object
   	 *
   	 * @return 	the number of MPEG7Name Strings in this Genre object
   	 */
   	public int getNumMPEG7Names()
   	{
   		return MPEG7Names.size();
   	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    Genre clone = new Genre();
    try {
      //if (numberedHierarchy != null) clone.setNumberedHierarchy(new String(numberedHierarchy));
      // setHREF is better because it sets CS as well
      if (href != null) clone.setHref(new String(href));
      clone.setType(type);
      for (int ct=0; ct<getNumMPEG7Names(); ct++) {
        clone.addMPEG7Name(new String(getMPEG7Name(ct)));
      }
    } catch (TVAnytimeException tvae) { System.out.println("Error cloning Genre: "+tvae.getMessage()); }
    return clone;
  }

 }