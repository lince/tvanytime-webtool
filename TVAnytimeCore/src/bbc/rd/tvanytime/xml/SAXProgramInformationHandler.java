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

import bbc.rd.tvanytime.programInformation.*;
import bbc.rd.tvanytime.creditsInformation.*;
import bbc.rd.tvanytime.*;
import bbc.rd.mpeg7.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;

import java.util.Vector;

/**
 * SAXProgramInformationHandler: SAX event hadler used by SAXXMLParser to parse
 * ProgramInformationTable.
 * Uses state machine to track current position in document.
 *
 * Error handling:
 *    Throws SAX XML exceptions
 *    Catches invalid TVAnytime data fields, unsets the variable and keep error
 *    message to end.
 *
 * Parsing profiles:
 * Two profiles are defined for which parts of the XML are parsed.
 *    BASIC: Only parses required/mandatory elements and attributes...
 *        ProgramInformation
 *          BasicDescription
 *            Title
 *
 *    STANDARD: Parses all of the currently used elements and attributes...
 *        ProgramInformation
 *          BasicDescription
 *            Title
 *            Synopsis
 *            Genre
 *            RelatedMaterial
 *			      CaptionLanguage
 *			      SignLanguage
 *            CreditsList
 *          AVAttributes
 *            AudioAttributes
 *              NumOfChannels
 *            VideoAttributes
 *              AspectRatio
 *          MemberOf
 *
 * @author Tristan Ferne, BBC Research & Development, September 2002
 * @version 1.0
 */

class SAXProgramInformationHandler extends DefaultHandler {

  /**
   * Constants for program information table that define what state the parser is in.
   */
  private static final int UNKNOWN = 0;
  private static final int PROG_INFO_TABLE = 1;
  private static final int PROG_INFO = 2;

  private static final int BASIC_DESC = 3;
  private static final int TITLE = 4;
  private static final int MEDIA_TITLE = 5;
  private static final int SYNOPSIS = 6;
  private static final int KEYWORD = 7;
  private static final int GENRE = 8;
  private static final int MPEG7_NAME = 9;
  private static final int RELATED_MATERIAL = 10;
  private static final int CAPTION_LANGUAGE = 11;
  private static final int SIGN_LANGUAGE = 12;
  private static final int PROMOTIONAL_INFO = 13;

  private static final int HOW_RELATED = 300;
  private static final int MEDIA_LOCATOR = 301;
  private static final int MEDIA_URI = 302;
  private static final int PROMOTIONAL_TEXT = 303;
  private static final int SOURCE_MEDIA_LOCATOR = 304;
  private static final int SOURCE_MEDIA_URI = 305;
  private static final int HOW_RELATED_NAME = 306;

  private static final int CREDITS_LIST = 310;
  private static final int CREDITS_ITEM = 311;
  private static final int PERSON_NAME = 312;
  private static final int GIVEN_NAME_PERSON = 313;
  private static final int FAMILY_NAME_PERSON = 314;
  private static final int CHARACTER = 315;
  private static final int GIVEN_NAME_CHARACTER = 316;
  private static final int FAMILY_NAME_CHARACTER = 317;
  private static final int ORGANIZATION_NAME = 318;

  private static final int AV_ATTRIBUTES = 100;
  private static final int AUDIO_ATTRIBUTES = 101;
  private static final int NUM_OF_CHANNELS = 102;
  private static final int VIDEO_ATTRIBUTES = 110;
  private static final int ASPECT_RATIO = 111;

  private static final int MEMBER_OF = 200;

  /**
   * Profile for XML parsing.
   */
  private int parseProfile = SAXXMLParser.STANDARD;
  /**
   * Current state of parser.
   */
  private int state = PROG_INFO_TABLE;
  /**
   * Number of levels of unknown elements we are currently in.
   */
  private int unknownDepth = 0;
  /**
   * Last recognised state before entering unknown element.
   */
  private int savedState = PROG_INFO_TABLE;
  /**
   * Reference to clients programInformationTable.
   */
  private ProgramInformationTable programInformationTable;
  /**
   * XML parser being used by parent that generates events to be handled.
   */
  private SAXParser parser;
  /**
   * Parent SAX event handler that asked this to handle events.
   */
  private DefaultHandler parent;
  /**
   * Reference to Vector of strings containing information about non-fatal exceptions.
   */
  private Vector exceptionStack;
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
  private ProgramInformation programInformation;
  private BasicDescription basicDescription;
  private Title title;
  private Synopsis synopsis;
  private Genre genre;
  private AVAttributes avAttributes;
  private AudioAttributes audioAttributes;
  private VideoAttributes videoAttributes;
  private AspectRatio aspectRatio;
  private MemberOf memberOf;
  private RelatedMaterial relatedMaterial;
  private CaptionLanguage captionLanguage;
  private SignLanguage signLanguage;
  private PromotionalInformation promotionalInformation;
  private MPEG7MediaLocator mediaLocator;
  private MPEG7MediaLocator sourceMediaLocator;
  private CreditsList creditsList;
  private CreditsItem creditsItem;
  private PersonName personName;
  private bbc.rd.tvanytime.creditsInformation.Character character;
  private OrganizationName organizationName;
  private Name name;
  private Keyword keyword;


  /**
   *
   */
  SAXProgramInformationHandler() {
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
   * Sets ProgramInformationTable to populate with parsed data.
   *
   * @param   programInformationTable   Table to populate with parsed data.
   */
  void setTable(ProgramInformationTable programInformationTable) {
    this.programInformationTable = programInformationTable;
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
    //System.out.println("State = "+state+", entering "+qName);
    switch(state) {
      case PROG_INFO_TABLE:
        // In ProgramInformationTable looking for ProgramInformation
        if ( qName.equals("ProgramInformation") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = PROG_INFO;
          // Create new ProgramInformation object
          programInformation = new ProgramInformation();
          // Set attributes
          setProgramInformationAttributes(atts);
          // Add to client's table
          programInformationTable.addProgramInformation(programInformation);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case PROG_INFO:
        // In ProgramInformation looking for BasicDescription
        if ( qName.equals("BasicDescription") && (parseProfile >= SAXXMLParser.BASIC) ) {
          state = BASIC_DESC;
          // Create new BasicDescription
          basicDescription = new BasicDescription();
          // Add to ProgramInformation
          programInformation.setBasicDescription(basicDescription);
        }
        // Looking for AVAttributes
        else if ( qName.equals("AVAttributes") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = AV_ATTRIBUTES;
          // Create new AVAttributes
          avAttributes = new AVAttributes();
          // Add to ProgramInformation
          programInformation.setAVAttributes(avAttributes);
        }
        // Looking for MemberOf
        else if ( qName.equals("MemberOf") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = MEMBER_OF;
          // Create new MemberOf
          memberOf = new MemberOf();
          // Set attributes
          setMemberOfAttributes(atts);
          // Add to ProgramInformation
          programInformation.addMemberOf(memberOf);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
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
        else if (qName.equals("PromotionalInformation") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = PROMOTIONAL_INFO;
          // Create new PromotionalInformation
          promotionalInformation = new PromotionalInformation();
          // Add to BasicDescription
          basicDescription.addPromotionalInformation(promotionalInformation);
        }
        else if (qName.equals("Keyword") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = KEYWORD;
          // Create new Keyword
          keyword = new Keyword();
          // Add to BasicDescription
          basicDescription.addKeyword(keyword);
          // Set attributes
          setKeywordAttributes(atts);
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
        else if (qName.equals("CaptionLanguage") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = CAPTION_LANGUAGE;
          // Create new CaptionLanguage
          captionLanguage = new CaptionLanguage();
          // Add to BasicDescription
          basicDescription.addCaptionLanguage(captionLanguage);
          // Set attributes
          setCaptionLanguageAttributes(atts);
        }
        else if (qName.equals("SignLanguage") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = SIGN_LANGUAGE;
          // Create new SignLanguage
          signLanguage = new SignLanguage();
          // Add to BasicDescription
          basicDescription.addSignLanguage(signLanguage);
          // Set attributes
          setSignLanguageAttributes(atts);
        }
        else if (qName.equals("RelatedMaterial") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = RELATED_MATERIAL;
          // Create new RelatedMaterial
          relatedMaterial = new RelatedMaterial();
          // Add to BasicDescription
          basicDescription.addRelatedMaterial(relatedMaterial);
        }
        else if (qName.equals("CreditsList") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = CREDITS_LIST;
          // Create new CreditsList
          creditsList = new CreditsList();
          // Add to BasicDescription
          basicDescription.setCreditsList(creditsList);
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

      case CREDITS_LIST:
        if (qName.equals("CreditsItem") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = CREDITS_ITEM;
          creditsItem = new CreditsItem();
          creditsList.addCreditsItem(creditsItem);
          setCreditsItemAttributes(atts);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case CREDITS_ITEM:
        if (qName.equals("PersonName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = PERSON_NAME;
          personName = new PersonName();
          creditsItem.addPersonName(personName);
        }
        else if (qName.equals("Character") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = CHARACTER;
          character = new bbc.rd.tvanytime.creditsInformation.Character();
          creditsItem.addCharacter(character);
        }
        else if (qName.equals("OrganizationName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = ORGANIZATION_NAME;
          organizationName = new OrganizationName();
          creditsItem.addOrganizationName(organizationName);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case PERSON_NAME:
        //if (qName.equals("mpeg7:GivenName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
        if (qName.equals("GivenName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = GIVEN_NAME_PERSON;
          this.name = new Name();
          personName.setGivenName(this.name);
        }
        //else if (qName.equals("mpeg7:FamilyName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
        else if (qName.equals("FamilyName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = FAMILY_NAME_PERSON;
          this.name = new Name();
          personName.setFamilyName(this.name);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case CHARACTER:
        //if (qName.equals("mpeg7:GivenName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
        if (qName.equals("GivenName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = GIVEN_NAME_CHARACTER;
          this.name = new Name();
          character.setGivenName(this.name);
        }
        //else if (qName.equals("mpeg7:FamilyName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
        else if (qName.equals("FamilyName") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = FAMILY_NAME_CHARACTER;
          this.name = new Name();
          character.setFamilyName(this.name);
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
        //if (qName.equals("mpeg7:MediaUri") && (parseProfile >= SAXXMLParser.STANDARD) ) {
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
        //if (qName.equals("mpeg7:MediaUri") && (parseProfile >= SAXXMLParser.STANDARD) ) {
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
      case AV_ATTRIBUTES:
        if (qName.equals("AudioAttributes") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = AUDIO_ATTRIBUTES;
          // Create new AudioAttributes
          audioAttributes = new AudioAttributes();
          // Add to AVAttributes
          avAttributes.setAudioAttributes(audioAttributes);
        }
        else if (qName.equals("VideoAttributes") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = VIDEO_ATTRIBUTES;
          // Create new VideoAttributes
          videoAttributes = new VideoAttributes();
          // Add to AVAttributes
          avAttributes.setVideoAttributes(videoAttributes);
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case AUDIO_ATTRIBUTES:
        if (qName.equals("NumOfChannels") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = NUM_OF_CHANNELS;
        }
        else {
          // Entered unknown element
          savedState = state;
          state = UNKNOWN;
          unknownDepth = 1;
        }
        break;

      case VIDEO_ATTRIBUTES:
        if (qName.equals("AspectRatio") && (parseProfile >= SAXXMLParser.STANDARD) ) {
          state = ASPECT_RATIO;
          aspectRatio = new AspectRatio();
          try {
            videoAttributes.addAspectRatio(aspectRatio);
          }
          catch (TVAnytimeException tvae) {
            addError("AspectRatio: "+tvae.getMessage());
          }
          setAspectRatioAttributes(atts);
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
    //System.out.println("State = "+state+", leaving "+qName);
    switch(state) {
      case PROG_INFO_TABLE:
        // Leaving table...
        // Set parser handler to parent when finished.
        parser.getXMLReader().setContentHandler(parent);
        break;

      case PROG_INFO:
        // Leaving ProgramInformation
        state = PROG_INFO_TABLE;
        break;

      case BASIC_DESC:
        // Leaving BasicDescription
        state = PROG_INFO;
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

      case PROMOTIONAL_INFO:
        // Leaving PromotionalInformation
        promotionalInformation.setPromotionalInformation(characterData.trim());
        state = BASIC_DESC;
        break;

      case KEYWORD:
        // Leaving Keyword
        keyword.setKeyword(characterData.trim());
        state = BASIC_DESC;
        break;

      case GENRE:
        // Leaving Genre
        state = BASIC_DESC;
        break;

      case CAPTION_LANGUAGE:
        // Leaving CaptionLanguage
        captionLanguage.setLanguage(characterData.trim());
        state = BASIC_DESC;
        break;

      case SIGN_LANGUAGE:
        // Leaving SignLanguage
        signLanguage.setLanguage(characterData.trim());
        state = BASIC_DESC;
        break;

      case MPEG7_NAME:
        // Leaving Name
        state = GENRE;
        genre.addMPEG7Name(characterData.trim());
        break;

      case CREDITS_LIST:
        // Leaving credits list
        state = BASIC_DESC;
        break;

      case CREDITS_ITEM:
        // Leaving credits item
        state = CREDITS_LIST;
        break;

      case PERSON_NAME:
        // Leaving person name
        state = CREDITS_ITEM;
        break;

      case CHARACTER:
        // Leaving character
        state = CREDITS_ITEM;
        break;

      case ORGANIZATION_NAME:
        // Leaving organization name
        state = CREDITS_ITEM;
        organizationName.setOrganizationName(characterData.trim());
        break;

      case GIVEN_NAME_PERSON:
        // Leaving given name
        state = PERSON_NAME;
        this.name.setName(characterData.trim());
        break;

      case FAMILY_NAME_PERSON:
        // Leaving family name
        state = PERSON_NAME;
        this.name.setName(characterData.trim());
        break;

      case GIVEN_NAME_CHARACTER:
        // Leaving given name
        state = CHARACTER;
        this.name.setName(characterData.trim());
        break;

      case FAMILY_NAME_CHARACTER:
        // Leaving family name
        state = CHARACTER;
        this.name.setName(characterData.trim());
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

      case AV_ATTRIBUTES:
        // Leaving AVAttributes
        state = PROG_INFO;
        break;

      case AUDIO_ATTRIBUTES:
        // Leaving AudioAttributes
        state = AV_ATTRIBUTES;
        break;

      case VIDEO_ATTRIBUTES:
        // Leaving VideoAttributes
        state = AV_ATTRIBUTES;
        break;

      case NUM_OF_CHANNELS:
        // Leaving NumOfChannels
        state = AUDIO_ATTRIBUTES;
        try {
          audioAttributes.setNumOfChannels(new Integer(Integer.parseInt(characterData.trim())));
        }
        catch (NumberFormatException nfe) {
          addError("NumberOfChannels: "+nfe.getMessage());
        }
        catch (TVAnytimeException tvae) {
          addError("NumberOfChannels: "+tvae.getMessage());
        }
        break;

      case ASPECT_RATIO:
        // Leaving AspectRatio
        state = VIDEO_ATTRIBUTES;
        try {
          aspectRatio.setAspectRatio(characterData.trim());
        }
        catch (TVAnytimeException tvae) {
          addError("AspectRatio: "+tvae.getMessage());
        }
        break;

      case MEMBER_OF:
        // Leaving MemberOf
        state = PROG_INFO;
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

  /**
   * New character data available.
   */
  public void characters(char[] ch, int start, int length) {
    switch (state) {
      case TITLE:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case SYNOPSIS:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case PROMOTIONAL_INFO:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case KEYWORD:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case CAPTION_LANGUAGE:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case SIGN_LANGUAGE:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case MPEG7_NAME:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case NUM_OF_CHANNELS:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case ASPECT_RATIO:
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
      case GIVEN_NAME_PERSON:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case FAMILY_NAME_PERSON:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case GIVEN_NAME_CHARACTER:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case FAMILY_NAME_CHARACTER:
        characterData += String.copyValueOf(ch,start,length);
        break;
      case ORGANIZATION_NAME:
        characterData += String.copyValueOf(ch,start,length);
        break;

      default:
    }
  }

  /**
   * Sets attributes for a ProgramInformation object.
   */
  private void setProgramInformationAttributes(Attributes atts) {
    // In ProgramInformation so get attributes
    attribute = atts.getValue("programId");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.BASIC) ) {
      try {
        programInformation.setProgramID(new ContentReference(attribute));
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("ProgramInformation: " + tvae.getMessage());
      }
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
   * Sets attributes for a CaptionLanguage object.
   */
  private void setCaptionLanguageAttributes(Attributes atts) {
    attribute = atts.getValue("closed");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
		if (attribute.equals("true")) captionLanguage.setClosed(true);
		else if (attribute.equals("false")) captionLanguage.setClosed(false);
    }
    attribute = atts.getValue("supplemental");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
		if (attribute.equals("true")) captionLanguage.setSupplemental(true);
		else if (attribute.equals("false")) captionLanguage.setSupplemental(false);
    }
  }

  /**
   * Sets attributes for a SignLanguage object.
   */
  private void setSignLanguageAttributes(Attributes atts) {
    attribute = atts.getValue("primary");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
		if (attribute.equals("true")) signLanguage.setPrimary(true);
		else if (attribute.equals("false")) signLanguage.setPrimary(false);
    }
    attribute = atts.getValue("translation");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
		if (attribute.equals("true")) signLanguage.setTranslation(true);
		else if (attribute.equals("false")) signLanguage.setTranslation(false);
    }
    attribute = atts.getValue("type");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
        signLanguage.setType(attribute);
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
   * Sets attributes for an AspectRatio object.
   */
  private void setAspectRatioAttributes(Attributes atts) {
    attribute = atts.getValue("type");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      try {
        if (attribute.equals("original")) aspectRatio.setType(AspectRatio.ORIGINAL);
        else if (attribute.equals("publication")) aspectRatio.setType(AspectRatio.PUBLICATION);
        else addError("AspectRatio: Invalid aspect ratio type");
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("AspectRatio: " + tvae.getMessage());
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
   * Sets attributes for a CreditsItem object.
   */
  private void setCreditsItemAttributes(Attributes atts) {
    attribute = atts.getValue("role");
    if ( (attribute!=null) && (parseProfile >= SAXXMLParser.STANDARD) ) {
      try {
        creditsItem.setRole(attribute);
      }
      catch (TVAnytimeException tvae) {
        // Add message to general exception and continue
        addError("CreditsItem: " + tvae.getMessage());
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