package org.jrabbit.standard.game.world;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.data.structures.LockingList;
import org.jrabbit.base.graphics.layers.Layered;
import org.jrabbit.base.graphics.layers.grouped.DefaultLayeredRenderList;
import org.jrabbit.base.graphics.layers.grouped.LayeredRenderList;
import org.jrabbit.standard.game.managers.GameManager;
import org.jrabbit.standard.game.world.background.Background;
import org.jrabbit.standard.game.world.camera.Camera;

/*****************************************************************************
 * A World is a 2D gameworld, populated with Renderable and Updateable objects.
 * 
 * The default world is just a Background, a Camera, and dynamic lists to 
 * contain objects to update and/or render. It is rather bare bones, and does
 * not contain any particular methods to manage its contents (e.g., checking for
 * collision between two sets of objects, or other such common game mechanics).
 * However, it is simple to extend World and add that functionality to it.
 * 
 * That's the whole point - for any reasonably complex game, this particular 
 * implementation isn't enough. However, it is a good base class for any 
 * particular gameworld, whether it is a top-down shooter, side-scrolling 
 * platformer, etc.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class World implements Renderable, Updateable, Layered
{
	/**
	 * The current delta by which the World is updating its contents.
	 **/
	protected int delta;
	
	/**
	 * The dynamic list of all objects being updated.
	 **/
	protected LockingList<Updateable> updated;
	
	/**
	 * The Layered list of all objects being Rendered.
	 **/
	protected LayeredRenderList layers;
	
	/**
	 * The background of the world. This is drawn "behind" all game objects.
	 **/
	protected Background background;
	
	/**
	 * The Camera that determines where the player is looking in the world.
	 **/
	protected Camera camera;

	/*************************************************************************
	 * Creates the default World. It has no objects in it, an opaque black 
	 * Background, its Camera points to [0, 0], and it has one Layer (referenced
	 * by the empty String).
	 *************************************************************************/
	public World()
	{
		camera = new Camera();
		background = new Background();
		updated = new LockingList<Updateable>();
		layers = new DefaultLayeredRenderList("");
	}

	/*************************************************************************
	 * Attempts to add the indicated object to the World.
	 * 
	 * If the object implements Updateable, it is added to the list of updated
	 * objects and is updated from the next loop on. Likewise, if the object is
	 * Renderable, it is also added to the topmost Layer in the render list, and
	 * is rendered from the next frame on.
	 * 
	 * @param object
	 * 			  The object to add.
	 *************************************************************************/
	public void add(Object object)
	{
		if(object instanceof Renderable)
			layers.add((Renderable) object);
		if(object instanceof Updateable)
			updated.add((Updateable) object);
	}
	
	/*************************************************************************
	 * Attempts to add the Objects to the world.
	 * 
	 * This functions exactly the same as calling add() for each individual 
	 * object.
	 * 
	 * @param objects
	 * 			  The list of Objects to add.
	 * 
	 * @see #add(Object)
	 *************************************************************************/
	public void add(Object... objects)
	{
		for(Object object : objects)
			add(object);
	}

	/*************************************************************************
	 * Attempts to add the indicated object to the World. If the object is 
	 * Renderable, it is added to the Layer indicated by the supplied reference.
	 * 
	 * @param layer
	 * 			  The identifier of the Layer to add to.
	 * @param object
	 * 			  The object to add.
	 *************************************************************************/
	public void add(String layer, Object object)
	{
		if(object instanceof Renderable)
			layers.add((Renderable) object, layer);
		if(object instanceof Updateable)
			updated.add((Updateable) object);
	}
	
	/*************************************************************************
	 * Attempts to add the Objects to the world, targeting the indicated Layer
	 * if they are Renderable.
	 * 
	 * This functions exactly the same as calling add() for each individual 
	 * object, specifying the Layer for each operation.
	 * 
	 * @param layer
	 * 			  The identifier of the Layer to add to.
	 * @param objects
	 * 			  The list of Objects to add.
	 * 
	 * @see #add(String, Object)
	 *************************************************************************/
	public void add(String layer, Object... objects)
	{
		for(Object object : objects)
			add(layer, object);
	}

	/*************************************************************************
	 * Attempts to remove the indicated Object from the World. If the object is
	 * Updateable or Renderable, the World attempts to remove it from the 
	 * appropriate list(s).
	 * 
	 * @param object
	 * 			  The object to remove.
	 *************************************************************************/
	public void remove(Object object)
	{
		if(object instanceof Renderable)
			layers.remove((Renderable) object);
		if(object instanceof Updateable)
			updated.remove((Updateable) object);
	}
	
	/*************************************************************************
	 * Attempts to remove all indicated Objects from the World.
	 * 
	 * This functions exactly the same as calling remove() for each individual 
	 * object.
	 * 
	 * @param objects
	 * 			  The objects to remove.
	 * 
	 * @see #remove(Object)
	 *************************************************************************/
	public void remove(Object... objects)
	{
		for(Object object : objects)
			remove(object);
	}
	
	/*************************************************************************
	 * Attempts to determine if the indicated object is contained in this world.
	 * 
	 * @param object
	 * 			  The object to check for.
	 * 
	 * @return True if the object is contained, false if not.
	 *************************************************************************/
	public boolean contains(Object object)
	{
		return (object instanceof Updateable && updated.contains((Updateable) 
				object)) || (object instanceof Renderable && layers.containing(
				(Renderable) object) != null);
	}

	/*************************************************************************
	 * Removes all objects from the lists. Also removes all layers.
	 *************************************************************************/
	public void clear()
	{
		layers.clear();
		updated.clear();
	}
	
	/*************************************************************************
	 * Accesses the list of updated objects.
	 * 
	 * @return The dynamic list of Updateable objects being managed by this 
	 *         World.
	 *************************************************************************/
	public LockingList<Updateable> updated() { return updated; }

	/*************************************************************************
	 * Accesses the Layered list of objects being rendered.
	 * 
	 * @return The LayeredRenderList this World uses to manage the objects it
	 *         renders.
	 ***************************************************************/ @Override
	public LayeredRenderList layers() { return layers; }

	/*************************************************************************
	 * Accesses the object rendering the background.
	 * 
	 * @return The Background of the World.
	 *************************************************************************/
	public Background background() { return background; }

	/*************************************************************************
	 * Redefines the Background being used by this World.
	 * 
	 * @param background
	 * 			  The new Background to use.
	 *************************************************************************/
	public void setBackground(Background background)
	{
		this.background = background;
	}
	
	/*************************************************************************
	 * Accesses this world's Camera.
	 * 
	 * @return The Camera object that controls the viewpoint.
	 *************************************************************************/
	public Camera camera() { return camera; }

	/*************************************************************************
	 * Redefines the Camera being used to view the World.
	 * 
	 * @param camera
	 *            The new Camera to use.
	 *************************************************************************/
	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}

	/*************************************************************************
	 * Learns the current timestep the World is using to update its children.
	 * 
	 * NOTE: It is very easy to extend this method to allow for "time dilation."
	 * Simply have a variable (say, 0.5 or 2) that multiplies the recorded delta
	 * when this method is called. Then, the speed of all updates will be
	 * affected by that amount.
	 * 
	 * @return The amount of clock ticks the World should update by.
	 *************************************************************************/
	public int currentDelta() { return delta; }

	/*************************************************************************
	 * Updates the World separately from the objects. This method is called
	 * every update, after all objects have finished being updated.
	 * 
	 * This method is the ideal place to put game controls such as collision 
	 * detection and resolution. It occurs after objects have finished their 
	 * normal action, but before rendering occurs.
	 * 
	 * @param delta
	 * 			  The amount of clock ticks since the last update.
	 *************************************************************************/
	protected void updateWorld(int delta) { }

	/*************************************************************************
	 * Updates the world and all of its Updateable children.
	 * 
	 * @param delta
	 * 			  The amount of clock ticks since the last update.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		this.delta = delta;
		int updateAmount = currentDelta();
		camera.update(updateAmount);
		background.update(updateAmount);
		for(Updateable u:updated)
			u.update(updateAmount);
		updated.unlock();
		updateWorld(updateAmount);
	}

	/*************************************************************************
	 * Renders the World. First the background is rendered, and then all 
	 * Renderable entities are drawn as dictated by the Camera.
	 ***************************************************************/ @Override
	public void render()
	{
		background.render();
		camera.bind();
		layers.render();
		camera.release();
	}

	/*************************************************************************
	 * Makes this world the active one in the current GameLoop. This is the same
	 * as calling {@link GameManager#setWorld(World)} with this world.
	 *************************************************************************/
	public void makeCurrent()
	{
		GameManager.setWorld(this);
	}
}