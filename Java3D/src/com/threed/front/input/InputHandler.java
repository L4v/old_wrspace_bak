package com.threed.front.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, FocusListener, KeyListener, MouseMotionListener {
	public boolean[] key = new boolean[68836];
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseMoved(MouseEvent e) {
		
		
	}

	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode > 0 && keyCode < key.length){
			key[keyCode] = true;
		}
	}

	
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode > 0 && keyCode < key.length){
			key[keyCode] = false;
		}
	}

	
	public void keyTyped(KeyEvent e) {
		
		
	}

	
	public void focusGained(FocusEvent e) {
		
		
	}

	
	public void focusLost(FocusEvent e) {
		for(int i = 0; i < key.length; i++){
			key[i] = false;
		}
		
	}

	
	public void mouseClicked(MouseEvent e) {
		
		
	}

	
	public void mouseEntered(MouseEvent e) {
		
		
	}

	
	public void mouseExited(MouseEvent e) {
		
		
	}

	
	public void mousePressed(MouseEvent e) {
		
		
	}

	
	public void mouseReleased(MouseEvent e) {
		
		
	}

}
