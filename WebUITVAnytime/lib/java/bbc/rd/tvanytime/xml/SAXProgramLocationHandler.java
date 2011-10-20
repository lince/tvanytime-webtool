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


package bbc.rd.tvanytime.xml;

import bbc.rd.tvanytime.programLocation.*;
import bbc.rd.tvanytime.util.*;
import bbc.rd.tvanytime.*;

import org.xml.sax.*;
import javax.xml.parsers.*;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * SAXProgramLocationHandler: SAX event hadler used by SAXXMLParser to parse
 * ProgramLocationTable.
 * Uses state machine to track current position in document.
 * Error handling:
 *    Throws SAX XML exceptions
 *    Catches invalid TVAnytime data fields, unsets the variable and keep error
 *    message to end.
 *
 * Parsing profiles:
 * Two profiles are defined for which parts of the XML are parsed.
 *    BASIC: Only parses required/mandatory elements and attributes...
 *        Schedule
 *          ScheduleEvent
 *            CRID
 *
 *    STANDARD: Parses all of the currently used elements and attributes...
 *        Schedule
 *          ScheduleEvent
 *            CRID
 *            ProgramURL
 *            InstanceMetadataId
 *            PublishedTime
 *            PublishedDuration
 *            Live
 *            Repeat
 *            FirstShowing
 *            LastShowing
 *            PPV
 *
 * @author Tristan Ferne, BBC Research & Development, May 2002
 * @version 1.0
 * Modified T.Ferne August 2004
 */

class SAXProgramLocationHandler extends DefaultHandler {



  /**
   * Constants for program location table that define what state the parser is in.
   */
  private static final int UNKNOWN = 0;
  private static final int PROG_LOC_TABLE = 1;
  private static final int SCHEDULE = 2;

  private static final int SERVICE_ID = 3;
  private static final int SCHEDULE_EVENT = 4;
  private static final int PROGRAM = 5;
  private static final int PROGRAM_URL = 6;
  private static final int IMI = 7;
  
  private static final int PUBLISHED_START_TIME = 100;
  private static final int PUBLISHED_END_TIME = 101;
  private static final int PUBLISHED_DURATION = 102;
  private static final int LIVE = 103;
  private static final int REPEAT = 104;
  private static final int FIRST_SHOWING = 105;
  private static final int LAST_SHOWING = 106;
  private static final int FREE = 107;

  /**
   * Current state of parser.
   */
  private int state = PROG_LOC_TABLE;
  /**
   * Number of levels of unknown elements we are currently in.
   */
  private int unknownDepth = 0;
  /**
   * Last recognised state before entering unknown element.
   */
  private int savedState = PROG_LOC_TABLE;
  /**
   * Profile for XML parsing.
   */
  private int parseProfile = SAXXMLParser.STANDARD;

  /**
   * Reference to Vector of strings containing information about non-fatal exceptions.
   */
  private Vector exceptionStack;
  /**
   * Reference to clients ProgramLocationTable.
   */
  private ProgramLocationTable programLocationTable;
  /**
   * XML parser being used by parent that generates events to be handled.
   */
  private SAXParser parser;
  /**
   * Parent SAX event handler that asked this to handle events.
   */
  private DefaultHandler parent;
  /**
   * Current position of parser within document.
   */
  private Locator currentLocator;
  /**
   * Temporary string used to hold character data for an element.
   */
  private String characterData = "";
  /**
   * Temporary string used to hold attributes.
   */
  private String attribute = "";
  /**
   * Temporary TVAnytime objects.
   */
  private Schedule schedule;
  private ScheduleEvent scheduleEvent;
  private ProgramURL programURL;
  private InstanceMetadataId instanceMetadataId;
  private Duration publishedDuration;
  private Date publishedStartTime;
  private Date publishedEndTime;

  /**
   *
   */
  SAXProgramLocationHandler() {
  }

  /**
   * Tells this class to take over handling of SAX events from parser.
   *
   * @param   parent    Parent SAX scheduleEvent handler that asked this to handle events.
   * @param   parser    XML parser being used by parent that generates events to be handled.
   * @param   locator   Current location while parsing.
   * @param   exceptionStack   Vector containing Strings about non-fatal errors.
   * @param   parseProfile  Indication of profile of XML to be parsed.
   * @throws  SAXException  If content handler couldn't be set.
   */
  void handle(DefaultHandler parent, SAXParser parser, Locator locator, Vector exceptionStack, int parseProfile) throws SAXException {
    this.parent = parent;
    this.parser = parser;
    this.currentLocator = locator;
    this.exceptionStack = exceptionStack;
    this.parseProfile = parseProfile;
    parser.getXMLReader().setContentHandler(this);
  }


  /**
   * Sets ProgramLocationTable to populate with parsed data.
   *
   * @param   programLocationTable   Table to populate with parsed data.
   */
  void setTable(ProgramLocationTable programLocationTable) {
    this.programLocationTable = programLocationTable;
  }


  /**
   * Called during parsing when start of element found.
   * Implements state machine.
   */
  public void startElement (String uri, String name, String _qName, Attributes atts) throws SAXException {
    // Handle namespaces...
    String qName = filterPrefix(uri, name, _qName);
    // Reset character data array
    characterData = "";
    switch(state) {
      case PROG_LOC_TABLE:
        // In ProgramLocationTable looking for Schedule
        if ( qName.equals("Schedule") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = SCHEDULE;
          // Create new Schedule object
          schedule = new Schedule();
          // Add to client's table
          programLocationTable.addSchedule(schedule);
          // TJS: changes to serviceID (now serviceIDRef as attribute) in v1.2
          setScheduleAttributes(atts);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case SCHEDULE:
        // In Schedule looking for ScheduleEvent
        if ( qName.equals("ScheduleEvent") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = SCHEDULE_EVENT;
          // Create new ScheduleEvent object
          scheduleEvent = new ScheduleEvent();
          // Add to schedule
          schedule.addScheduleEvent(scheduleEvent);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case SCHEDULE_EVENT:
        // In ScheduleEvent looking for Program
        if ( qName.equals("Program") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = PROGRAM;
          // Set attributes
          setProgramAttributes(atts);
        }
        // In ScheduleEvent looking for ProgramURL or PublishedStartTime or PublishedDuration or Live, etc.
        else if ( qName.equals("ProgramURL") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = PROGRAM_URL;
          programURL = new ProgramURL();
          scheduleEvent.setProgramURL(programURL);
        }
        else if ( qName.equals("InstanceMetadataId") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = IMI;
          instanceMetadataId = new InstanceMetadataId();
          scheduleEvent.setInstanceMetadataId(instanceMetadataId);
        }
        else if ( qName.equals("PublishedStartTime") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = PUBLISHED_START_TIME;
          publishedStartTime = new Date();
          scheduleEvent.setPublishedStartTime(publishedStartTime);
          break;
        }
        else if ( qName.equals("PublishedEndTime") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = PUBLISHED_END_TIME;
          publishedEndTime = new Date();
          scheduleEvent.setPublishedEndTime(publishedEndTime);
          break;
        }
        else if ( qName.equals("PublishedDuration") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = PUBLISHED_DURATION;
          publishedDuration = new Duration();
          scheduleEvent.setPublishedDuration(publishedDuration);
          break;
        }
        else if ( qName.equals("Live") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = LIVE;
          // Set attributes
          setLiveAttributes(atts);
          break;
        }
        else if ( qName.equals("Repeat") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = REPEAT;
          // Set attributes
          setRepeatAttributes(atts);
          break;
        }
        else if ( qName.equals("FirstShowing") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = FIRST_SHOWING;
          // Set attributes
          setFirstShowingAttributes(atts);
          break;
        }
        else if ( qName.equals("LastShowing") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = LAST_SHOWING;
          // Set attributes
          setLastShowingAttributes(atts);
          break;
        }
        else if ( qName.equals("Free") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = FREE;
          // Set attributes
          setFreeAttributes(atts);
          break;
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case UNKNOWN:
        unknownDepth++;
        break;

      default:
        break;

    }
  }

  /**
   * Called during parsing when end of element found.
   */
  public void endElement (String uri, String name, String qName) throws SAXException {
    switch(state) {
      case PROG_LOC_TABLE:
        // Leaving table...
        // Set parser handler to parent when finished.
        parser.getXMLReader().setContentHandler(parent);
        break;

      case SCHEDULE:
        // Leaving Schedule
        state = PROG_LOC_TABLE;
        break;

      case SERVICE_ID:
        // Leaving Service ID
        state = SCHEDULE;
        break;

      case SCHEDULE_EVENT:
        // Leaving ScheduleEvent
        state = SCHEDULE;
        break;

      case PROGRAM:
        // Leaving Program
        state = SCHEDULE_EVENT;
        break;

      case PROGRAM_URL:
        // Leaving ProgramURL
        state = SCHEDULE_EVENT;
        programURL.setProgramURL(characterData.trim());
        break;

      case IMI:
        // Leaving InstanceMetadataId
        state = SCHEDULE_EVENT;
        try {
          instanceMetadataId.setInstanceMetadataId(characterData.trim());
        }
        catch (TVAnytimeException tvae) {
          addError("ScheduleEvent InstanceMetadataId: "+tvae.getMessage());
        }
        break;

      case PUBLISHED_START_TIME:
        // Leaving PublishedStartTime
        state = SCHEDULE_EVENT;
        try {
	        publishedStartTime.setTime(TimeToolbox.makeDate(characterData.trim()).getTime());
        }
        catch (TVAnytimeException tvae) {
          addError("ScheduleEvent PublishedStartTime: "+tvae.getMessage());
        }
        break;

      case PUBLISHED_END_TIME:
        // Leaving PublishedEndTime
    		state = SCHEDULE_EVENT;
        try {
	        publishedEndTime.setTime(TimeToolbox.makeDate(characterData.trim()).getTime());
        }
        catch (TVAnytimeException tvae) {
          addError("ScheduleEvent Description: "+tvae.getMessage());
        }
        break;

      case PUBLISHED_DURATION:
        // Leaving PublishedDuration
    		state = SCHEDULE_EVENT;
        try {
	        publishedDuration.setDuration(characterData.trim());
        }
        catch (TVAnytimeException tvae) {
          addError("ScheduleEvent PublishedDuration: "+tvae.getMessage());
        }
        break;

      case LIVE:
        // Leaving Live
    		state = SCHEDULE_EVENT;
        break;

      case REPEAT:
        // Leaving Repeat
        state = SCHEDULE_EVENT;
        break;

      case FIRST_SHOWING:
        // Leaving FirstShowing
        state = SCHEDULE_EVENT;
        break;

      case LAST_SHOWING:
        // Leaving LastShowing
        state = SCHEDULE_EVENT;
        break;

      case FREE:
        // Leaving Free
        state = SCHEDULE_EVENT;
        break;

      case UNKNOWN:
        // Leaving unknown element
        unknownDepth--;
        // If left unknown elements then return state to previous
        if (unknownDepth==0) state = savedState;
        break;

      default:

    }

  }


  public void characters(char[] ch, int start, int length) {
    switch (state) {
      case PROGRAM_URL:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case IMI:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case PUBLISHED_START_TIME:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case PUBLISHED_END_TIME:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case PUBLISHED_DURATION:
        characterData += String.copyValueOf(ch,start,length);
        break;
      default:
    }

  }


  public void error(SAXParseException e) throws SAXParseException {
    throw e;
  }

  /**
   * Indicates where the parser is in the document.
   */
  public void setDocumentLocator(Locator locator) {
    currentLocator = locator;
  }

  /**
   * Sets attributes for an ScheduleEvent object from <Program> element.
   */
  private void setProgramAttributes(Attributes atts) throws SAXException {
    // In Program so get attributes
    attribute = atts.getValue("crid");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.BASIC) ) {
      try {
        scheduleEvent.setCRID(new ContentReference(attribute));
      }
      catch (TVAnytimeException tvae) {
        throw new SAXException("TVAnytime exception: ScheduleEvent: "+tvae.getMessage() + " at line " +
              currentLocator.getLineNumber() + ", column " +
              currentLocator.getColumnNumber() );
      }
    }
  }

  private void setScheduleAttributes(Attributes atts) throws SAXException {
    // In Schedule so get attributes
    attribute = atts.getValue("serviceIDRef");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.BASIC) ) {
      schedule.setServiceID(attribute);
    }
    attribute = atts.getValue("start");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.BASIC) ) {
      try {
      	schedule.setStart(TimeToolbox.makeDate(attribute.trim()));
			} catch (TVAnytimeException e) {
				throw new SAXException("TVAnytime exception: ScheduleEvent: "+e.getMessage() + " at line " +
            currentLocator.getLineNumber() + ", column " +
            currentLocator.getColumnNumber() );
			}
    }
    attribute = atts.getValue("end");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.BASIC) ) {
    	try {
    		schedule.setEnd(TimeToolbox.makeDate(attribute.trim()));
			} catch (TVAnytimeException e) {
				throw new SAXException("TVAnytime exception: ScheduleEvent: "+e.getMessage() + " at line " +
            currentLocator.getLineNumber() + ", column " +
            currentLocator.getColumnNumber() );
			}    		
    }
    
  }

  /**
   * Sets Live attribute for an ScheduleEvent object.
   */
  private void setLiveAttributes(Attributes atts) throws SAXException {
    // In Live so get attributes
    attribute = atts.getValue("value");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      scheduleEvent.setLive(new Boolean(attribute)); // Sets to true if string equals (ignoring case) "true"
    }
  }

  /**
   * Sets Repeat attribute for an ScheduleEvent object.
   */
  private void setRepeatAttributes(Attributes atts) throws SAXException {
    // In Repeat so get attributes
    attribute = atts.getValue("value");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      scheduleEvent.setRepeat(new Boolean(attribute)); // Sets to true if string equals (ignoring case) "true"
    }
  }

  /**
   * Sets FirstShowing attribute for an ScheduleEvent object.
   */
  private void setFirstShowingAttributes(Attributes atts) throws SAXException {
    // In FirstShowing so get attributes
    attribute = atts.getValue("value");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      scheduleEvent.setFirstShowing(new Boolean(attribute)); // Sets to true if string equals (ignoring case) "true"
    }
  }

  /**
   * Sets LastShowing attribute for an ScheduleEvent object.
   */
  private void setLastShowingAttributes(Attributes atts) throws SAXException {
    // In LastShowing so get attributes
    attribute = atts.getValue("value");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      scheduleEvent.setLastShowing(new Boolean(attribute)); // Sets to true if string equals (ignoring case) "true"
    }
  }

  /**
   * Sets Free attribute for an ScheduleEvent object.
   */
  private void setFreeAttributes(Attributes atts) throws SAXException {
    // In Free so get attributes
    attribute = atts.getValue("value");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      scheduleEvent.setFree(new Boolean(attribute)); // Sets to true if string equals (ignoring case) "true"
    }
  }

  /**
   * Adds non-fatal error to global error string.
   */
  private void addError(String message) {
    exceptionStack.addElement(message + " at line " +
                currentLocator.getLineNumber() + ", column " +
                currentLocator.getColumnNumber() + "\n");
  }

  /**
   * The appropriate way to use the element (and attribute) local and qualified
   * names is to receive the prefix mapping trough the startPrefixMapping()
   * method of the contentHandler and process the names as described as by D.
   * Megginson: " Code using element names would normally match first for a null
   * URI, and if it's null then use qName otherwise use localName.".
   * 
   * @param  uri  URI for namespace
   * @param  localName  Element local name
   * @param  qName  Element qualified name
   * @return  Name to use
   */
  public String filterPrefix(String uri, String localName, String qName) {
    if(uri.length() != 0) return localName; 
    else { 
      int colonPosition = qName.indexOf(":"); 
      if(colonPosition==-1) return qName; 
      else return qName.substring(colonPosition+1,qName.length());
    }
  }

}