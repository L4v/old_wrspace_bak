package org.jrabbit.base.graphics.font;

import org.jrabbit.base.data.cache.CachePointer;
import org.jrabbit.base.managers.Resources;

/*****************************************************************************
 * Provides a quick way to access Fonts in the default FontCache.
 * 
 * A FontPointer is different from the default CachePointer because it allows
 * the reference it uses to retrieve objects to be null. Doing so will cause the
 * FontPointer to retrieve the Cache's default font.
 * 
 * There is one caveat to using the default Font - if the default Font is
 * changed (or even destroyed), then the FontPointer's refresh() must be called
 * manually, or else the reference will not update.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class FontPointer extends CachePointer<Font>
{
	/*************************************************************************
	 * Creates a FontPointer that will access the default font in the FontCache.
	 *************************************************************************/
	public FontPointer()
	{
		super(null);
	}

	/*************************************************************************
	 * Creates a FontPointer that attempts to access a specific Font.
	 * 
	 * @param reference
	 *            A reference that matches that of the Font to retrieve from
	 *            the Cache.
	 *************************************************************************/
	public FontPointer(String reference)
	{
		super(reference);
	}

	/*************************************************************************
	 * Obtains the desired Font, based upon reference.
	 ***************************************************************/ @Override
	public Font retrieve()
	{
		return (reference == null) ? Resources.fonts().defaultFont()
				: Resources.fonts().get(this);
	}
	 
	 public Font object()
	 {
		 return reference == null ? object = Resources.fonts().defaultFont() : 
			 object;
	 }
}