package org.jrabbit.base.data;

/*****************************************************************************
 * An object that is Referenced has an associated string that somehow identifies
 * it.
 * 
 * For example, an Image is referenced by an "name" (possibly the same
 * string that specifies its location in the file system).
 * 
 * The built-in caching of jRabbit requires that the type of object it stores be
 * referenced.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Referenced
{
	/*************************************************************************
	 * Gets the reference associated with this object.
	 * 
	 * @return The String that identifies the object.
	 *************************************************************************/
	public String reference();
}
