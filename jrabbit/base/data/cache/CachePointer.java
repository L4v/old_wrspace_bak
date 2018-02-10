package org.jrabbit.base.data.cache;

import org.jrabbit.base.data.Destroyable;
import org.jrabbit.base.data.Referenced;
import org.jrabbit.base.data.Retriever;

/*****************************************************************************
 * A CachePointer is an object that retrieves an object from one of jRabbit's
 * Caches. A Cache returns the object whose reference matches that of the
 * CachePointer.
 * 
 * To provide additional speed above the normal Cache retrieval rate (which does
 * take some time, as it uses Strings for Hash keys), a CachePointer
 * automatically keeps a reference to the object it retrieves from the Cache.
 * This speeds up retrieval time significantly, but it is possible for this
 * reference to become "dirty" (e.g., when the Cache is told to remove and
 * destroy the object). If this happens, you can use refresh() to update the
 * reference.
 * 
 * NOTE: CachePointer is an abstract class. The default implementation shown
 * here does not define retrieve(). Extensions of this class need to indicate
 * how they interface with Caches to retrieve data.
 * 
 * @author Chris Molini
 * 
 * @param <T>
 *            The type of object this CachePointer is intended to retrieve.
 *****************************************************************************/
public abstract class CachePointer<T extends Destroyable & Referenced>
		implements Retriever<T>, Referenced
{
	/**
	 * The stored object reference.
	 **/
	protected T object;

	/**
	 * The string identifier that tells the cache which element we wish to use.
	 */
	protected String reference;

	/*************************************************************************
	 * Creates a CachePointer that will retrieve the object specified by the
	 * parameter.
	 * 
	 * @param reference
	 *            The identifier that will tell the Cache which object to
	 *            retrieve.
	 *************************************************************************/
	public CachePointer(String reference)
	{
		setReference(reference);
	}

	/*************************************************************************
	 * Returns the identifier the CachePointer is looking for.
	 * 
	 * @return The reference that matches that of the desired object in the
	 *         Cache.
	 ***************************************************************/ @Override
	public String reference()
	{
		return reference;
	}

	/*************************************************************************
	 * Redefines what the CachePointer needs to search for and refreshes the
	 * stored object.
	 * 
	 * @param reference
	 *            The new reference to search for.
	 *************************************************************************/
	public void setReference(String reference)
	{
		this.reference = reference;
		refresh();
	}

	/*************************************************************************
	 * Returns the object the CachePointer has retrieved.
	 * 
	 * NOTE: Theoretically, this can return a reference to an object that has
	 * been destroyed in the cache, since using this method doesn't actually
	 * check.
	 * 
	 * @return The stored object the CachePointer retrieves.
	 *************************************************************************/
	public T object()
	{
		return object;
	}

	/*************************************************************************
	 * Updates the stored object reference to reflect the contents of the cache.
	 *************************************************************************/
	public void refresh()
	{
		object = retrieve();
	}

	/*************************************************************************
	 * Checks to see if the stored object reference is non-null.
	 * 
	 * @return True if the reference points to an object in memory, false
	 *         otherwise.
	 *************************************************************************/
	public boolean valid()
	{
		return object != null;
	}
}