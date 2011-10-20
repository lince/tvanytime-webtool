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
 * CaptionLanguage: Represents a description of a CaptionLanguage.
 *
 * @author Tim Sargeant, BBC Research & Development, February 2003
 * @version 1.0
 */

public class CaptionLanguage implements Cloneable {

  /**
   * Closed flag
   */
  private boolean closed = true;

  /**
   * Supplemental flag
   */
  private boolean supplemental = false;;

  /**
   * language
   */
  private String lang;

  /**
   * Create CaptionLanguage object.
   */
  public CaptionLanguage() {

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
   * Is this using closed captioning?
   *
   * @return  closed captioning in use? (false if unset)
   */
  public boolean isClosed() {
    return closed;
  }

  /**
   * Is this using supplemental captioning?
   *
   * @return  supplemental captioning in use? (false if unset)
   */
  public boolean isSupplemental() {
    return supplemental;
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
   * Set closed captioning flag
   *
   * @param closed captioning value
   */
  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  /**
   * Set supplemental captioning flag
   *
   * @param  supplemental captioning value
   */
  public void setSupplemental(boolean supplemental) {
    this.supplemental = supplemental;
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

            xmlBuf.append("<CaptionLanguage closed=\"");
            xmlBuf.append(closed);
            xmlBuf.append("\" supplemental=\"");
            xmlBuf.append(supplemental);
            xmlBuf.append("\">");
            //xml += "<CaptionLanguage closed=\""+closed+"\" supplemental=\""+supplemental+"\">";
            xmlBuf.append(lang);
            xmlBuf.append("</CaptionLanguage>");
            //xml += lang+"</CaptionLanguage>";

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
		sysOut += "CaptionLanguage: \n";

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
		sysOut += "closed: "+closed+"\n";
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut += "supplemental: "+supplemental+"\n";

		return sysOut;
  }

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    CaptionLanguage clone = new CaptionLanguage();
	clone.setClosed(closed);
	clone.setSupplemental(supplemental);
	clone.setLanguage(lang);

    return clone;
  }

}