
public class ab {
	public static int[] pixels;
	public ab(){
		pixels = new int[aa.width * aa.height];
	}
	public void draw(ab render, int xOff, int yOff){
		for(int i = 0; i < aa.height; i ++){
			int yPixel = i + yOff;
			if(yPixel < 0 || yPixel >= aa.height){
				continue;
			}
			
			for(int x = 0; x<aa.width; x++){
				int xPixel = x + xOff;
				if(xPixel < 0 || xPixel >= aa.width){
					continue;
				}
				pixels[xPixel + yPixel * aa.width] = render.pixels[x + i * aa.width];
			}
		}
	}
}
