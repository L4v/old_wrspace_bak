package com.tdlp.engine;

public class Game {
	
	private Mesh mesh;
	
	public Game() {
		mesh = new Mesh();
		
		Vertex[] data = new Vertex[] {new Vertex(new Vector3f(-1, -1, 0)),
				  		new Vertex(new Vector3f(-1, 1, 0)),
						new Vertex(new Vector3f(0, 1, 0))};
		mesh.addVertices(data);
	}

	public void input() {
		if(Input.getMouseDown(1))System.out.println("Right" + Input.getMousePosition().toString());
		if(Input.getMouseUp(1))System.out.println("!Right");
	}

	public void tick() {

	}

	public void render() {
		mesh.draw();
	}
}
