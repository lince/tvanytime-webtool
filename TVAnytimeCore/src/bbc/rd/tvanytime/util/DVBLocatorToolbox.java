/**
 * Copyright 2003 BBC Research & Development
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

import java.util.*;
import java.util.Date;
import java.lang.Integer;
import bbc.rd.tvanytime.*;	// for TVAnytimeException

/**
 * DVBLocatorToolbox: A class (which should be treated as abstract) offering useful methods
 * on DVB Locators.
 * <br>This version accepts DVB locator strings in the following forms which are all compliant with 
 * the extended DVB Locator defined in ETSI TS 102 822-3-1 (V1.1.1):
 * "dvb://233a.1004.1084;6f2c@2001-12-08T02:50:00--PT00H10M45S"
 * "dvb://233a.1004.1084;6f2c@2001-12-08T02:50:00Z--PT00H10M45S"
 * "dvb://233a.1004.1084;6f2c@2001-12-08T02:50:00+01--PT00H10M45S"
 * "dvb://233a.1004.1084;6f2c@2001-12-08T02:50:00+01:00--PT00H10M45S"
 * 
 * It will also accept locators where the "--" symbols are replaced by "/" although this is not DVB compliant. 
 * 
 * @author Tim Sargeant, BBC Research & Development, May 2003
 * @author Chris Newell, BBC Research * Development, Nov 2004
 * @version 1.1
 */

public class DVBLocatorToolbox {

	private static String dvbTriplet;
    private static String eventID;
	private static String tvaID;
	private static String time_constraint;	// e.g. "2001-12-08T02:50:00Z--PT00H10M45S"

	// to prevent initialization(ish)
	protected DVBLocatorToolbox()
	{
	}

	/**
	 * Get the DVB triplet part of the DVBLocator
	 *
	 * @return	the String representation of the DVB triplet (e.g. "233a.1004.1084") or
	 * null if DVB triplet cannot be found.
	 *
	 * @throws	TVAnytimeException thrown when locator does not start with 'dvb://'
	 */
	public static String getDVBTriplet(String locator) throws TVAnytimeException
	{
		setLocator(locator);
		return dvbTriplet;
	}

	/**
	 * Get the event_id part of the DVBLocator
	 *
	 * @return	the String representation of the event ID (e.g. "6f2b") or
	 * null if event ID doesn't exist.
	 * @throws	TVAnytimeException thrown when locator does not start with 'dvb://'
	 */
	public static String getEventID(String locator) throws TVAnytimeException
	{
		setLocator(locator);
		return eventID;
	}
	
	/**
	 * Get the TVA_id part of the DVBLocator
	 *
	 * @return	the String representation of the TVA_id (e.g. "6f2b") or
	 * null if TVA_id doesn't exist.
	 * @throws	TVAnytimeException thrown when locator does not start with 'dvb://'
	 */
	public static String getTvaID(String locator) throws TVAnytimeException
	{
		setLocator(locator);
		return tvaID;
	}

	/**
	 * Get the date part of the locator.
	 * 
	 * <p>This method currently accepts "--" or "/" as separators to delineate the date/time and duration.
	 * However, the use of "/" is not DVB compliant and is deprecated.
	 * 
	 * @return	 the date part of the DVBLocator as a java Date object or null if there is no time_constraint.
	 * @throws	TVAnytimeException if the locator is invalid.
	 */
	public static Date getDate(String locator) throws TVAnytimeException
	{
		setLocator(locator);
		int indexOfDoubleHyphen = time_constraint.indexOf("--");
		int indexOfSolidus = time_constraint.indexOf("/");
		String dateTimeString;
		if(indexOfDoubleHyphen != -1) {
			dateTimeString = time_constraint.substring(0, indexOfDoubleHyphen);
		} else if(indexOfSolidus != -1) {
			dateTimeString = time_constraint.substring(0, indexOfSolidus);
		} else {
			throw new TVAnytimeException("DVBLocator: time_constraint is invalid: " + time_constraint);
		}

		// Process date & time.
		// System.out.println("dateTimeString = " + dateTimeString);
		int year = Integer.parseInt(dateTimeString.substring(0, 4));
		int month = Integer.parseInt(dateTimeString.substring(5, 7));
		int day = Integer.parseInt(dateTimeString.substring(8, 10));
		int hour = Integer.parseInt(dateTimeString.substring(11, 13));
		int minute = Integer.parseInt(dateTimeString.substring(14, 16));
		int second;			   // Seconds are optional.
		String timeZoneString;
		if(dateTimeString.length() > 18 && dateTimeString.substring(16, 17).equals(":")) {
			second = Integer.parseInt(dateTimeString.substring(17, 19));
			if(dateTimeString.length() > 19) {
				timeZoneString = dateTimeString.substring(19);
			} else {
				timeZoneString = "";
			}
		} else {
			second = 0;
			if(dateTimeString.length() > 16) {
				timeZoneString = dateTimeString.substring(16);
			} else {
				timeZoneString = "";
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.clear();
		cal.set(year, month-1, day, hour, minute, second);
		
		// Deal with time zone information.
		// System.out.println("timeZoneString = " + timeZoneString);
		if(timeZoneString.startsWith("+")) {
			// System.out.println("DAY_OF_MONTH = " + cal.get(Calendar.DAY_OF_MONTH) + " HOUR_OF_DAY = " + cal.get(Calendar.HOUR_OF_DAY));
			if(timeZoneString.length() == 3){
				cal.add(Calendar.HOUR_OF_DAY, -Integer.parseInt(timeZoneString.substring(1, 3)));
			} else if(timeZoneString.length() == 6) {
				cal.add(Calendar.HOUR_OF_DAY, -Integer.parseInt(timeZoneString.substring(1, 3)));
				cal.add(Calendar.MINUTE, -Integer.parseInt(timeZoneString.substring(4, 6)));
			} else {
				throw new TVAnytimeException("DVBLocator: Invalid time zone information: " + timeZoneString);
			}
			// System.out.println("DAY_OF_MONTH = " + cal.get(Calendar.DAY_OF_MONTH) + " HOUR_OF_DAY = " + cal.get(Calendar.HOUR_OF_DAY));
		} else if(timeZoneString.startsWith("-")) {
			if(timeZoneString.length() == 3){
				cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(timeZoneString.substring(1, 3)));
			} else if(timeZoneString.length() == 6) {
				cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(timeZoneString.substring(1, 3)));
				cal.add(Calendar.MINUTE, Integer.parseInt(timeZoneString.substring(4, 6)));
			} else {
				throw new TVAnytimeException("DVBLocator: Invalid time zone information: " + timeZoneString);
			}
		} else if(!timeZoneString.equals("") && !timeZoneString.equals("Z")) {
			throw new TVAnytimeException("DVBLocator: Invalid time zone information: " + timeZoneString);			  
		}
		Date date = cal.getTime();
		return date;
	}

	/**
	 * Get the duration part of the locator.
	 * 
	 * <p>The method currently accepts "--" or "/" as separators to delineate the date/time and 
	 * duration. However, the use of "/" is not DVB compliant and is deprecated.
	 *
	 * @return	the duration part of the DVBLocator as a bbc.rd.tvanytime.Duration object or null if there is no time_constraint.
	 * @throws	TVAnytimeException if the locator is invalid.
	 */
	public static Duration getDuration(String locator) throws TVAnytimeException
	{
		setLocator(locator);
		if(time_constraint == null) {
			return null;
		}
		int indexOfDoubleHyphen = time_constraint.indexOf("--");
		int indexOfSolidus = time_constraint.indexOf("/");
		String durationString;
		if(indexOfDoubleHyphen != -1) {
			durationString =  time_constraint.substring(indexOfDoubleHyphen + 2);
		} else if(indexOfSolidus != -1) {
			durationString =  time_constraint.substring(indexOfSolidus + 1);
		} else {
			throw new TVAnytimeException("DVBLocator: time_constraint is invalid: " + time_constraint);
		}
		Duration duration = new Duration(durationString);
		return duration;
	}

	/**
	 * Checks whether this is a DVBLocator
	 *
	 * @return true if this locator starts with "dvb://", false if it starts with anything else
	 */
	public static boolean isDVBLocator(String locator)
	{
		if (locator.substring(0,6).equalsIgnoreCase("dvb://")) {
			return true;
		} else {
			 return false;
		}
	}

	/*
	 * Sets the dvb locator
	 *
	 * This method overrides bbc.rd.tvanytime.contentReferencing.Locator.setLocator, becuase we provide
	 * syntax checking, etc.
	 * 
	 * param:  locator string representation of the dvb locator (e.g. "dvb://233a.1004.1084;6f2b@2001-12-08T02:05:00Z/PT00H45M")
	 * or new format "dvb://233a.1004.1084;6f2c@2001-12-08T02:50:00Z--PT00H10M45S"
	 * throws: TVAnytimeException thrown when locator does not start with 'dvb://'.
	 */
	private static void setLocator(String locator) throws TVAnytimeException {
		if (locator.substring(0,6).equalsIgnoreCase("dvb://")) {
			// make dvbTriple
			int indexOfAt = locator.indexOf("@", 6);
			int indexOfDoubleSemicolon = locator.indexOf(";;", 6);
			int indexOfSemicolon;
			if(indexOfDoubleSemicolon == -1) {
				indexOfSemicolon =  locator.indexOf(";", 6);
			} else {
				indexOfSemicolon = -1;
			}
			if(indexOfDoubleSemicolon != -1) {
				dvbTriplet = locator.substring(6, indexOfDoubleSemicolon);
			} else if(indexOfSemicolon != -1) {
				dvbTriplet = locator.substring(6, indexOfSemicolon);
			} else if(indexOfAt != -1) {
				dvbTriplet = locator.substring(6, indexOfAt);
			} else {
				dvbTriplet = locator.substring(6);
			}
			
			// make event_id or TVA_id
			int endId;
			if(indexOfAt != -1) {
				endId = indexOfAt;
			} else {
				endId = locator.length();
			}
			if (indexOfSemicolon != -1)
				eventID = locator.substring(indexOfSemicolon + 1, endId);
			else {
				eventID = null;
			}
			if (indexOfDoubleSemicolon != -1)
				tvaID = locator.substring(indexOfDoubleSemicolon + 2, endId);
			else {
				tvaID = null;
			}

			// Make time_constraint.
			if (indexOfAt != -1 ) {
				time_constraint = locator.substring(indexOfAt + 1);
			} else {
				time_constraint = null;
			  return;
			}
		}
		else throw new TVAnytimeException ("DVBLocator: Must start with 'dvb://', you gave me " + locator.substring(0,6));
	}
}