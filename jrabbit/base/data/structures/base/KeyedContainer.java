package org.jrabbit.base.data.structures.base;

import java.util.Collection;

import org.jrabbit.base.data.Factory;
import org.jrabbit.base.data.Referenced;

/*****************************************************************************
 * KeyedContainer adds functionality to Container to support "keying" Referenced
 * objects with their references - i.e., it's possible to access, add, or remove
 * objects in the list by their associated String.
 * 
 * A side effect of this is that no two objects in the KeyedContainer may have
 * identical Strings - no reference collisions are supported or allowed.
 * 
 * Additionally, a KeyedContainer is expected to be able to create an object
 * from a String; this allows objects to be added simply by supplying the String
 * they will associate with. The desired object is then produced and added.
 * 
 * @author Chris Molini
 * 
 * @param T
 *            The type of object to contain in the Cache. Must implement
 *            Referenced.
 *****************************************************************************/
public interface KeyedContainer<T extends Referenced> extends Container<T>,
		Factory<T>
{
	/*************************************************************************
	 * Creates an object from the indicated expression and attempts to add it.
	 * 
	 * @param reference
	 *            The String to identify the new object in the list.
	 * 
	 * @return True if the addition operation succeeded, false if not.
	 *************************************************************************/
	public boolean add(String reference);

	/*************************************************************************
	 * Attempts to create and add objects corresponding to every supplied
	 * reference.
	 * 
	 * @param references
	 *            The Strings to identify the new objects in the list.
	 *************************************************************************/
	public void add(String... references);

	/*************************************************************************
	 * Attempts to remove the object with the indicated Reference from the list.
	 * 
	 * @param reference
	 *            The String that identifies the object in the list.
	 * 
	 * @return If the object was removed, returns a reference to it, if the
	 *         removal failed, returns null.
	 *************************************************************************/
	public T remove(String reference);

	/*************************************************************************
	 * Attempts to remove and return all objects in the Container that
	 * correspond to the supplied references.
	 * 
	 * @param references
	 * 			  The Strings indicating which objects to remove.
	 * 
	 * @return A Collection containing all objects removed. If no objects are
	 *         removed successfully, an empty Collection is returned.
	 *************************************************************************/
	public Collection<T> remove(String... references);

	/*************************************************************************
	 * Attempts to access the object in the list with the given reference.
	 * 
	 * @param reference
	 *            The String that identifies the object in the list.
	 * 
	 * @return If the object is in the list, returns a reference to it.
	 *         Otherwise, returns null.
	 *************************************************************************/
	public T get(String reference);

	/*************************************************************************
	 * Checks to see if the Container contains an object with an identical
	 * reference to the one given.
	 * 
	 * @param reference
	 *            The String that identifies the object in the list.
	 * 
	 * @return If the object is in the list, returns a true, otherwise, false.
	 *************************************************************************/
	public boolean contains(String reference);
}