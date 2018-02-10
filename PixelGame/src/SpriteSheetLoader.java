import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class SpriteSheetLoader {
	BufferedImage sheet;
	public int[] sheetPixels;
	public int[] pixels;
	int x, y, sheetWidth;

	public SpriteSheetLoader(BufferedImage sheet) {
		BufferedImage image = new BufferedImage(sheet.getWidth(), sheet.getHeight(), BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(sheet, 0, 0, null);

		sheetPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		sheetWidth = sheet.getWidth();
	}

	public void grabTile(int tile, int width, int height) {
		pixels = new int[width * height];

		int xTile = tile % 16;
		int yTile = tile / 16;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + (y * width)] = sheetPixels[x+(xTile * width)+(y+(yTile * height)) * sheetWidth];
			}
		}
	}
}
