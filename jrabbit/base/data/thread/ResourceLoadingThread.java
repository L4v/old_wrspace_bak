package org.jrabbit.base.data.thread;

import org.jrabbit.base.graphics.font.Font;
import org.jrabbit.base.graphics.image.Image;
import org.jrabbit.base.sound.SoundData;

/*****************************************************************************
 * A base class for loading resources in a separate Thread that has some default
 * methods for loading each of the 3 basic resources (images, sounds, and
 * fonts).
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class ResourceLoadingThread extends WatchableGLThread
{
	/**
	 * The list of any Images loaded by this thread.
	 **/
	protected Image[] images;

	/**
	 * The list of any sounds loaded by this thread.
	 **/
	protected SoundData[] sounds;

	/**
	 * The list of any fonts loaded by this thread.
	 **/
	protected Font[] fonts;

	/*************************************************************************
	 * Delegates loading of each resource to the appropriate method.
	 ***************************************************************/ @Override
	protected void act()
	{
		loadImages();
		loadFonts();
		loadSounds();
	}

	/*************************************************************************
	 * Override this method to load any desired Fonts.
	 *************************************************************************/
	protected void loadFonts() { }

	/*************************************************************************
	 * Override this method to load any desired Images.
	 *************************************************************************/
	protected void loadImages() { }

	/*************************************************************************
	 * Override this method to load any desired Sounds.
	 *************************************************************************/
	protected void loadSounds() { }

	/*************************************************************************
	 * Accesses the loaded Images. Should not be used until loading is complete.
	 * 
	 * @return All Images loaded by this Thread.
	 *************************************************************************/
	public Image[] images()
	{
		return images;
	}

	/*************************************************************************
	 * Accesses the loaded Sound data. Should not be used until loading is
	 * complete.
	 * 
	 * @return All Images loaded by this Thread.
	 *************************************************************************/
	public SoundData[] sounds()
	{
		return sounds;
	}

	/*************************************************************************
	 * Accesses the loaded Fonts. Should not be used until loading is complete.
	 * 
	 * @return All Images loaded by this Thread.
	 *************************************************************************/
	public Font[] fonts()
	{
		return fonts;
	}
}
