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
import bbc.rd.tvanytime.util.TimeToolbox;

/**
 *
 * Result:	A TVAnytime Result object.
 *
 * @author Tim Sargeant, BBC Research &	Development, February 2003
 * @version	1.0
 */

public class Result implements	Cloneable, Serializable
{
	/**
	* LocationsResult (choice between this and CridResult)
	*/
	LocationsResult locationsResult;

	/**
	* CridResult (choice between this and LocationsResult)
	*/
	CRIDResult cridResult;

	/**
	* CRID attribute (required)
	*/
	ContentReference crid;

	/**
	* status attribute (required)
	*/
	String status;

	/**
	* complete attribute (required)
	*/
	boolean complete;

	/**
	* CRID attribute (required)
	*/
	String acquire;

	/**
	* reresolvedDate attribute (optional)
	*/
	Date reresolveDate;

 /**
	* Constructor for objects of type Result.
	*
	*/
	public Result() {

	}

	/**
	* Constructor, with required attributes.
	*
	* @param	crid 	The CRID.
	* @param	status	Resolve status ("resolved", "discard CRID", "cannot yet resolve", "unable to resolve")
	* @param	complete	is resolution complete?
	* @param	acquire	Aquisition directive ("all", "any")
	* @throws	TVAnytimeException if status or acquire are invalid
	*/
	public Result(ContentReference crid, String status, boolean complete, String acquire) throws TVAnytimeException{

		setCRID(crid);
		setStatus(status);
		setComplete(complete);
		setAcquire(acquire);
	}

	/**
	 * Set the CRIDResult<p>
	 * Since CRIDResult and LocationsResult are an xsd:choice (can only have one or the other), setting CRIDResult forces any LocationsResult object to null
	 *
	 * @param	cridResult the CRIDResult
	 */
	public void setCRIDResult(CRIDResult cridResult)
	{
		this.cridResult = cridResult;

		locationsResult = null;
	}

	/**
	 * Set the LocationsResult<p>
	 * Since CRIDResult and LocationsResult are an xsd:choice (can only have one or the other), setting LocationsResult forces any CRIDResult object to null
	 *
	 * @param	locationsResult the LocationsResult
	 */
	public void setLocationsResult(LocationsResult locationsResult)
	{
		this.locationsResult = locationsResult;

		cridResult = null;
	}

	/**
	 * Set the CRID
	 *
	 * @param	crid	the CRID
	 */
	public void setCRID(ContentReference crid)
	{
		this.crid = crid;
	}

	/**
	 * Set the complete flag
	 *
	 * @param	 complete	the 'complete' flag
	 */
	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}

	/**
	 * Set the complete flag
	 *
	 * @param	 acquire	the 'acquire' flag (must be "all" or "any")
	 */
	public void setAcquire(String acquire) throws TVAnytimeException
	{
		if ((acquire.equals("all") || acquire.equals("any")))
		{
			this.acquire = acquire;
		}
		else {
			throw new TVAnytimeException ("Invalid acquire flag in Result object. Must be 'all' or 'any', you tried: " + acquire);
		}
	}

	/**
	 * Set the status flag
	 *
	 * @param	 status the 'status' flag (must be "resolve", "discard CRID", "cannot yet resolve" or "unable to resolve")
	 */
	public void setStatus(String status) throws TVAnytimeException
	{
		if ((status.equals("resolved")
			|| status.equals("discard CRID")
			|| status.equals("cannot yet resolve")
			|| status.equals("unable to resolve")))
		{
			this.status = status;
		}
		else {
			throw new TVAnytimeException ("Invalid status in Result object. Must be 'resolved', 'discard CRID', 'cannot yet resolve', or 'unable to resolve', you tried: " + status);
		}
	}

	/**
	 * Set the reresolveDate
	 *
	 * @param	reresolveDate	the reresolveDate for this Result
	 */
	public void setReresolveDate(Date reresolveDate)
	{
		this.reresolveDate = reresolveDate;
	}

	/**
	 * Get the CRID
	 *
	 * @return	crid	the Result CRID
	 */
	public ContentReference getCRID()
	{
		return this.crid;
	}

	/**
	 * Get the LocationsResult. This will be null if this object contains a CRIDResult.
	 *
	 * @return	lr	the LocationsResult
	 */
	public LocationsResult getLocationsResult()
	{
		return this.locationsResult;
	}

	/**
	 * Get the CRIDResult. This will be null if this object contains a LocationsResult.
	 *
	 * @return	cr	the CRIDResult
	 */
	public CRIDResult getCRIDResult()
	{
		return this.cridResult;
	}

	/**
	 * Is the resolution complete?
	 *
	 * @return	complete	the Result complete flag
	 */
	public boolean isComplete()
	{
		return this.complete;
	}

	/**
	 * Get the acquire flag
	 *
	 * @return	acquire the Result acquire flag
	 */
	public String getAcquire()
	{
		return this.acquire;
	}

	/**
	 * Get the status
	 *
	 * @return	status the Result status
	 */
	public String getStatus()
	{
		return this.status;
	}

	/**
	 * Get the reresolveDate
	 *
	 * @return	reresolveDate the reresolveDate for this Result
	 */
	public Date getReresolveDate()
	{
		return this.reresolveDate;
	}

	/**
	* Returns XML representation of this object.
	*
	* @return XML representation of	this object.
	*/
	public String toXML()	{
		return toXML(0);
	}

	/**
	* Returns XML representation of this object.
	*
	* @param	  indent  Number of	tabs with which	to indent the string.
	* @return  XML representation of the object.
	*/
	public String toXML(int indent) {
		StringBuffer xmlBuf = new StringBuffer();

		synchronized (xmlBuf) {
			// Do tabs
			for (int i = 0; i < indent; i++) {
				xmlBuf.append("\t");
			}

			xmlBuf.append("<Result");

			if (crid != null) {
				xmlBuf.append(" CRID=\"");
				xmlBuf.append(crid.getCRID());
				xmlBuf.append("\"");
			}

			xmlBuf.append(" complete=\"");
			xmlBuf.append(complete);
			xmlBuf.append("\"");

			if (acquire != null) {
				xmlBuf.append(" acquire=\"");
				xmlBuf.append(acquire);
				xmlBuf.append("\"");				
			}

			if (status != null) {
				xmlBuf.append(" status=\"");
				xmlBuf.append(status);
				xmlBuf.append("\"");
			}

			if (reresolveDate != null) {
				xmlBuf.append(" reresolveDate=\"");
				xmlBuf.append(TimeToolbox.makeTVATimeString(reresolveDate));
				xmlBuf.append("\"");
			}
			
			xmlBuf.append(">\n");

			// Indent and call children
			indent++;

			// LocationsResult
			if (locationsResult != null) {
				xmlBuf.append(locationsResult.toXML(indent));
			}

			// cridResult
			if (cridResult != null) {
				xmlBuf.append(cridResult.toXML(indent));
			}

			xmlBuf.append("\n");
			for (int i = 0; i < indent - 1; i++) {
				xmlBuf.append("\t");
			}

			xmlBuf.append("</Result>");
			return xmlBuf.toString();
		}
	}


	/**
	 * Returns string representation of this object.
	 * 
	 * @return String representation of this object.
	 */
	public String toString() {
		return toString(0);
	}

	/**
	 * Returns string representation of this object.
	 * 
	 * @param indent
	 *            Number of tabs with which to indent the string.
	 * @return string representation of the object.
	 */
	public String toString(int indent) {
		String sysOut = "";

		// Do tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
		}
		sysOut += "Result:\n";

		// Indent and call children
		indent++;
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
		}

		// CRID
		if (crid != null) {
			sysOut = sysOut + "\n" + crid.toString(indent);
		}

		sysOut += "\n";

		// status
	    for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut = sysOut + "status = " + status + "\n";

		// complete
	    for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut = sysOut + "complete = " + complete + "\n";

		// acquire
	    for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut = sysOut + "acquire = " + acquire + "\n";

		// reresolveDate
	    for (int i=0;i<indent;i++) {
			sysOut += "\t";
	    }
		sysOut = sysOut + "reresolveDate = " + reresolveDate + "\n";

		// LocationsResult
		if (locationsResult != null) {
			sysOut = sysOut + "\n" + locationsResult.toString(indent);
		}

		// cridResult
		if (cridResult != null) {
			sysOut = sysOut + "\n" + cridResult.toString(indent);
		}

		return sysOut;
	}

	/**
	* Clones itself.
	* @return A	copy of	itself.
	*/
	public Object	clone()	{
		Result clone = new Result();
		try {
			if (acquire != null) clone.setAcquire(new String(acquire));
		} catch (TVAnytimeException tvae) {
			System.out.println("Error while cloning: "+tvae.getMessage());
		}
		try {
			if (status != null) clone.setStatus(new String(status));
		} catch (TVAnytimeException tvae) {
			System.out.println("Error while cloning: "+tvae.getMessage());
		}
		// complete is fundamental type so bitwise copy is OK
		if (crid != null) clone.setCRID((ContentReference)crid.clone());		
		if (cridResult != null) clone.setCRIDResult((CRIDResult)cridResult.clone());
		if (locationsResult != null) clone.setLocationsResult((LocationsResult)locationsResult.clone());
		if (reresolveDate != null) clone.setReresolveDate(new Date(reresolveDate.getTime()));		
		return clone;
	}
}