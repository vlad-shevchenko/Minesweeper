package main;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

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
public abstract class Const {

// Game
	/**
	 * Path to file with high score table.
	 */
	public static final String RecordsFile = "res\\records.ser";

	/**
	 * <p>
	 * Maximal number of records in one table.
	 * </p>
	 * 
	 * @author Vlad
	 */
	public static final int MaxRecordsNumber = 10;

// Easy
	/**
	 * Default field width for easy mode.
	 */
	public static final int EasyFieldWidth = 9;
	/**
	 * Default field height for easy mode.
	 */
	public static final int EasyFieldHeight = 9;
	/**
	 * Default bombs count for easy mode.
	 */
	public static final int EasyBombsCount = 10;

// Medium
	/**
	 * Default field width for medium mode.
	 */
	public static final int MediumFieldWidth = 15;
	/**
	 * Default field height for medium mode.
	 */
	public static final int MediumFieldHeight = 15;
	/**
	 * Default bombs count for medium mode.
	 */
	public static final int MediumBombsCount = 25;

// Hard
	/**
	 * Default field width for hard mode.
	 */
	public static final int HardFieldWidth = 25;
	/**
	 * Default field height for hard mode.
	 */
	public static final int HardFieldHeight = 25;
	/**
	 * Default bombs count for hard mode.
	 */
	public static final int HardBombsCount = 70;

// Graphics
	/**
	 * Size (in pixels) of one cell of game field.
	 */
	public static final int CellSize = 20;

	/**
	 * Size (in pixels) of gap between two neighbor cells of game field.
	 */
	public static final int CellGapSize = 0;

	/**
	 * Names of columns headers of high score table. <br>
	 * <b>See also</b> {@link RecordsFrame}
	 */
	public static final String[] TableColumnNames = { "Player name", "Time" };

	/**
	 * <p>X coordinate of the middle of the screen.</p>
	 * 
	 * @author Vlad
	 */
	public static final int MiddleOfTheScreenX = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 2;
	
	/**
	 * <p>Y coordinate of the middle of the screen.</p>
	 * 
	 * @author Vlad
	 */
	public static final int MiddleOfTheScreenY = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 2;

	/**
	 * <p>Font for {@link Cell} in any state except {@link CellState#Unknown}.</p>
	 * 
	 * @author Vlad
	 */
	public static final Font DefaultCellFont = new Font("ArialBlack", Font.PLAIN, 12);
	
	/**
	 * <p>Font for {@link Cell} in {@link CellState#Unknown}.</p>
	 * 
	 * @author Vlad
	 */
	public static final Font UnknownCellFont = new Font("ArialBlack", Font.BOLD, 15);
	
	/**
	 * <p>Minimal amount of digits in labels in {@link MainFrame}.</p>
	 * 
	 * @author Vlad
	 */
	public static final int MinDigitsAtLabel = 3;
	

	
// Images
	/**
	 * Image of negative smile (for new game button) scaled to 32x32
	 */
	public static final Image NegativeSmileIcon = new ImageIcon("res\\neg.png")
			.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);

	/**
	 * Image of positive smile (for new game button) scaled to 32x32
	 */
	public static final Image PositiveSmileIcon = new ImageIcon("res\\pos.png")
			.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);

	/**
	 * Image of bomb for opened cell (but no one that user has clicked)
	 */
	public static final Image BombInactiveIcon = new ImageIcon(
			"res\\bomb_inactive.png").getImage().getScaledInstance(CellSize,
			CellSize, Image.SCALE_DEFAULT);

	/**
	 * Image of bomb for opened cell that user has clicked
	 */
	public static final Image BombActiveIcon = new ImageIcon(
			"res\\bomb_active.png").getImage().getScaledInstance(CellSize,
			CellSize, Image.SCALE_DEFAULT);

	/**
	 * Image of cell that user mark as "bomb may be here"
	 */
	public static final Image MaybeBomb = new ImageIcon("res\\flag.png")
			.getImage().getScaledInstance(CellSize, CellSize,
					Image.SCALE_DEFAULT);


}
