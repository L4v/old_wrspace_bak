package prc.unc.Minecraft2D;

import java.awt.*;

public class Block2 extends Rectangle{
	private static final long serialVersionUID = 1L;
	public int[] id = {-1, -1};
	String k;
	

	
	public Block2(Rectangle size, int[] id){
		setBounds(size);
		this.id = id;
	}
	public void render(Graphics g){
		if(id != Tile.air){			
		g.drawImage(Tile.tileset_terrain, x-(int)Component.sX, y - (int)Component.sY , x + width - (int)Component.sX, y + height- (int)Component.sY ,id[0] * Tile.tileSize,id[1]*Tile.tileSize,id[0]*Tile.tileSize + Tile.tileSize,id[1] * Tile.tileSize + Tile.tileSize, null);
		}
	}
}
