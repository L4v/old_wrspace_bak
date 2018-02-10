package com.lp.nature.entity.spawner;

import com.lp.nature.entity.Entity;
import com.lp.nature.entity.particle.Particle;
import com.lp.nature.level.Level;

public class Spawner extends Entity {


	public enum Type {
		MOB, PARTICLE;
	}

	private Type type;

	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;		
	}
}
