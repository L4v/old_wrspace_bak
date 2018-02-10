package com.threed.front.graphics;

import com.threed.front.Display;

public class Render{
	public final int width;
	public final int height;
	public final int[] pixels;

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void draw(Render render, int xOffset, int yOffset) {
		for (int y = 0; y < height; y++) {
			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= height) {
				continue;
			}
			for (int x = 0; x < width; x++) {
				int xPix = x + xOffset;
				if (xPix < 0 || xPix >= width) {
					continue;
				}
				int alpha = render.pixels[x + y * render.width];
				
				if (alpha > 0) {
					pixels[xPix + yPix * width] = alpha;
				}
			}
		}

	}
}
