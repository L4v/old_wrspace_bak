package com.threed.front;

import java.awt.event.KeyEvent;

import com.threed.front.input.Controler;

public class Game {
	public int time;
	public Controler control;
	public Game(){
		control = new Controler();
	}

	public void tick(boolean[] key) {
		time ++;
		boolean forward = key[KeyEvent.VK_W];
		boolean backward = key[KeyEvent.VK_S];
		boolean right = key[KeyEvent.VK_D];
		boolean left = key[KeyEvent.VK_A];
		boolean turnLeft = key[KeyEvent.VK_LEFT];
		boolean turnRight = key[KeyEvent.VK_RIGHT];
		
		control.tick(forward, backward, left, right, turnLeft, turnRight);
		
	}
}
