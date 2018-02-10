package prc.unc.BuildingGame.Mob;

import java.awt.*;

import prc.unc.BuildingGame.Misc.Tile;

public class Zombie extends Mob{
	public Zombie(int x , int y,int width ,int height){
		super(x, y, width, height, Tile.mobZombie);
	}
	public void tick(){
		super.tick();
	}
	public void render(Graphics g){
		super.render(g);
	}
}
