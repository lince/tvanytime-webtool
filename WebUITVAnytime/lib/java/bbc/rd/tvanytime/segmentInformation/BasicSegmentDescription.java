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


package bbc.rd.tvanytime.segmentInformation;

import bbc.rd.tvanytime.*;
import java.util.Vector;

/**
 * BasicSegmentDescription: Represents a TV-Anytime Description object.<br>
 * This object is named 'Description' in the XML and appears in the SegmentationInformation table. It is
 * of type 'BasicSegmentDescription'.
 *
 * @author Tim Sargeant, BBC Research & Development, February 2003
 *
 * Changes: Added support for Keywords
 * @version 1.01
 */

 public class BasicSegmentDescription {
 	private Vector titles = null;
 	private Vector synopses = null;
 	private Vector relatedMaterials = null;
 	private Vector keywords = null;

	/**
	 * Default constructor for objects of class BasicSegmentDescription
	 */
	public BasicSegmentDescription()
	{
		titles = new Vector(0,1);
		synopses = new Vector(0,1);
		relatedMaterials = new Vector(0,1);
		keywords = new Vector(0,1);

	}

	/**
	 * Returns a XML representation of this BasicSegmentDescription object with the specified number of tab indentations
	 *
	 * @return 		the XML representation of the BasicSegmentDescription object
	 */
        public String toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";
            
            synchronized(xmlBuf) {
                //add required number of tabs
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("<Description>");
                //xml += "<Description>";

                //increment tab level for child toString methods
                indent++;

                //title
                for(int i=0;i<titles.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((Title)titles.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((Title)titles.elementAt(i)).toXML(indent);
                }
                
                //synopsis
                for(int i=0;i<synopses.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((Synopsis)synopses.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((Synopsis)synopses.elementAt(i)).toXML(indent);
                }
                
                // Keywords
                for(int i=0;i<keywords.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((Keyword)keywords.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((Keyword)keywords.elementAt(i)).toXML(indent);
                }

                //related material
                for(int i=0;i<relatedMaterials.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((RelatedMaterial)relatedMaterials.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((RelatedMaterial)relatedMaterials.elementAt(i)).toXML(indent);
                }
                
                xmlBuf.append("\n");
                //xml += "\n";
                //add required number of tabs
                for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("</Description>");
                //xml += "</Description>";
                return xmlBuf.toString();
            }
	}

	/**
	 * toString - returns a String representation of this BasicSegmentDescription object with the specified number of tab indentations
	 *
	 * @return 		the String representation of the BasicSegmentDescription object
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		//add required number of tabs
		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "BasicSegmentDescription:";

		//increment tab level for child toString methods
		indent++;

		//title
		for(int i=0;i<titles.size();i++)
			sysOut += "\n" + ((Title)titles.elementAt(i)).toString(indent);

		//synopsis
		for(int i=0;i<synopses.size();i++)
			sysOut += "\n" + ((Synopsis)synopses.elementAt(i)).toString(indent);

		// Keywords
		for(int i=0;i<keywords.size();i++)
			sysOut += "\n" + ((Keyword)keywords.elementAt(i)).toString(indent);

		//related material
		for(int i=0;i<relatedMaterials.size();i++)
			sysOut += "\n" + ((RelatedMaterial)relatedMaterials.elementAt(i)).toString(indent);

		return sysOut;
	}

	/**
	 * getNumTitles - gets the number of Title objects belonging to this Description object
	 *
	 * @return 		the number of Title objects in this Description object
	 */
	public int getNumTitles()
	{
		return titles.size();
	}

	/**
	 * getTitle - gets the Title object at the specified index
	 *
	 * @param	index	the index of the required Title object
	 * @return 		the Title object at the specified index
	 */
	public Title getTitle(int index)
	{
		return (Title)titles.elementAt(index);
	}

	/**
	 * addTitle - adds a Title object to the Description object
	 *
	 * @param	title	the Title object
	 */
	public void addTitle(Title title)
	{
		titles.addElement(title);
	}

	/**
	 * getNumSynopses - gets the number of Synopsis objects belonging to this Description object
	 *
	 * @return 		the number of Synopsis objects in this Description object
	 */

	public int getNumSynopses()
	{
		return synopses.size();
	}

	/**
	 * getSynopsis - gets the Synopsis object at the specified index
	 *
	 * @param	index	the index of the required Synopsis object
	 * @return 		the Synopsis object at the specified index
	 */
	public Synopsis getSynopsis(int index)
	{
		return (Synopsis)synopses.elementAt(index);
	}

	/**
	 * addSynopsis - adds a Synopsis object to the Description object
	 *
	 * @param	synopsis	the Synopsis object
	 */
	public void addSynopsis(Synopsis synopsis)
	{
		synopses.addElement(synopsis);
	}

	/**
	* Get number	of Keyword objects contained in	this program.
	*
	* @return  Number of	Keyword	objects	contained in this program.
	*/
	public int getNumKeywords() {
		return keywords.size();
	}

	/**
	* Get Keyword object	for	this program.
	*
	* @param	 index The index of	the	Keyword	object to access.
	* @return  Specified	Keyword	object.
	*/
	public Keyword getKeyword(int	index) {
		if ( (index>=0)	&& (index<keywords.size()) ) {
			return (Keyword)keywords.elementAt(index);
		}
		else return	null;
	}

  /**
   * Add Keyword object	to this	program.
   *
   * @param	 memberOf  The Keyword object to add to	this program.
   */
	public void addKeyword(Keyword keyword) {
		keywords.addElement(keyword);
	}

	/**
	 * Removes a Keyword object from the Description object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeKeyword(int index) {
		keywords.removeElementAt(index);
	}

	/**
	 * getNumRelatedMaterials - gets the number of RelatedMaterial objects belonging to this Description object
	 *
	 * @return 		the number of RelatedMaterial objects in this Description object
	 */
	public int getNumRelatedMaterials()
	{
		return relatedMaterials.size();
	}

	/**
	 * getRelatedMaterial - gets the RelatedMaterial object at the specified index
	 *
	 * @param	index	the index of the required RelatedMaterial object
	 * @return 		the RelatedMaterial object at the specified index
	 */
	public RelatedMaterial getRelatedMaterial(int index)
	{
		return (RelatedMaterial)relatedMaterials.elementAt(index);
	}

	/**
	 * addRelatedMaterial - adds a RelatedMaterial object to the Description object
	 *
	 * @param	relatedMaterial	the RelatedMaterial object
	 */
	public void addRelatedMaterial(RelatedMaterial relatedMaterial)
	{
		relatedMaterials.addElement(relatedMaterial);
	}

	/**
	* removeAll - removes all Title, Synopsis, RelatedMaterial, CaptionLanguage,
	* SignLanguage, PromotionalInformation and Genre objects from this
	* Description object.
	*/
	public void removeAll()
	{
		titles.removeAllElements();
		synopses.removeAllElements();
		relatedMaterials.removeAllElements();
		keywords.removeAllElements();

	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    BasicSegmentDescription clone = new BasicSegmentDescription();

	for(int i=0;i<titles.size();i++) {
      clone.addTitle((Title)((Title)titles.elementAt(i)).clone());
    }
	for(int i=0;i<synopses.size();i++) {
      clone.addSynopsis((Synopsis)((Synopsis)synopses.elementAt(i)).clone());
    }
	for(int i=0;i<keywords.size();i++) {
	  clone.addKeyword((Keyword)((Keyword)keywords.elementAt(i)).clone());
	}
	for(int i=0;i<relatedMaterials.size();i++) {
      clone.addRelatedMaterial((RelatedMaterial)((RelatedMaterial)relatedMaterials.elementAt(i)).clone());
    }
    return clone;
  }
}
