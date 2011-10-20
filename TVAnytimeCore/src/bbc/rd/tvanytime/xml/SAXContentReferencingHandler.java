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

import bbc.rd.tvanytime.*;
import bbc.rd.tvanytime.contentReferencing.*;
import bbc.rd.tvanytime.util.TimeToolbox;

import org.xml.sax.*;

import javax.xml.parsers.*;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * SAXContentReferencingHandler: SAX event hadler used by SAXXMLParser to parse
 * ContentReferencingTable.
 * Uses state machine to track current position in document.
 * Error handling:
 *    Throws SAX XML exceptions
 *    Catches invalid TVAnytime data fields, unsets the variable and keep error
 *    message to end.
 *
 * @author Tim Sargeant, BBC Research & Development, February 2003
 * @version 1.0
 */

class SAXContentReferencingHandler extends DefaultHandler {

  /**
   * Constants for ContentReferencingTable that define what state the parser is in.
   */
  private static final int UNKNOWN = 0;

  private static final int CONTENT_REF_TABLE = 1;

  private static final int RESULT = 2;

  private static final int CRID_RESULT = 3;
  private static final int LOCATIONS_RESULT = 4;

  private static final int LOCATOR = 5;
  private static final int CRID = 6;

  /**
   * Current state of parser.
   */
  private int state = CONTENT_REF_TABLE;
  /**
   * Number of levels of unknown elements we are currently in.
   */
  private int unknownDepth = 0;
  /**
   * Last recognised state before entering unknown element.
   */
  private int savedState = CONTENT_REF_TABLE;
  /**
   * Reference to Vector of strings containing information about non-fatal exceptions.
   */
  private Vector exceptionStack;
  /**
   * Reference to clients ContentReferencingTable.
   */
  private ContentReferencingTable contentReferencingTable;
  /**
   * XML parser being used by parent that generates events to be handled.
   */
  private SAXParser parser;
  /**
   * Parent SAX event handler that asked this to handle events.
   */
  private DefaultHandler parent;
  /**
   * Current position of parser within document. Note this Locator is a SAX locator not a TVA Locator!
   */
  private org.xml.sax.Locator currentLocator;
  /**
   * Temporary string used to hold character data for an element.
   */
  private String characterData = "";
  /**
   * Temporary string used to hold attributes.
   */
  private String attribute = "";
  /**
   * Temporary TVAnytime objects. Note the Locator is a TVA locator, not a SAX Locator!
   */
  private Result result;
  private CRIDResult cridResult;
  private LocationsResult locationsResult;
  private bbc.rd.tvanytime.contentReferencing.Locator locator;
  private ContentReference crid;

  /**
   *
   */
  SAXContentReferencingHandler() {
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
  void handle(DefaultHandler parent, SAXParser parser, org.xml.sax.Locator locator, Vector exceptionStack, int parseProfile) throws SAXException {
    this.parent = parent;
    this.parser = parser;
    this.currentLocator = locator;
    this.exceptionStack = exceptionStack;
    parser.getXMLReader().setContentHandler(this);
  }


  /**
   * Sets ContentReferencingTable to populate with parsed data.
   *
   * @param   contentReferencingTable   Table to populate with parsed data.
   */
  void setTable(ContentReferencingTable contentReferencingTable) {
    this.contentReferencingTable = contentReferencingTable;
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
      case CONTENT_REF_TABLE:
        // In ContentReferencingTable looking for Result
        if ( qName.equals("Result") ) {
          state = RESULT;
          // Create new Result object
          result = new Result();
          // Add to client's table
          setResultAttributes(atts);
          // Must be added after attributes have set CRID
          contentReferencingTable.addResult(result);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case RESULT:
        // In Result looking for CRIDResult or LocationsResult
        if ( qName.equals("CRIDResult") ) {
          state = CRID_RESULT;
          // Create new CRIDResult object
          cridResult = new CRIDResult();
          // Add to result object
          result.setCRIDResult(cridResult);
        }
        else if ( qName.equals("LocationsResult") ) {
          state = LOCATIONS_RESULT;
          // Create new LocationsResult object
          locationsResult = new LocationsResult();
          // Add to result object
          result.setLocationsResult(locationsResult);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case CRID_RESULT:
        // In CRIDResult looking for CRID
        if ( qName.equals("Crid") ) {
          state = CRID;
          // Create new CRID object
          crid = new ContentReference();
          // Add to result object
          cridResult.addCRID(crid);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case LOCATIONS_RESULT:
        // In LocationsResult looking for Locator (we assume always a DVBLocator!)
        if ( qName.equals("Locator") ) {
          state = LOCATOR;
          // Create new Locator object
          locator = new bbc.rd.tvanytime.contentReferencing.Locator();
          setLocatorAttributes(atts);
          // Add to result object
          locationsResult.addLocator(locator);
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
      case CONTENT_REF_TABLE:
        // Leaving table...
        // Set parser handler to parent when finished.
        parser.getXMLReader().setContentHandler(parent);
        break;

      case RESULT:
        // Leaving Result
        state = CONTENT_REF_TABLE;
        break;

      case CRID_RESULT:
        // Leaving CRIDResult
        state = RESULT;
        break;

      case LOCATIONS_RESULT:
        // Leaving LocationsResult
        state = RESULT;
        break;

      case CRID:
        // Leaving CRID
        state = CRID_RESULT;
				try {
					crid.setCRID(characterData.trim());
				}
				catch (TVAnytimeException tvae) {
					addError("CRID: "+tvae.getMessage());
				}
        break;

      case LOCATOR:
        // Leaving Locator
        state = LOCATIONS_RESULT;
        locator.setLocator(characterData.trim());
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
      case CRID:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case LOCATOR:
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
  public void setDocumentLocator(org.xml.sax.Locator locator) {
    currentLocator = locator;
  }

  /**
   * Sets attributes for an Result object.
   */
	private void setResultAttributes(Attributes atts) throws SAXException {
		// get attributes
		attribute = atts.getValue("CRID");
		if (attribute != null) {
			try {
				result.setCRID(new ContentReference(attribute));
			} catch (TVAnytimeException tvae) {
				throw new SAXException("TVAnytime exception: Result: "
						+ tvae.getMessage() + " at line "
						+ currentLocator.getLineNumber() + ", column "
						+ currentLocator.getColumnNumber());
			}
		}

		attribute = atts.getValue("status");
		if (attribute != null) {
			try {
				result.setStatus(attribute);
			} catch (TVAnytimeException tvae) {
				throw new SAXException("TVAnytime exception: Result: "
						+ tvae.getMessage() + " at line "
						+ currentLocator.getLineNumber() + ", column "
						+ currentLocator.getColumnNumber());
			}
		}

		attribute = atts.getValue("complete");
		if (attribute != null) {
			if (attribute.equals("true"))
				result.setComplete(true);
			else if (attribute.equals("false"))
				result.setComplete(false);
		}

		attribute = atts.getValue("acquire");
		if (attribute != null) {
			try {
				result.setAcquire(attribute);
			} catch (TVAnytimeException tvae) {
				throw new SAXException("TVAnytime exception: Result: "
						+ tvae.getMessage() + " at line "
						+ currentLocator.getLineNumber() + ", column "
						+ currentLocator.getColumnNumber());
			}
		}

		// NOTE: does not currently handle reresolveDate...
		attribute = atts.getValue("reresolveDate");
		if (attribute != null) {
			try {
				result.setReresolveDate(TimeToolbox.makeDate(attribute.trim()));
			} catch (TVAnytimeException tvae) {
				throw new SAXException("TVAnytime exception: Result: "
						+ tvae.getMessage() + " at line "
						+ currentLocator.getLineNumber() + ", column "
						+ currentLocator.getColumnNumber());
			}
		}
	}

	/**
	 * Sets attributes for an Result object.
	 */
	private void setLocatorAttributes(Attributes atts) throws SAXException {
		// get attributes
		attribute = atts.getValue("instanceMetadataId");
		if (attribute != null) {
			try {
				locator
						.setInstanceMetadataId(new bbc.rd.tvanytime.InstanceMetadataId(
								attribute));
			} catch (TVAnytimeException tvae) {
				throw new SAXException("TVAnytime exception: Locator: "
						+ tvae.getMessage() + " at line "
						+ currentLocator.getLineNumber() + ", column "
						+ currentLocator.getColumnNumber());
			}
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