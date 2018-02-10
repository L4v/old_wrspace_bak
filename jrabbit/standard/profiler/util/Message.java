package org.jrabbit.standard.profiler.util;

import org.jrabbit.base.data.ChangeListener;
import org.jrabbit.base.data.Referenced;

/*****************************************************************************
 * A Message is simply a utility object that manages a String (the message), and
 * sends updates to an object whenever it is changed. 
 * 
 * The whole purpose of a Message is to provide a controlled and somewhat 
 * optimized way for ProfilerEntities to handle text they need to render. To do 
 * this, a ProfilerEntity that uses a Message should implement ChangeListener 
 * and register itself with its Message; it will then receive the minimal amount
 * of updates possible when its message is changed. Then, the ProfilerEntity can
 * update any necessary dimension and rendering information.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class Message implements Referenced
{
	/**
	 * The reference of this Message.
	 **/
	protected String reference;
	
	/**
	 * The message this Message represents.
	 **/
	protected String message;
	
	/**
	 * The listener to alert when the message changes.
	 **/
	protected ChangeListener listener;

	/*************************************************************************
	 * Creates a Message labeled by the supplied reference.
	 * 
	 * NOTE: The initial message defaults to 'reference + ": --"'. So, if you
	 * supply, say, "Time" as a reference, the resulting initial message will be
	 * "Time: --".
	 * 
	 * @param reference
	 * 			  The identifier of this Message.
	 *************************************************************************/
	public Message(String reference)
	{
		this(reference, reference + ": --");
	}

	/*************************************************************************
	 * Creates a Message with the indicated reference and initial
	 * message.
	 * 
	 * @param reference
	 * 			  The identifier of this Message.
	 * @param message
	 * 			  The initial message to use.
	 *************************************************************************/
	public Message(String reference, String message)
	{
		this.reference = reference;
		this.message = message;
	}

	/*************************************************************************
	 * Accesses the identifier of this Message.
	 * 
	 * @return The String that identifies this object.
	 ***************************************************************/ @Override
	public String reference() { return reference; }

	/*************************************************************************
	 * Accesses the current message.
	 * 
	 * @return The String that this Message represents.
	 *************************************************************************/
	public String message() { return message; }

	/*************************************************************************
	 * Changes the message this Message uses. If the supplied message is 
	 * different than the one currently used, this Message alerts its listener 
	 * that a change has occurred.
	 * 
	 * @param message
	 * 			  The new message to use.
	 *************************************************************************/
	protected void setMessage(String message)
	{
		if(!this.message.equals(message))
		{
			this.message = message;
			alertListener();
		}
	}

	/*************************************************************************
	 * Accesses the object listening to this Message.
	 * 
	 * @return The ChangeListener that will be notified whenever the message is
	 *         altered.
	 *************************************************************************/
	public ChangeListener listener() { return listener; }

	/*************************************************************************
	 * Redefines this Message's listener.
	 * 
	 * @param listener
	 * 			  The object that should receive notification when the message
	 *            changes.
	 *************************************************************************/
	public void setListener(ChangeListener listener)
	{
		this.listener = listener;
	}

	/*************************************************************************
	 * Alerts this Message's ChangeListener (if it has one) that the message has
	 * changed.
	 *************************************************************************/
	protected void alertListener()
	{
		if(listener != null)
			listener.alertChange();
	}
}