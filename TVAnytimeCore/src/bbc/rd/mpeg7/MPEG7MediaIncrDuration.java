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


package bbc.rd.mpeg7;

/**
 * MPEG7MediaIncrDuration: Represents an mpeg7:MediaIncrDuration object
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 */

public class MPEG7MediaIncrDuration
{
	private long duration;

	private String timeUnit;
	private boolean timeUnitSet = false;

	/**
	 * Constructor for objects of type MPEG7MediaIncrDuration
	 */
	public MPEG7MediaIncrDuration()
	{

	}
	/**
	 * Constructor for objects of type MPEG7MediaIncrDuration
	 */
	public MPEG7MediaIncrDuration(long duration)
	{
		this.duration = duration;
	}

	/**
	 * Set the MPEG7MediaIncrDuration
	 *
	 * @param      duration the MPEG7MediaIncrDuration
	 */
	public void setTime(long duration)
	{
		this.duration = duration;
	}

	/**
	 * Get the MPEG7MediaIncrDuration
	 *
	 * @return     duration the MPEG7MediaIncrDuration
	 */
	public long getTime()
	{
		return duration;
	}

	/**
	 * setTimeUnit - sets the timeUnit
	 *
	 * @param     timeUnit the time unit
	 */
	public void setTimeUnit(String timeUnit)
	{
		this.timeUnit = timeUnit;
		timeUnitSet = true;
	}

	/**
	 * getTimeUnit - gets the timeUnit
	 *
	 * @return      the timeUnit
	 */
	public String getTimeUnit()
	{
		return timeUnit;
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
	 * @param	indent number of tabs to put before the string.
	 * @return  XML representation of this object.
	 */
	public String toXML(int indent)
	{
		String xml = "";

		// Do tabs
		for (int i=0;i<indent;i++) {
			xml += "\t";
		}

		if (timeUnitSet)
			xml += "<mpeg7:MediaIncrDuration mediaTimeUnit=\""+timeUnit+"\">"+duration+"</mpeg7:MediaIncrDuration>";
		else
			xml += "<mpeg7:MediaIncrDuration>"+duration+"</mpeg7:MediaIncrDuration>";

		return xml;
	}

	/* Returns string representation of this object.
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
	 * @param   number of tabs to put before the string.
	 * @return  string representation of this object.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "MPEG7MediaIncrDuration: " + duration;

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		if(timeUnitSet)
			sysOut += "\n\tTimeUnit: " + timeUnit;

		return sysOut;
	}

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		MPEG7MediaIncrDuration clone = new MPEG7MediaIncrDuration();

		clone.setTime(duration);

		if (timeUnitSet)
			clone.setTimeUnit(timeUnit);

		return clone;
	}
}

