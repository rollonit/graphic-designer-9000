import java.util.ArrayList;

import controlP5.DropdownList;
import processing.core.PApplet;

public class Layer {
	private ArrayList<Shape> shapes;
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

	public void addShape(ShapeType type, int beginX, int beginY, int endX, int endY, int color) {
		Shape s = new Shape(pApplet);
		switch (type) {
		case SQUARE:
			s.createSquare(beginX, beginY, -(beginX - endX), -(beginY - endY), color);
			break;
		case TRIANGLE:
			s.createTriangle(beginX, beginY, -(beginX - endX), -(beginY - endY), color);
			break;
		case ELLIPSE:
			s.createEllipse(beginX, beginY, -(beginX - endX), -(beginY - endY), color);
			break;
		}
		this.shapes.add(s);
	}

	public void draw() {
		for (Shape shape : shapes) {
			shape.draw();
		}
	}

	public void select() {
		boolean somethingSelected = false;
		for (int i = shapes.size() - 1; i >= 0; i--) {
			if (shapes.get(i).isInShape()) {
				this.deselectAll();
				shapes.get(i).select();
				somethingSelected = true;
				break;
			}
		}
		if (!somethingSelected) {
			this.deselectAll();
		}
	}

	public void save(int x, int y, int color) {
		this.selectedShape().setX(x);
		this.selectedShape().setY(y);
		this.selectedShape().setColor(color);
		this.selectedShape().update();
	}

	public void deselectAll() {
		for (Shape shape : shapes) {
			shape.deselect();
		}
	}

	public Shape selectedShape() {
		for (Shape shape : shapes) {
			if (shape.isSelected()) {
				return shape;
			}
		}
		return null;
	}
}