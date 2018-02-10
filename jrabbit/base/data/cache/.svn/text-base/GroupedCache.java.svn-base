package org.jrabbit.base.data.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.jrabbit.base.data.Destroyable;
import org.jrabbit.base.data.Factory;
import org.jrabbit.base.data.Referenced;
import org.jrabbit.base.data.structures.GroupTree;

/*****************************************************************************
 * This class extends Cache to provide some additional organizational
 * functionality. With GroupedCaches, the developer has the option of building
 * groups of references that make organizing data easier.
 * 
 * The groups are GroupTrees of Strings. There is no actual organization of the
 * data itself, simply the references. Because of this, the core data is
 * organized identically to that of a Cache, and all additional functionality is
 * separate.
 * 
 * In the comments for this class, certain groups are referenced as "top level"
 * and others are "lower level." This is based on the tree-like behavior of
 * GroupTrees. A GroupedCache only accesses the groups that have been directly
 * added to it. These are "top level," because they lie directly accessible to
 * the GroupedCache. However, the GroupedCache has no built-in ability to
 * directly control the children of these groups, and so they are "lower-level"
 * - out of sight unless the developer manually controls them (which is, in
 * fact, the idea).
 * 
 * @author Chris Molini
 * 
 * @param <T>
 *            The type of object to contain in the GroupedCache.
 *****************************************************************************/
public class GroupedCache<T extends Destroyable & Referenced> extends Cache<T>
{
	/**
	 * The groups. A group's "name" is the String it is keyed to.
	 **/
	protected GroupTree<String> groups;

	/*************************************************************************
	 * Creates am empty GroupCache with no defined groups.
	 * 
	 * @param factory
	 * 			  The Factory to use to create new objects.
	 *************************************************************************/
	public GroupedCache(Factory<T> factory)
	{
		super(factory);
		groups = new GroupTree<String>();
	}

	/*************************************************************************
	 * Accesses the list of top-level groups.
	 * 
	 * @return The collection of groups that contain references to resources in
	 *         the GroupedCache.
	 *************************************************************************/
	public LinkedHashMap<String, GroupTree<String>> groups()
	{
		return groups.children();
	}

	/*************************************************************************
	 * Adds the reference to the indicated top-level group. Note that this does
	 * not access lower-level groups.
	 * 
	 * If the reference is not in the Cache, then an object is created from it
	 * and added.
	 * 
	 * If you want to add a reference to a lower level group, you need to
	 * retrieve the top-level group that contains it, then trawl through its
	 * children to find the desired group
	 * 
	 * @param reference
	 *            The reference to an object in the Cache.
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @param Returns
	 *            true if the group exists and it accepted the add. A false
	 *            value indicates that the indicated group does not exist.
	 *************************************************************************/
	public boolean addIntoGroup(String reference, String groupName)
	{
		add(reference);
		GroupTree<String> group = groups.getChild(groupName);
		if (group != null)
		{
			group.contents().add(reference);
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Adds a series of references into a top-level group.
	 * 
	 * @param references
	 *            The list of references to add.
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @return If the group existed and the operation was successful, returns
	 *         true; otherwise returns false.
	 *************************************************************************/
	public boolean addIntoGroup(String groupName, String... references)
	{
		add(references);

		GroupTree<String> group = groups.getChild(groupName);
		if (group != null)
		{
			for (String reference : references)
			{
				group.contents().add(reference);
			}
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Adds an already defined object into the indicated group. If the object's
	 * reference is not in the Cache, it is also added.
	 * 
	 * @param object
	 *            The object we wish to reference.
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @param Returns
	 *            true if the group exists and it accepted the add. A false
	 *            value indicates that the indicated group does not exist.
	 *************************************************************************/
	public boolean addIntoGroup(T object, String groupName)
	{
		add(object);

		GroupTree<String> group = groups.getChild(groupName);
		if (group != null)
		{
			group.contents().add(object.reference());
			return true;
		}

		return false;
	}

	/*************************************************************************
	 * Adds a series of object references into a top-level group. If any of the
	 * objects aren't in the Cache, they are added.
	 * 
	 * @param references
	 *            The list of objects whose references we wish to add.
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @return If the group existed and the operation was successful, returns
	 *         true; otherwise returns false.
	 *************************************************************************/
	public void addIntoGroup(String groupName, T... objects)
	{
		add(objects);

		GroupTree<String> group = groups.getChild(groupName);
		if (group != null)
		{
			for (T object : objects)
			{
				group.contents().add(object.reference());
			}
		}
	}

	/*************************************************************************
	 * Attempts to remove an object from the indicated list.
	 * 
	 * @param reference
	 *            The reference we want to remove.
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @return True if the removal was a success. False if either the group did
	 *         not exist, or if the group did not contain the reference.
	 *************************************************************************/
	public boolean removeFromGroup(String reference, String groupName)
	{
		GroupTree<String> group = groups.getChild(groupName);
		if (group != null)
		{
			return group.contents().remove(reference);
		}
		return false;
	}

	/*************************************************************************
	 * Creates a new, empty group with the specified key. If a group already
	 * exists under the supplied name, then no changes occur.
	 * 
	 * @param groupName
	 *            The String used to identify the Group.
	 * 
	 * @return True if there was no group with the specified name, false if
	 *         there was.
	 *************************************************************************/
	public boolean addGroup(String groupName)
	{
		if (groups.children().containsKey(groupName))
			return false;
		groups.addChild(groupName);
		return true;
	}

	/*************************************************************************
	 * Adds an already defined GroupTree to the Cache, under the indicated name.
	 * If a group already exists under that name, then the two groups are
	 * combined; if not, then the group is simply added.
	 * 
	 * @param groupName
	 *            The reference to key to the group.
	 * @param group
	 *            The group to add.
	 * 
	 * @return False if a group already existed under the supplied String; true
	 *         if not, and the group was
	 *************************************************************************/
	public boolean addGroup(String groupName, GroupTree<String> group)
	{
		for(String groupedReference : group)
			add(groupedReference);
		GroupTree<String> collision = groups.getChild(groupName);
		if (collision != null)
		{
			collision.add(group);
			return false;
		}
		groups.addChild(groupName, group);
		return true;
	}

	/*************************************************************************
	 * Attempts to empty the specified top-level group. This removes all
	 * contained references and destroys all subgroups, but leaves the group in
	 * available in the GroupedCache and does not affect any actual Cache data.
	 * 
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @return True if a group was found and cleared; false if none exist under
	 *         the supplied key.
	 *************************************************************************/
	public boolean emptyGroup(String groupName)
	{
		GroupTree<String> group = groups.getChild(groupName);
		if (group != null)
		{
			group.clear();
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Attempts to completely remove a top-level group from the GroupedCache,
	 * removing the GroupTree under the specified reference. After this call,
	 * that group is no longer available; however, Cache data is unchanged.
	 * 
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @return True if a group was found and removed; false if no group was
	 *         found to remove.
	 *************************************************************************/
	public boolean disgroup(String groupName)
	{
		return groups.removeChild(groupName) != null;
	}

	/*************************************************************************
	 * Attempts to clear the contents of a top-level group from the Cache. All
	 * objects that the group (and its children) contains references for are
	 * removed from the Cache and destroyed, and the group is cleared. However,
	 * the group remains present in the list of groups - it is simply emptied.
	 * 
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @return True if a group was found and its contents were destroyed; false
	 *         if no group was found to remove.
	 *************************************************************************/
	public boolean wipeGroup(String groupName)
	{
		GroupTree<String> group = groups.getChild(groupName);
		if (group != null)
		{
			for (String object : group)
			{
				removeAndDestroy(object);
			}
			group.clear();
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Attempts to completely eradicate the specified top-level group, both
	 * destroying and removing every object it references, and removing it from
	 * the list of available groups.
	 * 
	 * This is the most heavy-duty group removal method; it destroys Cache data
	 * and edits the group list.
	 * 
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @return True if a group was found and annihilated; false if no group was
	 *         found to destroy.
	 *************************************************************************/
	public boolean destroyGroup(String groupName)
	{
		GroupTree<String> group = groups.removeChild(groupName);
		if (group != null)
		{
			for (String ref : group)
			{
				removeAndDestroy(ref);
			}
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Completely removes all groups. Cache data is left alone.
	 * 
	 * @return Whether or not there were any groups to remove - if false is
	 *         returned, there were no groups to begin with.
	 *************************************************************************/
	public boolean clearGroups()
	{
		boolean changed = groups.children().size() != 0;
		groups.clear();
		return changed;
	}

	/*************************************************************************
	 * Provides a convenience method to iterate through all objects referenced
	 * by the indicated top-level group.
	 * 
	 * @param groupName
	 *            The group identifier.
	 * 
	 * @return An Iterable that will cycle through all objects the Cache maps to
	 *         the references in the specified group. If no group is found,
	 *         returns null.
	 *************************************************************************/
	public Iterable<T> objectsReferenced(String groupName)
	{
		return objectsReferenced(groups.getChild(groupName));
	}

	/*************************************************************************
	 * Provides a convenience method to iterate through all objects referenced
	 * by the supplied list of Strings.
	 * 
	 * @param list
	 *            The list of references to use while iteration occurs.
	 * 
	 * @return If the supplied list is null, then returns null. Otherwise,
	 *         returns an iterable that cycles through the GroupTree and
	 *         accesses the objects the Cache has keyed to the references.
	 *************************************************************************/
	public Iterable<T> objectsReferenced(Iterable<String> list)
	{
		if (list != null)
			return new EntryList(list);
		return null;
	}

	/*************************************************************************
	 * Class that provides an Iterator through the group of objects that a list
	 * of Strings references, allowing iteration through selected sections of
	 * the Cache.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class EntryList implements Iterable<T>
	{
		/**
		 * The Iterable to use for iteration.
		 **/
		private Iterable<String> references;

		/*********************************************************************
		 * Creates a default GroupedEntries instance that uses the supplied
		 * GroupTree to iterate though the Cache.
		 * 
		 * @param group
		 *            The GroupTree to use for references.
		 *********************************************************************/
		public EntryList(Iterable<String> references)
		{
			this.references = references;
		}

		/*********************************************************************
		 * Returns an iterator through the objects referenced by the GroupTree
		 * this object was initialized with. If a reference in the Cache would
		 * return null, then that entry is skipped.
		 * 
		 * @return The iterator through the list of references.
		 ***********************************************************/ @Override
		public Iterator<T> iterator()
		{
			return new EntryIterator(references.iterator());
		}

		/*********************************************************************
		 * The iterator through a list of references, returning each object that
		 * has a valid reference. If a reference doesn't have a valid reference
		 * for the Cache (i.e., a get(reference) returns null), and there are
		 * more references after that, then the null value is skipped over.
		 * 
		 * @author Chris Molini
		 *********************************************************************/
		protected class EntryIterator implements Iterator<T>
		{
			/**
			 * The iterator through the references.
			 **/
			private Iterator<String> references;

			/**
			 * The calculated next object to return.
			 **/
			private T next;

			/*****************************************************************
			 * Creates an iterator that returns the objects referenced by the
			 * supplied list of references.
			 * 
			 * @param references
			 *            The references to utilize
			 *****************************************************************/
			public EntryIterator(Iterator<String> references)
			{
				this.references = references;
				verifyNext();
			}

			/*****************************************************************
			 * Calculates the next object to return, and skips over any invalid
			 * references if they are not the final ones.
			 *****************************************************************/
			private void verifyNext()
			{
				next = get(references.next());
				while (next == null && references.hasNext())
				{
					next = get(references.next());
				}
			}

			/*****************************************************************
			 * Checks to see if continuing iteration is a good idea.
			 * 
			 * @return Whether or not calling next() will return a valid object.
			 *******************************************************/ @Override
			public boolean hasNext()
			{
				return next != null;
			}

			/*****************************************************************
			 * Accesses the next object in iteration.
			 * 
			 * @return The next object that has a valid reference, or null if no
			 *         references remain to iterate through.
			 *******************************************************/ @Override
			public T next()
			{
				T value = next;
				verifyNext();
				return value;
			}

			/*****************************************************************
			 * Not supported. Don't use.
			 * 
			 * @throws UnsupportedOperationException
			 *******************************************************/ @Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		}
	}
}