package prc.unc.BuildingGame.Character;

public class DoubleRectangle {
	
	public double x ;
	public double y;
	public double width;
	public  double height;
	
	public DoubleRectangle(){
		setBounds(0, 0, 0, 0);
	}
	public DoubleRectangle(double x, double y, double width, double height){
		setBounds(x, y, width, height);
	}
	public void setBounds(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		}
}
