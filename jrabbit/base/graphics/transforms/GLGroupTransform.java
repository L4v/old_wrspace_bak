package org.jrabbit.base.graphics.transforms;

/*****************************************************************************
 * A GLGroupTransform is an object that performs a series of operations on the
 * OpenGL Modelview Matrix. It has a dynamic list of the operations it needs to
 * perform.
 * 
 * The class contains an internal array that holds the transforms to execute.
 * This resizes when needed to contain additional transforms.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GLGroupTransform implements GLTransform
{
	/**
	 * The "increment" value for resizing the list of transforms.
	 **/
	private static final int TRANSFORM_BUFFER = 5;

	/**
	 * The array of all transforms, in order of their execution.
	 **/
	protected GLTransform[] transforms;

	/**
	 * The number of transforms currently in the list.
	 **/
	protected int size;

	/*************************************************************************
	 * Creates an empty GLGroupTransform.
	 *************************************************************************/
	public GLGroupTransform()
	{
		transforms = new GLTransform[TRANSFORM_BUFFER];
	}

	/*************************************************************************
	 * Creates a GLGroupTransform with the indicated transforms added to the 
	 * list.
	 * 
	 * @param transforms
	 * 			  The GLTransforms for the GLGroupTransform to begin with.
	 *************************************************************************/
	public GLGroupTransform(GLTransform... transforms)
	{
		this.transforms = new GLTransform[transforms.length];
		add(transforms);
	}

	/*************************************************************************
	 * Applies every operation in the list, in order.
	 ***************************************************************/ @Override
	public void bind()
	{
		for (int i = 0; i < size; i++)
			transforms[i].bind();
	}

	/*************************************************************************
	 * Re-iterates through the list of transforms, undoing each one in the
	 * reverse order they were applied in.
	 ***************************************************************/ @Override
	public void release()
	{
		for (int i = size - 1; i >= 0; i--)
			transforms[i].release();
	}

	/*************************************************************************
	 * Expands the internal array to hold at least as many transforms as
	 * requested.
	 * 
	 * If the array is already at least as large as the indicated size, no
	 * changes occur.
	 * 
	 * @param size
	 *            The minimum number of transforms to contain in the list.
	 *************************************************************************/
	public void allocate(int size)
	{
		if (transforms.length < size)
		{
			GLTransform[] newList = new GLTransform[size];
			for (int i = 0; i < this.size; i++)
				newList[i] = transforms[i];
			transforms = newList;
		}
	}

	/*************************************************************************
	 * Places a transform after those already in the list.
	 * 
	 * @param transform
	 *            The object that will cause a change in OpenGL's state to
	 *            affect rendering.
	 *************************************************************************/
	public int add(GLTransform transform)
	{
		// If the current list is full, we need to expand
		// the array.
		if (size >= transforms.length)
			allocate(size + TRANSFORM_BUFFER);
		transforms[size] = transform;
		size++;
		return size - 1;
	}

	/*************************************************************************
	 * Places a series of transforms after those already in the list.
	 * 
	 * @param transforms
	 *            The object that will cause a change in OpenGL's state to
	 *            affect rendering.
	 *************************************************************************/
	public void add(GLTransform... transforms)
	{
		if (this.transforms.length < size + transforms.length)
			allocate(size + transforms.length);
		for (int i = 0; i < transforms.length; i++)
			this.transforms[size + i] = transforms[i];
		size += transforms.length;
	}

	/*************************************************************************
	 * Attempts to retrieve the desired transform.
	 * 
	 * @param place
	 *            The location of the transform in the internal array.
	 * 
	 * @return The transform at the indicated position.
	 *************************************************************************/
	public GLTransform get(int place)
	{
		return transforms[place];
	}

	/*************************************************************************
	 * Attempts to remove the indicated transform from the list.
	 * 
	 * @param key
	 *            The String that identifies which transform to remove.
	 * 
	 * @return If a transform was removed, returns that transform. If not, null
	 *         is returned.
	 *************************************************************************/
	public GLTransform remove(int place)
	{
		GLTransform removed = transforms[place];
		for (int i = place; i < size - 1; i++)
			transforms[i] = transforms[i + 1];
		transforms[size - 1] = null;
		size--;
		return removed;
	}

	/*************************************************************************
	 * Attempts to remove the indicated transform from the list. The
	 * GroupTransform searches through the list for an equivalent GLTransform
	 * (via equals()) and removes it if found.
	 * 
	 * @param transform
	 *            The transform (or one equivalent to it) to remove.
	 * 
	 * @return If a transform was removed, returns true, otherwise false.
	 *************************************************************************/
	public boolean remove(GLTransform transform)
	{
		for (int i = 0; i < size; i++)
			if (transforms[i].equals(transform))
				return remove(i) != null;
		return false;
	}

	/*************************************************************************
	 * Attempts to replace the GLTransform at the indicated position with the
	 * one supplied.
	 * 
	 * @param place
	 *            The position to replace.
	 * @param newTransform
	 *            The new transform to place in the list.
	 * 
	 * @return True if the operation succeeded, false if not.
	 *************************************************************************/
	public boolean replace(int place, GLTransform newTransform)
	{
		if (size < place)
			return false;
		transforms[place] = newTransform;
		return true;
	}

	/*************************************************************************
	 * Learns the size of the group transform.
	 * 
	 * @return The number of transforms currently used by the GroupTransform.
	 *************************************************************************/
	public int size()
	{
		return size;
	}

	/*************************************************************************
	 * Removes all transforms.
	 *************************************************************************/
	public void clear()
	{
		size = 0;
	}
}