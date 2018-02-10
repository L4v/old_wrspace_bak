package org.jrabbit.base.graphics.font;

import java.util.LinkedList;

import org.jrabbit.base.data.DataController;
import org.jrabbit.base.data.Referenced;
import org.jrabbit.base.graphics.font.renderer.FontRenderer;

/*****************************************************************************
 * A Font is an object that provides methods to render text. In addition to this
 * basic functionality, it also provides some basic means to format the text it
 * renders.
 * 
 * Most of the methods a Font uses are fairly heavy-duty; this means it's a good
 * idea to cache the results so that the Font does not need to do all the
 * calculations every iteration. This class has been designed with that in mind,
 * and various options are offered. From least advantageous to most:
 * 
 * 1: When formatting text, store the results of the formatting calculations. 2:
 * When rendering, store the rendering commands in a Display List. (Implemented
 * elsewhere.) 3: Finally, the finished formatting and rendering can be stored
 * on a texture and thereafter rendered as a textured quad. (Implemented
 * elsewhere.)
 * 
 * Unfortunately, none of these methods help deal with dynamic, rapidly changing
 * text. So, if you're using dynamic text, it's advisable to use as little of it
 * as possible.
 * 
 * Finally, it should be noted that this class isn't intended to be used as an
 * "object" - Fonts are most useful as resources on the side, accessed by other
 * objects and utilized (via a Cache).
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Font implements Referenced, DataController, FontRenderer
{
	/**
	 * The object that controls all rendering and measuring of text.
	 **/
	protected FontRenderer renderer;

	/**
	 * The name of this font.
	 **/
	protected String reference;

	/**
	 * How much of the standard line spacing (as specified by the renderer) that
	 * will divide each line of text. I.e., if you set this to 2, you get
	 * double-spaced fonts, and if you make it less than 1, the lines cluster
	 * together.
	 **/
	protected float lineSpacing;

	/**
	 * When formatting, each new paragraph is indented by this many spaces. The
	 * default is 4.
	 **/
	protected int indent;

	/*************************************************************************
	 * Creates a font with the indicated name and the supplied renderer.
	 * 
	 * @param reference
	 *            The identifier of the font.
	 * @param renderer
	 *            The renderer that will draw the text.
	 *************************************************************************/
	public Font(String reference, FontRenderer renderer)
	{
		this.reference = reference;
		this.renderer = renderer;
		lineSpacing = 1;
		indent = 4;
		create();
	}

	/*************************************************************************
	 * Accesses the name of the font. This is used to identify the Font in a
	 * Cache.
	 * 
	 * @return The name of this font.
	 ***************************************************************/ @Override
	public String reference()
	{
		return reference;
	}

	/*************************************************************************
	 * Initializes the Font's renderer.
	 ***************************************************************/ @Override
	public void create()
	{
		renderer.create();
	}

	/*************************************************************************
	 * Checks if the renderer is still valid.
	 ***************************************************************/ @Override
	public boolean valid()
	{
		return renderer.valid();
	}

	/*************************************************************************
	 * Destroys the renderer.
	 ***************************************************************/ @Override
	public void destroy()
	{
		renderer.destroy();
		renderer = null;
	}

	/*************************************************************************
	 * Gets the proportional spacing between lines of text. The actual gap
	 * between lines is this value * the base line separation (as supplied by
	 * the renderer).
	 * 
	 * @return The line spacing of this font.
	 *************************************************************************/
	public float spacing()
	{
		return lineSpacing;
	}

	/*************************************************************************
	 * Defines the proportional spacing this font will have between lines of
	 * text.
	 * 
	 * @param spacing
	 *            How much to multiply the default line spacing.
	 *************************************************************************/
	public void setSpacing(float spacing)
	{
		lineSpacing = spacing;
	}

	/*************************************************************************
	 * When formatting, every new paragraph has some indentation applied to its
	 * first line. For simplicity, this indentation is simply a number of space
	 * characters inserted at the beginning of the text. The number of spaces
	 * inserted is given by this number.
	 * 
	 * @return The number of spaces added to the first line of every paragraph.
	 *************************************************************************/
	public int indent()
	{
		return indent;
	}

	/*************************************************************************
	 * Defines the number of spaces used to indent each paragraph. To get rid of
	 * indenting, set this to 0.
	 * 
	 * The default value is 4.
	 * 
	 * @param indent
	 *            The number of spaces to indent paragraphs formatted by this
	 *            font.
	 *************************************************************************/
	public void setIndent(int indent)
	{
		this.indent = indent;
	}

	/*************************************************************************
	 * Obtains a reference to this Font's renderer.
	 * 
	 * @return The FontRenderer this font is using.
	 *************************************************************************/
	public FontRenderer fontRenderer()
	{
		return renderer;
	}

	/*************************************************************************
	 * Obtains the vertical distance between two lines of text. This is not the
	 * same as the value returned by the renderer, but is instead multiplied by
	 * the current line spacing.
	 * 
	 * @return The spacing between two lines of text.
	 ***************************************************************/ @Override
	public float lineHeight()
	{
		return renderer.lineHeight() * lineSpacing;
	}

	/*************************************************************************
	 * Calculates the width of the supplied text.
	 * 
	 * @return The width of the indicated text when rendered by this font.
	 ***************************************************************/ @Override
	public float widthOf(String text)
	{
		return renderer.widthOf(text);
	}

	/*************************************************************************
	 * Calculates the height of the supplied array of text. The font considers
	 * each entry in the array as a separate line.
	 * 
	 * @param text
	 *            The group of text to measure.
	 * 
	 * @return The total height of the indicated text when rendered.
	 *************************************************************************/
	public float heightOf(String... text)
	{
		return text != null ? heightOf(text.length) : 0;
	}

	/*************************************************************************
	 * Calculates the height of a paragraph of the indicated number of lines.
	 * 
	 * @param numeLines
	 *            The number of lines in the considered paragraph.
	 * 
	 * @return The total height of the indicated text when rendered.
	 *************************************************************************/
	public float heightOf(int numLines)
	{
		return numLines > 0 ? renderer.lineHeight() * (1 + ((numLines - 1) * 
				lineSpacing)) : 0;
	}

	/*************************************************************************
	 * Draws the indicated text at the current location of the Modelview Matrix.
	 * 
	 * @param text
	 *            The String to draw.
	 *************************************************************************/
	public void render(String text)
	{
		render(0, 0, text);
	}

	/*************************************************************************
	 * Draws the indicated text at the indicated location relative to the
	 * Modelview Matrix.
	 * 
	 * @param x
	 *            The x coordinate to draw at.
	 * @param y
	 *            The y coordinate to draw at.
	 * @param text
	 *            The String to draw.
	 ***************************************************************/ @Override
	public void render(float x, float y, String text)
	{
		renderer.render(x, y, text);
	}

	/*************************************************************************
	 * Draws the indicated text at the current location of the Modelview Matrix.
	 * 
	 * Each String in the supplied array is treated as a separate line of text.
	 * 
	 * @param text
	 *            The list of Strings to render.
	 *************************************************************************/
	public void render(String... text)
	{
		float lineHeight = lineHeight();
		for (int i = 0; i < text.length; i++)
			render(0, lineHeight * i, text[i]);
	}

	/*************************************************************************
	 * Draws the indicated text at the current location of the Modelview Matrix.
	 * 
	 * Each String in the supplied Iterable is treated as a separate line.
	 * 
	 * @param text
	 *            The Strings to render.
	 *************************************************************************/
	public void render(Iterable<String> text)
	{
		float lineHeight = lineHeight();
		int lineNum = 0;
		for (String line : text)
			render(0, lineHeight * lineNum++, line);
	}

	/*************************************************************************
	 * Formats the indicated String into a series of paragraphs, all fitting
	 * within the indicated width. This caches the results as an array of lines
	 * of text.
	 * 
	 * This method is very expensive, as it has to do a lot of string splitting
	 * and width calculations. Ideally, one should run the desired text through
	 * this method in an editor and save the results, and not ever have to call
	 * this in-game.
	 * 
	 * NOTE: This method simply delegates to {@link #toParagraphs(String, int, 
	 * boolean)}, where indentation is allowed.
	 * 
	 * @param text
	 *            The text to format.
	 * @param width
	 *            The width to keep each paragraph in.
	 * 
	 * @return An array of Strings where each String is a separate line of text.
	 *************************************************************************/
	public String[] toParagraphs(String text, int width)
	{
		return toParagraphs(text, width, true);
	}

	/*************************************************************************
	 * Formats the indicated String into a series of paragraphs, all fitting
	 * within the indicated width. This caches the results as an array of lines
	 * of text.
	 * 
	 * This method is very expensive, as it has to do a lot of string splitting
	 * and width calculations. Ideally, one should run the desired text through
	 * this method in an editor and save the results, and not ever have to call
	 * this in-game.
	 * 
	 * @param text
	 *            The text to format.
	 * @param width
	 *            The width to keep each paragraph in.
	 * @param indent
	 *            Whether or not to append indentation to the beginning of each
	 *            paragraph.
	 * 
	 * @return An array of Strings where each String is a separate line of text.
	 *************************************************************************/
	public String[] toParagraphs(String text, int width, boolean indent)
	{
		LinkedList<String> paragraphs = new LinkedList<String>();

		// Breaks up the text into separate lines. Thus,
		// each line break in the starting text divides
		// into multiple paragraphs.
		for (String paragraph : toLines(text))
			// Parse each line into a paragraph, in order.
			for (String line :toParagraph(paragraph, width, indent))
				paragraphs.add(line);

		// Convert the resulting list into an array.
		return paragraphs.toArray(new String[0]);
	}

	/*************************************************************************
	 * Breaks up the text by line breaks and carriage returns.
	 * 
	 * @param text
	 *            The text to split.
	 * 
	 * @return The text, separated by line.
	 *************************************************************************/
	public String[] toLines(String text)
	{
		LinkedList<String> lines = new LinkedList<String>();
		for (String pass1 : text.split("\n"))
			for (String pass2 : pass1.split("\r"))
				lines.add(pass2);
		return lines.toArray(new String[0]);
	}

	/*************************************************************************
	 * Breaks up the supplied String into a paragraph that cannot be wider than
	 * the indicated width. The supplied String should NOT include newline
	 * characters, the method toParagraphs() will handle text that requires line
	 * breaks.
	 * 
	 * The formatting process takes several passes. First, the supplied string
	 * is broken up by spaces. Then, each "word" is checked to see if it is
	 * wider than the supplied width. If so, it is split up into the minimum of
	 * sections that will fit in the width. Finally, all of these individual
	 * words and word sections are arranged in an optimal paragraph.
	 * 
	 * This method will take time, especially for long text with many long
	 * words. It's recommended to call this once for static text and reuse the
	 * result. Formatting text that changes rapidly is not recommended if
	 * performance is important.
	 * 
	 * @param text
	 *            The text we wish to format.
	 * @param width
	 *            The maximum number of pixels the resulting rendered
	 *            paragraph can have.
	 * @param indent
	 *            Whether or not to indent the paragraph.
	 * 
	 * @return A formatted paragraph, complete with indenting. Each entry in the
	 *         returned array is a separate line.
	 *************************************************************************/
	public String[] toParagraph(String text, int width, boolean indent)
	{
		LinkedList<String> words = new LinkedList<String>();
		String[] components = text.split(" ");
		for (String word : components)
			// Forces each word into chunks that are less
			// than the width desired.
			ensureSize(word, words, width);

		// Combines those pre-sized chunks.
		return forge(words, width, indent).toArray(new String[0]);
	}

	/*************************************************************************
	 * Recursively breaks up a word into the smallest number of sections that
	 * fit within the supplied width, and adds them to the list. If the
	 * indicated word is not wider than the limit, no cropping occurs.
	 * 
	 * @param word
	 *            The word to crop.
	 * @param list
	 *            The list to add words to.
	 * @param width
	 *            The width to keep the word within.
	 *************************************************************************/
	private void ensureSize(String word, LinkedList<String> list, int width)
	{
		if (widthOf(word) <= width)
			list.add(word);
		else
		{
			int cropPoint = word.length() - 1;
			String cropped = word.substring(0, cropPoint);
			while (widthOf(cropped) > width && cropPoint > 0)
			{
				cropPoint--;
				cropped = word.substring(0, cropPoint);
			}
			list.add(cropped);
			ensureSize(word.substring(cropPoint), list, width);
		}
	}

	/*************************************************************************
	 * Strings together the LinkedList of correctly-sized words into a
	 * paragraph.
	 * 
	 * @param words
	 *            The list of words to put together.
	 * @param width
	 *            The maximum width of the paragraph.
	 * @param indent
	 *            Whether or not to indent the beginning of the paragraph.
	 * 
	 * @return A LinkedList where every entry is a line in the paragraph.
	 *************************************************************************/
	private LinkedList<String> forge(LinkedList<String> words, int width, 
			boolean indent)
	{
		LinkedList<String> lines = new LinkedList<String>();
		String line = new String();
		if(indent)
			for (int i = 0; i < this.indent; i++)
				line += ' ';
		String testLine = new String(line);
		for (String word : words)
		{
			testLine += " " + word;
			if (widthOf(testLine) > width)
			{
				lines.add(line);
				testLine = word;
			}
			line = new String(testLine);
		}
		lines.add(line);

		return lines;
	}
}