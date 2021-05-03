import java.util.ArrayList;

import controlP5.DropdownList;
import processing.core.PApplet;

public class Layer {
	ArrayList<Shape> shapes;
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
		Shape s = new Shape(pApplet);
		this.createSquare(640, 360, 50, 50, 0x00FF00FF);
		shapes.add(s);
	}

	public void createSquare() {

	}

	void createSquare(int x, int y, int h, int w, int color) {
		Shape s = new Shape(pApplet);
		s.createSquare();
		shapes.add(s);
	}
}