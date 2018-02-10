package org.jrabbit.standard.game.objects.specialized;

import org.jrabbit.base.graphics.skins.text.TextSkin;
import org.jrabbit.standard.game.objects.base.BaseSprite;

/*****************************************************************************
 * A TextSprite is the basic Sprite used to render Text; it extends BaseSprite 
 * by using a TextSkin to render itself and manage its dimensions.
 * 
 * The text and Font used by a TextSprite are both fully dynamic; to alter them,
 * simply access the TextSkin and make the changes directly to it.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class TextSprite extends BaseSprite
{
	/**
	 * The active TextSkin.
	 **/
	protected TextSkin textSkin;
	
	/*************************************************************************
	 * Creates a TextSprite that uses the default Font to render the indicated
	 * text.
	 * 
	 * @param text
	 * 			  The text to render.
	 *************************************************************************/
	public TextSprite(String text)
	{
		this(new TextSkin(text));
	}
	
	/*************************************************************************
	 * Creates a TextSprite that uses the indicated Font to render the indicated
	 * text.
	 * @param text
	 * 			  The text to render.
	 * @param font
	 * 			  The reference that identifies the Font to use.
	 *************************************************************************/
	public TextSprite(String text, String font)
	{
		this(new TextSkin(text, font));
	}
	
	/*************************************************************************
	 * Creates a TextSprite from the indicated TextSkin.
	 * 
	 * NOTE: If you wish to use a RenderedTextSkin, simply supply one to this
	 * constructor.
	 * 
	 * @param textSkin
	 * 			  The TextSkin to use for rendering and dimension checking.
	 *************************************************************************/
	public TextSprite(TextSkin textSkin)
	{
		setTextSkin(textSkin);
	}
	
	/*************************************************************************
	 * Accesses the current TextSkin.
	 * 
	 * @return The TextSkin being used.
	 *************************************************************************/
	public TextSkin textSkin()
	{
		return textSkin;
	}
	
	/*************************************************************************
	 * Redefines the TextSkin being used.
	 * 
	 * @param textSkin
	 * 			  The new TextSkin to use.
	 *************************************************************************/
	public void setTextSkin(TextSkin textSkin)
	{
		this.textSkin = textSkin;
	}

	/*************************************************************************
	 * Accesses the dimensions of the TextSkin.
	 * 
	 * NOTE: Since Skins are supposed to be rendered from their center, but
	 * TextSkins are rendered from their top-left corners, there is a disparity
	 * between the value returned by this method and the width of the block of
	 * text being rendered. If the desired value is the width of the text 
	 * itself, access the current TextSkin and call {@link 
	 * TextSkin#textWidth()}.
	 * 
	 * @return The width of the active TextSkin.
	 ***************************************************************/ @Override
	public float width() { return textSkin.width(); }

	/*************************************************************************
	 * Accesses the dimensions of the TextSkin.
	 * 
	 * NOTE: Since Skins are supposed to be rendered from their center, but
	 * TextSkins are rendered from their top-left corners, there is a disparity
	 * between the value returned by this method and the height of the block of
	 * text being rendered. If the desired value is the width of the text 
	 * itself, access the current TextSkin and call {@link 
	 * TextSkin#textHeight()}.
	 * 
	 * @return The height of the active TextSkin.
	 ***************************************************************/ @Override
	public float height() { return textSkin.height(); }

	/*************************************************************************
	 * Renders the active TextSkin.
	 ***************************************************************/ @Override
	public void draw()
	{
		textSkin.render();
	}
}