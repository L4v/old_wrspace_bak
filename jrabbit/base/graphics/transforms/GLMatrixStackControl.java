package org.jrabbit.base.graphics.transforms;

import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A GLMatrixStackControl pushes the active OpenGL matrix so that it can be
 * restored to its original state at a later point in time. Basically, it
 * "saves" the current matrix.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class GLMatrixStackControl implements GLTransform
{
	/*************************************************************************
	 * Pushes the matrix.
	 ***************************************************************/ @Override
	public void bind()
	{
		GL11.glPushMatrix();
	}

	/*************************************************************************
	 * Pops the matrix.
	 ***************************************************************/ @Override
	public void release()
	{
		GL11.glPopMatrix();
	}
}