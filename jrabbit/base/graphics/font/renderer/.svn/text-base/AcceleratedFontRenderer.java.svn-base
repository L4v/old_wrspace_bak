package org.jrabbit.base.graphics.font.renderer;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * AcceleratedFontRenderer provides a default abstract FontRenderer class that
 * uses DisplayLists to hasten rendering.
 * 
 * Both default FontRenderers extend this class, but have DisplayList
 * acceleration turned off by default. This is to allow them to support rapidly
 * changing text more efficiently.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class AcceleratedFontRenderer implements FontRenderer
{
	/**
	 * This class can compile display lists to "remember" Strings that have been
	 * drawn. Doing so can potentially increase rendering speed significantly -
	 * IF only static strings are being drawn. If the Strings being drawn are
	 * constantly changing, then every String creates a new display list, and
	 * you end up never using any display lists to speed up rendering and yet
	 * are burdened with the overhead of creating them. For this reason, display
	 * lists are initially turned off.
	 **/
	protected boolean accelerate = false;

	/**
	 * The maximum number of display lists stored.
	 **/
	protected int maxDL = 200;

	/**
	 * When the DisplayLists are created, the ID of the first one is stored
	 * here.
	 **/
	protected int baseDL = -1;

	/**
	 * The eldest display list ID. If the cache of display lists becomes full,
	 * then the oldest DisplayList is removed to make room for the newest one.
	 **/
	protected int eldestDLID;

	/**
	 * The eldest display list. Used to simplify replacing the oldest
	 * DisplayList with a new one.
	 **/
	protected TextDL eldestDL;

	/**
	 * The display list cache for rendered text. It's slightly modified to make
	 * it easier to dynamically replace old display lists when the cache is
	 * full.
	 **/
	protected LinkedHashMap<String, TextDL> displayLists = 
		new LinkedHashMap<String, TextDL>(maxDL, 1, true)
	{
		private static final long serialVersionUID = -5418127191798714967L;

		protected boolean removeEldestEntry(Entry<String, TextDL> eldest)
		{
			eldestDL = (TextDL) eldest.getValue();
			eldestDLID = eldestDL.id;
			return false;
		}
	};

	/*************************************************************************
	 * Tells the font renderer whether or not it should attempt to hasten
	 * rendering of text.
	 * 
	 * If a particular font is only going to be rendering large, static amounts
	 * of text, it's strongly recommended to turn this on. Likewise, if the text
	 * being rendered is dynamic and expected to change, turn this OFF.
	 * 
	 * @param accel
	 *            Whether or not to speed up rendering with display lists.
	 *************************************************************************/
	public void useAcceleration(boolean accel)
	{
		accelerate = accel;
	}

	/*************************************************************************
	 * If acceleration is enabled, create() will allocate the desired display
	 * lists.
	 ***************************************************************/ @Override
	public void create()
	{
		if (accelerate)
		{
			// Check if the font can register DisplayLists
			// to hasten drawing.
			baseDL = GL11.glGenLists(maxDL);
			if (baseDL == 0)
				accelerate = false;
		}
	}

	/*************************************************************************
	 * First checks to see if display lists are being used. If so, then checks
	 * to see if a Display List has been defined for rendering.
	 * 
	 * If no DisplayLists have been defined, then the Renderer compiles and
	 * executes one via drawString(), and stores it for later use.
	 * 
	 * If display lists are disabled, this method delegates rendering to
	 * drawString().
	 * 
	 * @param x
	 *            The x coordinate to render at.
	 * @param y
	 *            The y coordinate to render at.
	 * @param text
	 *            The text to render.
	 ***************************************************************/ @Override
	public void render(float x, float y, String text)
	{
		 if(text == null)
			 return;
		 
		GL11.glTranslatef(x, y, 0);

		if (accelerate)
		{
			TextDL displayList = (TextDL) displayLists.get(text);
			if (displayList != null)
				GL11.glCallList(displayList.id);
			else
			{
				// Compile a new display list.
				displayList = new TextDL();
				displayList.text = text;
				int displayListCount = displayLists.size();
				if (displayListCount < maxDL)
					displayList.id = baseDL + displayListCount;
				else
				{
					displayList.id = eldestDLID;
					displayLists.remove(eldestDL.text);
				}

				displayLists.put(text, displayList);

				GL11.glNewList(displayList.id, GL11.GL_COMPILE_AND_EXECUTE);
				drawString(text);
				GL11.glEndList();
			}
		} 
		else
			drawString(text);

		GL11.glTranslatef(-x, -y, 0);
	}

	/*************************************************************************
	 * Measures the width of a String of text. Attempts to utilize the cached
	 * display lists to learn the width of the String; if no such information is
	 * stored, then delegates to measureString().
	 * 
	 * @param text
	 *            The String to measure.
	 * 
	 * @return The width of the text when rendered.
	 ***************************************************************/ @Override
	public float widthOf(String text)
	{
		if (text == null || text.length() == 0)
			return 0;

		if (accelerate)
		{
			TextDL cachedText = displayLists.get(text);
			if (cachedText != null)
			{
				if (cachedText.width != -1)
					return cachedText.width;
				cachedText.width = measureString(text);
				return cachedText.width;
			}
		}
		return measureString(text);
	}

	/*************************************************************************
	 * Directly measures the width of the supplied String.
	 * 
	 * @param text
	 *            The String to measure.
	 * 
	 * @return The width of the text when rendered.
	 *************************************************************************/
	protected abstract float measureString(String text);

	/*************************************************************************
	 * Renders the supplied text. The current location of the Modelview matrix
	 * is the top-left of the text drawn.
	 * 
	 * @param text
	 *            The text to draw.
	 *************************************************************************/
	protected abstract void drawString(String text);

	/*************************************************************************
	 * Represents a display list that renders text.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class TextDL
	{
		/**
		 * The ID for the DisplayList.
		 **/
		public int id;

		/**
		 * The width of the text rendered.
		 **/
		public float width = -1;

		/**
		 * The text that the display list holds.
		 **/
		public String text;
	}
}