package org.jrabbit.standard.profiler.renderer;

import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.standard.profiler.entities.ProfilerEntity;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * TRProfilerRenderer (short for "Top-Right" ProfilerRenderer) renders the list
 * of ProfilerEntities from the top-right corner of the screen, going down. 
 * Every time the rendering hits the bottom of the allowed area, the next 
 * ProfilerEntity is rendered at the top of the area and to the left. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class TRProfilerRenderer extends ProfilerRenderer
{
	/*************************************************************************
	 * Creates a TLProfilerRenderer with the indicated padding.
	 * 
	 * @param padding
	 * 			  The distance to put between each ProfilerEntity.
	 *************************************************************************/
	public TRProfilerRenderer(float padding)
	{
		super(padding);
	}

	/*************************************************************************
	 * Renders the indicated list of ProfilerEntities from the top-right corner
	 * of the screen. Rendering heads towards the bottom-left of the screen.
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
		GL11.glTranslatef(width, 0, 0);
		float shiftY = 0;
		float shiftX = 0;
		for(ProfilerEntity entity : entities)
			if(entity.visible())
			{
				float effectiveHeight = entity.height() + padding;
				float frameWidth = entity.width();
				if(shiftY + entity.height() > height)
				{
					GL11.glTranslatef(-(shiftX + padding), -shiftY, 0);
					shiftY = 0;
					shiftX = 0;
				}
				GL11.glTranslatef(-frameWidth, 0, 0);
				color.bind();
				entity.render();
				color.release();
				GL11.glTranslatef(frameWidth, 0, 0);
				shiftY += effectiveHeight;
				GL11.glTranslatef(0, effectiveHeight, 0);
				shiftX = Math.max(shiftX, entity.width());
			}
	}
}