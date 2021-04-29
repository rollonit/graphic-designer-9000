import java.util.ArrayList;

import controlP5.DropdownList;
import processing.core.PApplet;
import processing.core.PShape;

public class Layer {
	ArrayList<PShape> shapes;
	int layerIndex;
	DropdownList layerDDList;
	
	PApplet pApplet;

	public Layer(PApplet pApplet, DropdownList layerDDList, int layerIndex) {
		shapes = new ArrayList<>();
		this.layerDDList = layerDDList;
		this.layerIndex = layerIndex;
		this.pApplet = pApplet;
		layerDDList.addItem("Layer " + layerIndex, "Layer " + layerIndex);
	}
	
	public void addShape() {
		Shape s = new Shape();
		this.createSquare(640, 360, 50, 50, 0x00FF00FF);
		shapes.add(s);
	}
	
	public void createSquare( ) {
		
	}
	void createSquare(int x, int y, int h, int w, int color) {
		PShape s;
		s = pApplet.createShape();
		s.beginShape();
		s.fill(color);
		s.vertex(x, y);
		s.vertex(x, y + w);
		s.vertex(x + h, y + w);
		s.vertex(x + h, y);
		s.endShape(pApplet.CLOSE);
		shapes.add(s);
	}
}