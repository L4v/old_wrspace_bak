package org.jrabbit.base.graphics.layers.types;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.data.structures.LockingList;
import org.jrabbit.base.graphics.layers.Layer;
import org.jrabbit.base.graphics.transforms.GLGroupTransform;

/*****************************************************************************
 * DefaultLayer adds Layer functionality to LockingList. This way, all adding,
 * removal, and iteration processes are very fast, concurrent-safe, and allow 
 * only unique objects.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DefaultLayer extends LockingList<Renderable> implements Layer
{
	/**
	 * The identifier of this Layer.
	 **/
	protected String reference;

	/**
	 * The list of transforms that affect this Layer. By default, this is empty.
	 **/
	protected GLGroupTransform transforms;

	/*************************************************************************
	 * Creates a DefaultLayer with the specified identifier.
	 * 
	 * @param reference
	 * 			  The String that will identify this Layer.
	 *************************************************************************/
	public DefaultLayer(String reference)
	{
		this.reference = reference;
		transforms = new GLGroupTransform();
	}

	/*************************************************************************
	 * Gets the reference associated with this object.
	 * 
	 * @return The String that identifies the object.
	 ***************************************************************/ @Override
	public String reference()
	{
		return reference;
	}

	/*************************************************************************
	 * Accesses the list of transforms.
	 * 
	 * @return The GLGroupTransform that dynamically controls OpenGL's state.
	 ***************************************************************/ @Override
	public GLGroupTransform transforms()
	{
		return transforms;
	}

	/*************************************************************************
	 * Applies the GLGroupTransform to adjust OpenGL settings.
	 ***************************************************************/ @Override
	public void bind()
	{
		transforms.bind();
	}

	/*************************************************************************
	 * Releases all changes in the GLGroupTransform.
	 ***************************************************************/ @Override
	public void release()
	{
		transforms.release();
	}

	/*************************************************************************
	 * Applies the GLGroupTransform, renders every object contained by this
	 * Layer, and then releases the GLGroupTransform.
	 ***************************************************************/ @Override
	public void render()
	{
		bind();
		for (Renderable r : this)
			r.render();
		unlock();
		release();
	}
}