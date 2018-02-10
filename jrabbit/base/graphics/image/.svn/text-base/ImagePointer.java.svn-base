package org.jrabbit.base.graphics.image;

import org.jrabbit.base.data.cache.CachePointer;
import org.jrabbit.base.managers.Resources;

/*****************************************************************************
 * An ImagePointer is a CachePointer designed to retrieve Images from the
 * default ImageCache in Resources.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ImagePointer extends CachePointer<Image>
{
	/*************************************************************************
	 * Creates an ImagePointer that will retrieve the indicated Image.
	 * 
	 * @param reference
	 *            A reference that matches that of the Image to retrieve from
	 *            the Cache.
	 *************************************************************************/
	public ImagePointer(String reference)
	{
		super(reference);
	}

	/*************************************************************************
	 * Obtains the desired Image, based upon reference.
	 * 
	 * @return The Image in the Cache that has a matching reference.
	 ***************************************************************/ @Override
	public Image retrieve()
	{
		return Resources.images().get(this);
	}
}