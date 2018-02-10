package org.jrabbit.base.graphics.misc;

import java.awt.Point;
import java.io.IOException;
import java.nio.IntBuffer;

import org.jrabbit.base.data.loading.Loader;
import org.jrabbit.base.data.loading.SystemLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/*****************************************************************************
 * Convenience class for creating cursors from images.
 * 
 * At the moment, this only supports loading static cursors. Additionally,
 * cursors are limited to particular hardware, and many machines only support
 * single-bit alpha. Keep this in mind when creating your cursors.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class CursorLoader
{
	/*************************************************************************
	 * Loads a cursor from the indicated filepath with the hotspot at the top
	 * left corner of the image.
	 * 
	 * @param filepath
	 *            The location of the desired source image.
	 * 
	 * @return A cursor created from the indicated image.
	 *************************************************************************/
	public static Cursor load(String filepath)
	{
		return load(filepath, new Point(0, 0));
	}

	/*************************************************************************
	 * Loads a cursor from the indicated filepath.
	 * 
	 * @param filepath
	 *            The location of the source image.
	 * @param hotspot
	 *            The point on the cursor that will be the "point" of the
	 *            mouse.
	 * 
	 * @return A Cursor created from the indicated data.
	 *************************************************************************/
	public static Cursor load(String filepath, Point hotspot)
	{
		return load(new SystemLoader(filepath), hotspot);
	}

	/*************************************************************************
	 * Loads a cursor from the indicated loader.
	 * 
	 * @param filepath
	 *            A loader pointing to the desired image.
	 * @param hotspot
	 *            The point on the cursor that will be the "point" of the
	 *            mouse.
	 * 
	 * @return A Cursor created from the indicated data.
	 *************************************************************************/
	public static Cursor load(Loader loader, Point hotspot)
	{
		Texture image = null;
		try
		{
			image = TextureLoader.getTexture(loader.type(),
					loader.stream(), true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		int minDimension = Cursor.getMinCursorSize();
		int width = image.getTextureWidth();
		if (width < minDimension)
		{
			width = minDimension;
		}
		int height = image.getTextureHeight();
		if (height < minDimension)
		{
			height = minDimension;
		}

		try
		{
			Point point = adjustHotspot(hotspot, width, height);
			return new Cursor(width, height, point.x, point.y, 1, cursorImage(
					image, width, height), null);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/*************************************************************************
	 * Because of some complexities with Cursors, a hotspot must be adjusted to
	 * be contained within and flipped to the indicated dimension.
	 * 
	 * @param point
	 * 				The initial hotspot position.
	 * @param width
	 *            The width of the cursor.
	 * @param height
	 *            The height of the cursor.
	 * 
	 * @return A processed Point that has "safe" values.
	 *************************************************************************/
	private static Point adjustHotspot(Point point, int width, int height)
	{
		int x = point.x;
		int y = height - 1 - point.y;

		if (y < 0)
		{
			y = 0;
		} else if (y >= height)
		{
			y = height - 1;
		}
		if (x < 0)
		{
			x = 0;
		} else if (x >= width)
		{
			x = width - 1;
		}
		return new Point(x, y);
	}

	/*************************************************************************
	 * Loads the cursor data into an IntBuffer from the supplied texture.
	 * 
	 * @param texture
	 *            The texture to copy into the cursor.
	 * @param width
	 *            The width of the cursor.
	 * @param height
	 *            The height of the cursor.
	 * 
	 * @return An IntBuffer containing the pixel data of the cursor.
	 *************************************************************************/
	private static IntBuffer cursorImage(Texture texture, int width, int height)
	{
		int numBytes = texture.hasAlpha() ? 4 : 3;
		int textureWidth = texture.getTextureWidth();
		int tWidth = texture.getImageWidth();
		int tHeight = texture.getImageHeight();
		int[] imageData = new int[width * height];
		byte[] bytes = texture.getTextureData();
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				if (x < tWidth && y >= height - tHeight)
				{
					int i = ((y - (height - tHeight)) * textureWidth + x)
							* numBytes;

					int r = bytes[i] & 0xFF;
					int g = bytes[i + 1] & 0xFF;
					int b = bytes[i + 2] & 0xFF;
					int a = 0xFF;

					if (numBytes == 4)
					{
						a = bytes[i + 3] & 0xFF;
						double alpha = 0.5;
						if (a < alpha * 256)
							a = 0;
						else
							a = 0xFF;
						if (a > 0)
						{
							double ap = a / 255.0;
							r = (int) Math.round(r * ap);
							g = (int) Math.round(g * ap);
							b = (int) Math.round(b * ap);
							a = 0xFF;
						}
					}
					imageData[y * width + x] = (a << 24) | (r << 16) | (g << 8)
							| (b);
				}
		return IntBuffer.wrap(imageData);
	}
}