import processing.core.PApplet;
import processing.core.PShape;

/**
 * A custom layer list UI element on with visibility and layer re-arrangemnt
 * features.
 */
public class LayerList {
	PApplet pApplet;
	Canvas canvas;

	private int x;
	private int y;
	private int w;
	private int h;

	private int listWidth;
	private int visBoxPadding, moveBoxPadding;
	private int selectedLayerIndex;

	private int visBoxX, visBoxY, visBoxSide;
	private int moveUpX, moveDownX, moveY, moveSide;

	PShape eye, up, down;

	public LayerList(PApplet pApplet, Canvas canvas, int x, int y, int w, int h) {
		this.pApplet = pApplet;
		this.canvas = canvas;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.listWidth = 40;
		this.selectedLayerIndex = 0;
		this.visBoxPadding = 6;
		this.moveBoxPadding = 6;

		eye = pApplet.loadShape("eye.svg");
		up = pApplet.loadShape("up.svg");
		down = pApplet.loadShape("down.svg");

		visBoxSide = listWidth - this.visBoxPadding * 2;
		moveSide = listWidth - this.moveBoxPadding * 2;

		visBoxX = this.x + this.visBoxPadding;
		moveUpX = this.x + this.w - (moveBoxPadding * 2 + moveSide * 2);
		moveDownX = this.x + this.w - (moveBoxPadding + moveSide);

	}

	public void draw() {
		this.pApplet.fill(100);
		this.pApplet.rect(this.x, this.y, this.w, this.h);

		for (int i = 0; i < canvas.getLayers().size(); i++) {
			this.pApplet.stroke(0);

			this.pApplet.fill((this.getCurrentLayerIndex() == i) ? 150 : 100);
			this.pApplet.rect(this.x, this.y + (i * listWidth), this.w, this.listWidth);

			// Visibility box
			visBoxY = this.y + (i * listWidth) + this.visBoxPadding;
			this.pApplet.fill(canvas.getLayers().get(i).isVisible() ? 120 : 70);
			this.pApplet.rect(visBoxX, visBoxY, visBoxSide, visBoxSide);
			if (canvas.getLayers().get(i).isVisible())
				this.pApplet.shape(eye, visBoxX, visBoxY, visBoxSide, visBoxSide);

			// Move Buttons
			moveY = this.y + (i * listWidth) + this.moveBoxPadding;
			this.pApplet.shape(up, moveUpX, moveY, moveSide, moveSide);
			this.pApplet.shape(down, moveDownX, moveY, moveSide, moveSide);

			// Move Down Button
			this.pApplet.fill(0);
			this.pApplet.textAlign(PApplet.LEFT, PApplet.CENTER);
			this.pApplet.text(canvas.getLayers().get(i).getName(), this.x + listWidth + 4,
					(listWidth / 2) + this.y + (i * listWidth));
		}
	}

	public int getCurrentLayerIndex() {
		return this.selectedLayerIndex;
	}

	private boolean isInUpBox(int x, int y, int index) {
		System.out.println("UP!");
		this.moveY = this.y + (index * listWidth) + this.moveBoxPadding;
		return (x >= this.moveUpX && x <= this.moveUpX + this.moveSide)
				&& (y >= this.moveY && y <= this.moveY + this.moveSide);
	}

	private boolean isInDownBox(int x, int y, int index) {
		System.out.println("Down!");
		this.moveY = this.y + (index * listWidth) + this.moveBoxPadding;
		return (x >= this.moveDownX && x <= this.moveDownX + this.moveSide)
				&& (y >= this.moveY && y <= this.moveY + this.moveSide);
	}

	public boolean isInList(int x, int y) {
		return (x >= this.x && x <= (this.x + this.w)) && (y >= this.y && y <= (this.y + this.h));
	}

	private boolean isInListItem(int x, int y, int index) {
		return (x >= this.x && x <= this.x + this.w) && (y >= (this.y + (index * this.listWidth))
				&& y <= (this.y + (index * this.listWidth)) + this.listWidth);
	}

	private boolean isInVisBox(int x, int y, int index) {
		this.visBoxY = this.y + (index * listWidth) + this.visBoxPadding;
		return (x >= this.visBoxX && x <= this.visBoxX + this.visBoxSide)
				&& (y >= this.visBoxY && y <= this.visBoxY + this.visBoxSide);
	}

	public void click() {
		for (int i = 0; i < canvas.getLayers().size(); i++) {
			if (this.isInListItem(pApplet.mouseX, pApplet.mouseY, i)) {
				if (this.isInVisBox(pApplet.mouseX, pApplet.mouseY, i)) {
					this.canvas.getLayers().get(i).toggleVisibility();
					System.out.println("Visibility for layer " + i + " has been toggled!");
				} else if (this.isInUpBox(pApplet.mouseX, pApplet.mouseY, i)) {
					this.canvas.moveLayer(i, i - 1);
					this.selectedLayerIndex--;
					System.out.println("Layer Up Box for layer " + i + " has been clicked!\nLayer " + i
							+ " moved to position" + (i + 1));
				} else if (this.isInDownBox(pApplet.mouseX, pApplet.mouseY, i)) {
					this.canvas.moveLayer(i, i + 1);
					this.selectedLayerIndex++;
					System.out.println("Layer Down Box for layer " + i + " has been clicked!\nLayer " + i
							+ " moved to position" + (i - 1));
				} else {
					System.out.println("List Item Number " + i + " clicked!");
					canvas.getLayers().get(this.selectedLayerIndex).deselectAll();
					this.selectedLayerIndex = i;
				}
			}
		}
	}
}
