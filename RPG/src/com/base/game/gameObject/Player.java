package com.base.game.gameObject;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.base.engine.GameObject;
import com.base.engine.Main;
import com.base.engine.Sprite;
import com.base.game.Delay;
import com.base.game.Time;
import com.base.game.Util;
import com.base.game.gameObject.items.Item;

public class Player extends StatObject {

	public static final int SIZE = 33;
	public static final float FORWARD = 0;
	public static final float BACKWARD = 1;
	public static final float LEFT = 2;
	public static final float RIGHT = 3;

	private Inventory inventory;
	private int attackDamage;
	private int attackRange;
	private int facingDirection;
	private Delay attackDelay;

	public Player(float x, float y) {
		statistic = new Statistic(0, true);
		inventory = new Inventory(20);
		init(x, y, 0.1f, 1f, 0.25f, SIZE, SIZE, PLAYER_ID);
		this.x = x;
		this.y = y;
		this.spr = new Sprite(0.1f, 1f, 0.25f, SIZE, SIZE);
		attackDelay = new Delay(500);
		attackRange = 48;
		facingDirection = 0;
		attackDamage = 1;
		attackDelay.end();
	}

	@Override
	public void update() {
		// ////System.out.println("Statistics: S: " + getSpeed() + " L: " +
		// getLevel() + " MHP: " + getMaxHealth() + " HP: " + getCurrentHealth()
		// + " ST: " + getStrength() + " M: " + getMagic());
	}

	public void getInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			move(0, 1);
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			move(0, -1);
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			move(-1, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			move(1, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && attackDelay.over())
			attack();
	}

	public void attack() {
		System.out.print ("Attacking");
		//Find objects in attack range.
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		if (facingDirection == FORWARD) {
			objects = Main.rectangleCollide(x, y, x + SIZE, y + attackRange);
		} else if (facingDirection == BACKWARD) {
			objects = Main.rectangleCollide(x, y, x + SIZE, y - attackRange);
		} else if (facingDirection == LEFT) {
			objects = Main.rectangleCollide(x, y, x - attackRange, y + SIZE);
		} else if (facingDirection == RIGHT) {
			objects = Main.rectangleCollide(x, y, x + attackRange, y + SIZE);
		}
		//Find which objects are enemies.
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();

		for (GameObject go : objects) {
			if (go.getType() == ENEMY_ID)
				enemies.add((Enemy)go);
		}
		//Find closest enemy if it exists.
		if(enemies.size() > 0){
			Enemy target = enemies.get(0);
			if(enemies.size() > 1){
				for(Enemy e : enemies){
					if(Util.dist(x, y, e.getX(), e.getY())< Util.dist(x, y, target.getsX(), target.getsY()))
						target = e;
					
				}
			}
			target.damage(attackDamage);
			System.out.println(" : " + target.getCurrentHealth() + "/" + target.getMaxHealth());
		}else{
			System.out.println(" : No target.");
		}

		attackDelay.start();
	}

	private void move(float magX, float magY) {

		if (magX == 0 && magY == 1) {
			facingDirection = (int) FORWARD;
		}
		if (magX == 1 && magY == -1) {
			facingDirection = (int) BACKWARD;
		}
		if (magX == -1 && magY == 0) {
			facingDirection = (int) LEFT;
		}
		if (magX == 1 && magY == 0) {
			facingDirection = (int) RIGHT;
		}

		x += getSpeed() * magX * Time.getDelta();
		y += getSpeed() * magY * Time.getDelta();// /////MOvE
	}

	public void addItem(Item item) {
		inventory.add(item);
	}

	public void addXp(float amt) {
		statistic.addXp(amt);
	}

}
