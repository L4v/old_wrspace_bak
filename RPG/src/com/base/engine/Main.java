package com.base.engine;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.base.game.Game;
import com.base.game.Time;

import static org.lwjgl.opengl.GL11.*;

public class Main {

	private static Game game;

	public static void main(String[] args) {
		initDisplay();
		initGL();
		initGame();

		gameLoop();

		cleanUp();
	}

	private static void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);

		glDisable(GL_DEPTH_TEST);

		glClearColor(0, 0, 0, 0);

	}

	public static ArrayList<GameObject> sphereCollide(float x, float y, float radius) {
		return game.sphereCollide(x, y, radius);
	}
	public static ArrayList<GameObject> rectangleCollide(float x1, float y1, float x2, float y2){
		return game.rectangleCollide(x1, y1, x2,y2);
	}

	private static void initGame() {
		game = new Game();
	}

	private static void getInput() {
		game.getInput();
	}

	private static void update() {
		game.update();
	}

	private static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();

		game.render();

		Display.update();
		Display.sync(60);

	}

	private static void gameLoop() {
		Time.init();
		while (!Display.isCloseRequested()) {
			Time.update();
			getInput();
			update();
			render();
		}
	}

	private static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
			Keyboard.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void cleanUp() {
		Display.destroy();
		Keyboard.destroy();
	}
}
