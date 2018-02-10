package org.jrabbit.base.graphics.layers;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.data.Referenced;
import org.jrabbit.base.data.structures.base.Container;
import org.jrabbit.base.graphics.types.GLGroupTransformed;

/*****************************************************************************
 * A Layer is meant to provide functionality for organizing and rendering groups
 * of Renderable objects. Multiple Layers are intended to be used together, to
 * allow control over rendering order.
 * 
 * A Layer is also a GLGroupTransformed; it can alter render settings for the
 * entirety of its contents. An example of this is giving a Layer a particular
 * blending mode (e.g., Additive blending); if this is done, all objects 
 * rendered by that Layer will have that blending.
 * 
 * Layers should render all the Renderables they contain. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Layer extends Referenced, Renderable, Container<Renderable>, 
		GLGroupTransformed { }