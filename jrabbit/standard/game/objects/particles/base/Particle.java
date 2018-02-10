package org.jrabbit.standard.game.objects.particles.base;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.standard.game.objects.base.BaseSprite;
import org.jrabbit.standard.game.objects.particles.ParticleSprite;

/*****************************************************************************
 * A Particle is a lightweight game object that is intended to updated and
 * rendered, en-masse, by a ParticleSprite. By default, Particles are mostly 
 * independent of their parent ParticleSprites, but they do use the list of 
 * Skins maintained by their parent to render themselves.
 * 
 * To animate a Particle, simply have its choice of Skin change over time.
 * 
 * @author Chris Molini
 *****************************************************************************/
public abstract class Particle extends BaseSprite implements Updateable
{
	/**
	 * The ParticleSprite that contains, updates, and renders this Particle.
	 **/
	protected ParticleSprite parent;
	
	/**
	 * This points to the Skin in the parent's array of Skins to use for
	 * rendering.
	 **/
	protected int skinID;
	
	/*************************************************************************
	 * Redefines the parent ParticleSprite reference and tells the particle
	 * to append itself to it.
	 * 
	 * @param parent
	 * 			  The new ParticleSprite to treat as a parent.
	 *************************************************************************/
	public void setParent(ParticleSprite parent)
	{
		if(parent != null && parent != this.parent)
		{
			if(this.parent != null)
				kill();
			this.parent = parent;
			appendToParent();
		}
	}
	
	/*************************************************************************
	 * Adjusts this Particle so that it is appropriately influenced by its 
	 * parent. Color, location, rotation, and scaling/flipping are all effected. 
	 *************************************************************************/
	protected void appendToParent()
	{
		color.multiplyBy(parent.color());
		location.add(parent.location());
		rotation.rotate(parent.rotation().degrees());
		scalar.scaleBy(parent.scalar());
	}
	
	/*************************************************************************
	 * Learns which Skin the Particle is using.
	 * 
	 * @return The place in the array of Skins of its parent that this Particle 
	 *         uses.
	 *************************************************************************/
	public int skinID() { return skinID; }
	
	/*************************************************************************
	 * Redefines the Particle's choice of Skin.
	 * 
	 * @param skinID
	 * 			  The new place in the array of Skins to use.
	 *************************************************************************/
	public void setSkinID(int skinID)
	{
		this.skinID = skinID;
	}
	
	/*************************************************************************
	 * Accesses the Skin the particle is using.
	 * 
	 * @return The Skin in the array that is pointed to by this Particle.
	 *************************************************************************/
	public Skin skin()
	{
		return parent.skinAt(skinID);
	}
	
	/*************************************************************************
	 * Removes this Particle from its parent.
	 *************************************************************************/
	public void kill()
	{
		if(parent != null)
			parent.remove(this);
	}

	/*************************************************************************
	 * Accesses the Particle's dimensions.
	 * 
	 * @return The unscaled width of the Particle.
	 ***************************************************************/ @Override
	public float width() { return skin().width(); }

	/*************************************************************************
	 * Accesses the Particle's dimensions.
	 * 
	 * @return The unscaled height of the Particle.
	 ***************************************************************/ @Override
	public float height() { return skin().height(); }

	/*************************************************************************
	 * Renders the Particle's choice of Skin.
	 ***************************************************************/ @Override
	public void draw()
	{
		skin().render();
	}
}