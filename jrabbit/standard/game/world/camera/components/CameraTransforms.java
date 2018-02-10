package org.jrabbit.standard.game.world.camera.components;

import org.jrabbit.base.graphics.transforms.GLReset;
import org.jrabbit.base.graphics.transforms.Rotation;
import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.managers.window.WindowManager;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A Camera uses a Location, Scalar, and Rotation just like a Spatial does; 
 * however for these to effect a "viewpoint" effect, their effects when applied
 * need to be slightly different. To this end, this class contains the unique
 * GLTransforms used to handle the Camera.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class CameraTransforms
{
	/*************************************************************************
	 * This extension of Vector2f applies the negative of its coordinates.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class ViewpointVector extends Vector2f
	{
		/*********************************************************************
		 * Creates a ViewpointVector at [0, 0].
		 *********************************************************************/
		public ViewpointVector() { }

		/*********************************************************************
		 * Creates a ViewpointVector at the indicated coordinates.
		 * 
		 * @param x
		 * 			  The initial x-coordinate of the Vector.
		 * @param y
		 * 			  The initial y-coordinate of the Vector.
		 *********************************************************************/
		public ViewpointVector(float x, float y) { super(x, y); }

		/*********************************************************************
		 * Applies a negative translation of the vector's coordinates.
		 ***********************************************************/ @Override
		public void bind() { GL11.glTranslatef(-x, -y, 0); }

		/*********************************************************************
		 * Undoes the applied transform.
		 ***********************************************************/ @Override
		public void release() { GL11.glTranslated(x, y, 0); }
	}
	
	/*************************************************************************
	 * Similarly to ViewpointVector, ViewRotation needs to apply a negative of
	 * its rotation to work correctly.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class ViewRotation extends Rotation
	{
		/*********************************************************************
		 * Creates a ViewRotation with angle [0].
		 *********************************************************************/
		public ViewRotation() { }
		
		/*********************************************************************
		 * Creates a ViewRotation at the indicated degrees. 
		 * 
		 * @param degrees
		 * 			  The degrees of rotation the ViewRotation starts at.
		 *********************************************************************/
		public ViewRotation(float degrees) { super(degrees); }

		/*********************************************************************
		 * Applies a negative rotation of the Rotation's angle.
		 ***********************************************************/ @Override
		public void bind() { GL11.glRotatef(-degrees, 0, 0, 1); }

		/*********************************************************************
		 * Undoes the applied transform.
		 ***********************************************************/ @Override
		public void release() { GL11.glRotatef(degrees, 0, 0, 1); }
	}
	
	/*************************************************************************
	 * A ViewCenterer resets the Modelview Matrix and places the origin at the
	 * center of the scene.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class ViewCenterer extends GLReset
	{
		/*********************************************************************
		 * Pushes the stack, resets the Modelview Matrix, and makes the origin
		 * at the center of the scene. 
		 ***********************************************************/ @Override
		public void bind()
		{
			super.bind();
			GL11.glTranslated(WindowManager.controller().width() / 2f, 
					WindowManager.controller().height() / 2f, 0);
		}
	}
}