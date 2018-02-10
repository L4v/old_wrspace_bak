package com.lp.nature.level.tile;

import com.lp.nature.graphics.Screen;
import com.lp.nature.graphics.Sprite;
import com.lp.nature.level.tile.spawn_level.SpawnBushTile;
import com.lp.nature.level.tile.spawn_level.SpawnFloorTile;
import com.lp.nature.level.tile.spawn_level.SpawnGrassTile;
import com.lp.nature.level.tile.spawn_level.SpawnWallTile;
import com.lp.nature.level.tile.spawn_level.SpawnWaterTile;

public class Tile {
	public int x, y;
	public Sprite sprite;

	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile stone_grass = new RockTile(Sprite.stone_grass);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);

	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_wood_floor = new SpawnFloorTile(Sprite.spawn_wood_floor);
	public static Tile spawn_bush = new SpawnBushTile(Sprite.spawn_bush);
	public static Tile spawn_stone_brick = new SpawnWallTile(Sprite.spawn_stone_brick);
	public static Tile spawn_brick = new SpawnWallTile(Sprite.spawn_brick);
	public static Tile spawn_fresh_water = new SpawnWaterTile(Sprite.spawn_fresh_water);
	public static Tile spawn_sp = new SpawnFloorTile(Sprite.spawn_sp);

	public static final int col_spawn_grass = 0xff00ff00;
	public static final int col_spawn_bush = 0xff00A800;
	public static final int col_spawn_wood_floor = 0xff936030;
	public static final int col_spawn_fresh_water = 0xff0000ff;
	public static final int col_spawn_stone_brick = 0xffbababa;
	public static final int col_spawn_sp = 0xFFFFFFFF;

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {

	}

	public boolean solid() {
		return false;
	}

}
