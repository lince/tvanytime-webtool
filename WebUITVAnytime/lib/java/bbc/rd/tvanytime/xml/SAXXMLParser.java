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

import bbc.rd.tvanytime.programLocation.ProgramLocationTable;
import bbc.rd.tvanytime.serviceInformation.ServiceInformationTable;
import bbc.rd.tvanytime.groupInformation.GroupInformationTable;
import bbc.rd.tvanytime.segmentInformation.SegmentInformationTable;
import bbc.rd.tvanytime.contentReferencing.ContentReferencingTable;

import bbc.rd.tvanytime.search.*;
import bbc.rd.tvanytime.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;

import java.io.*;
import java.util.Vector;
import bbc.rd.tvanytime.programInformation.ProgramInformationTable;
import bbc.rd.tvanytime.contentReferencing.Result;


/**
 *
 * SAXXMLParser: Implementation of XMLParser Interface using SAX.
 * Uses state machine to track current position in document.
 * All TVAnytime objects that are parsed from files are added to one of the four
 * tables (ProgramInformation, ProgamLocation, GroupInformation, SegmentInformation).
 * <BR>
 * <P><I>Error handling:</I></P>
 * <UL>
 * <LI>Throws SAX XML exceptions</LI>
 * <LI>Catches invalid TVAnytime data fields, unsets the variable and keep error
 * message to end.</LI>
 * <BR>
 * <BR>
 * <P><I>Parsing profiles:</I></P>
 * <P>Two profiles are defined for which parts of the XML are parsed.<P/>
 * <UL>
 * <LI>   BASIC: Only parses required/mandatory elements and attributes...
 *          <UL><LI>ProgramInformation
 *              <UL><LI>BasicDescription
 *                </UL><LI><Title</LI></UL>
 *              </LI></UL>
 *          </LI></UL>
 * </LI>
 * </UL>
 * <UL>
 *    <LI>STANDARD: Parses all of the currently used elements and attributes...
 *        <UL><LI>ProgramInformation
 *          <UL><LI>BasicDescription
 *            <UL>
 *            <LI>Title</LI>
 *            <LI>Synopsis</LI>
 *            <LI>Genre</LI>
 *            </UL>
 *          </LI></UL>
 *          <UL><LI>AVAttributes
 *            <UL><LI>AudioAttributes
 *              <UL><LI>NumOfChannels</LI></UL>
 *            </LI>
 *            <LI>VideoAttributes
 *              <UL><LI>AspectRatio</LI></UL>
 *            </LI></UL>
 *          </LI></UL>
 *          <LI>MemberOf</LI>
 *        </LI></UL>
 *    </LI>
 * </UL>
 * @author Tristan Ferne, BBC Research & Development, April 2002
 * @version 1.0
 */

public class SAXXMLParser extends DefaultHandler implements MetadataSearch, XMLParser {

  /**
   * Basic profile for XML parsing. Parses only the elements and attributes that
   * are mandatory/required.
   */
  public static final int BASIC = 1;
  /**
   * Standard profile for XML parsing. Parses all the elements and attributes.
   */
  public static final int STANDARD = 2;

  /**
   * Profile for XML parsing.
   */
  private int parseProfile = STANDARD;

  /**
   * Constants that define what state the parser is currently in.
   */
  private static final int UNKNOWN = 0;
  private static final int INITIAL = 1;

  /**
   * Current state of parser.
   */
  private int state = INITIAL;
  /**
   * Number of levels of unknown elements we are currently in.
   */
  private int unknownDepth = 0;
  /**
   * Last recognised state before entering unknown element.
   */
  private int savedState = INITIAL;

  /**
   * Whether the parser will validate the XML.
   */
  private boolean validating = false;

  /**
   * Parser object.
   */
  private SAXParser saxParser;

  /**
   * Temporary string used to hold character data for an element.
   */
  private String characterData = "";

  /**
   * Temporary Vector containing information about non-fatal exceptions.
   */
  private Vector exceptionStack;

  /**
   * Current position of parser within document.
   */
  private Locator currentLocator;

  // Temp variable for getting attributes
  private String attribute = "";

  /**
   * SAX event handler for ProgramInformationTable.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation1> bbc.rd.tvanytime.SAXProgramInformationHandler
   */
  private SAXProgramInformationHandler saxProgramInformationHandler;

  /**
   * SAX event handler for ProgramLocationTable.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation2> bbc.rd.tvanytime.SAXProgramLocationHandler
   */
  private SAXProgramLocationHandler saxProgramLocationHandler;

  /**
   * SAX event handler for GroupInformationTable.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation4> bbc.rd.tvanytime.SAXGroupInformationHandler
   */
  private SAXGroupInformationHandler saxGroupInformationHandler;

  /**
   * SAX event handler for ServiceInformationTable.
   */
  private SAXServiceInformationHandler saxServiceInformationHandler;


  /**
   * SAX event handler for ContentReferencingTable.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation6> bbc.rd.tvanytime.SAXContentReferencingHandler
   */
  private SAXContentReferencingHandler saxContentReferencingHandler;

  /**
   * SAX event handler for SegmentInformationTable.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation8> bbc.rd.tvanytime.SAXSegmentInformationHandler
   */
  private SAXSegmentInformationHandler saxSegmentInformationHandler;

  /**
   * ProgramInformationTable for parser.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation10> bbc.rd.tvanytime.programInformation.ProgramInformationTable
   */
  private ProgramInformationTable programInformationTable;

  /**
   * ProgramLocationTable for parser.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation11> bbc.rd.tvanytime.programLocation.ProgramLocationTable
   */
  private ProgramLocationTable programLocationTable;

  /**
   * SegmentInformationTable for parser.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation12> bbc.rd.tvanytime.segmentInformation.SegmentInformationTable
   */
  private SegmentInformationTable segmentInformationTable;

  /**
   * GroupInformationTable for parser.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation13> bbc.rd.tvanytime.groupInformation.GroupInformationTable
   */
  private GroupInformationTable groupInformationTable;

  /**
   * ServiceInformationTable for parser.
   */
  private ServiceInformationTable serviceInformationTable;


  /**
   * ContentReferencingTable for parser.
   *
   * @association <bbc.rd.tvanytime.JavaAssociation13> bbc.rd.tvanytime.contentReferencing.ContentReferencingTable
   */
  private ContentReferencingTable contentReferencingTable;


  /**
   * Constructor creates SAX parser.
   */
  public SAXXMLParser() throws TVAnytimeException {
    try {
      // Use the default (non-validating) parser
      SAXParserFactory factory = SAXParserFactory.newInstance();
      saxParser = factory.newSAXParser();
    }
    catch (FactoryConfigurationError fce) {
      throw new TVAnytimeException(fce.getMessage());
    }
    catch (SAXException se) {
      throw new TVAnytimeException(se.getMessage());
    }
    catch (ParserConfigurationException pce) {
      throw new TVAnytimeException(pce.getMessage());
    }

    // Create handlers
    saxProgramInformationHandler = new SAXProgramInformationHandler();
    saxProgramLocationHandler = new SAXProgramLocationHandler();
    saxGroupInformationHandler = new SAXGroupInformationHandler();
    saxSegmentInformationHandler = new SAXSegmentInformationHandler();
    saxContentReferencingHandler = new SAXContentReferencingHandler();
    saxServiceInformationHandler = new SAXServiceInformationHandler();

    // Create tables
    programInformationTable = new ProgramInformationTable();
    programLocationTable = new ProgramLocationTable();
    groupInformationTable = new GroupInformationTable();
    segmentInformationTable = new SegmentInformationTable();
    contentReferencingTable = new ContentReferencingTable();
    serviceInformationTable = new ServiceInformationTable();

    // Set tables for handlers
    saxProgramInformationHandler.setTable(programInformationTable);
    saxProgramLocationHandler.setTable(programLocationTable);
    saxGroupInformationHandler.setTable(groupInformationTable);
    saxSegmentInformationHandler.setTable(segmentInformationTable);
    saxContentReferencingHandler.setTable(contentReferencingTable);
    saxServiceInformationHandler.setTable(serviceInformationTable);

    exceptionStack = new Vector();
  }

  /**
   * Called when parsing of document starts.
   */
  public void startDocument() {
    exceptionStack.removeAllElements();
  }

  /**
   * Called when parsing of document ends.
   */
  public void endDocument() {
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
      case INITIAL:
        if (qName.equals("TVAMain") && (parseProfile >= SAXXMLParser.BASIC)) {
          // Don't do anything, just wait for tables
        }
        else if (qName.equals("ProgramDescription") && (parseProfile >= SAXXMLParser.BASIC)) {
          // Don't do anything, just wait for tables
        }
        else if (qName.equals("ProgramInformationTable") && (parseProfile >= SAXXMLParser.BASIC)) {
          // Set attributes for table
          //setProgramInformationTableAttributes(atts);
          // Pass handling duties on to saxProgramInformationHandler
          saxProgramInformationHandler.handle(this,saxParser,currentLocator,exceptionStack,parseProfile);
        }
        else if (qName.equals("ProgramLocationTable") && (parseProfile >= SAXXMLParser.BASIC)) {
          // Set attributes for table
          //setProgramInformationTableAttributes(atts);
          // Pass handling duties on to saxProgramInformationHandler
          saxProgramLocationHandler.handle(this,saxParser,currentLocator,exceptionStack,parseProfile);
        }
        else if (qName.equals("GroupInformationTable") && (parseProfile >= SAXXMLParser.BASIC)) {
          // Set attributes for table
          //setGroupInformationTableAttributes(atts);
          // Pass handling duties on to saxGroupInformationHandler
          saxGroupInformationHandler.handle(this,saxParser,currentLocator,exceptionStack,parseProfile);
        }
        else if (qName.equals("SegmentInformationTable") && (parseProfile >= SAXXMLParser.BASIC)) {
          // Set attributes for table
    		  setSegmentInformationTableAttributes(atts);
          // Pass handling duties on to saxSegmentInformationHandler
          saxSegmentInformationHandler.handle(this,saxParser,currentLocator,exceptionStack,parseProfile);
        }
        else if (qName.equals("ContentReferencingTable") && (parseProfile >= SAXXMLParser.BASIC)) {
          // Set attributes for table
    		  setContentReferencingTableAttributes(atts);
          // Pass handling duties on to saxContentReferencingHandler
          saxContentReferencingHandler.handle(this,saxParser,currentLocator,exceptionStack,parseProfile);
        }
        else if (qName.equals("ServiceInformationTable") && (parseProfile >= SAXXMLParser.BASIC)) {
          // Pass handling duties on to saxGroupInformationHandler
          saxServiceInformationHandler.handle(this,saxParser,currentLocator,exceptionStack,parseProfile);
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
  public void endElement (String uri, String name, String qName) {
    switch(state) {
      case INITIAL:
        // Leaving initial state...
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
    //characterData += String.copyValueOf(ch,start,length);
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
   * Parse the content of the given InputSource as TVAnytime XML.
   *
   * @param   is  The InputSource containing the content to be parsed.
   * @throws  IOException   If any IO errors occur.
   * @throws  TVAnytimeException  If the underlying parser throws an error while parsing.
   * @throws  NonFatalXMLException  If the underlying parser throws a non-fatal error while parsing.
   */
  public void parse(InputSource is) throws IOException, TVAnytimeException, NonFatalXMLException {
    try {
      // Parse the input
      saxParser.parse(is, this);
      // Throw non-fatal exceptions (if present)
      if (exceptionStack.size()>0) {
        String exceptionString = "SAXXMLParser non-fatal exceptions:\n";
        for (int ct=0; ct<exceptionStack.size(); ct++) {
          exceptionString += (String)(exceptionStack.elementAt(ct));
        }
        throw new NonFatalXMLException(exceptionString);
      }
    }
    catch (IOException ioe) {
      throw ioe;
    }
    catch (SAXException se) {
      if(currentLocator!=null) {
        System.out.println(currentLocator.getLineNumber() + ", column " +
                  currentLocator.getColumnNumber() + "\n");
      }
     	se.printStackTrace();
      throw new TVAnytimeException("SAXException: "+se.getMessage());
    }
  }



  /**
   * Parse the content of the file specified as TVAnytime XML.
   *
   * @param   f  File containing the TVAnytime XML.
   * @throws  IOException   If any IO errors occur.
   * @throws  TVAnytimeException  If the underlying parser throws an error while parsing.
   * @throws  NonFatalXMLException  If the underlying parser throws a non-fatal error while parsing.
   */
  public void parse(File f) throws IOException, TVAnytimeException, NonFatalXMLException {
    try {
      // Parse the input
      saxParser.parse(f, this);
      // Throw non-fatal exceptions (if present)
      if (exceptionStack.size()>0) {
        String exceptionString = "SAXXMLParser non-fatal exceptions:\n";
        for (int ct=0; ct<exceptionStack.size(); ct++) {
          exceptionString += (String)(exceptionStack.elementAt(ct));
        }
        throw new NonFatalXMLException(exceptionString);
      }
    }
    catch (IOException ioe) {
      throw ioe;
    }
    catch (SAXException se) {
      if(currentLocator!=null) {
        System.out.println(currentLocator.getLineNumber() + ", column " +
                  currentLocator.getColumnNumber() + "\n");
      }
     	se.printStackTrace();
      System.out.println();
      throw new TVAnytimeException("SAXException: "+se.getMessage());
    }
  }

  /**
   * Parse the content of the given InputStream as TVAnytime XML.
   *
   * @param   is  The InputStream containing the content to be parsed.
   * @throws  IOException   If any IO errors occur.
   * @throws  TVAnytimeException  If the underlying parser throws an error while parsing.
   */
  public void parse(InputStream is) throws IOException, TVAnytimeException {
    try {
      // Parse the input
      saxParser.parse(is, this);
      // Throw non-fatal exceptions (if present)
      if (exceptionStack.size()>0) {
        String exceptionString = "SAXXMLParser non-fatal exceptions:\n";
        for (int ct=0; ct<exceptionStack.size(); ct++) {
          exceptionString += (String)(exceptionStack.elementAt(ct));
        }
        throw new NonFatalXMLException(exceptionString);
      }
    }
    catch (IOException ioe) {
      throw ioe;
    }
    catch (SAXException se) {
      if(currentLocator!=null) {
        System.out.println(currentLocator.getLineNumber() + ", column " +
                  currentLocator.getColumnNumber() + "\n");
      }
     	se.printStackTrace();
      throw new TVAnytimeException("SAXException: "+se.getMessage());
    }
  }

  /**
   * Parse the content described by the given Universal Resource Identifier (URI).
   *
   * @param   uri  The location of the content to be parsed.
   * @throws  IOException   If any IO errors occur.
   * @throws  TVAnytimeException  If the underlying parser throws an error while parsing.
   */
  public void parse(String uri) throws IOException, TVAnytimeException {
    try {
      // Parse the input
      saxParser.parse(uri, this);
      // Throw non-fatal exceptions (if present)
      if (exceptionStack.size()>0) {
        String exceptionString = "SAXXMLParser non-fatal exceptions:\n";
        for (int ct=0; ct<exceptionStack.size(); ct++) {
          exceptionString += (String)(exceptionStack.elementAt(ct));
        }
        throw new NonFatalXMLException(exceptionString);
      }
    }
    catch (IOException ioe) {
      throw ioe;
    }
    catch (SAXException se) {
      if(currentLocator!=null) {
        System.out.println(currentLocator.getLineNumber() + ", column " +
                  currentLocator.getColumnNumber() + "\n");
      }
     	se.printStackTrace();
      throw new TVAnytimeException("SAXException: "+se.getMessage());
    }

  }

  /**
   * Get ProgramInformationTable used by this parser.
   *
   * @return ProgramInformationTable used by this parser.
   */
  public ProgramInformationTable getProgramInformationTable() {
    return programInformationTable;
  }

  /**
   * Get SegmentInformationTable used by this parser.
   *
   * @return SegmentInformationTable used by this parser.
   */
  public SegmentInformationTable getSegmentInformationTable() {
    return segmentInformationTable;
  }

  /**
   * Get GroupInformationTable used by this parser.
   *
   * @return GroupInformationTable used by this parser.
   */
  public GroupInformationTable getGroupInformationTable() {
    return groupInformationTable;
  }

  /**
   * Get ProgramLocationTable used by this parser.
   *
   * @return ProgramLocationTable used by this parser.
   */
  public ProgramLocationTable getProgramLocationTable() {
    return programLocationTable;
  }

  /**
   * Get ContentReferencingTable used by this parser.
   *
   * @return ContentReferencingTable used by this parser.
   */
  public ContentReferencingTable getContentReferencingTable() {
    return contentReferencingTable;
  }

  /**
   * Get ServiceInformationTable used by this parser.
   *
   * @return ServiceInformationTable used by this parser.
   */
  public ServiceInformationTable getServiceInformationTable() {
    return serviceInformationTable;
  }


  /**
   * Sets the XML parsing profile.
   *
   * @param parseProfile  The XML parsing profile (SAXXMLParser.BASIC or SAXXMLParser.STANDARD).
   */
  public void setParseProfile(int parseProfile) {
    this.parseProfile = parseProfile;
  }

  /**
   * Gets the XML parsing profile.
   *
   * @return The XML parsing profile (SAXXMLParser.BASIC or SAXXMLParser.STANDARD).
   */
  public int getParseProfile() {
    return parseProfile;
  }

  /**
   * Sets whether the parser should validate the XML - SAXXMLParser does not support
   * validation and will always throw an exception.
   *
   * @param   validating    Whether the parser should validate.
   * @throws  TVAnytimeException  if couldn't set property.
   */
  public void setValidating(boolean validating) throws TVAnytimeException {
    throw new TVAnytimeException("TVAnytimeException: SAXXMLParser is not a validating parser");
  }

  /**
   * Resolves CRID using ProgramLocation table.
   *
   * @param   crid  the CRID of the ProgramLocation object to be searched for.
   * @return Vector  containing ProgramLocation objects. Empty if none found.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getProgramLocation(ContentReference crid) throws SearchInterfaceNotSupportedException {
    return programLocationTable.resolveLeafCRID(crid);
  }

  /**
   * Get program information for the specified CRID.
   *
   * @param   crid  the CRID of the ProgramInformation object to be searched for.
   * @return Vector  containing ProgramInformation objects. Empty if none found.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getProgramInformation(ContentReference crid) throws SearchInterfaceNotSupportedException {
    return programInformationTable.getProgramInformation(crid);
  }

  /**
   * Get content referencing result for the specified CRID.
   *
   * @param   crid  the CRID of the ContentReferencing object to be searched for.
   * @return Result object.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Result getResult(ContentReference crid) throws SearchInterfaceNotSupportedException {
    return contentReferencingTable.resolveCRID(crid);
  }

  /**
   * Unsupported method - always throws exception.
   *
   * @param   crid  the CRID of the SegmentInformation object to be searched for.
   * @return Vector  containing SegmentInformation objects. Empty if none found.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getSegmentInformation(ContentReference crid) throws SearchInterfaceNotSupportedException {
    throw new SearchInterfaceNotSupportedException("");
  }

  /**
   * Unsupported method - always throws exception.
   *
   * @param   crid  the CRID of the GroupInformation object to be searched for.
   * @return Vector  containing GroupInformation objects. Empty if none found.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getGroupInformation(ContentReference crid) throws SearchInterfaceNotSupportedException {
    throw new SearchInterfaceNotSupportedException("");
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

 /**
	* Sets attributes for a SegmentInformationTable object
	*/
	private void setSegmentInformationTableAttributes(Attributes atts)
	{
		String timeUnit;
		timeUnit = atts.getValue("timeUnit");

		if (timeUnit != null)
		{
			segmentInformationTable.setTimeUnit(atts.getValue("timeUnit"));
		}
	}

   /**
	* Sets attributes for a ContentReferencingTable object
	*/
	private void setContentReferencingTableAttributes(Attributes atts)
	{
    if (atts.getValue("version") != null) {
  		contentReferencingTable.setVersion((Float.valueOf(atts.getValue("version"))).floatValue());
    }
	}
}

