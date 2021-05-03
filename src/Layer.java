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

	public void addShape(ShapeType type) {
		Shape s = new Shape(pApplet);
		switch (type) {
		case SQUARE:
			s.createSquare();
			break;
		case TRIANGLE:
			s.createTriangle();
			break;
		case ELLIPSE:
			s.createEllipse();
			break;
		}
		shapes.add(s);
	}

	public void addShape(ShapeType type, int beginX, int beginY, int endX, int endY, int color) {
		Shape s = new Shape(pApplet);
		switch (type) {
		case SQUARE:
			s.createSquare(beginX, beginY, -(beginX - endX), -(beginY - endY), color);
			break;
		case TRIANGLE:
			s.createTriangle();
			break;
		case ELLIPSE:
			s.createEllipse();
			break;
		}
		shapes.add(s);
	}

	public void draw() {
		for (Shape shape : shapes) {
			shape.draw();
		}
	}
}