package org.jrabbit.standard.profiler.entities.base;

import org.jrabbit.base.graphics.font.FontPointer;

/*****************************************************************************
 * FontBasedProfilerEntity extends BaseProfilerEntity to allow the 
 * ProfilerEntity to use a FontPointer to access a Font in the cache. By 
 * default, the default Font is used.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class FontBasedProfilerEntity extends BaseProfilerEntity
{
	/**
	 * The FontPointer that accesses the ResourceCache.
	 **/
	protected FontPointer fontPointer;
	
	/*************************************************************************
	 * Creates a FontBasedProfilerEntity with the supplied identifier. It uses
	 * the default Font.
	 * 
	 * @param reference
	 * 			  The String that will identify this ProfilerEntity.
	 *************************************************************************/
	public FontBasedProfilerEntity(String reference)
	{
		super(reference);
		fontPointer = new FontPointer();
	}
	
	/*************************************************************************
	 * Accesses the FontPointer utilized by this object.
	 * 
	 * @return The FontPointer that accesses the central cache of Fonts.
	 *************************************************************************/
	public FontPointer fontPointer() { return fontPointer; }
}