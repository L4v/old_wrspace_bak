package com.base.game.gameObject;

public class Statistic {
	private float xp;
	private int level;
	private boolean levelable;
	private int health;
	public static final double LEVEL_CONST = 25 * Math.pow(3, (3.0 / 2.0));

	public Statistic(float xp, boolean levelable) {
		this.levelable = levelable;
		if(levelable){
		this.xp = xp;
		this.level = 1;
		}else{this.xp = -1; this.level = (int)xp;}
		health = getMaxHealth();
		
	}

	public float getSpeed() {
		return 4f;
	}

	public int getLevel() {
		if(!levelable){
			return level;
		}
		
		double x = xp + 105;

		double a = Math.sqrt(243 * (x * x) + 4050 * x + 17500);
		double c = (3 * x + 25) / 25;
		double d = Math.cbrt(a / LEVEL_CONST + c);

		return (int) (d - 1.0 / d * 3) - 1;
	}

	public int getMaxHealth() {
		return (getLevel() * 10);
	}

	public int getCurrentHealth() {
		int max = getMaxHealth();
		if (health > max)
			health = max;

		return health;
	}

	public float getStrength() {
		return getLevel() * 4f;
	}

	public float getMagic() {
		return getLevel() * 4;
	}

	public void addXp(float amt) {
		xp += amt;
	}
	public void damage(int amt){
		health -= amt;
	}
}
