package org.jrabbit.base.math.vector;

import java.util.Iterator;
import java.util.LinkedList;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * VectorPath is a lightweight data structure designed to keep an ordered list
 * of Vector2fs. In doing so, it represents a path.
 * 
 * VectorPath is bounded; it has a maximum number of vectors allowed in it. If
 * more Vectors are placed in the list than allowed, they are dynamically
 * streamed out of it.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class VectorPath implements Iterable<Vector2f>, Renderable
{
	/**
	 * The list of Vectors.
	 **/
	protected LinkedList<Vector2f> path;

	/**
	 * The maximum number of Vectors allowed in the path.
	 **/
	protected int maxLength;

	/*************************************************************************
	 * Creates a VectorPath that allows Integer.MAX_VALUE number of Vectors.
	 *************************************************************************/
	public VectorPath()
	{
		this(Integer.MAX_VALUE);
	}

	/*************************************************************************
	 * Creates a VectorPath that will hold the indicated number of Vectors.
	 * 
	 * @param maxLength
	 *            The number of Vectors to allow.
	 *************************************************************************/
	public VectorPath(int maxLength)
	{
		path = new LinkedList<Vector2f>();
		this.maxLength = maxLength;
	}

	/*************************************************************************
	 * Learns the current length of the path.
	 * 
	 * @return How many Vectors are contained.
	 *************************************************************************/
	public int length()
	{
		return path.size();
	}

	/*************************************************************************
	 * Learns the max length of the path.
	 * 
	 * @return The maximum number of Vectors allowed in this path.
	 *************************************************************************/
	public int maxLength()
	{
		return maxLength;
	}

	/*************************************************************************
	 * Redefines the number of allowed Vectors. If the current length is greater
	 * than the new max size, the path is cropped to fit.
	 * 
	 * @param length
	 *            The new max length.
	 *************************************************************************/
	public void setMaxLength(int length)
	{
		if (length > 0)
		{
			maxLength = length;
			checkPath();
		}
	}

	/*************************************************************************
	 * Accesses the "beginning" of the path. This is the oldest Vector currently
	 * contained.
	 * 
	 * @return The start of the path.
	 *************************************************************************/
	public Vector2f start()
	{
		return path.getFirst();
	}

	/*************************************************************************
	 * Accesses the "end" of the path. This is the newest Vector currently
	 * contained.
	 * 
	 * @return The end of the path.
	 *************************************************************************/
	public Vector2f end()
	{
		return path.getLast();
	}

	/*************************************************************************
	 * Adds another Vector to the end of this path, and checks the size. If more
	 * Vectors are contained than allowed, the "start" Vector is removed.
	 * 
	 * @param vector
	 *            The Vector to add.
	 *************************************************************************/
	public void push(Vector2f vector)
	{
		path.addLast(vector);
		checkPath();
	}

	/*************************************************************************
	 * Checks the path's length to keep it in bounds. While more Vectors are
	 * contained than allowed, the "start" Vector is removed.
	 *************************************************************************/
	private void checkPath()
	{
		while (path.size() > maxLength)
			path.removeFirst();
	}

	/*************************************************************************
	 * Accesses the list of Vectors.
	 * 
	 * @return The LinkedList used to keep track of path coordinates.
	 *************************************************************************/
	public LinkedList<Vector2f> pathData()
	{
		return path;
	}

	/*************************************************************************
	 * Accesses an iterator that cycles through the path.
	 * 
	 * @return The Iterator of the list used to contain the Vectors.
	 ***************************************************************/ @Override
	public Iterator<Vector2f> iterator()
	{
		return path.iterator();
	}

	/*************************************************************************
	 * Draws the path as a continuous line.
	 * 
	 * NOTE: This makes no effort to change the currently bound texture, so it's
	 * recommended to change the settings on bound textures before this is
	 * called.
	 ***************************************************************/ @Override
	public void render()
	{
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for (Vector2f vector : this)
			vector.vertex();
		GL11.glEnd();
	}
}