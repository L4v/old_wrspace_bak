package org.jrabbit.standard.profiler.entities.base;

import org.jrabbit.standard.profiler.entities.ProfilerEntity;

/*****************************************************************************
 * A BaseProfilerEntity provides some default functionality that is usable by
 * most ProfilerEntities.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class BaseProfilerEntity implements ProfilerEntity
{
	/**
	 * The identifier of this object.
	 **/
	protected String reference;
	
	/**
	 * The width of the object.
	 **/
	protected float width;
	
	/**
	 * The height of the object.
	 **/
	protected float height;
	
	/**
	 * Whether or not the ProfilerEntity is currently visible.
	 **/
	protected boolean visible;
	
	/*************************************************************************
	 * Creates a BaseProfilerEntity with the supplied identifier.
	 * 
	 * @param reference
	 * 			  The String that will identify this ProfilerEntity.
	 *************************************************************************/
	public BaseProfilerEntity(String reference)
	{
		this.reference = reference;
		visible = true;
	}

	/*************************************************************************
	 * Accesses this ProfilerEntity's reference.
	 * 
	 * @return The String that identifies this ProfilerEntity.
	 ***************************************************************/ @Override
	public String reference() { return reference; }

	/*************************************************************************
	 * Accesses the dimensions of the ProfilerEntity.
	 * 
	 * @return The width of the ProfilerEntity.
	 ***************************************************************/ @Override
	public float width() { return width; }

	/*************************************************************************
	 * Accesses the dimensions of the ProfilerEntity.
	 * 
	 * @return The height of the ProfilerEntity.
	 ***************************************************************/ @Override
	public float height() { return height; }

	/*************************************************************************
	 * Learns whether or not the ProfilerEntity is visible.
	 * 
	 * @return True if the ProfilerEntity should be rendered, false if not.
	 ***************************************************************/ @Override
	public boolean visible() { return visible; }

	/*************************************************************************
	 * Redefines whether or not the ProfilerEntity is visible.
	 * 
	 * @param visible
	 * 			  True if the ProfilerEntity should be rendered, false if not.
	 ***************************************************************/ @Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}