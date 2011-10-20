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

import bbc.rd.tvanytime.*;
import bbc.rd.tvanytime.search.*;
import java.util.Vector;
import java.util.Date;

/**
 * ProgramLocationTable: Represents a table that contains Schedule
 * objects.
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 * 
 * Modified T.Ferne, BBC R&D, February 2004
 * Modified T.Ferne, BBC R&D, 26/4/04: Added getScheduleAsEvents() method.
 */

public class ProgramLocationTable implements LocationResolution, Cloneable
{
	private Vector schedules;
	private boolean scheduleObjectExists = false;

	/**
	 * Constructor for objects of type ProgramLocationTable
	 */
	public ProgramLocationTable()
	{
		schedules = new Vector(0,1);
	}

	/**
	 * Get the number of Schedule objects belonging to this ProgramLocationTable
	 *
	 * @return     the number of Schedule objects
	 */
	public int getNumSchedules()
	{
		if (scheduleObjectExists)
			return schedules.size();
		else
			return 0;
	}

	/**
	 * Gets a Schedule object <B>(note this is different to the other getSchedule() methods
	 * in this object)</B>
	 *
	 * @param    index  the index to the Schedule object
	 * @return     the Schedule object, or null if no Schedule objects exist
	 */
	public Schedule getSchedule(int index)
	{
		if (scheduleObjectExists)
			return (Schedule)schedules.elementAt(index);
		else
			return null;
	}

	/**
	 * Adds a Schedule object
	 *
	 * @param  schedule    the Schedule object
	 */
	public void addSchedule(Schedule schedule)
	{
		schedules.addElement(schedule);
		scheduleObjectExists = true;
	}

	/**
	 * Removes all Schedule objects
	 */
	public void removeAll()
	{
		schedules.removeAllElements();
		scheduleObjectExists = false;
	}

	/**
	 * Returns vector of BroadcastURLs with this CRID.
	 *
	 * @param   crid  CRID to search for.
	 * @return  Vector of BroadcastURLs for this CRID.
	 */
	public Vector resolveLeafCRID(ContentReference crid)
	{
		Vector results = new Vector(0,1);

		// for each schedule object...
		for (int i=0; i<schedules.size();i++)
		{
			Schedule tmpSchedule = (Schedule)schedules.elementAt(i);

			// for each event object...
			for (int j=0; j<tmpSchedule.getNumScheduleEvents();j++)
			{
				ScheduleEvent tmpScheduleEvent = (ScheduleEvent)tmpSchedule.getScheduleEvent(j);

				if (tmpScheduleEvent.getCRID().equals(crid))
				{
					results.addElement((ProgramURL)tmpScheduleEvent.getProgramURL());
				}
			}
		}

		return results;
	}

	/**
	 * Returns vector of BroadcastURLs with this CRID on the specified service.
	 *
	 * @param   crid  CRID to search for.
	 * @param   serviceID  The serviceID of the service we are interested in.
	 * @return  Vector of all BroadcastURLs.
	 */
	public Vector resolveLeafCRID(ContentReference crid, String serviceID)
	{
		Vector results = new Vector(0,1);

		for (int i=0; i<schedules.size();i++)
		{
			// get each schedule object
			Schedule tmpSchedule = (Schedule)schedules.elementAt(i);

			// if the serviceID matches, carry on with the search
			if (tmpSchedule.getServiceID().equals(serviceID))
			{
				for (int j=0; j<tmpSchedule.getNumScheduleEvents();j++)
				{
					// get each event object
					ScheduleEvent tmpScheduleEvent = (ScheduleEvent)tmpSchedule.getScheduleEvent(j);

					// if the crid for this event matches our search crid, add it to the results list
					if (tmpScheduleEvent.getCRID().equals(crid))
					{
						results.addElement((ProgramURL)tmpScheduleEvent.getProgramURL());
					}
				}
			}
		}

		return results;
	}

	/**
	 * Returns vector of CRIDs for all events that start at a time between the
	 * start time and the end time.
	 * <BR><B>(Note, this has different functionality to the getSchedule(int index) method in this object)</B>
	 * <BR>This implementation returns CRIDS of programmes which are showing between start and
	 * end times.  (eg. if <I>startTime = 22:04</I>, the crid of a news programme which started
	 * at 22:00 will be returned.  Similarly, the crid of programmes showing at the endTime
	 * will be returned.)
	 * <BR>This method searches on the ScheduleEvent's optional 'publishedTime'.  If publishedTime is
	 * not present for a particular CRID, the CRID will not be returned.  If the optional
	 * 'publishedDuration' is not set, it is assumed to be zero.
	 *
	 * @param   startTime   Start time of schedule.
	 * @param   endTime     End time of schedule.
	 * @return Vector of all ContentReferences in this time period.
	 */
	public Vector getSchedule(Date startTime, Date endTime)
	{
		Vector results = new Vector (0,1);

		for (int i=0; i<schedules.size();i++)
		{
			// get each schedule object
			Schedule tmpSchedule = (Schedule)schedules.elementAt(i);

			for (int j=0; j<tmpSchedule.getNumScheduleEvents();j++)
			{
				// get each event object
				ScheduleEvent tmpScheduleEvent = (ScheduleEvent)tmpSchedule.getScheduleEvent(j);

				// publishedTime is an optional attribute of ScheduleEvent, so if it's not set
				// we can't search on it!
				Date publishedTime = (Date)tmpScheduleEvent.getPublishedStartTime();
				if(publishedTime == null)
					break;

				// publishedDuration is an optional attribute of ScheduleEvent, so if it's not set
				// assume a zero length programme!
				Duration publishedDuration = (Duration)tmpScheduleEvent.getPublishedDuration();
				if(publishedDuration == null)
					publishedDuration = new Duration(0);

				Date timePlusDuration = new Date();
				timePlusDuration.setTime(publishedDuration.getDurationInMsec()
					+ publishedTime.getTime());

				// if the published start time is between our search start and end times
				// add to our list of results
				if (startTime.before(timePlusDuration) && publishedTime.before(endTime))
				{
					results.addElement(tmpScheduleEvent.getCRID());
				}
			}
		}

		return results;
	}

	/**
	 * Returns vector of CRIDs for all events that start at a time between the
	 * start time and the end time and are on the specified service.
	 * <BR><B>(Note, this has different functionality to the getSchedule(int index) method in this object)</B>
	 * <BR>This implementation returns CRIDS of programmes which are showing between start and
	 * end times.  (eg. if <I>startTime = 22:04</I>, the crid of a news programme which started
	 * at 22:00 will be returned.  Similarly, the crid of programmes showing at the endTime
	 * will be returned.)
	 * <BR>This method searches on the ScheduleEvent's optional 'publishedTime'.  If publishedTime is
	 * not present for a particular CRID, the CRID will not be returned.  If the optional
	 * 'publishedDuration' is not set, it is assumed to be zero.
	 *
	 * @param   startTime   Start time of schedule.
	 * @param   endTime     End time of schedule.
	 * @param   serviceID   The serviceID of the service we are interested in.
	 * @return Vector of all ContentReferences on this service in this time period.
	 */
	public Vector getSchedule(Date startTime, Date endTime, String serviceID)
	{
		Vector results = new Vector (0,1);

		for (int i=0; i<schedules.size();i++)
		{
			// get each schedule object
			Schedule tmpSchedule = (Schedule)schedules.elementAt(i);

			// if the serviceID matches, carry on with the search
			if (tmpSchedule.getServiceID().equals(serviceID))
			{
				for (int j=0; j<tmpSchedule.getNumScheduleEvents();j++)
				{
					// get each event object
					ScheduleEvent tmpScheduleEvent = (ScheduleEvent)tmpSchedule.getScheduleEvent(j);

					// publishedTime is an optional attribute of ScheduleEvent, so if it's not set
					// we can't search on it!
					Date publishedTime = (Date)tmpScheduleEvent.getPublishedStartTime();
					if (publishedTime == null) break;

					// publishedDuration is an optional attribute of ScheduleEvent, so if it's not set
					// assume a zero length programme!
					Duration publishedDuration = (Duration)tmpScheduleEvent.getPublishedDuration();

					if(publishedDuration == null)
						publishedDuration = new Duration(0);

					Date timePlusDuration = new Date();
					timePlusDuration.setTime(publishedDuration.getDurationInMsec()
						+ publishedTime.getTime());

					// if the published start time is between our search start and end times
					// add to our list of results
					if (startTime.before(timePlusDuration) && publishedTime.before(endTime))
					{
						results.addElement(tmpScheduleEvent.getCRID());
					}
				}
			}
		}

		return results;
	}

	/**
	 * Returns vector of ScheduleEvents for all events that start at a time between the
	 * start time and the end time and are on the specified service.
   * 
   * <BR>This is the same functionality as getSchedule(startTime, endTime, serviceID).
   * 
	 * <BR>This implementation returns CRIDS of programmes which are showing between start and
	 * end times.  (eg. if <I>startTime = 22:04</I>, the crid of a news programme which started
	 * at 22:00 will be returned.  Similarly, the crid of programmes showing at the endTime
	 * will be returned.)
	 * <BR>This method searches on the ScheduleEvent's optional 'publishedTime'.  If publishedTime is
	 * not present for a particular CRID, the CRID will not be returned.  If the optional
	 * 'publishedDuration' is not set, it is assumed to be zero.
	 *
	 * @param   startTime   Start time of schedule.
	 * @param   endTime     End time of schedule.
	 * @param   serviceID   The serviceID of the service we are interested in.
	 * @return  Vector of all ScheduleEvents on this service in this time period.
	 */
	public Vector getScheduleAsEvents(Date startTime, Date endTime, String serviceID)
	{
		Vector results = new Vector();
    ScheduleEvent tmpScheduleEvent;
    Schedule tmpSchedule;
    Date publishedTime, timePlusDuration;
    Duration publishedDuration;
    
		for (int i=0; i<schedules.size();i++) {
			// get each schedule object
			tmpSchedule = (Schedule)schedules.elementAt(i);

			// if the serviceID matches, carry on with the search
			if (tmpSchedule.getServiceID().equals(serviceID))	{
				for (int j=0; j<tmpSchedule.getNumScheduleEvents();j++)	{
					// get each event object
					tmpScheduleEvent = (ScheduleEvent)tmpSchedule.getScheduleEvent(j);

					// publishedTime is an optional attribute of ScheduleEvent, so if it's not set
					// we can't search on it!
					publishedTime = (Date)tmpScheduleEvent.getPublishedStartTime();
					if (publishedTime == null) break;

					// publishedDuration is an optional attribute of ScheduleEvent, so if it's not set
					// assume a zero length programme!
					publishedDuration = (Duration)tmpScheduleEvent.getPublishedDuration();

					if(publishedDuration == null)	publishedDuration = new Duration(0);

					timePlusDuration = new Date();
					timePlusDuration.setTime(publishedDuration.getDurationInMsec() + publishedTime.getTime());

					// if the published start time is between our search start and end times
					// add to our list of results
					if (startTime.before(timePlusDuration) && publishedTime.before(endTime)) {
						results.addElement(tmpScheduleEvent);
					}
				}
			}
		}
		return results;
	}


  /**
   * Returns all ScheduleEvents that match this CRID.
   * 
   * Note: This is a very slow implementation that searches through all the events
   * in all the schedules sequentially. Could be made more efficient by caching
   * ScheduleEvents in a hashtable.
   * 
   * @param  crid  CRID of programme to search for.
   * @return  A Vector of ScheduleEvents that match the CRID.
   * @throws  SearchInterfaceNotSupportedException	 If	the	implementation doesn't support this	function.
   * 
   */
  public Vector getScheduleEvent(ContentReference crid) throws SearchInterfaceNotSupportedException {
    Vector temp = new Vector();
    Schedule schedule;
    for (int ct=0; ct<schedules.size(); ct++) {
      schedule = (Schedule)schedules.elementAt(ct);
      for (int ct2=0; ct2<schedule.getNumScheduleEvents(); ct2++) {
        if (schedule.getScheduleEvent(ct2).getCRID().equals(crid)) {
          temp.addElement(schedule.getScheduleEvent(ct2));
        }
      }
    }
    return temp;
  }

  /**
   * Returns XML representation of this table.
   *
   * @return  XML representation of this table.
   */
  public String toXML() {
    return toXML(0);
  }

  /**
   * Returns XML representation of this table.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  XML representation of the table.
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
            xmlBuf.append("<ProgramLocationTable>");
            //xml += "<ProgramLocationTable>";

            // Indent and call children
            indent++;

            for (int ct=0; ct<schedules.size(); ct++) {
                xmlBuf.append("\n");
                xmlBuf.append(((Schedule)schedules.elementAt(ct)).toXML(indent));
                //xml = xml + "\n" + ((Schedule)schedules.elementAt(ct)).toXML(indent);
            }   
            xmlBuf.append("\n");
            //xml += "\n";
            for (int i=0;i<indent-1;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }
            xmlBuf.append("</ProgramLocationTable>");
            //xml += "</ProgramLocationTable>";
            return xmlBuf.toString();
        }
    }


	/**
	 * Returns string representation of this table.
	 *
	 * @return  string representation of this table.
	 */
	public String toString()
	{
		return toString(0);
	}

	/**
	 * Returns string representation of this table.
	 *
	 * @param  indent number of tabs to put before the string.
	 * @return  string representation of this table.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "ProgramLocationTable: \n";

		indent++;

		for (int i=0; i<schedules.size();i++)
			sysOut += ((Schedule)schedules.elementAt(i)).toString(indent);

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    ProgramLocationTable clone = new ProgramLocationTable();
		for (int i=0; i<schedules.size();i++) {
      clone.addSchedule((Schedule) (((Schedule)schedules.elementAt(i)).clone()) );
    }
    return clone;
  }

}

