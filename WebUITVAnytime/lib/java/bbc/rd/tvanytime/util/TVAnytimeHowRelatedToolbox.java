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


package bbc.rd.tvanytime.util;

import java.util.*;

/**
 * TVAnytimeHowRelatedToolBox: Implementation of GenreToolbox specifically for TV-Anytime HowRelatedCS.
 *
 * Note: This class extends GenreToolBox, which has method names specific to 'genres'.  HowRelated
 * is not a genre as such, but the method names map across reasonably.
 *
 * @author Tim Sargeant, BBC Research & Development, Feb 2003
 * @version 1.0
 */

public class TVAnytimeHowRelatedToolbox extends GenreToolbox
{
	private static Hashtable table;

	//called when initializing class
	static
	{
		//table to store TVAnytime HowRelated
		table = new Hashtable(20);

		// HowRelated table
		table.put("1", "Trailer");
		table.put("2", "Group trailer");
		table.put("3", "Sibling");
		table.put("4", "Alternative");
		table.put("5", "Parent");
		table.put("6", "Recommendation");
		table.put("7", "Group recommendation");
		table.put("8", "Commercial Advert");
		table.put("9", "Direct product purchase");
		table.put("10", "For more information");
		table.put("11", "Programme review information");
		table.put("12", "Recap");
		table.put("13", "The making of");
		table.put("14", "Support");
	}

	//to prevent initialization
	private TVAnytimeHowRelatedToolbox()
	{
	}

	/**
	 * getNameHeirarchy - converts a numbered heirarchy to its corresponding name heirarchy
	 *
	 * @param	numberHeirarchy	the numbered heirarchy String to be converted
	 * @return 	the name heirarchy String
	 */
	public static String getNameHierarchy(String numberHierarchy)
	{
	    if (numberHierarchy != null) return (String)table.get(numberHierarchy);
   			else return "<null>";
	}

	/**
	 * getNumberHierarchy - converts a named hierarchy to its corresponding number hierarchy
	 *
	 * @param 	nameHierarchy	the named hierarchy String to be converted
	 * @return 	the name hierarchy String
	 */
	public static String getNumberHierarchy(String nameHierarchy)
	{
		String genreNumber = "";

		//list all the number heirarchies
		Enumeration genreNumbers = table.keys();

		//go through all the number heirarchies looking for the matching name hierarchy
		while (genreNumbers.hasMoreElements())
		{
			genreNumber = (String)genreNumbers.nextElement();

			if (nameHierarchy.equalsIgnoreCase((String)table.get(genreNumber)))
				return genreNumber;
		}

		return null;
	}

	/**
	 * isValid - verifies whether a genre hierarchy is included in the current genre scheme
	 *
	 * @param 	genreHierarchy	the hierarchy (number or name) String to be verified. If a name
	 * hierarchy is passed it must have correct case
	 *
	 * @return 	whether the hierarchy is valid as a boolean object
	 */
	public static boolean isValid(String hierarchy)
	{
		if (table.containsKey(hierarchy))
			return true;

		else if(table.contains(hierarchy))
			return true;

		else return false;

	}

	/**
	 * getSubGenres - from a genre heading(in numbered hierarchy form) get any sub-genres
	 *
	 * @param 	genreParent	the genre heading (number hierarchy String) from which sub genres are to be found
	 * @return	null, because not a hierarchical scheme.
	 */
	public static Vector getSubGenres(String genreParent)
	{
		return null;
	}

	/**
	 * getTopLevelSubGenres - from a genre heading(in numbered hierarchy form) get
	 * the immediate child sub-genres only.
	 *
	 * @param 	genreParent	 Only valid option is null which  returns all top-level genres only.
	 * @return	a Vector of the numbered hierarchy Strings of the top-level items.
	 */
	public static Vector getTopLevelSubGenres(String genreParent)
	{
		Vector genres = new Vector(0,1);

		//list all number heirarchies
		Enumeration genreNumbers = table.keys();

		//look through all the name heirarchies corresponding to the name heirarchies
		while (genreNumbers.hasMoreElements())
		{
      genres.addElement(genreNumbers.nextElement());
		}

		return genres;
	}

	/**
	 * findGenre - returns the named hierarchy of any genres containing the specifed genre name
	 *
	 * @param	genreName	the genre name to be searched for
	 *
	 * @return	a Vector of the Genre name heirarchy strings containing the Genre name
	 */
	public static Vector findGenre(String searchName)
	{
		Vector genres = new Vector(0,1);
		String genreName = "";
		searchName = searchName.toLowerCase();
		//list all the name heirarchies
		Enumeration genreNames = table.elements();
		while (genreNames.hasMoreElements())
		{
			genreName = ((String)genreNames.nextElement()).toLowerCase();

			//if the name hierarchy contains the Genre name we're looking for
			if (genreName.indexOf(searchName)>= 0)
				genres.addElement(genreName);
		}

		return genres;
	}

	/**
	 * getParent - returns the Genre hierarchy of the Genre one level up the Genre
	 * hierarchy from the the Genre hierarchy passed
	 *
	 * @param	hierarchy	the hierarchy String of the Genre whose parent is desired (can be numbered
	 * or named)
	 *
	 * @return 	null, because not hierarchical.
	 */
	public static String getParent(String hierarchy)
	{
    return null;
	}

	/**
	 * getNumLevels - returns the number of levels used in the Genre hierarchy passed
	 *
	 * @param	hierarchy 	the numbered or named hierarchy of the Genre object whose number of levels is desired
	 *
	 * @return 	the number of levels in the passed Genre hierarchy (always 1).
	 */
	public static int getNumLevels(String hierarchy)
	{
		return 1;
	}
}