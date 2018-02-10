package minecraft2d;

public enum BlockType {
	STONE("res/Stone.png"), AIR("res/air.png"), GRASS("res/Grass.png"), DIRT("res/DIRT.png");
	public final String location;
	 BlockType(String location){
		this.location = location;
	}
}
