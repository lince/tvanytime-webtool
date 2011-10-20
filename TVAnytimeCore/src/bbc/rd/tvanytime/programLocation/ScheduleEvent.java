/**
 * Copyright 2004 British Broadcasting Corporation
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


package bbc.rd.tvanytime.programLocation;

import bbc.rd.tvanytime.*;
import bbc.rd.tvanytime.util.*;
import java.util.Date;

/**
 * ScheduleEvent: Represents a TVA ScheduleEvent object.
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 * Modified T.Ferne, August 2004
 */

public class ScheduleEvent implements Cloneable
{
	private ContentReference crid;
	private ProgramURL programURL = null;
	private Date publishedStartTime = null;
	private Date publishedEndTime = null;
	private Duration publishedDuration = null;
  private InstanceMetadataId imi = null;

	// ScheduleEvent information
	private Boolean live = null;
	private Boolean repeat = null;
	private Boolean firstShowing = null;
	private Boolean lastShowing = null;
	private Boolean free = null;
	private Boolean ppv = null;

	/**
	 * Constructor for objects of class ScheduleEvent.
	 */
	public ScheduleEvent()
	{

	}

	/**
	 * Constructor for objects of class ScheduleEvent with required fields.
	 *
	 * @param crid a ContentReference object identifying this ScheduleEvent
	 */
	public ScheduleEvent(ContentReference crid)
	{
		this.crid = crid;
	}

	/**
	 * Get the CRID.
	 *
	 * @return     crid, or null if not set
	 */
	public ContentReference getCRID()
	{
		return crid;
	}

	/**
	 * Get the broadcast URL.
	 *
	 * @return     programURL, or null if not set
	 */
	public ProgramURL getProgramURL()
	{
		return programURL;
	}

	/**
	 * Get the InstanceMetadataId.
	 *
	 * @return  InstanceMetadataId, or null if not set
	 */
	public InstanceMetadataId getInstanceMetadataId()
	{
		return imi;
	}
  
	/**
	 * Get the published start time.
	 *
	 * @return     publishedStartTime, or null if not set
	 */
	public Date getPublishedStartTime()
	{
		return publishedStartTime;
	}

	/**
	 * Get the published end time.
	 *
	 * @return     publishedEndTime, or null if not set
	 */
	public Date getPublishedEndTime()
	{
		return publishedEndTime;
	}

	/**
	 * Get the published duration.
	 *
	 * @return     publishedDuration, or null if not set
	 */
	public Duration getPublishedDuration()
	{
		return publishedDuration;
	}

	/**
	 * Is this Live? (returns Boolean.TRUE, Boolean.FALSE, or null if not set).
	 *
	 * @return     live, or null if not set
	 */
	public Boolean isLive()
	{
		return live;
	}

	/**
	 * Is this a Repeat? (returns Boolean.TRUE, Boolean.FALSE, or null if not set).
	 *
	 * @return     repeat, or null if not set
	 */
	public Boolean isRepeat()
	{
		return repeat;
	}

	/**
	 * Is this the first showing? (returns Boolean.TRUE, Boolean.FALSE, or null if not set).
	 *
	 * @return     firstShowing, or null if not set
	 */
	public Boolean isFirstShowing()
	{
		return firstShowing;
	}

	/**
	 * Is this the last showing? (returns Boolean.TRUE, Boolean.FALSE, or null if not set).
	 *
	 * @return     lastShowing, or null if not set
	 */
	public Boolean isLastShowing()
	{
		return lastShowing;
	}

	/**
	 * Is this free? (returns Boolean.TRUE, Boolean.FALSE, or null if not set).
	 *
	 * @return     free, or null if not set
	 */
	public Boolean isFree()
	{
		return free;
	}

	/**
	 * Is this PPV? (returns Boolean.TRUE, Boolean.FALSE, or null if not set).
	 *
	 * @return     ppv, or null if not set
	 */
	public Boolean isPPV()
	{
		return ppv;
	}

	/**
	 * Set the CRID of this ScheduleEvent.
	 *
	 * @param crid the ContentReference object that identifys this ScheduleEvent
	 */
	public void setCRID(ContentReference crid)
	{
		this.crid = crid;
	}

	/**
	 * Set the broadcast URL of this ScheduleEvent.
	 *
	 * @param     programURL a programURL object
	 */
	public void setProgramURL(ProgramURL programURL)
	{
		this.programURL = programURL;
	}

	/**
	 * Set the InstanceMetadataId of this ScheduleEvent.
	 *
	 * @param  imi  An InstanceMetadataId object
	 */
	public void setInstanceMetadataId(InstanceMetadataId imi)
	{
		this.imi = imi;
	}

	/**
	 * Set the published start time of this ScheduleEvent.
	 *
	 * @param     publishedStartTime a Date object
	 */
	public void setPublishedStartTime(Date publishedStartTime)
	{
		this.publishedStartTime = publishedStartTime;
	}

	/**
	 * Set the published end time of this ScheduleEvent.
	 *
	 * @param     publishedEndTime a Date object
	 */
	public void setPublishedEndTime(Date publishedEndTime)
	{
		this.publishedEndTime = publishedEndTime;
	}

	/**
	 * Set the published duration of this ScheduleEvent.
	 *
	 * @param     publishedDuration the published duration, represented by bbc.rd.tvanytime.Duration
	 */
	public void setPublishedDuration(Duration publishedDuration)
	{
		this.publishedDuration = publishedDuration;
	}

	/**
	 * Sets whether this ScheduleEvent is a live showing.
	 *
	 * @param live is this program a live showing?
	 */
	public void setLive(Boolean live)
	{
		this.live = live;
	}

	/**
	 * Sets whether this ScheduleEvent is a repeat showing.
	 *
	 * @param  	repeat   is this program a repeat showing?
	 */
	public void setRepeat(Boolean repeat)
	{
		this.repeat = repeat;
	}

	/**
	 * Sets whether this ScheduleEvent is the first showing.
	 *
	 * @param  	firstShowing   is this program the first showing?
	 */
	public void setFirstShowing(Boolean firstShowing)
	{
		this.firstShowing = firstShowing;
	}

	/**
	 * Sets whether this ScheduleEvent is the last showing.
	 *
	 * @param   lastShowing  is this program the last showing?
	 */
	public void setLastShowing(Boolean lastShowing)
	{
		this.lastShowing = lastShowing;
	}

	/**
	 * Sets whether this ScheduleEvent is a free showing.
	 *
	 * @param  	free   is this program a free showing?
	 */
	public void setFree(Boolean free)
	{
		this.free = free;
	}

	/**
	 * Sets whether this ScheduleEvent is a pay-per-view showing.
	 *
	 * @param   ppv  is this program a pay-per-view showing?
	 */
	public void setPPV(Boolean ppv)
	{
		this.ppv = ppv;
	}

	/**
	 * Returns XML representation of this object.
	 *
	 * @return  XML representation of this object.
	 */
	public String toXML()
	{
		return toXML(0);
	}

	/**
	 * Returns XML representation of this table.
	 *
	 * @param  indent number of tabs to put before the string.
	 * @return  XML representation of this table.
	 */
	public String toXML(int indent) {
		StringBuffer xmlBuf = new StringBuffer();
		//String xml = "";

		synchronized (xmlBuf) {
			for (int i = 0; i < indent; i++) {
				xmlBuf.append("\t");
				//xml += "\t";
			}
			xmlBuf.append("<ScheduleEvent>\n");
			//xml += "<ScheduleEvent>\n";

			indent++;

			if (crid != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<Program crid=\"");
				xmlBuf.append(crid.getCRID());
				xmlBuf.append("\"/>\n");
				//xml = xml + "<Program crid=\""+crid.getCRID()+"\"/>\n";
			}

			if (programURL != null) {
				xmlBuf.append(programURL.toXML(indent));
				xmlBuf.append("\n");
				//xml = xml + programURL.toXML(indent) + "\n";
			}

			if (imi != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<InstanceMetadataId>");
				xmlBuf.append(imi.getInstanceMetadataId());
				xmlBuf.append("</InstanceMetadataId>\n");
				//xml = xml +
				// "<InstanceMetadataId>"+imi.getInstanceMetadataId()+"</InstanceMetadataId>\n";
			}

			if (publishedStartTime != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<PublishedStartTime>");
				xmlBuf
						.append(TimeToolbox
								.makeTVATimeString(publishedStartTime));
				xmlBuf.append("</PublishedStartTime>\n");
				//xml = xml + "<PublishedStartTime>" +
				// TimeToolbox.makeTVATimeString(publishedStartTime) +
				// "</PublishedStartTime>\n";
			}

			if (publishedEndTime != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<PublishedEndTime>");
				xmlBuf.append(TimeToolbox.makeTVATimeString(publishedEndTime));
				xmlBuf.append("</PublishedEndTime>\n");
				//xml = xml + "<PublishedEndTime>" +
				// TimeToolbox.makeTVATimeString(publishedEndTime) +
				// "</PublishedEndTime>\n";
			}

			if (publishedDuration != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<PublishedDuration>");
				xmlBuf.append(publishedDuration.getDurationAsString());
				xmlBuf.append("</PublishedDuration>\n");
				//xml += "<PublishedDuration>"+
				// publishedDuration.getDurationAsString() +
				// "</PublishedDuration>\n";
			}

			if (live != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<Live value=\"");
				xmlBuf.append(live);
				xmlBuf.append("\"/>\n");
				//xml += "<Live value=\"" + live + "\"/>\n";
			}

			if (repeat != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<Repeat value=\"");
				xmlBuf.append(repeat);
				xmlBuf.append("\"/>\n");
				//xml += "<Repeat value=\"" + repeat + "\"/>\n";
			}

			if (firstShowing != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<FirstShowing value=\"");
				xmlBuf.append(firstShowing);
				xmlBuf.append("\"/>\n");
				//xml += "<FirstShowing value=\"" + firstShowing + "\"/>\n";
			}

			if (lastShowing != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<LastShowing value=\"");
				xmlBuf.append(lastShowing);
				xmlBuf.append("\"/>\n");
				//xml += "<LastShowing value=\"" + lastShowing + "\"/>\n";
			}

			if (free != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<Free value=\"");
				xmlBuf.append(free);
				xmlBuf.append("\"/>\n");
				//xml += "<Free value=\"" + free + "\"/>\n";
			}

			if (ppv != null) {
				for (int i = 0; i < indent; i++) {
					xmlBuf.append("\t");
					//xml += "\t";
				}
				xmlBuf.append("<PayPerView value=\"");
				xmlBuf.append(ppv);
				xmlBuf.append("\"/>\n");
				//xml += "<PayPerView value=\"" + ppv + "\"/>\n";
			}

			for (int i = 0; i < indent - 1; i++) {
				xmlBuf.append("\t");
				//xml += "\t";
			}
			xmlBuf.append("</ScheduleEvent>");
			//xml += "</ScheduleEvent>";

			return xmlBuf.toString();
		}
	}


	/**
	 * Returns string representation of this object.
	 * 
	 * @return string representation of this object.
	 */
	public String toString()
	{
		return toString(0);
	}

	/**
	 * Returns string representation of this table.
	 * 
	 * @param indent
	 *            number of tabs to put before the string.
	 * @return string representation of this table.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "ScheduleEvent: \n";

		indent++;

		sysOut += crid.toString(indent) + "\n";
		if (programURL != null) sysOut += programURL.toString(indent) + "\n";
    if (imi != null) sysOut += imi.toString(indent) + "\n";

		if(publishedStartTime != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "publishedStartTime: " + publishedStartTime.toString() + "\n";
		}

		if(publishedEndTime != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "publishedEndTime: " + publishedEndTime.toString() + "\n";
		}

		if(publishedDuration != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			// note Duration's toString already prints "Duration: ", so do this
			// as a fix to get "publishedDuration: " (Yuck, but it's difficult
			// to remain
			// consistant here!)
			sysOut += "published"+ publishedDuration.toString() + "\n";
		}

		if(live != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "live = " + live + "\n";
		}

		if(repeat != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "repeat = " + repeat + "\n";
		}

		if(firstShowing != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "firstShowing = " + firstShowing + "\n";
		}

		if(lastShowing != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "lastShowing = " + lastShowing + "\n";
		}

		if(free != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "free = " + free + "\n";
		}

		if(ppv != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "ppv = " + ppv + "\n";
		}

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    ScheduleEvent clone = new ScheduleEvent();
    if (crid != null) clone.setCRID((ContentReference)crid.clone());
    if (programURL != null) clone.setProgramURL((ProgramURL)programURL.clone());
    if (imi != null) clone.setInstanceMetadataId((InstanceMetadataId)imi.clone());        
    if (publishedStartTime != null) clone.setPublishedStartTime(new Date(publishedStartTime.getTime()));
    if (publishedEndTime != null) clone.setPublishedEndTime(new Date(publishedEndTime.getTime()));
    if (publishedDuration != null) clone.setPublishedDuration((Duration)publishedDuration.clone());
    if (live != null) clone.setLive(new Boolean(live.booleanValue()));
    if (repeat != null) clone.setRepeat(new Boolean(repeat.booleanValue()));
    if (firstShowing != null) clone.setFirstShowing(new Boolean(firstShowing.booleanValue()));
    if (lastShowing != null) clone.setLastShowing(new Boolean(lastShowing.booleanValue()));
    if (free != null) clone.setFree(new Boolean(free.booleanValue()));
    if (ppv != null) clone.setPPV(new Boolean(ppv.booleanValue()));
    return clone;
  }

}
