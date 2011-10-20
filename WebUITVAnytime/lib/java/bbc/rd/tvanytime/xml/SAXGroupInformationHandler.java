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

import bbc.rd.mpeg7.MPEG7MediaLocator;
import bbc.rd.tvanytime.groupInformation.*;
import bbc.rd.tvanytime.*;

import org.xml.sax.*;

import javax.xml.parsers.*;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * SAXGroupInformationHandler: SAX event hadler used by SAXXMLParser to parse
 * groupInformationTable.
 * Uses state machine to track current position in document.
 * Error handling:
 *    Throws SAX XML exceptions
 *    Catches invalid TVAnytime data fields, unsets the variable and keep error
 *    message to end.
 *
 * Parsing profiles:
 * Two profiles are defined for which parts of the XML are parsed.
 *    BASIC: Only parses required/mandatory elements and attributes...
 *        GroupInformation
 *          BasicDescription
 *            Title
 *
 *    STANDARD: Parses all of the currently used elements and attributes...
 *        GroupInformation
 *          BasicDescription
 *            Title
 *            Synopsis
 *            Genre
 *			MemberOf
 *
 * @author Chris Akanbi, BBC Research & Development, May 2002
 * @version 1.0
 */

class SAXGroupInformationHandler extends DefaultHandler {

  /**
   * Constants for group information table that define what state the parser is in.
   */
  private static final int UNKNOWN = 0;
  private static final int GROUP_INFO_TABLE = 1;
  private static final int GROUP_INFO = 2;

  private static final int BASIC_DESC = 3;
  private static final int TITLE = 4;
  private static final int SYNOPSIS = 6;
  private static final int GENRE = 8;
  private static final int MPEG7_NAME = 9;
  private static final int GROUP_TYPE = 10;
  private static final int RELATED_MATERIAL = 11;
  
  private static final int MEMBER_OF = 200;

  private static final int HOW_RELATED = 300;
  private static final int MEDIA_LOCATOR = 301;
  private static final int MEDIA_URI = 302;
  private static final int PROMOTIONAL_TEXT = 303;
  private static final int SOURCE_MEDIA_LOCATOR = 304;
  private static final int SOURCE_MEDIA_URI = 305;
  private static final int HOW_RELATED_NAME = 306;

  
  /**
   * Profile for XML parsing.
   */
  private int parseProfile = SAXXMLParser.STANDARD;

  /**
   * Current state of parser.
   */
  private int state = GROUP_INFO_TABLE;
  /**
   * Number of levels of unknown elements we are currently in.
   */
  private int unknownDepth = 0;
  /**
   * Last recognised state before entering unknown element.
   */
  private int savedState = GROUP_INFO_TABLE;
  /**
   * Reference to Vector of strings containing information about non-fatal exceptions.
   */

  private Vector exceptionStack;
  /**
   * Reference to clients GroupInformationTable.
   */
  private GroupInformationTable groupInformationTable;
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
  private GroupInformation groupInformation;
  private BasicDescription basicDescription;
  private Title title;
  private Synopsis synopsis;
  private Genre genre;
  private MemberOf memberOf;
  private RelatedMaterial relatedMaterial;
  private MPEG7MediaLocator mediaLocator;
  private MPEG7MediaLocator sourceMediaLocator;

  /**
   *
   */
  SAXGroupInformationHandler() {
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
   * Sets GroupInformationTable to populate with parsed data.
   *
   * @param   groupInformationTable   Table to populate with parsed data.
   */
  void setTable(GroupInformationTable groupInformationTable) {
    this.groupInformationTable = groupInformationTable;
  }


  /**
   * Called during parsing when start of element found.
   * Implements state machine.
   */
  public void startElement (String uri, String name, String _qName, Attributes atts) {
    // Handle namespaces...
    String qName = filterPrefix(uri, name, _qName);
    // Reset character data array
    characterData = "";
    //System.out.println("State "+state+", entering "+qName);
    switch(state) {
      case GROUP_INFO_TABLE:
        // In GroupInformationTable looking for GroupInformation
        if ( qName.equals("GroupInformation") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = GROUP_INFO;
          // Create new GroupInformation object
          groupInformation = new GroupInformation();
          // Set attributes
          setGroupInformationAttributes(atts);
          // Add to client's table
          groupInformationTable.addGroupInformation(groupInformation);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case GROUP_INFO:
        // In GroupInformation looking for GroupType
        if ( qName.equals("GroupType") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = GROUP_TYPE;
          // Set attributes
          setGroupTypeAttributes(atts);
        }
        // In GroupInformation looking for BasicDescription
        else if ( qName.equals("BasicDescription") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = BASIC_DESC;
          // Create new BasicDescription
          basicDescription = new BasicDescription();
          // Add to GroupInformation
          groupInformation.setBasicDescription(basicDescription);
        }
        else if ( qName.equals("MemberOf") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = MEMBER_OF;
          // Create new MemberOf
          memberOf = new MemberOf();
          // Set attributes
          setMemberOfAttributes(atts);
          // Add to GroupInformation
          groupInformation.addMemberOf(memberOf);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case GROUP_TYPE:
        // In group type - do nothing
        // Entered unknown element
        savedState = state;
        state = UNKNOWN;
        unknownDepth = 1;
        break;

      case BASIC_DESC:
        // In BasicDescription looking for Title
        if (qName.equals("Title") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = TITLE;
          // Create new Title
          title = new Title();
          // Add to BasicDescription
          basicDescription.addTitle(title);
          // Set attributes
          setTitleAttributes(atts);
        }
        else if (qName.equals("Synopsis") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = SYNOPSIS;
          // Create new Synopsis
          synopsis = new Synopsis();
          // Add to BasicDescription
          basicDescription.addSynopsis(synopsis);
          // Set attributes
          setSynopsisAttributes(atts);
        }
        else if (qName.equals("Genre") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = GENRE;
          // Create new Genre
          genre = new Genre();
          // Add to BasicDescription
          basicDescription.addGenre(genre);
          // Set attributes
          setGenreAttributes(atts);
        }
        else if (qName.equals("RelatedMaterial") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = RELATED_MATERIAL;
          // Create new RelatedMaterial
          relatedMaterial = new RelatedMaterial();
          // Add to BasicDescription
          basicDescription.addRelatedMaterial(relatedMaterial);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case GENRE:
        if (qName.equals("Name") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = MPEG7_NAME;
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case RELATED_MATERIAL:
        if (qName.equals("MediaLocator") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = MEDIA_LOCATOR;
          // Create new MediaLocator
          mediaLocator = new MPEG7MediaLocator();
          // Add to RelatedMaterial
          relatedMaterial.setMediaLocator(mediaLocator);
        }
        else if (qName.equals("HowRelated") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = HOW_RELATED;
          // Set attributes
          setHowRelatedAttributes(atts);
        }
        else if (qName.equals("PromotionalText") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = PROMOTIONAL_TEXT;
        }
        else if (qName.equals("SourceMediaLocator") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = SOURCE_MEDIA_LOCATOR;
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

      case HOW_RELATED:
      	if (qName.equals("Name") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = HOW_RELATED_NAME;
        }
        else {
        	// Entered unknown elemet
          savedState = state;
	        state = UNKNOWN;
	        unknownDepth = 1;
        }
      	break;

      case MEDIA_LOCATOR:
        if (qName.equals("MediaUri") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = MEDIA_URI;
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case MEDIA_URI:
        // Entered unknown element
        savedState = state;
        state = UNKNOWN;
        unknownDepth = 1;
        break;

      case SOURCE_MEDIA_LOCATOR:
        if (qName.equals("MediaUri") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = SOURCE_MEDIA_URI;
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
  public void endElement (String uri, String name, String qName) throws SAXException{
  	switch(state) {
      case GROUP_INFO_TABLE:
        // Leaving table...
        // Set parser handler to parent when finished.
        parser.getXMLReader().setContentHandler(parent);
        break;

      case GROUP_INFO:
        // Leaving GroupInformation
        state = GROUP_INFO_TABLE;
        break;

      case BASIC_DESC:
        // Leaving BasicDescription
        state = GROUP_INFO;
        break;

      case GROUP_TYPE:
        // Leaving group type
        state = GROUP_INFO;
        break;

      case TITLE:
        // Leaving Title
        title.setText(characterData.trim());
        state = BASIC_DESC;
        break;

      case SYNOPSIS:
        // Leaving Synopsis
        synopsis.setText(characterData.trim());
        state = BASIC_DESC;
        break;

      case GENRE:
        // Leaving Genre
        state = BASIC_DESC;
        break;

      case MPEG7_NAME:
        // Leaving mpeg7:Name
        state = GENRE;
        genre.addMPEG7Name(characterData.trim());
        break;

      case MEMBER_OF:
        // Leaving MemberOf
        state = GROUP_INFO;
        break;

      case RELATED_MATERIAL:
        // Leaving RelatedMaterial
        state = BASIC_DESC;
        break;

      case HOW_RELATED:
        // Leaving HowRelated
        state = RELATED_MATERIAL;
        break;

      case HOW_RELATED_NAME:
        // Leaving HowRelated Name
      	relatedMaterial.setName(characterData.trim());
        state = HOW_RELATED;
        break;

      case MEDIA_LOCATOR:
        // Leaving MediaLocator
        state = RELATED_MATERIAL;
        break;

      case MEDIA_URI:
        // Leaving MediaURI
        try {
          mediaLocator.setMediaURI(new bbc.rd.tvanytime.URI(characterData.trim()));
        }
        catch (TVAnytimeException tvae) {
          addError("MediaUri: "+tvae.getMessage());
        }
        state = MEDIA_LOCATOR;
        break;

      case PROMOTIONAL_TEXT:
        // Leaving PromotionalText
        state = RELATED_MATERIAL;
        relatedMaterial.setPromotionalText(characterData.trim());
        break;

      case SOURCE_MEDIA_LOCATOR:
        // Leaving SourceMediaLocator
        state = RELATED_MATERIAL;
        break;

      case SOURCE_MEDIA_URI:
        // Leaving SourceMediaURI
        try {
          sourceMediaLocator.setMediaURI(new bbc.rd.tvanytime.URI(characterData.trim()));
        }
        catch (TVAnytimeException tvae) {
          addError("SourceMediaUri: "+tvae.getMessage());
        }
        state = SOURCE_MEDIA_LOCATOR;
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
      case TITLE:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case SYNOPSIS:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case MPEG7_NAME:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case MEDIA_URI:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case SOURCE_MEDIA_URI:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case HOW_RELATED_NAME:
        characterData += String.copyValueOf(ch,start,length);
        break;        
      case PROMOTIONAL_TEXT:
        characterData += String.copyValueOf(ch,start,length);
        break;
        
      default:
    }
  }

  /**
   * Sets attributes for a GroupInformation object.
   */
  private void setGroupInformationAttributes(Attributes atts) {
    // In GroupInformation so get attributes
    attribute = atts.getValue("groupId");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.BASIC) ) {
      try {
        groupInformation.setGroupId(new ContentReference(attribute));
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("GroupInformation: " + tvae.getMessage());
      }
    }
    attribute = atts.getValue("ordered");
    if ((attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
    	// Sets to true if string equals (ignoring case) "true"
        groupInformation.setOrdered(new Boolean(attribute));
    }
  }

  /**
   * Sets attributes for a Title object.
   */
  private void setTitleAttributes(Attributes atts) {
    attribute = atts.getValue("type");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      try {
        if (attribute.equals("main")) title.setType(Title.MAIN);
        else if (attribute.equals("secondary")) title.setType(Title.SECONDARY);
        else if (attribute.equals("alternative")) title.setType(Title.ALTERNATIVE);
        else if (attribute.equals("original")) title.setType(Title.ORIGINAL);
        else if (attribute.equals("popular")) title.setType(Title.POPULAR);
        else if (attribute.equals("opusNumber")) title.setType(Title.OPUSNUMBER);
        else if (attribute.equals("songTitle")) title.setType(Title.SONGTITLE);
        else if (attribute.equals("albumTitle")) title.setType(Title.ALBUMTITLE);
        else if (attribute.equals("seriesTitle")) title.setType(Title.SERIESTITLE);
        else if (attribute.equals("episodeTitle")) title.setType(Title.EPISODETITLE);
        else addError("Title: Invalid title type");
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("Title: " + tvae.getMessage());
      }
    }
  }

  /**
   * Sets attributes from group type.
   */
  private void setGroupTypeAttributes(Attributes atts) {
    attribute = atts.getValue("value");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      try {
        if (attribute.equals("series")) groupInformation.setGroupType(GroupInformation.SERIES);
        else if (attribute.equals("show")) groupInformation.setGroupType(GroupInformation.SHOW);
        else if (attribute.equals("programConcept")) groupInformation.setGroupType(GroupInformation.PROGRAM_CONCEPT);
        else if (attribute.equals("programCompilation")) groupInformation.setGroupType(GroupInformation.PROGRAM_COMPILATION);
        else if (attribute.equals("otherCollection")) groupInformation.setGroupType(GroupInformation.OTHER_COLLECTION);
        else if (attribute.equals("otherChoice")) groupInformation.setGroupType(GroupInformation.OTHER_CHOICE);
        else addError("GroupInformation: Invalid group type of '"+attribute+"'");
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("GroupInformation: Invalid group type of '"+attribute+"'");
      }
    }
  }


  /**
   * Sets attributes for a Synopsis object.
   */
  private void setSynopsisAttributes(Attributes atts) {
    attribute = atts.getValue("length");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      try {
        if (attribute.equals("short")) synopsis.setLength(Synopsis.SHORT);
        else if (attribute.equals("medium")) synopsis.setLength(Synopsis.MEDIUM);
        else if (attribute.equals("long")) synopsis.setLength(Synopsis.LONG);
        else addError("Synopsis: Invalid synopsis length type");
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("Synopsis: " + tvae.getMessage());
      }
    }
    attribute = atts.getValue("xml:lang");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
        synopsis.setLanguage(attribute);
    }
  }


  /**
   * Sets attributes for a Genre object.
   */
  private void setGenreAttributes(Attributes atts) {
    attribute = atts.getValue("type");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      try {
        if (attribute.equals("main")) genre.setType(Genre.MAIN);
        else if (attribute.equals("secondary")) genre.setType(Genre.SECONDARY);
        else if (attribute.equals("other")) genre.setType(Genre.OTHER);
        else addError("Genre: Invalid genre type");
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("Genre: " + tvae.getMessage());
      }
    }

    attribute = atts.getValue("href");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      try {
        genre.setHref(attribute);
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("Genre: " + tvae.getMessage());
      }
    }

  }

  /**
   * Sets attributes for a MemberOf object.
   */
  private void setMemberOfAttributes(Attributes atts) {
    // In MemberOf so get attributes
    attribute = atts.getValue("crid");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      try {
        memberOf.setCRID(attribute);
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("MemberOf: " + tvae.getMessage());
      }
    }
    attribute = atts.getValue("index");
      if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      try {
        int index = Integer.parseInt(attribute);
        memberOf.setIndex(new Integer(index));
      }
      catch (NumberFormatException nfe) {
        // Add message to general exception and continue
        addError("MemberOf: Invalid index number");
      }
    }
  }

  /**
   * Sets attributes for HowRelated information.
   */
  private void setHowRelatedAttributes(Attributes atts) {
    attribute = atts.getValue("href");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
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