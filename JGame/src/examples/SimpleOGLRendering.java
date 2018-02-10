package examples;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class SimpleOGLRendering {
	public SimpleOGLRendering(){
		try{
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Hello LWJGL");
			Display.create();
		}catch(LWJGLException e){
			e.printStackTrace();
		}
		//Initialization code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(  ) ;
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		while(!Display.isCloseRequested()){
			//rendering
			
			glBegin(GL_QUADS);
			glVertex2i(400, 400);
			glVertex2i(500, 400);
			glVertex2i(500, 500);
			glVertex2i(400, 500);
			glEnd();
			
			
			glBegin(GL_LINES);
				glVertex2i(100, 100);
				glVertex2i(200, 200);
			glEnd();	
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}
	
	public static void main(String[] args) {
		new SimpleOGLRendering();

	}

}
