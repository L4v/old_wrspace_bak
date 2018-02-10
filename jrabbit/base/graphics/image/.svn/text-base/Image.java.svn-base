package org.jrabbit.base.graphics.image;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.data.DataController;
import org.jrabbit.base.data.Referenced;
import org.jrabbit.base.data.loading.Loader;
import org.jrabbit.base.data.loading.SystemLoader;
import org.jrabbit.base.data.loading.URLLoader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/*****************************************************************************
 * An image is just that - image data on the graphics card, with methods to
 * simplify rendering and interacting with it.
 * 
 * It's assumed that most images will be loaded from file (as indicated by a
 * supplied String). However, it is possible to create an image via different
 * methods, including loading a texture externally and then supplying it in the
 * constructor (example use of this - loading an image via URL, from the web).
 * 
 * Images automatically attempt to accelerate their rendering with Display
 * Lists. This gives a (small) gain in performance.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Image implements Referenced, DataController, Renderable
{
	/**
	 * The texture filter value that results in smooth textures.
	 **/
	public static final int SMOOTH = GL11.GL_LINEAR;

	/**
	 * The texture filter value that results in pixelated textures.
	 **/
	public static final int PIXELATE = GL11.GL_NEAREST;

	/*************************************************************************
	 * Loads a texture with the indicated Loader.
	 * 
	 * @param loader
	 *            A pre-constructed loader that points to the image to load.
	 * 
	 * @return The texture loaded from the specified image.
	 *************************************************************************/
	public static final Texture load(Loader loader)
	{
		Texture texture = null;
		if (loader != null)
		{
			try
			{
				InputStream stream = loader.stream();
				texture = TextureLoader.getTexture(loader.type(), stream);
				stream.close();
			}
			catch (IOException e)
			{
				System.err
						.println("Problem loading image: " + loader.path());
				e.printStackTrace();
			}
		}
		return texture;
	}

	/**
	 * The reference that identifies this image.
	 **/
	protected String reference;

	/**
	 * Whether or not this image has a smoothing filter applied when it is
	 * scaled in size.
	 **/
	protected boolean smooth;

	/**
	 * The texture ID on the GPU.
	 **/
	protected int textureID;

	/**
	 * The width of the image, in pixels.
	 **/
	protected int width;

	/**
	 * The height of the image, in pixels.
	 **/
	protected int height;

	/**
	 * Since textures are PoT, and source images can have any dimension, the
	 * texture may have different dimensions than the image upon it. This
	 * indicates the width of the texture itself.
	 **/
	protected int textureWidth;

	/**
	 * The height of the texture that the image is placed upon.
	 **/
	protected int textureHeight;

	/**
	 * The ratio of the image width to the texture width. Used for texture
	 * coordinates.
	 **/
	protected float widthRatio;

	/**
	 * The ratio of the image height to the texture height. Used for texture
	 * coordinates.
	 **/
	protected float heightRatio;

	/**
	 * The ID of the displayList used by this image.
	 **/
	protected int dLID;

	/**
	 * Whether or not this image has an alpha channel.
	 **/
	protected boolean alpha;

	/**
	 * An image can have individual sections of it rendered. These sections are
	 * considered "sub-images." This list dynamically expands as new sections of
	 * the image are required for rendering.
	 **/
	protected SubImage[] subImages;

	/**
	 * The number of sub-images compiled and ready at any given time.
	 **/
	protected int numSubImages;

	/*************************************************************************
	 * Creates an Image from a single reference. This reference will be used to
	 * load the image data from the system.
	 * 
	 * @param reference
	 *            The filepath that will both identify the image and be used
	 *            to load the required texture data.
	 *************************************************************************/
	public Image(String reference)
	{
		this(reference, load(new SystemLoader(reference)));
	}

	/*************************************************************************
	 * Creates an Image from the indicated reference and filepath.
	 * 
	 * @param reference
	 *            The String that will identify the image in the Cache.
	 * @param filepath
	 *            The filepath used to load image data from the system.
	 *************************************************************************/
	public Image(String reference, String filepath)
	{
		this(reference, load(new SystemLoader(filepath)));
	}

	/*************************************************************************
	 * Creates an Image from a URL. This attempts to load the image via URL; and
	 * so if the image is on the web, a connection will be formed and it will
	 * attempt to be loaded.
	 * 
	 * @param url
	 *            The web address of the image.
	 *************************************************************************/
	public Image(URL url)
	{
		this(url.getPath(), load(new URLLoader(url)));
	}

	/*************************************************************************
	 * Creates an Image from the indicated reference and URL. The image can be
	 * on the web.
	 * 
	 * @param reference
	 *            The String that will identify the image in the Cache.
	 * @param url
	 *            The web address of the source image.
	 *************************************************************************/
	public Image(String reference, URL url)
	{
		this(reference, load(new URLLoader(url)));
	}

	/*************************************************************************
	 * Creates an Image from the indicated loader.
	 * 
	 * @param loader
	 *            The object that provides access to the source image.
	 *************************************************************************/
	public Image(Loader loader)
	{
		this(loader.path(), load(loader));
	}

	/*************************************************************************
	 * Creates an Image from the indicated loader.
	 * 
	 * @param reference
	 *            The String that will identify the image in the Cache.
	 * @param loader
	 *            The object that provides access to the source image.
	 *************************************************************************/
	public Image(String reference, Loader loader)
	{
		this(reference, load(loader));
	}

	/*************************************************************************
	 * Creates a blank Image with the indicated dimensions.
	 * 
	 * NOTE: Again, textures must be PoT, so supplying careless dimensions will
	 * result in a waste of graphical memory.
	 * 
	 * @param reference
	 *            The String that will identify the image in the Cache.
	 * @param width
	 *            The width of the image.
	 * @param height
	 *            The height of the image.
	 *************************************************************************/
	public Image(String reference, int width, int height)
	{
		this(reference, ImagePainter.createTexture(width, height));
	}

	/*************************************************************************
	 * Creates an Image with the indicated reference and the already created
	 * Texture data.
	 * 
	 * @param reference
	 *            The String that will identify the image in the Cache.
	 * @param texture
	 *            The texture object representing data on the graphics card.
	 *************************************************************************/
	public Image(String reference, Texture texture)
	{
		this(reference, texture.getTextureID(), texture.getImageWidth(),
				texture.getImageHeight(), texture.getTextureWidth(), texture
						.getTextureHeight(), texture.hasAlpha());
	}

	/*************************************************************************
	 * Creates an Image with the supplied reference and the indicated values.
	 * 
	 * @param reference
	 *            The String that will identify the image in the Cache.
	 * @param textureID
	 *            The ID of the texture on the graphics card.
	 * @param width
	 *            The width of the image to render.
	 * @param height
	 *            The height of the image to render.
	 * @param textureWidth
	 *            The width of the texture that the image is on.
	 * @param textureHeight
	 *            The height of the texture that the image is on.
	 * @param alphaChannel
	 *            Whether or not the texture has an alpha channel.
	 *************************************************************************/
	public Image(String reference, int textureID, int width, int height,
			int textureWidth, int textureHeight, boolean alphaChannel)
	{
		this.reference = reference;
		this.textureID = textureID;
		this.width = width;
		this.height = height;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.alpha = alphaChannel;
		widthRatio = (float) width / textureWidth;
		heightRatio = (float) height / textureHeight;
		subImages = new SubImage[20];
		smooth(true);
		create();
	}

	/*************************************************************************
	 * Creates the display list used to accelerate rendering. If a display list
	 * was already active (i.e., the display list ID was not 0) then it is
	 * destroyed before a new one is made.
	 *************************************************************************/
	private void compileDisplayList()
	{
		if (dLID != 0)
		{
			GL11.glDeleteLists(dLID, 1);
		}

		dLID = GL11.glGenLists(1);
		GL11.glNewList(dLID, GL11.GL_COMPILE);

		renderNoAcceleration();

		GL11.glEndList();
	}

	/*************************************************************************
	 * Compiles the display list. Since image creation already requires a valid
	 * texture, we really don't have very much left to do.
	 ***************************************************************/ @Override
	public void create()
	{
		compileDisplayList();
	}

	/*************************************************************************
	 * Destroys all graphics data associated with this texture. Nothing is
	 * recoverable after this method.
	 ***************************************************************/ @Override
	public void destroy()
	{
		if (dLID != 0)
		{
			GL11.glDeleteLists(dLID, 1);
			dLID = 0;
		}
		if (textureID != 0)
		{
			GL11.glDeleteTextures(textureID);
			textureID = 0;
		}
		for (SubImage sub : subImages)
		{
			if (sub != null)
				sub.destroy();
		}
	}

	/*************************************************************************
	 * Checks whether the image is valid.
	 * 
	 * @return Whether or not the Image is "intact" and can still be used for
	 *         rendering.
	 ***************************************************************/ @Override
	public boolean valid()
	{
		return textureID != 0 && dLID != 0;
	}

	/*************************************************************************
	 * Accesses the Image's reference.
	 * 
	 * @return The identifier for this Image.
	 ***************************************************************/ @Override
	public String reference()
	{
		return reference;
	}

	/*************************************************************************
	 * Redefines the width this image uses and refreshes the display list.
	 * 
	 * @param width
	 *            The new width of the image.
	 *************************************************************************/
	public void setWidth(int width)
	{
		if (this.width != width)
		{
			this.width = width;
			widthRatio = (float) width / textureWidth;
			compileDisplayList();
		}
	}

	/*************************************************************************
	 * Access the dimensions of the image.
	 * 
	 * @return The width of the image being drawn, in pixels.
	 *************************************************************************/
	public int width()
	{
		return width;
	}

	/*************************************************************************
	 * Redefines the height this image uses and refreshes the display list.
	 * 
	 * @param height
	 *            The new height of the image.
	 *************************************************************************/
	public void setHeight(int height)
	{
		if (this.height != height)
		{
			this.height = height;
			heightRatio = (float) height / textureHeight;
			compileDisplayList();
		}
	}

	/*************************************************************************
	 * Access the dimensions of the image.
	 * 
	 * @return The height of the image being drawn, in pixels.
	 *************************************************************************/
	public int height()
	{
		return height;
	}

	/*************************************************************************
	 * Redefines the dimensions this image uses and refreshes the display list.
	 * 
	 * @param width
	 *            The new width of the image.
	 * @param height
	 *            The new height of the image.
	 *************************************************************************/
	public void setDimensions(int width, int height)
	{
		if (this.width != width || this.height != height)
		{
			this.width = width;
			widthRatio = (float) width / textureWidth;
			this.height = height;
			heightRatio = (float) height / textureHeight;
			compileDisplayList();
		}
	}

	/*************************************************************************
	 * Accesses the dimensions of the texture used by this image.
	 * 
	 * @return The width of the texture.
	 *************************************************************************/
	public int textureWidth()
	{
		return textureWidth;
	}

	/*************************************************************************
	 * Accesses the dimensions of the texture used by this image.
	 * 
	 * @return The height of the texture.
	 *************************************************************************/
	public int textureHeight()
	{
		return textureHeight;
	}

	/*************************************************************************
	 * Accesses the dimensions of the image.
	 * 
	 * @return The proportion of the image width to that of the texture.
	 *************************************************************************/
	public float widthRatio()
	{
		return widthRatio;
	}

	/*************************************************************************
	 * Accesses the dimensions of the image.
	 * 
	 * @return The proportion of the image height to that of the texture.
	 *************************************************************************/
	public float heightRatio()
	{
		return heightRatio;
	}

	/*************************************************************************
	 * Accesses the texture ID.
	 * 
	 * @return The integer that indicates which texture to use on the graphics
	 *         card.
	 *************************************************************************/
	public int ID()
	{
		return textureID;
	}

	/*************************************************************************
	 * Learns if the texture has alpha.
	 * 
	 * @return Whether or not the texture has a transparency channel.
	 *************************************************************************/
	public boolean hasAlpha()
	{
		return alpha;
	}

	/*************************************************************************
	 * Calls OpenGL so that operations that involve textures use the texture
	 * referenced by this Image.
	 *************************************************************************/
	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}

	/*************************************************************************
	 * Defines the appearance of the Image when scaled.
	 * 
	 * @param smooth
	 *            True if a smoothing filter should be applied when the image
	 *            is scaled or rotated, false if a pixelated appearance is
	 *            desired.
	 *************************************************************************/
	public void smooth(boolean smooth)
	{
		if (this.smooth != smooth)
		{
			this.smooth = smooth;
			int filter = smooth ? SMOOTH : PIXELATE;
			bind();
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MIN_FILTER, filter);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, filter);
		}
	}

	/*************************************************************************
	 * Learns which scaling filter the Image is using.
	 * 
	 * @return True if the image is being smoothed, false if it is pixelated.
	 *************************************************************************/
	public boolean smoothing()
	{
		return smooth;
	}

	/*************************************************************************
	 * Draws the image, using a Display List for acceleration.
	 ***************************************************************/ @Override
	public void render()
	{
		GL11.glCallList(dLID);
	}

	/*************************************************************************
	 * Renders the image without using a Display List, manually passing in all
	 * required vertex and texture coordinate data.
	 *************************************************************************/
	public void renderNoAcceleration()
	{
		bind();
		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(-width / 2, -height / 2);

		GL11.glTexCoord2f(widthRatio, 0);
		GL11.glVertex2f(width / 2, -height / 2);

		GL11.glTexCoord2f(0, heightRatio);
		GL11.glVertex2f(-width / 2, height / 2);

		GL11.glTexCoord2f(widthRatio, heightRatio);
		GL11.glVertex2f(width / 2, height / 2);

		GL11.glEnd();
	}

	/*************************************************************************
	 * Accesses the texture data of the image, in bytes.
	 * 
	 * The format is dependent on the texture itself; if the image has alpha,
	 * then the pattern is 4 bytes (R/G/B/A) to a pixel; if not, it is 3
	 * (R/G/B).
	 * 
	 * @return An array of bytes that represents the texture data.
	 *************************************************************************/
	public byte[] data()
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer((alpha ? 4 : 3)
				* textureWidth * textureHeight);
		bind();
		GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, alpha ? GL11.GL_RGBA
				: GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
		byte[] data = new byte[buffer.limit()];
		buffer.get(data);
		buffer.clear();
		return data;
	}

	/*************************************************************************
	 * Renders a sub-Image without any acceleration, manually passing in the
	 * data for each vertice and texture coordinate.
	 * 
	 * @param coordinates
	 *            The coordinates that detail which section of the image to
	 *            render. These are not pixel values, but rather percentages.
	 *            The values in the array should be {x1, y1, x2, y2}, where the
	 *            first pair of values mark the upper left of the section and
	 *            the second pair mark the lower right.
	 * 
	 *            NOTE: The percentages should be coordinates for the image, not
	 *            for the texture itself.
	 *************************************************************************/
	public void renderSubImage(float[] coordinates)
	{
		float w = Math.abs(coordinates[2] - coordinates[0]) * textureWidth()/2;
		float h = Math.abs(coordinates[3] - coordinates[1]) * textureHeight()/2;

		bind();
		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

			GL11.glTexCoord2f(coordinates[0], coordinates[1]);
			GL11.glVertex2f(-w, -h);
	
			GL11.glTexCoord2f(coordinates[2], coordinates[1]);
			GL11.glVertex2f(w, -h);
	
			GL11.glTexCoord2f(coordinates[0], coordinates[3]);
			GL11.glVertex2f(-w, h);
	
			GL11.glTexCoord2f(coordinates[2], coordinates[3]);
			GL11.glVertex2f(w, h);

		GL11.glEnd();
	}

	/*************************************************************************
	 * Renders the indicated, pre-compiled sub image. This call utilizes display
	 * lists for acceleration.
	 * 
	 * @param id
	 *            The place in the array of compiled sub-images to use.
	 *************************************************************************/
	public void renderSubImage(int id)
	{
		GL11.glCallList(subImages[id].dLID);
	}

	/*************************************************************************
	 * Renders the indicated, pre-compiled sub image. This call does not use
	 * acceleration, but instead manually handles each texture vertex.
	 * 
	 * @param id
	 *            The place in the array of compiled sub-images to use.
	 *************************************************************************/
	public void renderSubImageNoAcceleration(int id)
	{
		renderSubImage(subImages[id].coordinates);
	}

	/*************************************************************************
	 * Calculates a series of sub-image coordinates from the indicated pixel
	 * values.
	 * 
	 * @param x
	 *            The x coordinate of the top-left pixel of the target
	 *            section.
	 * @param y
	 *            The y coordinate of the top-left pixel of the target
	 *            section.
	 * @param width
	 *            The width of the target section, in pixels.
	 * @param height
	 *            The height of the target section, in pixels.
	 * 
	 * @return The calculated coordinates that detail which section of the image
	 *         to use. These are not pixel values, but rather percentages. The
	 *         values in the array should be {x1, y1, x2, y2}, where the first
	 *         pair of values mark the upper left of the section and the second
	 *         pair mark the lower right.
	 *************************************************************************/
	public float[] subImageCoords(float x, float y, float width, float height)
	{
		return new float[] { x / textureWidth(), y / textureHeight(), 
				(x + width) / textureWidth(), (y + height) / textureHeight()};
	}

	/*************************************************************************
	 * Returns the ID of an accelerated sub-image with the indicated pixel
	 * values. If no identical sub-image exists, one is compiled and the list of
	 * sub-images is enlarged to fit it.
	 * 
	 * @param x
	 *            The x coordinate of the top-left pixel of the target
	 *            section.
	 * @param y
	 *            The y coordinate of the top-left pixel of the target
	 *            section.
	 * @param width
	 *            The width of the target section, in pixels.
	 * @param height
	 *            The height of the target section, in pixels.
	 * 
	 * @return The place in the internal array of sub-images.
	 *************************************************************************/
	public int subImage(float x, float y, float width, float height)
	{
		return subImage(subImageCoords(x, y, width, height));
	}

	/*************************************************************************
	 * Returns the ID of an accelerated sub-image with the indicated texture
	 * coordinates. If no identical sub-image exists, one is compiled and the
	 * list of sub-images is enlarged to fit it.
	 * 
	 * @param coordinates
	 *            The coordinates that detail which section of the image to
	 *            render. These are not pixel values, but rather percentages.
	 *            The values in the array should be {x1, y1, x2, y2}, where the
	 *            first pair of values mark the upper left of the section and
	 *            the second pair mark the lower right.
	 * 
	 * @return The place in the internal array of sub-images.
	 *************************************************************************/
	public int subImage(float[] coordinates)
	{
		int position = numSubImages;
		for (int i = 0; i < numSubImages; i++)
		{
			if (subImages[i] == null)
				if (position == numSubImages)
					position = i;
				else if (subImages[i].matches(coordinates))
					return i;
		}

		if (position != numSubImages)
		{
			subImages[position] = new SubImage(coordinates);
			return position;
		}

		if (numSubImages >= subImages.length)
		{
			SubImage[] newSubImages = new SubImage[numSubImages + 20];
			for (int i = 0; i < numSubImages; i++)
				newSubImages[i] = subImages[i];
			subImages = newSubImages;
		}

		subImages[numSubImages] = new SubImage(coordinates);
		numSubImages++;
		return numSubImages - 1;
	}

	/*************************************************************************
	 * Destroys the sub-image at the indicated position in the internal array.
	 * 
	 * @param id
	 *            The spot in the array to destroy.
	 * 
	 * @return True if the operation succeeded, false if not.
	 *************************************************************************/
	public boolean destroySubImage(int id)
	{
		if (subImages.length > id && subImages[id] != null)
		{
			subImages[id].destroy();
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * A simple internal class that contains data for drawing a subsection of an
	 * image.
	 * 
	 * The main use of this class is to simplify accelerated rendering of
	 * sections of the image. By using the "sub-image API" in place, an image
	 * can have a series of sections, all with compiled hardware acceleration.
	 * 
	 * Additionally, it should be noted that the simplest way to create "tiling"
	 * effects with objects is via sub-images; simply supply some coordinates
	 * that have texture coordinates greater than 1 and the final surface will
	 * work as desired.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	protected class SubImage implements DataController
	{
		/**
		 * The texture coordinates that draw the section of the image.
		 **/
		public float[] coordinates;

		/**
		 * The ID of the compiled display list.
		 **/
		public int dLID;

		/*********************************************************************
		 * Creates a SubImage from the specified coordinates.
		 * 
		 * @param coordinates
		 *            The texture coordinates to use for rendering.
		 *********************************************************************/
		public SubImage(float[] coordinates)
		{
			this.coordinates = coordinates;
			create();
		}

		/*********************************************************************
		 * Checks whether this sub-image has the same coordinates as those
		 * supplied.
		 * 
		 * @param coordinates
		 *            The texture coordinates supplied.
		 * 
		 * @return Whether or not this sub-image's coordinates match those
		 *         supplied.
		 *********************************************************************/
		public boolean matches(float[] coordinates)
		{
			return this.coordinates[0] != coordinates[0] &&
					this.coordinates[1] != coordinates[1] &&
					this.coordinates[2] != coordinates[2] &&
					this.coordinates[3] != coordinates[3];
		}

		/*********************************************************************
		 * Compiles the display list used to accelerate rendering.
		 ***********************************************************/ @Override
		public void create()
		{
			destroy();
			dLID = GL11.glGenLists(1);
			GL11.glNewList(dLID, GL11.GL_COMPILE);
			renderSubImage(coordinates);
			GL11.glEndList();
		}

		/*********************************************************************
		 * If this display list has been compiled, it is destroyed.
		 ***********************************************************/ @Override
		public void destroy()
		{
			if (dLID != 0)
				GL11.glDeleteLists(dLID, 1);
			dLID = 0;
		}

		/*********************************************************************
		 * Checks to see if the display list has been compiled.
		 * 
		 * @return Whether or not the display list has been compiled.
		 ***********************************************************/ @Override
		public boolean valid()
		{
			return dLID != 0;
		}
	}
}