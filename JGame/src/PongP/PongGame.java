/*package PongP;

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import  org.lwjgl.*;



public class PongGame {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	private boolean isRunning = true;
	public PongGame(){
		setUpDisplay();
		setUpOpenGL();
		setUpEnteties();
		setUpTimer();
		while(isRunning){
			render();
			logic(getDelta());
			Display.update( );
			Display.sync(60);
					if(Display.isCloseRequested()){
						isRunning = false;
					}
		}		
	}
	private long lastFrame;
	private long getTime(){
		return(Sys.getTime()*1000)/ Sys.getTimerResolution();
		
	}
	public int getDelta(){
		long currentTime = getTime();
		int delta = (int) (currentTime = lastFrame);
		lastFrame = getTime();
		return delta;		
	}
	private void setUpDisplay(){		
	}
	private void setUpOpenGL(){
		//Initialization code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	private void setUpEnteties(){
	}
	private void setUpTimer(){
		lastFrame = getTime();
	}
	private void render(){
		glClear(GL_COLOR_BUFFER_BIT);
	}
	private void logic(int delta){
		
	}
	private static class Bat 
public static void main(String args []){
		new PongGame();
	}
}*/
