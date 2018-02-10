package com.tdlp.engine;

public class MainComponent {

	public static final int WIDTH = 800, HEIGHT = 600;
	public static final String TITLE = "3D";
	public static final double FRAME_CAP = 5000.0;
	
	private Game game;

	private boolean isRunning;

	public MainComponent() {
		RenderUtil.initGraphics();
		isRunning = false;
		game = new Game();
	}

	public void start() {
		if (isRunning) return;
		run();
	}

	private void run() {
		isRunning = true;
		int frames = 0;
		long frameCounter = 0;

		final double frameTime = 1.0 / FRAME_CAP;
		long lastTime = Time.getTime();
		double unprocessedTime = 0;

		while (isRunning) {
			boolean render = false;

			long startTime = Time.getTime();
			long pastTime = startTime - lastTime;
			lastTime = startTime;
			unprocessedTime += pastTime / (double) Time.SECOND;
			frameCounter += pastTime;
			while (unprocessedTime > frameTime) {
				render = true;
				unprocessedTime -= frameTime;
				if (Window.isCloseRequested()) stop();
				if (frameCounter >= Time.SECOND) {
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
				
				Time.setDelta(frameTime);
				
				game.input();
				Input.tick();
				game.tick();
			}
			if (render) {
				render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		cleanUp();
	}

	public void stop() {
		if (!isRunning) return;
		isRunning = false;
	}

	private void cleanUp() {
		Window.dispose();
	}

	private void render() {
		RenderUtil.clearScreen();
		game.render();
		Window.render();
	}

	public static void main(String[] args) {
		Window.createWindow(WIDTH, HEIGHT, TITLE);
		MainComponent game = new MainComponent();
		game.start();
	}
}
