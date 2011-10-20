/**
 * Copyright 2003 BBC Research & Development
 *
 * This file is part of the BBC R&D TVAnytime Java API.
 *
 * The BBC R&D TVAnytime Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * The BBC R&D TVAnytime Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the BBC R&D TVAnytime Java API; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */


package bbc.rd.tvanytime.dvb;

import bbc.rd.tvanytime.*;
import bbc.rd.tvanytime.xml.*;
import bbc.rd.tvanytime.programInformation.*;
import bbc.rd.tvanytime.programLocation.*;
import bbc.rd.tvanytime.groupInformation.*;
import bbc.rd.tvanytime.serviceInformation.*;
import bbc.rd.tvanytime.contentReferencing.*;
import bbc.rd.tvanytime.search.*;

import java.io.*;
import java.util.*;

/**
 * Handles TVA fragments. Parses fragments and updates/adds appropriate parts
 * to the TVAnytime table objects contained in this class.
 *
 * @author Tristan Ferne, BBC Research & Development, March 2003
 * @version 1.0
 */
public class TVAFragmentManager  {

  /**
   * ProgramInformationTable.
   */
  private ProgramInformationTable programInformationTable;

  /**
   * GroupInformationTable.
   */
  private GroupInformationTable groupInformationTable;

  /**
   * ProgramLocationTable.
   */
  private ProgramLocationTable programLocationTable;

  /**
   * ServiceInformationTable.
   */
  private ServiceInformationTable serviceInformationTable;

  /**
   * ContentReferencingTable.
   */
  private ContentReferencingTable contentReferencingTable;

  /**
   * XML parser used.
   */
  private SAXXMLParser parser;


  /**
   * Constructor.
   * Creates tables and parser.
   */
  public TVAFragmentManager() {
    programInformationTable = new ProgramInformationTable();
    groupInformationTable = new GroupInformationTable();
    programLocationTable = new ProgramLocationTable();
    serviceInformationTable = new ServiceInformationTable();
    contentReferencingTable = new ContentReferencingTable();

    try {
      parser = new SAXXMLParser();
      ((SAXXMLParser)parser).setParseProfile(SAXXMLParser.STANDARD);
    }
    catch (TVAnytimeException tvae) {
      System.out.println(tvae.getMessage());
    }
  }

  /**
   * Adds ProgramInformation fragment. If it includes new ProgramInformation elements
   * then adds them, if it contains updates then it just replaces the old
   * ProgramInformation elements (using CRID to test for equality).
   *
   * @param  fragment  File containing TVA fragment.
   */
  public void addProgramInformationFragment(File fragment) {
    // Clear parser PI table
    parser.getProgramInformationTable().removeAll();
    // Parse file
    try {
      parser.parse(fragment);
    }
    catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
    catch (NonFatalXMLException tvae) {
      System.out.println(tvae.getMessage());
    }
    catch (TVAnytimeException tvae) {
      System.out.println(tvae.getMessage());
    }
    // Add/update contents of parser PI table to this PI table
    ProgramInformation progInfo;
    Vector vector;
    for (int ct=0; ct<parser.getProgramInformationTable().getNumProgramInformations(); ct++) {
      progInfo = parser.getProgramInformationTable().getProgramInformation(ct);
      try {
        vector = programInformationTable.getProgramInformation(progInfo.getProgramID());
        if (vector.size() > 0) {
          // Contains this program so remove old one and add new one
          // Hashtable does this anyway
          programInformationTable.addProgramInformation(progInfo);
        }
        else {
          // Doesn't contain this program so just add
          programInformationTable.addProgramInformation(progInfo);
        }
      } catch (SearchInterfaceNotSupportedException sinse) {
        System.out.println(sinse.getMessage());
      }
    }
  }

  /**
   * Adds GroupInformation fragment. If it includes new GroupInformation elements
   * then adds them, if it contains updates then it just replaces the old
   * GroupInformation elements (using CRID to test for equality).
   *
   * @param  fragment  File containing TVA fragment.
   */
  public void addGroupInformationFragment(File fragment) {
    // Clear parser GI table
    parser.getGroupInformationTable().removeAll();
    // Parse file
    try {
      parser.parse(fragment);
    }
    catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
    catch (NonFatalXMLException tvae) {
      System.out.println(tvae.getMessage());
    }
    catch (TVAnytimeException tvae) {
      System.out.println(tvae.getMessage());
    }
    // Add/update contents of parser GI table to this GI table
    GroupInformation groupInfo;
    for (int ct=0; ct<parser.getGroupInformationTable().getNumGroupInformations(); ct++) {
      groupInfo = parser.getGroupInformationTable().getGroupInformation(ct);
      // Check whether this group exists in current table
      boolean exists = false;
      for (int ct2=0; ct2<groupInformationTable.getNumGroupInformations(); ct2++) {
        if (groupInformationTable.getGroupInformation(ct2).getGroupId().equals(groupInfo.getGroupId())) {
          exists = true;
          break;
        }
      }
      if (exists) {
        // Contains this group so remove old one and add new one
        groupInformationTable.removeGroupInformation(groupInfo);
        groupInformationTable.addGroupInformation(groupInfo);
      }
      else {
        // Doesn't contain this group so just add
        groupInformationTable.addGroupInformation(groupInfo);
      }
    }
  }

  /**
   * Adds ServiceInformation fragment. If it includes new ServiceInformation elements
   * then adds them, if it contains updates then it just replaces the old
   * ServiceInformation elements (using CRID to test for equality).
   *
   * @param  fragment  File containing TVA fragment.
   */
  public void addServiceInformationFragment(File fragment) {
    // Clear parser SI table
    parser.getServiceInformationTable().removeAll();
    // Parse file
    try {
      parser.parse(fragment);
    }
    catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
    catch (NonFatalXMLException tvae) {
      System.out.println(tvae.getMessage());
    }
    catch (TVAnytimeException tvae) {
      System.out.println(tvae.getMessage());
    }
    // Add/update contents of parser SI table to this SI table
    ServiceInformation serviceInfo;
    for (int ct=0; ct<parser.getServiceInformationTable().getNumServiceInformations(); ct++) {
      serviceInfo = parser.getServiceInformationTable().getServiceInformation(ct);
      // Check whether this service exists in current table
      boolean exists = false;
      for (int ct2=0; ct2<serviceInformationTable.getNumServiceInformations(); ct2++) {
        if (serviceInformationTable.getServiceInformation(ct2).getServiceID().equals(serviceInfo.getServiceID())) {
          exists = true;
          break;
        }
      }
      if (exists) {
        // Contains this service so remove old one and add new one
        serviceInformationTable.removeServiceInformation(serviceInfo);
        serviceInformationTable.addServiceInformation(serviceInfo);
      }
      else {
        // Doesn't contain this service so just add
        serviceInformationTable.addServiceInformation(serviceInfo);
      }
    }
  }

  /**
   * Adds ContentReferencing fragment. If it includes new Result elements
   * then adds them, if it contains updates then it just replaces the old
   * Result elements (using CRID to test for equality).
   *
   * @param  fragment  File containing TVA fragment.
   */
  public void addContentReferencingFragment(File fragment) {
    // Clear parser CR table
    parser.getContentReferencingTable().removeAll();
    // Parse file
    try {
      parser.parse(fragment);
    }
    catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
    catch (NonFatalXMLException tvae) {
      System.out.println(tvae.getMessage());
    }
    catch (TVAnytimeException tvae) {
      System.out.println(tvae.getMessage());
    }
    // Add/update contents of parser CR table to this CR table
    Result result;
    for (int ct=0; ct<parser.getContentReferencingTable().getNumResults(); ct++) {
      result = parser.getContentReferencingTable().getResult(ct);
      // Check whether this result exists in current table
      boolean exists = false;
      for (int ct2=0; ct2<contentReferencingTable.getNumResults(); ct2++) {
        if (contentReferencingTable.getResult(ct2).getCRID().equals(result.getCRID())) {
          exists = true;
          break;
        }
      }
      if (exists) {
        // Contains this result so remove old one and add new one
        contentReferencingTable.removeResult(result);
        contentReferencingTable.addResult(result);
      }
      else {
        // Doesn't contain this result so just add
        contentReferencingTable.addResult(result);
      }
    }
  }

  /**
   * Adds ProgramLocation fragment. TVAFragmentManager has a ProgramLocation table
   * that will include a Schedule object for each service that is discovered while
   * parsing. Assumes that fragment ony contains a single Schedule element for
   * a single service.
   *
   * If a fragment includes new ScheduleEvent elements then it adds them, if it
   * contains updates then it removes any existing ScheduleEvents that are
   * overlapped by the new ScheduleEvent.
   * <Overlapped> means that:
   * The new ScheduleEvent's start time is >= an existing event's start time AND
   * start time < the event's end time
   * OR
   * The new ScheduleEvent's end time is > an existing start time AND end time
   * <= existing end time.
   * OR
   * The new ScheduleEvent's start time is < an existing start time AND end time
   * > existing end time.
   *
   * Note that ScheduleEvents within Schedules are not guaranteed to be sorted.
   *
   * @param  fragment  File containing TVA fragment.
   */
  public void addProgramLocationFragment(File fragment) {
    // Clear parser PL table
    parser.getProgramLocationTable().removeAll();
    // Parse file
    try {
      parser.parse(fragment);
    }
    catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
    catch (NonFatalXMLException tvae) {
      System.out.println(tvae.getMessage());
    }
    catch (TVAnytimeException tvae) {
      System.out.println(tvae.getMessage());
    }
    // Add/update contents of parser PL table to this PL table
    Schedule parserSchedule; // Schedule from parser
    Schedule managerSchedule = null; // Schedule for same service as current parser schedule
    for (int ct=0; ct<parser.getProgramLocationTable().getNumSchedules(); ct++) {
      // Check if TVAFragmentManager has a schedule for this service.
      parserSchedule = parser.getProgramLocationTable().getSchedule(ct);
      boolean exists = false;
      for (int ct2=0; ct2<programLocationTable.getNumSchedules(); ct2++) {
        if (programLocationTable.getSchedule(ct2).getServiceID().equals(parserSchedule.getServiceID())) {
          exists = true;
          managerSchedule = programLocationTable.getSchedule(ct2); // Set schedule to use
          break;
        }
      }
      // Make one if it doesn't
      if (!exists) {
        managerSchedule = new Schedule();
        managerSchedule.setServiceID(parserSchedule.getServiceID());
        programLocationTable.addSchedule(managerSchedule);
      }

      // Loop through all ScheduleEvents in parser Schedule
      ScheduleEvent parserEvent;
      long startTime, endTime, parserStartTime, parserEndTime;
      for (int ct2=0; ct2<parserSchedule.getNumScheduleEvents(); ct2++) {
        parserEvent = parserSchedule.getScheduleEvent(ct2);
        // Check if current Schedule has events that overlap with this ScheduleEvent
        if (managerSchedule != null) {
          for (int ct3=0; ct3<managerSchedule.getNumScheduleEvents(); ct3++) {
            startTime = managerSchedule.getScheduleEvent(ct3).getPublishedStartTime().getTime();
            endTime = startTime + managerSchedule.getScheduleEvent(ct3).getPublishedDuration().getDurationInMsec();
            parserStartTime = parserEvent.getPublishedStartTime().getTime();
            parserEndTime = parserStartTime + parserEvent.getPublishedDuration().getDurationInMsec();
            if (parserStartTime >= startTime && parserStartTime < endTime) {
              // Start time of new event is within old event's duration
              // so remove overlapping event
              managerSchedule.removeScheduleEvent(managerSchedule.getScheduleEvent(ct3));
            }
            else if (parserEndTime > startTime && parserEndTime <= endTime) {
              // End time of new event is within old event's duration
              // so remove overlapping event
              managerSchedule.removeScheduleEvent(managerSchedule.getScheduleEvent(ct3));
            }
            else if (parserStartTime < startTime && parserEndTime > endTime) {
              // New event completely covers old event
              // so remove overlapping event
              managerSchedule.removeScheduleEvent(managerSchedule.getScheduleEvent(ct3));
            }
          }
          // Having removed any overlaps (if necessary) we can add the new ScheduleEvent
          managerSchedule.addScheduleEvent(parserEvent);
        }
      }
    }
  }



  /**
   * Get ProgramInformationTable.
   */
  public ProgramInformationTable getProgramInformationTable() {
    return programInformationTable;
  }

  /**
   * Get GroupInformationTable.
   */
  public GroupInformationTable getGroupInformationTable() {
    return groupInformationTable;
  }

  /**
   * Get ProgramLocationTable.
   */
  public ProgramLocationTable getProgramLocationTable() {
    return programLocationTable;
  }

  /**
   * Get ServiceInformationTable.
   */
  public ServiceInformationTable getServiceInformationTable() {
    return serviceInformationTable;
  }

  /**
   * Get ContentReferencingTable.
   */
  public ContentReferencingTable getContentReferencingTable() {
    return contentReferencingTable;
  }


  /**
   *
   */
  public static void main(String[] args) {
    TVAFragmentManager tvaFragmentManager = new TVAFragmentManager();

    // Get directory listing of fragments
    File directory = new File("I:/tv-anytime/e2e/data/");
    //File[] dirList = directory.listFiles();
    String[] stringList = directory.list();
    File[] dirList = new File[stringList.length];
    for (int ct=0; ct<stringList.length; ct++) {
      dirList[ct] = new File(stringList[ct]);
    }

    if (dirList!=null) {
      // Loop through the fragments
      for (int ct=0; ct<dirList.length; ct++) {
        if (dirList[ct].getName().startsWith("pit")) {
          System.out.println("Adding "+dirList[ct].getName());
          // Add each one
          tvaFragmentManager.addProgramInformationFragment(dirList[ct]);
        }
        else if (dirList[ct].getName().startsWith("git")) {
          System.out.println("Adding "+dirList[ct].getName());
          // Add each one
          tvaFragmentManager.addGroupInformationFragment(dirList[ct]);
        }
        else if (dirList[ct].getName().startsWith("sit")) {
          System.out.println("Adding "+dirList[ct].getName());
          // Add each one
          tvaFragmentManager.addServiceInformationFragment(dirList[ct]);
        }
        else if (dirList[ct].getName().startsWith("crt")) {
          System.out.println("Adding "+dirList[ct].getName());
          // Add each one
          tvaFragmentManager.addContentReferencingFragment(dirList[ct]);
        }
        else if (dirList[ct].getName().startsWith("plt")) {
          System.out.println("Adding "+dirList[ct].getName());
          // Add each one
          tvaFragmentManager.addProgramLocationFragment(dirList[ct]);
        }
      }

      // Show ProgramInformationTable
      System.out.println("ProgramInformationTable contains "+tvaFragmentManager.getProgramInformationTable().getNumProgramInformations()+" programmes");
      System.out.println("First programme is:");
      System.out.println(tvaFragmentManager.getProgramInformationTable().getProgramInformation(0));
      // Show GroupInformationTable
      System.out.println("GroupInformationTable contains "+tvaFragmentManager.getGroupInformationTable().getNumGroupInformations()+" groups");
      System.out.println("First group is:");
      System.out.println(tvaFragmentManager.getGroupInformationTable().getGroupInformation(0));
      // Show ServiceInformationTable
      System.out.println("ServiceInformationTable contains "+tvaFragmentManager.getServiceInformationTable().getNumServiceInformations()+" services");
      System.out.println("First service is:");
      System.out.println(tvaFragmentManager.getServiceInformationTable().getServiceInformation(0));
      // Show ContentReferencingTable
      System.out.println("ContentReferencingTable contains "+tvaFragmentManager.getContentReferencingTable().getNumResults()+" results");
      System.out.println("First result is:");
      System.out.println(tvaFragmentManager.getContentReferencingTable().getResult(0));
      // Show ProgramLocationTable
      System.out.println("ProgramLocationTable contains "+tvaFragmentManager.getProgramLocationTable().getNumSchedules()+" schedules");
      int total = 0;
      for (int ct=0; ct<tvaFragmentManager.getProgramLocationTable().getNumSchedules(); ct++) {
        total += tvaFragmentManager.getProgramLocationTable().getSchedule(ct).getNumScheduleEvents();
      }
      System.out.println("...and "+total+" schedule events");
      System.out.println("First schedule/first event is:");
      System.out.println(tvaFragmentManager.getProgramLocationTable().getSchedule(0).getScheduleEvent(0));


    }
  }
}