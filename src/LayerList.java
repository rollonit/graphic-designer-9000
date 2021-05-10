import processing.core.PApplet;

public class LayerList {
	PApplet pApplet;
	Canvas canvas;

	private int x;
	private int y;
	private int w;
	private int h;

	private int listWidth;
	private int visBoxPadding;
	private int selectedLayerIndex;

	public LayerList(PApplet pApplet, int x, int y, int w, int h) {
		this.pApplet = pApplet;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.listWidth = 40;
		this.selectedLayerIndex = 0;
		this.visBoxPadding = 6;
	}

	public void draw() {
		this.pApplet.fill(100);
		this.pApplet.rect(x, y, w, h);

		/*
		 * for(Layer layer : canvas.getLayers()) {
		 * 
		 * }
		 */

		for (int i = 0; i < canvas.getLayers().size(); i++) {
			this.pApplet.stroke(0);

			this.pApplet.fill((this.getCurrentLayerIndex() == i) ? 150 : 100);
			this.pApplet.rect(x, y + (i * listWidth), w, this.listWidth);

			// Visibility box
			this.pApplet.fill(canvas.getLayers().get(i).isVisible() ? 120 : 70);
			this.pApplet.rect(x + this.visBoxPadding, y + (i * listWidth) + this.visBoxPadding,
					listWidth - this.visBoxPadding * 2, listWidth - this.visBoxPadding * 2);

			this.pApplet.fill(0);
			this.pApplet.textAlign(PApplet.LEFT, PApplet.CENTER);
			this.pApplet.text("Layer " + i, x + listWidth + 4, (listWidth / 2) + y + (i * listWidth));
		}
	}

	public void associateCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public int getCurrentLayerIndex() {
		return this.selectedLayerIndex;
	}

	public boolean isInVisBox(int x, int y, int index) {
		System.out.println("Act coords" + x + ", " + y + "\nVis coords: " + (x + this.visBoxPadding) + ", "
				+ (x + this.visBoxPadding + listWidth - this.visBoxPadding * 2) + ", "
				+ (y + (index * listWidth) + this.visBoxPadding) + ", "
				+ ((y + (index * listWidth) + this.visBoxPadding + listWidth - this.visBoxPadding * 2)));
		return (x >= (this.x + this.visBoxPadding)
				&& x <= (this.x + this.visBoxPadding + listWidth - this.visBoxPadding * 2))
				&& (y >= this.y + (index * listWidth) + this.visBoxPadding && y <= (this.y + (index * listWidth)
						+ this.visBoxPadding + listWidth - this.visBoxPadding * 2));
	}

	public boolean isInList(int x, int y) {
		return (x >= this.x && x <= (this.x + this.w)) && (y >= this.y && y <= (this.y + this.h));
	}

	private boolean isInListItem(int x, int y, int index) {
		return (x >= this.x && x <= this.x + this.w) && (y >= (this.y + (index * this.listWidth))
				&& y <= (this.y + (index * this.listWidth)) + this.listWidth);
	}

	public void click() {
		for (int i = 0; i < canvas.getLayers().size(); i++) {
			if (this.isInListItem(pApplet.mouseX, pApplet.mouseY, i)) {
				if (this.isInVisBox(pApplet.mouseX, pApplet.mouseY, i)) {
					this.canvas.getLayers().get(i).toggleVisibility();
					System.out.println("Visibility for layer " + i + " has been toggled!");
				} else {
					System.out.println("List Item Number " + i + " clicked!");
					this.selectedLayerIndex = i;
				}
			}
		}
	}
}
