package org.jrabbit.base.data.structures.base;


/*****************************************************************************
 * An extension of Accepter and Remover, describing an object that can keep
 * references to objects (via add() and remove(), and then check to see if they
 * are currently held, determine how many objects are contained, and remove all
 * objects at once.
 * 
 * @author Chris Molini
 * @param <T>
 *            The type of object to contain.
 *****************************************************************************/
public interface Container<T> extends Accepter<T>, Remover<T>, Iterable<T>
{
	/*************************************************************************
	 * Checks to see if the object is contained in the Container.
	 * 
	 * @param object
	 *            The object to look for.
	 * 
	 * @return True if the object is in the Container, false if not.
	 *************************************************************************/
	public boolean contains(T object);

	/*************************************************************************
	 * Learns how many objects are held.
	 * 
	 * @return The size of the Container (number of objects stored).
	 *************************************************************************/
	public int size();
	
	/*************************************************************************
	 * Removes all objects from the container.
	 *************************************************************************/
	public void clear();
}
