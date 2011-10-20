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

import java.util.*;

/**
 * VideoAttributes: Represents technical video attributes of a program.
 *
 * @author Tristan Ferne, BBC Research & Development, November 2002
 * @version 1.0
 */

public class VideoAttributes implements Cloneable {

  /**
   * Aspect ratios.
   */
  private Vector aspectRatios;

  /**
   * Create VideoAttributes object.
   */
  public VideoAttributes() {
    aspectRatios = new Vector(0,1);
  }

	/**
	 * Add an AspectRatio object to this VideoAttributes object. Maximum of two
   * allowed.
	 *
	 * @param	aspectRatio	the AspectRatio object to be added.
	 */
	public void addAspectRatio(AspectRatio aspectRatio)	throws TVAnytimeException {
    if (getNumAspectRatios()<2) {
  		aspectRatios.addElement(aspectRatio);
    }
    else throw new TVAnytimeException("AspectRatio: Cannot contain more than two AspectRatio objects");
	}

	/**
	 * getNumAspectRatios - returns the number of aspectRatio objects in this VideoAttributes object
	 *
	 * @return 		the number of AspectRatio objects in this VideoAttributes object
	 */
	public int getNumAspectRatios()	{
		return aspectRatios.size();
	}

	/**
	 * getAspectRatio - returns the AspectRatio object at the specified index
	 *
	 * @param	index	the index of the required AspectRatio object
	 *
	 * @return 	the AspectRatio object at the specified index
	 */
	public AspectRatio getAspectRatio(int index) {
		return (AspectRatio)aspectRatios.elementAt(index);
	}

	/**
	 * Removes the AspectRatio object at the specified index
	 *
	 * @param	index	the index of the AspectRatio object to be removed
	 */
	public void removeAspectRatio(int index) {
		aspectRatios.removeElementAt(index);
	}


	/**
	 * removeAll - removes all AspectRatio objects from this VideoAttributes object
	 *
	 */
	public void removeAll()	{
		aspectRatios.removeAllElements();
	}

  /**
   * Returns XML representation of the video attributes.
   *
   * @return  XML representation of the video attributes.
   */
  public String toXML() {
    return toXML(0);
  }

  /**
   * Returns XML representation of the video attributes.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  XML representation of the video attributes.
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
            xmlBuf.append("<VideoAttributes>");
            //xml += "<VideoAttributes>";

            // Indent and call children
            indent++;

            // AspectRatio
            for (int i=0; i<aspectRatios.size();i++) {
                xmlBuf.append("\n");
                xmlBuf.append(((AspectRatio)aspectRatios.elementAt(i)).toXML(indent));
                //xml = xml + "\n" + ((AspectRatio)aspectRatios.elementAt(i)).toXML(indent);
            }

            xmlBuf.append("\n");
            //xml+= "\n";
            for (int i=0;i<indent-1;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }
            xmlBuf.append("</VideoAttributes>");
            //xml += "</VideoAttributes>";
            return xmlBuf.toString();
        }
    }


  /**
   * Returns string representation of the video attributes.
   *
   * @return  string representation of the video attributes.
   */
  public String toString() {
    return toString(0);
  }

  /**
   * Returns string representation of the video attributes.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  string representation of the video attributes.
   */
  public String toString(int indent) {
		String sysOut = "";

		// Do tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut += "VideoAttributes:";

	    // Indent and call children
		indent++;

	    // AspectRatio
	    for (int i=0; i<aspectRatios.size();i++) {
				sysOut = sysOut + "\n" + ((AspectRatio)aspectRatios.elementAt(i)).toString(indent);
	    }

		return sysOut;
  }

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    VideoAttributes clone = new VideoAttributes();
    try {
      for (int i=0; i<aspectRatios.size();i++) {
        clone.addAspectRatio((AspectRatio)((AspectRatio)aspectRatios.elementAt(i)).clone());
      }
    } catch (TVAnytimeException tvae) { }
    return clone;
  }
}