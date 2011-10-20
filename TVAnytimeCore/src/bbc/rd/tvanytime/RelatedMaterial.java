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

import bbc.rd.mpeg7.*;
import bbc.rd.tvanytime.util.*;
import bbc.rd.tvanytime.segmentInformation.*;

/**
 * RelatedMaterial: Represents a RelatedMaterial object.
 *
 * @author Tim Sargeant, BBC Research & Development, September 2002
 * Modified Tristan Ferne, March 2005
 * @version 1.0
 */
public class RelatedMaterial implements Cloneable
{

	/**
   * Locator for this related material. Either this or segmentReference is set. 
   */
	private MPEG7MediaLocator locator;
	/**
	 * Segment reference for this related material. Either this or locator is set.
	 */
	private SegmentReference segmentReference;
	
	/**
   * Locator for current content, to which this description is associated.
   * E.g. the trailer.
   */
	private MPEG7MediaLocator sourceLocator;

  /**
   * Provides additional information about the link, which can be used as an
   * additional attractor.
   */
  private String promotionalText = "";

  /**
   * Specifies the nature of the relationship between the described AV content
   * and the related media assets. This is a CS of the form:
   * "urn:tva:metadata:cs:HowRelatedCS:2002:x"
   */
  private String howRelatedHREF = "";
  /**
   * Specifies the nature of the relationship between the described AV content
   * and the related media assets. This is the final numerical value of the CS.
   */
  private String howRelated = "";
  /**
   * The name element of the HowRelated element.
   */
  private String name = "";
  
  /**
   * The form of the HowRelated HREF string.
   */
  private static final String hrefCS = "urn:tva:metadata:cs:HowRelatedCS:2002:";

	/**
	 * Constructor for objects of type RelatedMaterial
	 */
	public RelatedMaterial()
	{

	}

	/**
	 * Constructor for objects of type RelatedMaterial with required fields
	 *
	 * @param locator the MediaLocator for this related material.
	 */
	public RelatedMaterial(MPEG7MediaLocator locator)
	{
		setMediaLocator(locator);
	}

	/**
	 * Constructor for objects of type RelatedMaterial with required fields
	 *
	 * @param segmentReference the SegmentReference for this related material.
	 */
	public RelatedMaterial(SegmentReference segmentReference)
	{
		setSegmentReference(segmentReference);
	}	
	
 	/**
	 * Set the MediaLocator for this RelatedMaterial object.
	 *
	 * @param locator the MediaLocator for this related material.
	 */
  public void setMediaLocator(MPEG7MediaLocator locator) {
		this.locator = locator;
		this.segmentReference = null; // Choice
  }

 	/**
	 * Set the SegmentReference for this RelatedMaterial object.
	 *
	 * @param segmentReference the SegmentReference for this related material.
	 */
  public void setSegmentReference(SegmentReference segmentReference) {
		this.segmentReference = segmentReference;
		this.locator = null; // Choice
  }

  
 	/**
	 * Return the MediaLocator for this RelatedMaterial object.
	 *
	 * @return  MediaLocator for this RelatedMaterial object.
	 */
  public MPEG7MediaLocator getMediaLocator() {
    return locator;
  }

 	/**
	 * Return the SegmentReference for this RelatedMaterial object.
	 *
	 * @return  SegmentReference for this RelatedMaterial object.
	 */
  public SegmentReference getSegmentReference() {
    return segmentReference;
  }
  
 	/**
	 * Set the SourceMediaLocator for this RelatedMaterial object.
	 *
	 * @param  locator  The MediaLocator for the source content, to which this
   * related material is attached.
	 */
  public void setSourceMediaLocator(MPEG7MediaLocator sourceLocator) {
		this.sourceLocator = sourceLocator;
  }

 	/**
	 * Return the MediaLocator for this RelatedMaterial object.
	 *
	 * @return  The MediaLocator for the source content, to which this
   * related material is attached.
	 */
  public MPEG7MediaLocator getSourceMediaLocator() {
    return sourceLocator;
  }

 	/**
	 * Set the PromotionalText for this RelatedMaterial object.
	 *
	 * @param  promotionalText  Additional information about the link, which can
   * be used as an additional attractor.
	 */
  public void setPromotionalText(String promotionalText) {
		this.promotionalText = promotionalText;
  }

 	/**
	 * Return the PromotionalText for this RelatedMaterial object.
	 *
	 * @return  Additional information about the link, which can be used as an
   * additional attractor.
	 */
  public String getPromotionalText() {
    return promotionalText;
  }

 	/**
 	 * Sets the HowRelated CS field.
   * This specifies the nature of the relationship between the described AV content
   * and the related media assets. This is a CS of the form:
   * "urn:tva:metadata:cs:HowRelatedCS:2002:x"
 	 *
 	 * @param 	howRelated		The CS HREF.
 	 * @throws	TVAnytimeException if the href string is invalid.
 	 */
 	public void setHowRelatedHREF(String howRelatedHREF) throws TVAnytimeException {
    if (howRelatedHREF.startsWith(hrefCS)) {
      // Valid, so set href and extract numerical value
      this.howRelatedHREF = howRelatedHREF;
      setHowRelated(howRelatedHREF.substring(hrefCS.length()));
    }
    else throw new TVAnytimeException("RelatedMaterial given invalid href for HowRelated: " + howRelatedHREF);
 	}

  /**
   * Gets the HowRelated CS field.
   * This specifies the nature of the relationship between the described AV content
   * and the related media assets. This is a CS of the form:
   * "urn:tva:metadata:cs:HowRelatedCS:2002:x"
   */
  public String getHowRelatedHREF() {
    return howRelatedHREF;
  }

  /**
   * Sets the HowRelated value.
   * Specifies the nature of the relationship between the described AV content
   * and the related media assets. This is the final numerical value of the CS.
   */
  public void setHowRelated(String howRelated) throws TVAnytimeException {
    if (TVAnytimeHowRelatedToolbox.isValid(howRelated)) {
      // Valid so set number and construct href
      this.howRelated = howRelated;
      this.howRelatedHREF = hrefCS + howRelated;
    }
    else throw new TVAnytimeException("RelatedMaterial given invalid value for HowRelated: " + howRelated);
  }

  /**
   * Gets the HowRelated value.
   * Specifies the nature of the relationship between the described AV content
   * and the related media assets. This is the final numerical value of the CS.
   */
  public String getHowRelated() {
    return howRelated;
  }

  /**
   * Set the Name for the HowRelated item.
   * 
   * @param name The name.
   */
  public void setName(String name) {
  	this.name = name;
  }
  
  /**
   * Get the Name for the HowRelated item.
   * 
   * @return The name.
   */
  public String getName() {
  	return name;
  }

	/**
	 * Return a XML representation of this object.
	 *
	 * @return  XML representation of this object.
	 */
	public String toXML()
	{
		return toXML(0);
	}

	/**
	 * Return a XML representation of this object.
	 * 
	 * @param indent
	 *            number of tabs to put before the string.
	 * @return XML representation of this object.
	 */
	public String toXML(int indent) {
		StringBuffer xmlBuf = new StringBuffer();

		synchronized (xmlBuf) {
			for (int i = 0; i < indent; i++) {
				xmlBuf.append("\t");
			}
			xmlBuf.append("<RelatedMaterial>");
			indent++;

			// HowRelated
			if (howRelatedHREF != null) {
				xmlBuf.append("\n");
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
				}
				xmlBuf.append("<HowRelated href=\"");
				xmlBuf.append(howRelatedHREF);
				xmlBuf.append("\">");
				// Name
				if (name.length() > 0) {
					xmlBuf.append("\n");
					for (int j = 0; j < indent + 1; j++) {
						xmlBuf.append("\t");
					}

					xmlBuf.append("<Name>");
					xmlBuf.append("<![CDATA[");
					xmlBuf.append(name);
					xmlBuf.append("]]>");
					xmlBuf.append("</Name>");
				}
				xmlBuf.append("\n");
				for (int j = 0; j < indent; j++) {
					xmlBuf.append("\t");
				}
				xmlBuf.append("</HowRelated>");
			}

			// Format (unused)

			// MediaLocator
			if (locator != null) {
				xmlBuf.append("\n");
				xmlBuf.append(locator.toXML(indent));
			}

			// SegmentReference
			if (segmentReference != null) {
				xmlBuf.append("\n");
				xmlBuf.append(segmentReference.toXML(indent));
			}			
			
			// Promotional text
			if (promotionalText.length() > 0) {
				xmlBuf.append("\n");
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
				}
				xmlBuf.append("<PromotionalText><![CDATA[" + promotionalText
						+ "]]></PromotionalText>");
			}

			// SourceMediaLocator
			if (sourceLocator != null) {
				xmlBuf.append("\n");
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
				}
				xmlBuf.append("<SourceMediaLocator>");
				if (sourceLocator.getMediaURI() != null) {
					xmlBuf.append("\n");
					for (int i = 0; i < indent + 1; i++) {
						xmlBuf.append("\t");
					}
					xmlBuf.append("<mpeg7:MediaUri>"
							+ sourceLocator.getMediaURI().getURI()
							+ "</mpeg7:MediaUri>");
				}
				xmlBuf.append("\n");
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
				}
				xmlBuf.append("</SourceMediaLocator>");
			}

			// Closing tag
			indent--;
			xmlBuf.append("\n");
			for (int i = 0; i < indent; i++) {
				xmlBuf.append("\t");
			}
			xmlBuf.append("</RelatedMaterial>");
			return xmlBuf.toString();
		}
	}
            


	/**
	 * Return a string representation of this object.
	 * 
	 * @return string representation of this object.
	 */
	public String toString()
	{
		return toString(0);
	}

	/**
	 * Return a string representation of this object.
	 * 
	 * @param indent
	 *            number of tabs to put before the string.
	 * @return string representation of this object.
	 */
	public String toString(int indent) {
		String sysOut = "";

		for (int i=0;i<indent;i++) {
			sysOut += "\t";
    }
		sysOut += "RelatedMaterial";

    indent++;
    // HowRelated
    if (howRelatedHREF != null) {
      sysOut += "\n";
      for (int i=0;i<indent;i++) {
        sysOut += "\t";
      }
      sysOut = sysOut + "HowRelated: href="+howRelatedHREF+" name="+name;
 		}

    // MediaLocator
		if (locator != null) {
      sysOut += "\n";
      sysOut += locator.toString(indent+1);
    }
    // SegmentReference
		else if (segmentReference != null) {
      sysOut += "\n";
      sysOut += segmentReference.toString(indent+1);
    }
    else {
      sysOut += "\n";
      for (int i=0;i<indent;i++) {
        sysOut += "\t";
      }
      sysOut += "<Error: missing locator or segment reference>";
    }

    // Promotional text
    if (promotionalText.length() > 0) {
      sysOut += "\n";
      for (int i=0;i<indent;i++) {
        sysOut += "\t";
      }
      sysOut = sysOut + "PromotionalText: "+promotionalText;
    }

    // SourceMediaLocator
    if (sourceLocator != null) {
      sysOut += "\n";
      for (int i=0;i<indent;i++) {
        sysOut += "\t";
      }
  		sysOut += "SourceMediaLocator: ";
      if (sourceLocator.getMediaURI() != null) {
        sysOut = sysOut + "MediaUri = " + sourceLocator.getMediaURI().getURI();
      }
    }

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    RelatedMaterial clone = new RelatedMaterial();
    if (locator != null) clone.setMediaLocator((MPEG7MediaLocator)locator.clone());
    if (segmentReference != null) clone.setSegmentReference((SegmentReference)locator.clone());    
    if (sourceLocator != null) clone.setSourceMediaLocator((MPEG7MediaLocator)sourceLocator.clone());
    clone.setPromotionalText(new String(promotionalText));
    clone.setName(new String(name));
    try {
      clone.setHowRelatedHREF(new String(howRelatedHREF));
      clone.setHowRelated(new String(howRelated));
    } catch (TVAnytimeException tvae) { System.out.println("Error cloning RelatedMaterial: "+tvae.getMessage()); }
    return clone;
  }
}
