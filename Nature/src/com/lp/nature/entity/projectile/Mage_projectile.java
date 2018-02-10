package com.lp.nature.entity.projectile;

import com.lp.nature.entity.spawner.ParticleSpawner;
import com.lp.nature.entity.spawner.Spawner;
import com.lp.nature.graphics.Screen;
import com.lp.nature.graphics.Sprite;

public class Mage_projectile extends Projectile {

	public static final int FIRE_RATE = 10; // HIGH - SLOW // LOW - FAST

	public Mage_projectile(int x, int y, double dir) {
		super(x, y, dir);
		range = random.nextInt(100) + 150;
		dmg = 20;
		sprite = Sprite.mage_projectile_1;
		speed = 2;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);

	}

	public void tick() {
		move();
	}

	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range || level.tileCollision((int) (x + nx), (int) (y + ny), 7, -4, 5)) {
			level.add(new ParticleSpawner((int) x, (int) y, 48, 80, level));

			remove();
		}
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - 12, (int) y - 3, this);
	}

	public double distance() {
		double dist = 0;

		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));

		return dist;
	}

}
