import de.bezier.data.sql.*;
import processing.core.PApplet;

public class DB extends PApplet {

	SQLite db;
	Canvas canvas;
	PApplet pApplet;

	public DB(PApplet pApplet, Canvas canvas) {
		db = new SQLite(this, "data/db.sqlite");
		this.canvas = canvas;
		this.pApplet = pApplet;

		this.read();

	}

	public void read() {
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

				ShapeType type;
				
				// catching the latest information
				while (db.next()) {
					
					if (db.getString("type").equals("SQUARE")) {
						type = ShapeType.SQUARE;
					} else if (db.getString("type").equals("TRIANGLE")) {
						type = ShapeType.TRIANGLE;
					} else {
						type = ShapeType.ELLIPSE;
					}

					canvas.addToLayer(db.getInt("#l"), type, db.getString("name"), db.getInt("x"), db.getInt("y"), db.getInt("h"), db.getInt("w"), db.getInt("stroke"), db.getInt("color"));

					
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
	

}
