package com.base.game.gameObject;

import java.util.ArrayList;

import com.base.engine.GameObject;
import com.base.engine.Main;
import com.base.engine.Sprite;
import com.base.game.Delay;
import com.base.game.Time;
import com.base.game.Util;

public class Enemy extends StatObject {

	private StatObject target;
	private static float attackRange = 48f;
	private Delay attackDelay;
	private int attackDamage;
	public static final float DAMPING = 0.5f;
	public static float sightRange;

	public Enemy(int level) {
		statistic = new Statistic(level, false);
		target = null;
		attackDelay = new Delay(500);
		attackRange = 48f;
		attackDamage = 1;
		attackDelay.end();
		sightRange = 128;
		type = ENEMY_ID;
	}

	@Override
	public void update() {
		if (target == null) {
			look();
		} else {
			if (Util.LineOfSight(this, target) && (Util.dist(x, y, getTarget().getX(), getTarget().getY()) <= attackRange)) {
				if (attackDelay.over())
					attack();
			} else {
				chase();
			}
		}
		if (statistic.getCurrentHealth() <= 0) {
			death();
		}
	}

	protected void attack() {
		getTarget().damage(getAttackDamage());
		System.out.println("We're hit:" + getTarget().getCurrentHealth() + "/" + getTarget().getMaxHealth());
		restartAttackDelay();
	}

	protected void death() {
		remove();
	}

	protected void look() {
		ArrayList<GameObject> object = Main.sphereCollide(x, y, sightRange);

		for (GameObject go : object) {
			if (go.getType() == PLAYER_ID)
				setTarget((StatObject) go);

		}
	}

	protected void chase() {
		float speedX = (getTarget().getX() - x);
		float speedY = (getTarget().getY() - y);
		float maxSpeed = getStats().getSpeed() * DAMPING;

		if (speedX > maxSpeed)
			speedX = maxSpeed;
		if (speedX < -maxSpeed)
			speedX = -maxSpeed;
		if (speedY > maxSpeed)
			speedY = maxSpeed;
		if (speedY < -maxSpeed)
			speedY = -maxSpeed;

		x = x + speedX * Time.getDelta();
		y = y + speedY * Time.getDelta();
	}

	public void setTarget(StatObject go) {
		target = go;
	}

	public StatObject getTarget() {
		return target;
	}

	public Statistic getStats() {
		return statistic;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackRange(int range) {
		attackRange = range;
	}

	public void setAttackDelay(int time) {
		attackDelay = new Delay(time);
		attackDelay.end();
	}

	public void setAttackDamage(int amt) {
		attackDamage = amt;
	}

	public void restartAttackDelay() {
		attackDelay.start();
	}

	public void setSightRange(float dist) {
		sightRange = dist;
	}

	@Override
	protected void init(float x, float y, float r, float g, float b, float sX, float sY, int type) {
		this.x = x;
		this.y = y;
		this.type = ENEMY_ID;
		this.spr = new Sprite(r, g, b, sX, sY);
	}
}
