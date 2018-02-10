package org.jrabbit.base.sound;

import java.io.IOException;

import org.jrabbit.base.data.loading.Loader;
import org.jrabbit.base.data.loading.SystemLoader;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;

/*****************************************************************************
 * Music is a convenience class that provides simple methods for managing a
 * single, streaming loop of audio, with controls for looping, volume, and
 * pitch.
 * 
 * Basically, this class simplifies playing background music. For the most part,
 * it offers methods that simply call other methods in Slick-Util's sound API,
 * but it provides a single, simple, unified API.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Music
{
	/**
	 * The currently loaded/playing music.
	 **/
	private static Audio music;

	/**
	 * The current volume setting.
	 **/
	private static float volume = 1f;

	/**
	 * The current pitch setting.
	 **/
	private static float pitch = 1f;
	
	/**
	 * Whether or not to loop the music.
	 **/
	private static boolean looping;

	/*************************************************************************
	 * Loops the indicated music file.
	 * 
	 * @param filepath
	 *            The resource location at which to find the desired sound file.
	 *************************************************************************/
	public static void loop(String filepath)
	{
		play(new SystemLoader(filepath), true);
	}

	/*************************************************************************
	 * Plays the indicated music file.
	 * 
	 * @param filepath
	 *            The resource location at which to find the desired sound file.
	 *************************************************************************/
	public static void play(String filepath)
	{
		play(new SystemLoader(filepath), false);
	}

	/*************************************************************************
	 * Plays or loops the indicated music file.
	 * 
	 * @param loader
	 *            The Loader pointing to the desired sound file.
	 * @param loop
	 * 			  Whether or not to loop the music file.
	 **************************************************************************/
	public static void play(Loader loader, boolean loop)
	{
		if(Music.music != null)
			Music.music.stop();
		try
		{
			music = AudioLoader.getStreamingAudio(loader.type(), 
					loader.url());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		play(loop);
	}

	/*************************************************************************
	 * Plays or loops the current music (if one has been already set). If it's 
	 * already playing, this restarts it.
	 * 
	 * @param loop
	 * 			  Whether or not to loop the music.
	 *************************************************************************/
	public static void play(boolean loop)
	{
		looping = loop;
		if(music != null)
		{
			music.playAsMusic(1f, 1f, loop);
			SoundStore.get().setCurrentMusicVolume(volume);
			SoundStore.get().setMusicPitch(pitch);
		}
	}

	/*************************************************************************
	 * Plays or loops the current music (if one has been already set). If it's 
	 * already playing, this restarts it.
	 * 
	 * NOTE: The music is looped based on whether or not the last played music
	 * was looping.
	 *************************************************************************/
	public static void play()
	{
		if(music != null)
		{
			music.playAsMusic(1f, 1f, looping);
			SoundStore.get().setCurrentMusicVolume(volume);
			SoundStore.get().setMusicPitch(pitch);
		}
	}

	/*************************************************************************
	 * Stops the current music playback, but remembers which music was playing.
	 *************************************************************************/
	public static void stop()
	{
		if(music != null)
			music.stop();
	}

	/*************************************************************************
	 * Sets the volume of the currently playing music.
	 * 
	 * @param volume 
	 * 			  The new volume to set.
	 *************************************************************************/
	public static void setVolume(float volume)
	{
		Music.volume = volume;
		SoundStore.get().setCurrentMusicVolume(volume);
	}

	/*************************************************************************
	 * Learns the current music volume.
	 * 
	 * @return The proportional value for how loud the music should be, compared
	 * to the source file. 1f = base volume, 2f = twice as loud, etc.
	 *************************************************************************/
	public static float volume()
	{
		return volume;
	}

	/*************************************************************************
	 * Sets the pitch of the currently playing music.
	 * 
	 * @param pitch 
	 * 			  The new pitch to set.
	 *************************************************************************/
	public static void setPitch(float pitch)
	{
		Music.pitch = pitch;
		SoundStore.get().setMusicPitch(pitch);
	}

	/*************************************************************************
	 * Learns the current music pitch.
	 * 
	 * @return The value for how much the pitch should be multiplied. 1f = base 
	 * pitch, 2f = 2x frequency, etc.
	 *************************************************************************/
	public static float pitch()
	{
		return pitch;
	}
}