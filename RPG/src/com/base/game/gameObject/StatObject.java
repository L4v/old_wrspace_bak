package com.base.game.gameObject;

import com.base.engine.GameObject;

public class StatObject extends GameObject {
	protected Statistic statistic;

	public void damage(int amt) {
		statistic.damage(amt);
	}

	public float getSpeed() {
		return statistic.getSpeed();
	}

	public int getLevel() {
		return statistic.getLevel();
	}

	public int getMaxHealth() {
		return statistic.getMaxHealth();
	}

	public int getCurrentHealth() {

		return statistic.getCurrentHealth();
	}

	public float getStrength() {
		return statistic.getStrength();
	}

	public float getMagic() {
		return statistic.getMagic();
	}

}
