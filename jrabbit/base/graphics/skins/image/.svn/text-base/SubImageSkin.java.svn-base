package org.jrabbit.base.graphics.skins.image;


/*****************************************************************************
 * Much like an ImageSkin, a SubImageSkin renders an Image it retrieves from the
 * ImageCache; however, it renders a particular section of the Image, instead of
 * the whole thing. This section can either be a small portion of it (like a
 * single image on a Sprite sheet), or it can be larger than the source Image,
 * in which case the resulting render is tiled.
 * 
 * When using a SubImageSkin, remember that the textures used are Power-Of-Two
 * textures; if an image is not PoT, its coordinates will need to be adjusted
 * accordingly.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SubImageSkin extends ImageSkin
{
	/**
	 * The Image coordinates that correspond to what section of the image to
	 * draw. These values are proportional; 0.5f means halfway across the image,
	 * not half a pixel.
	 **/
	private float[] coordinates;

	/**
	 * Images use an array to manage their accelerated sub-sections for
	 * rendering; this is the ID of the current sub-image in that array.
	 **/
	private int ID;

	/*************************************************************************
	 * Creates a SubImageSkin that uses the indicated Image and renders the
	 * indicated section of it.
	 * 
	 * @param reference
	 *            The identifier of the target image.
	 * @param coordinates
	 *            The proportional coordinate values to map to the image. The
	 *            values to use should be in the format {x1, y1, x2, y2}.
	 *************************************************************************/
	public SubImageSkin(String reference, float[] coordinates)
	{
		super(reference);
		this.coordinates = coordinates;
		refreshSubImage();
	}

	/*************************************************************************
	 * Creates a SubImageSkin that uses the indicated Image and renders the
	 * indicated section of it.
	 * 
	 * The indicated values are in pixels; they are not proportional.
	 * 
	 * @param reference
	 *            The identifier of the target image.
	 * @param x
	 *            The top-left x pixel of the section to render.
	 * @param y
	 *            The top-left y pixel of the section to render.
	 * @param width
	 *            The width of the section to render (in pixels).
	 * @param height
	 *            The height of the section to render (in pixels).
	 *************************************************************************/
	public SubImageSkin(String reference, int x, int y, int width, int height)
	{
		super(reference);
		coordinates = object.subImageCoords(x, y, width, height);
		refreshSubImage();
	}

	/*************************************************************************
	 * Renders the desired section of the source image.
	 ***************************************************************/ @Override
	public void render()
	{
		object.renderSubImage(ID);
	}

	/*************************************************************************
	 * Refreshes the cached Image reference; this does not need to be called 
	 * manually unless the contents of the Cache are altered.
	 * 
	 * Additionally, this updates the stored dimensional values (width and 
	 * height), and updates the ID used to render the sub-image.
	 ***************************************************************/ @Override
	public void refresh()
	{
		object = retrieve();
		refreshSubImage();
	}

	/*************************************************************************
	 * Accesses the coordinate values. The returned values are proportions; they
	 * indicated the percentage of the Image to render.
	 * 
	 * @return The array of floats that defines the section of the Image to 
	 * 		   render. These are in the format {x1, y1, x2, y2}.
	 *************************************************************************/
	public final float[] coordinates()
	{
		return coordinates;
	}

	/*************************************************************************
	 * Redefines the coordinates to use for rendering.
	 * 
	 * @param coordinates
	 * 			  The array of floats that defines the section of the Image to 
	 * 		 	  render. These are in the format {x1, y1, x2, y2}.
	 *************************************************************************/
	public void setCoordinates(float[] coordinates)
	{
		this.coordinates = coordinates;
		refreshSubImage();
	}

	/*************************************************************************
	 * Refreshes the rendering and dimensional data based on the currently set
	 * coordinate values.
	 *************************************************************************/
	protected void refreshSubImage()
	{
		if (coordinates != null)
		{
			ID = object.subImage(coordinates);
			width = Math
					.abs(object.width() * (coordinates[2] - coordinates[0]));
			height = Math.abs(object.height()
					* (coordinates[3] - coordinates[1]));
		}
	}
}