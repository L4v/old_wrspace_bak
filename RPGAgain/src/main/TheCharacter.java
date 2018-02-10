package main;

import java.awt.*;

public class TheCharacter extends Rectangle {
	public static int movingSpeed = 1;
	public static boolean movingLeft = false;
	public static boolean movingRight = false;
	public static boolean movingDown = false;
	public static boolean movingUp = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TheCharacter(int width, int height) {
		setBounds((Component.pixel.width / 2) - (width / 2), (Component.pixel.width / 2) - (height / 2), width, height);
	}

	public void tick() {
		if (movingLeft == true && !collision(new Point(x - 2, y), new Point(x - 2, y + height + width))) {
			x -= movingSpeed;
			Component.sX -= movingSpeed;
		}
		if (movingRight == true) {
			x += movingSpeed;
			Component.sX += movingSpeed;
		}
		if (movingDown) {
			y += movingSpeed;
			Component.sY += movingSpeed;
		}
		if (movingUp == true) {
			y -= movingSpeed;
			Component.sY -= movingSpeed;
		}

	}

	public boolean collision(Point pt1, Point pt2) {
		for (int x = (int) (this.x / Tile.tileSize); x < (int) (this.x / Tile.tileSize) + 30; x++) {
			for (int y = (int) (this.y / Tile.tileSize); y < (int) (this.y / Tile.tileSize) + 30; y++) {
				if (x >= 0 && y >= 0 && x < Component.level.block.length && y < Component.level.block[0].length) {
					if (Component.level.block[x][y].id != Tile.Air)
						if (Component.level.block[x][y].contains(pt1) || Component.level.block[x][y].contains(pt2)) {
							return true;

						}
				}
			}

		}
		return false;
	}

	public void render(Graphics g) {
		g.drawImage(Tile.tileset_terrain, x - (int) Component.sX, y - (int) Component.sY, (x + width) - (int) Component.sX, (y + height) - (int) Component.sY, Tile.Char[0] * Tile.tileSize, Tile.Char[1] * Tile.tileSize, Tile.Char[0] * Tile.tileSize + width, Tile.Char[1] * Tile.tileSize + height, null);
	}
}
