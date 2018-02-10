
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


import javax.swing.JFrame;

public class aa extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean isRunning = false;
	
	public static int height = 400;
	public static int width = 600;
	public int[] Pixels;
	public BufferedImage img;
	public static String title = "TRI DE";
	public ac screen;
	public Thread thread;
	
	public aa(){
		screen = new ac();
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	}
	
	
	
	public static void main(String[] args){
		
		aa game = new aa();
		
		JFrame f = new JFrame();
		f.setSize(width, height);
		f.setVisible(true);
		f.setTitle(title);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.add(game);
		
		
		
		game.start();
	}
	public void start(){
		if(isRunning) return;
		isRunning = true;
		Thread thread = new Thread(this);
		thread.start();
		}
	public void run(){
		while(isRunning){
			render();
		}
	}
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		screen.render();
		for(int i = 0; i < screen.pixels.length; i++){
			Pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		bs.show();
	}
	}

