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

/**
 * AVAttributes: Represents audio-visual information about a program.
 *
 * @author Tristan Ferne, BBC Research & Development, November 2002
 * @version 1.0
 */

public class AVAttributes implements Cloneable {

  /**
   * AudioAttributes object.
   *
   * @association <bbc.rd.tvanytime.programInformation.JavaAssociation1> bbc.rd.tvanytime.programInformation.AudioAttributes
   */
  private AudioAttributes audioAttributes = null;

  /**
   * VideoAttributes object.
   *
   * @association <bbc.rd.tvanytime.programInformation.JavaAssociation2> bbc.rd.tvanytime.programInformation.VideoAttributes
   */
  private VideoAttributes videoAttributes = null;


  /**
   * Constructor for AVAttributes.
   *
   * @param   audioAttributes  Audio attributes object.
   * @param   videoAttributes  Video attributes object.
   */
  public AVAttributes(AudioAttributes audioAttributes, VideoAttributes videoAttributes) {
    this();
    setAudioAttributes(audioAttributes);
    setVideoAttributes(videoAttributes);
  }

  /**
   * Constructor for AVAttributes.
   */
  public AVAttributes() {
  }

  /**
   * Set audio attributes object.
   *
   * @param   audioAttributes  Audio attributes object.
   */
  public void setAudioAttributes(AudioAttributes audioAttributes) {
    this.audioAttributes = audioAttributes;
  }

  /**
   * Get audio attributes object.
   *
   * @return  Audio attributes object.
   */
  public AudioAttributes getAudioAttributes() {
    return audioAttributes;
  }

  /**
   * Set video attributes object.
   *
   * @param   videoAttributes  Video attributes object.
   */
  public VideoAttributes getVideoAttributes() {
    return videoAttributes;
  }

  /**
   * Get video attributes object.
   *
   * @return  Video attributes object.
   */
  public void setVideoAttributes(VideoAttributes videoAttributes) {
    this.videoAttributes = videoAttributes;
  }

  /**
   * Returns XML representation of the audio-visual attributes.
   *
   * @return  XML representation of the audio-visual attributes.
   */
  public String toXML() {
    return toXML(0);
  }

  /**
   * Returns XML representation of the audio-visual attributes.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  XML representation of the audio-visual attributes.
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
            xmlBuf.append("<AVAttributes>");
            //xml += "<AVAttributes>";

            // Indent and call children
            indent++;

            // AudioAttributes
            if (audioAttributes != null) {
                xmlBuf.append("\n");
                xmlBuf.append(audioAttributes.toXML(indent));
                //xml = xml + "\n" + audioAttributes.toXML(indent);
            }

            // VideoAttributes
            if (videoAttributes != null) {
                xmlBuf.append("\n");
                xmlBuf.append(videoAttributes.toXML(indent));
                //xml = xml + "\n" + videoAttributes.toXML(indent);   
            }

            xmlBuf.append("\n");
            //xml += "\n";
            for (int i=0;i<indent-1;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }   
            xmlBuf.append("</AVAttributes>");
            //xml += "</AVAttributes>";
            return xmlBuf.toString();
        }
    }



  /**
   * Returns string representation of the audio-visual attributes.
   *
   * @return  String representation of the audio-visual attributes.
   */
  public String toString() {
    return toString(0);
  }

  /**
   * Returns string representation of the audio-visual attributes.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  string representation of the audio-visual attributes.
   */
  public String toString(int indent) {
		String sysOut = "";

		// Do tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut += "AVAttributes:";

	    // Indent and call children
		indent++;

	     // AudioAttributes
	    if (audioAttributes != null) {
	      sysOut = sysOut + "\n" + audioAttributes.toString(indent);
	    }

	    // VideoAttributes
	    if (videoAttributes != null) {
	      sysOut = sysOut + "\n" + videoAttributes.toString(indent);
	    }

		return sysOut;
  }

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    AVAttributes clone = new AVAttributes();
    if (audioAttributes != null) clone.setAudioAttributes((AudioAttributes)audioAttributes.clone());
    if (videoAttributes != null) clone.setVideoAttributes((VideoAttributes)videoAttributes.clone());
    return clone;
  }

}