package org.jrabbit.base.graphics.font.renderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.jrabbit.base.data.loading.SystemLoader;
import org.jrabbit.base.graphics.image.Image;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.util.Log;

/*****************************************************************************
 * JRabbit uses a couple of resources from the Slick game library (with slight
 * modifications). AngelCodeRenderer is one of these.
 * 
 * Basically everything is directly from Slick, so all credit goes there.
 * However, I've made a few changes to A) suit jRabbit's needs more precisely,
 * and to B) slim things down, make the class more OO and have less warnings.
 * 
 * A few thoughts on the use of this class:
 * 
 * An AngelCodeRenderer should load much faster than a UnicodeRenderer, since
 * everything is pre-calculated and pre-rendered. Additionally, since you can
 * directly edit the source image for the text, you can be as detailed as you
 * like with how the fonts look in-game.
 * 
 * However, it's a bit more limited - it only supports 256 characters, and if
 * you want to have a font that's even only slightly different, you essentially
 * need to make another font from the tool.
 * 
 * @author Kevin Glass, modified by Chris Molini.
 *****************************************************************************/
public class AngelCodeRenderer extends AcceleratedFontRenderer
{
	/**
	 * AngelCode uses a single image to manage its characters. This contains the
	 * texture data for the active font, and all stored characters reference it.
	 **/
	private Image image;

	/**
	 * To streamline font rendering/interpretation, only the first 256
	 * characters (a.k.a. the ASCII set) are allowed to be used.
	 **/
	private static final int MAX_CHAR = 255;

	/**
	 * The list of defined characters for rendering. The numerical value of a
	 * character corresponds to its position in the array.
	 **/
	private CharDef[] chars;

	/**
	 * The height of a line of text.
	 **/
	private int lineHeight;

	/*************************************************************************
	 * Creates a new font based on a font definition from AngelCode's tool and
	 * the image that goes with it.
	 * 
	 * @param fontFilepath
	 *            The location of the font definition file.
	 * @param imageFilepath
	 *            The location of the font image.
	 *************************************************************************/
	public AngelCodeRenderer(String fontFilepath, String imageFilepath)
	{
		image = new Image(imageFilepath);
		parseFontFile(new SystemLoader(fontFilepath).stream());
	}

	/*************************************************************************
	 * Parses the font file. This file contains all information used to map
	 * characters to an image.
	 * 
	 * @param fontFilestream
	 *            The input stream from the font file.
	 *************************************************************************/
	private void parseFontFile(InputStream fontFilestream)
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(
					fontFilestream));

			// The first 3 lines of AngelCode font files
			// aren't useful for our purposes.
			in.readLine();
			in.readLine();
			in.readLine();

			Map<Short, List<Short>> kerning = new HashMap<Short, List<Short>>(
					64);
			List<CharDef> charDefs = new ArrayList<CharDef>(MAX_CHAR);
			int maxChar = 0;
			boolean done = false;
			while (!done)
			{
				String line = in.readLine();
				if (line == null)
					done = true;
				else
				{
					if (line.startsWith("chars c")) { /* Ignore */ }
					else if (line.startsWith("char"))
					{
						CharDef def = parseChar(line);
						if (def != null)
						{
							maxChar = Math.max(maxChar, def.id);
							charDefs.add(def);
						}
					}
					if (line.startsWith("kernings c")) { /* Ignore */ }
					else if (line.startsWith("kerning"))
					{
						StringTokenizer sT = new StringTokenizer(line, " =");
						sT.nextToken();
						sT.nextToken();
						short first = Short.parseShort(sT.nextToken());
						sT.nextToken();
						int second = Integer.parseInt(sT.nextToken());
						sT.nextToken();
						int offset = Integer.parseInt(sT.nextToken());
						List<Short> values = kerning.get(new Short(first));
						if (values == null)
						{
							values = new ArrayList<Short>();
							kerning.put(new Short(first), values);
						}
						values.add(new Short((short) ((offset << 8) | second)));
					}
				}
			}

			chars = new CharDef[maxChar + 1];
			for (Iterator<CharDef> iter = charDefs.iterator(); iter.hasNext();)
			{
				CharDef def = (CharDef) iter.next();
				chars[def.id] = def;
			}

			// Turn each list of kerning values into a
			// short[] and set on the chardef.
			for (Iterator<Entry<Short, List<Short>>> iter = kerning.entrySet()
					.iterator(); iter.hasNext();)
			{
				Entry<Short, List<Short>> entry = iter.next();
				short first = entry.getKey().shortValue();
				List<Short> valueList = entry.getValue();
				short[] valueArray = new short[valueList.size()];

				int i = 0;
				for (Iterator<Short> valueIter = valueList.iterator(); valueIter
						.hasNext(); i++)
					valueArray[i] = ((Short) valueIter.next()).shortValue();

				chars[first].kerning = valueArray;
			}

			fontFilestream.close();
		}
		catch (IOException e)
		{
			Log.error(e);
		}
	}

	/*************************************************************************
	 * Parse a single character definition from the font file.
	 * 
	 * @param line
	 *            The line of text from the file that contains character
	 *            information.
	 * 
	 * @return The character definition from the line.
	 *************************************************************************/
	private CharDef parseChar(String line)
	{
		CharDef def = new CharDef();
		StringTokenizer tokens = new StringTokenizer(line, " =");

		tokens.nextToken();
		tokens.nextToken();

		def.id = (char) Short.parseShort(tokens.nextToken());
		if (def.id < 0 || def.id > MAX_CHAR)
			return null;

		tokens.nextToken();
		int x = Short.parseShort(tokens.nextToken());

		tokens.nextToken();
		int y = Short.parseShort(tokens.nextToken());

		tokens.nextToken();
		int width = Short.parseShort(tokens.nextToken());

		tokens.nextToken();
		int height = Short.parseShort(tokens.nextToken());

		tokens.nextToken();
		int xoffset = Short.parseShort(tokens.nextToken());

		tokens.nextToken();
		int yoffset = Short.parseShort(tokens.nextToken());

		tokens.nextToken();
		def.xadvance = Short.parseShort(tokens.nextToken());

		if (def.id != ' ')
			lineHeight = Math.max(height + yoffset, lineHeight);

		def.init(x, y, width, height, xoffset, yoffset);

		return def;
	}

	/*************************************************************************
	 * Decides whether or not the font image should have a smoothing filter
	 * applied when it is scaled up. By default this is true.
	 * 
	 * @param smooth
	 *            Whether or not to smooth the font when it is scaled.
	 *************************************************************************/
	public void smoothFont(boolean smooth)
	{
		image.smooth(smooth);
	}

	/*************************************************************************
	 * Decides whether or not the font image should have a smoothing filter
	 * applied when it is scaled up. By default this is true.
	 * 
	 * @param smooth
	 *            Whether or not to smooth the font when it is scaled.
	 *************************************************************************/
	public boolean smoothing()
	{
		return image.smoothing();
	}

	/*************************************************************************
	 * Determines whether or not an AngelCodeRenderer is considered valid for
	 * use. Since the image that holds character data is bound to the life of
	 * the font, this call simply checks to see if that image is intact.
	 * 
	 * @return Whether or not the AngelCodeRender can render text.
	 ***************************************************************/ @Override
	public boolean valid()
	{
		return image.valid();
	}

	/*************************************************************************
	 * Wipes the image and all stored displayLists.
	 ***************************************************************/ @Override
	public void destroy()
	{
		image.destroy();
		if (baseDL != 0)
			GL11.glDeleteLists(baseDL, maxDL);
	}

	/*************************************************************************
	 * Renders a string of text to the screen.
	 * 
	 * @param text
	 *            The text to be rendered.
	 ***************************************************************/ @Override
	protected void drawString(String text)
	{
		image.bind();
		GL11.glBegin(GL11.GL_QUADS);

		int x = 0, y = 0;
		CharDef lastCharDef = null;
		char[] data = text.toCharArray();

		for (int i = 0; i < data.length; i++)
		{
			char id = data[i];
			if (id == '\n')
			{
				x = 0;
				y += lineHeight();
				continue;
			}
			if (id >= chars.length)
				continue;
			CharDef charDef = chars[id];
			if (charDef == null)
				continue;
			if (lastCharDef != null)
				x += lastCharDef.getKerning(id);
			lastCharDef = charDef;
			if ((i >= 0) && (i <= text.length()))
				charDef.draw(x, y);
			x += charDef.xadvance;
		}

		GL11.glEnd();
	}

	/*************************************************************************
	 * Accesses the stored height of the line, as read from the font file.
	 * 
	 * @return The height of a line of text.
	 ***************************************************************/ @Override
	public float lineHeight()
	{
		return lineHeight;
	}

	/*************************************************************************
	 * Determines the width of a String when rendered.
	 * 
	 * @param text
	 *            The String to measure.
	 * 
	 * @return The width of the String when rendered.
	 ***************************************************************/ @Override
	public float measureString(String text)
	{
		float maxWidth = 0;
		float width = 0;
		CharDef lastCharDef = null;
		for (int i = 0, n = text.length(); i < n; i++)
		{
			int id = text.charAt(i);
			if (id == '\n')
			{
				width = 0;
				continue;
			}
			if (id >= chars.length)
				continue;
			CharDef charDef = chars[id];
			if (charDef == null)
				continue;
			if (lastCharDef != null)
				width += lastCharDef.getKerning(id);
			lastCharDef = charDef;
			if (i < n - 1)
				width += charDef.xadvance;
			else
				width += charDef.vertCoords[2];
			maxWidth = Math.max(maxWidth, width);
		}
		return maxWidth;
	}

	/*************************************************************************
	 * The definition of a single character read from an AngelCode font file.
	 * 
	 * Modified to be more streamlined and work with jRabbit.
	 * 
	 * @author Kevin Glass, modified by Chris Molini
	 *************************************************************************/
	private class CharDef
	{
		/**
		 * The character represented by this object.
		 **/
		public char id;

		/**
		 * The amount to move the current position after drawing the character.
		 **/
		public short xadvance;

		/**
		 * The kerning information for this character.
		 **/
		public short[] kerning;

		/**
		 * The calculated vertex coordinates that are used to render the
		 * character.
		 **/
		public float[] vertCoords;

		/**
		 * The stored texture coordinates that will display the character image.
		 **/
		public float[] texCoords;

		/*********************************************************************
		 * Initialize the image by cutting the right section from the map
		 * produced by the AngelCode tool.
		 *********************************************************************/
		public void init(int x, int y, int width, int height, int xoffset,
				int yoffset)
		{
			texCoords = image.subImageCoords(x, y, width, height);
			vertCoords = new float[] { xoffset, yoffset, xoffset + width,
					yoffset + height };
		}

		/*********************************************************************
		 * Draw this character. The draw renders the subsection of the main font
		 * sheet that contains this character.
		 * 
		 * @param x
		 *            The x position at which to draw the text.
		 * @param y
		 *            The y position at which to draw the text.
		 *********************************************************************/
		public void draw(float x, float y)
		{
			GL11.glTexCoord2f(texCoords[0], texCoords[1]);
			GL11.glVertex2f(x + vertCoords[0], y + vertCoords[1]);

			GL11.glTexCoord2f(texCoords[2], texCoords[1]);
			GL11.glVertex2f(x + vertCoords[2], y + vertCoords[1]);

			GL11.glTexCoord2f(texCoords[2], texCoords[3]);
			GL11.glVertex2f(x + vertCoords[2], y + vertCoords[3]);

			GL11.glTexCoord2f(texCoords[0], texCoords[3]);
			GL11.glVertex2f(x + vertCoords[0], y + vertCoords[3]);
		}

		/*********************************************************************
		 * Get the kerning offset between this character and the specified
		 * character.
		 * 
		 * @param otherCodePoint
		 *            The other code point to measure against.
		 * 
		 * @return The kerning offset.
		 *********************************************************************/
		public int getKerning(int otherCodePoint)
		{
			if (kerning == null)
				return 0;
			int low = 0;
			int high = kerning.length - 1;
			while (low <= high)
			{
				int midIndex = (low + high) >>> 1;
				int value = kerning[midIndex];
				int foundCodePoint = value & 0xff;
				if (foundCodePoint < otherCodePoint)
					low = midIndex + 1;
				else if (foundCodePoint > otherCodePoint)
					high = midIndex - 1;
				else
					return value >> 8;
			}
			return 0;
		}
	}
}