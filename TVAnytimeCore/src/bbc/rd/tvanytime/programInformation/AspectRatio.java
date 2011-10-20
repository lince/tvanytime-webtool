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

import bbc.rd.tvanytime.TVAnytimeException;


/**
 * AspectRatio: Represents aspect ratio of a program.
 *
 * @author Tristan Ferne, BBC Research & Development, November 2002
 * @version 1.0
 */

public class AspectRatio implements Cloneable {

	public static final int ORIGINAL = 0;
	public static final int PUBLICATION = 1;

  /**
   * Aspect ratio (x:y).
   */
  private String aspectRatio;

  /**
   * Type of aspect ratio (original and this showing)
   */
  private int type = ORIGINAL;

  /**
   * Create AspectRatio object.
   */
  public AspectRatio() {
  }

  /**
   * Create AspectRatio object.
   *
   * @param aspectRatio Aspect ratio of this program in the form "x:y".
   */
  public AspectRatio(String aspectRatio) throws TVAnytimeException {
    setAspectRatio(aspectRatio);
  }

  /**
   * Return aspect ratio of this program. Aspect ratio is in the form "x:x".
   *
   * @return  The aspect ratio of this program.
   */
  public String getAspectRatio() {
    return aspectRatio;
  }

  /**
   * Set aspect ratio of this program. Aspect ratio is in the form "x:y".
   *
   * @param  aspectRatio  The aspect ratio of this program.
   * @throws  TVAnytimeException  Thrown if passed invalid aspect ratio (must be "x:y").
   */
  public void setAspectRatio(String aspectRatio) throws TVAnytimeException {
    // Find first :
    int colon = aspectRatio.indexOf(":");
    if (colon<1) throw new TVAnytimeException("VideoAttributes: Invalid aspect ratio of "+aspectRatio+", should be of the form \"x:y\"");
    // Parse integer before it
    try {
      Integer.parseInt(aspectRatio.substring(0,colon));
    } catch (NumberFormatException nfe) {
      throw new TVAnytimeException("AspectRatio: Invalid aspect ratio of "+aspectRatio+", should be of the form \"x:y\"");
    }
    // Parse integer after it
    try {
      Integer.parseInt(aspectRatio.substring(colon+1));
    } catch (NumberFormatException nfe) {
      throw new TVAnytimeException("AspectRatio: Invalid aspect ratio of "+aspectRatio+", should be of the form \"x:y\"");
    }
    // Valid
    this.aspectRatio = aspectRatio;
  }

  /**
   * Return the type of aspect ratio of this program.
   *
   * @return  The type of aspect ratio of this program.
   */
  public int getType() {
    return type;
  }

  /**
   * Set the type of aspect ratio of this program.
   *
   * @param  type  The type of aspect ratio of this program.
   * @throws  TVAnytimeException  Thrown if passed invalid aspect ratio (must be "x:y").
   */
  public void setType(int type) throws TVAnytimeException {
		if ((type >= 0) && (type <= 1)) {
			this.type = type;
		}
		else throw new TVAnytimeException("AspectRatio: Not a valid aspect ratio type.");
  }

  /**
   * Returns XML representation of the aspect ratio.
   *
   * @return  XML representation of the aspect ratio.
   */
  public String toXML() {
    return toString(0);
  }

  /**
   * Returns XML representation of the aspect ratio.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  XML representation of the aspect ratio.
   */
    public String toXML(int indent) {
        StringBuffer xmlBuf = new StringBuffer();
        //String xml = "";
        
        synchronized(xmlBuf) {
            for (int i=0;i<indent;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }
            xmlBuf.append("<AspectRatio");
            //xml = xml + "<AspectRatio";
            if (type == ORIGINAL) 
		xmlBuf.append(" type=\"original\"");
		//xml += " type=\"original\"";
            else if (type == PUBLICATION) 
                xmlBuf.append(" type=\"publication\"");
                //xml += " type=\"publication\"";
            xmlBuf.append(">");
            xmlBuf.append(aspectRatio);
            xmlBuf.append("</AspectRatio>");
            //xml = xml + ">" + aspectRatio + "</AspectRatio>";

            return xmlBuf.toString();
        }
    }

  /**
   * Returns string representation of the aspect ratio.
   *
   * @return  string representation of the aspect ratio.
   */
  public String toString() {
    return toString(0);
  }

  /**
   * Returns string representation of the aspect ratio.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  string representation of the aspect ratio.
   */
  public String toString(int indent) {
		String sysOut = "";

    for (int i=0;i<indent;i++) {
			sysOut += "\t";
    }
    sysOut = sysOut + "aspectRatio: " + aspectRatio;
    if (type == ORIGINAL) sysOut += " (original)";
    else if (type == PUBLICATION) sysOut += " (publication)";

    return sysOut;
  }

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    AspectRatio clone = new AspectRatio();
    try {
      if (aspectRatio != null) clone.setAspectRatio(new String(aspectRatio));
      clone.setType(type);
    } catch (TVAnytimeException tvae) { }
    return clone;
  }

}