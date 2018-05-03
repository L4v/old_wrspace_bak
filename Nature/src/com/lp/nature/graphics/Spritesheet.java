package com.lp.nature.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Spritesheet {

	private String path;
	public final int SIZE;
	public int[] pixels;

	public static Spritesheet tiles = new Spritesheet("/res/textures/spritesheet2.png", 256);
	public static Spritesheet spawn_level = new Spritesheet("/res/textures/spawn_level.png", 48);
	public static Spritesheet projectile_mage = new Spritesheet("/res/textures/Projektili/projectiles.png", 48);
	//public static File test = new File ("/res/textures/spritesheet2.png");//"Spritesheet.class.getResourceAsStream(path)";

	public Spritesheet(String path, int size) {
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}

	private void load() {
		try {
			System.out.println(path);
			BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
