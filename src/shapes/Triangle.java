package shapes;
import core.Shape;
import core.ShapeType;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Triangle extends Shape {
	public Triangle(PApplet pApplet) {
		super(pApplet);
	}

	public Triangle(PApplet pApplet, int x, int y, int w, int h, int stroke, int color) {
		super(pApplet);
		create(x, y, w, h, stroke, color);
	}

	@Override
	public void create(int x, int y, int w, int h, int stroke, int color) {
		this.assignCoords(Shape.translate(x, y, w, h));
		this.color = color;
		this.stroke = stroke;
		type = ShapeType.TRIANGLE;

		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(this.x + (this.w / 2), this.y);
		this.shape.vertex(this.x, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y + this.h);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	@Override
	public void update() {
		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(this.x + (this.w / 2), this.y);
		this.shape.vertex(this.x, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y + this.h);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	@Override
	public boolean isInShape() {
		double x1 = this.x + this.w / 2;
		double y1 = this.y;
		double x2 = this.x;
		double y2 = this.y + this.h;
		double x3 = this.x + this.w;
		double y3 = this.y + this.h;
		double x = pApplet.mouseX;
		double y = pApplet.mouseY;
		double A = area(x1, y1, x2, y2, x3, y3);
		double A1 = area(x, y, x2, y2, x3, y3);
		double A2 = area(x1, y1, x, y, x3, y3);
		double A3 = area(x1, y1, x2, y2, x, y);

		return (Math.abs(A - (A1 + A2 + A3)) < 0.00001);
	}

	private double area(double x1, double y1, double x2, double y2, double x3, double y3) {
		return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
	}

	@Override
	public void highlightVertices() {
		for (int i = 0; i < this.shape.getVertexCount(); i++) {
			PVector v = this.shape.getVertex(i); // this.pApplet.noStroke();
			this.pApplet.fill(0xffff0000);
			this.pApplet.ellipseMode(PApplet.CENTER);
			this.pApplet.ellipse(v.x, v.y, 7, 7);
		}
	}
}
