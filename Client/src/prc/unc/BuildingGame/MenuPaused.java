package prc.unc.BuildingGame;

import java.awt.*;

public class MenuPaused {
	public MenuPaused(){
		
	}
	public void render(Graphics g){
		g.setColor(new Color(0, 0, 0, 250));
		g.fillRect(0, 0, 350, 270);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.setColor(new Color(0, 250, 0, 250));
		g.drawString("Game Paused", 86, 75);
		g.setColor(new Color(250, 250, 250, 250));
		g.drawString("ESC to resume", 84, 85);
		g.drawString("Q to quit", 98, 95);
	}
}
