package org.jrabbit.standard.profiler.entities.standard;

import org.jrabbit.base.data.ChangeListener;
import org.jrabbit.standard.profiler.entities.base.FontBasedProfilerEntity;
import org.jrabbit.standard.profiler.util.Message;

/*****************************************************************************
 * A ProfilerMessage is a ProfilerEntity that display the contents of ONE
 * Message. It is synced to the Message automatically, so that whenever its 
 * message changes, it is updated appropriately.
 * 
 * Keep in mind that to show the combined info of multiple Messages, 
 * the class ProfilerMessages (plural) should be used instead.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ProfilerMessage extends FontBasedProfilerEntity implements 
		ChangeListener
{
	/**
	 * The Message being represented.
	 **/
	protected Message controller;

	/*************************************************************************
	 * Creates a ProfilerMessage that displays the text of the supplied Message.
	 * 
	 * NOTE: The reference of the created ProfilerMessage is the same as the 
	 * reference of the supplied Message.
	 * 
	 * @param controller
	 * 			  The Message to render.
	 *************************************************************************/
	public ProfilerMessage(Message controller)
	{
		super(controller.reference());
		setController(controller);
	}
	
	/*************************************************************************
	 * Accesses the Message currently being rendered.
	 * 
	 * @return The current Message.
	 *************************************************************************/
	public Message controller() { return controller; }
	
	/*************************************************************************
	 * Redefines the current Message.
	 * 
	 * @param controller
	 * 			  The new Message to render.
	 *************************************************************************/
	public void setController(Message controller)
	{
		if(this.controller != null)
			this.controller.setListener(null);
		this.controller = controller;
		controller.setListener(this);
		updateDimensions();
	}
	
	/*************************************************************************
	 * Recalculates the dimensions of this ProfilerEntity.
	 *************************************************************************/
	protected void updateDimensions()
	{
		width = fontPointer.object().widthOf(controller.message());
		height = fontPointer.object().heightOf(controller.message());
	}
	
	/*************************************************************************
	 * Alerts the ProfilerMessage that the message has changed and it needs to
	 * recalculate its dimensions.
	 ***************************************************************/ @Override
	public void alertChange()
	{
		updateDimensions();
	}
	
	/*************************************************************************
	 * Renders the Message.
	 ***************************************************************/ @Override
	public void render()
	{
		fontPointer.object().render(controller.message());
	}
}