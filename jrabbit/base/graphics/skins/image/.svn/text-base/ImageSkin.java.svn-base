package org.jrabbit.base.graphics.skins.image;

import org.jrabbit.base.graphics.image.ImagePointer;
import org.jrabbit.base.graphics.skins.Skin;

/*****************************************************************************
 * An ImageSkin is (obviously) a Skin that renders an Image. All Images it
 * retrieves come from the ImageCache in Resources.
 * 
 * ImageSkin extends ImagePointer; thus, it has access to all of the Cache
 * retrieval and optimization of its parent.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ImageSkin extends ImagePointer implements Skin
{
	/**
	 * The width of the current image.
	 **/
	protected float width;

	/**
	 * The height of the current image.
	 **/
	protected float height;

	/*************************************************************************
	 * Creates an ImageSkin that uses the indicated reference to search for
	 * the desired Image in the ImageCache.
	 * 
	 * @param reference
	 * 			  The reference of the Image to search for in the Cache.
	 *************************************************************************/
	public ImageSkin(String reference)
	{
		super(reference);
	}

	/*************************************************************************
	 * Renders the image this ImageSkin represents.
	 ***************************************************************/ @Override
	public void render() { object.render(); }

	/*************************************************************************
	 * Learns about the dimensions of the ImageSkin.
	 * 
	 * @return The width of the Image being rendered.
	 ***************************************************************/ @Override
	public float width() { return width; }

	/*************************************************************************
	 * Learns about the dimensions of the ImageSkin.
	 * 
	 * @return The height of the Image being rendered.
	 ***************************************************************/ @Override
	public float height() { return height; }

	/*************************************************************************
	 * Refreshes the cached Image reference; this does not need to be called 
	 * manually unless the contents of the Cache are altered.
	 * 
	 * Additionally, this updates the stored dimensional values (width and 
	 * height).
	 ***************************************************************/ @Override
	public void refresh()
	{
		object = retrieve();
		width = object.width();
		height = object.height();
	}
}