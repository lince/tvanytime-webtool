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


package bbc.rd.tvanytime.serviceInformation;

import java.util.*;

/**
 *
 * ServiceInformationTable: Represents a table that contains ServiceInformation
 * objects.
 *
 * @author Tristan Ferne, BBC Research & Development, Febraury 2003
 * @version 1.0
 */
public class ServiceInformationTable  {

  /**
   * Vector containing service information objects.
   */
  private Vector serviceInformations;


  /**
   * Constructor.
   */
  public ServiceInformationTable() {
    serviceInformations = new Vector();
  }

  /**
   * Return specified ServiceInformation object from table.
   *
   * @param  index  Index of ServiceInformation object in table.
   * @return  Specified ServiceInformation object, or null if none exist.
   */
  public ServiceInformation getServiceInformation(int index) {
    if (serviceInformations.size() > 0) {
      return (ServiceInformation)serviceInformations.elementAt(index);
    }
    else return null;
  }

  /**
   * Add ServiceInformation object to the table.
   *
   * @param  serviceInformation  ServiceInformation object to add.
   */
  public void addServiceInformation(ServiceInformation serviceInformation) {
    serviceInformations.addElement(serviceInformation);
  }

	/**
	 * Removes a ServiceInformation object from this ServiceInformationTable
	 *
	 * @param	 index	 The index to the ServiceInformation object
	 */
	public void removeServiceInformation(int index)
	{
 		serviceInformations.removeElementAt(index);
	}

	/**
	 * Removes a ServiceInformation object from this ServiceInformationTable
	 *
	 * @param	 serviceInformation	 The ServiceInformation object to remove
	 */
	public void removeServiceInformation(ServiceInformation serviceInformation)
	{
    Enumeration e = serviceInformations.elements();
    ServiceInformation serviceInfo;
    int ct=0;
    while (e.hasMoreElements()) {
      serviceInfo = (ServiceInformation)e.nextElement();
      if (serviceInfo.getServiceID().equals(serviceInformation.getServiceID())) {
        // Matches so remove
        serviceInformations.removeElementAt(ct);
      }
      else ct++;
    }
	}


  /**
   * Get number of objects in ServiceInformation table.
   *
   * @return  Number of objects in ServiceInformation table.
   */
  public int getNumServiceInformations() {
    return serviceInformations.size();
  }

  /**
   * Remove all objects from the ServiceInformation table.
   */
  public void removeAll() {
    serviceInformations.removeAllElements();
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
            xmlBuf.append("<ServiceInformationTable>");
            //xml += "<ServiceInformationTable>";

	    // Indent and call children
            indent++;

	    for (int ct=0; ct<serviceInformations.size(); ct++) {
	    	xmlBuf.append("\n");
                xmlBuf.append(((ServiceInformation)serviceInformations.elementAt(ct)).toXML(indent));
                //xml = xml + "\n" + ((ServiceInformation)serviceInformations.elementAt(ct)).toXML(indent);
	    }
	    xmlBuf.append("\n");
            //xml += "\n";
            for (int i=0;i<indent-1;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
	    }
            xmlBuf.append("</ServiceInformationTable>");
            //xml += "</ServiceInformationTable>";
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

		sysOut += "ServiceInformationTable: \n";

		indent++;

		for (int i=0; i<serviceInformations.size();i++)
			sysOut += ((ServiceInformation)serviceInformations.elementAt(i)).toString(indent);

		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    ServiceInformationTable clone = new ServiceInformationTable();
		for (int i=0; i<serviceInformations.size();i++) {
      clone.addServiceInformation((ServiceInformation) (((ServiceInformation)serviceInformations.elementAt(i)).clone()) );
    }
    return clone;
  }

}