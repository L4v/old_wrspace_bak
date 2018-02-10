package org.jrabbit.standard.game.sound;

import org.jrabbit.base.sound.ControllableAudio;
import org.jrabbit.base.sound.SoundData;
import org.jrabbit.base.sound.SoundPointer;
import org.jrabbit.standard.game.managers.GameManager;

/*****************************************************************************
 * A Sound is a lightweight object that utilizes SoundData in the Cache to play
 * audio.
 * 
 * A Sound also has controllable pitch and volume settings.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Sound implements ControllableAudio
{
	/**
	 * This is used to access the SoundCache and retrieve the desired SoundData
	 * for playback.
	 **/
	protected SoundPointer soundPointer;

	/**
	 * This is a reference to the Channel this Sound is categorized under. The
	 * volume and pitch settings of this Channel affect playback.
	 **/
	protected Channel channel;

	/**
	 * The volume setting.
	 **/
	protected float volume;

	/**
	 * The pitch setting.
	 **/
	protected float pitch;
	
	/*************************************************************************
	 * Creates a Sound that attempts to play the SoundData indicated by the 
	 * supplied reference.
	 * 
	 * NOTE: This constructor sets the Channel to the default (represented by
	 * an empty String).
	 * 
	 * @param reference
	 * 			  The identifier matching the target SoundData.
	 *************************************************************************/
	public Sound(String reference)
	{
		this(reference, "");
	}
	
	/*************************************************************************
	 * Creates a Sound that attempts to play the SoundData indicated by the 
	 * supplied reference, and that categorizes itself under the 
	 * indicated Channel.
	 * 
	 * @param reference
	 * 			  The identifier matching the target SoundData.
	 * @param channel
	 * 			  The identifier of the channel to use.
	 *************************************************************************/
	public Sound(String reference, String channel)
	{
		soundPointer = new SoundPointer(reference);
		volume = 1f;
		pitch = 1f;
		setChannel(channel);
	}
	
	/*************************************************************************
	 * Accesses this Sound's audio settings.
	 * 
	 * @return The current volume setting.
	 ***************************************************************/ @Override
	public float volume() { return volume; }
	
	/*************************************************************************
	 * Redefines this Sound's volume setting.
	 * 
	 * NOTE: This method only takes effect if the supplied value isn't negative.
	 * 
	 * @param volume
	 * 			  The new setting for volume.
	 ***************************************************************/ @Override
	public void setVolume(float volume)
	{
		if(volume >= 0)
			this.volume = volume;
	}
	
	/*************************************************************************
	 * Accesses this Sound's audio settings.
	 * 
	 * @return The current pitch setting.
	 ***************************************************************/ @Override
	public float pitch() { return pitch; }
	
	/*************************************************************************
	 * Redefines this Sound's pitch setting.
	 * 
	 * NOTE: This method only takes effect if the supplied value isn't negative.
	 * 
	 * @param pitch
	 * 			  The new setting for pitch.
	 ***************************************************************/ @Override
	public void setPitch(float pitch)
	{
		if(pitch >= 0)
			this.pitch = pitch;
	}
	
	/*************************************************************************
	 * Changes the Channel this Sound is categorized under. This operation will
	 * only succeed if (A) the indicated Channel exists / is not null, and (B)
	 * the indicated Channel is not the same as the one currently active.
	 * 
	 * @param reference
	 * 			  The identifier of the Channel in the SoundBoard to use.
	 * 
	 * @return True if the operation succeeded, false if not.
	 *************************************************************************/
	public boolean setChannel(String reference)
	{
		return setChannel(GameManager.soundBoard().get(reference));
	}
	
	/*************************************************************************
	 * Changes the Channel this Sound is categorized under. This operation will
	 * only succeed if (A) the indicated Channel exists / is not null, and (B)
	 * the indicated Channel is not the same as the one currently active.
	 * 
	 * @param channel
	 * 			  The new Channel.
	 * 
	 * @return True if the operation succeeded, false if not.
	 *************************************************************************/
	public boolean setChannel(Channel channel)
	{
		if(channel == null || channel.equals(this.channel))
			return false;
		this.channel = channel;
		return true;
	}
	
	/*************************************************************************
	 * Accesses the SoundData this Sound uses to play audio.
	 * 
	 * @return The SoundData retrieved from the Cache.
	 *************************************************************************/
	public SoundData data()
	{
		return soundPointer.object();
	}
	
	/*************************************************************************
	 * Plays the sound once, using the in-place sound and pitch settings.
	 *************************************************************************/
	public void playOnce()
	{
		playSound(pitch, volume, false);
	}
	
	/*************************************************************************
	 * Plays the sound once, overriding the current pitch and volume settings.
	 * 
	 * @param pitch
	 * 			  The pitch setting to play the sound at.
	 * @param volume
	 * 			  The volume setting to play the sound at.
	 *************************************************************************/
	public void playOnce(float pitch, float volume)
	{
		playSound(pitch, volume, false);
	}
	
	/*************************************************************************
	 * Begins continuously looping the sound, using the in-place sound and pitch
	 * settings.
	 *************************************************************************/
	public void loop()
	{
		playSound(pitch, volume, true);
	}
	
	/*************************************************************************
	 * Begins continuously looping the sound, overriding the current pitch and 
	 * volume settings.
	 * 
	 * @param pitch
	 * 			  The pitch setting to loop the sound at.
	 * @param volume
	 * 			  The volume setting to loop the sound at.
	 *************************************************************************/
	public void loop(float pitch, float volume)
	{
		playSound(pitch, volume, true);
	}
	
	/*************************************************************************
	 * Plays the sound with the indicated settings.
	 * 
	 * @param pitch
	 * 			  The pitch setting to play the sound at.
	 * @param volume
	 * 			  The volume setting to play the sound at.
	 * @param loop
	 * 			  Whether or not the sound should loop continuously.
	 *************************************************************************/
	public void playSound(float pitch, float volume, boolean loop)
	{
		SoundBoard s = GameManager.soundBoard();
		data().audio().playAsSoundEffect(s.pitch() * channel.pitch() * pitch, 
				s.volume() * channel.volume() * volume, loop);
	}
}