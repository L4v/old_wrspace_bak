package prc.unc.BuildingGame;

import java.awt.*;



public class Death {
	public void render(Graphics g){
		if(!Component.character.alive){
		g.setColor(new Color(0, 0, 0, 250));
		g.fillRect(0, 0, 350, 270);
		g.setColor(new Color(250, 250, 250, 250));
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.drawString("Press ESC to exit the game", 55, 75);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(new Color(250, 0, 0));
		g.drawString("You died", 75, 50);
		}
	}
}
