package com.base.game;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.base.engine.GameObject;
import com.base.engine.Physics;
import com.base.game.gameObject.CookehMonster;
import com.base.game.gameObject.Player;
import com.base.game.gameObject.items.Cube;

public class Game {

	private ArrayList<GameObject> remove;
	private ArrayList<GameObject> objects;
	private Player player;

	public Game() {
		objects = new ArrayList<GameObject>();
		player = new Player(Display.getWidth() / 2 - Player.SIZE / 2, Display.getHeight() / 2 - Player.SIZE / 2);
		remove = new ArrayList<GameObject>();

		objects.add(player);
		objects.add(new Cube(32, 32, player));
		objects.add(new CookehMonster(300, 500, 1));
	}

	public void getInput() {

		player.getInput();
	}

	public void update() {
		for (GameObject go : objects) {
			if (!go.getRemove())
				go.update();
			else {
				remove.add(go);
			}

		}
		for (GameObject go : remove)
			objects.remove(go);
	}

	public void render() {
		for (GameObject go : objects) {
			go.render();

		}
	}

	public ArrayList<GameObject> sphereCollide(float x, float y, float radius) {

		ArrayList<GameObject> res = new ArrayList<GameObject>();

		for (GameObject go : objects) {
			if (Util.dist(go.getX(), go.getY(), x, y) < radius)
				res.add(go);
		}
		return res;
	}

	public ArrayList<GameObject> rectangleCollide(float x1, float y1, float x2, float y2) {
		ArrayList<GameObject> res = new ArrayList<GameObject>();
		float sx = (x2 - x1);
		float sy = (y2 - y1);

		Rectangle collider = new Rectangle((int) x1, (int) y1, (int) sx, (int) sy);

		for (GameObject go : objects) {
			if(Physics.checkCollision(collider, go) != null){
				res.add(go);
			}
		}
		return res;
	}
}
