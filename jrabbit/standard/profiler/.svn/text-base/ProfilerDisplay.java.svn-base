package org.jrabbit.standard.profiler;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.data.Factory;
import org.jrabbit.base.data.structures.OrderedKeyedContainer;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.GLGroupTransform;
import org.jrabbit.base.graphics.transforms.GLReset;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.graphics.types.Colored;
import org.jrabbit.base.graphics.types.GLGroupTransformed;
import org.jrabbit.base.graphics.types.Visible;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.profiler.entities.ProfilerEntity;
import org.jrabbit.standard.profiler.entities.standard.ProfilerMessage;
import org.jrabbit.standard.profiler.renderer.ProfilerRenderer;
import org.jrabbit.standard.profiler.renderer.TLProfilerRenderer;
import org.jrabbit.standard.profiler.util.Timer;

/*****************************************************************************
 * A ProfilerDisplay is an object rendered and updated by a GameLoop that stays
 * constant despite changes in the GameLoop's world. It is specifically designed
 * to display information; most particularly, it is intended to display 
 * information about how the game itself is functioning (a.k.a., it is called a
 * "Profiler").
 * 
 * The API for using the ProfilerDisplay is somewhat complex, but a default 
 * setup that works for most situations is already provided.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ProfilerDisplay implements Renderable, Visible, GLGroupTransformed,
		Colored
{
	/**
	 * The list of ProfilerEntities, keyed to their references.
	 **/
	protected OrderedKeyedContainer<ProfilerEntity> contents;
	
	/**
	 * The object being used to render the list of ProfilerEntities.
	 **/
	protected ProfilerRenderer renderer;
	
	/**
	 * The list of GLTransforms that is applied to this ProfilerDisplay.
	 **/
	protected GLGroupTransform transforms;
	
	/**
	 * This represents the "buffer" of empty space that is around the contents
	 * of the ProfilerDisplay when rendered.
	 **/
	protected Vector2f buffer;
	
	/**
	 * This is the Color all ProfilerEntities are rendered at.
	 **/
	protected Color color;
	
	/**
	 * Whether or not the ProfilerDisplay should be shown. By default this is
	 * left active. 
	 **/
	protected boolean visible;
	
	/*************************************************************************
	 * Creates a ProfilerDisplay that renders its contents from the top-left of
	 * the screen.
	 *************************************************************************/
	public ProfilerDisplay()
	{
		this(new TLProfilerRenderer(10));
	}
	
	/*************************************************************************
	 * Creates a ProfilerDisplay that uses the indicated renderer.
	 * 
	 * @param renderer
	 * 			  The ProfilerRenderer that will organize the ProfilerEntities
	 * 			  when rendered.
	 *************************************************************************/
	public ProfilerDisplay(ProfilerRenderer renderer)
	{
		visible = true;
		color = new Color(1f, 1f, 1f, 0.5f);
		
		transforms = new GLGroupTransform(new GLReset(),
				buffer = new Vector2f(25, 25));
		
		contents = new OrderedKeyedContainer<ProfilerEntity>(
				new Factory<ProfilerEntity>() {
				public ProfilerEntity create(String reference){
					return new ProfilerMessage(new Timer(reference));
				}});
		this.renderer = renderer;
	}

	/*************************************************************************
	 * Accesses the list of ProfilerEntities.
	 * 
	 * @return The OrderedKeyedContainer that maintains the keyed list of
	 * ProfilerEntities.
	 *************************************************************************/
	public OrderedKeyedContainer<ProfilerEntity> contents() { return contents; }

	/*************************************************************************
	 * Accesses the object used to render the list of ProfilerEntities.
	 * 
	 * @return The ProfilerRenderer that renders the ProfilerDisplay.
	 *************************************************************************/
	public ProfilerRenderer renderer() { return renderer; }

	/*************************************************************************
	 * Redefines the object used to render the list of ProfilerEntities.
	 * 
	 * @param renderer
	 * 			  The new ProfilerRenderer to use.
	 *************************************************************************/
	public void setRenderer(ProfilerRenderer renderer)
	{
		this.renderer = renderer;
	}

	/*************************************************************************
	 * Accesses the ProfilerDisplay's buffer.
	 * 
	 * @return The Vector2f that represents the buffer around the edges of the
	 * screen when the ProfilerDisplay is rendered. 
	 *************************************************************************/
	public Vector2f buffer() { return buffer; }

	/*************************************************************************
	 * Accesses the ProfilerDisplay's color.
	 * 
	 * @return The Color that is applied to every ProfilerEntity when rendered.
	 ***************************************************************/ @Override
	public Color color() { return color; }

	/*************************************************************************
	 * Accesses the dynamic list of GLTransforms being applied to the 
	 * ProfilerDisplay.
	 * 
	 * @return The GLGroupTransform that affects this ProfilerDisplay.
	 ***************************************************************/ @Override
	public GLGroupTransform transforms() { return transforms; }

	/*************************************************************************
	 * Learns if the ProfilerDisplay is being rendered.
	 * 
	 * @return True if the ProfilerDisplay is being rendered, false if not.
	 ***************************************************************/ @Override
	public boolean visible() { return visible; }

	/*************************************************************************
	 * Redefines the ProfilerDisplay's visibility flag.
	 * 
	 * @param visible
	 * 			  Whether or not the ProfilerDisplay should be rendered.
	 ***************************************************************/ @Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	/*************************************************************************
	 * Sets the visibility flag for all ProfilerEntities in the list.
	 * 
	 * NOTE: This call is different that {@link 
	 * ProfilerDisplay#setVisible(boolean)}. If this is being called in an
	 * attempt to hide the ProfilerDisplay, use the other method.
	 * 
	 * @param visible
	 * 			  Whether or not the ProfilerEntities should be visible.
	 *************************************************************************/
	public void setAllVisible(boolean visible)
	{
		for(ProfilerEntity entity : contents)
			entity.setVisible(visible);
	}

	/*************************************************************************
	 * Sets the visibility flag for all specified ProfilerEntities.
	 * 
	 * @param visible
	 * 			  Whether or not an affected ProfilerEntity should be visible or
	 * 			  not.
	 * @param references
	 * 			  The references of all ProfilerEntities to affect.
	 *************************************************************************/
	public void setVisibleFor(boolean visible, String... references)
	{
		for(String reference : references)
			contents.get(reference).setVisible(visible);
	}

	/*************************************************************************
	 * Sets the visibility flag for all ProfilerEntities, except those with
	 * the indicated references.
	 * 
	 * @param visible
	 * 			  Whether or not an affected ProfilerEntity should be visible or
	 * 			  not.
	 * @param references
	 * 			  The references of all ProfilerEntities to exclude.
	 *************************************************************************/
	public void setVisibleForAllExcept(boolean visible, String... references)
	{
		for(ProfilerEntity entity : contents)
			check :
			{
				for(String reference : references)
					if(entity.reference().equals(reference))
						break check;
				entity.setVisible(visible);
			}
	}

	/*************************************************************************
	 * Applies all GLTransforms in the list.
	 ***************************************************************/ @Override
	public void bind()
	{
		transforms.bind();
	}

	/*************************************************************************
	 * Calls release() on the list of GLTransforms.
	 ***************************************************************/ @Override
	public void release()
	{
		transforms.release();
	}

	/*************************************************************************
	 * Renders the ProfilerDisplay.
	 ***************************************************************/ @Override
	public void render()
	{
		if(visible())
		{
			bind();
			renderer.render(contents, WindowManager.controller().width() - 
					buffer.x() * 2, WindowManager.controller().height() - 
					buffer.y() * 2, color);
			release();
		}
	}
}