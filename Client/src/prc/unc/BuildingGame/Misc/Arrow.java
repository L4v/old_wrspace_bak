package prc.unc.BuildingGame.Misc;

import java.awt.*;



public class Arrow{
	static boolean see = false;
	int x;
	int y;
	public Rectangle projectile = new Rectangle(100, 10, 10, 5);
	Image screen;
	public Arrow(int startX, int startY){
		x = startX;
		y = startY;
	}
	public void render(Graphics g){
		if(see){
			g.setColor(new Color(0, 255, 255, 250));
			g.drawRect(x, y, projectile.width, projectile.height);
			}
	}
	public void fire(){
		if(see && x < 720){
		x = x + 1;
		}else{
			x = 100;
		}
	}
}
