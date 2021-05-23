package shapes;

import core.Shape;
import core.ShapeType;
import processing.core.PApplet;
import processing.core.PShape;

public class Ellipse extends Shape {
	public Ellipse(PApplet pApplet) {
		super(pApplet);
	}

	public Ellipse(PApplet pApplet, int x, int y, int w, int h, int stroke, int color) {
		super(pApplet);
		create(x, y, w, h, stroke, color);
	}

	@Override
	public void create(int x, int y, int w, int h, int stroke, int color) {
		this.assignCoords(Shape.translate(x, y, w, h));
		this.color = color;
		this.stroke = stroke;
		type = ShapeType.ELLIPSE;

		pApplet.ellipseMode(PApplet.CORNER);
		this.shape = pApplet.createShape(PShape.ELLIPSE, this.x, this.y, this.w, this.h);
		this.shape.setFill(color);
		this.shape.setStroke(stroke);
	}

	@Override
	public void update() {
		pApplet.ellipseMode(PApplet.CORNER);
		this.shape = pApplet.createShape(PShape.ELLIPSE, this.x, this.y, this.w, this.h);
		this.shape.setFill(color);
		this.shape.setStroke(stroke);
	}

	@Override
	public boolean isInShape() {
		double centerX = this.x + this.w / 2;
		double centerY = this.y + this.h / 2;
		double det = (Math.pow((pApplet.mouseX - centerX), 2) / Math.pow(w / 2, 2))
				+ (Math.pow((pApplet.mouseY - centerY), 2) / Math.pow(h / 2, 2));
		System.out.println("det=" + det);
		return (det <= 1);
	}

	@Override
	public void highlightVertices() {
		this.pApplet.fill(0xffff0000);
		this.pApplet.ellipseMode(PApplet.CENTER);
		this.pApplet.ellipse(this.x, this.y, 7, 7);
		this.pApplet.ellipse(this.x, this.y + this.h, 7, 7);
		this.pApplet.ellipse(this.x + this.w, this.y, 7, 7);
		this.pApplet.ellipse(this.x + this.w, this.y + this.h, 7, 7);
	}

	@Override
	public String getTypeString() {
		return "ELLIPSE";
	}

}
