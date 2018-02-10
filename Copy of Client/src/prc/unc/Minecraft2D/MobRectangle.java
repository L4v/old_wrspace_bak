package prc.unc.Minecraft2D;

public class MobRectangle {
	public double x ;
	public double y;
	public double width;
	public double height;
	
	public MobRectangle(){
		setBounds(0, 0, 0, 0);
	}
	public MobRectangle(double x, double y, double width, double height){
		setBounds(x, y, width, height);
	}
	public void setBounds(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		}
}
