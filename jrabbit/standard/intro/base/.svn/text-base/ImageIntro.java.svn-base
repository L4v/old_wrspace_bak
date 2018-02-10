package org.jrabbit.standard.intro.base;

import org.jrabbit.base.data.loading.Loader;
import org.jrabbit.base.data.loading.SystemLoader;
import org.jrabbit.base.graphics.image.Image;
import org.jrabbit.standard.intro.Intro;

/*****************************************************************************
 * An ImageIntro is an Intro that uses an Image. All that this abstract 
 * implementation does is to add the automated loading and deletion of the 
 * desired Image. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class ImageIntro implements Intro
{
	/**
	 * The Loader that will load the desired Image.
	 **/
	protected Loader imageLoader;
	
	/**
	 * The Image that has been loaded and is available for the Intro to use.
	 **/
	protected Image image;
	
	/*************************************************************************
	 * Creates an ImageIntro that uses the Image at the indicated resource 
	 * location.
	 * 
	 * @param filepath
	 * 			  The path to the desired source image file.
	 *************************************************************************/
	public ImageIntro(String filepath)
	{
		this(new SystemLoader(filepath));
	}

	/*************************************************************************
	 * Creates an ImageIntro that uses the Image retrieved from the supplied
	 * Loader.
	 * 
	 * @param loader
	 * 			  The Loader that accesses the desired source image.
	 *************************************************************************/
	public ImageIntro(Loader loader)
	{
		imageLoader = loader;
	}

	/*************************************************************************
	 * Loads the Image this Intro will use.
	 ***************************************************************/ @Override
	public void create()
	{
		image = new Image(imageLoader);
		imageLoader = null;
	}

	/*************************************************************************
	 * Deletes the loaded Image, freeing memory on the GPU.
	 ***************************************************************/ @Override
	public void destroy()
	{
		image.destroy();
	}
}