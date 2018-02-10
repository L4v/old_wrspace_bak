package com.lp.nature.graphics;

public class Sprite {
	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private Spritesheet sheet;

	private int width, height;

	public static Sprite grass = new Sprite(16, 0, 1, Spritesheet.tiles);
	public static Sprite flower = new Sprite(16, 1, 0, Spritesheet.tiles);
	public static Sprite stone_grass = new Sprite(16, 2, 0, Spritesheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x1b87e0);

	public static Sprite spawn_grass = new Sprite(16, 0, 0, Spritesheet.spawn_level);
	public static Sprite spawn_stone_brick = new Sprite(16, 1, 0, Spritesheet.spawn_level);
	public static Sprite spawn_brick = new Sprite(16, 1, 1, Spritesheet.spawn_level);
	public static Sprite spawn_wood_floor = new Sprite(16, 2, 0, Spritesheet.spawn_level);
	public static Sprite spawn_bush = new Sprite(16, 0, 1, Spritesheet.spawn_level);
	public static Sprite spawn_fresh_water = new Sprite(16, 0, 2, Spritesheet.spawn_level);
	public static Sprite spawn_sp = new Sprite(16, 2, 1, Spritesheet.spawn_level);

	public static Sprite player_up = new Sprite(32, 0, 5, Spritesheet.tiles);
	public static Sprite player_side = new Sprite(32, 1, 5, Spritesheet.tiles);
	public static Sprite player_down = new Sprite(32, 2, 5, Spritesheet.tiles);

	public static Sprite player_up_1 = new Sprite(32, 0, 6, Spritesheet.tiles);
	public static Sprite player_up_2 = new Sprite(32, 0, 7, Spritesheet.tiles);

	public static Sprite player_side_1 = new Sprite(32, 1, 6, Spritesheet.tiles);
	public static Sprite player_side_2 = new Sprite(32, 1, 7, Spritesheet.tiles);

	public static Sprite player_down_1 = new Sprite(32, 2, 6, Spritesheet.tiles);
	public static Sprite player_down_2 = new Sprite(32, 2, 7, Spritesheet.tiles);

	public static Sprite player_fire_up = new Sprite(32, 4, 5, Spritesheet.tiles);
	public static Sprite player_fire_side = new Sprite(32, 4, 6, Spritesheet.tiles);

	public static Sprite mage_projectile_1 = new Sprite(16, 0, 0, Spritesheet.projectile_mage);
	
	public static Sprite particle_normal = new Sprite(3, 0xff4444);

	public Sprite(int size, int x, int y, Spritesheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	public Sprite(int width, int height, int col) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(col);
	}

	public Sprite(int size, int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	public void setColor(int color) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
}
