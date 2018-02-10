package org.jrabbit.base.managers;

import java.util.Random;

import org.jrabbit.base.data.Factory;
import org.jrabbit.base.data.cache.Cache;
import org.jrabbit.base.graphics.font.FontCache;
import org.jrabbit.base.graphics.image.ImageCache;
import org.jrabbit.base.sound.SoundData;

/*****************************************************************************
 * Resources is a static class that contains references to caches for each of
 * the three main resource types - images, fonts, and sounds. It is used to
 * provide unified access to all resources.
 * 
 * Additionally, Resources contains its own random number generator. They're
 * used a lot in games, so one is provided.
 * 
 * @author Chris Molini
 *****************************************************************************/
public final class Resources
{
	/**
	 * The cache of all Images.
	 **/
	private static ImageCache images;

	/**
	 * The cache of all fonts.
	 **/
	private static FontCache fonts;

	/**
	 * The cache of all sound data.
	 **/
	private static Cache<SoundData> sounds;

	/**
	 * The random number generator.
	 **/
	private static Random random;

	/*************************************************************************
	 * Initializes all resources.
	 *************************************************************************/
	public static void create()
	{
		images = new ImageCache();
		fonts = new FontCache();
		sounds = new Cache<SoundData>(new Factory<SoundData>() {
			public SoundData create(String reference) {
				return new SoundData(reference); }});
		random = new Random();
	}

	/*************************************************************************
	 * Accesses the ImageCache.
	 * 
	 * @return The cache of images.
	 *************************************************************************/
	public static ImageCache images()
	{
		return images;
	}

	/*************************************************************************
	 * Accesses the FontCache.
	 * 
	 * @return The cache of fonts.
	 *************************************************************************/
	public static FontCache fonts()
	{
		return fonts;
	}

	/*************************************************************************
	 * Accesses the SoundCache.
	 * 
	 * @return The cache of sound data.
	 *************************************************************************/
	public static Cache<SoundData> sounds()
	{
		return sounds;
	}

	/*************************************************************************
	 * Accesses the random number generator.
	 * 
	 * @return A reference to the general Random contained by Resources.
	 *************************************************************************/
	public static Random random()
	{
		return random;
	}
}