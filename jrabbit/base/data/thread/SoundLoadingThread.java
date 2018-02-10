package org.jrabbit.base.data.thread;

import org.jrabbit.base.sound.SoundData;

/*****************************************************************************
 * A class that provides an easy way to load Sounds in a separate Thread. This
 * is bound by the same problems as with a WatchableGLThread.
 * 
 * NOTE: See ImageLoadingThread's caveats for a brief discussion on the use of
 * this class.
 * 
 * @author Chris Molini
 *****************************************************************************/
public final class SoundLoadingThread extends WatchableGLThread
{
	/**
	 * The supplied list of filepaths that are used to load sounds.
	 **/
	private String[] filepaths;

	/**
	 * The list of constructed Sounds.
	 **/
	private SoundData[] sounds;

	/*************************************************************************
	 * Instantiates a Thread that will load sounds from the list of filepaths.
	 * 
	 * NOTE: Each of these sounds will have its appropriate filepath as a
	 * reference.
	 * 
	 * @param filepaths
	 *            The locations in the file system of the sounds to load.
	 *************************************************************************/
	public SoundLoadingThread(String[] filepaths)
	{
		this.filepaths = filepaths;
		sounds = new SoundData[filepaths.length];
		total = filepaths.length;
	}

	/*************************************************************************
	 * Creates the desired sounds, incrementing progress as it goes.
	 ***************************************************************/ @Override
	protected void act()
	{
		for (int i = 0; i < filepaths.length; i++)
		{
			sounds[i] = new SoundData(filepaths[i]);
			progress++;
		}
	}

	/*************************************************************************
	 * Accesses a reference to the array of sounds in the loading Thread. This
	 * shouldn't be used until loading is complete.
	 * 
	 * @return The list of created sound data.
	 *************************************************************************/
	public SoundData[] sounds()
	{
		if (complete())
			return sounds;
		return null;
	}
}
