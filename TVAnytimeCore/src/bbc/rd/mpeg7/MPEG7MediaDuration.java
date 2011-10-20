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
 * MPEG7MediaDuration: Represents an mpeg7:MediaDuration object
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @version 1.0
 */

public class MPEG7MediaDuration implements Cloneable
{
	private long duration;

	/**
	 * Constructor for objects of type MPEG7MediaDuration
	 */
	public MPEG7MediaDuration()
	{

	}
	/**
	 * Constructor for objects of type MPEG7MediaDuration
	 */
	public MPEG7MediaDuration(long duration)
	{
		this.duration = duration;
	}

	/**
	 * Set the MPEG7MediaDuration
	 *
	 * @param      duration the MPEG7MediaDuration
	 */
	public void setTime(long duration)
	{
		this.duration = duration;
	}

	/**
	 * Get the MPEG7MediaDuration
	 *
	 * @return     duration the MPEG7MediaDuration
	 */
	public long getTime()
	{
		return duration;
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

		xml += "<mpeg7:MediaDuration>"+duration+"</mpeg7:MediaDuration>";
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

		sysOut += "\n";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "MPEG7MediaDuration: " + duration;

		return sysOut;
	}

	/**
	* Clones itself.
	* @return  A copy of itself.
	*/
	public Object clone() {
		MPEG7MediaDuration clone = new MPEG7MediaDuration();

		clone.setTime(duration);

		return clone;
	}

}

