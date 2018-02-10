package org.jrabbit.standard.game.sound;

import org.jrabbit.base.data.Referenced;
import org.jrabbit.base.sound.ControllableAudio;

/*****************************************************************************
 * A Channel is a referenced object that maintains audio settings. It is used to
 * set general audio and pitch settings for groups of Sounds in the same 
 * category.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Channel implements Referenced, ControllableAudio
{
	/**
	 * The identifier of this Channel (basically, the name of the category that
	 * these audio settings affect).
	 **/
	protected String reference;
	
	/**
	 * The volume setting. This is a multiplier, not the number of decibels that
	 * should be emitted.
	 **/
	protected float volume;
	
	/**
	 * The pitch setting. Again, this is a multiplier.
	 **/
	protected float pitch;
	
	/*************************************************************************
	 * Creates a Channel with the indicated name. The volume and pitch settings
	 * are set to 1.
	 * 
	 * @param reference
	 * 			  The name of the Channel.
	 *************************************************************************/
	public Channel(String reference)
	{
		this(reference, 1f, 1f);
	}
	
	/*************************************************************************
	 * Creates a Channel with the indicated name and audio settings.
	 * 
	 * @param reference
	 * 			  The name of the Channel.
	 * @param volume
	 * 			  The initial volume setting.
	 * @param pitch
	 * 			  The initial pitch setting.
	 *************************************************************************/
	public Channel(String reference, float volume, float pitch)
	{
		this.reference = reference;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	/*************************************************************************
	 * Learns this Channel's name.
	 * 
	 * @return The String that identifies this Channel.
	 ***************************************************************/ @Override
	public String reference() { return reference; }
	
	/*************************************************************************
	 * Accesses this Channel's audio settings.
	 * 
	 * @return The current volume setting.
	 ***************************************************************/ @Override
	public float volume() { return volume; }
	
	/*************************************************************************
	 * Redefines this Channel's volume setting.
	 * 
	 * NOTE: This method only takes effect if the supplied value isn't negative.
	 * 
	 * @param volume
	 * 			  The new setting for volume.
	 ***************************************************************/ @Override
	public void setVolume(float volume)
	{
		this.volume = volume;
	}
	
	/*************************************************************************
	 * Accesses this Channel's audio settings.
	 * 
	 * @return The current pitch setting.
	 ***************************************************************/ @Override
	public float pitch() { return pitch; }
	
	/*************************************************************************
	 * Redefines this Channel's pitch setting.
	 * 
	 * NOTE: This method only takes effect if the supplied value isn't negative.
	 * 
	 * @param pitch
	 * 			  The new setting for pitch.
	 ***************************************************************/ @Override
	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}
}