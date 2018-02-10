package com.base.game.gameObject;


public class CookehMonster extends Enemy {
	public static final int SIZE = 32;

	public CookehMonster(float x, float y, int level) {
		super(level);
		this.init(x, y, 0.2f, 0.2f, 1f, SIZE, SIZE, 0);
		setAttackDelay(200);
	}
}
