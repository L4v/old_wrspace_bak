package org.jrabbit.base.graphics.skins.text;

import org.jrabbit.base.graphics.font.Font;
import org.jrabbit.base.graphics.font.FontPointer;
import org.jrabbit.base.graphics.skins.Skin;

/*****************************************************************************
 * A TextSkin is a Skin that uses one of the currently loaded Fonts to render a
 * dynamic string of text. The text displayed can be formatted into paragraphs
 * (by default this is turned on).
 * 
 * It should be noted that unlike other skins, TextSkin is rendered from its top
 * left corner, not from its center.
 * 
 * NOTE: Rendering text is expensive, and very little is done to accelerate font
 * rendering in this class. If the Font used to render text is accelerated, this
 * Skin will benefit, but beyond that this class is relatively slow for large
 * amounts of text. On the plus side, there is not much additional overhead for
 * rendering changing text.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class TextSkin implements Skin
{
	/**
	 * The base text to render.
	 **/
	protected String text;

	/**
	 * The text that has been passed through the Font for formatting. This is
	 * the text that is actually drawn.
	 **/
	protected String[] formattedText;

	/**
	 * Whether or not to push the text into paragraph format. Even if this is
	 * set to false, the base text will be broken by line breaks.
	 **/
	protected boolean format;
	
	/**
	 * If formatting is enabled, this marks whether or not to indent the lines
	 * of text that are retrieved.
	 **/
	protected boolean indent;

	/**
	 * If formatting is enabled, this indicated the maximum width of the
	 * paragraph.
	 **/
	protected int formatWidth;

	/**
	 * The base width of the text being rendered.
	 **/
	protected float width;

	/**
	 * The base height of the text being rendered.
	 **/
	protected float height;

	/**
	 * Whether or not the TextSkin needs to reformat its text. This may happen
	 * for a variety of reasons - the text to render may change; the font may
	 * change, the maximum width of the formatted paragraph may change, etc.
	 **/
	protected boolean reformat;

	/**
	 * The FontPointer used to obtain and set the Font used to render text.
	 **/
	protected FontPointer fontPointer;

	/*************************************************************************
	 * Creates a TextSkin that uses the default font to render the supplied
	 * text.
	 * 
	 * @param text
	 *            The text to render.
	 *************************************************************************/
	public TextSkin(String text)
	{
		this(text, null);
	}

	/*************************************************************************
	 * Creates a TextSkin that uses the indicated font to render the supplied
	 * text.
	 * 
	 * NOTE: Supplying a null reference for the font will result in the default
	 * font being used.
	 * 
	 * @param text
	 *            The text to render.
	 * @param font
	 *            The identifier of the Font to render.
	 *************************************************************************/
	public TextSkin(String text, String font)
	{
		this.text = text;
		fontPointer = new FontPointer(font);
		defaultFormattting();
	}

	/*************************************************************************
	 * This method is used to obtain the initial formatting the TextSkin will
	 * use. Placing this into a method allows extensions of TextSkin to have a
	 * different default formatting.
	 *************************************************************************/
	protected void defaultFormattting()
	{
		formatWidth = 512;
		format = false;
		indent = false;
		reformat = true;
		checkFormat();
	}

	/*************************************************************************
	 * Obtains a reference to the active Font.
	 * 
	 * @return The Font currently being used to render text.
	 *************************************************************************/
	public Font font()
	{
		return fontPointer.object();
	}

	/*************************************************************************
	 * Changes the font currently being used.
	 * 
	 * @param font
	 *            The identifier of the new Font to use.
	 *************************************************************************/
	public void setFont(String font)
	{
		if (!font.equals(fontPointer.reference()))
		{
			fontPointer.setReference(font);
			reformat = true;
		}
	}

	/*************************************************************************
	 * Resets the current font to use the default font at all times.
	 *************************************************************************/
	public void setToDefaultFont()
	{
		if (fontPointer.reference() != null)
		{
			fontPointer.setReference(null);
			reformat = true;
		}
	}

	/*************************************************************************
	 * Accesses the current String of text.
	 * 
	 * @return The base text being rendered.
	 *************************************************************************/
	public String text()
	{
		return text;
	}

	/*************************************************************************
	 * Changes the active text.
	 * 
	 * @param text
	 *            The new text to render.
	 *************************************************************************/
	public void setText(String text)
	{
		if (!this.text.equals(text))
		{
			this.text = text;
			reformat = true;
		}
	}

	/*************************************************************************
	 * Accesses the processed, formatted Strings of text that are actually
	 * rendered.
	 * 
	 * @return The formatted text in array form. Each element in the array is a
	 *         separate line of text. Formatting is managed this way to simplify
	 *         rendering.
	 *************************************************************************/
	public String[] formattedText()
	{
		return formattedText;
	}

	/*************************************************************************
	 * Redefines the maximum paragraph width for formatted text.
	 * 
	 * @param width
	 *            The new width for formatting.
	 *************************************************************************/
	public void setFormatWidth(int width)
	{
		if (width > formatWidth || this.width > width)
			reformat = format;
		formatWidth = width;
	}

	/*************************************************************************
	 * Learns the current formatting width.
	 * 
	 * @return The maximum width of formatted paragraphs.
	 *************************************************************************/
	public int formatWidth()
	{
		return formatWidth;
	}

	/*************************************************************************
	 * Sets whether or not to format the base text into paragraphs.
	 * 
	 * NOTE: Even if this is turned off, the base text will still be split by
	 * line breaks ('\n' or '\r').
	 * 
	 * @param format
	 *            Whether or not formatting should be enabled.
	 *************************************************************************/
	public void enableFormatting(boolean format)
	{
		if (this.format != format)
		{
			this.format = format;
			reformat = true;
		}
	}

	/*************************************************************************
	 * Learns if formatting is enabled.
	 * 
	 * @return True if text should be formatted into paragraphs, false if
	 *         formatting occurs simply by line breaks.
	 *************************************************************************/
	public boolean formatting()
	{
		return format;
	}

	/*************************************************************************
	 * Sets whether or not to indent formatted text.
	 * 
	 * @param indent
	 *            Whether or not indenting should be enabled.
	 *************************************************************************/
	public void enableIndent(boolean indent)
	{
		if (this.indent != indent)
		{
			this.indent = indent;
			reformat = format;
		}
	}

	/*************************************************************************
	 * Learns if indenting is enabled.
	 * 
	 * @return True if text should be indented when formatted into paragraphs.
	 *************************************************************************/
	public boolean indenting()
	{
		return indent;
	}

	/*************************************************************************
	 * Makes sure that the text to render is properly formatted. If the skin has
	 * already been formatted and no changes have been made, this does nothing.
	 * However, if the formatted text is not up-to-date, then this recalculates
	 * the text to render.
	 *************************************************************************/
	public void checkFormat()
	{
		if (reformat)
			reformat();
	}

	/*************************************************************************
	 * This method forces a reformat of the text to render.
	 * 
	 * NOTE: The reformat happens immediately, it does not wait for
	 *************************************************************************/
	public void reformat()
	{
		format(font());
		reformat = false;
	}
	
	/*************************************************************************
	 * Flags the TextSprite that it needs to reformat on the next render.
	 *************************************************************************/
	public void flagReformat()
	{
		reformat = true;
	}
	
	/*************************************************************************
	 * This calculates the list of Strings to use for rendering. Additionally, 
	 * this calculates the rendered dimensions of the final lines of text.
	 * 
	 * @param font
	 * 			  The Font that should do the formatting calculations.
	 *************************************************************************/
	protected void format(Font font)
	{
		formattedText = format ? font.toParagraphs(text, formatWidth, indent) : 
				font.toLines(text);
		width = 0;
		for (String s : formattedText)
			width = Math.max(font.widthOf(s), width);
		height = font.heightOf(formattedText);
	}

	/*************************************************************************
	 * Learns the maximum width of all formatted lines of text.
	 * 
	 * @return The width of the longest line of text, in pixels.
	 *************************************************************************/
	public float textWidth()
	{
		return width;
	}

	/*************************************************************************
	 * Learns the height of the formatted text.
	 * 
	 * @return The total height of all rendered lines of text, in pixels.
	 *************************************************************************/
	public float textHeight()
	{
		return height;
	}

	/*************************************************************************
	 * Learns about the dimensions of the TextSkin. Since this is rendered from
	 * its top left corner, and we need to take rotation into account, we need
	 * to return 2x the width of the text.
	 * 
	 * @return The width the TextSkin will fit within when rotated in any 
	 * direction.
	 ***************************************************************/ @Override
	public float width()
	{
		return width * 2;
	}

	/*************************************************************************
	 * Learns about the dimensions of the TextSkin. Since this is rendered from
	 * its top left corner, and we need to take rotation into account, we need
	 * to return 2x the height of the text.
	 * 
	 * @return The height the TextSkin will fit within when rotated in any 
	 * direction.
	 ***************************************************************/ @Override
	public float height()
	{
		return height * 2;
	}

	/*************************************************************************
	 * First, checks to see if the TextSkin needs to update its formatting; then
	 * renders the formatted text.
	 ***************************************************************/ @Override
	public void render()
	{
		checkFormat();
		font().render(formattedText);
	}
}