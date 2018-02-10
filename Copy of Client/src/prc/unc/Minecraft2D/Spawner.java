package prc.unc.Minecraft2D;

import java.util.*;


public class Spawner implements Runnable{

	public static boolean Removemob = false;
	
	public boolean isRunning = true;
	
	public Spawner(){
		new Thread(this).start();
		
	}
	public void removeMob(Mob mob){
		if(Removemob == true){
		Component.mob.remove(mob);
		}
	}
	public void spawnMob(Mob mob){
		Component.mob.add(mob);
	}
	public void run(){
	while(isRunning){
		if(Component.mob.toArray().length < Tile.maxMobs && Sky.time == Sky.night && Tile.maxMobs != 8){
		spawnMob(new Zombie(new Random().nextInt((Level.worldW - 2 ) * Tile.tileSize) + Tile.tileSize + 30, 50, Tile.tileSize, Tile.tileSize * 2));
		Tile.maxMobs ++;
		}
		try{
			Thread.sleep(new Random().nextInt(7000)+7000);
		}catch(Exception e){}
	}	
	}
}
