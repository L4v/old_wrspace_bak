package org.jrabbit.base.graphics.layers.grouped;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.graphics.layers.Layer;
import org.jrabbit.base.graphics.layers.types.DefaultLayer;

/*****************************************************************************
 * DefaultLayeredRenderList provides a standard implementation of the
 * LayeredRenderList interface. Basically, it manages Layers via a LinkedHashMap
 * and creates DefaultLayers from Strings when required.
 * 
 * A DefaultLayeredRenderList is intended to have at least one Layer at all
 * times; if all layers are ever removed it will create a new Layer with an
 * empty String as a reference.
 * 
 * Additionally, DefaultLayeredRenderList maintains a "default" layer; this is
 * intended to always be the "top" layer. If a Renderable is added without an
 * indicated Layer, it is added to this default.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class DefaultLayeredRenderList implements LayeredRenderList
{
	/**
	 * The list of Layers, keyed to their references.
	 **/
	protected LinkedHashMap<String, Layer> layers;
	
	/**
	 * The current Layer accepting default add methods.
	 **/
	private Layer defaultLayer;

	/*************************************************************************
	 * Creates a DefaultLayeredRenderList with no Layers in it.
	 *************************************************************************/
	public DefaultLayeredRenderList()
	{
		layers = new LinkedHashMap<String, Layer>();
	}
	
	/*************************************************************************
	 * Creates a DefaultLayeredRenderList with the indicated Layers in it.
	 * 
	 * @param layers
	 * 			  The identifiers of the DefaultLayers to put into the list.
	 *************************************************************************/
	public DefaultLayeredRenderList(String... layers)
	{
		this();
		for(String layer:layers)
			add(layer);
	}
	
	/*************************************************************************
	 * Creates a DefaultLayeredRenderList with the indicated Layers in it.
	 * 
	 * @param layers
	 * 			  The Layers to place into the list.
	 *************************************************************************/
	public DefaultLayeredRenderList(Layer... layers)
	{
		this();
		for(Layer layer:layers)
			add(layer);
	}

	/*************************************************************************
	 * Attempts to add the indicated Layer on top of those already present in 
	 * the list. The operation fails if another layer is in the list with an 
	 * identical reference.
	 * 
	 * Additionally, if the addition succeeds, this sets the default layer to
	 * the supplied one.
	 * 
	 * @param layer
	 * 			  The Layer to add.
	 * 
	 * @return True if the Layer was added successfully, false if not.
	 ***************************************************************/ @Override
	public boolean add(Layer layer)
	{
		if (layer != null && !layers.containsKey(layer.reference()))
		{
			layers.put(layer.reference(), layer);
			defaultLayer = layer;
			return true;
		}
		return false;
	}

	/*************************************************************************
	 * Attempts to add the indicated series of Layers to the list.
	 * 
	 * @param layers
	 * 			  The Layers to add.
	 * 
	 * @see #add(Layer)
	 ***************************************************************/ @Override
	public void add(Layer... layers)
	{
		for(Layer layer : layers)
			add(layer);
	}

	/*************************************************************************
	 * Adds a new Layer with the indicated reference. This operation will fail
	 * if a Layer is already on the list with an identical reference.
	 * 
	 * Additionally, if the addition succeeds, this sets the default layer to
	 * the new one.
	 * 
	 * @param reference
	 *            The String to identify the new Layer.
	 * 
	 * @return True if the add succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean add(String layerName)
	{
		return add(create(layerName));
	}

	/*************************************************************************
	 * Attempts to create and add Layers corresponding to every supplied
	 * layer name.
	 * 
	 * @param layerNames
	 *            The Strings to identify the new Layers.
	 ***************************************************************/ @Override
	public void add(String... layerNames)
	{
		for(String layerName : layerNames)
			add(layerName);
	}

	/*************************************************************************
	 * Attempts to remove the indicated Layer from the list. If different Layer
	 * with the same reference is on the list, nothing happens.
	 * 
	 * @param layer
	 * 			  The Layer to remove.
	 * 
	 * @return True if the removal operation succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean remove(Layer layer)
	{
		if (layer != null)
		{
			Layer toRemove = layers.get(layer.reference());
			if (toRemove != null && layer == toRemove)
			{
				layers.remove(layer.reference());
				if (layer == defaultLayer)
					adjustDefault();
				return true;
			}
		}
		return false;
	}

	/*************************************************************************
	 * Attempts to remove the indicated series of Layers from the list.
	 * 
	 * @param layers
	 * 			  The Layers to remove.
	 * 
	 * @see #add(Layer)
	 ***************************************************************/ @Override
	public void remove(Layer... layers)
	{
		for(Layer layer : layers)
			remove(layer);
	}

	/*************************************************************************
	 * Removes the Layer (if any) on the list with the indicated reference. If
	 * no Layer has the reference, nothing changes.
	 * 
	 * If the current default layer is removed, then the new "top" layer is set
	 * as the default.
	 * 
	 * @param reference
	 *            The String that identifies the target Layer.
	 * 
	 * @return The Layer that was removed. If no layer was removed, returns 
	 * 		   null.
	 ***************************************************************/ @Override
	public Layer remove(String layerName)
	{
		Layer removed = layers.remove(layerName);
		if(removed != null && removed == defaultLayer)
			adjustDefault();
		return removed;
	}

	/*************************************************************************
	 * Attempts to remove and return all Layers with the supplied names.
	 * 
	 * @param layerNames
	 * 			  The Strings indicating which Layers to remove.
	 * 
	 * @return A LinkedList containing all Layers removed. If no Layers are
	 *         removed successfully, an empty list is returned.
	 ***************************************************************/ @Override
	public LinkedList<Layer> remove(String... layerNames)
	{
		LinkedList<Layer> removed = new LinkedList<Layer>();
		for(String layerName : layerNames)
		{
			Layer layer = remove(layerName);
			if(layer != null)
				removed.add(layer);
		}
		return removed;
	}

	/*************************************************************************
	 * Attempts to find the Layer with the indicated reference.
	 * 
	 * @param reference
	 *            The String that identifies the target Layer.
	 * 
	 * @return The Layer in the list with the indicated reference, or (if none
	 *         exist) returns the "default" Layer.
	 ***************************************************************/ @Override
	public Layer get(String reference)
	{
		Layer layer = layers.get(reference);
		return layer == null ? defaultLayer : layer;
	}

	/*************************************************************************
	 * Checks to see if the list contains the indicated layer.
	 * 
	 * @param layer
	 * 			  The Layer to check for.
	 * 
	 * @return True if the layer is contained, false if not.
	 ***************************************************************/ @Override
	public boolean contains(Layer layer)
	{
		return layers.containsValue(layer);
	}

	/*************************************************************************
	 * Checks to see if the list contains a layer with the indicated String as
	 * a reference.
	 * 
	 * @param layerName
	 * 			  The String that identifies the target Layer.
	 * 
	 * @return True if a Layer is contained whose reference matches the supplied
	 * 		   String.
	 ***************************************************************/ @Override
	public boolean contains(String layerName)
	{
		return layers.containsKey(layerName);
	}

	/*************************************************************************
	 * Removes all Layers; this effectively "empties" the list.
	 * 
	 * NOTE: It's recommended that a new, empty Layer should be added to the
	 * list after this method, so that it's still possible to add Renderable
	 * objects.
	 ***************************************************************/ @Override
	public void clear()
	{
		layers.clear();
		add(new DefaultLayer(""));
	}

	/*************************************************************************
	 * Determines how many Layers are in the list.
	 * 
	 * @return The number of Layers contained.
	 ***************************************************************/ @Override
	public int size()
	{
		return layers.size();
	}

	/*************************************************************************
	 * Creates a DefaultLayer with the indicated String as an identifier.
	 * 
	 * @param reference
	 * 			  The String to identify the new Layer.
	 * 
	 * @return A new DefaultLayer created from the reference.
	 ***************************************************************/ @Override
	public Layer create(String reference)
	{
		return new DefaultLayer(reference);
	}
	
	/*************************************************************************
	 * Ensures that the default layer is the last Layer in the list (the one "on 
	 * top"). If no Layers are currently in the list, a new Layer with an empty
	 * String as an identifier is created and added.
	 *************************************************************************/
	protected void adjustDefault()
	{
		defaultLayer = null;
		Iterator<Layer> layerIter = layers.values().iterator();
		while (layerIter.hasNext())
			defaultLayer = layerIter.next();
		if (defaultLayer == null)
			add("");
	}

	/*************************************************************************
	 * Renders every Layer in the list, in order.
	 ***************************************************************/ @Override
	public void render()
	{
		for (Layer layer : layers.values())
			layer.render();
	}

	/*************************************************************************
	 * Attempts to add the indicated Renderable object to the Layer in the list
	 * with an identical reference to the one supplied.
	 * 
	 * If no Layer with the indicated reference is in the list, the addition 
	 * fails. 
	 * 
	 * @param renderable
	 * 			  The Renderable object to add to the Layer.
	 * @param layerName
	 * 			  The reference that identifies the Layer to add to.
	 * 
	 * @return True if the add succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean add(Renderable renderable, String layerName)
	{
		Layer layer = layers.get(layerName);
		if (layer != null)
			return layer.add(renderable);
		return false;
	}

	/*************************************************************************
	 * Attempts to add the indicated Renderable to the default Layer.
	 * 
	 * @param renderable
	 * 			  The Renderable object to add.
	 * 
	 * @return True if the add succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean add(Renderable renderable)
	{
		return defaultLayer.add(renderable);
	}

	/*************************************************************************
	 * Attempts to remove the indicated Renderable from the Layer in the list
	 * with an identical reference.
	 * 
	 * If no Layer with the indicated reference is in the list, the removal 
	 * fails. 
	 * 
	 * @param renderable
	 * 			  The Renderable object to remove from the Layer.
	 * @param layerName
	 * 			  The reference that identifies the Layer to remove from.
	 * 
	 * @return True if the removal succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean remove(Renderable renderable, String layerName)
	{
		Layer layer = layers.get(layerName);
		if(layer != null)
			return layer.remove(renderable);
		return false;
	}

	/*************************************************************************
	 * Attempts to remove the indicated Renderable object from the list of
	 * Layers. This method searches through all Layers in an attempt to find the
	 * Layer to remove from.
	 * 
	 * @param renderable
	 * 			  The Renderable object to remove.
	 * 
	 * @return True if the removal succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean remove(Renderable renderable)
	{
		Iterator<Layer> iter = layers.values().iterator();
		while (iter.hasNext())
			if (iter.next().remove(renderable))
				return true;
		return false;
	}
	
	/**
	 * 
	 ***************************************************************/ @Override
	public Layer containing(Renderable renderable)
	{
		for(Layer layer:this)
			if(layer.contains(renderable))
				return layer;
		return null;
	}

	/*************************************************************************
	 * Gets an iterator that moves through all Layers in the list, in order.
	 * 
	 * @return An iterator through all the Layers.
	 ***************************************************************/ @Override
	public Iterator<Layer> iterator()
	{
		return layers.values().iterator();
	}
}