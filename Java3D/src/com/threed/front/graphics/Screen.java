package com.threed.front.graphics;


import com.threed.front.Display;
import com.threed.front.Game;

public class Screen extends Render {

	private Render3D render;
	public Screen(int width, int height) {
		super(width, height);
		render = new Render3D(width, height);
	}

	public void render(Game game) {
		for (int i = 0; i < Display.WIDTH * Display.HEIGHT; i++) {
			pixels[i] = 0;
		}
		render.floor(game);
		render.renderDistanceLimiter();
		draw(render, 0, 0);
	}
}
