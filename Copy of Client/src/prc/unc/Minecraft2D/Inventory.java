package prc.unc.Minecraft2D;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Inventory {
	public static Cell[] invbar = new Cell[Tile.invLength];
	public static boolean isOpen = false;
	public static boolean add = false;
	public static int selected = 1;
	public static boolean clicked = false;
	public static Cell[] workbench = new Cell[Tile.invLength];
	public static boolean holding = false;
	public static boolean open = false;
	public static Cell[] invBag = new Cell[Tile.invLength * Tile.invHeight];
	public static boolean isHolding = false;
	public static int[] holdingID = Tile.air;

	public Inventory() {
		for (int i = 0; i < invbar.length; i++) {
			invbar[i] = new Cell(new Rectangle((Component.pixel.width / 2) - ((Tile.invLength * (Tile.invCellSize + Tile.invCellSpace)) / 2) + (i * (Tile.invCellSize + Tile.invCellSpace)), Component.pixel.height - (Tile.invCellSize + Tile.invBorderSpace), Tile.invCellSize, Tile.invCellSize), Tile.air);
		}
		int x = 0, y = 0;
		for (int i = 0; i < invBag.length; i++) {

			invBag[i] = new Cell(new Rectangle((Component.pixel.width / 2) - ((Tile.invLength * (Tile.invCellSize + Tile.invCellSpace)) / 2) + (x * (Tile.invCellSize + Tile.invCellSpace)), Component.pixel.height - (Tile.invCellSize + Tile.invBorderSpace) - (Tile.invHeight * (Tile.invCellSize + Tile.invCellSpace)) + (y * (Tile.invCellSize + Tile.invCellSpace)), Tile.invCellSize, Tile.invCellSize), Tile.air);
			x++;
			if (x == Tile.invLength) {
				x = 0;
				y++;
			}
		}
		invbar[0].id = Tile.Grass;
		invbar[1].id = Tile.dirt;
		invbar[2].id = Tile.sanD;
		invbar[3].id = Tile.tree;
		invbar[4].id = Tile.WOOD;
		invbar[5].id = Tile.Leaf;
		invbar[6].id = Tile.Chest;
		invbar[7].id = Tile.Dynamite;
		invbar[8].id = Tile.power;

	}

	public void tick() {
		if (invBag[0].id == Tile.Dynamite && invBag[1].id == Tile.power) {
			invBag[8].id = Tile.Water;
			if (invBag[8].id == Tile.Water) {
				invBag[0].id = Tile.air;
				invBag[1].id = Tile.air;
			}
		}
		if (add == true) {
			invBag[3].id = Tile.dirt;
			add = false;
		}

	}

	public static void click(MouseEvent e) {
		if (e.getButton() == 1) {
			if (open) {
				for (int i = 0; i < workbench.length; i++) {
					if (workbench[i].contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
						if (workbench[i].id != Tile.air && !holding) {
							holdingID = workbench[i].id;
							workbench[i].id = Tile.air;
							holding = true;
						} else if (holding && workbench[i].id == Tile.air) {
							workbench[i].id = holdingID;
							holding = false;
						} else if (holding && workbench[i].id != Tile.air) {
							int[] con = workbench[i].id;
							workbench[i].id = holdingID;
							holdingID = con;

						}
					}
				}
			}
			if (isOpen) {
				for (int i = 0; i < invbar.length; i++) {
					if (invbar[i].contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
						if (invbar[i].id != Tile.air && !isHolding) {
							holdingID = invbar[i].id;
							invbar[i].id = Tile.air;
							isHolding = true;
						} else if (isHolding && invbar[i].id == Tile.air) {
							invbar[i].id = holdingID;
							isHolding = false;
						} else if (isHolding && invbar[i].id != Tile.air) {
							int[] con = invbar[i].id;
							invbar[i].id = holdingID;
							holdingID = con;

						}
					}
				}
				for (int i = 0; i < invBag.length; i++) {
					if (invBag[i].contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
						if (invBag[i].id != Tile.air && !isHolding) {
							holdingID = invBag[i].id;
							invBag[i].id = Tile.air;
							isHolding = true;
						} else if (isHolding && invBag[i].id == Tile.air) {
							invBag[i].id = holdingID;
							isHolding = false;
						} else if (isHolding && invBag[i].id != Tile.air) {
							int[] con = invBag[i].id;
							invBag[i].id = holdingID;
							holdingID = con;

						}
					}
				}
			}
		}
	}

	public void render(Graphics g) {
		if (open) {
			for (int i = 0; i < workbench.length; i++) {
				workbench[i].render(g, false);
			}
		}
		for (int i = 0; i < invbar.length; i++) {
			boolean isSelected = false;
			if (i == selected) {
				isSelected = true;
			}
			invbar[i].render(g, isSelected);
		}
		if (isOpen) {
			for (int i = 0; i < invBag.length; i++) {
				invBag[i].render(g, false);
			}
		}
		if (isHolding) {
			g.drawImage(Tile.tileset_terrain, (Component.mse.x / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.InvItemBorder, (Component.mse.y / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.InvItemBorder, (Component.mse.x / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.tileSize - Tile.InvItemBorder, (Component.mse.y / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.tileSize - Tile.InvItemBorder, holdingID[0] * Tile.tileSize, holdingID[1] * Tile.tileSize, holdingID[0] * Tile.tileSize + Tile.tileSize, holdingID[1] * Tile.tileSize + Tile.tileSize, null);
		}
		
		}
		

	}

