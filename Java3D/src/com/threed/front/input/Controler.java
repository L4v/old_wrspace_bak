package com.threed.front.input;

public class Controler {

	public double x, z, rotation, xa, za, rotationa;

	public void tick(boolean forward, boolean backward, boolean left, boolean right, boolean turnLeft, boolean turnRight) {
		double rotationSpeed = 0.1;
		double walkSpeed = 0.4;
		double xMove = 0;
		double zMove = 0;
		if (forward) {
			zMove++;
		}
		if (backward) {
			zMove--;
		}
		if (left) {
			xMove--;
		}
		if (right) {
			xMove++;
		}
		if (turnLeft) {
			rotationa -= rotationSpeed;
		}
		if (turnRight) {
			rotationa += rotationSpeed;
		}
		xa += (xMove * Math.cos(rotationa)+ zMove * Math.sin(rotationa)* walkSpeed);
		za += (zMove * Math.cos(rotationa)- xMove * Math.sin(rotationa)* walkSpeed);
		x += xa;
		z += za;
		xa *= 0.1;
		za*= 0.1;
		rotation += rotationa;
		rotationa *= 0.5;

	}
}
