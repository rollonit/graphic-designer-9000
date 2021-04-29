import processing.core.PApplet;
import processing.core.PShape;

public class Shape extends PShape {

	PShape s;
	
	int stroke;

	public Shape(int mouseX, int mouseY, boolean draw, int stroke, int color) {
		
		this.stroke = color;
		
	}
	
	public Shape() {
	}

	void setup() {

	}

	void createSquare() {
		s = createShape(null, s);
		s.beginShape();
		s.fill(0, 0, 255);
		s.setStroke(stroke);
		s.vertex(0, 0);
		s.vertex(0, 50);
		s.vertex(50, 50);
		s.vertex(50, 0);
		s.endShape(CLOSE);
	}

	void createTriangle() {
		s = createShape(null, s);
		s.beginShape(TRIANGLE_STRIP);
		s.setStroke(stroke);
		s.vertex(30, 75);
		s.vertex(40, 20);
		s.vertex(50, 75);
		s.vertex(60, 20);
		s.vertex(70, 75);
		s.vertex(80, 20);
		s.vertex(90, 75);
		s.endShape();
	}

	void createCircle() {
		s = createShape(null, s);
		s.beginShape(ELLIPSE);
		s.setStroke(stroke);
		s.fill(0, 0, 255);
		s.noStroke();
		s.vertex(0, 0);
		s.vertex(0, 50);
		s.vertex(50, 50);
		s.vertex(50, 0);
	}

}
