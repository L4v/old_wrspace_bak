package org.jrabbit.base.graphics.font;

import java.awt.Font;

/*****************************************************************************
 * A convenience class for manipulating Java AWT Fonts.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class FontProfile
{
	/**
	 * If the profile should apply a Bold typeface.
	 **/
	protected boolean bold;

	/**
	 * If the profile should apply an Italic typeface.
	 **/
	protected boolean italic;

	/**
	 * The font size to apply.
	 **/
	protected int size;

	/*************************************************************************
	 * Creates a default FontProfile that will simply apply a font size of 20.
	 *************************************************************************/
	public FontProfile()
	{
		this(false, false, 20);
	}

	/*************************************************************************
	 * Creates a FontProfile that will apply a specified font size.
	 * 
	 * @param size
	 *            The size to apply on fonts.
	 *************************************************************************/
	public FontProfile(int size)
	{
		this(false, false, size);
	}

	/*************************************************************************
	 * Creates a FontProfile with the indicated settings.
	 * 
	 * @param bold
	 *            Whether the font should be bold or not.
	 * @param italic
	 *            Whether the font should be italicized or not
	 * @param size
	 *            The size to apply to the font.
	 *************************************************************************/
	public FontProfile(boolean bold, boolean italic, int size)
	{
		this.bold = bold;
		this.italic = italic;
		this.size = size;
	}

	/*************************************************************************
	 * Accesses FontProfile settings.
	 * 
	 * @return The size the FontProfile will apply.
	 *************************************************************************/
	public int size()
	{
		return size;
	}

	/*************************************************************************
	 * Accesses FontProfile settings.
	 * 
	 * @return Whether or not the FontProfile is applying bold typeface.
	 *************************************************************************/
	public boolean bold()
	{
		return bold;
	}

	/*************************************************************************
	 * Accesses FontProfile settings.
	 * 
	 * @return Whether or not the FontProfile is applying italic typeface.
	 *************************************************************************/
	public boolean italic()
	{
		return italic;
	}

	/*************************************************************************
	 * Creates a new Font from the supplied Font, applying FontProfile settings.
	 * 
	 * @param font
	 *            The base font.
	 * 
	 * @return A new font, as indicated by this FontProfile.
	 *************************************************************************/
	public Font derive(Font font)
	{
		return (italic ? (bold ? font.deriveFont(Font.ITALIC | Font.BOLD)
				: font.deriveFont(Font.ITALIC)) : (bold ? font
				.deriveFont(Font.BOLD) : font.deriveFont(Font.PLAIN)))
				.deriveFont(size);
	}
}
