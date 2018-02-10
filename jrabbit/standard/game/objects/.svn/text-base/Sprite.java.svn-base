package org.jrabbit.standard.game.objects;

import org.jrabbit.base.graphics.skins.DynamicSkinned;
import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.skins.image.ImageSkin;
import org.jrabbit.standard.game.objects.base.BaseSprite;

/*****************************************************************************
 * Sprite provides a simple implementation of BaseSprite that uses a Skin for
 * rendering and maintaining its dimensions. This Skin is fully dynamic, and can
 * be redefined at any time.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Sprite extends BaseSprite implements DynamicSkinned
{
	/**
	 * The Skin used to render the Sprite.
	 **/
	protected Skin skin;
	
	/*************************************************************************
	 * Creates a Sprite with an ImageSkin that attempts to retrieve the 
	 * indicated Image from the ImageCache.
	 * 
	 * @param reference
	 * 			  The reference indicating the Image to use.
	 *************************************************************************/
	public Sprite(String reference)
	{
		this(new ImageSkin(reference));
	}
	
	/*************************************************************************
	 * Creates a Sprite that utilizes the indicated Skin.
	 * 
	 * @param skin
	 * 			  The Skin to use.
	 *************************************************************************/
	public Sprite(Skin skin)
	{
		setSkin(skin);
	}
	
	/*************************************************************************
	 * Accesses the current Skin.
	 * 
	 * @return The Skin being used to render and determine the Sprite's 
	 *         dimensions.
	 ***************************************************************/ @Override
	public Skin skin() { return skin; }
	
	/*************************************************************************
	 * Redefines the active Skin being used.
	 * 
	 * @param skin
	 * 			  The Skin for the Sprite to use.
	 ***************************************************************/ @Override
	public void setSkin(Skin skin)
	{
		this.skin = skin;
	}
	
	/*************************************************************************
	 * Accesses the dimensions of the current Skin used by the Sprite.
	 * 
	 * @return The width of the Skin. 
	 ***************************************************************/ @Override
	public float width() { return skin.width(); }
	
	/*************************************************************************
	 * Accesses the dimensions of the current Skin used by the Sprite.
	 * 
	 * @return The height of the Skin. 
	 ***************************************************************/ @Override
	public float height() { return skin.height(); }
	
	/*************************************************************************
	 * Renders the active skin.
	 ***************************************************************/ @Override
	public void draw()
	{
		skin.render();
	}
}