package org.jrabbit.standard.intro.base;

import org.jrabbit.base.data.loading.Loader;
import org.jrabbit.base.data.loading.SystemLoader;
import org.jrabbit.base.sound.SoundData;

/*****************************************************************************
 * ImageAudioIntro extends ImageIntro to also allow a single clip of Sound to
 * be loaded and used by the Intro.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class ImageAudioIntro extends ImageIntro
{
	/**
	 * The Loader that will load the desired SoundData.
	 **/
	protected Loader soundLoader;
	
	/**
	 * The SoundData this Intro will utilize.
	 **/
	protected SoundData sound;
	
	/*************************************************************************
	 * Creates an ImageAudioIntro that uses the Image and SoundData retrieved 
	 * from the indicated system locations.
	 * 
	 * @param imageFilepath
	 * 			  The system location of the source image.
	 * @param soundFilepath
	 * 			  The system location of the source audio.
	 *************************************************************************/
	public ImageAudioIntro(String imageFilepath, String soundFilepath)
	{
		this(new SystemLoader(imageFilepath), new SystemLoader(soundFilepath));
	}

	/*************************************************************************
	 * Creates an ImageAudioIntro that uses the Image and SoundData retrieved 
	 * from the supplied Loaders.
	 * 
	 * @param imageFilepath
	 * 			  The loader that accesses the desired source image.
	 * @param soundFilepath
	 * 			  The loader that accesses the desired source sound.
	 *************************************************************************/
	public ImageAudioIntro(Loader imageLoader, Loader soundLoader)
	{
		super(imageLoader);
		this.soundLoader = soundLoader;
	}

	/*************************************************************************
	 * Loads both the desired Image and the desired SoundData this Intro will 
	 * use.
	 ***************************************************************/ @Override
	public void create()
	{
		super.create();
		sound = new SoundData(soundLoader);
	}

	/*************************************************************************
	 * Destroys both the Image and SoundData used by this Intro.
	 ***************************************************************/ @Override
	public void destroy()
	{
		super.destroy();
		sound.destroy();
	}
}