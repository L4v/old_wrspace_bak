package org.jrabbit.standard.profiler.renderer;

import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.standard.profiler.entities.ProfilerEntity;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * BLProfilerRenderer (short for "Bottom-Left" ProfilerRenderer) renders the 
 * list of ProfilerEntities from the bottom-left corner of the screen, going 
 * up. Every time the rendering hits the top of the allowed area, the next 
 * ProfilerEntity is rendered at the bottom of the area and to the right. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class BLProfilerRenderer extends ProfilerRenderer
{
	/*************************************************************************
	 * Creates a TLProfilerRenderer with the indicated padding.
	 * 
	 * @param padding
	 * 			  The distance to put between each ProfilerEntity.
	 *************************************************************************/
	public BLProfilerRenderer(float padding)
	{
		super(padding);
	}

	/*************************************************************************
	 * Renders the indicated list of ProfilerEntities from the bottom-left 
	 * corner of the screen. Rendering heads towards the top-right of the 
	 * screen.
	 * 
	 * @param entities
	 * 			  The ordered list of ProfilerEntities to render.
	 * @param width
	 * 			  The width of the area to keep rendering within.
	 * @param height
	 * 			  The height of the area to keep rendering within.
	 * @param color
	 * 			  The Color to render each ProfilerEntity at.
	 ***************************************************************/ @Override
	public void render(Iterable<ProfilerEntity> entities, float width,
			float height, Color color)
	{
		GL11.glTranslatef(0, height, 0);
		float shiftY = 0;
		float shiftX = 0;
		for(ProfilerEntity entity : entities)
			if(entity.visible())
			{
				float frameHeight = entity.height();
				if(shiftY + frameHeight > height)
				{
					GL11.glTranslatef(shiftX + padding, shiftY, 0);
					shiftY = 0;
					shiftX = 0;
				}
				GL11.glTranslatef(0, -frameHeight, 0);
				color.bind();
				entity.render();
				color.release();
				GL11.glTranslatef(0, -padding, 0);
				shiftY += frameHeight + padding;
				shiftX = Math.max(shiftX, entity.width());
			}
	}
}