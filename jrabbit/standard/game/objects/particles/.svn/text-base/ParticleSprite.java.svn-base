package org.jrabbit.standard.game.objects.particles;

import java.util.Iterator;

import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.data.structures.LockingList;
import org.jrabbit.base.data.structures.base.Container;
import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.skins.image.ImageSkin;
import org.jrabbit.base.graphics.transforms.BlendOp;
import org.jrabbit.base.graphics.types.Blended;
import org.jrabbit.standard.game.objects.base.BaseSprite;
import org.jrabbit.standard.game.objects.particles.base.Particle;

/*****************************************************************************
 * A ParticleSprite is a simple but flexible controller for a group of 
 * Particles. How these Particles look and behave is entirely customizable, and
 * so virtually any effect can be created.
 * 
 * Essentially, a ParticleSprite maintains, updates, and renders a list of 
 * Particles. The behavior and rendering of these Particles is left up to them.
 * Consider the ParticleSprite as an "entry point" for these particles; upon 
 * creation, their Locations, Rotations, Scalars, and Colors are all set to the
 * same values as those in the ParticleSprite. After that, however, the 
 * ParticleSprite is somewhat removed from its children; the particles do not 
 * move, rotate, or scale with it, and are not affected by changes in their 
 * parent's Color.
 * 
 * One of the advantages of this is that the ParticleSprite can be moved around
 * the gameworld at will, but already created Particles remain as they were. For
 * example, a single ParticleSprite can manage all "explosions" in a game; to
 * create an explosion effect, simply move the ParticleSprite to the target 
 * location, create the desired Particles, then move it to the next position.
 * 
 * However, it is possible to create ParticleSprites that cause their particles
 * to move/rotate/scale with them. To do so, simply use the internal class
 * ParticleSprites.Transforming.
 * 
 * Lastly: A ParticleSprite controls the Blending function used by all of its
 * child Particles, and allows easy modification of the active blending 
 * function.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ParticleSprite extends BaseSprite implements Updateable, 
		Blended, Container<Particle>
{
	/**
	 * The default blending function used by the rendered Particles.
	 **/
	protected BlendOp blending;
	
	/**
	 * The Skins used to render Particles.
	 **/
	protected Skin skins[];
	
	/**
	 * The list of Particles.
	 **/
	protected LockingList<Particle> particles;
	
	/*************************************************************************
	 * Creates a ParticleSprite that uses the supplied Skins in rendering.
	 * 
	 * @param skins
	 * 			  The Skins that are accessible to the child Particles.
	 *************************************************************************/
	public ParticleSprite(Skin... skins)
	{
		this.skins = skins;
		transforms.clear();
		transforms.add(	screenCoords,
						blending = new BlendOp.Normal());
		particles = new LockingList<Particle>();
	}
	
	/*************************************************************************
	 * Accesses one of the Skins being used to render Particles.
	 * 
	 * @param place
	 *            The target location in the array. 
	 * 
	 * @return The indicated Skin.
	 *************************************************************************/
	public Skin skinAt(int place) { return skins[place]; }
	
	/*************************************************************************
	 * Accesses the list of skins.
	 * 
	 * @return The array of Skins being used to render Particles.
	 *************************************************************************/
	public Skin[] skins() { return skins; }
	
	/*************************************************************************
	 * Redefines the list of Skins.
	 * 
	 * @param skins
	 * 			  The new list of Skins to use in rendering.
	 *************************************************************************/
	public void setSkins(Skin... skins)
	{
		this.skins = skins;
	}
	
	/*************************************************************************
	 * Accesses the blending controls.
	 * 
	 * @return The BlendOp that controls the blend function used by all child
	 *         Particles.
	 ***************************************************************/ @Override
	public BlendOp blending() { return blending; }
	
	/*************************************************************************
	 * Accesses the "dimensions" of the ParticleSprite.
	 * 
	 * @return The "width" of the ParticleSprite. HOWEVER, since this does not 
	 *         represent an actual object, this returns 0.
	 ***************************************************************/ @Override
	public float width() { return 0; }
	
	/*************************************************************************
	 * Accesses the "dimensions" of the ParticleSprite.
	 * 
	 * @return The "height" of the ParticleSprite. HOWEVER, since this does not 
	 *         represent an actual object, this returns 0.
	 ***************************************************************/ @Override
	public float height() { return 0; }
	
	/*************************************************************************
	 * Determines whether or not the ParticleSprite should render itself.
	 * 
	 * NOTE: Since a ParticleSprite's own location can be entirely different
	 * than those of its children, onscreen/offscreen based culling is ignored.
	 * Thus, this simply returns the value of the visibility flag.
	 * 
	 * @return True if rendering should continue, false if not.
	 ***************************************************************/ @Override
	protected boolean shouldRender()
	{
		return visible;
	}
	
	/*************************************************************************
	 * Updates all contained particles.
	 * 
	 * @param delta
	 * 			  The amount of microseconds that have passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		for(Particle p:particles)
			p.update(delta);
		particles.unlock();
	}
	
	/*************************************************************************
	 * Renders all contained particles.
	 ***************************************************************/ @Override
	public void draw()
	{
		for(Particle particle : particles)
			particle.render();
		particles.unlock();
	}
	
	/*************************************************************************
	 * Adds a Particle to the ParticleSprite.
	 * 
	 * @param particle
	 * 			  The Particle to add.
	 * 
	 * @return True if the add succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean add(Particle particle)
	{
		if(particle != null)
			particle.setParent(this);
		return particles.add(particle);
	}
	
	/*************************************************************************
	 * Adds a series of Particles to the ParticleSprite.
	 * 
	 * @param particles
	 * 			  The Particles to add.
	 ***************************************************************/ @Override
	public void add(Particle... particles)
	{
		for(Particle particle : particles)
			add(particle);
	}
	
	/*************************************************************************
	 * Removes a Particle from the ParticleSprite.
	 * 
	 * @param particle
	 * 			  The Particle to remove.
	 * 
	 * @return True if the removal succeeded, false if not.
	 ***************************************************************/ @Override
	public boolean remove(Particle particle)
	{
		return particles.remove(particle);
	}
	
	/*************************************************************************
	 * Removes a series of Particles from the ParticleSprite.
	 * 
	 * @param particles
	 * 			  The Particles to remove.
	 ***************************************************************/ @Override
	public void remove(Particle... particles)
	{
		for(Particle particle : particles)
			remove(particle);
	}

	/*************************************************************************
	 * Checks to see if the ParticleSprite contains a particular Particle.
	 * 
	 * @param particle
	 * 			  The Particle to check for.
	 * 
	 * @return True if the particle is ocntained, false if not.
	 ***************************************************************/ @Override
	public boolean contains(Particle particle)
	{
		return particles.contains(particle);
	}

	/*************************************************************************
	 * Learns the size of the Particle system.
	 * 
	 * @return The number of contained Particles.
	 ***************************************************************/ @Override
	public int size()
	{
		return particles.size();
	}

	/*************************************************************************
	 * Removes all Particles.
	 ***************************************************************/ @Override
	public void clear()
	{
		particles.clear();
	}

	/*************************************************************************
	 * Accesses the list of Particles, in order of addition.
	 * 
	 * @return An Iterator through all contained Particles.
	 ***************************************************************/ @Override
	public Iterator<Particle> iterator()
	{
		return particles.iterator();
	}
	
	/*************************************************************************
	 * ParticleSprite.Images is a convenience class that simplifies the process
	 * to create a ParticleSprite that uses Images in rendering.
	 *************************************************************************/
	public static class Images extends ParticleSprite
	{
		/*********************************************************************
		 * Creates a ParticleSprite that uses Images to render its particles.
		 * 
		 * @param references
		 *            The list of identifiers that determine which Images to
		 *            access from the cache.
		 *********************************************************************/
		public Images(String... references)
		{
			super(createImageSkins(references));
		}
		
		/*********************************************************************
		 * A convenience method to turn a list of references into a list of 
		 * ImageSkins. 
		 * 
		 * @param references
		 *            The series of references that indicate which Images to 
		 *            use.
		 * 
		 * @return An array of ImageSkins corresponding to the references 
		 *         indicated.
		 *********************************************************************/
		private static ImageSkin[] createImageSkins(String... references)
		{
			ImageSkin[] skins = new ImageSkin[references.length];
			for(int i = 0; i < references.length; i++)
				skins[i] = new ImageSkin(references[i]);
			return skins;
		}
	}
	
	/*************************************************************************
	 * ParticleSprite.Transforming creates a ParticleSprite that causes all
	 * contained Particles to move, rotate, and scale along with it. So, 
	 * Particles at [0, 0] will be viewed at the location of the Sprite, etc.
	 * 
	 * @author Chris Molini
	 *************************************************************************/
	public static class Transforming extends ParticleSprite
	{
		public Transforming(Skin... skins)
		{
			super(skins);
			transforms.add(	location, 
							rotation, 
							scalar);
		}
		
		/*********************************************************************
		 * Creates a ParticleSprite.Transforming that uses Images to render its 
		 * particles.
		 * 
		 * @param references
		 *            The list of identifiers that determine which Images to
		 *            access from the cache.
		 *********************************************************************/
		public Transforming(String... references)
		{
			this(Images.createImageSkins(references));
		}
	}
}