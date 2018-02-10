package prc.unc.BuildingGame.Misc;

import java.awt.*;

import prc.unc.BuildingGame.Component;

public class Light extends Rectangle{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int[] id = {-1, -1};
	public Light(Rectangle size, int[] id){
		setBounds(size);
		this.id = id;
	}
	public void render(Graphics g){
		if(id == Tile.light || id == Tile.light2 || id == Tile.light3){			
			g.drawImage(Tile.tileset_terrain, x-(int)Component.sX, y - (int)Component.sY , x + width - (int)Component.sX, y + height- (int)Component.sY ,id[0] * Tile.tileSize,id[1]*Tile.tileSize,id[0]*Tile.tileSize + Tile.tileSize,id[1] * Tile.tileSize + Tile.tileSize, null);
			}
	}
}
