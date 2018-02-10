package org.jrabbit.standard.game.sound;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.data.structures.base.KeyedContainer;
import org.jrabbit.base.sound.ControllableAudio;
import org.newdawn.slick.openal.SoundStore;

/*****************************************************************************
 * A SoundBoard maintains volume and pitch settings for all Channels. Basically,
 * it is a simplistic audio equalizer.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SoundBoard implements KeyedContainer<Channel>, ControllableAudio,
		Updateable
{
	/**
	 * The list of all contained Channels.
	 **/
	protected LinkedHashMap<String, Channel> channels;
	
	/**
	 * The master volume setting.
	 **/
	protected float volume;
	
	/**
	 * The master pitch setting.
	 **/
	protected float pitch;
	
	/*************************************************************************
	 * Creates the default SoundBoard. Volume and pitch are set to 1, and three
	 * channels are created - the default, one for ambient sounds, and one for
	 * general sound effects.
	 *************************************************************************/
	public SoundBoard()
	{
		this(1f, 1f, "Ambient", "Effects");
	}
	
	/*************************************************************************
	 * Creates a SoundBoard.
	 * 
	 * @param volume
	 * 			  The initial master volume setting.
	 * @param pitch
	 * 			  The initial master pitch setting.
	 * @param startingChannels
	 * 			  The list of channels to create in addition to the default.
	 *************************************************************************/
	public SoundBoard(float volume, float pitch, String... startingChannels)
	{
		this.volume = volume;
		this.pitch = pitch;
		channels = new LinkedHashMap<String, Channel>();
		add("");
		for(String channel : startingChannels)
			add(channel);
	}

	/*************************************************************************
	 * Updates the Slick-Util SoundStore so that playback will advance.
	 * 
	 * @param delta
	 * 			  The number of microseconds that have passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		SoundStore.get().poll(0);
	}

	/*************************************************************************
	 * Accesses this SoundBoard's audio settings.
	 * 
	 * @return The current master volume setting.
	 ***************************************************************/ @Override
	public float volume() { return volume; }

	/*************************************************************************
	 * Redefines the SoundBoard's master volume setting.
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
	 * Accesses this SoundBoard's audio settings.
	 * 
	 * @return The current master pitch setting.
	 ***************************************************************/ @Override
	public float pitch() { return pitch; }

	/*************************************************************************
	 * Redefines the SoundBoard's master pitch setting.
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
	 * Creates an adds a new Channel with the indicated reference.
	 * 
	 * @return True if the add succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean add(String name)
	{
		return add(create(name));
	}

	/*************************************************************************
	 * Attempts to create and add Channels for every indicated reference.
	 * 
	 * @param references
	 * 			  The references that indicate which Channels to create.
	 ***************************************************************/ @Override
	public void add(String... references)
	{
		for(String reference : references)
			add(reference);
	}

	/*************************************************************************
	 * Adds the indicated Channel to the SoundBoard.
	 * 
	 * NOTE: This will not work if a Channel with an identical reference is
	 * already in the list.
	 * 
	 * @param channel
	 * 			  The Channel to add.
	 * 
	 * @return True if the add succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean add(Channel channel)
	{
		if(contains(channel.reference()))
			return false;
		channels.put(channel.reference(), channel);
		return true;
	}

	/*************************************************************************
	 * Attempts to add the indicated Channels.
	 * 
	 * @param channels
	 * 			  The channels to add to the SoundBoard.
	 ***************************************************************/ @Override
	public void add(Channel... channels)
	{
		for(Channel channel : channels)
			add(channel);
	}

	/*************************************************************************
	 * Attempts to remove the Channel with a matching reference.
	 * 
	 * @param reference
	 * 			  The identifier of the Channel to remove.
	 * 
	 * @return The Channel removed (null if the operation failed).
	 ***************************************************************/ @Override
	public Channel remove(String reference)
	{
		return channels.remove(reference);
	}

	/*************************************************************************
	 * Attempts to remove and return all Channels indicated.
	 * 
	 * @param references
	 * 			  The list of identifiers that correspond to the Channels to 
	 * 			  remove.
	 * 
	 * @return A Collection of all Channels that were removed.
	 ***************************************************************/ @Override
	public Collection<Channel> remove(String... references)
	{
		LinkedList<Channel> removed = new LinkedList<Channel>();
		for(String reference : references)
		{
			Channel channel = remove(reference);
			if(channel != null)
				removed.add(channel);
		}
		return removed;
	}

	/*************************************************************************
	 * Attempts to remove the indicated Channel from the SoundBoard.
	 * 
	 * @param channel
	 * 			  The channel to remove.
	 * 
	 * @return True if the removal succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean remove(Channel channel)
	{
		if(channels.get(channel.reference()) != null)
		{
			remove(channel.reference());
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Attempts to remove all indicated Channels.
	 * 
	 * @param channels
	 * 			  The list of Channels to remove.
	 ***************************************************************/ @Override
	public void remove(Channel... channels)
	{
		for(Channel channel : channels)
			remove(channel);
	}

	/*************************************************************************
	 * Creates a new Channel.
	 * 
	 * @param reference
	 * 			  The identifier of the Channel to create.
	 * 
	 * @return A new Channel with the indicated identifier.
	 ***************************************************************/ @Override
	public Channel create(String reference)
	{
		return new Channel(reference);
	}

	/*************************************************************************
	 * Accesses the Channel keyed to the indicated reference.
	 * 
	 * @param reference
	 * 			  The String that is keyed to the Channel.
	 * 
	 * @return The Channel mapped to the reference, or null if no Channel is 
	 * 		   keyed to it.
	 ***************************************************************/ @Override
	public Channel get(String reference)
	{
		return channels.get(reference);
	}

	/*************************************************************************
	 * Determines whether or not a particular Channel is in the list.
	 * 
	 * @param channel
	 * 			  The Channel to find.
	 * 
	 * @return True if the Channel is in the list, false if not.
	 ***************************************************************/ @Override
	public boolean contains(Channel channel)
	{
		return channels.containsValue(channel);
	}

	/*************************************************************************
	 * Determines whether or not a particular Channel is in the list.
	 * 
	 * @param reference
	 * 			  The identifier of the Channel to find.
	 * 
	 * @return True if a Channel with an identical indicator is in the list,
	 * 		   false if not.
	 ***************************************************************/ @Override
	public boolean contains(String reference)
	{
		return channels.containsKey(reference);
	}

	/*************************************************************************
	 * Learns the size of the Channel list.
	 * 
	 * @return The number of Channels currently in the SoundBoard. 
	 ***************************************************************/ @Override
	public int size()
	{
		return channels.size();
	}

	/*************************************************************************
	 * Clears all channels.
	 ***************************************************************/ @Override
	public void clear()
	{
		channels.clear();
	}

	/*************************************************************************
	 * Accesses the list of Channels.
	 * 
	 * @return An Iterator that moves through all Channels in order of addition.
	 ***************************************************************/ @Override
	public Iterator<Channel> iterator()
	{
		return channels.values().iterator();
	}
}