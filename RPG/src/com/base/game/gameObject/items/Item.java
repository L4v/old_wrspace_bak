package com.base.game.gameObject.items;

import com.base.engine.GameObject;
import com.base.engine.Physics;
import com.base.engine.Sprite;
import com.base.game.gameObject.Player;

public class Item extends GameObject {
	protected String name;
	protected Player player;

	public Item(Player play) {
		this.player = play;
	}

	public void pickUp() {
		System.out.println("You picked up " + name + "!");
		player.addItem(this);
		remove();
	}

	protected void init(float x, float y, float r, float g, float b, float sX, float sY, String name) {
		this.x = x;
		this.y = y;
		this.type = ITEM_ID;
		this.spr = new Sprite(r, g, b, sX, sY);
		this.name = name;
	}

	@Override
	public void update() {
		if (Physics.checkCollision(this, player) != null)
			pickUp();
	}
	public String getName(){
		return name;
	}
	
}
