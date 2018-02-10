package org.jrabbit.base.data.thread;

import org.jrabbit.base.graphics.image.Image;

/*****************************************************************************
 * A class that provides an easy way to load Images in a separate Thread. This
 * is bound by the same problems as with a WatchableGLThread.
 * 
 * It's quite easy to make one's own loading Thread, and in fact, this is a
 * recommended way of loading things in the background - you can more closely
 * tie the functionality with your needs. However, this class illustrates a
 * simple way to load images while keeping track of the total progress, and if
 * you simply want a fast and easy method to create some Images from file
 * references, then this is for you.
 * 
 * @author Chris Molini
 *****************************************************************************/
public final class ImageLoadingThread extends WatchableGLThread
{
	/**
	 * The supplied list of filepaths that are used to load images.
	 **/
	private String[] filepaths;

	/**
	 * The list of constructed Images.
	 **/
	private Image[] images;

	/*************************************************************************
	 * Instantiates a Thread that will load images from the list of filepaths.
	 * 
	 * NOTE: Each of these Images will have its appropriate filepath as a
	 * reference.
	 * 
	 * @param filepaths
	 *            The locations in the file system of the images to load.
	 *************************************************************************/
	public ImageLoadingThread(String[] filepaths)
	{
		this.filepaths = filepaths;
		images = new Image[filepaths.length];
		total = filepaths.length;
	}

	/*************************************************************************
	 * Loads the images, and increments the progress counter.
	 ***************************************************************/ @Override
	protected void act()
	{
		for (int i = 0; i < filepaths.length; i++)
		{
			images[i] = new Image(filepaths[i]);
			progress++;
		}
	}

	/*************************************************************************
	 * Accesses a reference to the array of images in the loading Thread. This
	 * shouldn't be used until loading is complete.
	 * 
	 * @return The list of created Images.
	 *************************************************************************/
	public Image[] images()
	{
		if (complete())
			return images;
		return null;
	}
}