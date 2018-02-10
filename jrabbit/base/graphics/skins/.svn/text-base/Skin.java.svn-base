package org.jrabbit.base.graphics.skins;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.graphics.types.Dimensioned;

/*****************************************************************************
 * Basically, a skin is a lightweight rendering component. It adds a little more
 * definition to Renderable by having a "space" it contains, represented by a
 * width and a height.
 * 
 * Skins should be as lightweight as possible; they should not themselves
 * control rendering data (such as images) but should instead access and
 * reference it. For example, a Skin that renders an image should retrieve the
 * image it needs from the Cache, instead of creating one for itself. (This also
 * allows multiple skins to share data.)
 * 
 * Game objects should delegate their rendering to skins instead of managing it
 * themselves, if possible. This makes the functionality more modular, and can
 * allow the objects to change from one skin to another without any cost to
 * themselves.
 * 
 * A Skin should not affect the GLModelviewMatrix for its rendering unless
 * necessary, and it should never make a lasting change. It's intended that an
 * object using a Skin should first translate, scale, or rotate OpenGL as 
 * necessary and then render the skin.
 * 
 * @author Chris Molini
 *****************************************************************************/
public interface Skin extends Renderable, Dimensioned { }