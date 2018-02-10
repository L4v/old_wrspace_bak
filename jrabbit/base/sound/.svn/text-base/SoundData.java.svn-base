package org.jrabbit.base.sound;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jrabbit.base.data.Destroyable;
import org.jrabbit.base.data.Referenced;
import org.jrabbit.base.data.loading.*;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

/*****************************************************************************
 * SoundData represents audio data loaded into the game. Basically, it's a
 * wrapper class that manages a Slick-Util Audio instance.
 * 
 * A SoundData object can have multiple playbacks running simultaneously, so it
 * can be used as a general resource. Thus, it's a logical object to place in a
 * Cache.
 * 
 * NOTE: SoundData is not streamed - it is loaded all at once. This can cause a
 * noticeable pause if it occurs in-game.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SoundData implements Referenced, Destroyable
{
	/*************************************************************************
	 * Loads an Audio object (from Slick-Util) from the indicated Loader.
	 * 
	 * @param loader
	 *            The Loader that accesses the desired source file.
	 * 
	 * @return The Audio created from the indicated sound file.
	 *************************************************************************/
	public static Audio loadData(Loader loader)
	{
		Audio audio = null;
		try
		{
			InputStream dataStream = loader.stream();
			audio = AudioLoader.getAudio(loader.type(), dataStream);
			dataStream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return audio;
	}

	/**
	 * The Audio used to manage sound data.
	 **/
	private Audio audio;
	
	/**
	 * The String that identifies this SoundData.
	 **/
	private String reference;

	/*************************************************************************
	 * Loads the sound file at the indicated resource location into a SoundData.
	 * 
	 * The indicated filepath is used as the reference.
	 * 
	 * @param filepath
	 * 			  The location of the source audio.
	 *************************************************************************/
	public SoundData(String filepath)
	{
		this(filepath, new SystemLoader(filepath));
	}

	/*************************************************************************
	 * Loads the sound file at the indicated resource location into a SoundData
	 * and sets the identifier.
	 * 
	 * @param reference
	 * 			  The String to identify the SoundData.
	 * @param filepath
	 * 			  The location of the source audio.
	 *************************************************************************/
	public SoundData(String reference, String filepath)
	{
		this(reference, new SystemLoader(filepath));
	}

	/*************************************************************************
	 * Loads the sound file at the indicated resource location into a SoundData.
	 * 
	 * NOTE: The created SoundData is streamed.
	 * 
	 * @param url
	 * 			  The URL that points to the desired source file.
	 *************************************************************************/
	public SoundData(URL url)
	{
		this(url.getPath(), new URLLoader(url));
	}

	/*************************************************************************
	 * Loads the sound file at the indicated resource location into a SoundData
	 * and sets the identifier.
	 * 
	 * NOTE: The created SoundData is streamed.
	 * 
	 * @param reference
	 * 			  The String to identify the SoundData.
	 * @param url
	 * 			  The URL that points to the desired source file.
	 *************************************************************************/
	public SoundData(String reference, URL url)
	{
		this(reference, new URLLoader(url));
	}

	/*************************************************************************
	 * Loads the sound file from the indicated resource into a SoundData.
	 * 
	 * @param loader
	 * 			  The Loader that accesses the desired source file.
	 *************************************************************************/
	public SoundData(Loader loader)
	{
		this(loader.path(), loader);
	}

	/*************************************************************************
	 * Loads the sound file from the indicated resource into a SoundData and
	 * sets the identifier.
	 * 
	 * @param reference
	 * 			  The String to identify the SoundData.
	 * @param loader
	 * 			  The Loader that accesses the desired source file.
	 *************************************************************************/
	public SoundData(String reference, Loader loader)
	{
		this.reference = reference;
		audio = loadData(loader);
	}

	/*************************************************************************
	 * Gets the reference associated with this SoundData.
	 * 
	 * @return The string that identifies the SoundData.
	 ***************************************************************/ @Override
	public String reference()
	{
		return reference;
	}

	/*************************************************************************
	 * Accesses the Slick-Util Audio object contained in this SoundData.
	 * 
	 * @return The Audio that contains and controls the loaded sound.
	 *************************************************************************/
	public Audio audio()
	{
		return audio;
	}

	/*************************************************************************
	 * Deletes the sound data associated with this object.
	 ***************************************************************/ @Override
	public void destroy()
	{
		if(audio != null)
		{
			audio.stop();
			AL10.alDeleteBuffers(audio.getBufferID());
			audio = null;
		}
	}
}