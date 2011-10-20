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


package bbc.rd.tvanytime.programInformation;

import bbc.rd.tvanytime.*;

/**
 * AudioAttributes: Represents technical audio attributes of a program.
 *
 * @author Tristan Ferne, BBC Research & Development, November 2002
 * @version 1.0
 */

public class AudioAttributes implements Cloneable {

  /**
   * Number of audio channels (>=0).
   */
  private Integer numOfChannels = null;

  /**
   * Create AudioAttributes object.
   *
   * @param  numOfChannels  Number of audio channels.
   * @throws  TVAnytimeException  Thrown if invalid number of channels (must be >=0).
   */
  public AudioAttributes(Integer numOfChannels) throws TVAnytimeException {
    this();
    setNumOfChannels(numOfChannels);
  }

  /**
   * Create AudioAttributes object.
   */
  public AudioAttributes() {
  }

  /**
   * Get number of audio channels.
   *
   * @return  Number of audio channels
   */
  public Integer getNumOfChannels() {
    return numOfChannels;
  }

  /**
   * Set number of audio channels
   *
   * @param  numOfChannels  Number of audio channels.
   * @throws  TVAnytimeException  Thrown if invalid number of channels (must be >=0).
   */
  public void setNumOfChannels(Integer numOfChannels) throws TVAnytimeException {
    if (numOfChannels!=null && numOfChannels.intValue()>=0) this.numOfChannels = numOfChannels;
    else {
      throw new TVAnytimeException("AudioAttributes: Invalid number of channels ("+numOfChannels+"), must be >=0.");
    }
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
            xmlBuf.append("<AudioAttributes>");
            //xml += "<AudioAttributes>";

            // Indent and call children
            indent++;

            if (numOfChannels != null) {
                // Number of channels
                xmlBuf.append("\n");
                //xml += "\n";
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("<NumOfChannels>");
                xmlBuf.append(numOfChannels.intValue());
                xmlBuf.append("</NumOfChannels>");
                //xml = xml + "<NumOfChannels>" + numOfChannels.intValue() + "</NumOfChannels>";
            }

            xmlBuf.append("\n");
            //xml+= "\n";
            for (int i=0;i<indent-1;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }
            xmlBuf.append("</AudioAttributes>");
            //xml += "</AudioAttributes>";

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
		sysOut += "AudioAttributes: \n";

	    // Indent and call children
		indent++;

	    // programID
	    for (int i=0;i<indent;i++) {
				sysOut += "\t";
	    }
	    sysOut = sysOut + "numOfChannels: " + numOfChannels.intValue();

		return sysOut;
  }

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    AudioAttributes clone = new AudioAttributes();
    try {
      if (numOfChannels != null) clone.setNumOfChannels(numOfChannels);
    } catch (TVAnytimeException tvae) { }
    return clone;
  }

}