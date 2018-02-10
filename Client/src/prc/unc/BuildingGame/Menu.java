package prc.unc.BuildingGame;

import java.awt.*;

import prc.unc.BuildingGame.Misc.Tile;

public class Menu {
	public int sbwidth = 170;
	public int sbheight = 37;
	boolean Contain = false;

	Rectangle start = new Rectangle(88, 85, sbwidth / Component.pixelSize, sbheight / Component.pixelSize);

	public Menu() {
	}

	public void tick() {
		if (Component.isMouseLeft && Contain) {
			Component.Menu = false;
		}
	}

	public void render(Graphics g) {
		g.drawImage(Tile.mmenu, 0, 0, null);
		g.setColor(new Color(0, 255, 0, 255));
		g.fill3DRect(start.x, start.y, start.width, start.height, true);
		g.setColor(new Color(0, 0, 0, 50));
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.drawString("Start game", (int) start.x + 3, (int) start.y + 9);
		g.setColor(new Color(250, 250, 250));
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.drawString("Start game", (int) start.x + 2, (int) start.y + 8);

		if (start.contains((int) (Component.mse.x / Component.pixelSize), (int) (Component.mse.y / Component.pixelSize))) {
			g.setColor(new Color(0, 0, 0));
			g.drawRect(start.x - 1, start.y - 1, start.width + 1, start.height + 1);
			Contain = true;
		}

		// /g.drawString("To continue to the game:", Start.x - 40, Start.y -
		// 4);

	}
}
