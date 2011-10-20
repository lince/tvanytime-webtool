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

import bbc.rd.tvanytime.*;
import org.xml.sax.*;
import java.io.*;

/**
 * XMLParser: Interface for TVAnytime XML parsers.
 *
 * @author Tristan Ferne, BBC Research & Development, April 2002
 * @version 1.0
 */

public interface XMLParser  {

  /**
   * Parse the content of the given InputSource as TVAnytime XML.
   *
   * @param   is  The InputSource containing the content to be parsed.
   * @throws  IOException   If any IO errors occur.
   * @throws  TVAnytimeException  If the underlying parser throws an error while parsing.
   * @throws  NonFatalXMLException  If the underlying parser throws a non-fatal error while parsing.
   */
  public void parse(InputSource is) throws IOException, TVAnytimeException, NonFatalXMLException;

  /**
   * Parse the content of the file specified as TVAnytime XML.
   *
   * @param   f  File containing the TVAnytime XML.
   * @throws  IOException   If any IO errors occur.
   * @throws  TVAnytimeException  If the underlying parser throws an error while parsing.
   * @throws  NonFatalXMLException  If the underlying parser throws a non-fatal error while parsing.
   */
  public void parse(File f) throws IOException, TVAnytimeException, NonFatalXMLException;

  /**
   * Parse the content of the given InputStream as TVAnytime XML.
   *
   * @param   is  The InputStream containing the content to be parsed.
   * @throws  IOException   If any IO errors occur.
   * @throws  TVAnytimeException  If the underlying parser throws an error while parsing.
   */
  public void parse(InputStream is) throws IOException, TVAnytimeException;

  /**
   * Parse the content described by the given Universal Resource Identifier (URI).
   *
   * @param   uri  The location of the content to be parsed.
   * @throws  IOException   If any IO errors occur.
   * @throws  TVAnytimeException  If the underlying parser throws an error while parsing.
   */
  public void parse(String uri) throws IOException, TVAnytimeException;


  /**
   * Sets whether the parser should validate the XML.
   *
   * @param   validating    Whether the parser should validate.
   * @throws  TVAnytimeException  if couldn't set property.
   */
  public void setValidating(boolean validating) throws TVAnytimeException;

}