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

import java.lang.Math;

/**
 * Duration: Represents a duration (of the form "PT00H25M30S");
 *
 * @author Tim Sargeant, BBC Research & Development, April 2002
 * @author Tristan Ferne, BBC Research & Development, September 2003
 * @version 1.0
 */

public class Duration
{
	private String durationAsString;
	private long durationInMsec;
  private int days = 0;
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;

	/**
	 * Constructor for objects of type Duration represented in String of the form (e.g. "PT00H25M30S")
	 */
	public Duration()
	{

	}

	/**
	 * Constructor for objects of type Duration
	 *
	 * @param duration string representation of the duration (e.g. "PT00H25M30S")
	 * @throws	TVAnytimeException thrown when the duration string is of an unexpected format
	 */
	public Duration(String duration) throws TVAnytimeException
	{
		setDuration(duration);
	}

	/**
	 * Constructor for objects of type Duration
	 *
	 * @param duration represented by number of milliseconds
	 */
	public Duration(long duration)
	{
		setDuration(duration);
	}

	/**
	 * Get the duration
	 *
	 * @return     the String representation of the duration (e.g. "PT00H25M30S")
	 */
	public String getDurationAsString()
	{
		return durationAsString;
	}

	/**
	 * Get the duration in milliseconds
	 *
	 * @return     the duration in ms
	 */
	public long getDurationInMsec()
	{
		return durationInMsec;
	}

	/**
	 * Get the days part of the duration string.
	 *
	 * @return     the duration days part
	 */
	public int getDays()
	{
		return days;
	}


	/**
	 * Get the hours part of the hh:mm:ss
	 *
	 * @return     the duration hours part of the hh:mm:ss
	 */
	public int getHours()
	{
		return hours;
	}

	/**
	 * Get the minutes part of the hh:mm:ss
	 *
	 * @return     the duration minutes part of the hh:mm:ss
	 */
	public int getMinutes()
	{
		return minutes;
	}

	/**
	 * Get the seconds part of the hh:mm:ss
	 *
	 * @return     the duration seconds part of the hh:mm:ss
	 */
	public int getSeconds()
	{
		return seconds;
	}

	/**
	 * Set the duration
	 *
	 * @param	duration	string representation of the duration (e.g. "PT00H25M30S")
	 */
	public void setDuration(String duration) throws TVAnytimeException
	{
    int previousIndex = 0;
    int currentIndex = 0;
		this.durationAsString = duration;

		// check to see if the string starts with 'PT' (- it should!)
		if(!durationAsString.startsWith("P")) {
			throw new TVAnytimeException ("Duration: duration as string must start with 'P'.");
		}
    else previousIndex = durationAsString.indexOf("P");

    // Get position of T
    currentIndex = durationAsString.indexOf("T");
    if (currentIndex > -1) {
      // Check if any digits between P and T
      if (currentIndex-previousIndex>1) {
        // Set days
        try {
          days = Integer.parseInt(durationAsString.substring(previousIndex+1,currentIndex));
        } catch (NumberFormatException e) {
          throw new TVAnytimeException ("Duration: problem parsing duration days.");
        }
        // check for valid day range
        if (days < 0) {
          throw new TVAnytimeException ("Duration: days must be greater than 0.");
        }      
      }
      previousIndex = currentIndex;
    }

    // Get position of H
    currentIndex = durationAsString.indexOf("H");
    if (currentIndex > -1) {
      // Check if any digits between T and H
      if (currentIndex-previousIndex>1) {
        // Set hours
        try {
          hours = Integer.parseInt(durationAsString.substring(previousIndex+1,currentIndex));
        } catch (NumberFormatException e) {
          throw new TVAnytimeException ("Duration: problem parsing duration hours.");
        }
        // check for valid range
        /*
        if (hours < 0 || hours > 23) {
          throw new TVAnytimeException ("Duration: hours must be between 0 and 23.");
        }
        */
      }
      previousIndex = currentIndex;
    }

    // Get position of M
    currentIndex = durationAsString.indexOf("M");
    if (currentIndex > -1) {
      // Check if any digits between H and M
      if (currentIndex-previousIndex>1) {
        // Set minutes
        try {
          minutes = Integer.parseInt(durationAsString.substring(previousIndex+1,currentIndex));
        } catch (NumberFormatException e) {
          throw new TVAnytimeException ("Duration: problem parsing duration minutes.");
        }
        // check for valid range
        if (minutes < 0 || minutes > 59) {
          throw new TVAnytimeException ("Duration: minutes must be between 0 and 59.");
        }
      }
      previousIndex = currentIndex;
    }

    // Get position of S (note this is optional)
    currentIndex = durationAsString.indexOf("S");
    if (currentIndex > -1) {
      // Check if any digits between M and S
      if (currentIndex-previousIndex>1) {
        // Set seconds
        try {
          seconds = Integer.parseInt(durationAsString.substring(previousIndex+1,currentIndex));
        } catch (NumberFormatException e) {
          throw new TVAnytimeException ("Duration: problem parsing duration seconds.");
        }
        // check for valid range
        if (seconds < 0 || seconds > 59) {
          throw new TVAnytimeException ("Duration: seconds must be between 0 and 59.");
        }
      }
      previousIndex = currentIndex;
    }

    // Doesn't support any fractions

		// set durationAsMsec
		durationInMsec = 1000 * ( (days*3600*24)+(hours*3600)+(minutes*60)+seconds);    
	}



  /* 
	public void setDuration(String duration) throws TVAnytimeException
	{
		this.durationAsString = duration;

		// check to see if the string starts with 'PT' (- it should!)
		if(durationAsString.startsWith("PT")) { }
		else {
			throw new TVAnytimeException ("Duration: duration as string must start with 'PT'.");
		}

		if(durationAsString.indexOf("H") == -1) {
			hours = 0;
		}
		else {
			try {
				hours = Integer.parseInt(durationAsString.substring(durationAsString.indexOf("H")-2, durationAsString.indexOf("H")));
			} catch (NumberFormatException e) {
				throw new TVAnytimeException ("Duration: problem parsing duration hours.");
			}

			// check for valid hours range
			if (hours < 0 || hours > 23) {
				throw new TVAnytimeException ("Duration: hours must be between 0 and 23.");
			}
		}

		if(durationAsString.indexOf("M") == -1) {
			minutes = 0;
		}
		else {
			try {
				minutes = Integer.parseInt(durationAsString.substring(durationAsString.indexOf("M")-2, durationAsString.indexOf("M")));
			} catch (NumberFormatException e) {
				throw new TVAnytimeException ("Duration: problem parsing duration minutes.");
			}

			// check for valid minutes range
			if (minutes < 0 || minutes > 59) {
				throw new TVAnytimeException ("Duration: minutes must be between 0 and 59.");
			}
		}

		if(durationAsString.indexOf("S") == -1) {
			seconds = 0;
		}
		else {
			try {
				seconds = Integer.parseInt(durationAsString.substring(durationAsString.indexOf("S")-2, durationAsString.indexOf("S")));
			} catch (NumberFormatException e) {
				throw new TVAnytimeException ("Duration: problem parsing duration seconds.");
			}

			// check for valid seconds range
			if (seconds < 0 || seconds > 59) {
				throw new TVAnytimeException ("Duration: problem parsing duration hours.");
			}
		}

		// set durationAsMsec
		durationInMsec = 1000 * ((hours*3600)+(minutes*60)+seconds);    
	}
  */

	/**
	 * Set the duration
	 *
	 * @param	duration	number of milliseconds
	 */
	public void setDuration(long duration)
	{
		this.durationInMsec = duration;

		int durationInSeconds = (int)durationInMsec/1000;

		days = (int)Math.floor(durationInSeconds/(3600*24));

		hours = (int)Math.floor(durationInSeconds/3600);

		minutes = (int)Math.floor((durationInSeconds-(3600*hours))/60);

		seconds = durationInSeconds-(3600*hours)-(60*minutes);

		durationAsString = "P"+((days==0)?"":Integer.toString(days))+"T" + hours + "H" + minutes + "M" + seconds + "S";
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
	 * @param	indent	number of tabs to put before the string.
	 * @return  string representation of this object.
	 */
	public String toString(int indent)
	{
		String sysOut = "";

		for (int i=0;i<indent;i++)
			sysOut += "\t";

		sysOut += "Duration: " + durationAsString;

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    Duration clone = new Duration();
    try {
      if (durationAsString != null) clone.setDuration(new String(durationAsString));
    } catch (TVAnytimeException tvae) { }
    return clone;
  }

}
