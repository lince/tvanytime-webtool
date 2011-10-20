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


package bbc.rd.tvanytime.search;

import java.util.*;
import bbc.rd.tvanytime.*;

/**
 * <P>MetadataSearch: Interface defining search functions of TVAnytime metadata
 * tables: <I>ProgramInformation, ProgramLocation, GroupInformation,
 * SegmentationInformation</I></P>
 * <BR>
 * <P>Implementations should throw SearchInterfaceNotSupportedException if they do
 * not support this function.</P>
 *
 * @author Tristan Ferne, BBC Research & Development, May 2002
 * @version 1.0
 */

public interface MetadataSearch  {

  /**
   * Get program location information for the specified CRID.
   *
   * @param   crid  the CRID of the ProgramLocation object to be searched for.
   * @return Vector  containing ProgramLocation objects.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getProgramLocation(ContentReference crid) throws SearchInterfaceNotSupportedException;

  /**
   * Get program information for the specified CRID.
   *
   * @param   crid  the CRID of the ProgramInformation object to be searched for.
   * @return Vector  containing ProgramInformation objects.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getProgramInformation(ContentReference crid) throws SearchInterfaceNotSupportedException;

  /**
   * Get segment information for the specified CRID.
   *
   * @param   crid  the CRID of the SegmentInformation object to be searched for.
   * @return Vector  containing SegmentInformation objects.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getSegmentInformation(ContentReference crid) throws SearchInterfaceNotSupportedException;

  /**
   * Get group information for the specified CRID.
   *
   * @param   crid  the CRID of the GroupInformation object to be searched for.
   * @return Vector  containing GroupInformation objects.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  public Vector getGroupInformation(ContentReference crid) throws SearchInterfaceNotSupportedException;
}