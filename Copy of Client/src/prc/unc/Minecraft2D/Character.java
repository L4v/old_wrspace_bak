package prc.unc.Minecraft2D;

import java.awt.Graphics;
import java.awt.Point;

public class Character extends DoubleRectangle {
	public float fallingSpeed = 2;
	public double movingSpeed = 1.0;
	public static boolean living = true;
	public static int animation = 0;
	public static int animationFrame = 0, animationTime = 23;
	public int Health = 100;
	public boolean alive = true;
	public boolean isFalling = false;
	public static int jumpingheight = Tile.tileSize, jumpingcount = 0;
	public static boolean isJumping = false;
	public double jumpingSpeed = 1.5;
	public double damage = 0.0;

	public Character(int width, int height) {
		setBounds((Component.pixel.width / 2) - (width / 2), (Component.pixel.height / 2) - (height / 2), width, height);
	}

	public void tick() {
		if (Health <= 0) {
			alive = false;
		}
		if (isFalling) {
			damage += 0.1;
		} else if (isFalling == false) {
			if (damage > 16) {
				Health -= (int) damage;
			}
			damage = 0;

		}
		if (IsCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height)))) {
			isFalling = false;
		}
		if (!isJumping && !IsCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height)))) {
			y += fallingSpeed;
			Component.sY += fallingSpeed;
			isFalling = true;
		} else {
			if (Component.isJumping && !Inventory.isOpen) {
				isJumping = true;
				isFalling = false;
			}
		}
		if (Component.isMoving && !Inventory.isOpen) {
			boolean canMove = false;

			if (Component.dir == movingSpeed) {
				canMove = IsCollidingWithBlock(new Point((int) (x + width), (int) y), new Point((int) (x + width), (int) (y + (height - 3))));

			} else if (Component.dir == -movingSpeed) {
				canMove = IsCollidingWithBlock(new Point((int) x - 1, (int) y), new Point((int) x - 1, (int) (y + (height - 3))));
			}
			if (animationFrame >= animationTime) {
				if (animation > 1) {
					animation = 1;
				} else {
					animation += 1;
				}
				animationFrame = 0;
				// //////////
			} else {
				animationFrame += 1;
			}// ////////////Pomera se <===
			if (!canMove) {

				x += Component.dir;
				Component.sX += Component.dir;
			}
		} else {
			animation = 0;
		}
		if (isJumping) {
			if (!IsCollidingWithBlock(new Point((int) (x + 2), (int) y), new Point((int) (x + width - 2), (int) y))) {

				if (jumpingcount >= jumpingheight) {

					isJumping = false;

					jumpingcount = 0;
				} else {
					y -= jumpingSpeed;
					Component.sY -= jumpingSpeed;
					jumpingcount += 1;
				}
			} else {
				isJumping = false;
				jumpingcount = 0;

			}
		}
	}
	public boolean IsCollidingWithBlock(Point pt1, Point pt2) {
		for (int x = (int) (this.x / Tile.tileSize); x < (int) (this.x / Tile.tileSize + 3); x++) {
			for (int y = (int) (this.y / Tile.tileSize); y < (int) (this.y / Tile.tileSize + 3); y++) {
				if (x >= 0 && y >= 0 && x < Component.level.block.length && y < Component.level.block[0].length)

					if (Component.level.block[x][y].id != Tile.air && Component.level.block[x][y].id != Tile.power) {
						if (Component.level.block[x][y].contains(pt1) || Component.level.block[x][y].contains(pt2)) {

							return true;
						}
					}
			}
		}
		return false;
	}

	public void render(Graphics g) {

		if (living) {

			if (Component.dir == movingSpeed) {
				g.drawImage(Tile.tileset_terrain, (int) x - (int) Component.sX, (int) y - (int) Component.sY, (int) x + (int) width - (int) Component.sX, (int) y + (int) height - (int) Component.sY/**/, (Tile.character[0] * Tile.tileSize) + (Tile.tileSize * animation), (Tile.character[1] * Tile.tileSize), (Tile.character[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) width, Tile.character[1] * Tile.tileSize + (int) height, null);
			} else {
				g.drawImage(Tile.tileset_terrain, (int) x - (int) Component.sX, (int) y - (int) Component.sY, (int) x + (int) width - (int) Component.sX, (int) y + (int) height - (int) Component.sY/**/, (Tile.character[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) width, (Tile.character[1] * Tile.tileSize), (Tile.character[0] * Tile.tileSize) + (Tile.tileSize * animation), Tile.character[1] * Tile.tileSize + (int) height, null);

			}
		}
	}
}
