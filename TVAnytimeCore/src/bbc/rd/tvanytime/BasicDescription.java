/**
 * Copyright 2003 British Broadcasting Corporation
 *
 * This	file is	part of	the	BBC	R&D	TV-Anytime Java	API.
 *
 * The BBC R&D TV-Anytime Java API is free software; you can redistribute it
 * and/or modify it	under the terms	of the GNU Lesser General Public License as
 * published by	the	Free Software Foundation; either version 2 of the
 * License,	or (at your	option)	any	later version.
 *
 * The BBC R&D TV-Anytime Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A	PARTICULAR PURPOSE.	 See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received	a copy of the GNU Lesser General Public	License
 * along with the BBC R&D TV-Anytime Java API; if not, write to	the	Free
 * Software	Foundation,	Inc., 59 Temple	Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */


package	bbc.rd.tvanytime;

import bbc.rd.tvanytime.creditsInformation.*;
import java.util.Vector;

/**
 * BasicDescsription: Represents a TV-Anytime BasicDescription object.
 *
 * @author Chris Akanbi, BBC Research &	Development, April 2002
 * @version	1.0
 */

 public	class BasicDescription {
 	private Vector genres = null;
 	private Vector titles = null;
 	private Vector synopses = null;
 	private Vector relatedMaterials = null;
 	private Vector captionLanguages = null;
 	private Vector signLanguages = null;
 	private Vector promotionalInformations = null;
 	private Vector keywords = null;
	private CreditsList creditsList =	null;

 	/**
	 * Constructor for objects of class BasicDescription
	 *
	 * @param	title	the title object
	 */
	public BasicDescription(Title title)
	{
		this();
		addTitle(title);
	}

	/**
	 * Default constructor for objects of class BasicDescription
	 */
	public BasicDescription()
	{
		titles = new Vector(0,1);
		synopses = new Vector(0,1);
		relatedMaterials = new Vector(0,1);
		captionLanguages = new Vector(0,1);
		signLanguages = new Vector(0,1);
		promotionalInformations = new Vector(0,1);
		genres = new Vector(0,1);
		keywords = new Vector(0,1);
		creditsList	= new CreditsList();
	}

	/**
	 * Returns a XML representation of this BasicDescription object with the specified number of tab indentations
	 *
	 * @return 		the XML representation of the BasicDescription object
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
		xmlBuf.append("<BasicDescription>");
                //xml += "<BasicDescription>";

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
		// PromotionalInformation
		for(int i=0;i<promotionalInformations.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((PromotionalInformation)promotionalInformations.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((PromotionalInformation)promotionalInformations.elementAt(i)).toXML(indent);
		}

		// Keywords
		for(int i=0;i<keywords.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((Keyword)keywords.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((Keyword)keywords.elementAt(i)).toXML(indent);
                }

		//genre
		for(int i=0;i<genres.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((Genre)genres.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((Genre)genres.elementAt(i)).toXML(indent);
                }

		// CaptionLanguage
		for(int i=0;i<captionLanguages.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((CaptionLanguage)captionLanguages.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((CaptionLanguage)captionLanguages.elementAt(i)).toXML(indent);
		}
		// SignLanguage
		for(int i=0;i<signLanguages.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((SignLanguage)signLanguages.elementAt(i)).toXML(indent));
                    //xml += "\n" + ((SignLanguage)signLanguages.elementAt(i)).toXML(indent);
		}
		// CreditsList
		if (creditsList.getNumCreditsItems()>0) {
                    xmlBuf.append("\n");
                    xmlBuf.append(creditsList.toXML(indent));
                    //xml += "\n" + creditsList.toXML(indent);
                }
		// Related material
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
		xmlBuf.append("</BasicDescription>");
                //xml += "</BasicDescription>";
		return xmlBuf.toString();
            }
	}


	/**
	 * toString - returns a String representation of this BasicDescription object with the specified number of tab indentations
	 *
	 * @return 		the String representation of the BasicDescription object
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		//add required number of tabs
		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "BasicDescription:";

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

		//genre
		for(int i=0;i<genres.size();i++)
			sysOut += "\n" + ((Genre)genres.elementAt(i)).toString(indent);

		// CreditsList
		if (creditsList	!= null) sysOut	+= "\n"	+ creditsList.toString(indent);

		// Related material
		for(int i=0;i<relatedMaterials.size();i++)
			sysOut += "\n" + ((RelatedMaterial)relatedMaterials.elementAt(i)).toString(indent);

		// CaptionLanguage
		for(int i=0;i<captionLanguages.size();i++)
			sysOut += "\n" + ((CaptionLanguage)captionLanguages.elementAt(i)).toString(indent);

		// SignLanguage
		for(int i=0;i<signLanguages.size();i++)
			sysOut += "\n" + ((SignLanguage)signLanguages.elementAt(i)).toString(indent);

		// PromotionalInformation
		for(int i=0;i<promotionalInformations.size();i++)
			sysOut += "\n" + ((PromotionalInformation)promotionalInformations.elementAt(i)).toString(indent);

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
	 * removeTitle - Removes a Title object to the Description object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeTitle(int index)
	{
		titles.removeElementAt(index);
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
	 * removeSynopsis - Removes a Synopsis object to the Description object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeSynopsis(int index)
	{
		synopses.removeElementAt(index);
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
	 * removeRelatedMaterial - Removes a RelatedMaterial object from the Description object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeRelatedMaterial(int index)
	{
		relatedMaterials.removeElementAt(index);
	}


	/**
	 * getNumCaptionLanguages - gets the number of CaptionLanguage objects belonging to this Description object
	 *
	 * @return 		the number of CaptionLanguage objects in this Description object
	 */
	public int getNumCaptionLanguages()
	{
		return captionLanguages.size();
	}

	/**
	 * getCaptionLanguage - gets the CaptionLanguage object at the specified index
	 *
	 * @param	index	the index of the required CaptionLanguage object
	 * @return 		the CaptionLanguage object at the specified index
	 */
	public CaptionLanguage getCaptionLanguage(int index)
	{
		return (CaptionLanguage)captionLanguages.elementAt(index);
	}

	/**
	 * addCaptionLanguage - adds a CaptionLanguage object to the Description object
	 *
	 * @param	title	the CaptionLanguage object
	 */
	public void addCaptionLanguage(CaptionLanguage captionLanguage)
	{
		captionLanguages.addElement(captionLanguage);
	}

	/**
	 * removeCaptionLanguage - Removes a CaptionLanguage object from the Description object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeCaptionLanguage(int index)
	{
		captionLanguages.removeElementAt(index);
	}

	/**
	 * getNumSignLanguages - gets the number of SignLanguage objects belonging to this Description object
	 *
	 * @return 		the number of SignLanguage objects in this Description object
	 */
	public int getNumSignLanguages()
	{
		return signLanguages.size();
	}

	/**
	 * getSignLanguage - gets the SignLanguage object at the specified index
	 *
	 * @param	index	the index of the required CaptionLanguage object
	 * @return 		the CaptionLanguage object at the specified index
	 */
	public SignLanguage getSignLanguage(int index)
	{
		return (SignLanguage)signLanguages.elementAt(index);
	}

	/**
	 * addSignLanguage - adds a SignLanguage object to the Description object
	 *
	 * @param	signLanguage	the SignLanguage object
	 */
	public void addSignLanguage(SignLanguage signLanguage)
	{
		signLanguages.addElement(signLanguage);
	}

	/**
	 * removeSignLanguage - Removes a SignLanguage object from the Description object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeSignLanguage(int index)
	{
		signLanguages.removeElementAt(index);
	}


	/**
	 * getNumPromotionalInformations - gets the number of PromotionalInformation objects belonging to this Description object
	 *
	 * @return 		the number of PromotionalInformation objects in this Description object
	 */
	public int getNumPromotionalInformations()
	{
		return promotionalInformations.size();
	}

	/**
	 * getPromotionalInformation - gets the PromotionalInformation object at the specified index
	 *
	 * @param	index	the index of the required PromotionalInformation object
	 * @return 		the CaptionLanguage object at the specified index
	 */
	public PromotionalInformation getPromotionalInformation(int index)
	{
		return (PromotionalInformation)promotionalInformations.elementAt(index);
	}

	/**
	 * addPromotionalInformation - adds a PromotionalInformation object to the Description object
	 *
	 * @param	PromotionalInformation	the PromotionalInformation object
	 */
	public void addPromotionalInformation(PromotionalInformation promotionalInformation)
	{
		promotionalInformations.addElement(promotionalInformation);
	}

	/**
	 * removePromotionalInformation - Removes a PromotionalInformation object from the Description object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removePromotionalInformation(int index)
	{
		promotionalInformations.removeElementAt(index);
	}

	/**
	 * addGenre - add a Genre object to this BasicDescription object
	 *
	 * @param	genre	the Genre object to be added
	 */
	public void addGenre(Genre genre)
	{
		genres.addElement(genre);
	}

	/**
	 * removeGenre - Removes a Genre object from the Description object
	 *
	 * @param	 index	The index of the object to remove.
	 */
	public void removeGenre(int index)
	{
		genres.removeElementAt(index);
	}

	/**
	 * getNumGenres - returns the number of Genre objects in this BasicDescription object
	 *
	 * @return 		the number of Genre objects in this BasicDescription object
	 */
	public int getNumGenres()
	{
		return genres.size();
	}

	/**
	 * getGenre - returns the Genre object at the specified index
	 *
	 * @param	index	the index of the required Genre object
	 *
	 * @return 		the Genre object at the specified index
	 */
	public Genre getGenre(int index)
	{
		return (Genre)genres.elementAt(index);
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
   * Returns the CreditsList.
   *
   * @return  The credits list.
   */
  public CreditsList getCreditsList() {
	return creditsList;
  }

  /**
   * Sets the CreditsList.
   *
   * @param	 creditsList  The credits list.
   */
  public void setCreditsList(CreditsList creditsList) {
	this.creditsList = creditsList;
  }


	/**
	 * removeAll - removes all Title, Synopsis, RelatedMaterial, CaptionLanguage,
   * SignLanguage, PromotionalInformation, Keyword and Genre objects from this
   * Description object.
	 */
	public void removeAll()
	{
		titles.removeAllElements();
		synopses.removeAllElements();
		relatedMaterials.removeAllElements();
		captionLanguages.removeAllElements();
		signLanguages.removeAllElements();
		promotionalInformations.removeAllElements();
		genres.removeAllElements();
		keywords.removeAllElements();
	}

  /**
   * Clones	itself.
   * @return  A	copy of	itself.
   */
  public Object	clone()	{
	BasicDescription clone = new BasicDescription();

		for(int i=0;i<titles.size();i++) {
	  clone.addTitle((Title)((Title)titles.elementAt(i)).clone());
	}
		for(int i=0;i<synopses.size();i++) {
	  clone.addSynopsis((Synopsis)((Synopsis)synopses.elementAt(i)).clone());
	}
		for(int i=0;i<relatedMaterials.size();i++) {
	  clone.addRelatedMaterial((RelatedMaterial)((RelatedMaterial)relatedMaterials.elementAt(i)).clone());
	}
		for(int i=0;i<keywords.size();i++) {
	  clone.addKeyword((Keyword)((Keyword)keywords.elementAt(i)).clone());
	}
		for(int i=0;i<genres.size();i++) {
	  clone.addGenre((Genre)((Genre)genres.elementAt(i)).clone());
	}
	clone.setCreditsList((CreditsList)getCreditsList().clone());
	for(int	i=0;i<captionLanguages.size();i++) {
	  clone.addCaptionLanguage((CaptionLanguage)((CaptionLanguage)captionLanguages.elementAt(i)).clone());
	}
  	for(int i=0;i<signLanguages.size();i++) {
	  clone.addSignLanguage((SignLanguage)((SignLanguage)signLanguages.elementAt(i)).clone());
	}
  	for(int i=0;i<promotionalInformations.size();i++) {
	  clone.addPromotionalInformation((PromotionalInformation)((PromotionalInformation)promotionalInformations.elementAt(i)).clone());
	}
	return clone;
  }


}
