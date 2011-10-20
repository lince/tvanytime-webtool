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


package bbc.rd.tvanytime.creditsInformation;
/**
 * Represents a TV-Anytime Name object.
 * UNFINISHED: Doesn't support "initial" and "abbrev" attributes.
 *
 * @author Tristan Ferne, BBC Research & Development, April 2003
 *
 * @version 1.0
 */
public class Name  implements Cloneable  {

  /**
   * Name.
   */
  private String name;

  /**
   * Constructor.
   */
  public Name() {
  }

  /**
   * Constructor.
   *
   * @param  name  Name.
   */
  public Name(String name) {
    setName(name);
  }

  /**
   * Set name.
   *
   * @param  name  Name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get name.
   *
   * @return  Name.
   */
  public String getName() {
    return name;
  }



	/**
	 * toString - returns a String representation of this object with the specified number of tab indentations
	 *
	 * @return 		the String representation of the object
	 */
	public String toString(int indent) {
		String sysOut = "";

		//add required number of tabs
		for (int i=0;i<indent;i++) {
			sysOut += "\t";
    }
		sysOut += "Name: "+name;
    return sysOut;
  }

	/**
	 * toString - returns a String representation of this object with the specified number of tab indentations
	 *
	 * @return 		the String representation of the object
	 */
	public String toString() {
    return toString(0);
  }

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    Name clone = new Name();
    if (name != null) clone.setName(new String(name));
    return clone;
  }


}