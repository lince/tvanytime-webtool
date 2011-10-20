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


package bbc.rd.tvanytime.programLocation;

import bbc.rd.tvanytime.util.TimeToolbox;
import java.util.*;


/**
 * Schedule: Represents the a Schedule object
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * 
 * @version 1.0
 * 
 * Modified 26/4/04 T.Ferne: Added start and end times.
 */

public class Schedule implements Cloneable
{
	private String serviceID;  // this needs to be a tva:IDRefType
	private Vector scheduleEvents;
  private Date startTime = null;
  private Date endTime = null;

	/**
	 * Constructor for objects of type Schedule
	 *
	 */
	public Schedule()
	{
		scheduleEvents = new Vector(1,1);
	}

	/**
	 * Constructor for objects of type Schedule with required fields
	 *
	 * @param     serviceID a tva:IDRefType
	 * @param	  event the Event object
	 */
	public Schedule(String serviceID, ScheduleEvent scheduleEvent)
	{
		this(serviceID);
		scheduleEvents.addElement(scheduleEvent);
	}

	/**
	 * Constructor for objects of type Schedule with required fields
	 *
	 * @param     serviceID a tva:IDRefType
	 */
	public Schedule(String serviceID) {
    this();
    this.serviceID = serviceID;    
  }

	/**
	 * Constructor for objects of type Schedule with required fields
	 *
	 * @param     serviceID a tva:IDRefType
	 */
	public Schedule(String serviceID, Date startTime, Date endTime) {
    this(serviceID);
    setStart(startTime);
    setEnd(endTime);
  }
  
	/**
	 * Gets the service ID of this Schedule
	 *
	 * @return     serviceID
	 */
	public String getServiceID()
	{
		return serviceID;
	}

  /**
   * Get start date for this schedule.
   * 
   * @return  Start date.
   */
  public Date getStart() {
    return startTime;
  }

  /**
   * Get end date for this schedule.
   * 
   * @return  End date.
   */
  public Date getEnd() {
    return endTime;
  }
  
	/**
	 * Get the number of events in this Schedule
	 *
	 * @return     number of event objects
	 */
	public int getNumScheduleEvents()
	{
		return scheduleEvents.size();
	}

	/**
	 * Gets an Event object
	 *
	 * @param     index  the index to the ScheduleEvent object
	 * @return     the ScheduleEvent object
	 */
	public ScheduleEvent getScheduleEvent(int index)
	{
		return (ScheduleEvent)scheduleEvents.elementAt(index);
	}

	/**
	 * Adds an ScheduleEvent object
	 *
	 * @param    event  the ScheduleEvent object
	 */
	public void addScheduleEvent(ScheduleEvent scheduleEvent)
	{
		scheduleEvents.addElement(scheduleEvent);
	}

	/**
	 * Removes a ScheduleEvent object
	 *
	 * @param  scheduleEvent  The ScheduleEvent object to be removed.
	 */
	public void removeScheduleEvent(ScheduleEvent scheduleEvent)
	{
		scheduleEvents.removeElement(scheduleEvent);
	}


	/**
	 * Sets the ServiceID
	 *
	 * @param     serviceID the service ID of this Schedule object
	 */
	public void setServiceID(String serviceID)
	{
		this.serviceID = serviceID;
	}
  
  /**
   * Set start date for this schedule.
   * 
   * @param  startTime  Start date.
   */
  public void setStart(Date startTime) {
    this.startTime = startTime;
  }

  /**
   * Set end date for this schedule.
   * 
   * @param  endTime  End date.
   */
  public void setEnd(Date endTime) {
    this.endTime = endTime;
  }

	/**
	 * Removes all Event objects
	 */
	public void removeAll()
	{
		scheduleEvents.removeAllElements();
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
	 * Returns XML representation of this object.
	 *
	 * @param  indent number of tabs to put before the string.
	 * @return  XML representation of this object.
	 */
	public String toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";
            
            synchronized(xmlBuf) {
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                
                xmlBuf.append("<Schedule");
                //xml += "<Schedule";
                if (serviceID!=null) {
                    xmlBuf.append(" serviceIDRef=\"");
                    xmlBuf.append(serviceID);
                    xmlBuf.append("\"");
                    //xml = xml + " serviceIDRef=\""+serviceID+"\"";
                }
                if (startTime!=null) {
                    xmlBuf.append(" start=\"");
                    xmlBuf.append(TimeToolbox.makeTVATimeString(startTime));
                    xmlBuf.append("\"");
                    //xml = xml + " start=\""+TimeToolbox.makeTVATimeString(startTime)+"\"";
                }
                if (startTime!=null) {
                    xmlBuf.append(" end=\"");
                    xmlBuf.append(TimeToolbox.makeTVATimeString(endTime));
                    xmlBuf.append("\"");
                    //xml = xml + " end=\""+TimeToolbox.makeTVATimeString(endTime)+"\"";
                }
                xmlBuf.append(">");
                //xml += ">";

                indent++;
                for (int i=0; i<scheduleEvents.size();i++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((ScheduleEvent)scheduleEvents.elementAt(i)).toXML(indent));
                    //xml = xml + "\n" + ((ScheduleEvent)scheduleEvents.elementAt(i)).toXML(indent);
                }
                xmlBuf.append("\n");
                //xml += "\n";
                for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
                xmlBuf.append("</Schedule>");
                //xml = xml + "</Schedule>";
                return xmlBuf.toString();
            }
	}


	/**
	 * Returns string representation of this object.
	 *
	 * @return  string representation of this object.
	 */
	public String toString()
	{
		return toString(0);
	}

	/**
	 * Returns string representation of this object.
	 *
	 * @param  indent number of tabs to put before the string.
	 * @return  string representation of this object.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "Schedule: \n";

		indent++;

		if (serviceID != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "serviceID = " + serviceID + "\n";
		}
		if (startTime != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "startTime = " + startTime + "\n";
		}
		if (endTime != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "endTime = " + endTime + "\n";
		}

		for (int i=0; i<scheduleEvents.size();i++)
			sysOut += ((ScheduleEvent)scheduleEvents.elementAt(i)).toString(indent);

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    Schedule clone = new Schedule();
    if (serviceID != null) clone.setServiceID(new String(serviceID));
    if (startTime != null) clone.setStart(new Date(startTime.getTime()));
    if (endTime != null) clone.setEnd(new Date(endTime.getTime()));    
    for (int i=0; i<scheduleEvents.size();i++) {
      clone.addScheduleEvent((ScheduleEvent) (((ScheduleEvent)scheduleEvents.elementAt(i)).clone()) );
    }
    return clone;
  }

}
