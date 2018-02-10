package main;

import java.awt.event.*;

public class Listening implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	public Listening() {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_A:
			TheCharacter.movingLeft = true;
			break;
		case KeyEvent.VK_D:
			TheCharacter.movingRight = true;
			break;
		case KeyEvent.VK_S:
			TheCharacter.movingDown = true;
			break;
		case KeyEvent.VK_W:
			TheCharacter.movingUp = true;
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_A:
			TheCharacter.movingLeft = false;
			break;
		case KeyEvent.VK_D:
			TheCharacter.movingRight = false;
		case KeyEvent.VK_W:
			TheCharacter.movingUp = false;
			break;
		case KeyEvent.VK_S:
			TheCharacter.movingDown = false;
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
