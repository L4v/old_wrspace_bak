package com.lp.nature.level.tile;

import com.lp.nature.graphics.Screen;
import com.lp.nature.graphics.Sprite;

public class FlowerTile extends Tile {
	public FlowerTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
