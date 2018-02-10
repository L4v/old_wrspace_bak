package org.jrabbit.standard.profiler.entities.standard;

import org.jrabbit.base.graphics.skins.DynamicSkinned;
import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.skins.image.ImageSkin;
import org.jrabbit.standard.profiler.entities.base.BaseProfilerEntity;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A SkinnedProfilerEntity renders a Skin. Obviously uses of this are, say,
 * rendering an image or animation instead of just text, or simply having a more
 * flexible means of rendering.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SkinnedProfilerEntity extends BaseProfilerEntity implements 
		DynamicSkinned
{
	/**
	 * The Skin being rendered.
	 **/
	protected Skin skin;

	/*************************************************************************
	 * Creates a SkinnedProfilerEntity that renders the indicated Skin.
	 * 
	 * @param reference
	 * 			  The String to identify this ProfilerEntity.
	 * @param skin
	 * 			  The Skin to render.
	 *************************************************************************/
	public SkinnedProfilerEntity(String reference, Skin skin)
	{
		super(reference);
		setSkin(skin);
	}

	/*************************************************************************
	 * Creates a SkinnedProfilerEntity that renders the indicated Image in the 
	 * Cache.
	 * 
	 * @param reference
	 * 			  The String to identify this ProfilerEntity.
	 * @param imageReference
	 * 			  The identifer of the Skin to render.
	 *************************************************************************/
	public SkinnedProfilerEntity(String reference, String imageReference)
	{
		this(reference, new ImageSkin(imageReference));
	}

	/*************************************************************************
	 * Accesses the active Skin.
	 * 
	 * @return The current Skin being rendered.
	 ***************************************************************/ @Override
	public Skin skin() { return skin; }

	/*************************************************************************
	 * Redefines the Skin to render.
	 * 
	 * @param skin
	 * 			  The new Skin to render.
	 ***************************************************************/ @Override
	public void setSkin(Skin skin)
	{
		this.skin = skin;
	}

	/*************************************************************************
	 * Accesses the dimensions of the ProfilerEntity.
	 * 
	 * @return The current width of the Skin being rendered.
	 ***************************************************************/ @Override
	public float width() { return width = skin.width(); }

	/*************************************************************************
	 * Accesses the dimensions of the ProfilerEntity.
	 * 
	 * @return The current height of the Skin being rendered.
	 ***************************************************************/ @Override
	public float height() { return height = skin.height(); }

	/*************************************************************************
	 * Renders the skin.
	 ***************************************************************/ @Override
	public void render()
	{
		float w = (int) (width() / 2);
		float h = (int) (height() / 2);
		GL11.glTranslatef(w, h, 0);
		skin.render();
		GL11.glTranslatef(-w, -h, 0);
	}
}