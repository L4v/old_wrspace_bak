package prc.unc.Minecraft2D;

import java.awt.*;

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
