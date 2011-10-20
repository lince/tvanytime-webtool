/**
 * Copyright 2005 British Broadcasting Corporation
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

import bbc.rd.tvanytime.TVAnytimeException;

/**
 * SegmentReference: Represents a SegmentReference object.
 *
 * @author Tristan Ferne, BBC Research & Development, March 2005
 * @version 1.0
 */
public class SegmentReference {

	/**
	 * SegmentReference for segment.
	 */
	public static final int SEGMENT = 0; 
	/**
	 * SegmentReference for segment group.
	 */
	public static final int SEGMENT_GROUP = 1;
	
	
	/**
	 * Identifies the segment or segmentGroup, depending on segmentType.
	 */
	private String ref;
	
	/**
	 * Type of segmentRef - either SegmentReference.SEGMENT or SegmentReference.SEGMENT_GROUP
	 */
	private int segmentType = SEGMENT;
	
	/**
	 * Constructor.
	 *
	 */
	public SegmentReference() {		
	}

	/**
	 * Constructor.
	 * 
	 * @param segmentType Segment type
	 * @param ref Segment ref
	 * @throws TVAnytimeException if invalid segment type.
	 */
	public SegmentReference(int segmentType, String ref) throws TVAnytimeException {
		setSegmentType(segmentType);
		setRef(ref);
	}
	
	
	/**
	 * Set segment type - either SegmentReference.SEGMENT or SegmentReference.SEGMENT_GROUP.
	 * 
	 * @param segmentType SegmentReference.SEGMENT or SegmentReference.SEGMENT_GROUP
	 * @throws TVAnytimeException if invalid segment type.
	 */
	public void setSegmentType(int segmentType) throws TVAnytimeException {
    if (segmentType==SEGMENT || segmentType==SEGMENT_GROUP) {
  		this.segmentType = segmentType;
    }
    else throw new TVAnytimeException("SegmentReference: "+segmentType+" is not a valid SegmentReference type");
		
	}
	
	/**
	 * Get segment type  - either SegmentReference.SEGMENT or SegmentReference.SEGMENT_GROUP.
	 * 
	 * @return segment type.
	 */
	public int getSegmentType() {
		return segmentType;
	}
	
	/**
	 * Set segment ref. Either a segmentId or a groupId.
	 * 
	 * @param ref Segment ref.
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	/**
	 * Return segment ref. Either a segmentId or a groupId.
	 * 
	 * @return Segment ref
	 */
	public String getRef() {
		return ref;
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
	 * @param indent number of tabs to put before the string.
	 * @return XML representation of this object.
	 */
	public String toXML(int indent) {
		StringBuffer xmlBuf = new StringBuffer();

		synchronized (xmlBuf) {
			for (int i = 0; i < indent; i++) {
				xmlBuf.append("\t");
			}
			xmlBuf.append("<SegmentReference segmentType=");
			if (segmentType == SEGMENT) xmlBuf.append("\"segment\"");
			else if (segmentType == SEGMENT_GROUP) xmlBuf.append("\"segmentgroup\"");			
			xmlBuf.append(" ref=\""+ref+"\"/>");

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
	 * @param indent number of tabs to put before the string.
	 * @return string representation of this object.
	 */
	public String toString(int indent) {
		String sysOut = "";

		for (int i=0;i<indent;i++) {
			sysOut += "\t";
    }
		sysOut += "SegmentReference: segmentType=";
		if (segmentType == SEGMENT) sysOut += "segment";
		else if (segmentType == SEGMENT_GROUP) sysOut += "segmentgroup";
		sysOut += " ref="+ref;

		return sysOut;
	}
	
  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    SegmentReference clone = new SegmentReference();
    try {
    	clone.setSegmentType(segmentType);
    } catch (TVAnytimeException tvae) { System.out.println("Error cloning SegmentReference: "+tvae.getMessage()); }    	
    clone.setRef(new String(ref));
    return clone;
  }	
	
}
