/**
 * Copyright 2004 BBC Research & Development
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


import bbc.rd.tvanytime.*;
import bbc.rd.tvanytime.segmentInformation.SegmentInformationTable;
import bbc.rd.tvanytime.serviceInformation.ServiceInformation;
import bbc.rd.tvanytime.xml.*;
import bbc.rd.tvanytime.programInformation.ProgramInformationTable;
import bbc.rd.tvanytime.serviceInformation.ServiceInformationTable;
import bbc.rd.tvanytime.programLocation.ProgramLocationTable;
import bbc.rd.tvanytime.groupInformation.GroupInformationTable;
import bbc.rd.tvanytime.contentReferencing.ContentReferencingTable;

import java.io.*;
import java.util.*;
import java.text.*;


/**
 * Manages TV-Anytime metadata and acts as a central point for accessing it.
 * <BR/>
 * <BR/>What does it expect?
 * <BR/>
 * <BR/>ServiceInformation.xml
 * <BR/>Group content referencing table: groups_cr.xml
 * <BR/>Group information table: groups_gr.xml
 * <BR/>Program information, location and content referencing tables:
 * <YYYYMMDD><ServiceID>_<pi/pl/cr>.xml   e.g. 20040504BBCSeven_pl.xml
 *
 * @author Tristan Ferne, BBC Research & Development, April 2004
 * @version 1.0
 * 
 * Modified T.Ferne September 2004
 */
public class ScheduleManager  {

  /**
   * Filename of the directory where the schedules are to be loaded from.
   */
  private static String xmlLocation = "";
  private static String pcXMLLocation = "I:/tv-anytime/tvdata/tvadb/xml13/";
  private static String unixXMLLocation = "/project/tv-anytime/tvdata/tvadb/xml13/";

  /**
   * Number of days of schedule to load (from today).
   */
  private static int numDays = 7;

  /**
   * Number of previous days of schedule to load (from yesterday).
   */
  private static int numDaysBack = 0;

  /**
   * Date for schedules to start on. Default is today.
   */
  private static Date startDate;

  /**
   * Top-level group CRID that is used for this set of data.
   */
  private static ContentReference topLevelGroupCRID;

  /**
   * List of group files to parse. Should include Group Information and Group
   * Content Referencing.
   */
  private static String[] groupFilenames = {"groups_gr.xml", "groups_cr.xml" };
  
  /**
   * TVAnytime XML Parser
   */
  private static SAXXMLParser parser = null;

  /**
   * Whether log progress to console.
   */
  private static boolean logging = true;

  static {
    if (System.getProperty("os.name").equalsIgnoreCase("SunOS")) {
      ScheduleManager.xmlLocation = unixXMLLocation;
    }
    else {
      ScheduleManager.xmlLocation = pcXMLLocation;      
    }
    ScheduleManager.setStartDate(new Date()); // Today
    try {
      setTopLevelGroupCRID(new ContentReference("crid://bbc.co.uk/BBCGroups"));
    } catch (TVAnytimeException tvae) {
      System.out.println(tvae.getMessage());
    }
  }

  /**
   * Private constructor so you can't create an instance.
   */
  private ScheduleManager() {
  }

  /**
   * Create parser and load schedules.
   */
  private static void initialiseInternal() {
    // Create parser
    try {
      parser = new SAXXMLParser();
      ((SAXXMLParser)parser).setParseProfile(SAXXMLParser.STANDARD);
    }
    catch (TVAnytimeException tvae) {
      System.out.println("ScheduleManager: Error creating XML parser: "+tvae.getMessage());
      return; // Don't continue
    }
    loadSchedules();    
  }
  
  /**
   * Reads configuration file and load schedules.
   * This must be called before the manager is used. The configuration file should
   * be a properties file with the following (optional) properties:
   * <br>
   * <br>start=<DD-MMM-YYYY>, e.g. 26-Apr-2004
   * <br>numdays=<int>, e.g. 7
   * <br>numdaysback=<int>, e.g. 1 
   * <br>location=<path>, e.g. I:/tv-anytime/tvdata/tvadb/xml13/
   * <br>toplevelgroup=<crid>, e.g. crid://bbc.co.uk/BBCGroups
   * 
   * <br><i>Default is today, 7 days and /project/tv-anytime/tvdata/tvadb/xml13/</i>
   * 
   * @param  The filename of the configuration file.
   */
  /*
  public static void initialise(String configFilename, Date startDate) {
    // Set default schedule location, depending on OS
  	System.out.println("initialise(filename,date)");
  	
  	//ScheduleManager.setStartDate(startDate); //
    //loadProperties(configFilename);    // Loads alternative location
    //initialiseInternal();
    
    loadProperties(configFilename);    // Loads alternative location
    initialise(startDate);
  }
*/

  /**
   * Initialises and loads schedules using default parameters.
   * This must be called before the manager is used. 
   */  
  public static void initialise(Date startDate) {
  	ScheduleManager.setStartDate(startDate); //
    initialiseInternal();
  }
  

  
  /**
   * Reads configuration file and load schedules.
   * This must be called before the manager is used. The configuration file should
   * be a properties file with the following (optional) properties:
   * <br>
   * <br>start=<DD-MMM-YYYY>, e.g. 26-Apr-2004
   * <br>numdays=<int>, e.g. 7
   * <br>numdaysback=<int>, e.g. 1 
   * <br>location=<path>, e.g. I:/tv-anytime/tvdata/tvadb/xml13/
   * <br>toplevelgroup=<crid>, e.g. crid://bbc.co.uk/BBCGroups
   * 
   * <br><i>Default is today, 7 days and /project/tv-anytime/tvdata/tvadb/xml13/</i>
   * 
   * @param  The filename of the configuration file.
   */
  public static void initialise(String configFilename) {
  	//initialise(configFilename,new Date()); // Today
    loadProperties(configFilename);    // Loads alternative location  	
  	initialiseInternal();
  }

  /**
   * Initialises and loads schedules using default parameters.
   * This must be called before the manager is used. 
   */
  public static void initialise() {
  	//initialise(new Date()); // Today
  	ScheduleManager.setStartDate(new Date());
    initialiseInternal();
  	
  }
  
  
  /**
   * Get number of days the EPG displays.
   * 
   * @return  The number of days.
   */
  public static int getNumDays() {
    return numDays;
  }

  /**
   * Set number of days the EPG displays.
   * 
   * @param  numDays  The number of days.
   */
  public static void setNumDays(int numDays) {
    ScheduleManager.numDays = numDays;
  }

  /**
   * Get number of days backwards the EPG displays.
   * 
   * @return  The number of days back.
   */
  public static int getNumDaysBack() {
    return numDaysBack;
  }

  /**
   * Set number of days backwards the EPG displays.
   * 
   * @param  numDays  The number of days back.
   */
  public static void setNumDaysBack(int numDaysBack) {
    ScheduleManager.numDaysBack = numDaysBack;
  }

  
  /**
   * Get the date the EPG starts on.
   * 
   * @return  The start date.
   */
  public static Date getStartDate() {
    return startDate;
  }

  /**
   * Set the date the EPG starts on. Zeros time to 00:00:00 on the day concerned.
   * 
   * @param  startDate  The start date.
   */
  public static void setStartDate(Date startDate) {
    GregorianCalendar start = (GregorianCalendar)Calendar.getInstance();
    start.setTime(startDate);
    start.set(Calendar.HOUR_OF_DAY,0);
    start.set(Calendar.MINUTE,0);
    start.set(Calendar.SECOND,0);    
    start.set(Calendar.MILLISECOND,0);        
    ScheduleManager.startDate = start.getTime();    
  }

  /**
   * Get the top-level group CRID that is used for this set of data.
   * 
   * @return  The top-level group CRID.
   */
  public static ContentReference getTopLevelGroupCRID() {
    return topLevelGroupCRID;
  }

  /**
   * Set the top-level group CRID that is used for this set of data.
   * 
   * @param  topLevelGroupCRID  The top-level group CRID.
   */
  public static void setTopLevelGroupCRID(ContentReference topLevelGroupCRID) {
    ScheduleManager.topLevelGroupCRID = topLevelGroupCRID;
  }

  /**
   * Get the location of the TV-Anytime schedule files.
   * 
   * @return  The directory containing the TV-Anytime XML files.
   */
  public static String getXMLLocation() {
    return xmlLocation;
  }

  /**
   * Set the location of the TV-Anytime schedule files. Should include trailing
   * slash in directory structure (e.g. /project/shareit/). Should also use
   * forward slashes regardless of platform.
   * Note: Now adds trailing slash if not present.
   * 
   * @param  xmlLocation  The directory containing the TV-Anytime XML files.
   */
  public static void setXMLLocation(String xmlLocation) {
  	if (!xmlLocation.endsWith("/")) {
  		xmlLocation += "/";
  	}
    ScheduleManager.xmlLocation = xmlLocation;
  }

  /**
   * Set whether to log progress to console.
   * 
   * @param  logging  Whether to log.
   */
  public static void setLogging(boolean logging) {
    ScheduleManager.logging = logging;
  }

  /**
   * Get program information table.
   * 
   * @return  Program information table.
   */
  public static ProgramInformationTable getProgramInformationTable() {
    return parser.getProgramInformationTable();
  }
  
  /**
   * Get program location table.
   * 
   * @return  Program location table.
   */
  public static ProgramLocationTable getProgramLocationTable() {
    return parser.getProgramLocationTable();
  }
  
  /**
   * Get group information table.
   * 
   * @return  Group information table.
   */
  public static GroupInformationTable getGroupInformationTable() {
    return parser.getGroupInformationTable();
  }
  
  /**
   * Get content referencing table.
   * 
   * @return  Content referencing table.
   */
  public static ContentReferencingTable getContentReferencingTable() {
    return parser.getContentReferencingTable();
  }

  /**
   * Get segment information table.
   * 
   * @return  Segment information table.
   */
  public static SegmentInformationTable getSegmentInformationTable() {
    return parser.getSegmentInformationTable();
  }

  
  /**
   * Clear all schedule information from all tables.
   */
  public static void clear() {
  	if (parser != null) {
	  	parser.getProgramInformationTable().removeAll();
	  	parser.getProgramLocationTable().removeAll();
	  	parser.getGroupInformationTable().removeAll();
	  	parser.getContentReferencingTable().removeAll();
	  	parser.getServiceInformationTable().removeAll();
	  	parser.getSegmentInformationTable().removeAll();
  	}
  }
  
  /**
   * Get service information table.
   * 
   * @return  Service information table.
   */
  public static ServiceInformationTable getServiceInformationTable() {
    return parser.getServiceInformationTable();
  }
  
  /**
   * Get number of services described in SI file.
   * 
   * @return  Number of services.
   */
  public static int getNumServices() {
    return getServiceInformationTable().getNumServiceInformations();    
  }
  
  /**
   * Get the service URL for the service with the specified service ID.
   * 
   * @param  serviceID  Service ID of the service.
   * @return  Service URL of the service. Returns null if not found.
   */
  public static URI getServiceURL(String serviceID) {
    for (int ct=0; ct<getNumServices(); ct++) {
      if (getServiceInformationTable().getServiceInformation(ct).getServiceID().equals(serviceID)) {
        return getServiceInformationTable().getServiceInformation(ct).getServiceURL();
      }
    }
    return null;
  }

  /**
   * Get the service URL ID the service with the specified service URL.
   * 
   * @param  serviceURL  Service URL of the service.
   * @return  Service ID of the service. Returns null if not found.
   */
  public static String getServiceID(URI serviceURL) {
    for (int ct=0; ct<getNumServices(); ct++) {
      if (getServiceInformationTable().getServiceInformation(ct).getServiceURL().equals(serviceURL)) {
        return getServiceInformationTable().getServiceInformation(ct).getServiceID();
      }
    }    
    return null;
  }
  
  /**
   * Get the name of a service.
   * 
   * @param  id  Either service ID (as a string) or service URL (as a URI object).
   * @return  The service name. Returns null if not found.
   */
  public static String getServiceName(Object id) {
    ServiceInformation serviceInformation;
    for (int ct=0; ct<getNumServices(); ct++) {
      serviceInformation = getServiceInformationTable().getServiceInformation(ct);
      if (serviceInformation.getServiceURL().equals(id) || serviceInformation.getServiceID().equals(id)) {
        return serviceInformation.getName();
      }
    }    
    return null;
  }

  /**
   * Get the owner of a service.
   * 
   * @param  id  Either service ID (as a string) or service URL (as a URI object).
   * @return  The service owner. Returns null if not found.
   */
  public static String getOwner(Object id) {
    ServiceInformation serviceInformation;
    for (int ct=0; ct<getNumServices(); ct++) {
      serviceInformation = getServiceInformationTable().getServiceInformation(ct);
      if (serviceInformation.getServiceURL().equals(id) || serviceInformation.getServiceID().equals(id)) {
        return serviceInformation.getOwner();
      }
    }        
    return null;
  }
  
  /**
   * Load properties file.
   * This can have the following (optional) properties which will override the defaults:
   * <br>
   * <br>start=<DD-MMM-YYYY>, e.g. 26-Apr-2004
   * <br>numdays=<int>, e.g. 7
   * <br>numdaysback=<int>, e.g. 1
   * <br>location=<path>, e.g. I:/tv-anytime/tvdata/tvadb/xml13/
   * <br>toplevelgroup=<crid>, e.g. crid://bbc.co.uk/groups
   * <br>groupfiles=<comma-separated list of filenames>, e.g. groups_itv_gr.xml, groups_itv_cr.xml
   * Note: Should be in normal xmlLocation and should be both Group Information and
   * Group Content Referencing.
   * 
   * @param  filename  Filename of the properties file.
   */
  private static void loadProperties(String filename) {
    // Create and load default properties
    Properties defaultProps = new Properties();
    try {
      FileInputStream in = new FileInputStream(filename);
      defaultProps.load(in);
      in.close();
    } catch (IOException ioe) {
      System.out.println("ScheduleManager: Error loading properties file: "+ioe.getMessage());
    }
    // Set properties
    String property = defaultProps.getProperty("start");
    if (property != null) {
      // Set start date
      //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
      try {
        Date startDate = dateFormat.parse(property);
        setStartDate(startDate);
      } catch (ParseException pe) {
        System.out.println("ScheduleManager: Error reading startDate from properties file. Date should be in the format <DD-MMM-YYYY>, e.g. 07-May-2003: "+pe.getMessage());
      }
    }
    property = defaultProps.getProperty("numdays");
    if (property != null) {
      // Set num days
      int numDaysI = -1;
      try {
        numDaysI = Integer.parseInt(property);
        setNumDays(numDaysI);
      }
      catch (NumberFormatException nfe) {
        System.out.println("ScheduleManager: Error reading numdays from properties file: "+nfe.getMessage());   
      }
    }
    property = defaultProps.getProperty("numdaysback");
    if (property != null) {
      // Set num days
      int numDaysbackI = -1;
      try {
      	numDaysbackI = Integer.parseInt(property);
        setNumDaysBack(numDaysbackI);
      }
      catch (NumberFormatException nfe) {
        System.out.println("ScheduleManager: Error reading numdaysback from properties file: "+nfe.getMessage());   
      }
    }    
    property = defaultProps.getProperty("location");
    if (property != null) {
      // Set location
      setXMLLocation(property);
    }
    property = defaultProps.getProperty("toplevelgroup");
    if (property != null) {
      // Set top level group
      try {
        setTopLevelGroupCRID(new ContentReference(property));
      } catch (TVAnytimeException tvae) {
        System.out.println(tvae.getMessage());
      }
    }
    property = defaultProps.getProperty("groupfiles");
    if (property != null) {
    	// Set filenames
    	StringTokenizer tokenizer = new StringTokenizer(property,",");
    	Vector files = new Vector();
    	while (tokenizer.hasMoreTokens()) {
    		files.addElement(tokenizer.nextToken().trim());
    		System.out.println("Adding "+files.lastElement());
    	}
			// Convert to array
    	groupFilenames = new String[files.size()];
    	for (int ct=0; ct<files.size(); ct++) {
    		groupFilenames[ct] = (String)files.elementAt(ct);
    	}
		}
    
  }
 
  /**
   * Read XML schedules. Resets all tables first.
   */
  public static void loadSchedules() {
    // Create list of programme metadata
    String dateMask = "", filename;
    GregorianCalendar selectedDate = (GregorianCalendar)Calendar.getInstance();
    DecimalFormat format = new DecimalFormat("00"); // Date always two digits
    selectedDate.setTime(ScheduleManager.getStartDate());
    // Modify selectedDate backwards if necessary
    selectedDate.add(Calendar.DATE,-1*numDaysBack);
    
    // Reset tables
    /*
    parser.getContentReferencingTable().removeAll();
    parser.getGroupInformationTable().removeAll();
    parser.getProgramInformationTable().removeAll();
    parser.getProgramLocationTable().removeAll();
    parser.getServiceInformationTable().removeAll();
    */
    ScheduleManager.clear();
    
    // Load SI file
    try {
      parser.parse(new File(xmlLocation+"ServiceInformation.xml"));
    } catch (NonFatalXMLException nfxe) {
      System.out.println("ScheduleManager: Error parsing SI file: "+nfxe.getMessage());
    } catch (TVAnytimeException tvae) {
      System.out.println("ScheduleManager: Error parsing SI file: "+tvae.getMessage());      
    } catch (IOException ioe) {
      System.out.println("ScheduleManager: Error parsing SI file: "+ioe.getMessage());      
    }
    ServiceInformationTable serviceInformationTable = parser.getServiceInformationTable();
    // Load files for all days
    for (int ct=0; ct<(numDays+Math.abs(numDaysBack)); ct++) {
  	  dateMask = selectedDate.get(Calendar.YEAR) + format.format(selectedDate.get(Calendar.MONTH)+1) + format.format(selectedDate.get(Calendar.DATE));
      
      for (int ct2=0; ct2<serviceInformationTable.getNumServiceInformations(); ct2++) {
        if (logging) System.out.println("Parsing "+serviceInformationTable.getServiceInformation(ct2).getServiceID()+" for "+dateMask);
        try {
          filename = xmlLocation+dateMask+serviceInformationTable.getServiceInformation(ct2).getServiceID()+"_pi.xml";
          parser.parse(filename);
        } catch (TVAnytimeException e) {
          System.out.println("XML parsing error: "+e.getMessage());
        } catch (IOException e) {
          System.out.println("XML parsing error: "+e.getMessage());          
        }
        
        try {          
          filename = xmlLocation+dateMask+serviceInformationTable.getServiceInformation(ct2).getServiceID()+"_pl.xml";
          parser.parse(filename);
        } catch (TVAnytimeException e) {
          System.out.println("XML parsing error: "+e.getMessage());
        } catch (IOException e) {
          System.out.println("XML parsing error: "+e.getMessage());          
        }

        try {          
          filename = xmlLocation+dateMask+serviceInformationTable.getServiceInformation(ct2).getServiceID()+"_cr.xml";
          parser.parse(filename);
        } catch (TVAnytimeException e) {
          System.out.println("XML parsing error: "+e.getMessage());
        } catch (IOException e) {
          System.out.println("XML parsing error: "+e.getMessage());          
        }        
        
        if (logging) System.out.println(parser.getProgramInformationTable().getNumProgramInformations());
      }
   	  selectedDate.set(Calendar.DATE,(selectedDate.get(Calendar.DATE)+1)); // Increment date
    }
    try {
      // Load group info 
    	for (int i = 0; i < groupFilenames.length; i++) {
    		parser.parse(new File(xmlLocation+groupFilenames[i]));
    	}
    } catch (Exception e) {
      System.out.println("XML parsing error: "+e.getMessage());
    }
  }
  
  /**
   * Loads the contents of a single file and adds the contents to the 
   * appropriate table.
   * 
   * @param  filename  Location of file to load
   */
  public static void loadFile(String filename) throws NonFatalXMLException, TVAnytimeException, IOException {
    parser.parse(new File(filename));
  }

  /**
   * Testing.
   * 
   * @param args
   */
  public static void main(String[] args) {
    ScheduleManager.initialise("schedule.properties");
    System.out.println("<<PI TABLE>>");
    System.out.println(ScheduleManager.getProgramInformationTable());
  }
  
}