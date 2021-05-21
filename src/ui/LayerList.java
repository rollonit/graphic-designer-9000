package ui;

import core.Canvas;
import processing.core.PApplet;
import processing.core.PShape;

/**
 * <h1>Layer List</h1>
 * <h3>A custom layer list UI element on with visibility and layer
 * re-arrangement features.</h3>
 * <p>
 * Each layer item has a move up and a move down buttons, which can be used to
 * rearrange them. It also has a visibility toggle.
 * </p>
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

	/**
	 * Default constructor that creates a layer list UI element in with the supplied
	 * parameters.
	 * 
	 * @param pApplet The handle for the PApplet
	 * @param canvas  The canvas which the layer list is associated with
	 * @param x       The X coordinate of the corner of the box.
	 * @param y       The Y coordinate of the corner of the box.
	 * @param w       The width of the layer box.
	 * @param h       The height of the layer box.
	 */
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

	/**
	 * Draw call to draw the list. To be called every frame.
	 */
	public void draw() {
		// Background of the box
		this.pApplet.fill(65);
		this.pApplet.rect(this.x, this.y, this.w, this.h);

		for (int i = 0; i < canvas.getLayers().size(); i++) {
			this.pApplet.stroke(0);

			// List items
			this.pApplet.fill((this.getCurrentLayerIndex() == i) ? 150 : 100);
			this.pApplet.rect(this.x, this.y + (i * listWidth), this.w, this.listWidth);

			// Visibility box
			visBoxY = this.y + (i * listWidth) + this.visBoxPadding;
			this.pApplet.fill(canvas.getLayer(i).isVisible() ? 120 : 70);
			this.pApplet.rect(visBoxX, visBoxY, visBoxSide, visBoxSide);
			if (canvas.getLayer(i).isVisible())
				this.pApplet.shape(eye, visBoxX, visBoxY, visBoxSide, visBoxSide);

			// Move Buttons
			moveY = this.y + (i * listWidth) + this.moveBoxPadding;
			this.pApplet.shape(up, moveUpX, moveY, moveSide, moveSide);
			this.pApplet.shape(down, moveDownX, moveY, moveSide, moveSide);

			// Move Down Button
			this.pApplet.fill(0);
			this.pApplet.textAlign(PApplet.LEFT, PApplet.CENTER);
			this.pApplet.text(canvas.getLayer(i).getName(), this.x + listWidth + 4,
					(listWidth / 2) + this.y + (i * listWidth));
		}
	}

	public void removeSelectedLayer() {
		if (this.canvas.getLayers().size() > 1) {
			this.canvas.removeLayer(this.getCurrentLayerIndex());
			if (this.selectedLayerIndex > 0) {
				this.selectedLayerIndex--;
			}
		} else {
			System.out.println("Only one layer left! Can't remove!");
		}
	}

	/**
	 * Method to find which layer is currently selected.
	 * 
	 * @return The index of the currently selected layer.
	 */
	public int getCurrentLayerIndex() {
		return this.selectedLayerIndex;
	}

	/**
	 * Checks if a particular point is in the Up box of a particular list item.
	 * 
	 * @param x     The X coordinate of the point
	 * @param y     The Y coordinate of the point
	 * @param index The index of the list item to check for.
	 * @return True if the point is in the layer item's Up box.
	 */
	private boolean isInUpBox(int x, int y, int index) {
		System.out.println("UP!");
		this.moveY = this.y + (index * listWidth) + this.moveBoxPadding;
		return (x >= this.moveUpX && x <= this.moveUpX + this.moveSide)
				&& (y >= this.moveY && y <= this.moveY + this.moveSide);
	}

	/**
	 * Checks if a particular point is in the Down box of a particular list item.
	 * 
	 * @param x     The X coordinate of the point
	 * @param y     The Y coordinate of the point
	 * @param index The index of the list item to check for
	 * @return True if the point is in the layer item's Down box
	 */
	private boolean isInDownBox(int x, int y, int index) {
		System.out.println("Down!");
		this.moveY = this.y + (index * listWidth) + this.moveBoxPadding;
		return (x >= this.moveDownX && x <= this.moveDownX + this.moveSide)
				&& (y >= this.moveY && y <= this.moveY + this.moveSide);
	}

	/**
	 * Checks if a point is anywhere within the layer list.
	 * 
	 * @param x The X coordinate of the point
	 * @param y The Y coordinate of the point
	 * @return True if the point is anywhere within the list
	 */
	public boolean isInList(int x, int y) {
		return (x >= this.x && x <= (this.x + this.w)) && (y >= this.y && y <= (this.y + this.h));
	}

	/**
	 * Checks if the mouse is within a particular list item.
	 * 
	 * @param x     The X coordinate of the point
	 * @param y     The Y coordinate of the point
	 * @param index The index of the list item to check for
	 * @return True if the point is in somewhere in the list item
	 */
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
			// If the mouse is in a particular list item.
			if (this.isInListItem(pApplet.mouseX, pApplet.mouseY, i)) {
				// If the mouse is in the visibility toggle box.
				if (this.isInVisBox(pApplet.mouseX, pApplet.mouseY, i)) {
					this.canvas.getLayer(i).toggleVisibility();
					System.out.println("Visibility for layer " + i + " has been toggled!");
					// If the mouse is in the Move Layer Up box. It goes down, because the layers
					// are ordered ascending, top to bottom.
				} else if (this.isInUpBox(pApplet.mouseX, pApplet.mouseY, i)) {
					if (this.canvas.moveLayer(i, i - 1) == 1) {
						this.selectedLayerIndex--;
					}
					System.out.println("Layer Up Box for layer " + i + " has been clicked!\nLayer " + i
							+ " moved to position" + (i - 1));
					// If the mouse is in the Move Layer Down box. See above for why the directions
					// are flipped.
				} else if (this.isInDownBox(pApplet.mouseX, pApplet.mouseY, i)) {
					if (this.canvas.moveLayer(i, i + 1) == 1) {
						this.selectedLayerIndex++;
					}
					System.out.println("Layer Down Box for layer " + i + " has been clicked!\nLayer " + i
							+ " moved to position" + (i + 1));
					// If it's not in any box, which means it was a layer select action.
				} else {
					System.out.println("List Item Number " + i + " clicked!");
					canvas.getLayers().get(this.selectedLayerIndex).deselectAll();
					this.selectedLayerIndex = i;
				}
			}
		}
	}
}
