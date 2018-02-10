package org.jrabbit.standard.profiler.renderer;

import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.standard.profiler.entities.ProfilerEntity;

/*****************************************************************************
 * A ProfilerRenderer is used by a ProfilerDisplay to render its 
 * ProfilerEntities.
 * 
 * Essentially, though a ProfilerDisplay manages the LIST of entities it shows,
 * it delegates their ORGANIZATION to its renderer. That it the sole purpose of
 * this class - to provide a lightweight means of custom organization.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class ProfilerRenderer
{
	/**
	 * The space to put between each rendered ProfilerEntity.
	 **/
	protected float padding;

	/*************************************************************************
	 * Creates a ProfilerRenderer that spaces the ProfilerEntities the indicated
	 * distance apart.
	 * 
	 * @param padding
	 * 			  The distance to put between each ProfilerEntity.
	 *************************************************************************/
	public ProfilerRenderer(float padding)
	{
		setPadding(padding);
	}

	/*************************************************************************
	 * Learns the current padding being used.
	 * 
	 * @return The space between each ProfilerEntity when rendered.
	 *************************************************************************/
	public float padding() { return padding; }

	/*************************************************************************
	 * Redefines the current padding.
	 * 
	 * @param padding.
	 * 			  The space to put between each ProfilerEntity when rendered.
	 *************************************************************************/
	public void setPadding(float padding)
	{
		this.padding = padding;
	}

	/*************************************************************************
	 * Renders the supplied list of ProfilerEntities, rendering each at the
	 * indicated Color.
	 * 
	 * NOTE: The ProfilerRenderer should do its best to keep all rendering 
	 * within the indicated dimensions.
	 * 
	 * NOTE #2: At the beginning of rendering, the OpenGL Modelview Matrix is
	 * already set to the top-left corner of the indicated rectangle. This 
	 * should be respected.
	 * 
	 * @param entities
	 * 			  The ordered list of ProfilerEntities to render.
	 * @param width
	 * 			  The width of the area to keep rendering within.
	 * @param height
	 * 			  The height of the area to keep rendering within.
	 * @param color
	 * 			  The Color to render each ProfilerEntity at.
	 *************************************************************************/
	public abstract void render(Iterable<ProfilerEntity> entities, float width, 
			float height, Color color);
}