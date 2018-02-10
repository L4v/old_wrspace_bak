package com.lp.nature.level.tile.spawn_level;

import com.lp.nature.graphics.Screen;
import com.lp.nature.graphics.Sprite;
import com.lp.nature.level.tile.Tile;

public class SpawnBushTile extends Tile {

	public SpawnBushTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid(){
		return true;
	}

}
