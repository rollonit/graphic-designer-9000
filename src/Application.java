import processing.core.PApplet;
import processing.event.MouseEvent;

/**
 * <h1>Graphics Designer 9000</h1>
 * <h3>A basic graphics design software written in Java using Processing.</h3>
 * It has basic shape creation and edition functions, along with layer that can
 * be rearranged and toggled on and off.
 * 
 * @author rollonit
 * @author Klemens Fliri
 * @version 1
 */
public class Application extends PApplet {

	Canvas mainApp;

	public static void main(String[] args) {
		PApplet.main("Application");
	}

	public void settings() {
		size(1280, 720);
	}

	public void setup() {
		mainApp = new Canvas(this, 280, 5, 710, 720);
		mainApp.init();
	}

	public void draw() {
		mainApp.draw();
	}

	// Button Events
	public void save() {
		mainApp.save();
	}

	public void remove() {
		mainApp.remove();
	}

	public void addLayer() {
		mainApp.addLayer();
	}

	public void removeLayer() {
		mainApp.removeLayer();
	}

	public void loadFile() {
		mainApp.loadFile();
	}

	public void saveFile() {
		mainApp.saveFile();
	}

	public void mousePressed(MouseEvent event) {
		if (event.getButton() == RIGHT) {
			mainApp.beginShape();
		} else if (event.getButton() == LEFT) {
			mainApp.beginDrag();
		}
	}

	public void mouseReleased(MouseEvent event) {
		if (event.getButton() == RIGHT) {
			mainApp.endShape();
		} else if (event.getButton() == LEFT) {
			mainApp.endDrag();
		}
	}
}
