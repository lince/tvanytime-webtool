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

import bbc.rd.tvanytime.segmentInformation.*;
import bbc.rd.tvanytime.*;
import bbc.rd.mpeg7.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * SAXSegmentInformationHandler: SAX event hadler used by SAXXMLParser to parse
 * SegmentInformationTable.
 * Uses state machine to track current position in document.
 *
 * Error handling:
 *    Throws SAX XML exceptions
 *    Catches invalid TVAnytime data fields, unsets the variable and keep error
 *    message to end.
 *
 *    Parses all of the currently used elements and attributes...
 *        SegmentList
 *			SegmentInformation
 *			  ProgramRef
 *				Description
 *				  Title
 *				  Synopsis
 *				SegmentLocator
 *				  mpeg7:MediaRelIncrTimePoint
 *				  mpeg7:MediaIncrDuration
 *		  SegmentGroupList
 *			SegmentGroupInformation
 *			  ProgramRef
 *				GroupType
 *				Description
 *				  Title
 *
 * @author Tristan Ferne, BBC Research & Development, May 2002
 *
 * Changes: Added support for Keywords (TJS)
 * @version 1.01
 */

class SAXSegmentInformationHandler extends DefaultHandler {

  /**
   * Constants for segment information table that define what state the parser is in.
   */
  private static final int UNKNOWN = 0;
  private static final int SEGMENT_INFO_TABLE = 1;

  private static final int SEGMENT_LIST = 100;
  private static final int SEGMENT_GROUP_LIST = 101;

  private static final int SEGMENT_INFORMATION = 200;
  private static final int SEGMENT_GROUP_INFORMATION = 201;

  private static final int PROGRAM_REF_SEG_GROUP_INFO = 300;
  private static final int PROGRAM_REF_SEG_INFO = 301;
  private static final int GROUP_TYPE = 302;
  private static final int DESCRIPTION_SEG_INFO = 303;
  private static final int DESCRIPTION_SEG_GROUP_INFO = 304;
  private static final int SEGMENT_LOCATOR = 305;
  private static final int SEGMENTS = 306;
  private static final int GROUPS = 307;

  private static final int TITLE = 400;
  private static final int TITLE_SEG_GROUP = 401;
  private static final int SYNOPSIS = 402;
  private static final int SYNOPSIS_SEG_GROUP = 403;
  private static final int KEYWORD = 404;
  private static final int KEYWORD_SEG_GROUP = 405;
  private static final int MPEG7_MEDIA_TIMEPOINT = 406;
  private static final int MPEG7_MEDIA_INCR_DURATION = 407;

  private static final int RELATED_MATERIAL_SEG_INFO = 500;
  private static final int HOW_RELATED_SEG_INFO = 501;
  private static final int SOURCE_MEDIA_LOCATOR_SEG_INFO = 505;
  private static final int PROMOTIONAL_TEXT_SEG_INFO = 506;
  private static final int SEGMENT_REF_SEG_INFO = 507;

  private static final int RELATED_MATERIAL_SEG_GROUP_INFO = 550;
  private static final int HOW_RELATED_SEG_GROUP_INFO = 551;
  private static final int SOURCE_MEDIA_LOCATOR_SEG_GROUP_INFO = 552;
  private static final int PROMOTIONAL_TEXT_SEG_GROUP_INFO = 553;
  private static final int SEGMENT_REF_SEG_GROUP_INFO = 557;
  
  private static final int MEDIA_LOCATOR_SEG_INFO = 601;
  private static final int MEDIA_URI_SEG_INFO = 602;
  private static final int MEDIA_LOCATOR_SEG_GROUP_INFO = 603;
  private static final int MEDIA_URI_SEG_GROUP_INFO = 604;
  private static final int SOURCE_MEDIA_URI_SEG_INFO = 605;

  /**
   * Profile for XML parsing.
   */
  private int parseProfile = SAXXMLParser.STANDARD;

  /**
   * Current state of parser.
   */
  private int state = SEGMENT_INFO_TABLE;

  /**
   * Reference to Vector of strings containing information about non-fatal exceptions.
   */
  private Vector exceptionStack;
  /**
   * Number of levels of unknown elements we are currently in.
   */
  private int unknownDepth = 0;
  /**
   * Last recognised state before entering unknown element.
   */
  private int savedState = SEGMENT_INFO_TABLE;
  /**
   * Reference to clients segmentInformationTable.
   */
  private SegmentInformationTable segmentInformationTable;
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
   * Temporary string to hold attributes.
   */
  private String attribute = "";
  /**
   * Temporary string to hold character data.
   */
  private String characterData = "";
  /**
   * Temporary TVAnytime objects.
   */
   private SegmentList segmentList;
   private SegmentGroupList segmentGroupList;
   private SegmentInformation segmentInformation;
   private SegmentGroupInformation segmentGroupInformation;
   private BasicSegmentDescription description;
   private ContentReference crid;
   private Title title;
   private Synopsis synopsis;
   private Keyword keyword;
   private SegmentLocator segmentLocator;
   private MPEG7MediaRelIncrTimePoint mpeg7MediaRelIncrTimePoint;
   private MPEG7MediaIncrDuration mpeg7MediaIncrDuration;
   private RelatedMaterial relatedMaterial;
   private MPEG7MediaLocator mediaLocator;
   private MPEG7MediaLocator sourceMediaLocator;
   private SegmentReference segmentReference;


  /**
   *
   */
  SAXSegmentInformationHandler() {
  }

  /**
   * Tells this class to take over handling of SAX events from parser.
   *
   * @param   parent    Parent SAX event handler that asked this to handle events.
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
   * Sets SegmentInformationTable to populate with parsed data.
   *
   * @param   segmentInformationTable   Table to populate with parsed data.
   */
  void setTable(SegmentInformationTable segmentInformationTable) {
    this.segmentInformationTable = segmentInformationTable;

  }


  /**
   * Called during parsing when start of element found.
   * Implements state machine.
   */
  public void startElement (String uri, String name, String _qName, Attributes atts) {
    // Handle namespaces...
    String qName = filterPrefix(uri, name, _qName);

    // reset character data array
    characterData = "";
    
    switch(state)
    {
      case SEGMENT_INFO_TABLE:
        // in SegmentInformationTable, looking for SegmentList or SegmentGroup
        if (qName.equals("SegmentList")) {
          state = SEGMENT_LIST;
          // create new SegmentList object
          segmentList = new SegmentList();
          // add to table
          segmentInformationTable.setSegmentList(segmentList);
        }
        else if (qName.equals("SegmentGroupList")) {
          state = SEGMENT_GROUP_LIST;
          // create new SegmentGroupList object
          segmentGroupList = new SegmentGroupList();
          // add to table
          segmentInformationTable.setSegmentGroupList(segmentGroupList);
        }
        else {
          // entered unknown element
          savedState = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case SEGMENT_LIST:
        // in SegmentList, looking for SegmentInformation
        if (qName.equals("SegmentInformation")) {
          state = SEGMENT_INFORMATION;
          // create new SegmentInformation object
          segmentInformation = new SegmentInformation();
          // set attributes
          setSegmentInformationAttributes(atts);
          segmentList.addSegmentInformation(segmentInformation);
        }
        else {
          // entered unknown element
          savedState = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case SEGMENT_GROUP_LIST:
        // in SegmentGroupList, looking for SegmentGroupInformation
        if (qName.equals("SegmentGroupInformation")) {
          state = SEGMENT_GROUP_INFORMATION;
          // create new SegmentGroupInformation object

          segmentGroupInformation = new SegmentGroupInformation();
          // set attributes
          setSegmentGroupInformationAttributes(atts);
          segmentGroupList.addSegmentGroupInformation(segmentGroupInformation);
        }
        else {
          // entered unknown element
          savedState = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case SEGMENT_INFORMATION:
        // in SegmentInformation, looking for ProgramRef or Description or SegmentLocator
        if (qName.equals("ProgramRef")) {
          state = PROGRAM_REF_SEG_INFO;
          // set attributes
          setProgramRefAttributes(atts);
        }
        else if (qName.equals("Description")) {
          state = DESCRIPTION_SEG_INFO;
          // create new BasicDescription object
          description = new BasicSegmentDescription();
          segmentInformation.setDescription(description);
        }
        else if (qName.equals("SegmentLocator")) {
          state = SEGMENT_LOCATOR;
          // create new SegmentLocator object
          segmentLocator = new SegmentLocator();
          segmentInformation.setSegmentLocator(segmentLocator);
        }
        else {
          // entered unknown element
          savedState = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case SEGMENT_GROUP_INFORMATION:
        // in SegmentGroupInformation, looking for ProgramRef or Description or Segments or Groups
        if (qName.equals("ProgramRef")) {
          state = PROGRAM_REF_SEG_GROUP_INFO;
          // set attributes
          setProgramRefAttributes(atts);
        }
        else if (qName.equals("GroupType")) {
          state = GROUP_TYPE;
          setGroupTypeAttributes(atts);
        }
        else if (qName.equals("Description")) {
          state = DESCRIPTION_SEG_GROUP_INFO;
          // create new Description object
          description = new BasicSegmentDescription();
          segmentGroupInformation.addDescription(description);
        }
        else if (qName.equals("Segments")) {
          state = SEGMENTS;
          // set attributes
          setSegmentsAttributes(atts);
        }
        else if (qName.equals("Groups")) {
          state = GROUPS;
          // set attributes
          setGroupsAttributes(atts);
        }
        else {
          // entered unknown element
          savedState = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case DESCRIPTION_SEG_INFO:
        // in Description (seg info), looking for Title or Synopsis
        if (qName.equals("Title")) {
          state = TITLE;
          // create new Title object
          title = new Title();
          description.addTitle(title);
        }
        else if (qName.equals("Synopsis")) {
          state = SYNOPSIS;
          // create new Synopsis object
          synopsis = new Synopsis();
          description.addSynopsis(synopsis);
        }
        else if (qName.equals("Keyword")) {
          state = KEYWORD;
          // create new Keyword object
          keyword = new Keyword();
          description.addKeyword(keyword);
        }
        else if (qName.equals("RelatedMaterial")) {
          state = RELATED_MATERIAL_SEG_INFO;
          // Create new RelatedMaterial
          relatedMaterial = new RelatedMaterial();
          // Add to Description
          description.addRelatedMaterial(relatedMaterial);
        }
        else {
          // entered unknown element
          savedState = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case DESCRIPTION_SEG_GROUP_INFO:
        // in Description (seg group info), looking for Title
        if (qName.equals("Title")) {
          state = TITLE_SEG_GROUP;
          // create new Title object
          title = new Title();
          description.addTitle(title);
        }
        // in Description (seg group info), looking for Synopsis
        else if (qName.equals("Synopsis")) {
          state = SYNOPSIS_SEG_GROUP;
          // create new Synopsis object
          synopsis = new Synopsis();
          description.addSynopsis(synopsis);
        }
        else if (qName.equals("Keyword")) {
          state = KEYWORD_SEG_GROUP;
          // create new Keyword object
          keyword = new Keyword();
          description.addKeyword(keyword);
        }
        else if (qName.equals("RelatedMaterial")) {
          state = RELATED_MATERIAL_SEG_GROUP_INFO;
          // Create new RelatedMaterial
          relatedMaterial = new RelatedMaterial();
          // Add to Description
          description.addRelatedMaterial(relatedMaterial);
        }
        else {
          // entered unknown element
          savedState = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case RELATED_MATERIAL_SEG_INFO:
        // in RelatedMaterial, looking for MediaLocator
        if (qName.equals("HowRelated")) {
          state = HOW_RELATED_SEG_INFO;
          // Set attributes
          setHowRelatedAttributes(atts);
        }
        else if (qName.equals("MediaLocator")) {
          state = MEDIA_LOCATOR_SEG_INFO;
          // Create new MediaLocator
          mediaLocator = new MPEG7MediaLocator();
          // Add to RelatedMaterial
          relatedMaterial.setMediaLocator(mediaLocator);
        }
        else if (qName.equals("SegmentReference")) {
          // Create new SegmentReference
        	state = SEGMENT_REF_SEG_INFO;
          segmentReference = new SegmentReference();
          setSegmentReferenceAttributes(atts);          
          // Add to RelatedMaterial
          relatedMaterial.setSegmentReference(segmentReference);
        }        
        else if (qName.equals("PromotionalText")) {
          state = PROMOTIONAL_TEXT_SEG_INFO;
        }
        else if (qName.equals("SourceMediaLocator")) {
          state = SOURCE_MEDIA_LOCATOR_SEG_INFO;
          // Create new MediaLocator
          sourceMediaLocator = new MPEG7MediaLocator();
          // Add to RelatedMaterial
          relatedMaterial.setSourceMediaLocator(sourceMediaLocator);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case HOW_RELATED_SEG_INFO:
        // Entered unknown element
        savedState = state;
        state = UNKNOWN;
        unknownDepth = 1;
        break;

      case MEDIA_LOCATOR_SEG_INFO:
        // in MediaLocator, looking for MediaUri
        if (qName.equals("MediaUri")) {
          state = MEDIA_URI_SEG_INFO;
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      // TF  - ADDED
      case SOURCE_MEDIA_LOCATOR_SEG_INFO:
        // in SourceMediaLocator, looking for MediaUri
        if (qName.equals("MediaUri")) {
          state = SOURCE_MEDIA_URI_SEG_INFO;
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case RELATED_MATERIAL_SEG_GROUP_INFO:
        // in RelatedMaterial, looking for MediaLocator
        if (qName.equals("MediaLocator")) {
          state = MEDIA_LOCATOR_SEG_GROUP_INFO;
          // Create new MediaLocator
          mediaLocator = new MPEG7MediaLocator();
          // Add to RelatedMaterial
          relatedMaterial.setMediaLocator(mediaLocator);
        }
        else if (qName.equals("HowRelated")) {
          state = HOW_RELATED_SEG_GROUP_INFO;
          // Set attributes
          setHowRelatedAttributes(atts);
        }
        else if (qName.equals("SegmentReference")) {
          // Create new SegmentReference
        	state = SEGMENT_REF_SEG_GROUP_INFO;
          segmentReference = new SegmentReference();
          setSegmentReferenceAttributes(atts);          
          // Add to RelatedMaterial
          relatedMaterial.setSegmentReference(segmentReference);
        }                
        else if (qName.equals("PromotionalText")) {
          state = PROMOTIONAL_TEXT_SEG_GROUP_INFO;
        }
        else if (qName.equals("SourceMediaLocator")) {
          state = SOURCE_MEDIA_LOCATOR_SEG_GROUP_INFO;
          // Create new MediaLocator
          sourceMediaLocator = new MPEG7MediaLocator();
          // Add to RelatedMaterial
          relatedMaterial.setSourceMediaLocator(sourceMediaLocator);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case HOW_RELATED_SEG_GROUP_INFO:
        // Entered unknown element
        savedState = state;
        state = UNKNOWN;
        unknownDepth = 1;
        break;

      case MEDIA_LOCATOR_SEG_GROUP_INFO:
        // in MediaLocator, looking for MediaUri
        //if (qName.equals("mpeg7:MediaUri")) {
        if (qName.equals("MediaUri")) {
          state = MEDIA_URI_SEG_GROUP_INFO;
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case SEGMENT_LOCATOR:
        // in SegmentLocator, looking for mpeg7:MediaRelIncrTimePoint or mpeg7:MediaIncrDuration
        //if (qName.equals("mpeg7:MediaRelIncrTimePoint")) {
        if (qName.equals("MediaRelIncrTimePoint")) {
          state = MPEG7_MEDIA_TIMEPOINT;
          // create new MPEG7MediaTimePoint object
          mpeg7MediaRelIncrTimePoint = new MPEG7MediaRelIncrTimePoint ();

          // set attributes
          setMediaRelIncrTimePointAttributes(atts);

          segmentLocator.addMPEG7MediaRelIncrTimePoint(mpeg7MediaRelIncrTimePoint);
        }
        //else if (qName.equals("mpeg7:MediaIncrDuration")) {
        else if (qName.equals("MediaIncrDuration")) {
          state = MPEG7_MEDIA_INCR_DURATION;
          // create new MPEG7MediaIncrDuration object
          mpeg7MediaIncrDuration = new MPEG7MediaIncrDuration ();

          // set attributes
          setMediaIncrDurationAttributes(atts);

          segmentLocator.addMPEG7MediaIncrDuration(mpeg7MediaIncrDuration);
        }
        else {
          // entered unknown element
          savedState = UNKNOWN;
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
  public void endElement (String uri, String name, String qName) throws SAXException
  {
  		switch (state)
  		{
			case SEGMENT_INFO_TABLE:
				// leaving table
				// set parser handler to parent when finished
				parser.getXMLReader().setContentHandler(parent);
				break;

			case SEGMENT_LIST:
				// leaving SegmentList
				state = SEGMENT_INFO_TABLE;
				break;

			case SEGMENT_GROUP_LIST:
				// leaving SegmentGroupList
				state = SEGMENT_INFO_TABLE;
				break;

			case SEGMENT_INFORMATION:
				// leaving SegmentInformation
				state = SEGMENT_LIST;
				break;

			case SEGMENT_GROUP_INFORMATION:
				// leaving SegmentGroupInformation
				state = SEGMENT_GROUP_LIST;
				break;

			case DESCRIPTION_SEG_INFO:
				// leaving Description
				state = SEGMENT_INFORMATION;
				break;

			case DESCRIPTION_SEG_GROUP_INFO:
				// leaving Description
				state = SEGMENT_GROUP_INFORMATION;
				break;

			case RELATED_MATERIAL_SEG_INFO:
				// Leaving RelatedMaterial
				state = DESCRIPTION_SEG_INFO;
				break;

			case HOW_RELATED_SEG_INFO:
				// Leaving HowRelated
				state = RELATED_MATERIAL_SEG_INFO;
				break;

			case SOURCE_MEDIA_LOCATOR_SEG_INFO:
				// Leaving SourceMediaLocator
				state = RELATED_MATERIAL_SEG_INFO;
				break;

			case MEDIA_LOCATOR_SEG_INFO:
				// Leaving MediaLocator
				state = RELATED_MATERIAL_SEG_INFO;
				break;

			case PROMOTIONAL_TEXT_SEG_INFO:
				// Leaving PromotionalText
				state = RELATED_MATERIAL_SEG_INFO;
				relatedMaterial.setPromotionalText(characterData.trim());
				break;

			case SEGMENT_REF_SEG_INFO:
				// Leaving SegmentReference
				state = RELATED_MATERIAL_SEG_INFO;
				break;				
				
			case MEDIA_URI_SEG_INFO:
				// Leaving MediaURI
				try {
					mediaLocator.setMediaURI(new bbc.rd.tvanytime.URI(characterData.trim()));
				}
				catch (TVAnytimeException tvae) {
					addError("MediaUri: "+tvae.getMessage());
				}
				state = MEDIA_LOCATOR_SEG_INFO;
				break;

      // TF - Added 4/9/03
      case SOURCE_MEDIA_URI_SEG_INFO:
				// Leaving MediaURI
				try {
					sourceMediaLocator.setMediaURI(new bbc.rd.tvanytime.URI(characterData.trim()));
				}
				catch (TVAnytimeException tvae) {
					addError("MediaUri: "+tvae.getMessage());
				}
				state = SOURCE_MEDIA_LOCATOR_SEG_INFO;
				break;

			case RELATED_MATERIAL_SEG_GROUP_INFO:
				// Leaving RelatedMaterial
				state = DESCRIPTION_SEG_GROUP_INFO;
				break;

			case MEDIA_LOCATOR_SEG_GROUP_INFO:
				// Leaving MediaLocator
				state = DESCRIPTION_SEG_GROUP_INFO;
				break;

			case PROMOTIONAL_TEXT_SEG_GROUP_INFO:
				// Leaving PromotionalText
				state = DESCRIPTION_SEG_GROUP_INFO;
				relatedMaterial.setPromotionalText(characterData.trim());
				break;

			case SEGMENT_REF_SEG_GROUP_INFO:
				// Leaving SegmentReference
				state = DESCRIPTION_SEG_GROUP_INFO;
				break;								
				
			case MEDIA_URI_SEG_GROUP_INFO:
				// Leaving MediaURI
				try {
					mediaLocator.setMediaURI(new URI(characterData.trim()));
				}
				catch (TVAnytimeException tvae) {
					addError("MediaUri: "+tvae.getMessage());
				}
				state = MEDIA_LOCATOR_SEG_GROUP_INFO;
				break;

			case PROGRAM_REF_SEG_INFO:
				// leaving ProgramRef
				state = SEGMENT_INFORMATION;
				break;

			case PROGRAM_REF_SEG_GROUP_INFO:
				// leaving ProgramRef
				state = SEGMENT_GROUP_INFORMATION;
				break;

			case GROUP_TYPE:
				// leaving GroupType
				state = SEGMENT_GROUP_INFORMATION;
				break;

			case SEGMENTS:
				// leaving Segments
				state = SEGMENT_GROUP_INFORMATION;
				break;

			case GROUPS:
				// leaving Groups
				state = GROUPS;
				break;

			case SEGMENT_LOCATOR:
				// leaving SegmentLocator
				state = SEGMENT_INFORMATION;
				break;

			case TITLE:
				// leaving Title
				title.setText(characterData.trim());
				state = DESCRIPTION_SEG_INFO;
				break;

			case TITLE_SEG_GROUP:
				// leaving Title
				title.setText(characterData.trim());
				state = DESCRIPTION_SEG_GROUP_INFO;
				break;

			case SYNOPSIS:
				// leaving Synopsis
				synopsis.setText(characterData.trim());
				state = DESCRIPTION_SEG_INFO;
				break;

			case SYNOPSIS_SEG_GROUP:
				// leaving Synopsis
				synopsis.setText(characterData.trim());
				state = DESCRIPTION_SEG_GROUP_INFO;
				break;
				
			case KEYWORD:
				// leaving Keyword
				keyword.setKeyword(characterData.trim());
				state = DESCRIPTION_SEG_INFO;
				break;
			
			case KEYWORD_SEG_GROUP:
				// leaving Keyword
				keyword.setKeyword(characterData.trim());
				state = DESCRIPTION_SEG_GROUP_INFO;
				break;

			case MPEG7_MEDIA_TIMEPOINT:
				// leaving mpeg7:MediaRelIncrTimePoint
				mpeg7MediaRelIncrTimePoint.setTime(Long.parseLong(characterData.trim()));
				state = SEGMENT_LOCATOR;
				break;

			case MPEG7_MEDIA_INCR_DURATION:
				// leaving mpeg7:MediaIncrDuration
				mpeg7MediaIncrDuration.setTime(Long.parseLong(characterData.trim()));
				state = SEGMENT_LOCATOR;
				break;

			case UNKNOWN:
				// leaving unknown element
				unknownDepth--;
				// if left unknown elements then return state to previous
				if (unknownDepth == 0) state = savedState;
				break;

			default:
	}
  }


  public void characters(char[] ch, int start, int length)
  {
		switch (state)
		{
			case TITLE:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case SYNOPSIS:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case KEYWORD:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case KEYWORD_SEG_GROUP:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case SYNOPSIS_SEG_GROUP:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case TITLE_SEG_GROUP:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case MPEG7_MEDIA_INCR_DURATION:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case MPEG7_MEDIA_TIMEPOINT:
				characterData += String.copyValueOf(ch,start,length);
				break;
      // TF - Added 4/9/03
      case SOURCE_MEDIA_URI_SEG_INFO:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case MEDIA_URI_SEG_INFO:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case MEDIA_URI_SEG_GROUP_INFO:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case PROMOTIONAL_TEXT_SEG_INFO:
				characterData += String.copyValueOf(ch,start,length);
				break;
			case PROMOTIONAL_TEXT_SEG_GROUP_INFO:
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
	* Sets attributes for a SegmentInformation object
	*/
	private void setSegmentInformationAttributes(Attributes atts)
	{
		segmentInformation.setSegmentID(atts.getValue("segmentId"));
	}

   /**
	* Sets attributes for a Keyword object.
	*/
	private void setKeywordAttributes(Attributes atts) {
		attribute = atts.getValue("type");
		if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
			try {
				if (attribute.equals("main")) keyword.setType(Keyword.MAIN);
				else if (attribute.equals("secondary")) keyword.setType(Keyword.SECONDARY);
				else if (attribute.equals("other")) keyword.setType(Keyword.OTHER);
				else addError("Genre: Invalid keyword type");
			}
			catch (TVAnytimeException tvae) {
				// Add message to general exception and continue
				addError("Keyword: " + tvae.getMessage());
			}
		}
	}


   /**
	* Sets attributes for a mpeg7:MediaRelIncrTimePoint object
	*/
	private void setMediaRelIncrTimePointAttributes(Attributes atts)
	{
		String timeUnit = atts.getValue("mediaTimeUnit");

		if (timeUnit != null)
			mpeg7MediaRelIncrTimePoint.setTimeUnit(timeUnit);
	}

   /**
	* Sets attributes for a mpeg7:MediaIncrDuration object
	*/
	private void setMediaIncrDurationAttributes(Attributes atts)
	{
		String timeUnit = atts.getValue("mediaTimeUnit");

		if (timeUnit != null)
			mpeg7MediaIncrDuration.setTimeUnit(timeUnit);
	}

   /**
	* Sets attributes for a ProgramRef object
	*/
	private void setProgramRefAttributes(Attributes atts)
	{
		attribute = atts.getValue("crid");

		try {
			crid = new ContentReference(attribute);
			if (state == PROGRAM_REF_SEG_INFO)
			{
				segmentInformation.setProgramRef(crid);
			}
			else if (state == PROGRAM_REF_SEG_GROUP_INFO)
			{
				segmentGroupInformation.setProgramRef(crid);
			}
		}
		catch (TVAnytimeException tvae) {
			// add message to general exception and continue
			addError("ProgramRef: " + tvae.getMessage());
		}
	}

   /**
	* Sets attributes for a SegmentGroupInformation object
	*/
	private void setSegmentGroupInformationAttributes(Attributes atts)
	{
		segmentGroupInformation.setGroupID(atts.getValue("groupId"));

		attribute = atts.getValue("ordered");

		if (attribute != null)
		{
			if (attribute.equals("true"))
				segmentGroupInformation.setOrdered(true);
			else if (attribute.equals("false"))
				segmentGroupInformation.setOrdered(false);
		}
	}

   /**
	* Sets attributes for a GroupType object
	*/
	private void setGroupTypeAttributes(Attributes atts)
	{
		attribute = atts.getValue("value");

		if (attribute != null)
		{
			try {
				if (attribute.equals("highlights")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.HIGHLIGHTS));
				else if (attribute.equals("highlights/objects")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.HIGHLIGHTS_OBJECTS));
				else if (attribute.equals("highlights/events")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.HIGHLIGHTS_EVENTS));
				else if (attribute.equals("bookmarks")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.BOOKMARKS));
				else if (attribute.equals("bookmarks/objects")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.BOOKMARKS_OBJECTS));
				else if (attribute.equals("bookmarks/events")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.BOOKMARKS_EVENTS));
				else if (attribute.equals("themeGroup")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.THEME_GROUP));
				else if (attribute.equals("preview")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.PREVIEW));
				else if (attribute.equals("preview/title")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.PREVIEW_TITLE));
				else if (attribute.equals("preview/slideshow")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.PREVIEW_SLIDESHOW));
				else if (attribute.equals("tableOfContents")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.TABLE_OF_CONTENTS));
				else if (attribute.equals("synopsis")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.SYNOPSIS));
				else if (attribute.equals("shots")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.SHOTS));
				else if (attribute.equals("insertionPoints")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.INSERTION_POINTS));
				else if (attribute.equals("alternativeGroups")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.ALTERNATIVE_GROUPS));
				else if (attribute.equals("other")) segmentGroupInformation.addGroupType(new SegmentGroupType(SegmentGroupType.OTHER));
				else addError("SegmentGroupInformation: Invalid groupType");
			}
			catch (TVAnytimeException tvae) {
				// Add message to general exception and continue
				addError("SegmentGroupInformation: " + tvae.getMessage());
			}
		}
	}

 /**
	* Sets attributes for a Segments object
	*/
	private void setSegmentsAttributes(Attributes atts)
	{
		segmentGroupInformation.setSegmentRefList(atts.getValue("refList"));
	}

 /**
	* Sets attributes for a Groups object
	*/
	private void setGroupsAttributes(Attributes atts)
	{
		segmentGroupInformation.setGroupRefList(atts.getValue("refList"));
	}

 /**
	* Sets attributes for HowRelated information.
	*/
	private void setHowRelatedAttributes(Attributes atts)
	{
		attribute = atts.getValue("href");
		if ( (attribute!=null)) {
			try {
				relatedMaterial.setHowRelatedHREF(attribute);
			}
			catch (TVAnytimeException tvae) {
				// Add message to general exception and continue
				addError("HowRelated: " + tvae.getMessage());
			}
    }
  }

 /**
	* Sets attributes for SegmentReference information.
	*/
	private void setSegmentReferenceAttributes(Attributes atts)
	{
		attribute = atts.getValue("segmentType");
		if ( (attribute!=null)) {
			try {
				segmentReference.setSegmentType(Integer.parseInt(attribute));				
			}
			catch (TVAnytimeException tvae) {
				// Add message to general exception and continue
				addError("SegmentReference: " + tvae.getMessage());
			}
    }
		attribute = atts.getValue("ref");
		if ( (attribute!=null)) {
			segmentReference.setRef(attribute);				
    }

	}


}