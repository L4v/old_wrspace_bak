package org.jrabbit.standard.profiler.entities.base;

/*****************************************************************************
 * A TextProfilerEntity extends FontBasedProfilerEntity to create a 
 * ProfilerEntity that renders lines of text.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class TextProfilerEntity extends FontBasedProfilerEntity
{
	/**
	 * The lines of text.
	 **/
	protected String[] text;
	
	/**
	 * The recorded width of all lines of text.
	 **/
	protected float[] widths;

	/*************************************************************************
	 * Creates a TextProfilerEntity with the indicated text.
	 * 
	 * @param reference
	 * 			  The identifier of this ProfilerEntity.
	 * @param text
	 * 			  The text to display.
	 *************************************************************************/
	public TextProfilerEntity(String reference, String... text)
	{
		super(reference);
		setText(text);
	}
	
	/*************************************************************************
	 * Accesses one of the lines of text.
	 * 
	 * @param place
	 * 			  The number of the line of text to access.
	 * 
	 * @return The String that represents the indicated line of text.
	 *************************************************************************/
	public String getText(int place)
	{
		return text[place];
	}

	/*************************************************************************
	 * Changes one line of text. This does not affect any other lines.
	 * 
	 * @param place
	 * 			  The number of the line of text to change.
	 * @param line
	 * 			  The new line to display.
	 *************************************************************************/
	public void setText(int place, String line)
	{
		text[place] = line;
		widths[place] = fontPointer.object().widthOf(line);
		updateDimensions();
	}

	/*************************************************************************
	 * Redefines all of the text.
	 * 
	 * @param text
	 * 			  The new text to display.
	 *************************************************************************/
	public void setText(String... text)
	{
		this.text = text;
		widths = new float[text.length];
		for(int i = 0; i < text.length; i++)
			widths[i] = fontPointer().object().widthOf(text[i]);
		updateDimensions();
	}

	/*************************************************************************
	 * Recalculates the width and height of the whole ProfilerEntity.
	 *************************************************************************/
	protected void updateDimensions()
	{
		width = 0;
		for(float lineWidth : widths)
			width = Math.max(width, lineWidth);
		height = fontPointer.object().heightOf(text);
	}

	/*************************************************************************
	 * Renders the text.
	 ***************************************************************/ @Override
	public void render()
	{
		fontPointer.object().render(text);
	}
}