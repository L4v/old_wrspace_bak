package a.level.tiles;

import a.gfx.Colours;
import a.gfx.Screen;
import a.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colours.get(0, 0, -1, -1));
	public static final Tile STONE = new BasicSolidTile(1, 1, 0,Colours.get(-1, 333, -1, -1));
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1));
	
	protected byte id;
	protected boolean sollid;
	protected boolean emitter;
	
	public Tile(int id, boolean isSollid, boolean isEmiter){
		this.id = (byte)id;
		if(tiles[id] != null)throw new RuntimeException("Duplicate Tile id on "+ id);
		this.sollid = isSollid;
		this.emitter = isEmiter;
		tiles[id] = this;
	}
	
	public byte getId(){
		return id;
	}
	
	public boolean isSollid(){
		return sollid;
	}
	
	public boolean isEmiter(){
		return emitter;
	}
	
	public abstract void render(Screen screen, Level level, int x, int y);
	
}
