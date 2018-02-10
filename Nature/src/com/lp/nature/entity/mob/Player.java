package com.lp.nature.entity.mob;

import com.lp.nature.Game;
import com.lp.nature.entity.projectile.Mage_projectile;
import com.lp.nature.entity.projectile.Projectile;
import com.lp.nature.graphics.Screen;
import com.lp.nature.graphics.Sprite;
import com.lp.nature.input.Keyboard;
import com.lp.nature.input.Mouse;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	public static boolean shooting = false;
	public static double dtest;

	private int fireRate = 0;

	public Player(Keyboard input) {
		this.input = input;
		sprite = sprite.player_up;
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = sprite.player_up;
		fireRate = Mage_projectile.FIRE_RATE;
	}

	public void tick() {
		if (fireRate > 0){
			fireRate--;
			shooting = true;
		}
		if(shooting == true && fireRate == 0){
			shooting = false;
		}

		int xa = 0, ya = 0;
		if (anim < 7500) {
			anim++;
		} else {
			anim = 0;
		}
		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}

		shootingTick();
		clear();
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}
	}

	private void shootingTick() {
		if (Mouse.getButton() == 1 && fireRate == 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = Mage_projectile.FIRE_RATE;
		}

	}

	public void render(Screen screen) {
		int flip = 0;
		if (dir == 0) {
			sprite = sprite.player_up;
			if (walking == true) {
				if (anim % 20 > 10) {
					sprite = sprite.player_up_1;
				} else {
					sprite = sprite.player_up_2;
				}
			}
			if(shooting == true){
				sprite = sprite.player_fire_up;
			}
		}
		if (dir == 1) {
			sprite = sprite.player_side;
			if (walking == true) {
				if (anim % 20 > 10) {
					sprite = sprite.player_side_1;
				} else {
					sprite = sprite.player_side_2;
				}
			}
			if(shooting == true){
				sprite = sprite.player_fire_side;
			}
		}
		if (dir == 2) {
			sprite = sprite.player_down;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = sprite.player_down_1;
				} else {
					sprite = sprite.player_down_2;
				}
			}
		}
		if (dir == 3) {
			sprite = sprite.player_side;
			if (walking == true) {
				if (anim % 20 > 10) {
					sprite = sprite.player_side_1;
				} else {
					sprite = sprite.player_side_2;
				}
			}
			if(shooting == true){
				sprite = sprite.player_fire_side;
			}
			flip = 1;
		}
		screen.renderPlayer(x - 8, y - 16, sprite, flip);
	}

}
