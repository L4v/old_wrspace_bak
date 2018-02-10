package org.jrabbit.base.data.structures.base;

/*****************************************************************************
 * An Acceptor is an object that is able to "add" objects to itself. Obviously,
 * data structures are most likely to utilize this.
 * 
 * @author Chris Molini
 * 
 * @param <T>
 *            The type of object to add.
 *****************************************************************************/
public interface Accepter<T>
{
	/*************************************************************************
	 * Adds an object, and checks whether the add was successful.
	 * 
	 * @param object
	 *            The object to add.
	 * 
	 * @return Whether or not the add was successful.
	 *************************************************************************/
	public boolean add(T object);
	
	/*************************************************************************
	 * Attempts to add every supplied object.
	 * 
	 * @param objects
	 *            The objects to add.
	 *************************************************************************/
	public void add(T... objects);
}
