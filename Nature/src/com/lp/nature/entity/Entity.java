package com.lp.nature.entity;

import java.util.Random;

import com.lp.nature.graphics.Screen;
import com.lp.nature.level.Level;

public abstract class Entity {
	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random RANDOM = new Random();

	public void tick() {

	}

	public void render(Screen screen) {

	}

	public void remove() {
		// Remove from level
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void init(Level level) {
		this.level = level;
	}
}
