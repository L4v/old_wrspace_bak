package org.jrabbit.base.data.cache;

import java.util.LinkedList;

import org.jrabbit.base.data.Destroyable;
import org.jrabbit.base.data.Referenced;
import org.jrabbit.base.data.Factory;
import org.jrabbit.base.data.structures.BaseKeyedContainer;

/*****************************************************************************
 * A Cache is a data structure that holds objects mapped to keys - i.e., it's a
 * HashMap! However, it has some additional requirements and functionality.
 * 
 * Objects in a Cache are Referenced - the key that they are mapped to in the
 * cache must always be equal to their Reference. This is so ingrained in the
 * cache that it's expected that you can create a cache entry from a reference
 * (for example, an Image can be loaded by supplying its file location).
 * 
 * Additionally, Cache entries are required to be Destroyable. The main point of
 * the Cache system is to unify the means to manage OpenGL resources - images,
 * sounds, etc. - and there are some convenience methods in place that make it
 * easier to clean up resources.
 * 
 * NOTE: There are two ways of populating Caches - either manually add all of 
 * the objects required, and retrieve them from the Cache for use, or to have 
 * the Cache dynamically create new objects when asked for them.
 * 
 * Neither is universally "recommended," and here's why:
 * 
 * When possible, it makes sense to manually add every needed object before use 
 * is required - say, during a loading screen - because that means that the game
 * does not have to pause to create the object when the object is needed. 
 * Additionally, certain types of objects (such as Fonts) require various 
 * information, and it is difficult to fit all of that info into a single 
 * String. In this case, it's almost mandatory that you insert them manually.
 * 
 * On the flip side, it's far easier to have a Cache simply obtain an object 
 * when you need it. Options for data creation are a bit limited in comparison -
 * you can only define objects with a single String - but it's very convenient 
 * to let the Cache take care of everything. Additionally, this is potentially 
 * more efficient - it's essentially deferred loading, which is a useful tactic 
 * in efficiency situations.
 * 
 * @author Chris Molini
 * 
 * @param <T>
 *            The type of object the cache holds.
 *****************************************************************************/
public class Cache<T extends Referenced & Destroyable> extends 
	BaseKeyedContainer<T> implements Destroyable
{
	/*************************************************************************
	 * Creates an empty Cache without a defined Factory to create its entries.
	 *************************************************************************/
	public Cache()
	{
		super(null);
	}
	
	/*************************************************************************
	 * Creates an empty Cache that utilizes the supplied Factory.
	 * 
	 * @param factory
	 * 			  The Factory to use in object creation.
	 *************************************************************************/
	public Cache(Factory<T> factory)
	{
		super(factory);
	}
	
	/*************************************************************************
	 * Accesses the content pointed to by the supplied CachePointer.
	 * 
	 * @param pointer
	 * 			  The object that indicates what content it wants to retrieve
	 * 			  from the Cache.
	 * 
	 * @return The data accessed by the CachePointer's reference.
	 *************************************************************************/
	public T get(CachePointer<T> pointer)
	{
		return get(pointer.reference());
	}
	
	/*************************************************************************
	 * Attempts to access the object in the Cache with the given reference.
	 * 
	 * If no entry is in the Container under the supplied reference, the
	 * Cache attempts to create one. 
	 * 
	 * @param reference
	 *            The String that identifies the object in the list.
	 * 
	 * @return The object in the list under the indicated reference.
	 ***************************************************************/ @Override
	public T get(String reference)
	{
		T result = contents.get(reference);
		if(result == null)
		{
			result = create(reference);
			if(result != null)
				contents.put(reference, result);
		}
		return result;
	}

	/*************************************************************************
	 * Attempts to remove an object, and then calls destroy() on it if the
	 * removal was successful. Since by definition, the object should be after
	 * this call, this method simply returns a true or false instead of an
	 * object reference.
	 * 
	 * @param reference
	 *            The key to find the object.
	 * 
	 * @return True if the removal and destruction succeeded, false otherwise.
	 *************************************************************************/
	public boolean removeAndDestroy(String reference)
	{
		T obj = remove(reference);
		if (obj != null)
		{
			obj.destroy();
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * The Cache will attempt to adjust its contents to match the supplied list
	 * of references. Any referenced object in the cache that is not referenced
	 * on the list is removed and destroyed. Any reference that is on the list
	 * but not in the Cache is added (via add()). If the Cache already contains
	 * a required reference, it stays on.
	 * 
	 * An example of where this is useful is level loading. Say you have one set
	 * of images for one level, but need a somewhat different set for the next
	 * (you need to keep some images for both). This method provides a
	 * general-purpose case for you to make minimal changes to the Cache.
	 * 
	 * @param references
	 *            The desired list of references.
	 * 
	 * @return The number of changes to the Cache. Note that these may be a
	 *         remove or an add - it isn't specified. If this returns 0, then
	 *         the list did not change.
	 *************************************************************************/
	public int setTo(String[] references)
	{
		LinkedList<String> toRemove = new LinkedList<String>();
		LinkedList<String> toAdd = new LinkedList<String>();

		// Finds the references to remove from the Cache.
		for (String reference : contents.keySet())
		{
			boolean retain = false;
			for (int i = 0; i < references.length && !retain; i++)
				if (reference.equals(references[i]))
					retain = true;
			if (!retain)
				toRemove.add(reference);
		}

		// Finds the references that need to be added.
		for (int i = 0; i < references.length; i++)
			if (!contents.containsKey(references[i]))
				toAdd.add(references[i]);

		// Adjusts the Cache.
		for (String reference : toRemove)
			remove(reference);
		for (String reference : toAdd)
			add(reference);

		return toRemove.size() + toAdd.size();
	}

	/*************************************************************************
	 * Destroys every object and clears everything. Nothing remains. However,
	 * the Cache itself can still be used - it's simply empty.
	 * 
	 * NOTE: This method should be used instead of clear().
	 ***************************************************************/ @Override
	public void destroy()
	{
		for (T object : this)
			object.destroy();
		clear();
	}
}