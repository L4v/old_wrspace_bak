package prc.unc.BuildingGame.Level;

import java.awt.*;

import prc.unc.BuildingGame.Component;
import prc.unc.BuildingGame.Misc.Tile;

public class Sky {
	public static int night = 1;
	public static int day = 0;
	public static int time = day;
	public int r1=100, g1 =150, b1=250;
	public int r2 =30 , g2=30,b2=100;
	public int r = r2, g = g1, b = b1;
	public int dayFrame=0, dayTime=70000;
	public int changeFrame = 0;
	public int changeTime = 4;
	
	public Sky(){
		if(time == day){
			r = r1;
			g = g1;
			b = b1;
		}else if(time == night)  {
			r = r2;
			b = b2;
			g = g2;
		}
	}
	
	
	public void tick(){
		if(dayFrame >= dayTime){
			if(time==day){
				time=night;
			}else if(time ==night){
				time = day;
			}
			dayFrame = 0;
		}else{
			dayFrame += 1;
		}
		if(changeFrame >= changeTime){
			if (time == day){
				if(r < r1){
					r += 1;
				}
				if(g < g1){
					g += 1;
				}
				if(b < b1){
					b += 1;
				}
			}else if(time==night){
				if(r > r2){
					r -= 1;
				}
				if(g > g2){
					g -= 1;
				}
				if(b > b2){
					b -= 1;
				}
			}
			
			changeFrame -= 0;
		}else{
			changeFrame += 1;
		}
	}
	public void render(Graphics gr){
		gr.setColor(new Color(r, g, b));
		gr.fillRect(0, 0, Component.pixel.width, Component.pixel.height);
		if(time == night){
			gr.drawImage(Tile.star1, 0, 0, null);
		}
		}
		
	}

