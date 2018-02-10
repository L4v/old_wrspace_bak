package prc.unc.Minecraft2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Arrow extends DoubleRectangle {

	public float movingS = 1;
	public float fallingS = 1;
	public static boolean moving = false;

	public Arrow() {
		setBounds((Component.pixel.width / 4) - (width / 4), (Component.pixel.height / 4) - (height / 4), width, height);
	}

	public void tick() {
		if(moving && !Colliding(new Point((int) x + 2, (int) (y +  height)),new Point ((int) (x + width - 2), (int) (y +  height)))){
			x += movingS;
			movingS += 0.001;
		}
	}

	public void render(Graphics d) {
		if(moving == true){
		d.setColor(new Color(0, 0, 0));
		d.fillRect(45, 43, 4, 4);
		}
	}
	public boolean Colliding(Point pt1, Point pt2){
		for(int x=(int)(this.x/Tile.tileSize); x<(int)(this.x/Tile.tileSize + 3);x++){
			for(int y=(int)(this.y/Tile.tileSize); y<(int)(this.y/Tile.tileSize + 3);y++){
				if(x >= 0 && y >= 0 && x < Component.level.block.length && y< Component.level.block[0].length)
				if(Component.level.block[x][y].id != Tile.air &&Component.level.block[x][y].id != Tile.power){
				if(Component.level.block[x][y].contains(pt1)||Component.level.block[x][y].contains(pt2)){
				
					return true;
					}
				}
			}
		}
		return false;
	}
	}

