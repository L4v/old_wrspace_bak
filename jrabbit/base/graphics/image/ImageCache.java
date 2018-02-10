package org.jrabbit.base.graphics.image;

import org.jrabbit.base.data.Factory;
import org.jrabbit.base.data.cache.GroupedCache;

/*****************************************************************************
 * A Cache for holding Images.
 * 
 * In addition to simply holding images for access, an ImageCache adds some
 * simple methods for managing the scaling filter (i.e., pixelation or
 * smoothing) for groups of images.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ImageCache extends GroupedCache<Image>
{
	/**
	 * Whether or not images are supposed to be smoothed or not. Images have the
	 * appropriate value applied to them whenever they are added to the cache.
	 **/
	private boolean smoothImages = true;

	/*************************************************************************
	 * Creates an empty ImageCache and sets its Factory to the default.
	 * 
	 * If an Image is requested that is not in the list, the Factory attempts to
	 * create one as if its reference was a filepath pointing to the desired
	 * source file.
	 *************************************************************************/
	public ImageCache()
	{
		super(null);
		setFactory(new ImageFactory());
	}

	/*************************************************************************
	 * Adds an Image, and checks whether the add was successful.
	 * 
	 * NOTE: If the Image is added, its smoothing flag is set to the default
	 * value of the cache.
	 * 
	 * @param object
	 *            The object to add.
	 * 
	 * @return Whether or not the add was successful.
	 ***************************************************************/ @Override
	public boolean add(Image image)
	{
		if(super.add(image))
		{
			image.smooth(smoothImages);
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Sets whether or not images should be pixelated or smoothed.
	 * 
	 * Immediately on this call, every image in the cache has its scaling filter
	 * updated to deal with this change. Additionally, every Image created by
	 * the factory will have the same filter applied to it.
	 * 
	 * NOTE: If you create images externally, and add them after setting this
	 * value, you need to set the scaling filter yourself.
	 * 
	 * @param smooth
	 *            True if images should have a smoothed appearance, false if
	 *            they should be pixelated.
	 *************************************************************************/
	public void setImageSmooth(boolean smooth)
	{
		if (smoothImages != smooth)
		{
			smoothImages = smooth;
			for (Image i : this)
				i.smooth(smoothImages);
		}
	}

	/*************************************************************************
	 * Changes the scaling filter for a particular group of Images in the Cache.
	 * 
	 * @param smooth
	 *            Whether or not to smooth the image.
	 * @param groupName
	 *            The name of the group to use.
	 *************************************************************************/
	public void setImageSmooth(boolean smooth, String groupName)
	{
		for (Image i : objectsReferenced(groupName))
			i.smooth(smooth);
	}

	/*************************************************************************
	 * The default Factory attempts to create an Image as if its reference was a
	 * filepath pointing to the desired source file.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	private class ImageFactory implements Factory<Image>
	{
		/*********************************************************************
		 * Makes an image from the supplied reference.
		 * 
		 * @param reference
		 *            The filepath to the desired source image.
		 ***********************************************************/ @Override
		public Image create(String reference)
		{
			if (reference != null)
			{
				Image image = new Image(reference);
				image.smooth(smoothImages);
				return image;
			}
			return null;
		}
	}
}