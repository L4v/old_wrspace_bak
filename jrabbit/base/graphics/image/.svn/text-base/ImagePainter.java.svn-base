package org.jrabbit.base.graphics.image;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Stack;

import org.jrabbit.base.graphics.GLSettings;
import org.jrabbit.base.graphics.transforms.Color;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;

/*****************************************************************************
 * A class that manages creating and rendering to images. This allows for much
 * more control and utilization of Images.
 * 
 * This functionality is quite useful in a variety of situations. For example,
 * say your game has some brick walls. If a bullet hits a wall, you can render a
 * hole where you want to show the impact.
 * 
 * Other options are useful, too - rendering a complex, computationally
 * expensive object (like a large paragraph of text) to an image lets you
 * "cache" the resulting render and use it far more quickly in the future.
 * 
 * NOTE: This class can render to ANY texture, not just those created by Images.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ImagePainter
{
	/**
	 * Rendering to texture is designed to work in a "recursive" manner - that
	 * is, starting to render to another texture while already rendering to
	 * another will not cause the engine to crash. This Stack keeps track of all
	 * rendering implemented through this class.
	 **/
	private static Stack<Integer> activeFBOs = new Stack<Integer>();

	/*************************************************************************
	 * Loads a blank (completely transparent) image into memory that has the
	 * required dimensions.
	 * 
	 * @param width
	 *            The requested width of the texture.
	 * @param height
	 *            The requested height of the texture.
	 * 
	 * @return A blank texture that contains the indicated dimensions. The
	 *         resulting texture is actually Power-Of-Two, so it may be
	 *         significantly larger than is needed.
	 *************************************************************************/
	public static Texture createTexture(int width, int height)
	{
		try
		{
			return InternalTextureLoader.get().createTexture(width, height);
		}
		catch (IOException e)
		{
			// If this fails, the graphics card likely does
			// not have enough memory to allocate it.
			System.err.println("Could not create the required texture.");
			e.printStackTrace();
			return null;
		}
	}

	/*************************************************************************
	 * Creates a copy of the target image under the indicated name. This is done
	 * by creating a blank image of the same dimensions, and rendering the
	 * source image to it.
	 * 
	 * @param source
	 *            The image to copy.
	 * @param reference
	 *            The identifier of the copied image.
	 * 
	 * @return An Image that is a copy of the one specified.
	 *************************************************************************/
	public static Image copy(Image source, String reference)
	{
		Image image = new Image(reference, (int) source.width(),
				(int) source.height());

		drawTo(image);
		GL11.glTranslated(image.width() / 2, image.height() / 2, 0);
		GLSettings.Blend.replace();
		source.render();
		finishDrawing();

		return image;
	}

	/*************************************************************************
	 * Same as copy(), but the returned image is a scaled copy.
	 * 
	 * @param source
	 *            The image to copy.
	 * @param name
	 *            The identifier of the copied image.
	 * @param scale
	 *            How much to multiply the width and height of the image.
	 * 
	 * @return An image that is a copy of the one specified, but at a scaled
	 *         width and height.
	 * 
	 * @see copy
	 *************************************************************************/
	public static Image copyAtScale(Image source, String name, float scale)
	{
		return copyAtScale(source, name, scale, scale);
	}

	/*************************************************************************
	 * Same as copy(), but the returned image is scaled across both its width
	 * and its height.
	 * 
	 * @param source
	 *            The image to copy.
	 * @param name
	 *            The identifier of the copied image.
	 * @param scaleX
	 *            How much to multiply the width of the image.
	 * @param scaleY
	 *            How much to multiply the height of the image.
	 * 
	 * @return An image that is a copy of the one specified, but at a scaled
	 *         width and height.
	 * 
	 * @see #copy(Image, String)
	 *************************************************************************/
	public static Image copyAtScale(Image src, String name, double scaleX,
			double scaleY)
	{
		Image image = new Image(name, (int) (src.width() * scaleX),
				(int) (src.height() * scaleY));
		drawTo(image);
		GL11.glTranslated(image.width() / 2, image.height() / 2, 0);
		GL11.glScaled(scaleX, scaleY, 1);
		GLSettings.Blend.premultiplied();
		src.render();
		finishDrawing();
		return image;
	}

	/*************************************************************************
	 * Checks whether or not the graphics card and driver support FrameBuffers.
	 * 
	 * @return True if rendering to texture is possible, false if not.
	 *************************************************************************/
	public static boolean renderToTextureCapable()
	{
		return GLContext.getCapabilities().GL_EXT_framebuffer_object;
	}

	/*************************************************************************
	 * Begins drawing to the supplied Image.
	 * 
	 * @param image
	 *            The image to draw to.
	 * 
	 * @return True if the target is being rendered to, false if not.
	 *************************************************************************/
	public static boolean drawTo(Image image)
	{
		return drawTo(image.ID(), (int) image.width(), (int) image.height());
	}

	/*************************************************************************
	 * Begins drawing to the supplied texture.
	 * 
	 * @param texture
	 *            The texture to draw to.
	 * 
	 * @return True if the target is being rendered to, false if not.
	 *************************************************************************/
	public static boolean drawTo(Texture texture)
	{
		return drawTo(texture.getTextureID(), texture.getTextureHeight(),
				texture.getTextureWidth());
	}

	/*************************************************************************
	 * Begins drawing to the OpenGL texture referenced by the ID, bounded by the
	 * indicated width and height.
	 * 
	 * This stores the current Modelview Matrix, Color information (the color
	 * bound, blending function, etc) and the Viewport settings on the stack so
	 * that they can be restored after rendering has finished. After this call
	 * is finished, the viewport is set to the window dimensions, the bound
	 * color is opaque white, and the Blend setting defaults to Normal blending.
	 * 
	 * NOTE: The indicated dimensions should be less than or equal to those of
	 * the actual texture.
	 * 
	 * @param target
	 *            The ID of the texture on the graphics card.
	 * @param width
	 *            The width of the area to render to.
	 * 
	 * @return True if the target is being rendered to, false if not.
	 *************************************************************************/
	public static boolean drawTo(int target, int width, int height)
	{
		if (renderToTextureCapable())
		{
			activeFBOs.push(genFBO());
			bindFBO(activeFBOs.peek(), target);

			GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
			GLSettings.Blend.normal();
			GL11.glColor4f(1f, 1f, 1f, 1f);

			GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
			GL11.glViewport(0, 0, width, height);

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glPushMatrix();
			GL11.glLoadIdentity();
			GL11.glOrtho(0, width, 0, height, -1, 1);

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPushMatrix();
			GL11.glLoadIdentity();

			return true;
		}
		return false;
	}
	
	/*************************************************************************
	 * Attempts to create an FBO and return its ID.
	 * 
	 * @return If the FBO creation completed successfully, returns the ID. If
	 *         not, returns 0.
	 *************************************************************************/
	public static int genFBO()
	{
		if(renderToTextureCapable())
		{
			IntBuffer buffer = ByteBuffer.allocateDirect(4).order(ByteOrder.
					nativeOrder()).asIntBuffer();
			glGenFramebuffersEXT(buffer);
			int fBOID = buffer.get();
			checkFBO(fBOID);
			return fBOID;
		}
		return 0;
	}
	
	/*************************************************************************
	 * Destroys the indicated FBO.
	 * 
	 * @param fBOID
	 * 			  The ID of the FBO to destroy.
	 *************************************************************************/
	public static void deleteFBO(int fBOID)
	{
		glDeleteFramebuffersEXT(fBOID);
	}
	
	/*************************************************************************
	 * Binds the target FBO, rendering to the indicated target.
	 * 
	 * @param fBO
	 * 			  The FBO ID to use.
	 * @param target
	 * 			  The ID of the render target.
	 *************************************************************************/
	public static void bindFBO(int fBOID, int target)
	{
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fBOID);
		glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT,
				GL11.GL_TEXTURE_2D, target, 0);
	}
	
	/*************************************************************************
	 * Escapes drawing from an FBO and simply renders to the regular Display.
	 * 
	 * NOTE: This should be avoided unless the FBOs that are active are not
	 * managed by ImagePainter. Using this command while ImagePainter has its
	 * own active FBOs will result in undefined behavior.
	 *************************************************************************/
	public static void unbindFBO()
	{
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
	}

	/*************************************************************************
	 * Finishes the current render-to-texture if there is one. Restores the
	 * ModelView matrix, the viewport, and the color settings (including the
	 * blending function) to what they were before render-to-texture began.
	 * 
	 * @return True if a render-to-texture finished, false if not.
	 *************************************************************************/
	public static boolean finishDrawing()
	{
		if (renderToTextureCapable() && renderingToTexture())
		{
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glPopMatrix();

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPopMatrix();

			GL11.glPopAttrib();
			GL11.glPopAttrib();

			deleteFBO(activeFBOs.pop());

			if (renderingToTexture())
				glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, activeFBOs.peek());
			else
				unbindFBO();
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Continuously exits from rendering to texture, resolving each one until
	 * rendering is once again pointed to the Display.
	 *************************************************************************/
	public static void escapeDrawing()
	{
		while (renderingToTexture())
			finishDrawing();
	}

	/*************************************************************************
	 * Clears the current render target to that indicated color.
	 * 
	 * @param color
	 *            The color to wipe the screen to.
	 *************************************************************************/
	public static void wipe(Color color)
	{
		wipe(color.red(), color.green(), color.blue(), color.alpha());
	}

	/*************************************************************************
	 * Wipes the current render target to that indicated solid color.
	 * 
	 * @param red
	 *            The red value to clear the screen to.
	 * @param green
	 *            The green value to clear the screen to.
	 * @param blue
	 *            The blue value to clear the screen to.
	 *************************************************************************/
	public static void wipe(float red, float green, float blue)
	{
		wipe(red, green, blue, 1f);
	}

	/*************************************************************************
	 * Wipes the current render target to that indicated solid color.
	 * 
	 * @param red
	 *            The red value to clear the screen to.
	 * @param green
	 *            The green value to clear the screen to.
	 * @param blue
	 *            The blue value to clear the screen to.
	 * @param alpha
	 *            The alpha value to clear the screen to.
	 *************************************************************************/
	public static void wipe(float red, float green, float blue, float alpha)
	{
		if (renderingToTexture())
		{
			GL11.glClearColor(red, green, blue, alpha);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		}
	}

	/*************************************************************************
	 * Clears the current render target to transparent black.
	 *************************************************************************/
	public static void wipe()
	{
		if (renderingToTexture())
		{
			GL11.glClearColor(0, 0, 0, 0);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		}
	}

	/*************************************************************************
	 * Checks whether or not OpenGL is rendering to texture.
	 * 
	 * NOTE: This method only checks values within this class - it doesn't
	 * interface with OpenGL to determine the render target. As such, the
	 * developer can conceivably use external means to render to texture, and
	 * this method will not register that.
	 * 
	 * @return True if a texture is being rendered to, false if not.
	 *************************************************************************/
	public static boolean renderingToTexture()
	{
		return !activeFBOs.isEmpty();
	}

	/*************************************************************************
	 * Determines whether or not a particular FBO is "intact" (is working
	 * properly). If not, a RuntimeException is thrown.
	 * 
	 * @param FBOID
	 *            The ID of the FrameBuffer to check.
	 *************************************************************************/
	public static void checkFBO(int FBOID)
	{
		int status = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT);

		switch (status)
		{
			case GL_FRAMEBUFFER_COMPLETE_EXT:
				break;
			case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT:
				throw new RuntimeException("FrameBuffer: " + FBOID + ", has c" +
						"aused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exc" +
						"eption");
			case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT:
				throw new RuntimeException("FrameBuffer: " + FBOID + ", has c" +
						"aused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT" +
						"_EXT exception");
			case GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT:
				throw new RuntimeException("FrameBuffer: " + FBOID + ", has c" +
						"aused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exc" +
						"eption");
			case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT:
				throw new RuntimeException("FrameBuffer: " + FBOID + ", has c" +
						"aused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT ex" +
						"ception");
			case GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT:
				throw new RuntimeException("FrameBuffer: " + FBOID + ", has c" +
						"aused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT except" +
						"ion");
			case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT:
				throw new RuntimeException("FrameBuffer: " + FBOID + ", has c" +
						"aused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT ex" +
						"ception");
			default:
				throw new RuntimeException("Unexpected reply from glCheckFram" +
						"ebufferStatusEXT: " + status);
		}
	}
}