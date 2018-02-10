package org.jrabbit.standard.game.objects.base;

import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.transforms.GLControlledReset;
import org.jrabbit.base.graphics.transforms.GLGroupTransform;
import org.jrabbit.base.graphics.types.Colored;
import org.jrabbit.base.graphics.types.GLGroupTransformed;
import org.jrabbit.base.graphics.types.ScreenObject;
import org.jrabbit.base.graphics.types.Visible;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.managers.GameManager;

/*****************************************************************************
 * BaseSprite defines the a Sprite object. It extends Spatial, adding various
 * rendering capabilities and controls - namely, a visibility flag, a color, a
 * GLGroupTransform (allowing for dynamic changing of render properties), 
 * methods to determine whether or not the Sprite is visible, and a control that
 * determines whether the Sprite is rendered in an absolute screen location or
 * is viewed relative to the active Camera.
 * 
 * BaseSprite is abstract, and all extending objects need to define three 
 * methods: The methods that detect width and height, and {@link #draw()}, a 
 * method that handles how the object is rendered. It should be noted that 
 * {@link #draw()} is called after the contents of the GLGroupTransform are 
 * bound, so it does not need to take scaling/rotation/location etc. into 
 * account.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class BaseSprite extends Spatial implements Visible, Colored, 
		ScreenObject, GLGroupTransformed, Skin
{
	/**
	 * Determines whether or not the BaseSprite is visible.
	 **/
	protected boolean visible;
	
	/**
	 * The Color of the BaseSprite. This is white by default.
	 **/
	protected Color color;
	
	/**
	 * This determines whether or not the BaseSprite is rendered relative to the
	 * active camera, or if it is shown in absolute, screen coordinates.
	 **/
	protected GLControlledReset screenCoords;
	
	/**
	 * The dynamic list of GLTransforms that are applied to this object upon 
	 * rendering.
	 **/
	protected GLGroupTransform transforms;

	/*************************************************************************
	 * Creates a default BaseSprite.
	 *************************************************************************/
	public BaseSprite()
	{
		visible = true;
		transforms = new GLGroupTransform(
				screenCoords = new GLControlledReset(),
				location, 
				rotation, 
				scalar, 
				color = new Color());
	}

	/*************************************************************************
	 * Accesses the object that controls whether or not the BaseSprite is 
	 * rendered relative to the active camera or in screen coordinates.
	 * 
	 * @return The GLControlledReset that handles pushing and resetting the
	 *         OpenGL ModelviewMatrix to control rendering.
	 *************************************************************************/
	public GLControlledReset screenCoords() { return screenCoords; }
	
	/*************************************************************************
	 * Accesses the Color of the BaseSprite.
	 * 
	 * @return The Color set before every render.
	 ***************************************************************/ @Override
	public Color color() { return color; }
	
	/*************************************************************************
	 * Accesses the list of GLTransforms used to affect the rendering of this
	 * BaseSprite.
	 * 
	 * @return The GLGroupTransform that maintains a dynamic list of all 
	 *         GLTransforms used in rendering. 
	 ***************************************************************/ @Override
	public GLGroupTransform transforms() { return transforms; }
	
	/*************************************************************************
	 * Learns whether or not the object is currently visible.
	 * 
	 * @return True if the object is allowed to be rendered, false if not.
	 ***************************************************************/ @Override
	public boolean visible() { return visible; }
	
	/*************************************************************************
	 * Redefines the visibility flag of the BaseSprite.
	 * 
	 * @param visible
	 * 			  Whether or not the object should be visible.
	 ***************************************************************/ @Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	/*************************************************************************
	 * Determines if this object is onscreen. This check is influenced by the
	 * GLControlledReset object that handles switching to and from screen/world
	 * coordinates; it delegates the call to either the active camera or active
	 * WindowController, as necessary.
	 * 
	 * NOTE: For speed reasons (to avoid calculating sine/cosine of the angle), 
	 * this does not check the BaseSprite's rotation, but instead calculates its
	 * radius and uses it in the check. Thus, the current rotation does not 
	 * affect this method at all.
	 * 
	 * @return True if the object is potentially visible when rendered, false if
	 *         not.
	 ***************************************************************/ @Override
	public boolean onscreen()
	{
		return (screenCoords.enabled() ? WindowManager.controller() : 
				GameManager.camera()).views(location.x(), location.y(), 
				scaledDiameter() / 2);
	}
	
	/*************************************************************************
	 * Delegates the bind() call to the GLGroupTransform contained by this
	 * BaseSprite.
	 * 
	 * @see GLGroupTransform#bind()
	 ***************************************************************/ @Override
	public void bind()
	{
		transforms.bind();
	}
	
	/*************************************************************************
	 * Delegates the release() call to the GLGroupTransform contained by this
	 * BaseSprite.
	 * 
	 * @see GLGroupTransform#release()
	 ***************************************************************/ @Override	
	public void release()
	{
		transforms.release();
	}
	
	/*************************************************************************
	 * Determines whether or not the BaseSprite should be rendered.
	 * 
	 * By default, this returns true only if the BaseSprite is visible and if
	 * it is considered {@link #onscreen()}.
	 *************************************************************************/
	protected boolean shouldRender()
	{
		return visible && onscreen();
	}
	
	/*************************************************************************
	 * BaseSprite delegates its rendering to this method. Extensions of the 
	 * class must define this method.
	 *************************************************************************/
	public abstract void draw();

	
	/*************************************************************************
	 * Renders the BaseSprite (as dictated by {@link #shouldRender()}).
	 * 
	 * To render, the following methods are called, in the indicated order: 
	 * {@link #bind()}, {@link #draw()}, and {@link #release()}.
	 ***************************************************************/ @Override
	public void render()
	{
		if(shouldRender())
		{
			bind();
			draw();
			release();
		}
	}
}