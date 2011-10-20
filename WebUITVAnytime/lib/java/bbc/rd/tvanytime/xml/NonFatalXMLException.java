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

/**
 * NonFatalXMLException: An exception to be thrown by implementations of
 * XMLParser when XML is valid but invalid TVAnytime data values are found.
 *
 * @author Tristan Ferne, BBC Research & Development, May 2002
 * @version 1.0
 */

public class NonFatalXMLException extends TVAnytimeException
{
	/**
	 * Constructor for objects of type NonFatalXMLException.
	 *
	 * @param	msg	the string message to display when the exception is thrown
	 */
	public NonFatalXMLException(String msg)
	{
		super("NonFatalXMLException: "+msg);	// call Exception's constructor and associate msg string with Exception
	}
}