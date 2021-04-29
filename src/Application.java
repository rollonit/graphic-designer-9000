import processing.core.PApplet;

public class Application extends PApplet {

	Canvas mainApp;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Application");
	}

	public void settings() {
		size(1280, 720);
	}

	public void setup() {
		mainApp = new Canvas(this);
		mainApp.init();
	}

	public void draw() {
		mainApp.draw();
	}

	// Button Events
	public void save() {
		mainApp.save();
	}

	public void add() {
		mainApp.add();
	}

	public void addLayer() {
		mainApp.addLayer();
	}
	
	public void removeLayer() {
		mainApp.removeLayer();
	}
}
