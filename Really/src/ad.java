
public class ad extends ab {
	public ad(){
		
	}
	private static int floorPossition = 20;
	private static int ceilingPossition = 20;
	
	public static void flei(){///////////////////FLOOR && ceiling
		for(int i = 0; i < aa.height; i++){
			double ceiling  = (i - aa.height/2.0)/aa.height;
			double z = floorPossition / ceiling;
			if(ceiling < 0){
				z = ceilingPossition / -ceiling;
			}
			for(int x = 0; x < aa.width; x ++){
				double depth = (x - aa.width / 2.0) / aa.height;
				depth *= z;
				double xx = depth ;
				double yy = z;
				int xPixel = (int)(xx);
				int yPixel = (int)(yy);
				pixels[x+i*aa.width] = ((xPixel & 15) * 16) | ((yPixel & 15)*16)<<8;
			}
		}
	}
}
