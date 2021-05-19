package shapes;
import core.Shape;
import core.ShapeType;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Square extends Shape {

	public Square(PApplet pApplet) {
		super(pApplet);
	}

	public Square(PApplet pApplet, int x, int y, int w, int h, int stroke, int color) {
		super(pApplet);
		this.create(x, y, w, h, stroke, color);
	}

	@Override
	public void create(int x, int y, int w, int h, int stroke, int color) {
		this.assignCoords(Shape.translate(x, y, w, h));
		this.color = color;
		this.stroke = stroke;
		type = ShapeType.SQUARE;

		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(this.x, this.y);
		this.shape.vertex(this.x, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	@Override
	public void update() {
		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(this.x, this.y);
		this.shape.vertex(this.x, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	@Override
	public boolean isInShape() {
		return (pApplet.mouseX >= this.x && pApplet.mouseX <= (this.x + this.w))
				&& (pApplet.mouseY >= this.y && pApplet.mouseY <= (this.y + this.h));
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
