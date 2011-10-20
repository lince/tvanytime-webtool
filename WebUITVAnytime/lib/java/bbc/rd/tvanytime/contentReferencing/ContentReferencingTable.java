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


package	bbc.rd.tvanytime.contentReferencing;

import java.io.Serializable;
import java.util.*;
import bbc.rd.tvanytime.*;
import bbc.rd.tvanytime.search.*;
import bbc.rd.tvanytime.util.*;

/**
 *
 * ContentReferencingTable:	Represents a table that	contains ContentReferencing
 * objects.
 *
 * @author Tim Sargeant, BBC Research &	Development, February 2003
 * @author Tristan Ferne, BBC Research &	Development, August 2003
 * @version	1.1
 */

public class ContentReferencingTable implements	Cloneable, LocationResolution, Serializable
{
	private float version;
	private Vector results;
  private Hashtable resultsHashtable;
	private boolean resultObjectExists = false;

	/**
	* Constructor for objects of type ContentReferencingTable.
	*
	*/
	public ContentReferencingTable() {

		results = new Vector (0,1);
    resultsHashtable = new Hashtable();
	}

	/**
	* Constructor for objects of type ContentReferencingTable with required version attribute.
	*
	* @param	 Version number.
	*/
	public ContentReferencingTable(float version) {

		this();
		this.version = version;
	}

	/**
	*
	* Add a Result object.
	*
	* @param	 The Result object to add to this table.
	*/
	public void addResult(Result result) {

		results.addElement(result);
    resultsHashtable.put(result.getCRID().getCRID().toLowerCase(),result);
		resultObjectExists = true;
	}

	/**
	*
	* Get a Result object.
	*
	* @param	 index The index of	the	Result object to access.
	* @return  Specified Result object.
	*/
	public Result getResult(int index) {

		if (resultObjectExists)
			return (Result)results.elementAt(index);
		else
			return null;
	}

	/**
	 * Removes a Result object from this ContentReferencingTable
	 *
	 * @param	 index	 The index to the Result object
	 */
	public void removeResult(int index)
	{
    resultsHashtable.remove(this.getResult(index));
    results.removeElementAt(index);
	}

	/**
	 * Removes a Result object from this ContentReferencingTable
	 *
	 * @param	 result	 The Result object to remove
	 */
	public void removeResult(Result result)
	{
    resultsHashtable.remove(result);  
    Enumeration e = results.elements();
    Result currentResult;
    int ct=0;
    while (e.hasMoreElements()) {
      currentResult = (Result)e.nextElement();
      if (currentResult.getCRID().equals(result.getCRID())) {
        // Matches so remove
        results.removeElementAt(ct);
        break;
      }
      else ct++;
    }    
	}

  /**
   * Remove all objects from the ContentReferencing table.
   */
  public void removeAll() {
    results.removeAllElements();
    resultsHashtable.clear();
  }

	/**
	*
	* Get the number of Result objects.
	*
	* @return  The number of Result objects contained in this table.
	*/
	public int getNumResults() {

		if (resultObjectExists)
			return results.size();
		else
			return 0;
	}

	/**
	 * Set version
	 *
	 * @param version the table version number
	 */
	public void setVersion(float version)
	{
		this.version = version;
	}

	/**
	 * Get version
	 *
	 * @return The version number for this table
	 */
	public float getVersion()
	{
		return this.version;
	}

	/**
	* Unsupported method - always throws exception. Use resolveCRID() instead.
	*
	* @param   crid  the CRID to search for.
	* @return Vector  of BroadcastURLs for this CRID.
	* @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
	*/
	public Vector resolveLeafCRID(ContentReference crid) throws SearchInterfaceNotSupportedException {
		throw new SearchInterfaceNotSupportedException("");
	}

	/**
	* Unsupported method - always throws exception. Use resolveCRID() instead, then if you've got locators
	* (having looked up the DVB triplet for your serviceID in the ServiceInformation table) check the
	* ProgramURL's dvb triplet for a match.
	*
	* @param   crid  the CRID to search for.
	* @param   serviceID  The serviceID of the service we are interested in.
	* @return Vector  containing BroadcastURLs for this CRID.
	* @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
	*/
	public Vector resolveLeafCRID(ContentReference crid, String serviceID) throws SearchInterfaceNotSupportedException {
		throw new SearchInterfaceNotSupportedException("");
	}

	/**
	 * Returns Result object for this CRID.
	 *
	 * @param   crid  CRID to search for.
	 * @return  Result for this CRID.
	 */
	public Result resolveCRID(ContentReference crid)
	{
		// Do search and add results to results vector
    /*
		for (int i=0;i<results.size();i++)
		{
			// get ith result in this table
			Result r = this.getResult(i);
			// if this is the result for the crid we've asked for:
			if (r.getCRID().equals(crid))
			{
				return r;
			}
		}

		return null;
    */
    return (Result)resultsHashtable.get(crid.getCRID().toLowerCase());
	}

	/**
	 * Returns vector of CRIDs for all events that start at a time between the
	 * start time and the end time.
	 * <BR>This implementation returns CRIDS of programmes which are showing between start and
	 * end times.  (eg. if <I>startTime = 22:04</I>, the crid of a news programme which started
	 * at 22:00 will be returned.  Similarly, the crid of programmes showing at the endTime
	 * will be returned.)<p>
	 * <B>The search is carried out by examining the start time and duration found in the dvb locator,
	 * so will fail if DVB locator isn't present.</b>
	 *
	 * @param   startTime   Start time of schedule.
	 * @param   endTime     End time of schedule.
	 * @return Vector of all ContentReferences in this time period.
	 */
	public Vector getSchedule(Date startTime, Date endTime)
	{
		Vector crids = new Vector (0,1);

		/*
		 * Do search and add results to results vector
		 */
		for (int i=0;i<results.size();i++)
		{
			// get ith result in this table
			Result r = this.getResult(i);
			// if we've got leaf crids
			if (r.getLocationsResult() != null)
			{
				for (int j=0;j<r.getLocationsResult().getNumLocators();j++)
				{
					// check against each location
					Locator loc = (Locator)r.getLocationsResult().getLocator(j);

					// now we've got a Locator - need to check against startTime & (startTime + duration)

					// get end
					Date progEnd = new Date();
					Date progStart;

					try {

						progEnd.setTime((DVBLocatorToolbox.getDate(loc.getLocator())).getTime()
							 + (DVBLocatorToolbox.getDuration(loc.getLocator())).getDurationInMsec());

						// get start
						progStart = DVBLocatorToolbox.getDate(loc.getLocator());

						// The test I'm using is:
						if ((progStart.after(startTime) && progStart.before(endTime))
							|| (progEnd.after(startTime) && progEnd.before(endTime)))
						{
							crids.addElement(r.getCRID());
						}
					}
					catch (TVAnytimeException tvae) {

					}
				}
			}
		}

		return crids;
	}

	/**
	 * Unsupported method - always throws exception.
	 *
	 * This method is not supported on ContentReferencingTable.
	 * serviceID is not known in the table - to perform an equivalent search, you should use
	 * getSchedule( ) on this (ContentReferencing) table, then resolve each returned CRID to retrieve
	 * the locator(s).  Now, using the ServiceInformation table, translate between serviceID and dvb triplet,
	 * and check the DVB triplet part of each locator for a match.
	 *
	 * @param   startTime   Start time of schedule.
	 * @param   endTime     End time of schedule.
	 * @param   serviceID   The serviceID of the service we are interested in.
	 * @return Vector of all ContentReferences on this service in this time period.
	 * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
	 */
	public Vector getSchedule(Date startTime, Date endTime, String serviceID) throws SearchInterfaceNotSupportedException
	{
		throw new SearchInterfaceNotSupportedException("");
	}

	/**
	* Returns XML representation of this table.
	*
	* @return XML representation of	this table.
	*/
	public String	toXML()	{
		return toXML(0);
	}

	/**
	* Returns XML representation of this table.
	*
	* @param	  indent  Number of	tabs with which	to indent the string.
	* @return  XML representation of the table.
	*/
	public String	toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";
            synchronized(xmlBuf) {
                // Do tabs
                for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }

                xmlBuf.append("<ContentReferencingTable version=\"");
                xmlBuf.append(version);
                xmlBuf.append("\">");
                //xml += "<ContentReferencingTable version=\""+version+"\">";

                // Indent and call children
                indent++;

                for (int ct=0; ct<results.size(); ct++) {
                    xmlBuf.append("\n");
                    xmlBuf.append(((Result)results.elementAt(ct)).toXML(indent));
                    //xml = xml + "\n" + ((Result)results.elementAt(ct)).toXML(indent);
                }
                xmlBuf.append("\n");
                //xml += "\n";
                for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }

                xmlBuf.append("</ContentReferencingTable>");
                //xml += "</ContentReferencingTable>";
                return xmlBuf.toString();
            }
	}


	/**
	* Returns string	representation of this table.
	*
	* @return  String representation of this table.
	*/
	public String toString() {
		return toString(0);
	}

	/**
	* Returns string	representation of this table.
	*
	* @param	  indent  Number of	tabs with which	to indent the string.
	* @return  string representation	of the table.
	*/
	public String toString(int indent) {
		String sysOut = "";

		// Do tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
		}
		sysOut += "ContentReferencingTable:\n";

		// Indent and call children
		indent++;

    // Version number
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
		}
    sysOut += "version: "+version+"\n";
    
		for (int i=0; i<results.size();i++)
			sysOut += ((Result)results.elementAt(i)).toString(indent);

		return sysOut;
	}

	/**
	* Clones itself.
	* @return A	copy of	itself.
	*/
	public Object	clone()	{
		ContentReferencingTable	clone =	new	ContentReferencingTable(this.getVersion());
		for (int i=0; i<results.size();i++) {
			clone.addResult((Result)((Result)results.elementAt(i)).clone());
    }
		return clone;
	}
}