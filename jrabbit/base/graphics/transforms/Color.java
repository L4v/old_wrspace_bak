package org.jrabbit.base.graphics.transforms;

import java.util.ArrayList;

import org.jrabbit.base.graphics.transforms.listeners.ColorListener;
import org.lwjgl.opengl.GL11;

/*****************************************************************************
 * A Color represents a color setting for OpenGL.
 * 
 * In addition to setting the color and binding/releasing it, there are some
 * additional methods that provide functionality to quickly manipulate and blend
 * colors.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Color implements GLTransform
{
	/**
	 * Solid white. [1, 1, 1]
	 **/
	public static final Color WHITE = new Color();

	/**
	 * A light gray. [0.75, 0.75, 0.75]
	 **/
	public static final Color LIGHT_GRAY = new Color(0.75f, 0.75f, 0.75f);

	/**
	 * A medium gray. [0.5, 0.5, 0.5]
	 **/
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f);

	/**
	 * A dark gray. [0.25, 0.25, 0.25]
	 **/
	public static final Color DARK_GRAY = new Color(0.25f, 0.25f, 0.25f);

	/**
	 * Solid black. [0, 0, 0]
	 **/
	public static final Color BLACK = new Color(0, 0, 0);

	/**
	 * A full red. [1, 0, 0]
	 **/
	public static final Color RED = new Color(1f, 0, 0);

	/**
	 * A warm orange. [1, 0.5, 0]
	 **/
	public static final Color ORANGE = new Color(1f, 0.5f, 0);

	/**
	 * A sunny yellow. [1, 1, 0]
	 **/
	public static final Color YELLOW = new Color(1f, 1f, 0);

	/**
	 * A vivid green. [0, 1, 0]
	 **/
	public static final Color GREEN = new Color(0, 1f, 0);

	/**
	 * A bright, electric blue. [0, 1, 1]
	 **/
	public static final Color CYAN = new Color(0, 1f, 1f);

	/**
	 * A deep blue. [0, 0, 1]
	 **/
	public static final Color BLUE = new Color(0, 0, 1f);

	/**
	 * A deep purple. [0.5, 0, 0.5]
	 **/
	public static final Color VIOLET = new Color(0.5f, 0, 0.5f);

	/**
	 * A vivid magenta. [1, 0, 1]
	 **/
	public static final Color MAGENTA = new Color(1f, 0, 1f);

	/**
	 * A bright, acid pink. [1, 0.35, 0.65]
	 **/
	public static final Color PINK = new Color(1f, 0.35f, 0.65f);

	/**
	 * The red value of this color.
	 **/
	private float r;

	/**
	 * The green value of this color.
	 **/
	private float g;

	/**
	 * The blue value of this color.
	 **/
	private float b;

	/**
	 * The alpha (transparency) value of this color.
	 **/
	private float a;

	/**
	 * A list of all objects listening to this color.
	 **/
	private ArrayList<ColorListener> listeners;

	/*************************************************************************
	 * Creates a white color.
	 *************************************************************************/
	public Color()
	{
		this(1f, 1f, 1f, 1f);
	}

	/*************************************************************************
	 * Creates a color with the indicated hue, but with full transparency.
	 * 
	 * The allowed values are from 0 to 1, inclusive.
	 * 
	 * @param red
	 *            The red value.
	 * @param green
	 *            The green value.
	 * @param blue
	 *            The blue value.
	 *************************************************************************/
	public Color(float red, float green, float blue)
	{
		this(red, green, blue, 1f);
	}

	/*************************************************************************
	 * Creates a color with the indicated hue and transparency.
	 * 
	 * The allowed values are from 0 to 1, inclusive.
	 * 
	 * @param red
	 *            The red value.
	 * @param green
	 *            The green value.
	 * @param blue
	 *            The blue value.
	 * @param alpha
	 *            The alpha value.
	 *************************************************************************/
	public Color(float red, float green, float blue, float alpha)
	{
		r = getValidatedFloat(red);
		g = getValidatedFloat(green);
		b = getValidatedFloat(blue);
		a = getValidatedFloat(alpha);
		listeners = new ArrayList<ColorListener>();
	}

	/*************************************************************************
	 * Creates a color with the same values as the indicated java AWT Color.
	 * 
	 * @param color
	 *            The color to copy.
	 *************************************************************************/
	public Color(java.awt.Color color)
	{
		this(color.getRed() / 255f, color.getGreen() / 255f,
				color.getBlue() / 255f, color.getAlpha() / 255f);
	}

	/*************************************************************************
	 * Creates a color with the same values as the indicated Slick Color.
	 * 
	 * @param color
	 *            The color to copy.
	 *************************************************************************/
	public Color(org.newdawn.slick.Color color)
	{
		this(color.r, color.g, color.b, color.a);
	}

	/*************************************************************************
	 * Adds a ColorListener to this object. This will cause the object to be
	 * notified whenever any of the Color's values change.
	 * 
	 * @param listener
	 *            The listener to remove.
	 *************************************************************************/
	public void addListener(ColorListener listener)
	{
		listeners.add(listener);
	}

	/*************************************************************************
	 * Removes a ColorListener from the object. This causes it to stop receiving
	 * updates.
	 * 
	 * @param listener
	 *            The listener to remove.
	 * 
	 * @return True if the listener was removed, false if not.
	 *************************************************************************/
	public boolean removeListener(ColorListener listener)
	{
		return listeners.remove(listener);
	}

	/*************************************************************************
	 * Alerts all listeners that the Color has changed.
	 *************************************************************************/
	protected void alertChange()
	{
		for (ColorListener listener : listeners)
			listener.changed(this);
	}

	/*************************************************************************
	 * Creates a copy of the color.
	 * 
	 * @return A new Color that has identical values to this one.
	 *************************************************************************/
	public Color copy()
	{
		return new Color(r, g, b, a);
	}

	/*************************************************************************
	 * Sets the Color to opaque white.
	 *************************************************************************/
	public void reset()
	{
		set(1f, 1f, 1f, 1f);
	}

	/*************************************************************************
	 * Individually sets the red value of the color. The value supplied is
	 * capped to [0, 1] if it is outside those bounds.
	 * 
	 * @param red
	 *            The new red value.
	 *************************************************************************/
	public void setRed(float red)
	{
		r = getValidatedFloat(red);
		alertChange();
	}

	/*************************************************************************
	 * Accesses the red value of the color.
	 * 
	 * @return The strength of red in the color.
	 *************************************************************************/
	public float red()
	{
		return r;
	}

	/*************************************************************************
	 * Individually sets the green value of the color. The value supplied is
	 * capped to [0, 1] if it is outside those bounds.
	 * 
	 * @param green
	 *            The new green value.
	 *************************************************************************/
	public void setGreen(float green)
	{
		g = getValidatedFloat(green);
		alertChange();
	}

	/*************************************************************************
	 * Accesses the green value of the color.
	 * 
	 * @return The strength of green in the color.
	 *************************************************************************/
	public float green()
	{
		return g;
	}

	/*************************************************************************
	 * Individually sets the blue value of the color. The value supplied is
	 * capped to [0, 1] if it is outside those bounds.
	 * 
	 * @param blue
	 *            The new blue value.
	 *************************************************************************/
	public void setBlue(float blue)
	{
		b = getValidatedFloat(blue);
		alertChange();
	}

	/*************************************************************************
	 * Accesses the blue value of the color.
	 * 
	 * @return The strength of blue in the color.
	 *************************************************************************/
	public float blue()
	{
		return b;
	}

	/*************************************************************************
	 * Individually sets the alpha value of the color. The value supplied is
	 * capped to [0, 1] if it is outside those bounds.
	 * 
	 * @param alpha
	 *            The new alpha value.
	 *************************************************************************/
	public void setAlpha(float alpha)
	{
		a = getValidatedFloat(alpha);
		alertChange();
	}

	/*************************************************************************
	 * Accesses the alpha value of the color.
	 * 
	 * @return How opaque the color is. 1.0 = opaque, 0 = transparent.
	 *************************************************************************/
	public float alpha()
	{
		return a;
	}

	/*************************************************************************
	 * Linearly adjusts the alpha of the color.
	 * 
	 * Example: if alpha is at 0.5f, supplying 0.25f will change it to 0.75.
	 * 
	 * @param amount
	 *            The amount to shift alpha.
	 *************************************************************************/
	public void shiftAlpha(float amount)
	{
		a = getValidatedFloat(a + amount);
		alertChange();
	}

	/*************************************************************************
	 * Redefines the color.
	 * 
	 * @param red
	 *            The new red value.
	 * @param green
	 *            The new green value.
	 * @param blue
	 *            The new blue value.
	 *************************************************************************/
	public void set(float red, float green, float blue)
	{
		r = getValidatedFloat(red);
		g = getValidatedFloat(green);
		b = getValidatedFloat(blue);
		alertChange();
	}

	/*************************************************************************
	 * Redefines the color.
	 * 
	 * @param red
	 *            The new red value.
	 * @param green
	 *            The new green value.
	 * @param blue
	 *            The new blue value.
	 * @param alpha
	 *            The new alpha value.
	 *************************************************************************/
	public void set(float red, float green, float blue, float alpha)
	{
		r = getValidatedFloat(red);
		g = getValidatedFloat(green);
		b = getValidatedFloat(blue);
		a = getValidatedFloat(alpha);
		alertChange();
	}

	/*************************************************************************
	 * Redefines the color to have the same values as another.
	 * 
	 * NOTE: Alpha is not affected.
	 * 
	 * @param color
	 *            The color to copy.
	 *************************************************************************/
	public void set(Color color)
	{
		r = color.r;
		g = color.g;
		b = color.b;
		alertChange();
	}

	/*************************************************************************
	 * Redefines the color to have the same values as a Slick Color.
	 * 
	 * NOTE: Alpha is not affected.
	 * 
	 * @param color
	 *            The color to copy.
	 *************************************************************************/
	public void set(org.newdawn.slick.Color color)
	{
		r = color.r;
		g = color.g;
		b = color.b;
		alertChange();
	}

	/*************************************************************************
	 * Redefines the color to have the same values as a java AWT Color.
	 * 
	 * NOTE: Alpha is not affected.
	 * 
	 * @param color
	 *            The color to copy.
	 *************************************************************************/
	public void set(java.awt.Color color)
	{
		r = color.getRed() / 255f;
		g = color.getGreen() / 255f;
		b = color.getBlue() / 255f;
		alertChange();
	}

	/*************************************************************************
	 * Caps the indicated value so that it stays within the valid bounds for the
	 * color.
	 * 
	 * @param value
	 *            The value to cap.
	 * 
	 * @return If the value is within the bounds, returns it. If not, returns
	 *         the closest value within bounds.
	 *************************************************************************/
	private float getValidatedFloat(float value)
	{
		if (value >= 1f)
			return 1f;
		else if (value <= 0)
			return 0;
		else
			return value;
	}

	/*************************************************************************
	 * Lightens the color by the indicated amount.
	 * 
	 * NOTE: This method does not preserve hue or saturation. Instead, it moves
	 * each of the color values closer to 1.0 by the indicated percent. So,
	 * supplying 1 will always make the color become white; supplying 0.5f will
	 * make the color 50% less dark.
	 * 
	 * @param amount
	 *            The percentage to adjust each color value closer to 1.0.
	 *************************************************************************/
	public void brighten(float amount)
	{
		set(r + (1f-r) * amount, g + (1f-g) * amount, b + (1f-b) * amount);
		alertChange();
	}

	/*************************************************************************
	 * Multiplies the color values by the indicated amount. This preserves hue
	 * and saturation, but makes the value change.
	 * 
	 * Take the color [1, 0.6, 0]. Calling darken(0.5f) will result in [0.5f,
	 * 0.3f, 0] as the new hue.
	 * 
	 * @param amount
	 *            The amount to multiply the color by.
	 *************************************************************************/
	public void darken(float amount)
	{
		set(r * amount, g * amount, b * amount);
		alertChange();
	}

	/*************************************************************************
	 * Multiples the values of this Color by those of another.
	 * 
	 * @param color
	 * 			  The Color to multiply by.
	 *************************************************************************/
	public void multiplyBy(Color color)
	{
		r *= color.r;
		g *= color.g;
		b *= color.b;
		a *= color.a;
	}

	/*************************************************************************
	 * Averages the color with another color. After this method, the color's hue
	 * is exactly halfway between its original hue and the others.
	 * 
	 * @param color
	 *            The color to average with.
	 *************************************************************************/
	public void blendWith(Color color)
	{
		set((r + color.r) / 2, (g + color.g) / 2, (b + color.b) / 2);
	}

	/*************************************************************************
	 * Blends this color with another by the indicated amount. If the ratio is
	 * 1, this color becomes identical with the other. If the ratio is 0, this
	 * color does not change. If it is 0.5, the two are blended evenly, etc.
	 * 
	 * @param color
	 *            The color to blend with.
	 * @param ratio
	 *            The amount the other color influences this one.
	 *************************************************************************/
	public void blendWith(Color color, float ratio)
	{
		float p2 = 1f - ratio;
		set(r * p2 + color.r * ratio, g * p2 + color.g * ratio, b * p2 + color.b
				* ratio);
	}

	/*************************************************************************
	 * Averages this color with a series of other colors, resulting in a hue
	 * exactly between all of them.
	 * 
	 * @param colors
	 *            The colors to blend with.
	 *************************************************************************/
	public void blendWith(Color... colors)
	{
		float r = 0;
		float g = 0;
		float b = 0;
		for (Color color : colors)
		{
			r += color.r;
			g += color.g;
			b += color.b;
		}
		set(r / colors.length, g / colors.length, b / colors.length);
	}

	/*************************************************************************
	 * Creates a Color that is a mix of the two supplied Colors. The ratio
	 * indicated determines the weighting of the colors - 0.5 for equal
	 * blending, closer to 1.0 to give the second color more weight, and closer
	 * to 0 to give the first color more weight.
	 * 
	 * @param a
	 *            The first color to blend.
	 * @param b
	 *            The second color to blend.
	 * @param ratio
	 *            The weighting between the two colors.
	 * 
	 * @return A color that is a mix of the two supplied colors.
	 *************************************************************************/
	public static Color blend(Color a, Color b, float ratio)
	{
		float p2 = 1f - ratio;
		return new Color(a.r * p2 + b.r * ratio, a.g * p2 + b.g * ratio, a.b
				* p2 + b.b * ratio);
	}

	/*************************************************************************
	 * Averages a series of other colors, resulting in a hue exactly between all
	 * of them.
	 * 
	 * @param colors
	 *            The colors to blend together.
	 *************************************************************************/
	public static Color blend(Color... colors)
	{
		float r = 0;
		float g = 0;
		float b = 0;
		for (Color color : colors)
		{
			r += color.r;
			g += color.g;
			b += color.b;
		}
		return new Color(r / colors.length, g / colors.length, b
				/ colors.length);
	}

	/*************************************************************************
	 * Applies the color to OpenGL.
	 ***************************************************************/ @Override
	public void bind()
	{
		GL11.glColor4f(r, g, b, a);
	}

	/*************************************************************************
	 * Sets the OpenGL color to solid white.
	 ***************************************************************/ @Override
	public void release()
	{
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	/*************************************************************************
	 * Binds the color multiplied by the indicated transparency. The resulting
	 * transparency is (alpha * trans).
	 * 
	 * @param trans
	 *            The transparency multiplier.
	 *************************************************************************/
	public void bind(float trans)
	{
		GL11.glColor4f(r, g, b, a * trans);
	}

	/*************************************************************************
	 * Binds this color multiplied by the indicated color values.
	 * 
	 * @param red
	 *            The red multiplication factor.
	 * @param green
	 *            The green multiplication factor.
	 * @param blue
	 *            The blue multiplication factor.
	 * @param trans
	 *            The transparency multiplication factor.
	 *************************************************************************/
	public void bind(float red, float green, float blue, float trans)
	{
		GL11.glColor4f(r * red, g * green, b * blue, a * trans);
	}

	/*************************************************************************
	 * Binds this color multiplied by the indicated color.
	 * 
	 * @param mult
	 *            The color to multiply this color by.
	 *************************************************************************/
	public void bind(Color mult)
	{
		GL11.glColor4f(r * mult.r, g * mult.g, b * mult.b, a * mult.a);
	}

	/*************************************************************************
	 * Sets the clear color to the hue of this Color.
	 *************************************************************************/
	public void bindClear()
	{
		GL11.glClearColor(r, g, b, a);
	}

	/*************************************************************************
	 * As equals() in Object, but if the supplied object is a color, it checks
	 * to see if their color values are the same.
	 * 
	 * @param object
	 *            The object to check against.
	 * 
	 * @return Whether or not this object is equivalent to the target.
	 ***************************************************************/ @Override
	public boolean equals(Object object)
	{
		if (object instanceof Color)
		{
			Color c = (Color) object;
			return ((c.r == r) && (c.g == g) && (c.b == b) && (c.a == a));
		}
		return false;
	}

	/*************************************************************************
	 * Obtain the color as a String.
	 * 
	 * @return A String representation of the Color.
	 ***************************************************************/ @Override
	public String toString()
	{
		return '[' + r + ", " + g + ", " + b + ", " + a + ']';
	}
}