package core;
import de.bezier.data.sql.*;
import processing.core.PApplet;

/**
 * <h3>Database class that handles all the SQlite data read and write
 * operations.</h3> It must be associated with a certain DB file location, and
 * reads and writes the layers and shapes from the canvas.
 */
public class DB {

	SQLite db;
	Canvas canvas;
	Layer layer;
	PApplet pApplet;

	public DB(PApplet pApplet, Canvas canvas) {
		this.canvas = canvas;
		this.pApplet = pApplet;
		db = new SQLite(this.pApplet, "data/db.sqlite");
	}

	public void read() {
		ShapeType type;

		try {
			if (db.connect()) {
				db.query("SELECT * FROM layer ORDER BY #l");

				// catching the latest information
				while (db.next()) {

					canvas.addLayer(db.getString("name"), (db.getInt("visibility") == 1) ? true : false);

					PApplet.print(db.getString("name"));
					PApplet.print(db.getInt("visibility"));
				}
			}

			if (db.connect()) {
				db.query("SELECT * FROM shape ORDER BY #s");

				// catching the latest information
				while (db.next()) {

					if (db.getString("type").equals("SQUARE")) {
						type = ShapeType.SQUARE;
					} else if (db.getString("type").equals("TRIANGLE")) {
						type = ShapeType.TRIANGLE;
					} else {
						type = ShapeType.ELLIPSE;
					}

					canvas.addToLayer(db.getInt("#l"), type, db.getString("name"), db.getInt("x"), db.getInt("y"),
							db.getInt("h"), db.getInt("w"), db.getInt("stroke"), db.getInt("color"));

					PApplet.print(db.getString("name"));
					PApplet.print(db.getString("x"));
					PApplet.print(db.getString("y"));

				}
			}
		}
		catch (Exception e) {
			// Your only way to see whether an UPDATE or INSERT statement worked
			// is when no exception occurred
			e.printStackTrace();
		}
	}

	public void write() {

		String type = "";

		try {
			if (db.connect()) {

				// write layers
				for (int i = 0; i < canvas.getLayers().size(); i++) {
					int visible = canvas.getLayer(i).isVisible() ? 1 : 0;
					db.query("INSERT INTO layer VALUES(" + i + ", \"" + canvas.getLayer(i).getName() + "\", " + visible
							+ ")");
				}

				// write shapes
				for (int i = 0; i < canvas.getLayers().size(); i++) {
					for (int j = 0; j < layer.getShapes().size(); j++) {
						switch (canvas.getLayer(i).getShape(j).getType()) {
						case SQUARE:
							type = "SQUARE";
							break;
						case TRIANGLE:
							type = "TRIANGLE";
							break;
						case ELLIPSE:
							type = "ELLIPSE";
							break;
						}

						db.query("INSERT INTO shape VALUES(\"" + i + "\", \"" + j + "\", \"" + type + "\", \""
								+ canvas.getLayer(i).getShape(j).getName() + "\",\""
								+ canvas.getLayer(i).getShape(j).getX() + "\",\""
								+ canvas.getLayer(i).getShape(j).getY() + "\",\""
								+ canvas.getLayer(i).getShape(j).getH() + "\",\""
								+ canvas.getLayer(i).getShape(j).getW() + "\",\""
								+ canvas.getLayer(i).getShape(j).getStroke() + "\",\""
								+ canvas.getLayer(i).getShape(j).getColor() + "\") ");

						PApplet.print("INSERT INTO shape VALUES(\"" + i + "\", \"" + j + "\", \"" + type + "\", \""
								+ canvas.getLayer(i).getShape(j).getName() + "\", \""
								+ canvas.getLayer(i).getShape(j).getX() + "\", \""
								+ canvas.getLayer(i).getShape(j).getY() + "\", \""
								+ canvas.getLayer(i).getShape(j).getH() + "\", \""
								+ canvas.getLayer(i).getShape(j).getW() + "\", \""
								+ canvas.getLayer(i).getShape(j).getStroke() + "\", \""
								+ canvas.getLayer(i).getShape(j).getColor() + "\") ");
					}
				}

			}

		}

		catch (Exception e) {
			// Your only way to see whether an UPDATE or INSERT statement worked
			// is when no exception occurred
			e.printStackTrace();
		}
	}

}
