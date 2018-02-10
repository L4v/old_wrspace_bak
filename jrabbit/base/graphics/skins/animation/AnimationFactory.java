package org.jrabbit.base.graphics.skins.animation;

import org.jrabbit.base.graphics.skins.image.ImageSkin;

/*****************************************************************************
 * AnimationFactory is a static convenience class for creating AnimatedSkins.
 * 
 * The methods here only help with creating AnimatedSkins that use whole images
 * (by using ImageSkins); to make animations with more complex Skins the
 * developer needs to do the work themselves.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class AnimationFactory
{
	/*************************************************************************
	 * Creates an AnimatedSkin that is "not" animated, in that it is simply a
	 * single image shown for every frame.
	 * 
	 * Obviously, this somewhat defeats the purpose of using Animations, but it
	 * is sometimes useful (e.g., skinning an object extending a class that only
	 * uses AnimatedSkins, but doesn't need to be expressly animated).
	 * 
	 * @param reference
	 *            The reference of the Image to show.
	 * 
	 * @return An AnimatedSkin that displays the indicated image.
	 *************************************************************************/
	public static AnimatedSkin createStill(String reference)
	{
		return new AnimatedSkin(makeFrames(reference), new int[][] {{0}});
	}

	/*************************************************************************
	 * Returns a looping animation that cycles through the indicated Images
	 * continuously at the default speed.
	 * 
	 * @param references
	 *            The references of the Images to show.
	 * 
	 * @return An AnimatedSkin that loops through the indicated Images.
	 *************************************************************************/
	public static AnimatedSkin createLoop(String... references)
	{
		return createLoop(AnimatedSkin.defaultSpeed(), references);
	}

	/*************************************************************************
	 * Returns a looping animation that cycles through the indicated Images
	 * continuously at the indicated speed.
	 * 
	 * @param speed
	 * 			  The base duration of each frame of animation.
	 * @param references
	 *            The references of the Images to show.
	 * 
	 * @return An AnimatedSkin that loops through the indicated Images.
	 *************************************************************************/
	public static AnimatedSkin createLoop(int speed, String... references)
	{
		int[][] index = new int[1][references.length];
		for (int i = 0; i < references.length; i++)
			index[0][i] = i;
		return new AnimatedSkin(makeFrames(references), index, speed);
	}

	/*************************************************************************
	 * Creates an AnimatedSkin that uses the indicated references and has the
	 * indicated animation cycles. It is animated at the default speed.
	 * 
	 * @param index
	 * 			  The list of animation cycles to use.
	 * @param references
	 * 			  The list of Image references.
	 * 
	 * @return An AnimatedSkin that uses the indicated Images and animations.
	 *************************************************************************/
	public static AnimatedSkin createAnimation(int[][] index,
			String... references)
	{
		return new AnimatedSkin(makeFrames(references), index);
	}

	/*************************************************************************
	 * Creates an AnimatedSkin that uses the indicated references and has the
	 * indicated animation cycles. It is animated at the indicated speed.
	 * 
	 * @param index
	 * 			  The list of animation cycles to use.
	 * @param speed
	 * 			  The base duration of each frame of animation.
	 * @param references
	 * 			  The list of Image references.
	 * 
	 * @return An AnimatedSkin that uses the indicated Images and animations.
	 *************************************************************************/
	public static AnimatedSkin createAnimation(int[][] index, int speed,
			String... references)
	{
		return new AnimatedSkin(makeFrames(references), index, speed);
	}

	/*************************************************************************
	 * Creates a list of ImageSkins that display the Images with the indicated
	 * references.
	 * 
	 * @param references
	 *            The references of the Images to show.
	 * 
	 * @return An array of ImageSkins created from the list of references.
	 *************************************************************************/
	private static ImageSkin[] makeFrames(String... references)
	{
		ImageSkin[] frames = new ImageSkin[references.length];
		for (int i = 0; i < references.length; i++)
			frames[i] = new ImageSkin(references[i]);
		return frames;
	}
}