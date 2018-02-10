package prc.unc.Minecraft2D;

import java.awt.*;

public class Light extends Rectangle{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int[] idl = {-1, -1};
	public Light(Rectangle size, int[] idl){
		setBounds(size);
		this.idl = idl;
	}
	public void render(Graphics g){
		if(idl != Tile.air){			
			g.drawImage(Tile.tileset_terrain, x-(int)Component.sX, y - (int)Component.sY , x + width - (int)Component.sX, y + height- (int)Component.sY ,idl[0] * Tile.tileSize,idl[1]*Tile.tileSize,idl[0]*Tile.tileSize + Tile.tileSize,idl[1] * Tile.tileSize + Tile.tileSize, null);
			}
	}
}
