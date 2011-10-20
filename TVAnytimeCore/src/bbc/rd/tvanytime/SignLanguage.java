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
 * SignLanguage: Represents a description of a SignLanguage.
 *
 * @author Tim Sargeant, BBC Research & Development, February 2003
 * @version 1.0
 */

public class SignLanguage implements Cloneable {

  /**
   * Primary flag attribute
   */
  private boolean primary = false;

  /**
   * Translation flag attribute
   */
  private boolean translation = false;;

  /**
   * type attribute
   */
  private String type = null;

  /**
   * language
   */
  private String lang;

  /**
   * Create SignLanguage object.
   */
  public SignLanguage() {

  }

  /**
   * Get language
   *
   * @return language a string representing the language
   */
  public String getLanguage() {
    return lang;
  }

  /**
   * Is this the primary sign language?
   *
   * @return  primary flag? (false if unset)
   */
  public boolean isPrimary() {
    return primary;
  }

  /**
   * Is this a translation sign language?
   *
   * @return  translation flag (false if unset)
   */
  public boolean isTranslation() {
    return translation;
  }

  /**
   * Get type.
   *
   * @return a string representing the type
   */
  public String getType() {
    return type;
  }


  /**
   * Set type
   *
   * @param type a string representing the SignLanguage type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Set language
   *
   * @param language a string representing the language
   */
  public void setLanguage(String language) {
    this.lang = language;
  }

  /**
   * Set primary flag
   *
   * @param primary value
   */
  public void setPrimary(boolean primary) {
    this.primary = primary;
  }

  /**
   * Set translation flag
   *
   * @param  translation value
   */
  public void setTranslation(boolean translation) {
    this.translation = translation;
  }

  /**
   * Returns XML representation of the audio attributes.
   *
   * @return  XML representation of the audio attributes.
   */
  public String toXML() {
    return toXML(0);
  }

  /**
   * Returns XML representation of the audio attributes.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  XML representation of the audio attributes.
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

            xmlBuf.append("<SignLanguage translation=\"");
            xmlBuf.append(translation);
            xmlBuf.append("\" primary=\"");
            xmlBuf.append(primary);
            xmlBuf.append("\"");
            //xml += "<SignLanguage translation=\""+translation+"\" primary=\""+primary+"\"";
	    if (type != null) {
                xmlBuf.append(" type=\"");
                xmlBuf.append(type);
                xmlBuf.append("\"");
                //xml += " type=\""+type+"\"";
            }
            xmlBuf.append(">")  ;  
	    //xml += ">";
            xmlBuf.append(lang);
            xmlBuf.append("</SignLanguage>");
            //xml += lang+"</SignLanguage>";

            return xmlBuf.toString();
        }
    }


  /**
   * Returns string representation of the audio attributes.
   *
   * @return  String representation of the audio attributes.
   */
  public String toString() {
    return toString(0);
  }

  /**
   * Returns string representation of the audio attributes.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  string representation of the audio attributes.
   */
  public String toString(int indent) {
		String sysOut = "";

		// Do tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut += "SignLanguage: \n";

	    // Indent and call children
		indent++;

		// Do tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut += "language: "+lang+"\n";
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut += "translation: "+translation+"\n";
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut += "primary: "+primary+"\n";
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		if (type != null) sysOut += "type: "+type+"\n";

		return sysOut;
  }

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    SignLanguage clone = new SignLanguage();
	clone.setPrimary(primary);
	clone.setTranslation(translation);
	clone.setType(type);
	clone.setLanguage(lang);

    return clone;
  }

}