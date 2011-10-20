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

import bbc.rd.tvanytime.*;

/**
 *
 * ServiceInformation: Represents a ServiceInformation object.
 *
 * @author Tristan Ferne, BBC Research & Development, February 2003
 * @version 1.0
 */
public class ServiceInformation  {

  /**
   * Service ID.
   */
  private String serviceID = null;
  /**
   * DVB URL of service.
   */
  private URI serviceURL = null;
  /**
   * Name of service.
   */
  private String name = null;
  /**
   * Name of owner of service.
   */
  private String owner = null;

  /**
   * Constructor.
   */
  public ServiceInformation() {
  }

  /**
   * Set Service ID of service.
   *
   * @param  serviceId  Service ID of this service.
   */
  public void setServiceID(String serviceID) {
    this.serviceID = serviceID;
  }

  /**
   * Get Service ID of service.
   *
   * @return  Service ID for this service.
   */
  public String getServiceID() {
    return serviceID;
  }

  /**
   * Set DVB URL of service.
   *
   * @param  serviceURL  DVB URL of this service.
   */
  public void setServiceURL(URI serviceURL) {
    this.serviceURL = serviceURL;
  }

  /**
   * Get DVB URL of service.
   *
   * @return  DVB URL for this service.
   */
  public URI getServiceURL() {
    return serviceURL;
  }


  /**
   * Set name of service.
   *
   * @param  name  Name of this service.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get name of service.
   *
   * @return  Name for this service.
   */
  public String getName() {
    return name;
  }

  /**
   * Set owner of service.
   *
   * @param  owner  Owner of this service.
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }

  /**
   * Get owner of service.
   *
   * @return  Owner for this service.
   */
  public String getOwner() {
    return owner;
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
	 * Returns XML representation of this table.
	 *
	 * @param  indent number of tabs to put before the string.
	 * @return  XML representation of this table.
	 */
	public String toXML(int indent) {
            StringBuffer xmlBuf = new StringBuffer();
            //String xml = "";
            
            synchronized(xmlBuf) {
		for (int i=0;i<indent;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
		}
    		xmlBuf.append("<ServiceInformation serviceId=\"");
                xmlBuf.append(serviceID);
                xmlBuf.append("\">\n");
                //xml += "<ServiceInformation serviceId=\""+serviceID+"\">\n";

		indent++;

                if (name != null) {
                    for (int i=0;i<indent;i++) {
                        xmlBuf.append("\t");
                        //xml += "\t";
		    }
		    xmlBuf.append("<Name>");
                    xmlBuf.append(name);
                    xmlBuf.append("</Name>\n");
                    //xml = xml + "<Name>"+name+"</Name>\n";
                }

                if (owner != null) {
                    for (int i=0;i<indent;i++) {
	    		xmlBuf.append("\t");
                        //xml += "\t";
                    }
                    xmlBuf.append("<Owner>");
                    xmlBuf.append(owner);
                    xmlBuf.append("</Owner>\n");
                    //xml = xml + "<Owner>"+owner+"</Owner>\n";
                }

                if (serviceURL != null) {
                    for (int i=0;i<indent;i++) {
                        xmlBuf.append("\t");
                        //xml += "\t";
                    }
                    xmlBuf.append("<ServiceURL>");
                    xmlBuf.append(serviceURL.getURI());
                    xmlBuf.append("</ServiceURL>\n");
                    //xml = xml + "<ServiceURL>"+serviceURL.getURI()+"</ServiceURL>\n";
                }

	  	for (int i=0;i<indent-1;i++) {
                    xmlBuf.append("\t");
                    //xml += "\t";
                }
		xmlBuf.append("</ServiceInformation>");
                //xml = xml + "</ServiceInformation>";

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

		sysOut += "ServiceInformation: serviceID: "+serviceID+"\n";

		indent++;

		if (serviceURL != null)
			sysOut += serviceURL.toString(indent) + "\n";

		if(name != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "name: " + name + "\n";
		}
		if(owner != null) {
			for (int i=0;i<indent;i++)
				sysOut += "\t";
			sysOut += "owner: " + owner + "\n";
		}
		return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    ServiceInformation clone = new ServiceInformation();
    if (serviceID != null) clone.setServiceID(new String(serviceID));
    if (serviceURL != null) clone.setServiceURL((URI)serviceURL.clone());
    if (name != null) clone.setName(new String(name));
    if (owner != null) clone.setOwner(new String(owner));
    return clone;
  }



}