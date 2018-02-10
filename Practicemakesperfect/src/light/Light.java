package light;

import java.awt.*;
import javax.swing.JFrame;
import java.applet.*;

public class Light extends Applet implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "LightingTesting";
	public static int pixelSize = 3;
	public static boolean running = false;
	public Image screen;
	public static Dimension size = new Dimension(640, 480);
	public static Dimension pixel = new Dimension(size.width / pixelSize, size.height / pixelSize);

	public Light() {
		setPreferredSize(size);
	}

	private static JFrame f;

	public static void main(String[] args) {
		Light light = new Light();
		f = new JFrame();
		f.add(light);
		f.pack();
		
		f.setTitle(title);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}

	public void start() {
		running = true;
		new Thread(this).start();
	}

	public void render() {
		Graphics g = screen.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, size.width, size.height);
		g.drawImage(screen, 0, 0, size.width, size.height, 0, 0, pixel.width, pixel.height, null);
		g.dispose();
	}

	public void run() {
		while (running == true) {
			render();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
