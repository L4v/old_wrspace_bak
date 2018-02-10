package org.jrabbit.base.graphics.skins.text;

import org.jrabbit.base.graphics.GLSettings;
import org.jrabbit.base.graphics.font.Font;
import org.jrabbit.base.graphics.image.Image;
import org.jrabbit.base.graphics.image.ImagePainter;
import org.jrabbit.base.graphics.transforms.Color;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * RenderedTextSkin builds on the functionality provided by TextSkin to render
 * its drawn text onto an image. This provides an immense speed boost over
 * rendering each character in the font.
 * 
 * However, there are two limitations in rendering the text like this. Firstly,
 * the text shouldn't be changed that often - doing so will require a re-render
 * of the text, which is slower. If the text to render can change quite often,
 * usually it is better to simply stick with a basic TextSkin.
 * 
 * Secondly, a RenderedTextSkin deals with Images outside of the ImageCache, and
 * thus they are not affected by the default, simplified memory management
 * system already in place. However, as Skins are intended to be lightweight, it
 * doesn't make sense for a RenderedTextSkin to need to be manually destroyed.
 * The solution to this problem is relatively simple. As Images are created by
 * RenderedTextSkins, they are added to a list in the static class RenderedText.
 * Thereafter, one can destroy all Image data by using the methods in
 * RenderedText.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class RenderedTextSkin extends TextSkin
{
	/**
	 * The Image used to render text.
	 **/
	protected Image renderSurface;

	/*************************************************************************
	 * Creates a RenderedTextSkin that uses the default font to render the 
	 * supplied text.
	 * 
	 * @param text
	 *            The text to render.
	 *************************************************************************/
	public RenderedTextSkin(String text)
	{
		super(text);
	}

	/*************************************************************************
	 * Creates a RenderedTextSkin that uses the indicated font to render the 
	 * supplied text.
	 * 
	 * @param text
	 *            The text to render.
	 * @param font
	 *            The identifier of the Font to render.
	 *************************************************************************/
	public RenderedTextSkin(String text, String font)
	{
		super(text, font);
	}

	/*************************************************************************
	 * RenderedTextSkin overrides the default formatting to ensure that it is
	 * formatted and indented within a 512-width paragraph.
	 ***************************************************************/ @Override
	protected void defaultFormattting()
	{
		formatWidth = 512;
		format = true;
		indent = true;
		reformat = true;
		checkFormat();
	}

	/*************************************************************************
	 * Accesses the RenderedTextSkin's Image.
	 * 
	 * @return The Image used to pre-render text.
	 *************************************************************************/
	public Image renderSurface()
	{
		return renderSurface;
	}

	/*************************************************************************
	 * Calculates the list of Strings to use for rendering as in TextSprite, and
	 * then renders the text to the Image.
	 * 
	 * @param font
	 * 			  The Font to use in formatting and rendering.
	 ***************************************************************/ @Override
	protected void format(Font font)
	{
		// Formats the text in the same manner as TextSkin.
		super.format(font);

		// Adjusts the image being used to render the text.
		if (renderSurface == null)
			RenderedText.add(renderSurface = new Image("", (int) width, 
					(int) height));
		else if (width > renderSurface.textureWidth()
				|| height > renderSurface.textureHeight())
		{
			RenderedText.destroy(renderSurface);
			renderSurface = new Image("", (int) width, (int) height);
		} else if (width != renderSurface.width()
				|| height != renderSurface.height())
			renderSurface.setDimensions((int) width, (int) height);

		// Renders to the image.
		if (ImagePainter.drawTo(renderSurface))
		{
			ImagePainter.wipe();
			GLSettings.Blend.premultiplied();
			Color.WHITE.bind();
			font.render(formattedText);
			ImagePainter.finishDrawing();
		}
	}

	/*************************************************************************
	 * Checks to if the rendered text needs to be updated, then draws the Image
	 * containing the rendered text.
	 ***************************************************************/ @Override
	public void render()
	{
		checkFormat();
		GL11.glTranslatef(width / 2, height / 2, 0);
		renderSurface.render();
		GL11.glTranslatef(width / -2, height / -2, 0);
	}
}