package org.jrabbit.base.data.structures;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

/*****************************************************************************
 * A GroupTree is a storage data structure that allows for recursive
 * organization. It's "grouped" because it's a collection of objects, and it's a
 * "tree" because groups can contain other groups which can contain others, etc.
 * 
 * GroupTree is defined so that every group uses its child groups for
 * operations. For example, calling contains() searches down the entire tree. To
 * perform operations on a particular group and not affect its children, use
 * contents() to retrieve the Collection that stores a group's personal list of
 * objects. Likewise, one can access groups further down the tree using the
 * provided getGroup() methods.
 * 
 * Iterating through a GroupTree iterates through both the group contents and
 * children - i.e., it runs through the entire tree below the specified group.
 * 
 * Finally, a default GroupTree is "downwards only" - there are no methods for
 * moving back up a lit (i.e., moving from a child group to the parent).
 * 
 * @author Chris Molini
 * 
 * @param <T>
 *            The type of object to contain in the group.
 *****************************************************************************/
public class GroupTree<T> implements Iterable<T>
{
	/**
	 * The list of references contained in this level of the tree.
	 **/
	private LinkedList<T> contents;

	/**
	 * The list of subgroups under this group.
	 **/
	private LinkedHashMap<String, GroupTree<T>> children;

	/*************************************************************************
	 * Creates a default, empty group tree.
	 *************************************************************************/
	public GroupTree()
	{
		contents = new LinkedList<T>();
		children = new LinkedHashMap<String, GroupTree<T>>();
	}

	/*************************************************************************
	 * Get access to the objects the group contains.
	 * 
	 * @return The LinkedList of objects in a group.
	 *************************************************************************/
	public LinkedList<T> contents()
	{
		return contents;
	}

	/*************************************************************************
	 * Get access to the list of child groups.
	 * 
	 * @return The LinkedHashMap of lower-level groups.
	 *************************************************************************/
	public LinkedHashMap<String, GroupTree<T>> children()
	{
		return children;
	}

	/*************************************************************************
	 * Checks whether this group contains an object. Recursively searches all
	 * children.
	 * 
	 * @param object
	 *            The object to search for.
	 * 
	 * @return If the object was found.
	 *************************************************************************/
	public boolean contains(T object)
	{
		if (contents.contains(object))
			return true;
		for (GroupTree<T> child : children.values())
			if (child.contains(object))
				return true;
		return false;
	}

	/*************************************************************************
	 * Combines another GroupTree into this one. The contents and the child
	 * groups are mixed.
	 * 
	 * If the supplied group contains a mapping that matches a mapping in this
	 * group, then the two groups that share the key are mixed.
	 * 
	 * The group that is being added is unchanged.
	 * 
	 * @param group
	 *            The GroupTree to add.
	 *************************************************************************/
	public void add(GroupTree<T> group)
	{
		contents.addAll(group.contents);

		for (Entry<String, GroupTree<T>> entry : group.children.entrySet())
		{
			GroupTree<T> child = children.get(entry.getKey());
			if (child == null)
				children.put(entry.getKey(), entry.getValue());
			else
				child.add(entry.getValue());
		}
	}

	/*************************************************************************
	 * Attempts to remove an entire GroupTree from the list. All shared contents
	 * are removed.
	 * 
	 * If the Trees share children, then instead of removing those child groups,
	 * all shared contents between those two groups are removed, and then
	 * children are checked. In this way the method recursively subtracts group
	 * contents while leaving the tree of groups intact.
	 * 
	 * @param group
	 *            The GroupTree to remove.
	 *************************************************************************/
	public void remove(GroupTree<T> group)
	{
		contents.removeAll(group.contents);

		for (Entry<String, GroupTree<T>> entry : group.children.entrySet())
		{
			GroupTree<T> child = children.get(entry.getKey());
			if (child != null)
				child.remove(entry.getValue());
		}
	}

	/*************************************************************************
	 * Creates a new group and adds it as a child with the supplied String as an
	 * access key.
	 * 
	 * The add fails if a group is already keyed to the String.
	 * 
	 * @param reference
	 *            The key that will retrieve the new group.
	 * 
	 * @return Whether or not the add was successful.
	 *************************************************************************/
	public boolean addChild(String reference)
	{
		if (children.containsKey(reference))
			return false;
		children.put(reference, new GroupTree<T>());
		return true;
	}

	/*************************************************************************
	 * Adds an existing group as a child with the supplied String as an access
	 * key.
	 * 
	 * If a group already exists under the supplied key, then the indicated
	 * group is combined with the existing entry.
	 * 
	 * @param reference
	 *            The key that will return the group.
	 * @param group
	 *            The group to add as child group.
	 * 
	 * @return True if no group existed in that location, and the add simply
	 *         added a new GroupTree; false if there was a collision and the two
	 *         groups had to be mixed.
	 *************************************************************************/
	public boolean addChild(String reference, GroupTree<T> group)
	{
		GroupTree<T> current = children.get(reference);
		if (current == null)
		{
			children.put(reference, group);
			return true;
		}
		else
		{
			current.add(group);
			return false;
		}
	}

	/*************************************************************************
	 * Accesses the appropriate child group.
	 * 
	 * @param reference
	 *            The key used to access the target group.
	 * 
	 * @return The group that is keyed to the supplied String. If no group has
	 *         been keyed, then returns null.
	 *************************************************************************/
	public GroupTree<T> getChild(String reference)
	{
		return children.get(reference);
	}

	/*************************************************************************
	 * If a group is keyed to the supplied reference, it is removed.
	 * 
	 * @param reference
	 *            The key used to find the target group.
	 * 
	 * @return The group that was removed. If no object was removed, then
	 *         returns null.
	 *************************************************************************/
	public GroupTree<T> removeChild(String reference)
	{
		return children.remove(reference);
	}

	/*************************************************************************
	 * Recursively checks the tree of groups to find the supplied object, and
	 * returns the group it is in when found.
	 * 
	 * @param object
	 *            The object to search for.
	 * 
	 * @return If the group is found, then returns the group that contained it.
	 *         Otherwise, returns null.
	 *************************************************************************/
	public GroupTree<T> containing(T object)
	{
		if (contents.contains(object))
			return this;
		for (GroupTree<T> group : children.values())
		{
			GroupTree<T> result = group.containing(object);
			if (result != null)
				return result;
		}
		return null;
	}

	/*************************************************************************
	 * Finds the a child group that has been keyed to the indicated String.
	 * 
	 * NOTE: This finds the *first* group under an identical reference. It is
	 * perfectly possible for multiple groups to be keyed to similar Strings in
	 * different sections of the tree.
	 * 
	 * @param key
	 *            The reference that identifies the group to find.
	 * 
	 * @return A GroupTree keyed to the indicated String. If no group in the
	 *         tree is mapped to the String, then returns null.
	 *************************************************************************/
	public GroupTree<T> findChild(String key)
	{
		GroupTree<T> attempt = children.get(key);

		if (attempt == null)
			if (!children.isEmpty())
				for (GroupTree<T> child : children.values())
				{
					attempt = child.findChild(key);
					if (attempt != null)
						return attempt;
				}
		else
			return attempt;

		return null;
	}

	/*************************************************************************
	 * Clears the group and all children.
	 *************************************************************************/
	public void clear()
	{
		contents.clear();
		for (GroupTree<T> group : children.values())
			group.clear();
		children.clear();
	}

	/*************************************************************************
	 * Recursively finds the number of GroupTrees in the tree that includes and
	 * extends from this GroupTree. Does not include number of elements, just
	 * the number of groups.
	 * 
	 * @return How many GroupTrees make up the tree.
	 *************************************************************************/
	public int numGroups()
	{
		int numGroups = 1;
		for (GroupTree<T> child : children.values())
			numGroups += child.numGroups();
		return numGroups;
	}

	/*************************************************************************
	 * Returns the size of the tree that includes and extends from this
	 * GroupTree and all child groups. The size does not include the Groups
	 * themselves in the count, but the number of their elements.
	 * 
	 * @return How many elements this GroupTree contains.
	 *************************************************************************/
	public int size()
	{
		int size = contents.size();
		for (GroupTree<T> group : children.values())
			size += group.size();
		return size;
	}

	/*************************************************************************
	 * Checks to see if the tree holds any elements.
	 * 
	 * @return If this GroupTree has any elements in any of its groups.
	 *************************************************************************/
	public boolean isEmpty()
	{
		if (!contents.isEmpty())
			return false;
		for (GroupTree<T> group : children.values())
			if (!group.isEmpty())
				return false;
		return true;
	}

	/*************************************************************************
	 * Converts the entire tree into an array.
	 * 
	 * @param array
	 *            The base array that will be resized to hold all contents.
	 * 
	 * @return The tree in array form.
	 *************************************************************************/
	public T[] toArray(T[] array)
	{
		array = Arrays.copyOf(array, size());
		int i = 0;
		Iterator<T> iter = iterator();
		while (iter.hasNext() && i < array.length)
		{
			array[i] = iter.next();
			i++;
		}
		return array;
	}

	/*************************************************************************
	 * Returns an iterator that will go through the tree recursively.
	 * 
	 * NOTE: It is not safe to change the contents of a group while iterating
	 * through it.
	 * 
	 * @return An iterator through the GroupTree and all children.
	 ***************************************************************/ @Override
	public Iterator<T> iterator()
	{
		return new GroupTreeIterator(this);
	}

	/************************************************************************
	 * Defines an iterator that is designed to recursively move through a
	 * GroupTree.
	 * 
	 * @author Chris Molini
	 ************************************************************************/
	protected class GroupTreeIterator implements Iterator<T>
	{
		/**
		 * The current iterator.
		 **/
		private Iterator<T> activeIterator;

		/**
		 * Iterates through the list of children groups.
		 **/
		private Iterator<GroupTree<T>> subgroupIterator;

		/********************************************************************
		 * Creates an iterator through the specified group.
		 * 
		 * @param frames
		 *            The contents to start iterating through.
		 * @param subgroups
		 *            The list of subgroups to iterate through.
		 ********************************************************************/
		public GroupTreeIterator(GroupTree<T> group)
		{
			activeIterator = group.contents().iterator();
			subgroupIterator = group.children().values().iterator();
		}

		/********************************************************************
		 * Advances the iterators forward until either A) the active iterator
		 * has a next object, or B) there are no more items to iterate through.
		 ********************************************************************/
		private void resolveIterator()
		{
			while (!activeIterator.hasNext() && subgroupIterator.hasNext())
				activeIterator = subgroupIterator.next().iterator();
		}

		/********************************************************************
		 * Determines whether or not continued iteration will return valid
		 * objects.
		 * 
		 * @return If the iterator has another object to iterate through.
		 **********************************************************/ @Override
		public boolean hasNext()
		{
			resolveIterator();
			return activeIterator.hasNext();
		}

		/********************************************************************
		 * Advances iteration.
		 * 
		 * @return The next object in the iterator, or null if none are left.
		 **********************************************************/ @Override
		public T next()
		{
			resolveIterator();
			return activeIterator.next();
		}

		/********************************************************************
		 * Delegates the removal operation to the ArrayList the active iterator
		 * is moving through.
		 **********************************************************/ @Override
		public void remove()
		{
			activeIterator.remove();
		}
	}
}