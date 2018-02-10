package org.jrabbit.base.graphics.font.renderer;

import org.jrabbit.base.data.DataController;

/*****************************************************************************
 * jRabbit's Fonts aren't really intended to be "customizable," but are instead
 * "pluggable" - they have some core functionality which is dependent upon an
 * object which can itself be customized.
 * 
 * Basically, everything a Font does is based on its FontRenderer. To change how
 * the Font works, change the Renderer.
 * 
 * A FontRenderer is intended to take care of all the OpenGL data and
 * calculations for the Font. A Font simply tells its renderer what to do and
 * interprets the results.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface FontRenderer extends DataController
{
	/*************************************************************************
	 * Renders a String of text at the specified x and y coordinates relative to
	 * the current OpenGL Modelview matrix. The specified x and y values should
	 * correspond to the top-left of the rendered String.
	 * 
	 * @param x
	 *            The x coordinate of the top-left of the first character.
	 * @param y
	 *            The y coordinate of the top-left of the first character.
	 * @param text
	 *            The text to display.
	 *************************************************************************/
	public abstract void render(float x, float y, String text);

	/*************************************************************************
	 * Computes how wide a String will be when rendered.
	 * 
	 * @param text
	 *            The text to measure.
	 * 
	 * @return How wide the supplied String is when drawn by this renderer.
	 *************************************************************************/
	public abstract float widthOf(String text);

	/*************************************************************************
	 * Determines how tall each line of text this renderer draws is.
	 * 
	 * @return The height of a standard line of text.
	 *************************************************************************/
	public abstract float lineHeight();
}
