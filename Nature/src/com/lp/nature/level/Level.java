package com.lp.nature.level;

import java.util.ArrayList;
import java.util.List;

import com.lp.nature.entity.Entity;
import com.lp.nature.entity.particle.Particle;
import com.lp.nature.entity.projectile.Projectile;
import com.lp.nature.entity.spawner.Spawner;
import com.lp.nature.graphics.Screen;
import com.lp.nature.level.tile.Tile;

public class Level {
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	public static Level spawn = new Level("/levels/spawn.png");

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();

	}

	public void add(Entity e) {
		e.init(this);

		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {

			projectiles.add((Projectile) e);
		} else {
			entities.add(e);
		}

	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).tick();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).tick();
		}
		remove();
	}

	private void time() {

	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);

			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == Tile.col_spawn_wood_floor) return Tile.spawn_wood_floor;
		if (tiles[x + y * width] == Tile.col_spawn_stone_brick) return Tile.spawn_stone_brick;
		if (tiles[x + y * width] == Tile.col_spawn_grass) return Tile.spawn_grass;
		if (tiles[x + y * width] == Tile.col_spawn_bush) return Tile.spawn_bush;
		if (tiles[x + y * width] == Tile.col_spawn_fresh_water) return Tile.spawn_fresh_water;
		if (tiles[x + y * width] == Tile.col_spawn_sp) return Tile.spawn_sp;
		return Tile.voidTile;
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile((int) xt, (int) yt).solid()) solid = true;
		}
		return solid;
	}

}
