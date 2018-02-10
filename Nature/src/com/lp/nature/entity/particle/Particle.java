package com.lp.nature.entity.particle;

import com.lp.nature.entity.Entity;
import com.lp.nature.graphics.Screen;
import com.lp.nature.graphics.Sprite;

public class Particle extends Entity {
	private Sprite sprite;

	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double za, xa, ya;

	public Particle(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (RANDOM.nextInt(40) - 10);
		sprite = Sprite.particle_normal;
		//
		this.xa = RANDOM.nextGaussian() + 1.8;
		if (this.xa < 0) xa = 1.0;
		this.ya = RANDOM.nextGaussian();
		this.zz = RANDOM.nextFloat() + 2.0;
	}

	public void tick() {
		time++;
		if (time >= 7800) time = 0;
		if (time > life) remove();
		za -= 0.1;

		if (zz < 0) {
			zz = 0;
			za *= -0.55;
			xa *= 0.4;
			ya *= 0.4;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx - 12, (int) yy - (int) zz, sprite, true);
	}

}
