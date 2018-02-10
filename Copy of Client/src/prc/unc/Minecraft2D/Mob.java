package prc.unc.Minecraft2D;

import java.awt.*;
import java.util.Random;

public class Mob extends MobRectangle {
	public double movingSpeed = 0.4;
	public int[] id;
	public boolean isMoving = false;
	public boolean isFalling = false;
	
	
	public int animation = 0;
	public int animationFrame = 0, animationTime = 25;	
	public int jumpingheight =  10 , jumpingcount = 0;
	public double jumpingSpeed = 1;
	
	
	public boolean isRunning =  false;
	
	
	public double dir = movingSpeed;
	public boolean isJumping = false;
	public double fallingSpeed = 1 ;
	public Mob(int x, int y, int width, int height, int[] id){
		setBounds(x, y, width, height);
		this.id = id;
		
		
		
	}
	public void tick(){
		if(!isJumping && !IsCollidingWithBlock(new Point((int) x + 2, (int) (y +  height)),new Point ((int) (x + width - 2), (int) (y +  height)))){
			y += fallingSpeed ;
			isFalling = true;
		}else {
			isFalling = false;
			
			if (new Random().nextInt(100 )<1){
				isMoving = true;
				if(new Random().nextInt(100)<50){
					dir = -movingSpeed;
				}else{
					dir = movingSpeed;
				}
			}
		}
		
		if (isMoving){
			boolean canMove = false;
			
			if(dir == movingSpeed){
				canMove = IsCollidingWithBlock(new Point((int)(x + width), (int)y), new Point((int)(x +  width), (int)(y + (height - 3))));
			}else if(dir == -movingSpeed){
				canMove = IsCollidingWithBlock(new Point((int)x - 1, (int)y), new Point((int)x - 1, (int)(y + (height-3))));
			}
			if(canMove && !isFalling ){
				isJumping = true;
			}
			if(animationFrame >= animationTime){
				if(animation > 1){
					animation = 1;
				}else{
				animation+= 1 ;
				}
				animationFrame = 0 ; 
				////////////
			}else{
				animationFrame +=1;
			}//////////////Pomera se <===
			if(!canMove){
				
			x +=dir;
			}
		}else{
			animation =0;
		}
		
		if(isJumping ){
			if(!IsCollidingWithBlock(new Point((int)(x + 2), (int)y), new Point((int)(x + width - 2),(int)y))){
			
			if(jumpingcount >= jumpingheight){
				
				isJumping =false;
							
				jumpingcount=0;
			}else{
				y-= jumpingSpeed;
				jumpingcount += 1;
				}
			}else{
				isJumping = false;
				jumpingcount=0;
			}
		}
		
	}
	public boolean IsCollidingWithBlock(Point pt1, Point pt2){
		for(int x=(int)(this.x/Tile.tileSize); x<(int)(this.x/Tile.tileSize + 3);x++){
			for(int y=(int)(this.y/Tile.tileSize); y<(int)(this.y/Tile.tileSize + 3);y++){
				if(x >= 0 && y >= 0 && x < Component.level.block.length && y< Component.level.block[0].length)
				if(Component.level.block[x][y].id != Tile.air ){
				if(Component.level.block[x][y].contains(pt1)||Component.level.block[x][y].contains(pt2)){
					return true;
					}
				}
			}
		}
		return false;
	}
	public void render(Graphics g){
		if(dir == movingSpeed){
			g.drawImage(Tile.tileset_terrain, (int) x - (int)Component.sX, (int) y- (int)Component.sY,(int)x+ (int)width- (int)Component.sX,(int)y+ (int)height- (int)Component.sY/**/, (id[0] * Tile.tileSize)+(Tile.tileSize * animation),(id[1]*Tile.tileSize), (id[0]*Tile.tileSize)+(Tile.tileSize * animation) + (int)width,id[1 ]*Tile.tileSize + (int)height, null);
		}else{
			g.drawImage(Tile.tileset_terrain, (int) x - (int)Component.sX, (int) y- (int)Component.sY,(int)x+ (int)width- (int)Component.sX,(int)y+ (int)height- (int)Component.sY/**/, (id[0]*Tile.tileSize)+(Tile.tileSize * animation) + (int)width, (id[1]*Tile.tileSize),(id[0] * Tile.tileSize)+(Tile.tileSize * animation),id[1 ]*Tile.tileSize + (int)height, null);

		}
	
	}
}
