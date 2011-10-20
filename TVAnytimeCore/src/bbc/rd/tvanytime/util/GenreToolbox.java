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


package bbc.rd.tvanytime.util;

import java.util.Vector;

/**
 * GenreToolBox: Contains tools for use with Genres. Should not be instantiated. Should
 * be inherited from and have its static methods implemented.
 *
 * @author Chris Akanbi, BBC Research & Development, April 2002
 * @version 1.0
 */

//class should be treated as abstract and inherited from for implementation
public class GenreToolbox
{
	//to prevent initialization(ish)
	protected GenreToolbox()
	{
	}

	/**
	 * getNameHierarchy - converts a numbered hierarchy to its corresponding name hierarchy
	 *
	 * @param	numberHierarchy	the numbered hierarchy String to be converted
	 * @return null
	 */
	public static String getNameHierarchy(String numberHierarchy)
	{
		return null;
	}

	/**
	 * getNumberHierarchy - converts a named hierarchy to its corresponding number hierarchy
	 *
	 * @param 	nameHierarchy	the named hierarchy String to be converted
	 * @return 	null
	 */
	public static String getNumberHierarchy(String nameHierarchy)
	{
		return null;
	}

	/**
	 * getSubGenres - from a genre heading(in numbered hierarchy form) get any sub-genres
	 *
	 * @param 	genreParent	the genre heading(numbered hierarchy String) from which sub genres are to be found
	 * @return	null
	 */
	public static Vector getSubGenres(String genreParent)
	{
		return null;
	}

	/**
	 * getTopLevelSubGenres - from a genre heading(in numbered hierarchy form) get
	 * the immediate child sub-genres only.
	 *
	 * @param 	genreParent	the genre heading (number hierarchy) from which sub genres
	 *          are to be found. If null then returns all top-level genres only.
	 * @return	null
	 */
	public static Vector getTopLevelSubGenres(String genreParent) {
		return null;
	}

	/**
	 * findGenre - returns the named hierarchy of any genres containing the specifed genre name
	 *
	 * @param	genreName	the genre name String to be searched for
	 *
	 * @return	null
	 */
	public static Vector findGenre(String genreName)
	{
		return null;
	}

	/**
	 * isValid - verifies whether a genre hierarchy is included in the current genre scheme
	 *
	 * @param 	genreHierarchy the hierarchy String (named or numbered)to be verified
	 *
	 * @return 	false
	 */
	public static boolean isValid(String genreHierarchy)
	{
		return false;
	}

	/**
	 * getParent - returns the Genre hierarchy of the Genre one level up the Genre
	 * hierarchy from the the Genre hierarchy passed
	 *
	 * @param	hierarchy	the hierarchy String of the Genre whose parent is desired (can be numbered
	 * or named)
	 *
	 * @return 	null
	 */
	public static String getParent(String hierarchy)
	{
		return null;
	}

	/**
	 * getNumLevels - returns the number of levels used in the Genre hierarchy passed
	 *
	 * @param	hierarchy	the numbered or named hierarchy String of the Genre object whose number of levels is desired
	 *
	 * @return 	0
	 */
	public static int getNumLevels(String hierarchy)
	{
		return 0;
	}
}


