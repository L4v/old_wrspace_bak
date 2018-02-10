package org.jrabbit.standard.game.world.background;

import org.jrabbit.base.core.types.Renderable;
import org.jrabbit.base.core.types.Updateable;
import org.jrabbit.base.graphics.skins.DynamicSkinned;
import org.jrabbit.base.graphics.skins.Skin;
import org.jrabbit.base.graphics.skins.image.ImageSkin;
import org.jrabbit.base.graphics.transforms.Color;
import org.jrabbit.base.graphics.types.Colored;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.standard.game.managers.GameManager;
import org.jrabbit.standard.game.world.camera.Camera;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * Just as 3D games tend to have skyboxes, jRabbit's 2D Worlds have Backgrounds. 
 * Before a world renders its layers, its background is rendered over the entire 
 * scene to clear the display of last frame's view. This background can either
 * be a solid color (the default) or it can be any Skin.
 * 
 * Backgrounds are fairly straightforward, but there are a few useful tips for 
 * using them:
 * 
 *          1: If a Background is using a Skin, the Skin should most likely 
 *              render a complete, opaque rectangle (it should fill its entire
 *              dimension when rendered). Otherwise, sections of the scene will
 *              not be covered by the background, and old frame data will bleed
 *              through. So, if an Image is being used to render the background,
 *              it should not have any transparent regions.
 *              
 *          2: The fact that old frame data persists can actually be used to
 *              create a simple "motion blur" effect. If the background is 
 *              rendered with an alpha value less than 1, the old frame will not
 *              completely be wiped out, and the viewer will see old objects 
 *              fade out over time.
 *              
 *          3: If a Background is supplied with a Skin that is Updateable (like
 *              an AnimationSkin) it will update it over time, as desired.
 *              However, the dimensions of the AnimationSkin should not change.
 *              So, a fiery, burning background with flickering flames (or 
 *              something similar) is fully supported, as long as all the Images
 *              used to render the scene have the same dimensions. 
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Background implements Updateable, Renderable, Colored, 
		DynamicSkinned
{
	/**
	 * The width of the scene being rendered.
	 **/
	protected float sceneWidth;

	/**
	 * The height of the scene being rendered.
	 **/
	protected float sceneHeight;
	
	/**
	 * The scale of the x-axis at which to render the current Skin (if any).
	 **/
	protected float scaleX;
	
	/**
	 * The scale of the y-axis at which to render the current Skin (if any).
	 **/
	protected float scaleY;
	
	/**
	 * Whether the Skin (if any) should be rendered at its original aspect ratio
	 * or it it should be squashed into the smallest dimensions possible. By
	 * default, this is true.
	 **/
	protected boolean keepAspect;
	
	/**
	 * Whether or not the Background should be rotated as the active Camera is
	 * rotated. By default this is true.
	 **/
	protected boolean rotateWithCamera;
	
	/**
	 * The Skin being used to render the Background. If this is null, then a 
	 * solid color is used for the background.
	 **/
	protected Skin skin;
	
	/**
	 * The Color used to render the Background.
	 **/
	protected Color color;
	
	/*************************************************************************
	 * Creates a default Background - solid black, non-skinned.
	 *************************************************************************/
	public Background()
	{
		keepAspect = true;
		rotateWithCamera = true;
		color = new Color();
		clear();
	}

	/*************************************************************************
	 * Creates a Background that renders the referenced Image.
	 * 
	 * @param imageReference
	 * 			  The identifier of the Image to render.
	 *************************************************************************/
	public Background(String imageReference)
	{
		this();
		setSkin(imageReference);
	}

	/*************************************************************************
	 * Creates a Background that renders the supplied Skin.
	 * 
	 * @param skin
	 * 			  The Skin to use in rendering the background.
	 *************************************************************************/
	public Background(Skin skin)
	{
		this();
		setSkin(skin);
	}

	/*************************************************************************
	 * Creates a Background that uses the indicated RGB values.
	 * 
	 * @param red
	 * 			  The red value of the desired color.
	 * @param green
	 * 			  The green value of the desired color.
	 * @param blue
	 * 			  The blue value of the desired color.
	 *************************************************************************/
	public Background(float red, float green, float blue)
	{
		this(red, green, blue, 1f);
	}

	/*************************************************************************
	 * Creates a Background that uses the indicated RGBA values.
	 * 
	 * @param red
	 * 			  The red value of the desired color.
	 * @param green
	 * 			  The green value of the desired color.
	 * @param blue
	 * 			  The blue value of the desired color.
	 * @param alpha
	 * 			  The alpha value of the desired color.
	 *************************************************************************/
	public Background(float red, float green, float blue, float alpha)
	{
		this();
		color.set(red, green, blue, alpha);
	}

	/*************************************************************************
	 * Creates a Background that uses the indicated Color.
	 * 
	 * @param color
	 * 			  The Color with the desired RGBA settings.
	 *************************************************************************/
	public Background(Color color)
	{
		this(color.red(), color.green(), color.blue(), color.alpha());
	}

	/*************************************************************************
	 * Accesses the dimensions of the Background.
	 * 
	 * @return The width of the Background.
	 ***************************************************************/
	@Override
	public float width()
	{
		return skin != null ? skin.width() * scaleX : sceneWidth;
	}

	/*************************************************************************
	 * Accesses the dimensions of the Background.
	 * 
	 * @return The height of the Background. 
	 ***************************************************************/ @Override
	public float height()
	{
		return skin != null ? skin.height() * scaleY : sceneHeight;
	}

	/*************************************************************************
	 * Accesses the current Color.
	 * 
	 * @return The Color being used to render the Background.
	 ***************************************************************/ @Override
	public Color color() { return color; }

	/*************************************************************************
	 * Accesses the active Skin.
	 * 
	 * @return The Skin being used to render the Background.
	 ***************************************************************/ @Override
	public Skin skin() { return skin; }

	/*************************************************************************
	 * Redefines the current Skin to render the Image with the indicated 
	 * reference.
	 * 
	 * @param imageReference
	 * 			  The indicator of the Image in the Cache to use.
	 *************************************************************************/
	public void setSkin(String imageReference)
	{
		setSkin(new ImageSkin(imageReference));
	}

	/*************************************************************************
	 * Redefines the current Skin.
	 * 
	 * @param skin
	 * 			  The new Skin to use for rendering.
	 ***************************************************************/ @Override
	public void setSkin(Skin skin)
	{
		this.skin = skin;
		color.set(Color.WHITE);
		rescale();
	}

	/*************************************************************************
	 * Sets the Skin reference to null and makes the Background simply render
	 * solid black.
	 *************************************************************************/
	public void clear()
	{
		clear(Color.BLACK);
	}

	/*************************************************************************
	 * Sets the Skin reference to null and makes the Background simply render
	 * the indicated Color.
	 *************************************************************************/
	public void clear(Color color)
	{
		skin = null;
		this.color.set(color);
		rescale();
	}

	/*************************************************************************
	 * Sets whether or not the Background should rotate with the active camera
	 * as when renders. Keeping this enabled will make the scene more believable
	 * if the camera rotates.
	 * 
	 * By default, background rotation is enabled.
	 * 
	 * @param rotate
	 * 			Whether or not rotation should be allowed.
	 *************************************************************************/
	public void rotateWithCamera(boolean rotate)
	{
		if(rotateWithCamera != rotate)
		{
			rotateWithCamera = rotate;
			if(skin != null)
				rescale();
		}
	}

	/*************************************************************************
	 * Sets whether or not the Background should keep the aspect ratio of the 
	 * Skins it uses to render.
	 * 
	 * By default, aspect maintenance is enabled.
	 * 
	 * @param keepAspect
	 * 			  True if the Skin's aspect ratio should be preserved, false if
	 * 			  all rendered Skins should be squashed into the smallest 
	 * 			  dimensions possible.
	 *************************************************************************/
	public void keepAspect(boolean keepAspect)
	{
		if(this.keepAspect != keepAspect)
		{
			this.keepAspect = keepAspect;
			if(skin != null)
				rescale();
		}
	}
	
	/*************************************************************************
	 * This method recalculates the scaling of the Background.
	 *************************************************************************/
	private void rescale()
	{
		// This method is big, and long, and really gnarly,
		// but it actually is somewhat optimized, so don't
		// hold it against it too much. Of course, if you
		// come up with a way to make it more efficient
		// and/or smaller, go ahead and change it!
		sceneWidth = WindowManager.screenWidth();
		sceneHeight = WindowManager.screenHeight();
		float screenDiagonal = (float) Math.sqrt((sceneWidth * sceneWidth) + 
				(sceneHeight * sceneHeight));
		if(skin != null)
		{
			float ratio = skin.width() / skin.height();
			if(keepAspect)
				if(rotateWithCamera)
					if(ratio > 1)
						scaleX = (scaleY = screenDiagonal / skin.height())
								* ratio;
					else
						scaleY = (scaleX = screenDiagonal / skin.width())
								* ratio;
				else
					if(ratio > (sceneWidth / sceneHeight))
						scaleX = (scaleY = sceneHeight / skin.height()) * ratio;
					else
						scaleY = (scaleX = sceneWidth / skin.width()) * ratio;
			else
				if(rotateWithCamera)
				{
					scaleX = screenDiagonal / skin.width();
					scaleY = screenDiagonal / skin.height();
				}
				else
				{
					scaleX = sceneWidth / skin.width();
					scaleY = sceneHeight / skin.height();
				}
		}
		else
			scaleX = scaleY = 1;
	}

	/*************************************************************************
	 * If the current Skin is both not null and is Updateable, this updates the
	 * Skin.
	 * 
	 * @param delta
	 * 			  The number of microseconds that has passed.
	 ***************************************************************/ @Override
	public void update(int delta)
	{
		if(skin != null && skin instanceof Updateable)
			((Updateable) skin).update(delta);
	}

	/*************************************************************************
	 * Renders the Background. Calling this also resets the OpenGL Modelview
	 * Matrix and sets the bound color to white.
	 ***************************************************************/ @Override
	public void render()
	{
		if(color.alpha() > 0)
		{
			if(sceneWidth != WindowManager.controller().width() || 
					sceneHeight != WindowManager.controller().height())
				rescale();
			GL11.glLoadIdentity();
			color.bind();
			if(skin != null)
			{
				Camera camera = GameManager.camera();
				float cameraScaleX = camera.scalar().xScale();
				float cameraScaleY = camera.scalar().yScale();
				float smaller = Math.min(cameraScaleX, cameraScaleY);
				cameraScaleX /= smaller;
				cameraScaleY /= smaller;
				GL11.glTranslated(sceneWidth / 2, sceneHeight / 2, 0);
				GL11.glScalef(cameraScaleX * scaleX, cameraScaleY * scaleY, 1f);
				camera.scalar().bindFlip();
				if(rotateWithCamera)
					camera.rotation().bind();
				skin.render();
			}
			else
			{
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
					GL11.glVertex2f(0, 0);
					GL11.glVertex2f(sceneWidth, 0);
					GL11.glVertex2f(0, sceneHeight);
					GL11.glVertex2f(sceneWidth, sceneHeight);
				GL11.glEnd();
			}
			color.release();
			GL11.glLoadIdentity();
		}
	}
}