package constant;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import window.champions.RecordsFrame;
import window.main.MainFrame;
import window.main.cell.Cell;
import window.main.cell.CellState;

/**
 * Abstract class with static final fields for encapsulation of constants.
 * 
 * @author Vlad
 */
public final class Const {

	private static HashMap<String, Entry<ConstType, Object>> consts;
	
	static {
		consts = new HashMap<String, Entry<ConstType, Object>>();
		
		put("RecordsFile", ConstType.String, "res\\records.ser");
		put("MaxRecordsNumber", ConstType.Integer, 10);
		
		put("EasyFieldWidth", ConstType.Integer, 9);
		put("EasyFieldHeight", ConstType.Integer, 9);
		put("EasyBombsCount", ConstType.Integer, 10);
		
		put("MediumFieldWidth", ConstType.Integer, 15);
		put("MediumFieldHeight", ConstType.Integer, 15);
		put("MediumBombsCount",ConstType.Integer, 25);
		
		put("HardFieldWidth", ConstType.Integer, 25);
		put("HardFieldHeight", ConstType.Integer, 25);
		put("HardBombsCount", ConstType.Integer, 70);
		
		put("CellSize", ConstType.Integer, 18);
		put("CellGapSize", ConstType.Integer, 0);
		
		put("TableColumnNames", ConstType.StringArray, 
				new String[] {"Player name", "Time" });
		
		put("DefaultCellFont", ConstType.Font, 
				new Font("ArialBlack", Font.PLAIN, 12));
		put("UnknownCellFont", ConstType.Font, 
				new Font("ArialBlack", Font.BOLD, 15));
		put("LabelFont", ConstType.Font, 
				new Font("Asrock7Segment", Font.BOLD, 16));
		
		put("LabelForeground", ConstType.Color, 
				new Color(165, 42, 42));
		put("LabelBackground", ConstType.Color, 
				new Color(0, 0, 139));
		
		put("MinDigitsAtLabel", ConstType.Integer, 3);
	}
	
	/**
	 * <p>
	 * Put constant with specified name, type and value to constant set (only if
	 * value not equal to <b>null</b>)
	 * </p>
	 * 
	 * @param name
	 *            of constant
	 * @param type
	 *            of constant
	 * @param value
	 *            of constant
	 * 
	 * @author Vlad
	 */
	static void put(String name, ConstType type, Object value) {
		if(value != null) {
			consts.put(name, new AbstractMap.SimpleEntry<ConstType, Object>(type, value));
		}
	}
	
	static HashMap<String, Entry<ConstType, Object>> getConsts() {
		return consts;
	}
	
	
// -----------
// Getters
// -----------
	
	/**
	 * Path to file with high score table.
	 */
	public static String RecordsFile() {
		return (String) consts.get("RecordsFile").getValue();
	}

	/**
	 * Maximal number of records in one table.
	 */
	public static Integer MaxRecordsNumber() {
		return (Integer) consts.get("MaxRecordsNumber").getValue();
	}

	/**
	 * Default field width for easy mode.
	 */
	public static int EasyFieldWidth() {
		return (Integer) consts.get("EasyFieldWidth").getValue();
	}
	
	/**
	 * Default field height for easy mode.
	 */
	public static int EasyFieldHeight() {
		return (Integer) consts.get("EasyFieldHeight").getValue();
	}
	/**
	 * Default bombs count for easy mode.
	 */
	public static int EasyBombsCount() {
		return (Integer) consts.get("EasyBombsCount").getValue();
	}

	/**
	 * Default field width for medium mode.
	 */
	public static int MediumFieldWidth() {
		return (Integer) consts.get("MediumFieldWidth").getValue();
	}
	/**
	 * Default field height for medium mode.
	 */
	public static int MediumFieldHeight() {
		return (Integer) consts.get("MediumFieldHeight").getValue();
	}
	/**
	 * Default bombs count for medium mode.
	 */
	public static int MediumBombsCount() {
		return (Integer) consts.get("MediumBombsCount").getValue();
	}

	/**
	 * Default field width for hard mode.
	 */
	public static int HardFieldWidth() {
		return (Integer) consts.get("HardFieldWidth").getValue();
	}
	/**
	 * Default field height for hard mode.
	 */
	public static int HardFieldHeight() {
		return (Integer) consts.get("HardFieldHeight").getValue();
	}
	/**
	 * Default bombs count for hard mode.
	 */
	public static int HardBombsCount() {
		return (Integer) consts.get("HardBombsCount").getValue();
	}

	/**
	 * Size (in pixels) of one cell of game field.
	 */
	public static int CellSize() {
		return (Integer) consts.get("CellSize").getValue();
	}

	/**
	 * Size (in pixels) of gap between two neighbor cells of game field.
	 */
	public static int CellGapSize() {
		return (Integer) consts.get("CellGapSize").getValue();
	}

	/**
	 * Names of columns headers of high score table. <br>
	 * <b>See also</b> {@link RecordsFrame}
	 */
	public static String[] TableColumnNames() {
		return (String[]) consts.get("TableColumnNames").getValue();
	}

	/**
	 * <p>Font for {@link Cell} in any state except {@link CellState#Unknown}.</p>
	 * 
	 * @author Vlad
	 */
	public static Font DefaultCellFont() {
		return (Font) consts.get("DefaultCellFont").getValue();
	}
	
	/**
	 * <p>Font for {@link Cell} in {@link CellState#Unknown}.</p>
	 * 
	 * @author Vlad
	 */
	public static Font UnknownCellFont() {
		return (Font) consts.get("UnknownCellFont").getValue();
	}
	
	/**
	 * <p>Minimal amount of digits in labels in {@link MainFrame}.</p>
	 * 
	 * @author Vlad
	 */
	public static int MinDigitsAtLabel() {
		return (Integer) consts.get("MinDigitsAtLabel").getValue();
	}
	
	/**
	 * <p>Font for timer and bombs counter.</p>
	 * 
	 * @author Vlad
	 */
	public static Font LabelFont() {
		return (Font) consts.get("LabelFont").getValue();
	}
	
	/**
	 * <p>Foreground color for timer and bombs counter.</p>
	 * 
	 * @author Vlad
	 */
	public static Color LabelForeground() {
		return (Color) consts.get("LabelForeground").getValue();
	}
	
	/**
	 * <p>Background color for timer and bombs counter.</p>
	 * 
	 * @author Vlad
	 */
	public static Color LabelBackground() {
		return (Color) consts.get("LabelBackground").getValue();
	}


// --------------
// Calculated (not loaded from *.xml)
// --------------
	
	/**
	 * Image of negative smile (for new game button) scaled to 32x32
	 */
	public static Image NegativeSmileIcon() {
		return new ImageIcon("res\\neg.png").getImage().getScaledInstance(32,
				32, Image.SCALE_DEFAULT);
	}
	
	/**
	 * Image of positive smile (for new game button) scaled to 32x32
	 */
	public static Image PositiveSmileIcon() {
		return new ImageIcon("res\\pos.png").getImage().getScaledInstance(32,
				32, Image.SCALE_DEFAULT);
	}
	
	/**
	 * Image of bomb for opened cell (but no one that user has clicked)
	 */
	public static Image BombInactiveIcon() {
		return new ImageIcon("res\\bomb_inactive.png").getImage()
				.getScaledInstance(CellSize(), CellSize(), Image.SCALE_DEFAULT);
	}
	
	/**
	 * Image of bomb for opened cell that user has clicked
	 */
	public static Image BombActiveIcon() {
		return new ImageIcon("res\\bomb_active.png").getImage()
				.getScaledInstance(CellSize(), CellSize(), Image.SCALE_DEFAULT);
	}
	
	/**
	 * Image of cell that user mark as "bomb may be here"
	 */
	public static Image MaybeBomb() {
		return new ImageIcon("res\\flag.png").getImage().getScaledInstance(
				CellSize(), CellSize(), Image.SCALE_DEFAULT);
	}
}
