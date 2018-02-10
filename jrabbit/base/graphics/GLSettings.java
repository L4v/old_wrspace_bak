package org.jrabbit.base.graphics;

import static org.lwjgl.opengl.GL11.*;

/*****************************************************************************
 * GLSettings contains a series of LWJGL "macros" to help define settings in
 * OpenGL.
 * 
 * These are very simple, the exact same effects can be obtained via straight
 * LWJGL commands. They are provided to be a little more beginner-friendly and
 * intuitive, that's all.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GLSettings
{
	/*************************************************************************
	 * Sets the default OpenGL settings:
	 * 
	 * 		Depth testing - disabled. 
	 * 		Lighting - disabled. 
	 * 		Textures - enabled.
	 * 		Blending - enabled, and set to the default Normal blending.
	 *************************************************************************/
	public static void default2D()
	{
		Enable.depth(false);
		Enable.lighting(false);
		Enable.textures(true);
		Enable.blending(true);
		Blend.normal();
	}

	/*************************************************************************
	 * Handles enabling and disabling general OpenGL settings.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class Enable
	{
		/*********************************************************************
		 * Controls whether textures can be used.
		 * 
		 * @param enable
		 *            Whether or not to use textures.
		 *********************************************************************/
		public static void textures(boolean enable)
		{
			if (enable)
				glEnable(GL_TEXTURE_2D);
			else
				glDisable(GL_TEXTURE_2D);
		}

		/*********************************************************************
		 * Controls whether lighting can be used.
		 * 
		 * NOTE: Lighting is per-vertex and the default way of rendering images
		 * are simple textured quads, which may lead to poor quality. There is
		 * no attempt to accommodate lighting in the base jRabbit graphics
		 * package; support for lighting must come from the developer.
		 * 
		 * @param enable
		 *            Whether or not to use lighting.
		 *********************************************************************/
		public static void lighting(boolean enable)
		{
			if (enable)
				glEnable(GL_LIGHTING);
			else
				glDisable(GL_LIGHTING);
		}

		/*********************************************************************
		 * Controls whether depth calculations can be used.
		 * 
		 * NOTE: This really shouldn't be used in a 2D context unless you know
		 * what you're doing.
		 * 
		 * @param enable
		 *            Whether or not to use depth testing.
		 *********************************************************************/
		public static void depth(boolean enable)
		{
			if (enable)
				glEnable(GL_DEPTH_TEST);
			else
				glDisable(GL_DEPTH_TEST);
		}

		/*********************************************************************
		 * Controls whether blending calculations can be used. If blending is
		 * turned off, simple color replacement occurs and alpha transparency is
		 * ignored. Obviously, this is usually a bad thing for 2D graphics.
		 * 
		 * @param enable
		 *            Whether or not to use blending.
		 *********************************************************************/
		public static void blending(boolean enable)
		{
			if (enable)
				glEnable(GL_BLEND);
			else
				glDisable(GL_BLEND);
		}
	}

	/*************************************************************************
	 * A static class containing some methods to set the default blending
	 * functions.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class Blend
	{
		/*********************************************************************
		 * Sets normal blending.
		 * 
		 * @see glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
		 *********************************************************************/
		public static void normal()
		{
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}

		/*********************************************************************
		 * Sets replacement blending.
		 * 
		 * @see glBlendFunc(GL_ONE, GL_ZERO)
		 *********************************************************************/
		public static void replace()
		{
			glBlendFunc(GL_ONE, GL_ZERO);
		}

		/*********************************************************************
		 * Sets additive blending.
		 * 
		 * @see glBlendFunc(GL_SRC_ALPHA, GL_ONE)
		 *********************************************************************/
		public static void additive()
		{
			glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		}

		/*********************************************************************
		 * Sets overlay blending.
		 * 
		 * NOTE: This does not work well without premultiplied alpha images. It
		 * is safe to use with completely opaque images, but using alpha becomes
		 * difficult.
		 * 
		 * @see glBlendFunc(GL_DST_COLOR, GL_ONE_MINUS_SRC_ALPHA)
		 *********************************************************************/
		public static void overlay()
		{
			// USE PREMULTIPLIED ALPHA IMAGES.
			glBlendFunc(GL_DST_COLOR, GL_ZERO);
		}

		/*********************************************************************
		 * Sets blending to work with premultiplied alpha images. Don't use it
		 * with images that are not premultiplied alpha.
		 * 
		 * @see glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA)
		 *********************************************************************/
		public static void premultiplied()
		{
			// USE PREMULTIPLIED ALPHA IMAGES.
			glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		}

		/*********************************************************************
		 * Defines the OpenGL color mask. This flags which color channels can be
		 * altered in rendering.
		 * 
		 * @param red
		 *            Whether or not to allow changes to the red channel.
		 * @param green
		 *            Whether or not to allow changes to the green channel.
		 * @param blue
		 *            Whether or not to allow changes to the blue channel.
		 * @param alpha
		 *            Whether or not to allow changes to the alpha channel.
		 *********************************************************************/
		public static void colorMask(boolean red, boolean green, boolean blue,
				boolean alpha)
		{
			glColorMask(red, green, blue, alpha);
		}
	}
}