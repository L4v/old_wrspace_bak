package org.jrabbit.base.sound;

import org.jrabbit.base.data.cache.CachePointer;
import org.jrabbit.base.managers.Resources;

/*****************************************************************************
 * An SoundPointer is a CachePointer designed to retrieve SoundData from the
 * default SoundCache in Resources.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SoundPointer extends CachePointer<SoundData>
{
	/*************************************************************************
	 * Creates an SoundPointer that will retrieve the indicated SoundData.
	 * 
	 * @param reference
	 *            A reference that matches that of the SoundData to retrieve 
	 *            from the Cache.
	 *************************************************************************/
	public SoundPointer(String filepath)
	{
		super(filepath);
	}

	/*************************************************************************
	 * Accesses the SoundCache and retrieves the associated SoundData.
	 * 
	 * @return The SoundData in the Cache that has a matching reference with
	 *         this one.
	 *************************************************************************/
	public SoundData retrieve()
	{
		return Resources.sounds().get(this);
	}
}