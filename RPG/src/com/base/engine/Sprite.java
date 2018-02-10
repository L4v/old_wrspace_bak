package com.base.engine;

import static org.lwjgl.opengl.GL11.*;

public class Sprite {
	private float r;
	private float g;
	private float b;

	private float sX;
	private float sY;

	public Sprite(float r, float g, float b, float sX, float sY) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.sX = sX;
		this.sY = sY;
	}

	public void render() {
		glColor3f(r, g, b);
		glBegin(GL_QUADS);
		{

			glVertex2f(0, 0);
			glVertex2f(0, sY);
			glVertex2f(sX, sY);
			glVertex2f(sX, 0);
		}
		glEnd();
	}

	public float getsX() {
		return sX;
	}

	public float getsY() {
		return sY;
	}

	public void setsX(float sX) {
		this.sX = sX;
	}

	public void setsY(float sY) {
		this.sY = sY;
	}

}
