package org.jrabbit.base.data.structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.jrabbit.base.data.Factory;
import org.jrabbit.base.data.Referenced;
import org.jrabbit.base.data.structures.base.KeyedContainer;

/*****************************************************************************
 * BaseKeyedContainer provides a standard implementation of KeyedContainer. This
 * will NOT maintain order of addition - if that functionality is desirable, use
 * OrderedKeyedContainer.
 * 
 * Basically, this uses a HashMap to fulfill the methods in KeyedContainer.
 * 
 * @author Chris Molini
 * 
 * @param T
 *            The type of object to contain in the Cache. Must implement
 *            Referenced.
 *****************************************************************************/
public class BaseKeyedContainer<T extends Referenced> implements 
		KeyedContainer<T>
{
	/**
	 * The Factory used to create objects.
	 **/
	protected Factory<T> factory;
	
	/**
	 * The LinkedHashMap that organizes all objects in the Container.
	 **/
	protected HashMap<String, T> contents;
	
	/*************************************************************************
	 * Creates an OrderedBaseKeyedContainer that uses the supplied Factory to
	 * make objects on demand.
	 * 
	 * @param factory
	 * 			  The Factory used to create entries on the fly, from String 
	 *            data.
	 *************************************************************************/
	public BaseKeyedContainer(Factory<T> factory)
	{
		contents = createMap();
		setFactory(factory);
	}
	
	/*************************************************************************
	 * Obtains the HashMap used to maintain the list.
	 * 
	 * @return A HashMap that will key Objects to Strings.
	 *************************************************************************/
	protected HashMap<String, T> createMap()
	{
		return new HashMap<String, T>();
	}
	
	/*************************************************************************
	 * Creates an object from the indicated expression and attempts to add it.
	 * 
	 * @param reference
	 *            The String to identify the new object in the list.
	 * 
	 * @return True if the addition operation succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean add(String reference)
	{
		return add(create(reference));
	}

	/*************************************************************************
	 * Attempts to create and add objects corresponding to every supplied
	 * reference.
	 * 
	 * @param references
	 *            The Strings to identify the new objects in the list.
	 ***************************************************************/ @Override
	public void add(String... references)
	{
		for(String reference : references)
			add(reference);
	}

	/*************************************************************************
	 * Adds an object, and checks whether the add was successful.
	 * 
	 * @param object
	 *            The object to add.
	 * 
	 * @return Whether or not the add was successful.
	 ***************************************************************/ @Override
	public boolean add(T object)
	{
		if(contains(object.reference()))
			return false;
		contents.put(object.reference(), object);
		return true;
	}

	/*************************************************************************
	 * Attempts to add every supplied object.
	 * 
	 * @param objects
	 *            The objects to add.
	 ***************************************************************/ @Override
	public void add(T... objects)
	{
		for(T object : objects)
			add(object);
	}

	/*************************************************************************
	 * Attempts to remove the object with the indicated Reference from the list.
	 * 
	 * @param reference
	 *            The String that identifies the object in the list.
	 * 
	 * @return If the object was removed, returns a reference to it, if the
	 *         removal failed, returns null.
	 ***************************************************************/ @Override
	public T remove(String reference)
	{
		return contents.remove(reference);
	}

	/*************************************************************************
	 * Attempts to remove and return all objects in the Container that
	 * correspond to the supplied references.
	 * 
	 * @param references
	 * 			  The Strings indicating which objects to remove.
	 * 
	 * @return A Collection containing all objects removed. If no objects are
	 *         removed successfully, an empty Collection is returned.
	 ***************************************************************/ @Override
	public Collection<T> remove(String... references)
	{
		LinkedList<T> removed = new LinkedList<T>();
		for(String reference : references)
		{
			T object = remove(reference);
			if(object != null)
				removed.add(object);
		}
		return removed;
	}

	/*************************************************************************
	 * Removes an object.
	 * 
	 * @param object
	 *            The object to remove.
	 * 
	 * @return True if the operation succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean remove(T object)
	{
		T removed = remove(object.reference());
		return removed != null ? removed.equals(object) : false;
	}

	/*************************************************************************
	 * Removes a series of objects.
	 * 
	 * @param objects
	 *            The objects to remove.
	 ***************************************************************/ @Override
	public void remove(T... objects)
	{
		for(T object : objects)
			remove(object);
	}

	/*************************************************************************
	 * Checks to see if the Container contains an object with an identical
	 * reference to the one given.
	 * 
	 * @param reference
	 *            The String that identifies the object in the list.
	 * 
	 * @return If the object is in the list, returns a true, otherwise, false.
	 ***************************************************************/ @Override
	public boolean contains(String reference)
	{
		return contents.containsKey(reference);
	}

	/*************************************************************************
	 * Checks to see if the object is contained in the Container.
	 * 
	 * @param object
	 *            The object to look for.
	 * 
	 * @return True if the object is in the Container, false if not.
	 ***************************************************************/ @Override
	public boolean contains(T object)
	{
		return object != null ? contents.get(object.reference()).equals(object) 
				: false;
	}

	/*************************************************************************
	 * Attempts to access the object in the list with the given reference.
	 * 
	 * @param reference
	 *            The String that identifies the object in the list.
	 * 
	 * @return If the object is in the list, returns a reference to it.
	 *         Otherwise, returns null.
	 ***************************************************************/ @Override
	public T get(String reference)
	{
		return contents.get(reference);
	}

	/*************************************************************************
	 * Removes all objects from the container.
	 ***************************************************************/ @Override
	public void clear()
	{
		contents.clear();
	}

	/*************************************************************************
	 * Learns how many objects are held.
	 * 
	 * @return The size of the Container (number of objects stored).
	 ***************************************************************/ @Override
	public int size()
	{
		return contents.size();
	}

	/*************************************************************************
	 * Creates an object from the supplied reference. This uses the 
	 * OrderedBaseKeyedContainer's personal Factory to make the object.
	 * 
	 * @param reference
	 *            The information we use to define the object.
	 * 
	 * @return The object built from the String's data.
	 ***************************************************************/ @Override
	public T create(String reference)
	{
		return factory != null ? factory.create(reference) : null;
	}
	
	/*************************************************************************
	 * Accesses the object used to create new entries upon demand.
	 * 
	 * @return The Factory that makes objects for the OrderedBaseKeyedContainer.
	 *************************************************************************/
	public Factory<T> factory() { return factory; }
	
	/*************************************************************************
	 * Redefines the OrderedBaseKeyedContainer's Factory.
	 * 
	 * @param factory
	 * 			  The new Factory for the Container to use.
	 *************************************************************************/
	public void setFactory(Factory<T> factory)
	{
		this.factory = factory;
	}

	/*************************************************************************
	 * Accesses an iterator through the list of entries.
	 * 
	 * @return An Iterator that will access the contents of the list.
	 ***************************************************************/ @Override
	public Iterator<T> iterator()
	{
		return contents.values().iterator();
	}
}