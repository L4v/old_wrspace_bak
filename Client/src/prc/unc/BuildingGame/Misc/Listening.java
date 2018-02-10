package prc.unc.BuildingGame.Misc;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import prc.unc.BuildingGame.Component;
import prc.unc.BuildingGame.Level.Sky;

public class Listening implements KeyListener, MouseListener, MouseMotionListener,MouseWheelListener{


	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_H:
			if(Component.character.alive){
				Component.character.alive = false;
			}else{
				Component.character.alive = true;
			}
			break;
		case KeyEvent.VK_Q:
			if(Component.Paused == true){
				System.exit(0);
			}
		case KeyEvent.VK_ESCAPE:
			if(Component.character.alive == true && Component.Menu == false){
			if(Component.Paused){
				Component.Paused = false;
			}else
				Component.Paused = true;
			}else if (Component.character.alive == false && Component.Menu == false && Component.Paused == false){
				System.exit(1);
			}
			break;
		case KeyEvent.VK_D:
			Component.isMoving = true;
			Component.dir = Component.character.movingSpeed;  
			
		
		break;
		case KeyEvent.VK_A:
			Component.isMoving = true;
			Component.dir = -Component.character.movingSpeed;
		break;
		case KeyEvent.VK_SPACE:
			Component.isJumping = true;
			
			
			break;
		case KeyEvent.VK_E:
			if(Inventory.isOpen){
				Inventory.isOpen = false;
			}else{
				Inventory.isOpen = true;
			}
				break;
			
				
			
		case KeyEvent.VK_1:
			Inventory.selected = 0;
			break;
		case KeyEvent.VK_2:
			Inventory.selected = 1;
			break;
		case KeyEvent.VK_3:
			Inventory.selected = 2;
			break;
		case KeyEvent.VK_4:
			Inventory.selected = 3;
			break;
		case KeyEvent.VK_5:
			Inventory.selected = 4;
			break;
		case KeyEvent.VK_6:
			Inventory.selected = 5;
			break;
		case KeyEvent.VK_7:
			Inventory.selected = 6;
			break;
		case KeyEvent.VK_8:
			Inventory.selected = 7;
			break;
		case KeyEvent.VK_9:
			Inventory.selected = 8;
			break;
		case KeyEvent.VK_F5:
			Sky.time = Sky.night;
			break;
		case KeyEvent.VK_F4:
			Sky.time = Sky.day;
			break;
		}
		
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_D:
		if(Component.dir == Component.character.movingSpeed){
			Component.isMoving = false;
		}
		break;
		case KeyEvent.VK_A:
			if(Component.dir == -Component.character.movingSpeed){
				Component.isMoving = false;
			}

		break;
		case KeyEvent.VK_SPACE:
			Component.isJumping = false;
			
			
			break;
		}
	}
	public void keyTyped(KeyEvent e) {
		
	}
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()>0){/////////DOWNn
			if(Inventory.selected < Tile.invLength - 1){
				Inventory.selected += 1;
			}else{
				Inventory.selected = 0;}
			}else if(e.getWheelRotation()<0){/////////DOWNn
				if(Inventory.selected > 0){
					Inventory.selected -= 1;
				}else{
					Inventory.selected = Tile.invLength -1;}}
		
	}
	public void mouseDragged(MouseEvent e) {
		Component.mse.setLocation(e.getX(), e.getY());

		
	}
	public void mouseMoved(MouseEvent e) {
		Component.mse.setLocation(e.getX(), e.getY());
		
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			Component.isMouseLeftc = true;
		}else if(e.getButton() == MouseEvent.BUTTON3){
			Component.isMouseRightc = true;
		}
		
	}
	public void mouseEntered(MouseEvent e) {
		
		
	}
	public void mouseExited(MouseEvent e) {
		
		
	}
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			Component.isMouseLeft = true;
		}else if(e.getButton() == MouseEvent.BUTTON3){
			Component.isMouseRight =true;
		}
		Inventory.click(e);
	}
	public void mouseReleased(MouseEvent e) {
		if(Component.level.bx > 0){
			Component.level.bx = 0;
		}
		if(e.getButton()==MouseEvent.BUTTON1){
			Component.isMouseLeft = false;
		}else if(e.getButton() == MouseEvent.BUTTON3){
			Component.isMouseRight =false;
		}
		
	}

}
