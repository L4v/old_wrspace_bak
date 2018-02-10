package main;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
	public static int tileSize = 16;
	
	public static final int[] Air = {-1, -1};
	public static final int[] Grass = {0, 0};
	public static final int[] Char = {0, 15};
	
	public static BufferedImage tileset_terrain;
	
	
	public Tile(){
		try {
			Tile.tileset_terrain = ImageIO.read(new File("res/tileset_terrain.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
