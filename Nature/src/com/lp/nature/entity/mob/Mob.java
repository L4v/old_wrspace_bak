package com.lp.nature.entity.mob;

import com.lp.nature.entity.Entity;
import com.lp.nature.entity.particle.Particle;
import com.lp.nature.entity.projectile.Mage_projectile;
import com.lp.nature.entity.projectile.Projectile;
import com.lp.nature.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;

		if (!collision(xa, ya)) {
			x += xa;
			y += ya;
		}
	}

	public void tick() {

	}

	protected void shoot(int x, int y, double dir) {
		// dir *= 180 / Math.PI;
		Projectile p = new Mage_projectile(x, y, dir);
		level.add(p);
		// double sir = dir *= 180/ Math.PI;
	}

	public void render() {

	}

	private boolean collision(int xa, int ya) {//
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = ((x + xa) + c % 2 * 14 + 1) / 16;
			int yt = ((y + ya) + c / 2 * 12 + 2) / 16;
			if (level.getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}
}