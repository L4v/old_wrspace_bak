package com.base.engine;

public class Frame {

	private int length;
	private int numDisplayed;
	private Sprite spr;

	public Frame(Sprite spr, int length) {
		this.spr = spr;
		this.length = length;
		numDisplayed = 0;
	}

	public boolean render() {
		spr.render();
		numDisplayed++;

		if (numDisplayed >= length) {
			numDisplayed = 0;
			return true;
		}

		return false;
	}
}
