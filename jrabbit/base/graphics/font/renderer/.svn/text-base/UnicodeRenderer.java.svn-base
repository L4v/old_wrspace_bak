package org.jrabbit.base.graphics.font.renderer;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jrabbit.base.data.Destroyable;
import org.jrabbit.base.data.loading.SystemLoader;
import org.jrabbit.base.graphics.font.FontProfile;
import org.jrabbit.base.graphics.image.Image;
import org.jrabbit.base.graphics.transforms.Color;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/*****************************************************************************
 * JRabbit uses a couple of resources from the Slick game library (with slight
 * modifications). UnicodeRenderer is one of these.
 * 
 * Basically everything is directly from Slick, so all credit goes there.
 * However, I've made a few changes to A) suit jRabbit's needs more precisely,
 * and to B) slim things down, make the class more OO and have less warnings.
 * 
 * A few thoughts on the use of this class:
 * 
 * A UnicodeRenderer is very flexible, capable class, but very heavy duty to
 * load. It can load any .TTF file, or access any installed TrueType fonts on
 * the user's computer, but it then has to calculate and render all resources
 * needed to put that font in-game. This is obviously very expensive and will
 * cause a noticeable pause in a game.
 * 
 * UnicodeRenderer also has a limitation beyond initialization time. The font
 * loaded is a solid white color. Color filters can be applied, but you cannot
 * detail the source texture like you can with AngelCode fonts.
 * 
 * However, the use of it does have benefits. As mentioned before, it can load
 * .TTF files and put them into fonts. These can be at any scale, and can have
 * basic text transforms (bold, italic, etc.) applied to them before conversion.
 * The same TrueType font can be used for multiple fonts, as well.
 * 
 * Additionally, a UnicodeRenderer can render virtually any character the font
 * supports. This includes those outside of the ASCII range.
 * 
 * @author Nathan Sweet, modified by Chris Molini.
 *****************************************************************************/
public class UnicodeRenderer extends AcceleratedFontRenderer
{
	/* ********************************************************************* *
	 * 							Static class data 							 *
	 * ********************************************************************* */

	/**
	 * The highest character value allowed.
	 **/
	static protected final int MAX_CHAR = 0x10FFFF;

	/**
	 * The number of glyphs that can be on a page.
	 **/
	protected static final int PAGE_SIZE = 512;

	/**
	 * The number of pages allowed.
	 **/
	protected static final int PAGES = MAX_CHAR / PAGE_SIZE;

	/*************************************************************************
	 * Loads a java Font from a supplied String.
	 * 
	 * The first attempt this method takes is to assume that the String
	 * references a .TTF file. If such a file is found, then the font described
	 * by the file is loaded.
	 * 
	 * If that fails, then the method attempts to load a font from the system
	 * (e.g., if "Arial" is supplied, Java can load the font from the OS).
	 * 
	 * If that fails, a default font is created.
	 * 
	 * @param font
	 *            The description of the font file.
	 * 
	 * @return The font created from the reference.
	 *************************************************************************/
	public static final Font loadFont(String font)
	{
		Font awtFont = null;
		try
		{
			InputStream stream = new SystemLoader(font).stream();
			awtFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			stream.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.err.println(font + " not loaded. Attempting to load"
					+ " the system font '" + font + '\'');
			awtFont = new Font(font, Font.PLAIN, 20);
		}
		return awtFont;
	}

	/*************************************************************************
	 * A custom Comparator for sorting Glyphs by height, tallest first.
	 *************************************************************************/
	protected static final Comparator<Glyph> heightComparator = 
		new Comparator<Glyph>()
	{
		public int compare(Glyph g1, Glyph g2)
		{
			return g1.height - g2.height;
		}
	};

	/* ********************************************************************* *
	 * 						General UnicodeRenderer data 					 *
	 * ********************************************************************* */

	/**
	 * The AWT font that is being rendered.
	 **/
	protected Font font;

	/**
	 * The reference to the True Type Font file that has kerning information.
	 **/
	protected String ttfFileRef;

	/**
	 * The ascent of the font.
	 **/
	protected int ascent;

	/**
	 * The decent of the font.
	 **/
	protected int descent;

	/**
	 * The leading edge of the font.
	 **/
	protected int leading;

	/**
	 * The width of a space for the font.
	 **/
	protected int spaceWidth;

	/**
	 * The glyphs that are available in this font.
	 **/
	protected final Glyph[][] glyphs = new Glyph[PAGES][];

	/**
	 * The pages that have been loaded for this font.
	 **/
	protected final List<GlyphPage> glyphPages = new ArrayList<GlyphPage>();

	/**
	 * The glyphs queued up to be rendered.
	 **/
	protected final List<Glyph> queuedGlyphs = new ArrayList<Glyph>(256);

	/**
	 * The padding applied in pixels to the top of the glyph rendered area.
	 **/
	protected int paddingTop;

	/**
	 * The padding applied in pixels to the left of the glyph rendered area.
	 **/
	protected int paddingLeft;

	/**
	 * The padding applied in pixels to the bottom of the glyph rendered area.
	 **/
	protected int paddingBottom;

	/**
	 * The padding applied in pixels to the right of the glyph rendered area.
	 **/
	protected int paddingRight;

	/**
	 * The padding applied in pixels to horizontal advance for each glyph.
	 **/
	protected int paddingAdvanceX;

	/**
	 * The padding applied in pixels to vertical advance for each glyph.
	 **/
	protected int paddingAdvanceY;

	/**
	 * The glyph to display for missing glyphs in code points.
	 **/
	protected Glyph missingGlyph;

	/**
	 * The width of the glyph page generated.
	 **/
	protected int glyphPageWidth = 512;

	/**
	 * The height of the glyph page generated.
	 **/
	protected int glyphPageHeight = 512;

	/**
	 * The default glyph painter.
	 **/
	protected GlyphPainter glyphPainter = new GlyphPainter()
	{
		public void drawGlyph(Graphics2D graphics, Shape glyph)
		{
			graphics.setComposite(AlphaComposite.SrcOver);
			graphics.setColor(java.awt.Color.white);
			graphics.fill(glyph);
		}
	};

	/*************************************************************************
	 * Create a new UnicodeRenderer based on a TTF file.
	 * 
	 * @param ttfFileRef
	 *            The file system or classpath location of the desired
	 *            TrueTypeFont file.
	 * @param p
	 *            a FontProfile which is applied to the loaded font.
	 *************************************************************************/
	public UnicodeRenderer(String filepath, FontProfile p)
	{
		this.ttfFileRef = filepath;
		initializeFont(loadFont(ttfFileRef), p.size(), p.bold(), p.italic());
	}

	/*************************************************************************
	 * Creates a UnicodeRenderer from the specified TrueTypeFont file. This uses
	 * the default font size of 20.
	 * 
	 * @param filepath
	 *            The path to the desired TrueTypeFont file.
	 *************************************************************************/
	public UnicodeRenderer(String filepath)
	{
		this(filepath, new FontProfile());
	}

	/*************************************************************************
	 * Creates a new UnicodeRenderer.
	 * 
	 * @param font
	 *            The AWT font to render
	 *************************************************************************/
	public UnicodeRenderer(Font font)
	{
		initializeFont(font, font.getSize(), font.isBold(), font.isItalic());
	}

	/*************************************************************************
	 * Creates a new UnicodeRenderer.
	 * 
	 * @param font
	 *            The AWT font to render
	 * @param size
	 *            The point size of the font to generated
	 * @param bold
	 *            True if the font should be rendered in bold.
	 * @param italic
	 *            True if the font should be rendered in italic.
	 *************************************************************************/
	public UnicodeRenderer(Font font, int size, boolean bold, boolean italic)
	{
		initializeFont(font, size, bold, italic);
	}

	/*************************************************************************
	 * Creates a UnicodeRenderer from the indicated file at the specified size.
	 * 
	 * @param filepath
	 *            The file to load a .TTF file from.
	 * @param size
	 *            The font size to use for this font.
	 *************************************************************************/
	public UnicodeRenderer(String filepath, int size)
	{
		this.ttfFileRef = filepath;
		initializeFont(loadFont(ttfFileRef), size, false, false);
	}

	/*************************************************************************
	 * Initialize the font to be used based on the indicated configuration.
	 * 
	 * @param baseFont
	 *            The AWT font to render
	 * @param size
	 *            The point size of the font to generated
	 * @param bold
	 *            True if the font should be rendered in bold.
	 * @param italic
	 *            True if the font should be rendered in italic.
	 **************************/ @SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initializeFont(Font baseFont, int size, boolean bold,
			boolean italic)
	{
		Map attributes = baseFont.getAttributes();
		attributes.put(TextAttribute.SIZE, new Float(size));
		attributes.put(TextAttribute.WEIGHT, bold ? TextAttribute.WEIGHT_BOLD
				: TextAttribute.WEIGHT_REGULAR);
		attributes.put(TextAttribute.POSTURE,
				italic ? TextAttribute.POSTURE_OBLIQUE
						: TextAttribute.POSTURE_REGULAR);

		try
		{
			attributes.put(
					TextAttribute.class.getDeclaredField("KERNING").get(null),
					TextAttribute.class.getDeclaredField("KERNING_ON")
							.get(null));
		}
		catch (Exception ignored) { }

		font = baseFont.deriveFont(attributes);

		FontMetrics metrics = GlyphPage.getScratchGraphics().getFontMetrics(
				font);
		ascent = metrics.getAscent();
		descent = metrics.getDescent();
		leading = metrics.getLeading();

		// Determine width of space glyph.
		// (getGlyphPixelBounds() gives a width of zero.)
		char[] chars = " ".toCharArray();
		GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext,
				chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT);
		spaceWidth = vector.getGlyphLogicalBounds(0).getBounds().width;

		// Starts the font with some default characters.
		addAsciiGlyphs();
	}

	/*************************************************************************
	 * Redefines the GlyphPainter.
	 * 
	 * @param painter
	 *            The painter to use for rendering the glyphs.
	 *************************************************************************/
	public void setGlyphPainter(GlyphPainter painter)
	{
		glyphPainter = painter;
	}

	/*************************************************************************
	 * Accesses the GlyphPainter.
	 * 
	 * @return The object that is used to render the glyphs.
	 *************************************************************************/
	public GlyphPainter glyphPainter()
	{
		return glyphPainter;
	}

	/*************************************************************************
	 * Queues the glyphs in the specified codepoint range (inclusive) to be
	 * loaded. Note that the glyphs are not actually loaded until
	 * {@link #loadGlyphs()} is called.
	 * 
	 * Some characters like combining marks and non-spacing marks can only be
	 * rendered with the context of other glyphs. In this case, use
	 * {@link #addGlyphs(String)}.
	 * 
	 * @param startCodePoint
	 *            The code point of the first glyph to add.
	 * @param endCodePoint
	 *            The code point of the last glyph to add.
	 *************************************************************************/
	public void addGlyphs(int startCodePoint, int endCodePoint)
	{
		for (int codePoint = startCodePoint; codePoint <= endCodePoint; 
				codePoint++)
			addGlyphs(new String(Character.toChars(codePoint)));
	}

	/*************************************************************************
	 * Queues the glyphs in the specified text to be loaded. Note that the
	 * glyphs are not actually loaded until {@link #loadGlyphs()} is called.
	 * 
	 * @param text
	 *            The text that contains all characters desired.
	 *************************************************************************/
	public void addGlyphs(String text)
	{
		if (text == null)
			throw new IllegalArgumentException("Text cannot be null!");

		char[] chars = text.toCharArray();
		GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext,
				chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT);
		for (int i = 0, n = vector.getNumGlyphs(); i < n; i++)
		{
			int codePoint = text.codePointAt(vector.getGlyphCharIndex(i));
			Rectangle bounds = getGlyphBounds(vector, i, codePoint);
			getGlyph(vector.getGlyphCode(i), codePoint, bounds, vector, i);
		}
	}

	/*************************************************************************
	 * Queues the glyphs in the ASCII character set (codepoints 32 through 255)
	 * to be loaded. Note that the glyphs are not actually loaded until
	 * {@link #loadGlyphs()} is called.
	 *************************************************************************/
	public void addAsciiGlyphs()
	{
		addGlyphs(32, 255);
	}

	/*************************************************************************
	 * Queues the glyphs in the NEHE character set (codepoints 32 through 128)
	 * to be loaded. Note that the glyphs are not actually loaded until
	 * {@link #loadGlyphs()} is called.
	 *************************************************************************/
	public void addNeheGlyphs()
	{
		addGlyphs(32, 32 + 96);
	}

	/*************************************************************************
	 * Loads all queued glyphs to the backing textures. Glyphs that are
	 * typically displayed together should be added and loaded at the same time
	 * so that they are stored on the same backing texture. This reduces the
	 * number of backing texture binds required to draw glyphs.
	 * 
	 * @return True if the glyphs were loaded entirely. false if not.
	 *************************************************************************/
	public boolean loadGlyphs()
	{
		return loadGlyphs(-1);
	}

	/*************************************************************************
	 * Loads up to the specified number of queued glyphs to the backing
	 * textures. This is typically called from the game loop to load glyphs on
	 * the fly that were requested for display but have not yet been loaded.
	 * 
	 * @param maxGlyphsToLoad
	 *            The maximum number of glyphs to be loaded.
	 * @return True if the glyphs were loaded entirely, false if not.
	 *************************************************************************/
	public boolean loadGlyphs(int maxGlyphsToLoad)
	{
		if (queuedGlyphs.isEmpty())
			return false;

		for (Iterator<Glyph> iter = queuedGlyphs.iterator(); iter.hasNext();)
		{
			Glyph glyph = (Glyph) iter.next();
			int codePoint = glyph.codePoint;

			// Don't load an image for a glyph with nothing to display.
			if (glyph.width == 0 || codePoint == ' ')
			{
				iter.remove();
				continue;
			}

			// Only load the first missing glyph.
			if (glyph.isMissing)
			{
				if (missingGlyph != null)
				{
					if (glyph != missingGlyph)
						iter.remove();
					continue;
				}
				missingGlyph = glyph;
			}
		}

		Collections.sort(queuedGlyphs, heightComparator);

		// Add to existing pages.
		for (Iterator<GlyphPage> iter = glyphPages.iterator(); iter.hasNext();)
		{
			GlyphPage glyphPage = (GlyphPage) iter.next();
			maxGlyphsToLoad -= glyphPage.loadGlyphs(queuedGlyphs,
					maxGlyphsToLoad, glyphPainter);
			if (maxGlyphsToLoad == 0 || queuedGlyphs.isEmpty())
				return true;
		}

		// Add to new pages.
		while (!queuedGlyphs.isEmpty())
		{
			GlyphPage glyphPage = new GlyphPage(glyphPageWidth, glyphPageHeight);
			glyphPages.add(glyphPage);
			maxGlyphsToLoad -= glyphPage.loadGlyphs(queuedGlyphs,
					maxGlyphsToLoad, glyphPainter);
			if (maxGlyphsToLoad == 0)
				return true;
		}

		return true;
	}

	/*************************************************************************
	 * Clears all loaded and queued glyphs. Also wipes existing OpenGL data.
	 *************************************************************************/
	public void clearGlyphs()
	{
		for (int i = 0; i < PAGES; i++)
			glyphs[i] = null;

		for (Iterator<GlyphPage> iter = glyphPages.iterator(); iter.hasNext();)
		{
			GlyphPage page = (GlyphPage) iter.next();
			page.pageImage.destroy();
		}
		glyphPages.clear();

		for (TextDL dL : displayLists.values())
		{
			GL11.glDeleteLists(dL.id, 1);
		}

		displayLists.clear();
		queuedGlyphs.clear();
		missingGlyph = null;
	}

	/*************************************************************************
	 * Checks to see if the UnicodeRenderer is intact. This is based on its
	 * GlyphPages.
	 * 
	 * @return True if every GlyphPage's image is still valid; if any are false
	 *         this returns false.
	 ***************************************************************/ @Override
	public boolean valid()
	{
		for (GlyphPage page : glyphPages)
			if (!page.pageImage.valid())
				return false;
		return true;
	}

	/*************************************************************************
	 * Loads all registered glyphs for this font, and then initializes all
	 * needed display lists.
	 ***************************************************************/ @Override
	public void create()
	{
		super.create();
		loadGlyphs();
	}

	/*************************************************************************
	 * Releases all resources used by this UnicodeFont. First deletes all
	 * display lists, then gets rid of every stored Glyph.
	 ***************************************************************/ @Override
	public void destroy()
	{
		clearGlyphs();
	}

	/*************************************************************************
	 * Renders the supplied text.
	 * 
	 * @param text
	 *            The String to render.
	 ***************************************************************/ @Override
	protected void drawString(String text)
	{
		char[] chars = text.toCharArray();

		GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext,
				chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT);

		int maxWidth = 0, totalHeight = 0;
		int extraX = 0, extraY = ascent;
		boolean startNewLine = false;
		Image image = null;

		for (int glyphIndex = 0, n = vector.getNumGlyphs(); glyphIndex < n; 
				glyphIndex++)
		{
			int charIndex = vector.getGlyphCharIndex(glyphIndex);
			if (charIndex < 0)
				continue;
			if (charIndex > chars.length)
				break;

			int codePoint = chars[charIndex];

			Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);
			Glyph glyph = getGlyph(vector.getGlyphCode(glyphIndex), codePoint,
					bounds, vector, glyphIndex);

			if (startNewLine && codePoint != '\n')
			{
				extraX = -bounds.x;
				startNewLine = false;
			}
			image = glyph.drawGlyph(image, (int) bounds.x + extraX,
					(int) bounds.y + extraY);

			if (glyphIndex > 0)
				extraX += paddingRight + paddingLeft + paddingAdvanceX;

			maxWidth = Math.max(maxWidth, bounds.x + extraX + bounds.width);
			totalHeight = Math.max(totalHeight, ascent + bounds.y
					+ bounds.height);

			if (codePoint == '\n')
			{
				startNewLine = true;
				extraY += lineHeight();
				totalHeight = 0;
			}
		}

		GL11.glEnd();
	}

	/*************************************************************************
	 * Returns the glyph for the specified codePoint. If the glyph does not
	 * exist yet, it is created and queued to be loaded.
	 * 
	 * @param glyphCode
	 *            The code of the glyph to locate.
	 * @param codePoint
	 *            The code point associated with the glyph.
	 * @param bounds
	 *            The bounds of the glyph on the page.
	 * @param vector
	 *            The vector the glyph is part of.
	 * @param index
	 *            The index of the glyph within the vector.
	 * 
	 * @return The requested glyph.
	 *************************************************************************/
	protected Glyph getGlyph(int glyphCode, int codePoint, Rectangle bounds,
			GlyphVector vector, int index)
	{
		// GlyphVector#getGlyphCode sometimes returns
		// negative numbers on OS X.
		if (glyphCode < 0 || glyphCode >= MAX_CHAR)
		{
			Glyph glyph = new Glyph(codePoint, bounds, vector, index);
			glyph.isMissing = true;
			return glyph;
		}

		int pageIndex = glyphCode / PAGE_SIZE;
		int glyphIndex = glyphCode & (PAGE_SIZE - 1);
		Glyph glyph = null;
		Glyph[] page = glyphs[pageIndex];
		if (page != null)
		{
			glyph = page[glyphIndex];
			if (glyph != null)
				return glyph;
		} 
		else
			page = glyphs[pageIndex] = new Glyph[PAGE_SIZE];

		// Add glyph so size information is available and
		// queue it so its image can be loaded later.
		glyph = page[glyphIndex] = new Glyph(codePoint, bounds, vector, index);
		queuedGlyphs.add(glyph);

		return glyph;
	}

	/*************************************************************************
	 * Finds the dimensions of the specified glyph.
	 * 
	 * @param vector
	 *            The vector the glyph is part of.
	 * @param index
	 *            The index of the glyph within the vector.
	 * @param codePoint
	 *            The code point associated with the glyph.
	 * 
	 * @return The bounds of the specified glyph.
	 *************************************************************************/
	protected Rectangle getGlyphBounds(GlyphVector vector, int index,
			int codePoint)
	{
		Rectangle bounds = vector.getGlyphPixelBounds(index,
				GlyphPage.renderContext, 0, 0);
		if (codePoint == ' ')
			bounds.width = spaceWidth;

		return bounds;
	}

	/*************************************************************************
	 * Finds the current width of the space character.
	 * 
	 * @return How wide a space is.
	 *************************************************************************/
	public int getSpaceWidth()
	{
		return spaceWidth;
	}

	/*************************************************************************
	 * Forcibly defines the width of a space.
	 * 
	 * @param space
	 *            The gap created by the space character.
	 *************************************************************************/
	public void setSpaceWidth(int space)
	{
		spaceWidth = space;
	}

	/*************************************************************************
	 * Directly measures a String of text to find out how wide it is.
	 * 
	 * @param text
	 *            The text to measure.
	 * 
	 * @return The width of the String when rendered.
	 ***************************************************************/ @Override
	protected float measureString(String text)
	{
		char[] chars = text.toCharArray();
		GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext,
				chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT);

		float width = 0;
		float extraX = 0;
		boolean startNewLine = false;
		for (int glyphIndex = 0, n = vector.getNumGlyphs(); glyphIndex < n; 
				glyphIndex++)
		{
			int charIndex = vector.getGlyphCharIndex(glyphIndex);
			int codePoint = text.codePointAt(charIndex);
			Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);

			if (startNewLine && codePoint != '\n')
				extraX = -bounds.x;

			if (glyphIndex > 0)
				extraX += paddingLeft + paddingRight + paddingAdvanceX;
			width = Math.max(width, bounds.x + extraX + bounds.width);

			if (codePoint == '\n')
				startNewLine = true;
		}

		return width;
	}

	/*************************************************************************
	 * Accesses the height of each line of text.
	 * 
	 * @return The height of a line of text.
	 ***************************************************************/ @Override
	public float lineHeight()
	{
		return descent + ascent + leading + paddingTop + paddingBottom
				+ paddingAdvanceY;
	}

	/*************************************************************************
	 * Returns the TrueTypeFont for this UnicodeFont.
	 * 
	 * @return The AWT Font being rendered.
	 *************************************************************************/
	public Font getFont()
	{
		return font;
	}

	/*************************************************************************
	 * Returns the GlyphPages for this UnicodeRenderer.
	 * 
	 * @return The glyph pages that have been loaded into this font.
	 *************************************************************************/
	public List<GlyphPage> getGlyphPages()
	{
		return glyphPages;
	}

	/*************************************************************************
	 * GlyphPainter is an interface that lets the developer customize how
	 * Unicode fonts are rendered onto textures.
	 * 
	 * The default renderer simply fills the shape with solid white; however,
	 * any operations permitted a Graphics2D object are possible.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static interface GlyphPainter
	{
		/*********************************************************************
		 * Draws the glyph onto the graphics.
		 * 
		 * @param graphics
		 *            The graphics to draw on.
		 * @param glyph
		 *            The shape of the glyph we need to draw.
		 *********************************************************************/
		public void drawGlyph(Graphics2D graphics, Shape glyph);
	}

	/* ********************************************************************* *
	 * 							Internal classes							 *
	 * ********************************************************************* */

	/*************************************************************************
	 * A GlyphPage controls a group of Glyphs. It manages loading them and it
	 * contains them on a collective texture.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected static class GlyphPage implements Destroyable
	{
		/**
		 * The maximum size of an individual glyph. If a glyph is attempted that
		 * has a larger dimension, its size is cropped to this value.
		 **/
		public static final int MAX_GLYPH_SIZE = 256;

		/**
		 * A temporary working buffer.
		 **/
		protected static ByteBuffer scratchByteBuffer = ByteBuffer
				.allocateDirect(MAX_GLYPH_SIZE * MAX_GLYPH_SIZE * 4);

		// Orders the buffer appropriately.
		static
		{
			scratchByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		}

		/**
		 * A temporary working buffer.
		 **/
		protected static IntBuffer scratchIntBuffer = scratchByteBuffer
				.asIntBuffer();

		/**
		 * A temporary image used to generate the glyph page.
		 **/
		protected static BufferedImage scratchImage = new BufferedImage(
				MAX_GLYPH_SIZE, MAX_GLYPH_SIZE, BufferedImage.TYPE_INT_ARGB);

		/**
		 * The graphics context form the temporary image.
		 **/
		protected static Graphics2D scratchGraphics = (Graphics2D) scratchImage
				.getGraphics();

		// Initializes rendering values for the scratch
		// graphics.
		static
		{
			scratchGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			scratchGraphics.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			scratchGraphics.setRenderingHint(
					RenderingHints.KEY_FRACTIONALMETRICS,
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		}

		/**
		 * The render context in which the glyphs will be generated.
		 **/
		public static FontRenderContext renderContext = scratchGraphics
				.getFontRenderContext();

		/*********************************************************************
		 * Get the scratch graphics used to generate the page of glyphs.
		 * 
		 * @return The scratch graphics used to build the page.
		 *********************************************************************/
		public static Graphics2D getScratchGraphics()
		{
			return scratchGraphics;
		}

		/**
		 * The width of this page's image.
		 **/
		protected final int pageWidth;

		/**
		 * The height of this page's image.
		 **/
		protected final int pageHeight;

		/**
		 * The image containing the glyphs.
		 **/
		protected final Image pageImage;

		/**
		 * The x position of the page.
		 **/
		protected int pageX;

		/**
		 * The y position of the page.
		 **/
		protected int pageY;

		/**
		 * The height of the last row on the page.
		 **/
		protected int rowHeight;

		/**
		 * True if the glyphs are ordered.
		 **/
		protected boolean orderAscending;

		/**
		 * The list of glyphs on this page.
		 **/
		protected final List<Glyph> pageGlyphs = new ArrayList<Glyph>(32);

		/*********************************************************************
		 * Create a new page of glyphs. This initializes a texture of the
		 * supplied size.
		 * 
		 * @param pageWidth
		 *            The width of the backing texture.
		 * @param pageHeight
		 *            The height of the backing texture.
		 *********************************************************************/
		public GlyphPage(int pageWidth, int pageHeight)
		{
			this.pageWidth = pageWidth;
			this.pageHeight = pageHeight;
			pageImage = new Image("", pageWidth, pageHeight);
		}

		/*********************************************************************
		 * Eradicates the texture that contains glyph data.
		 ***********************************************************/ @Override
		public void destroy()
		{
			pageImage.destroy();
		}

		/*********************************************************************
		 * Loads glyphs to the backing texture and sets the image on each loaded
		 * glyph. Loaded glyphs are removed from the list.
		 * 
		 * If this page already has glyphs and maxGlyphsToLoad is -1, then this
		 * method will return 0 if all the new glyphs don't fit. This reduces
		 * texture binds when drawing since glyphs loaded at once are typically
		 * displayed together.
		 * 
		 * @param glyphs
		 *            The glyphs to load.
		 * @param maxGlyphsToLoad
		 *            This is the maximum number of glyphs to load from the
		 *            list. Set to -1 to attempt to load all the glyphs.
		 * @param painter
		 *            The painter to render the glyphs.
		 * 
		 * @return The number of glyphs that were actually loaded.
		 *********************************************************************/
		public int loadGlyphs(List<Glyph> glyphs, int maxGlyphsToLoad,
				GlyphPainter painter)
		{
			if (rowHeight != 0 && maxGlyphsToLoad == -1)
			{
				// If this page has glyphs and we are not
				// loading incrementally, return zero if
				// any of the glyphs don't fit.
				int testX = pageX;
				int testY = pageY;
				int testRowHeight = rowHeight;
				for (Iterator<Glyph> iter = iter(glyphs); iter.hasNext();)
				{
					Glyph glyph = (Glyph) iter.next();
					if (testX + glyph.width >= pageWidth)
					{
						testX = 0;
						testY += testRowHeight;
						testRowHeight = glyph.height;
					} else if (glyph.height > testRowHeight)
					{
						testRowHeight = glyph.height;
					}
					if (testY + testRowHeight >= pageWidth)
						return 0;
					testX += glyph.width;
				}
			}

			Color.WHITE.bind();
			pageImage.bind();

			int i = 0;
			for (Iterator<Glyph> iter = iter(glyphs); iter.hasNext();)
			{
				Glyph glyph = (Glyph) iter.next();
				glyph.width = (short) Math.min(MAX_GLYPH_SIZE, glyph.width);
				glyph.height = (short) Math.min(MAX_GLYPH_SIZE, glyph.height);

				if (rowHeight == 0)
				{
					// The first glyph always fits.
					rowHeight = glyph.height;
				} else
				{
					// Wrap to the next line if needed, or
					// break if no more fit.
					if (pageX + glyph.width >= pageWidth)
					{
						if (pageY + rowHeight + glyph.height >= pageHeight)
							break;
						pageX = 0;
						pageY += rowHeight;
						rowHeight = glyph.height;
					} else if (glyph.height > rowHeight)
					{
						if (pageY + glyph.height >= pageHeight)
							break;
						rowHeight = glyph.height;
					}
				}

				renderGlyph(glyph, painter);
				pageGlyphs.add(glyph);

				pageX += glyph.width;

				iter.remove();

				i++;
				if (i == maxGlyphsToLoad)
				{
					// If loading incrementally, flip
					// orderAscending so it won't change,
					// since we'll probably load the rest
					// next time.
					orderAscending = !orderAscending;
					break;
				}
			}

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

			// Every other batch of glyphs added to a page
			// are sorted the opposite way to attempt to
			// keep same size glyps together.
			orderAscending = !orderAscending;

			return i;
		}

		/*********************************************************************
		 * Loads a single glyph to the backing texture, if it fits.
		 * 
		 * To place the glyph onto a texture, the shape is rendered via the
		 * GlyphPainter, and then the pixel data is copied onto a texture.
		 * 
		 * @param glyph
		 *            The glyph to be rendered.
		 * @param painter
		 *            The painter to render the glyphs.
		 *********************************************************************/
		protected void renderGlyph(Glyph glyph, GlyphPainter painter)
		{
			// Draw the glyph to the scratch image using Java2D.
			scratchGraphics.setComposite(AlphaComposite.Clear);
			scratchGraphics.fillRect(0, 0, MAX_GLYPH_SIZE, MAX_GLYPH_SIZE);

			painter.drawGlyph(scratchGraphics, glyph.shape);

			glyph.shape = null; // The shape will never be needed again.

			WritableRaster raster = scratchImage.getRaster();
			int[] row = new int[glyph.width];
			for (int y = 0; y < glyph.height; y++)
			{
				raster.getDataElements(0, y, glyph.width, 1, row);
				scratchIntBuffer.put(row);
			}

			GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, pageX, pageY,
					glyph.width, glyph.height, GL12.GL_BGRA,
					GL11.GL_UNSIGNED_BYTE, scratchByteBuffer);

			scratchIntBuffer.clear();

			glyph.image = pageImage;
			glyph.texCoords = pageImage.subImageCoords(pageX, pageY,
					glyph.width, glyph.height);
		}

		/*********************************************************************
		 * Returns an iterator for the specified glyphs, sorted either ascending
		 * or descending.
		 * 
		 * @param glyphs
		 *            The glyphs to iterate through.
		 * 
		 * @return An iterator through the sorted list of glyphs.
		 *********************************************************************/
		protected Iterator<Glyph> iter(List<Glyph> glyphs)
		{
			if (orderAscending)
				return glyphs.iterator();

			final ListIterator<Glyph> iter = glyphs.listIterator(glyphs.size());

			return new Iterator<Glyph>()
			{
				public boolean hasNext() { return iter.hasPrevious(); }
				public Glyph next() { return iter.previous(); }
				public void remove() { iter.remove(); }
			};
		}
	}

	/*************************************************************************
	 * Represents a character onscreen.
	 * 
	 * @author Kevin Glass, modified by Chris Molini
	 *************************************************************************/
	protected class Glyph
	{
		/**
		 * The code point in which this glyph is found.
		 **/
		protected int codePoint;

		/**
		 * The width of this glyph in pixels.
		 **/
		protected short width;

		/**
		 * The height of this glyph in pixels.
		 **/
		protected short height;

		/**
		 * True if the glyph isn't defined.
		 **/
		protected boolean isMissing;

		/**
		 * The shape drawn for this glyph.
		 **/
		protected Shape shape;

		/**
		 * The image that contains this glyph.
		 **/
		protected Image image;

		/**
		 * The calculated texture coordinates that correspond to the character
		 * location on the texture.
		 **/
		protected float[] texCoords;

		/*********************************************************************
		 * Create a new glyph from the specified information.
		 * 
		 * @param codePoint
		 *            The code point in which this glyph can be found.
		 * @param bounds
		 *            The bounds that this glyph can fill.
		 * @param vector
		 *            The vector this glyph is part of.
		 * @param index
		 *            The index of this glyph within the vector.
		 *********************************************************************/
		public Glyph(int codePoint, Rectangle bounds, GlyphVector vector,
				int index)
		{
			this.codePoint = codePoint;

			GlyphMetrics metrics = vector.getGlyphMetrics(index);
			int lsb = (int) metrics.getLSB();
			if (lsb > 0)
				lsb = 0;
			int rsb = (int) metrics.getRSB();
			if (rsb > 0)
				rsb = 0;

			int glyphWidth = bounds.width - lsb - rsb;
			int glyphHeight = bounds.height;

			if (glyphWidth > 0 && glyphHeight > 0)
			{
				width = (short) (glyphWidth + paddingLeft + paddingRight + 1);
				height = (short) (glyphHeight + paddingTop + paddingBottom + 1);
			}

			shape = vector.getGlyphOutline(index, -bounds.x + paddingLeft,
					-bounds.y + paddingBottom);

			isMissing = !getFont().canDisplay((char) codePoint);
		}

		/*********************************************************************
		 * Draws the character at the specified location.
		 * 
		 * Since characters can be represented on a series of GlyphPages, which
		 * means various images may hold the characters, certain checks are in
		 * place to ensure things render smoothly.
		 * 
		 * @param lastBound
		 *            The last image
		 * @param x
		 *            The x coordinate to draw at.
		 * @param y
		 *            The y coordinate to draw at.
		 * 
		 * @return The image this character used to render. This is necessary to
		 *         keep each character render aware of the last image used, in
		 *         case a texture change needs to be made.
		 *********************************************************************/
		public Image drawGlyph(Image lastBound, int x, int y)
		{
			if (image != null)
			{
				if (lastBound == null)
				{
					image.bind();
					GL11.glBegin(GL11.GL_QUADS);
				} else if (lastBound != image)
				{
					GL11.glEnd();
					image.bind();
					GL11.glBegin(GL11.GL_QUADS);
				}

				GL11.glTexCoord2f(texCoords[0], texCoords[1]);
				GL11.glVertex2f(x, y);

				GL11.glTexCoord2f(texCoords[2], texCoords[1]);
				GL11.glVertex2f(x + width, y);

				GL11.glTexCoord2f(texCoords[2], texCoords[3]);
				GL11.glVertex2f(x + width, y + height);

				GL11.glTexCoord2f(texCoords[0], texCoords[3]);
				GL11.glVertex2f(x, y + height);
			}
			return image;
		}
	}
}