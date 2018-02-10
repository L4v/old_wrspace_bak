package prc.unc.BuildingGame.Misc;

import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Tile {
	public static  int tileSize =10;
	public static final int[] air = {-1, -1};
	public static final int[] dirt = {0, 0};
	public static final int[] Grass = {1, 0};	
	public static final int[] sanD = {2, 0};
	public static final int[] WOOD = {3, 0};
	public static final int[] Stone = {7, 0};
	public static final int[] tree = {5, 0};
	public static final int[] WaterSource = {9, 0};
	public static final int[] Water = {8, 0};
	public static final int[] solidair =  {4,0};
	public static final int[] Chest = {0, 1};
	public static final int[] power = {2, 1};
	public static final int[] Dynamite = {1, 1};
	public static final int[] Leaf = {6, 0};
	public static final int[] light3 = {0, 2};
	public static final int[] light = {2,2};
	public static final int[] light2 = {1, 2};
	

	public static int[] character = {0, 18};
	public static int[] mobZombie = {0,16};
	
	public static int maxMobs = 8;
	
	public static int InvItemBorder = 2;
	public static int invHeight = 3;
	public static int invLength= 9;
	public static int invCellSpace = 0;
	public static int invBorderSpace = 4;
	public static int invCellSize = 17;
	
	public static BufferedImage tile_stests;
	public static BufferedImage tile_select;
	public static BufferedImage tile_cell;
	public static BufferedImage tileset_terrain;
	public static BufferedImage star1;
	public static BufferedImage Start2;
	public static BufferedImage Light;
	public static BufferedImage mmenu;
	
	
	
	
	
	public Tile(){
		
		
		
		try{
			Tile.mmenu = ImageIO.read(new File("res/Textures/Menu.png"));
			Tile.Light = ImageIO.read(new File("res/Textures/Light.png"));
			Tile.Start2 = ImageIO.read(new File("res/Textures/Start.png"));
			Tile.tileset_terrain = ImageIO.read(new File("res/Textures/tileset_terrain2small.png"));
			Tile.tile_cell = ImageIO.read(new File("res/Textures/tile_cell.png"));
			Tile.tile_select = ImageIO.read(new File("res/Textures/tile_select.png"));
			Tile.star1 = ImageIO.read(new File("res/Textures/star.png"));
			Tile.tile_stests = ImageIO.read(new File("res/Textures/character.gif"));
		}catch(Exception e){}
	}
}
