package org.jrabbit.base.data.structures.base;

/*****************************************************************************
 * A Remover is a general-purpose interface for "removing" objects. It simply
 * describes one method - a method to remove an object and return a flag if the
 * operation succeeded.
 * 
 * @author Chris Molini
 * 
 * @param <T>
 *            The type of object to remove.
 *****************************************************************************/
public interface Remover<T>
{
	/*************************************************************************
	 * Removes an object.
	 * 
	 * @param object
	 *            The object to remove.
	 * 
	 * @return True if the operation succeeded, false if not.
	 *************************************************************************/
	public boolean remove(T object);
	
	/*************************************************************************
	 * Removes a series of objects.
	 * 
	 * @param objects
	 *            The objects to remove.
	 *************************************************************************/
	public void remove(T... objects);
}
