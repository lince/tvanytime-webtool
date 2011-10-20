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

import bbc.rd.tvanytime.*;

import java.util.*;

/**
 * LocationResolution: Interface for location resolution services.
 *
 * @author Tristan Ferne, BBC Research & Development, April 2002
 * @version 1.0
 */

public interface LocationResolution  {

  /**
   * Returns Vector of all locations for this CRID. This is implemented within the ProgramLocationTable,
   * where CRIDs are known to point to locators. The 'resolveCRID' method is implemented within the ContentReferencingTable,
   * where CRIDs can point either to locators or other CRIDS.
   *
   * @param   crid  CRID to search for.
   * @return  Vector of all locations <I>or</I> a Vector of member ContentReferences for this CRID.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  Vector resolveLeafCRID(ContentReference crid) throws SearchInterfaceNotSupportedException;

  /**
   * Returns Vector of all locations for this CRID on the specified service. This is implemented within the ProgramLocationTable,
   * where CRIDs are known to point to locators. The 'resolveCRID' method is implemented within the ContentReferencingTable,
   * where CRIDs can point either to locators or other CRIDS.
   *
   * @param   crid  CRID to search for.
   * @param   serviceID  The serviceID of the service we are interested in.
   * @return  Vector of all locations <I>or</I> a Vector of member ContentReferences for this CRID.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  Vector resolveLeafCRID(ContentReference crid, String serviceID) throws SearchInterfaceNotSupportedException;

  /**
   * Returns array of CRIDs for all events that start at a time between the
   * start time and the end time.
   * It is recommended that implementations return this array in time order.
   *
   * @param   startTime   Start time of schedule.
   * @param   endTime     End time of schedule.
   * @return Vector of all ContentReferences in this time period.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  Vector getSchedule(Date startTime, Date endTime) throws SearchInterfaceNotSupportedException;

  /**
   * Returns array of CRIDs for all events that start at a time between the
   * start time and the end time and are on the specified service.
   * It is recommended that implementations return this array in time order.
   *
   * @param   startTime   Start time of schedule.
   * @param   endTime     End time of schedule.
   * @param   serviceID   The serviceID of the service we are interested in.
   * @return Vector of all ContentReferences on this service in this time period.
   * @throws  SearchInterfaceNotSupportedException   If the implementation doesn't support this function.
   */
  Vector getSchedule(Date startTime, Date endTime, String serviceID) throws SearchInterfaceNotSupportedException;
}