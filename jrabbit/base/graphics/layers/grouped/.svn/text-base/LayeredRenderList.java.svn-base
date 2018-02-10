package org.jrabbit.base.graphics.layers.grouped;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.data.structures.base.KeyedContainer;
import org.jrabbit.base.graphics.layers.Layer;

/*****************************************************************************
 * A LayeredRenderList is a data structure designed to use Layers to manage and
 * render groups of Renderable objects.
 * 
 * The most important functionality of the class is to manage and render a
 * dynamic list of Layers. The list remembers order of addition, and renders the
 * Layers in that order. In this list, Layers are keyed to their references, and
 * no two layers can have the same reference. This functionality is inherited
 * from the KeyedContainer and Renderable interfaces.
 * 
 * Secondly, a LayeredRenderList provides methods to place Renderable objects in
 * the desired Layer. There are methods to add and remove, either with a String
 * pointing to the desired Layer to affect or without (in which case the
 * LayeredRenderList does the best it can to accommodate the operation).
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface LayeredRenderList extends Renderable, KeyedContainer<Layer>
{
	/*************************************************************************
	 * Adds the indicated Renderable to the referenced Layer. If the indicated
	 * Layer is not in the list, no add takes place.
	 * 
	 * @param renderable
	 *            The Renderable object to add to the Layer.
	 * @param reference
	 *            The reference that identifies the target Layer in the list.
	 * 
	 * @return True if the add succeeded, false if not.
	 *************************************************************************/
	public boolean add(Renderable renderable, String reference);

	/*************************************************************************
	 * Adds a Renderable to a Layer within the list. Which layer this is may
	 * vary by implementation - it probably makes most sense to add this to the
	 * "top" layer, but other methods may make sense depending on the
	 * application.
	 * 
	 * @param renderable
	 *            The Renderable object to add.
	 * 
	 * @return True if the add succeeded, false if not.
	 *************************************************************************/
	public boolean add(Renderable renderable);

	/*************************************************************************
	 * Attempts to remove the indicated Renderable object from the referenced
	 * Layer.
	 * 
	 * NOTE: If the renderable is in one of the Layers but not the one
	 * referenced, this method makes no attempt to search through other Layers -
	 * it simply returns false.
	 * 
	 * @param renderable
	 *            The Renderable object to remove from the Layer.
	 * @param reference
	 *            The reference that identifies the target Layer in the list.
	 * 
	 * @return True if the removal succeeded, false if not.
	 *************************************************************************/
	public boolean remove(Renderable renderable, String reference);

	/*************************************************************************
	 * Attempts to remove the indicated Renderable object from the list of
	 * Layers. This method should search through all Layers in an attempt to
	 * find the Layere to remove from.
	 * 
	 * @param renderable
	 *            The Renderable object to remove.
	 * 
	 * @return True if the removal succeeded, false if not.
	 *************************************************************************/
	public boolean remove(Renderable renderable);
	
	/*************************************************************************
	 * Finds and retrieves the Layer that contains the indicated Renderable.
	 * 
	 * @param renderable
	 * 			  The Renderable object to search for.
	 * 
	 * @return The Layer that contains the object; or if none do, null.
	 *************************************************************************/
	public Layer containing(Renderable renderable);
}