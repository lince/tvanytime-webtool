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


package bbc.rd.tvanytime.xml;

import bbc.rd.tvanytime.serviceInformation.*;
import bbc.rd.tvanytime.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * SAXServiceInformationHandler: SAX event handler used by SAXXMLParser to parse
 * ServiceInformationTable.
 * Uses state machine to track current position in document.
 * Error handling:
 *    Throws SAX XML exceptions
 *    Catches invalid TVAnytime data fields, unsets the variable and keep error
 *    message to end.
 *
 * Parsing profiles:
 * Two profiles are defined for which parts of the XML are parsed.
 *    BASIC: Only parses required/mandatory elements and attributes...
 *        Service
 *          serviceID
 *          Name
 *
 *    STANDARD: Parses all of the currently used elements and attributes...
 *        Service
 *          serviceID
 *          Name
 *          serviceURL
 *          owner
 *
 * @author Tristan Ferne, BBC Research & Development, February 2003
 * @version 1.0
 */
public class SAXServiceInformationHandler extends DefaultHandler {

  /**
   * Constants for program location table that define what state the parser is in.
   */
  private static final int UNKNOWN = 0;
  private static final int SERVICE_INFO_TABLE = 1;
  private static final int SERVICE_INFO = 2;
  private static final int SERVICE_URL = 3;
  private static final int SERVICE_NAME = 4;
  private static final int SERVICE_OWNER = 5;

  /**
   * Current state of parser.
   */
  private int state = SERVICE_INFO_TABLE;
  /**
   * Number of levels of unknown elements we are currently in.
   */
  private int unknownDepth = 0;
  /**
   * Last recognised state before entering unknown element.
   */
  private int savedState = SERVICE_INFO_TABLE;
  /**
   * Profile for XML parsing.
   */
  private int parseProfile = SAXXMLParser.STANDARD;

  /**
   * Reference to Vector of strings containing information about non-fatal exceptions.
   */
  private Vector exceptionStack;
  /**
   * Reference to clients ServiceInformationTable.
   */
  private ServiceInformationTable serviceInformationTable;
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
  private ServiceInformation serviceInformation;
  private URI serviceURL;

  /**
   * Constructor.
   */
  public SAXServiceInformationHandler() {
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
   * Sets ServiceInformationTable to populate with parsed data.
   *
   * @param   serviceInformationTable   Table to populate with parsed data.
   */
  void setTable(ServiceInformationTable serviceInformationTable) {
    this.serviceInformationTable = serviceInformationTable;
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
      case SERVICE_INFO_TABLE:
        // In ServiceInformationTable looking for ServiceInformation
        if ( qName.equals("ServiceInformation") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = SERVICE_INFO;
          // Create new ServiceInformation object
          serviceInformation = new ServiceInformation();
          // Add to client's table
          serviceInformationTable.addServiceInformation(serviceInformation);
          setServiceInformationAttributes(atts); // i.e. serviceID
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case SERVICE_INFO:
        // In ServiceInformation looking for serviceURL
        if ( qName.equals("ServiceURL") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = SERVICE_URL;
        }
        else if ( qName.equals("Name") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = SERVICE_NAME;
        }
        else if ( qName.equals("Owner") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = SERVICE_OWNER;
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
      case SERVICE_INFO_TABLE:
        // Leaving table...
        // Set parser handler to parent when finished.
        parser.getXMLReader().setContentHandler(parent);
        break;

      case SERVICE_INFO:
        // Leaving ServiceInfo
        state = SERVICE_INFO_TABLE;
        break;

      case SERVICE_URL:
        // Leaving ServiceURL
        state = SERVICE_INFO;
        try {
          serviceURL = new URI(characterData.trim());
          serviceInformation.setServiceURL(serviceURL);
        }
        catch (TVAnytimeException tvae) {
          addError("ServiceInformation: ServiceURL: "+tvae.getMessage());
        }
        break;

      case SERVICE_NAME:
        // Leaving ServiceName
        state = SERVICE_INFO;
        serviceInformation.setName(characterData.trim());
        break;

      case SERVICE_OWNER:
        // Leaving ServiceOwner
        state = SERVICE_INFO;
        serviceInformation.setOwner(characterData.trim());
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
      case SERVICE_URL:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case SERVICE_NAME:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case SERVICE_OWNER:
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
   * Sets attributes for a ServiceInformation object.
   */
  private void setServiceInformationAttributes(Attributes atts) throws SAXException {
    // In Program so get attributes
    attribute = atts.getValue("serviceId");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.BASIC) ) {
      serviceInformation.setServiceID(attribute);
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