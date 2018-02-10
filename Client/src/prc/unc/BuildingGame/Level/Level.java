package prc.unc.BuildingGame.Level;

import java.awt.*;
import java.util.*;

import prc.unc.BuildingGame.Component;
import prc.unc.BuildingGame.Character.DoubleRectangle;
import prc.unc.BuildingGame.Misc.Inventory;
import prc.unc.BuildingGame.Misc.Tile;

public class Level extends DoubleRectangle {
	public double fallingSpeed = 2;
	public static boolean lol = false;
	public static boolean Test = false;
	public boolean breaking = false;
	public int Water = 0;
	public boolean Waterb = false;
	public boolean blockh = false;
	public int animationFrame = 0;
	public int animationTime = 3;
	public static boolean fire = false;
	public static int worldW = 126, worldH = 126;
	public static boolean rotation = false;
	public int fuse = 0;
	public int timer = 100;
	public static boolean background = false;
	public static boolean isFalling = false;
	public boolean fuqwertyfu = false;
	public int boomFrame = 0;
	public static int ax = 45;
	public static int ay = 45;
	public static int[] blocks;
	public int boomTime = 90;
	public boolean boom = false;
	public int bx;
	public static Rectangle range = new Rectangle((209 / Component.pixelSize), (120 / Component.pixelSize), (280 / Component.pixelSize), (280 / Component.pixelSize));

	public Block[][] block = new Block[worldW][worldH];

	public Level() {
		blocks = new int[64];
		for (int x = 0; x < block.length; x++) {
			for (int y = 0; y < block[0].length; y++) {
				block[x][y] = new Block(new Rectangle(x * Tile.tileSize, y * Tile.tileSize, Tile.tileSize, Tile.tileSize), Tile.air);

			}
		}

		generateLevel();
	}

	public void generateLevel() {
		// GeneRating MoUntaIns, dirt, etC
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (y > worldH / 4) {
					if (new Random().nextInt(100) > 20) {
						try {
							if (block[x - 1][y - 1].id == Tile.dirt) {
								block[x][y].id = Tile.dirt;
							}
						} catch (Exception e) {
						}
					}
					if (new Random().nextInt(100) > 30) {
						try {
							if (block[x + 1][y - 1].id == Tile.dirt) {
								block[x][y].id = Tile.dirt;
							}
						} catch (Exception e) {
						}
					}
					try {
						if (block[x][y - 1].id == Tile.dirt) {
							block[x][y].id = Tile.dirt;
						}
					} catch (Exception e) {
					}
					if (new Random().nextInt(100) < 2) {
						block[x][y].id = Tile.dirt;

					}

				}
			}
		}

		// ////////Grass
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (block[x][y].id == Tile.dirt && block[x][y - 1].id == Tile.air) {
					block[x][y].id = Tile.Grass;
				}
				if (block[x][y].id == Tile.Grass) {
					for (int i = 0; i < 68; i++) {
						block[x][y + 5 + i].id = Tile.Stone;
					}
					for (int i = 0; i < new Random().nextInt(5) + 2; i++) {
						if (new Random().nextInt(50) < 4) {
							if (block[x][y].id == Tile.Stone) {
								block[x - i][y].id = Tile.sanD;
							}
						}
					}
				}
			}
		}
		for (int y = 0; y < block.length; y++) {// //////////////////////////////////////////MAP
												// PROTECT air that is solid
			for (int x = 0; x < block[0].length; x++) {
				if (x == 0 || y == 0 || x == block[0].length - 1 || y == block[0].length - 1) {
					block[x][y].id = Tile.solidair;
				}
			}
		}// ////////////////////////////////////////////////
		for (int y = 0; y < block.length; y++) {// ////////////////////////////TREE
			for (int x = 0; x < block[0].length; x++) {
				try {
					if (block[x][y + 1].id == Tile.Grass && block[x][y].id == Tile.air) {
						if (new Random().nextInt(100) <= 2) {
							for (int i = 0; i < new Random().nextInt(5) + 3; i++) {
								block[x][y - i].id = Tile.tree;
							}
							if (block[x][y].id == Tile.tree && block[x][y - 1].id == Tile.tree) {
								block[x - 1][y - 2].id = Tile.Leaf;
								block[x - 1][y - 3].id = Tile.Leaf;
								block[x + 1][y - 2].id = Tile.Leaf;
								block[x + 1][y - 3].id = Tile.Leaf;
								block[x - 1][y - 4].id = Tile.Leaf;
								block[x + 1][y - 4].id = Tile.Leaf;
								block[x + 1][y - 5].id = Tile.Leaf;
								block[x - 1][y - 5].id = Tile.Leaf;
								block[x - 2][y - 2].id = Tile.Leaf;
								block[x - 2][y - 3].id = Tile.Leaf;
								block[x + 2][y - 2].id = Tile.Leaf;
								block[x + 2][y - 3].id = Tile.Leaf;
								block[x - 2][y - 4].id = Tile.Leaf;
								block[x + 2][y - 4].id = Tile.Leaf;
								block[x][y - 4].id = Tile.Leaf;
								block[x][y - 5].id = Tile.Leaf;
								block[x][y - 6].id = Tile.Leaf;
							}
						}
					}
				} catch (Exception e) {
				}

			}
		}
	}

	public void breaking(int camX, int camY, int renW, int renH) {
		for (int x = camX / Tile.tileSize; x < (camX / Tile.tileSize) + renW; x++) {
			for (int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++) {
				if (x >= 0 && y >= 0 && x < worldW && y < worldH) {
					if (block[x][y].id == Tile.dirt) {
						for (int o = 0; o < 25; o++) {
							if (o == 25) {
								block[x][y].id = Tile.air;
							}
							o = 0;
						}
					}
				}
			}
		}
	}

	public void blowUpTNT(int camX, int camY, int renW, int renH) {
		for (int x = 0; x < block.length; x++) {
			for (int y = 0; y < block[0].length; y++) {
				if (block[x][y].id == Tile.Dynamite) {
					if (fuse >= timer) {
						if (boom == false) {
							if (block[x - 1][y].id == Tile.power || block[x + 1][y].id == Tile.power || block[x][y - 1].id == Tile.power || block[x][y + 1].id == Tile.power && block[x][y].id == Tile.Dynamite) {
								boom = true;
							}
						} else if (boom == true) {
							boom = false;
						}

						fuse = 0;
					} else {
						fuse += 1;
					}

					if (boomFrame >= boomTime) {
						if (boom == true) {
							for (int i = 0; i < new Random().nextInt(3) + 2; i++) {
								block[x][y].id = Tile.air;
								if (block[x][y + i].id != Tile.air && block[x][y + i].id != Tile.Dynamite) {
									block[x][y + i].id = Tile.air;
								}
								if (block[x][y - i - i].id != Tile.air && block[x][y - i - i].id != Tile.Dynamite) {
									block[x][y - i - 1].id = Tile.air;
								}
								if (block[x][y + i + 1].id != Tile.air && block[x][y + i + 1].id != Tile.Dynamite) {
									block[x][y + i + 1].id = Tile.air;
								}
								if (block[x + i + 1][y].id != Tile.air && block[x + i + 1][y].id != Tile.Dynamite) {
									block[x + i + 1][y].id = Tile.air;
								}
								if (block[x + i][y + i].id != Tile.air && block[x + i][y + i].id != Tile.Dynamite) {
									block[x + i][y + i].id = Tile.air;
								}
								if (block[x + i][y - i].id != Tile.air && block[x + i][y - i].id != Tile.Dynamite) {
									block[x + i][y - i].id = Tile.air;
								}
								if (block[x - i - 1][y].id != Tile.air && block[x - i - 1][y].id != Tile.Dynamite) {
									block[x - i - 1][y].id = Tile.air;
								}
								if (block[x - i][y + i].id != Tile.air && block[x - i][y + i].id != Tile.Dynamite) {
									block[x - i][y + i].id = Tile.air;
								}
								if (block[x - i][y - i].id != Tile.air && block[x - i][y - i].id != Tile.Dynamite) {
									block[x - i][y - i].id = Tile.air;
								}
							}
						}
						boomFrame = 0;
					} else {
						boomFrame += 1;
					}
				}
			}
		}
	}

	public void building(int camX, int camY, int renW, int renH) {
		if (Component.isMouseLeft || Component.isMouseRight) {
			for (int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++) {
				for (int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++) {
					if (x >= 0 && y >= 0 && x < worldW && y < worldH) {
						if (block[x][y].contains(new Point((Component.mse.x / Component.pixelSize) + (int) Component.sX, (Component.mse.y / Component.pixelSize) + (int) Component.sY))) {
							int st[] = Inventory.invbar[Inventory.selected].id;

							if (Component.isMouseLeft && !Inventory.isOpen) {
								if (block[x][y].id != Tile.solidair && block[x][y].id != Tile.air && block[x][y].id != Tile.Dynamite && block[x][y].id != Tile.power) {

									bx++;
									if (bx == 50) {
										block[x][y].id = Tile.air;
									}
									if (bx > 50) {
										bx = 0;
									}

									if (block[x][y].id == Tile.dirt || block[x][y].id == Tile.Grass) {
										Inventory.add = true;
										if (Inventory.add == true) {
											Inventory.add = false;
										}
									}
								}
								if (block[x][y].id == Tile.power || block[x][y].id == Tile.Dynamite) {
									block[x][y].id = Tile.air;
								}

							} else if (Component.isMouseRight) {
								if (block[x][y].id == Tile.air && (block[x][y + 1].id != Tile.air || block[x + 1][y].id != Tile.air || block[x - 1][y].id != Tile.air || block[x][y - 1].id != Tile.air)) {

									if (st != Tile.air) {
										block[x][y].id = st;

										if (block[x][y + 1].id == Tile.Grass) {
											block[x][y + 1].id = Tile.dirt;
										}
									}
									if (st == Tile.WaterSource) {
										Waterb = true;
									}
								}
								if (block[x][y].id == Tile.Chest) {
								}
								if (block[x][y].id == Tile.power && block[x][y + 1].id == Tile.air) {
									block[x][y].id = Tile.air;
								}

							}
							if (!Component.isMouseLeft) {
								bx = 0;
							}
							break;
						}
					}
				}
			}
		}
	}

	public void tick(int camX, int camY, int renW, int renH) {
		// //////////Water
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (fuse < timer && boom) {
					if (block[x][y].id == Tile.Dynamite && block[x][y + 1].id == Tile.air) {
						block[x][y + 1].id = Tile.Dynamite;
						block[x][y].id = Tile.air;
						block[x][y + 2].id = Tile.Dynamite;
						block[x][y + 1].id = Tile.air;
					}
				}
			}
		}
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (block[x][y].id == Tile.Water) {
					if (block[x][y + 1].id == Tile.air) {
						block[x][y + 1].id = Tile.Water;
					}
				}
				if (block[x][y].id == Tile.sanD && block[x][y + 1].id == Tile.air) {
					if (isFalling == false) {
						isFalling = true;
					} else if (isFalling == true) {
						block[x][y + 1].id = Tile.sanD;
						block[x][y].id = Tile.air;
						for (int i = 0; i < 50; i++) {
							isFalling = false;
						}
					}
				}
			}
		}
		int o = worldW / 2;
		int p = worldH / 2;
		if (lol) {
			block[o][p].id = Tile.Chest;
		}

		if (Waterb) {
			for (int y = 0; y < block.length; y++) {
				for (int x = 0; x < block[0].length; x++) {
					for (int i = 0; i < 7; i++) {
						if (block[x][y].id == Tile.WaterSource && block[x - i][y].id == Tile.air) {
							Water += 1;
							if (Water == 4) {
								block[x - i][y].id = Tile.Water;
							}
							if (Water > 4) {
								Water = 0;
							}
						}
					}
				}
			}
		}

		if (!Inventory.isOpen) {
			building(camX, camY, renW, renH);
		}
		blowUpTNT(camX, camY, renW, renH);

	}

	public void render(Graphics g, int camX, int camY, int renW, int renH) {
		for (int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++) {
			for (int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++) {
				if (x >= 0 && y >= 0 && x < worldW && y < worldH) {
					block[x][y].render(g); //
					if (block[x][y].id != Tile.air && block[x][y].id != Tile.solidair && !Inventory.isOpen && !Inventory.open && block[x][y].id != Tile.power) {
						if (block[x][y].contains(new Point((Component.mse.x / Component.pixelSize) + (int) Component.sX, (Component.mse.y / Component.pixelSize) + (int) Component.sY))) {
							g.setColor(new Color(250, 250, 250, 30));
							g.fillRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width - 1, block[x][y].height - 1);
							g.setColor(new Color(0, 0, 0));
							g.drawRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width - 1, block[x][y].height - 1);

							if (Component.isMouseLeft) {
								g.setColor(new Color(5 * bx, 0, 0, 255));
								g.drawRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width - 1, block[x][y].height - 1);
								g.setColor(new Color(0, 0, 0, 255));

							}
						}
					}
				}
			}
		}
	}
}
