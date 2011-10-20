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


package bbc.rd.tvanytime;

/**
 * MemberOf: Represents a CRID that is used to identify a group.
 *
 * @author Tristan Ferne, BBC Research & Development, April 2002
 * @version 1.0
 */

public class MemberOf extends ContentReference implements Cloneable
{
  /**
   * The index within a group.
   */
	private Integer index;

	/**
	 * Constructor for objects of class MemberOf.
	 */
	public MemberOf()
	{

	}

	/**
	 * Constructor for objects of class MemberOf.
   *
	 * @param  crid  String representation of the CRID (e.g. "crid://bbc.co.uk/21837").
   * @throws TVAnytimeException  Thrown if passed invalid CRID.
	 */
	public MemberOf(String crid) throws TVAnytimeException
	{
    super(crid);
	}

	/**
	 * Returns the index for this CRID within a group
	 *
	 * @return  The index of this object within its group.
	 */
	public Integer getIndex()
	{
		return index;
	}

	/**
	 * Sets the index for this CRID within a group
	 *
	 * @param  index  The index of this object within its group.
	 */
	public void setIndex(Integer index)
	{
    this.index = index;
	}

  /**
   * Returns an XML representation of this object.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  XML representation of the MemberOf object.
   */
    public String toXML(int indent) {
        StringBuffer xmlBuf = new StringBuffer();
        //String xml = "";
        synchronized(xmlBuf) {
            for (int i=0;i<indent;i++) {
                xmlBuf.append("\t");
                //xml += "\t";
            }
            xmlBuf.append("<MemberOf crid=\"");
            xmlBuf.append(getCRID());
            xmlBuf.append("\"");
            //xml = xml + "<MemberOf crid=\""+getCRID()+"\"";

            if (index!=null) {
                xmlBuf.append(" index=\"");
                xmlBuf.append(index.intValue());
                xmlBuf.append("\"");
                //xml = xml + " index=\"" + index.intValue() + "\"";
            }
            xmlBuf.append("/>");
            //xml = xml + "/>";
            return xmlBuf.toString();
        }
    }


  /**
   * Returns a string representation of this object.
   *
   * @param   indent  Number of tabs with which to indent the string.
   * @return  string representation of the MemberOf object.
   */
	public String toString(int indent)
	{
    String sysOut = "";
    for (int i=0;i<indent;i++)
			sysOut += "\t";
    sysOut = sysOut + "MemberOf: "+super.toString(0);

    if (index!=null) {
      indent++;
      sysOut += "\n";
  		for (int i=0;i<indent;i++) {
  			sysOut += "\t";
      }
      sysOut = sysOut + "index: " + index.intValue();
    }
    return sysOut;
	}

  /**
   * Clones itself.
   * @return  A copy of itself.
   */
  public Object clone() {
    MemberOf clone = new MemberOf();
    try {
      if (getCRID() != null) clone.setCRID(new String(getCRID()));
      if (index != null) clone.setIndex(index);
    } catch (TVAnytimeException tvae) { }
    return clone;
  }

}