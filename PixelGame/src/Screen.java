public class Screen {
	private SpriteSheetLoader loader;
	private int w, h;
	int xOffset = 0, yOffset = 0;

	public Screen(int w, int h, SpriteSheetLoader newloader) {
		this.loader = newloader;
		this.w = w;
		this.h = h;
	}

	public void render(int tile, int xPos, int yPos, int width, int height) {
		loader.grabTile(tile, width, height);
	}
}
