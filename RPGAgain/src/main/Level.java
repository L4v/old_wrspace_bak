package main;

import java.awt.*;


public class Level {
	public static final int WW = 170, WH = 170;

	public Block[][] block = new Block[WW][WH];

	public Level() {
		for (int x = 0; x < block.length; x++) {
			for (int y = 0; y < block[0].length; y++) {
				block[x][y] = new Block(new Rectangle(x * Tile.tileSize, y * Tile.tileSize, Tile.tileSize, Tile.tileSize), Tile.Air);
			}
		}
		createLevel();
	}

	public void createLevel() {
		for (int x = 0; x < block.length; x++) {
			for (int y = 0; y < block[0].length; y++) {
				if(x == 0 || y == 0 || y == block.length -1 || x == block.length - 1){block[x][y].id = Tile.Grass;}
			if(block[x][y].id == Tile.Air){
				block[x][y].id = Tile.Grass;
			}
			}
		}
	}
	

	public void tick() {
	}

	public void render(Graphics g) {
		for(int x = 0; x < block.length; x ++){
			for(int y = 0; y < block[0].length; y ++){
				block[x][y].render(g);
			}
		}
	}
}
