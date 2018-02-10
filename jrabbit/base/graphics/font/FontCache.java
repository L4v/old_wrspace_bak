package org.jrabbit.base.graphics.font;

import org.jrabbit.base.data.Factory;
import org.jrabbit.base.data.cache.GroupedCache;
import org.jrabbit.base.graphics.font.renderer.UnicodeRenderer;

/*****************************************************************************
 * Rather obviously, a FontCache is a GroupedCache that contains Fonts.
 * 
 * FontCache has one additional bit of functionality, however. It has a
 * "default" font that it maintains a direct reference to. Whenever a font is
 * requested that is not in the Cache, it automatically returns the default.
 * Though a default font is automatically in place, any font in the Cache can be
 * set as the default.
 * 
 * Initially, the Cache is full and the default font is null. It's highly
 * recommended to set the default font on startup.
 * 
 * NOTE: It's really not recommended to use the "dynamic creation" capabilities
 * of a Cache when dealing with Fonts. There are reasons:
 * 
 * 		1: 	Fonts are heavy-duty, and they take a long time to load. It's much 
 * 			better to the user if font loading takes place out-of-game. 
 * 		2: 	The default FontRenderers work best if more than one String is used
 * 			to instantiate them. For example, an AngelCodeRenderer requires both
 * 			a path to a font file and a path to an image.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class FontCache extends GroupedCache<Font>
{
	/**
	 * The reference to the default font.
	 **/
	private Font defaultFont;

	/*************************************************************************
	 * Creates a default, empty FontCache. The default font reference is null.
	 *************************************************************************/
	public FontCache()
	{
		super(new Factory<Font>() { public Font create(String reference) {
				return reference == null ? null : new Font(reference,
						new UnicodeRenderer(reference, new FontProfile())); }});
	}

	/*************************************************************************
	 * Gets information about the default font.
	 * 
	 * @return The default font's name.
	 *************************************************************************/
	public String defaultFontName()
	{
		return defaultFont.reference();
	}

	/*************************************************************************
	 * Obtains the current default font.
	 * 
	 * @return A reference to the default font.
	 *************************************************************************/
	public Font defaultFont()
	{
		return defaultFont;
	}

	/*************************************************************************
	 * Sets the default font.
	 * 
	 * @param font
	 *            The font identifier to find the desired font.
	 * 
	 * @return Whether or not the assignment succeeded. This call fails if the
	 *         indicated font is not in the Cache.
	 *************************************************************************/
	public boolean setDefaultFont(String font)
	{
		Font newDefault = get(font);
		if (newDefault != null)
		{
			defaultFont = newDefault;
			return true;
		}
		return false;
	}
}