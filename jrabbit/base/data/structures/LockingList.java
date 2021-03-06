package org.jrabbit.base.data.structures;

import java.util.HashMap;
import java.util.Iterator;

import org.jrabbit.base.data.structures.base.Container;

/*****************************************************************************
 * This class is supposed to handle large, dynamic amounts of objects and be
 * able to iterate through them very quickly without being disturbed by changes
 * to its structure. Duplicate entries are not supported. Random access is also
 * not supported.
 * 
 * At its heart, LockingList is a LinkedList. However, the weakness in Linked
 * Lists is that removal operations are inefficient when compared to add methods
 * - it has to iterate through the entire list, checking each node. To attempt
 * to combat this, when objects are added to the list, a HashMap keys the object
 * reference to the node that contains it. Thereafter, when remove() is called,
 * it finds the node from the HashMap and does a quick and easy cull.
 * 
 * It's a little extreme to make a custom data structure for a single purpose,
 * but all the common "fast" data structures I know of have some flaws in a
 * gaming environment (especially since I wanted to remember order of addition).
 * ArrayList is fast, but it takes time to resize. LinkedLists are fast and
 * highly dynamic when adding, but removal requires a search through the list.
 * LinkedHashMaps have some O(1) operations, but have needless overhead. Adding 
 * to the mix is the fact that I needed something that could be modified during
 * iteration without causing errors. Ultimately, I decided to marry a HashMap
 * and a LinkedList. The result is a very stripped down data structure that,
 * nevertheless, performs very well. When no changes are occurring, it performs
 * at LinkedList speed (which is quite fast), and even when the list is being
 * rapidly changed, its operations tend towards O(1) complexity (and are fairly 
 * fast at that).
 * 
 * @author Chris Molini
 * 
 * @param <T>
 *            The type of object to hold in the list.
 *****************************************************************************/
public class LockingList<T> implements Container<T>
{
	/**
	 * The currently active list of items. Because iteration runs through the
	 * actual list of objects, we cannot safely allow this list to change while
	 * we are going through it. Therefore, we need to cache changes and apply
	 * them after the list is safe again.
	 **/
	protected UList main;

	/**
	 * A list to cache addition operations. When the list is "locked," all add()
	 * operations are applied to this.
	 **/
	protected UList addCache;

	/**
	 * Likewise, this caches removal operations.
	 * 
	 * NOTE: When we are unlocked, we want to simply remove objects from the
	 * main list, but when we lock the list we need to add them to a storage
	 * structure. So, we modify this list on instantiation to provide that
	 * functionality.
	 **/
	protected UList removalCache;

	/**
	 * This reference is meant to switch between main and toAdd. When locked,
	 * the list automatically (without unnecessary checks) delegates addition
	 * operations.
	 **/
	protected UList adding;

	/**
	 * Similarly, this reference handles directing removal operations.
	 **/
	protected UList removing;

	/**
	 * When the list is locked, if a clear() is demanded, we cannot destroy the
	 * list immediately - we need to remember it until we unlock the list (and
	 * immediately clear the list on release).
	 **/
	protected boolean clear;

	/*************************************************************************
	 * Creates a default, unlocked, empty list.
	 *************************************************************************/
	public LockingList()
	{
		main = new UList();
		addCache = new UList();

		// We need to define toRemove to have its remove operations cache the
		// objects instead of the default operations.
		removalCache = new UList()
		{
			protected boolean remove(T object)
			{
				super.add(object);
				return false;
			}

			protected void remove(UList list)
			{
				super.add(list);
			}
		};

		adding = main;
		removing = main;
	}

	/*************************************************************************
	 * Adds an object or caches the operation.
	 * 
	 * @param object
	 *            The object to add.
	 *            
	 * @return True if the add succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean add(T object)
	{
		return adding.add(object);
	}
	
	/*************************************************************************
	 * Attempts to add every supplied object.
	 * 
	 * @param objects
	 * 			  The objects to add.
	 ***************************************************************/ @Override
	public void add(T... objects)
	{
		for(T object : objects)
			add(object);
	}

	/*************************************************************************
	 * Handles adding an entire list of objects of the same generic type.
	 * 
	 * if the other list is locked, only the main list is applied. Pending
	 * additions and removals do not factor in.
	 * 
	 * @param list
	 *            the list of objects to add.
	 *************************************************************************/
	public void add(LockingList<T> list)
	{
		adding.add(list.main);
	}

	/*************************************************************************
	 * Removes an object or caches the operation.
	 * 
	 * @param object
	 *            The object to remove.
	 * 
	 * @return If locked, automatically returns false. If unlocked, returns
	 *         whether or not the removal was successful.
	 ***************************************************************/ @Override
	public boolean remove(T object)
	{
		return removing.remove(object);
	}
		
	/*************************************************************************
	 * Attempts to remove the supplied list of objects.
	 * 
	 * @param objects
	 * 			  The objects to remove.
	 ***************************************************************/ @Override
	public void remove(T... objects)
	{
		for(T object : objects)
			remove(object);
	}

	/*************************************************************************
	 * Handles removing an entire list of objects of the same type.
	 * 
	 * If the other list is locked, only the active list is applied. Pending
	 * additions and removals do not factor in.
	 * 
	 * @param list
	 *            The list of objects we wish to remove.
	 *************************************************************************/
	public void remove(LockingList<T> list)
	{
		removing.remove(list.main);
	}

	/*************************************************************************
	 * Checks if an object is on the list.
	 * 
	 * @param object
	 *            The object to check for.
	 * 
	 * @return If the object is on the list.
	 ***************************************************************/ @Override
	public boolean contains(T object)
	{
		return main.contains(object) || addCache.contains(object);
	}

	/*************************************************************************
	 * Clears the list if unlocked. If locked, it clears the current state of
	 * toAdd and toRemove, and remembers that it should clear the main list on
	 * release().
	 *************************************************************************/
	public void clear()
	{
		if (locked())
		{
			clear = true;
			addCache.clear();
			removalCache.clear();
		}
		else
			main.clear();
	}

	/*************************************************************************
	 * Returns the size of the main list. If the list is locked, there is no
	 * concession to pending adds and removals.
	 * 
	 * @return How many elements are in the active list.
	 ***************************************************************/ @Override
	public int size()
	{
		return main.size;
	}

	/*************************************************************************
	 * Returns the predicted size of the list. If the list is not locked, this
	 * returns the same value of size(), but if it is, it returns the estimated
	 * final size after pending operations have been resolved.
	 * 
	 * NOTE: This list can be inaccurate. It assumes that all pending add and
	 * remove operations will be successful, which may not be the case
	 * (depending on whether the objects involved in the operations are on the
	 * list or not).
	 * 
	 * @return The estimate for list size when it is unlocked.
	 *************************************************************************/
	public int predictedSize()
	{
		return main.size + addCache.size - removalCache.size;
	}

	/*************************************************************************
	 * Returns whether or not the active list is empty. Possibly inaccurate if
	 * the list is locked.
	 * 
	 * @return If the active list has elements.
	 *************************************************************************/
	public boolean isEmpty()
	{
		return main.size > 0;
	}

	/*************************************************************************
	 * Redirects removal and addition operations, causing the active list to
	 * become unchangeable through normal methods.
	 *************************************************************************/
	public void lock()
	{
		adding = addCache;
		removing = removalCache;
	}

	/*************************************************************************
	 * If locked, redirects removal and addition to the main list, and causes
	 * any cached operations to be applied to the main list.
	 *************************************************************************/
	public void unlock()
	{
		if (locked())
		{
			adding = main;
			removing = main;

			if (clear)
			{
				clear();
				clear = false;
			}

			main.add(addCache);
			addCache.clear();

			main.remove(removalCache);
			removalCache.clear();
		}
	}

	/*************************************************************************
	 * Checks to see if the list is caching changes.
	 * 
	 * @return Whether or not operations apply to the main list.
	 **************************************************************************/
	public boolean locked()
	{
		return adding != main;
	}

	/*************************************************************************
	 * Allows iteration through the LockingList in the usual manner. Auto-locks
	 * the list.
	 * 
	 * @return The iterator through the main list.
	 ***************************************************************/ @Override
	public Iterator<T> iterator()
	{
		lock();
		return main.iterator();
	}

	/* ********************************************************************* *
	 * ************************** Internal Classes ************************* *
	 * ********************************************************************* */

	/*************************************************************************
	 * Provides a singly-linked list implementation to dynamically store a large
	 * list of objects for quick iteration.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class UList implements Iterable<T>
	{
		/**
		 * How many elements the list has.
		 **/
		private int size;

		/**
		 * The first node in the list.
		 **/
		private UListNode first;

		/**
		 * The last node in the list.
		 **/
		private UListNode last;

		/**
		 * This keys object references with their positions on the list, making
		 * removal operations much faster. Without this, we would need to
		 * iterate through the entire list to search for each removal.
		 **/
		private HashMap<T, UListNode> removalMap;

		/*********************************************************************
		 * Creates an empty list.
		 *********************************************************************/
		protected UList()
		{
			removalMap = new HashMap<T, UListNode>();
			clear();
		}

		/*********************************************************************
		 * Returns whether or not the specified object is on the list.
		 * 
		 * @param obj
		 *            The object to check for.
		 * 
		 * @return If the object is on the list.
		 *********************************************************************/
		protected boolean contains(T obj)
		{
			return removalMap.containsKey(obj);
		}

		/*********************************************************************
		 * Clears the list.
		 *********************************************************************/
		protected void clear()
		{
			first = last = null;
			removalMap.clear();
			size = 0;
		}

		/*********************************************************************
		 * Adds an object to the end of the list if it is not already on it.
		 * 
		 * @param object
		 *            The object to add.
		 * @return
		 *********************************************************************/
		protected boolean add(T object)
		{
			if (size == 0)
			{
				first = last = new UListNode(object);
				removalMap.put(object, first);
				size++;
				return true;
			} else if (!removalMap.containsKey(object))
			{
				last = last.setNext(object);
				removalMap.put(object, last);
				size++;
				return true;
			}
			return false;
		}

		/*********************************************************************
		 * Adds another list to this one.
		 * 
		 * Instead of appending the entire list in one fell swoop, we iterate
		 * through the list and call add() for every object on it. This helps us
		 * preserve entry uniqueness.
		 * 
		 * @param uL
		 *            the list to add.
		 *********************************************************************/
		protected void add(UList uL)
		{
			UListNode toAdd = uL.first;
			while (toAdd != null)
			{
				add(toAdd.obj);
				toAdd = toAdd.next;
			}
		}

		/*********************************************************************
		 * Attempts to remove an object from the list.
		 * 
		 * Ordinarily, removal from a LinkedList requires searching through the
		 * entire thing, removing an object once and if it is found. To try to
		 * speed up the process, we use a HashMap to store the locations of
		 * objects on the list, and use that for quickly finding which node to
		 * remove.
		 * 
		 * @param object
		 *            The object to remove.
		 * 
		 * @return True if the object was found and removed, false otherwise.
		 *********************************************************************/
		protected boolean remove(T object)
		{
			UListNode toRemove = removalMap.remove(object);

			// If toRemove is null, the object was not on the list.
			if (toRemove != null)
			{
				if (toRemove == first)
				{
					first = first.next;
					if (first != null)
						first.last = null;
				}
				else if (toRemove == last)
				{
					last = last.last;
					last.next = null;
				}
				else
				{
					toRemove.last.next = toRemove.next;
					toRemove.next.last = toRemove.last;
				}
				size--;
				return true;
			}
			return false;
		}

		/*********************************************************************
		 * Attempts to remove from this list every element in another.
		 * 
		 * @param list
		 *            The list of objects to attempt to remove.
		 *********************************************************************/
		protected void remove(UList list)
		{
			UListNode toRemove = list.first;
			while (toRemove != null)
			{
				remove(toRemove.obj);
				toRemove = toRemove.next;
			}
		}

		/*********************************************************************
		 * Returns a String representation of the list.
		 * 
		 * @return A list of every entry in the list. Elements are divided by
		 *         line breaks.
		 ***********************************************************/ @Override
		public String toString()
		{
			String results = "List: \n";
			if (size > 0)
				for (T obj : this)
					results += "\n" + obj;
			else
				results += "(empty)";
			return results;
		}

		/*********************************************************************
		 * Returns the iterator. It's inadvisable to change the list contents
		 * while an iterator is working.
		 * 
		 * @return The iterator for perusing the list.
		 ***********************************************************/ @Override
		public Iterator<T> iterator()
		{
			return new UListIterator(first);
		}

		/*********************************************************************
		 * A node in our doubly-linked list.
		 * 
		 * @author Chris Molini
		 *********************************************************************/
		private class UListNode
		{
			/**
			 * The data the node stores.
			 **/
			private T obj;

			/**
			 * The reference to the next node in the list.
			 **/
			private UListNode next;

			/**
			 * The reference to the last node in the list.
			 **/
			private UListNode last;

			/*****************************************************************
			 * Creates an empty node containing the specified object.
			 *****************************************************************/
			private UListNode(T object)
			{
				obj = object;
			}

			/*****************************************************************
			 * Used to efficiently append items to the end of the list.
			 * 
			 * @param object
			 *            The object to add.
			 * 
			 * @return The reference to the next node in the list. Simplifies
			 *         appending.
			 *****************************************************************/
			private UListNode setNext(T object)
			{
				next = new UListNode(object);
				next.last = this;
				return next;
			}

			/*****************************************************************
			 * Returns a string representation of the node.
			 * 
			 * @return A string that describes the object the node contains and
			 *         whether or not the list has references to a node before
			 *         and after it.
			 *******************************************************/ @Override
			public String toString()
			{
				return "Object: " + obj + "\nLast: " + (last != null)
						+ "\nNext: " + (next != null);
			}
		}

		/*********************************************************************
		 * Defines an iterator through our list.
		 * 
		 * To get the best possible speed, there are no checks to throw
		 * ConcurrentModificationException, though editing the list while
		 * iterating can potentially throw a bug. It will manifest in a
		 * NullPointerException and immediately crash the program.
		 * 
		 * This would ordinarily be a severe problem, but the entire list as a
		 * whole is designed to not cause those problems during execution, and
		 * so operations should remain steady if the developer uses the
		 * available methods.
		 * 
		 * @author Chris Molini
		 *********************************************************************/
		private class UListIterator implements Iterator<T>
		{
			/**
			 * The node used to iterate.
			 **/
			private UListNode node;

			/*****************************************************************
			 * Creates an empty iterator. Whenever a UList calls iterator(), it
			 * automatically sets the node to reference the beginning of the
			 * list.
			 *****************************************************************/
			private UListIterator(UListNode n)
			{
				node = new UListNode(null);
				node.next = n;
			}

			/*****************************************************************
			 * Returns whether or not there are more elements in the iterator.
			 * 
			 * @return If the next element will be null.
			 *******************************************************/ @Override
			public boolean hasNext()
			{
				return node.next != null;
			}

			/*****************************************************************
			 * Returns the next object and advances iteration.
			 * 
			 * @return The next object in the iteration.
			 *******************************************************/ @Override
			public T next()
			{
				node = node.next;
				return node.obj;
			}

			/*****************************************************************
			 * The iterator does not support modifying the list.
			 *******************************************************/ @Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		}
	}
}