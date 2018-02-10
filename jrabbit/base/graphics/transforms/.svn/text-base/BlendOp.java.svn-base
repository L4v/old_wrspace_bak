package org.jrabbit.base.graphics.transforms;

import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A BlendOp provides a simple method to switch to another type of pixel
 * blending and back.
 * 
 * Basically, a BlendOp is an object that stores instructions for glBlendFunc.
 * Whenever it is bound, it pushes the color buffer bit (to store previous
 * blending) and calls glBlendFunc() with the stored values.
 * 
 * If you don't have an understanding of how glBlendFunc works, it's recommended
 * that you stick with the default versions of this class.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class BlendOp implements GLTransform
{
	/**
	 * A BlendOp with normal blending.
	 **/
	public static BlendOp NORMAL = new BlendOp.Normal();

	/**
	 * A BlendOp with replacement blending.
	 **/
	public static BlendOp REPLACE = new BlendOp.Replace();

	/**
	 * A BlendOp with additive blending.
	 **/
	public static BlendOp ADDITIVE = new BlendOp.Additive();

	/**
	 * A BlendOp with overlay blending.
	 **/
	public static BlendOp OVERLAY = new BlendOp.Overlay();

	/**
	 * A BlendOp with premultiplied blending.
	 **/
	public static BlendOp PREMULTIPLIED = new BlendOp.Premultiplied();

	/**
	 * The source blending factor.
	 **/
	protected int srcFactor;

	/**
	 * The destination blending factor.
	 **/
	protected int dstFactor;

	/*************************************************************************
	 * Creates a BlendOp that will supply the indicated values to glBlendFunc.
	 * 
	 * The indicated values should be one of the appropriate GL symbolic
	 * constants (GL_ONE, GL_SRC_ALPHA, etc).
	 * 
	 * @param srcFactor
	 *            The value to multiply source pixels by.
	 * @param dstFactor
	 *            The value to multiply the destination pixels by.
	 *************************************************************************/
	public BlendOp(int srcFactor, int dstFactor)
	{
		this.srcFactor = srcFactor;
		this.dstFactor = dstFactor;
	}

	/*************************************************************************
	 * Defines the BlendOp to have the same blending settings as another.
	 * 
	 * @param blend
	 *            The BlendOp to copy.
	 *************************************************************************/
	public void set(BlendOp blend)
	{
		srcFactor = blend.srcFactor;
		dstFactor = blend.dstFactor;
	}

	/*************************************************************************
	 * Redefines the value to use for source pixel blending.
	 * 
	 * @param srcFactor
	 *            The new value to multiply pixels by.
	 *************************************************************************/
	public void setSrcFactor(int srcFactor)
	{
		this.srcFactor = srcFactor;
	}

	/*************************************************************************
	 * Accessed a value used to blend.
	 * 
	 * @return The source color blending factor.
	 *************************************************************************/
	public int srcFactor()
	{
		return srcFactor;
	}

	/*************************************************************************
	 * Redefines the value to use for destination pixel blending.
	 * 
	 * @param srcFactor
	 *            The new value to multiply pixels by.
	 *************************************************************************/
	public void setDstFactor(int dstFactor)
	{
		this.dstFactor = dstFactor;
	}

	/*************************************************************************
	 * Accessed a value used to blend.
	 * 
	 * @return The destination color blending factor.
	 *************************************************************************/
	public int dstFactor()
	{
		return dstFactor;
	}

	/*************************************************************************
	 * Pushes the current color settings (including blending) onto the stack and
	 * then sets the new blending factors.
	 ***************************************************************/ @Override
	public void bind()
	{
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glBlendFunc(srcFactor, dstFactor);
	}

	/*************************************************************************
	 * Pops the attribute stack. If the order of popping and pushing is being
	 * taken care of, this should pop the color settings initially placed on the
	 * stack, and everything should return to the previous settings.
	 ***************************************************************/ @Override
	public void release()
	{
		GL11.glPopAttrib();
	}

	/*************************************************************************
	 * As equals(), but if the supplied object is a BlendOp, it checks to see if
	 * their blending factors are the same.
	 * 
	 * @param object
	 *            The java Object to check against.
	 * 
	 * @return Whether or not the two objects are identical or equivalent.
	 ***************************************************************/ @Override
	public boolean equals(Object object)
	{
		if (object instanceof Color)
		{
			BlendOp bO = (BlendOp) object;
			return (bO.srcFactor == srcFactor && bO.dstFactor == dstFactor);
		}
		return false;
	}

	/*************************************************************************
	 * Enforces "normal" blending - where images retain their full color, and
	 * replace each other depending on how transparent they are.
	 * 
	 * This is the default blending function that jRabbit uses.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class Normal extends BlendOp
	{
		/*********************************************************************
		 * Creates a BlendOp that will use normal, alpha-based replacement
		 * blending.
		 *********************************************************************/
		public Normal()
		{
			super(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	/*************************************************************************
	 * Enforces "replacement" blending - alpha is not taken into account, and
	 * one set of pixel colors completely replaces another.
	 * 
	 * This is the default OpenGL blending function.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class Replace extends BlendOp
	{
		/*********************************************************************
		 * Creates a BlendOp that will use ignore alpha values and use
		 * replacement blending.
		 *********************************************************************/
		public Replace()
		{
			super(GL11.GL_ONE, GL11.GL_ZERO);
		}
	}

	/*************************************************************************
	 * Enforces "additive" blending - where colors only get brighter, not
	 * darker. This form of additive also takes the transparency of images into
	 * account.
	 * 
	 * Additive blending is VERY useful for rendering "bright" effects, like
	 * fire, lasers, explosions, etc.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class Additive extends BlendOp
	{
		/*********************************************************************
		 * Creates a BlendOp that will use additive blending.
		 *********************************************************************/
		public Additive()
		{
			super(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		}
	}

	/*************************************************************************
	 * Enforces blending that will cause any images to be limited to the color
	 * they are being rendered on top of.
	 * 
	 * The result of this is that you get a "darken only" effect, where the
	 * result of a render can only be as bright as the color above it.
	 * 
	 * This should only be used with premultiplied alpha images or with
	 * full-alpha (non-transparent) images. It's limited in that it doesn't take
	 * the transparency of the source image into account.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class Overlay extends BlendOp
	{
		/*********************************************************************
		 * Creates a BlendOp that will use overlay blending.
		 *********************************************************************/
		public Overlay()
		{
			super(GL11.GL_DST_COLOR, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	/*************************************************************************
	 * Enforces blending that is designed for use with premultiplied-alpha
	 * images. Since these images already have their colors multiplied by their
	 * transparency, they need a variant on the normal blending function.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class Premultiplied extends BlendOp
	{
		/*********************************************************************
		 * Creates a BlendOp that will use premultiplied-alpha blending.
		 *********************************************************************/
		public Premultiplied()
		{
			super(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}
}