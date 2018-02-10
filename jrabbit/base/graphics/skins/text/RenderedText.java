package org.jrabbit.base.graphics.skins.text;

import org.jrabbit.base.data.structures.LockingList;
import org.jrabbit.base.graphics.image.Image;

/*****************************************************************************
 * RenderedText is a static class whose sole purpose is to allow for controlled
 * cleanup of the Images used by RenderedTextSkins. RenderedTextSkins are
 * designed to automatically register their Images with this class, so the only
 * methods the developer needs to worry about are the destroy() commands.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class RenderedText
{
	/**
	 * The list of Images stored.
	 **/
	private static LockingList<Image> storedText = new LockingList<Image>();

	/*************************************************************************
	 * Registers the indicated Image on the list.
	 * 
	 * @param renderSurface
	 * 			  The Image used to render text.
	 *************************************************************************/
	static void add(Image renderSurface)
	{
		storedText.add(renderSurface);
	}

	/*************************************************************************
	 * Obliterates the data associated with the indicated RenderedTextSkin. The
	 * skin itself is flagged to recalculate its rendering data, so if it is
	 * still in use it will simply recreate its required resources.
	 * 
	 * @param renderedText
	 * 			  The RenderedTextSkin to "clean."
	 *************************************************************************/
	static void destroy(RenderedTextSkin renderedText)
	{
		destroy(renderedText.renderSurface());
		renderedText.renderSurface = null;
		renderedText.flagReformat();
	}

	/*************************************************************************
	 * Obliterates the indicated Image and removes it from the list. 
	 * 
	 * @param renderedText
	 * 			  The Image to destroy.
	 *************************************************************************/
	static void destroy(Image renderedText)
	{
		storedText.remove(renderedText);
		renderedText.destroy();
	}

	/*************************************************************************
	 * Destroys every Image on the list.
	 *************************************************************************/
	public static void destroyAll()
	{
		for (Image image : storedText)
			image.destroy();
		storedText.unlock();
		storedText.clear();
	}
}