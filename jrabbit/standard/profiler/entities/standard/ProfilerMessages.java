package org.jrabbit.standard.profiler.entities.standard;

import org.jrabbit.base.data.ChangeListener;
import org.jrabbit.standard.profiler.entities.base.TextProfilerEntity;
import org.jrabbit.standard.profiler.util.Message;

/*****************************************************************************
 * ProfilerMessages, similarly to ProfilerMessage, displays the output of a 
 * series of Messages. It is automatically updated whenever their contents 
 * change.
 * 
 * NOTE: This class renders its reference above its Messages, this is to declare
 * the "category" of Messages it displays.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class ProfilerMessages extends TextProfilerEntity
{
	/**
	 * The list of MessageListeners.
	 **/
	protected MessageListener[] listeners;

	/*************************************************************************
	 * Creates a ProfilerMessages that displays the information represented by
	 * the indicated Messages. It will be automatically updated whenever their
	 * contents change.
	 * 
	 * @param reference
	 * 			  The identifier of this ProfilerEntity. This is also the 
	 *            category name rendered above the list of Messages.
	 * @param messages
	 * 			  The Messages to render.
	 *************************************************************************/
	public ProfilerMessages(String reference, Message... messages)
	{
		super(reference);
		setMessages(messages);
	}

	/*************************************************************************
	 * Accesses the indicated Message object.
	 * 
	 * NOTE: The place specified should not be the line number of text, but the 
	 * index in the list of Messages.
	 * 
	 * @param place
	 * 			  The index of the Message to access.
	 * 
	 * @return The Message at the specified index that is being listened to. 
	 *************************************************************************/
	public Message message(int place)
	{
		return listeners[place].controller;
	}

	/*************************************************************************
	 * Redefines the Message at the indicated place in the list.
	 * 
	 * @param
	 * 			  The index of the Message to redefine.
	 * @param message
	 * 			  The new Message to render.
	 *************************************************************************/
	public void setMessage(int place, Message message)
	{
		listeners[place].kill();
		listeners[place] = new MessageListener(place + 1, message);
		setText(place + 1, message.message());
	}

	/*************************************************************************
	 * Resets the list of Messages.
	 * 
	 * @param messages
	 * 			  The new Messages to render.
	 *************************************************************************/
	public void setMessages(Message... messages)
	{
		if(listeners != null)
			for(MessageListener listener : listeners)
				listener.kill();
		text = new String[messages.length + 1];
		widths = new float[messages.length + 1];
		listeners = new MessageListener[messages.length];
		for(int i = 0; i < messages.length; i++)
		{
			text[i + 1] = messages[i].message();
			widths[i + 1] = fontPointer().object().widthOf(text[i + 1]);
			listeners[i] = new MessageListener(i + 1, messages[i]);
		}
		setText(0, "-  -  " + reference + "  -  -");
	}

	/*************************************************************************
	 * A MessageListener automatically updates the text of the ProfilerMessages
	 * it belongs to whenever the Message it is listening to changes.
	 *************************************************************************/
	protected class MessageListener implements ChangeListener
	{
		/**
		 * The line number that the Message's text should appear on.
		 **/
		protected int place;
		
		/**
		 * The Message the MessageListener is listening to.
		 **/
		protected Message controller;

		/*********************************************************************
		 * Creates a new MessageListener that focuses on the indicated
		 * Message.
		 * 
		 * @param place
		 * 			  The line number that the controller's message should 
		 * 			  appear on.
		 * @param controller
		 * 			  The Message to listen to.
		 *********************************************************************/
		public MessageListener(int place, Message controller)
		{
			this.place = place;
			this.controller = controller;
			controller.setListener(this);
		}

		/*********************************************************************
		 * Clears the reference to the Message so that no undesired references 
		 * remain.
		 *********************************************************************/
		public void kill()
		{
			if(controller != null && controller.listener() == this)
				controller.setListener(null);
			controller = null;
		}

		/*********************************************************************
		 * Alerts the MessageListener that it needs to update the text in the
		 * ProfilerMessages.
		 ***********************************************************/ @Override
		public void alertChange()
		{
			setText(place, controller.message());
		}
	}
}