package org.jrabbit.base.graphics.skins;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.graphics.types.Dimensioned;

/*****************************************************************************
 * A Skinned object uses a Skin to render itself and manage its Dimensions.
 * 
 * If an object implements Skinned, it's assumed that the Skin used should not
 * be changed outside of its own methods. If the Skin can change, the object
 * should instead implement DynamicSkinned.
 *  
 * @author Chris Molini
 *****************************************************************************/
public interface Skinned extends Renderable, Dimensioned
{
	/*************************************************************************
	 * Accesses the Skin currently being used.
	 * 
	 * @return The Skin used for rendering and dimensional data.
	 *************************************************************************/
	public Skin skin();
}