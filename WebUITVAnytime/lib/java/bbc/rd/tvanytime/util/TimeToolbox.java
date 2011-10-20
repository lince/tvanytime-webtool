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


package bbc.rd.tvanytime.util;

import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.text.*;
import bbc.rd.tvanytime.TVAnytimeException;

/**
 * TimeToolbox: A class (which should be treated as abstract) offering useful methods
 * on TV Anytime time and date types.
 *
 * @author Tim Sargeant, BBC Research & Development, May 2002
 * @author Tristan Ferne, BBC R&D, September 2003
 * @version 1.0
 */

public class TimeToolbox
{
  /**
   * Text formatting for date string.
   */
  private static DecimalFormat twoDigits = new DecimalFormat("00");

	//to prevent initialization(ish)
	protected TimeToolbox()
	{
	}

	/**
	 * makeDate - creates and returns a Date object of UTC when given a TVA time format string (eg
	 * <i>"2002-05-16T00:30:00Z"</i> or <i>"2002-05-16T00:30:00:+02:30"</i>).
	 *
	 * @param	tvaTimeString TVA formatted time string
	 *
	 * @return	Date a java.util.Date object
	 * @throws	TVAnytimeException thrown when there's a problem parsing tvaTimeString
	 */
	public static Date makeDate(String tvaTimeString) throws TVAnytimeException
	{
		int year = -1;
		int month = -1;
		int day = -1;
		int hours = -1;
		int minutes = -1;
		int seconds = -1;

		int offsetHours = 0;		// an offset to deal with time zones
		int offsetMinutes = 0;

		// look to see if the string ends in "Z" (GMT) 
		if (tvaTimeString.endsWith("Z")) {
			offsetHours = 0;
			offsetMinutes = 0;
		}
    // or no +/- for offset
    else if (tvaTimeString.length()>6 && (!tvaTimeString.substring(tvaTimeString.length()-6,tvaTimeString.length()-5).equals("+")) && (!tvaTimeString.substring(tvaTimeString.length()-6,tvaTimeString.length()-5).equals("-")) ) {
			offsetHours = 0;
			offsetMinutes = 0;
    }
		// if not, pull out the offsets for hours and minutes
		else {
			String stringOffset = (tvaTimeString.substring(tvaTimeString.length()-6, tvaTimeString.length()));

			try {
				offsetHours = Integer.parseInt(stringOffset.substring(1,3));
			} catch (NumberFormatException e) {
				throw new TVAnytimeException ("TimeToolbox: problem parsing time offsetHours. "+e);
			}

			if (offsetHours < 0 || offsetHours > 23)
				throw new TVAnytimeException ("TimeToolbox: hours offset is out of range (must be between 0 and 23).");

			try {
				offsetMinutes = Integer.parseInt(stringOffset.substring(4,6));
			} catch (NumberFormatException e) {
				throw new TVAnytimeException ("TimeToolbox: problem parsing time offsetMinutes. "+e);
			}

			// check for valid offsetMinutes range
			if (offsetMinutes < 0 || offsetMinutes > 59)
				throw new TVAnytimeException ("TimeToolbox: minutes offset is out of range (must be between 0 and 59).");

			// if a negative offset...
			if (stringOffset.startsWith("-")) {
				offsetHours = (offsetHours * -1);
				offsetMinutes = (offsetMinutes * -1);
			}
		}
		try {
			year = Integer.parseInt(tvaTimeString.substring(0, 4));
			month = Integer.parseInt(tvaTimeString.substring(5, 7));
			day = Integer.parseInt(tvaTimeString.substring(8, 10));
		} catch (NumberFormatException e) {
			throw new TVAnytimeException ("TimeToolbox: problem parsing year, month or day integer. "+e);
		}

		// check validity of year
		if (year < 0)
			throw new TVAnytimeException ("TimeToolbox: year is out of range (must be greater than 0.");

		// check validity of month
		if (month < 1 || month > 12)
			throw new TVAnytimeException ("TimeToolbox: month is out of range (must be between 1 and 12).");

		// check validity of day
		if (day < 1 || day > 31)
			throw new TVAnytimeException ("TimeToolbox: day is out of range (must be between 1 and 31).");

		try {
			hours = Integer.parseInt(tvaTimeString.substring(11, 13));
			minutes = Integer.parseInt(tvaTimeString.substring(14, 16));
			seconds = Integer.parseInt(tvaTimeString.substring(17, 19));
		} catch (NumberFormatException e) {
			throw new TVAnytimeException ("TimeToolbox: problem parsing hour, minute or seconds integer. "+e);
		}

		// check validity of hours
		if (hours < 0 || hours > 23)
			throw new TVAnytimeException ("TimeToolbox: hour is out of range (must be between 0 and 23.");

		// check validity of minutes
		if (minutes < 0 || minutes > 59)
			throw new TVAnytimeException ("TimeToolbox: minutes is out of range (must be between 0 and 59).");

		// check validity of seconds
		if (seconds < 0 || seconds > 59)
			throw new TVAnytimeException ("TimeToolbox: seconds is out of range (must be between 0 and 59).");

		Calendar cal = Calendar.getInstance();

		cal.set(year, month-1, day, hours-offsetHours, minutes-offsetMinutes, seconds);
    cal.set(Calendar.MILLISECOND, 0);

		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		return cal.getTime();
	}

	/**
	 * makeTVATimeString - creates and returns a TVA time format string (eg
	 * <i>"2002-05-16T00:30:00Z"</i> - ie always GMT) when given a java.util.Date object.
	 *
	 * @param	date a java.util.Date object
	 *
	 * @return	TVA formatted time string
	 *
	 */
	public static String makeTVATimeString(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		String tvaTimeString =
				"" + cal.get(Calendar.YEAR)
				+ "-" + twoDigits.format((cal.get(Calendar.MONTH)+1))
				+ "-" + twoDigits.format(cal.get(Calendar.DATE))
				+ "T" + twoDigits.format(cal.get(Calendar.HOUR_OF_DAY))
				+ ":" + twoDigits.format(cal.get(Calendar.MINUTE))
				+ ":" + twoDigits.format(cal.get(Calendar.SECOND))
				+ "Z";

		return tvaTimeString;
	}
}

