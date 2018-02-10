package org.jrabbit.standard.game.objects.specialized;

import org.jrabbit.base.graphics.skins.image.ImageSkin;
import org.jrabbit.standard.game.objects.base.BaseSprite;

/*****************************************************************************
 * An ImageSprite is a BaseSprite that exclusively uses ImageSkins to render
 * itself and handles its dimensions.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ImageSprite extends BaseSprite
{
	/**
	 * The currently active ImageSkin.
	 **/
	protected ImageSkin imageSkin;

	/*************************************************************************
	 * Creates an ImageSprite that uses an ImageSkin set to the indicated 
	 * reference.
	 * 
	 * @param reference
	 * 			  The reference of the Image in the ImageCache to retrieve. 
	 *************************************************************************/
	public ImageSprite(String reference)
	{
		this(new ImageSkin(reference));
	}
	
	/*************************************************************************
	 * Creates an ImageSprite that uses the indicated ImageSkin.
	 * 
	 * @param imageSkin
	 *************************************************************************/
	public ImageSprite(ImageSkin imageSkin)
	{
		setImageSkin(imageSkin);
	}
	
	/*************************************************************************
	 * Accesses the active ImageSkin.
	 * 
	 * @return The ImageSkin currently being used.
	 *************************************************************************/
	public ImageSkin imageSkin()
	{
		return imageSkin;
	}
	
	/*************************************************************************
	 * Redefines the active ImageSkin.
	 * 
	 * @param imageSkin
	 * 			  The new ImageSkin to use for rendering and dimension checks.
	 *************************************************************************/
	public void setImageSkin(ImageSkin imageSkin)
	{
		this.imageSkin = imageSkin;
	}
	
	/*************************************************************************
	 * Redefines the Image the ImageSkin should retrieve from the ImageCache..
	 * 
	 * @param reference
	 * 			  The reference of the Image to use.
	 *************************************************************************/
	public void setImageSkin(String reference)
	{
		imageSkin.setReference(reference);
	}

	/*************************************************************************
	 * Accesses the dimensions of the ImageSkin.
	 * 
	 * @return The width of the active ImageSkin.
	 ***************************************************************/ @Override
	public float width() { return imageSkin.width(); }

	/*************************************************************************
	 * Accesses the dimensions of the ImageSkin.
	 * 
	 * @return The height of the active ImageSkin.
	 ***************************************************************/ @Override
	public float height() { return imageSkin.height(); }

	/*************************************************************************
	 * Renders the active ImageSkin.
	 ***************************************************************/ @Override
	public void draw()
	{
		imageSkin.render();
	}
}