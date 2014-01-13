package main;

import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.ImageIcon;

import window.champions.RecordsFrame;

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
	public static final String[] TableColumnNames = {
		"Player name", "Time"
	};
	
	public static final int MiddleOfTheScreenX = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 2;
	public static final int MiddleOfTheScreenY = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 2;

	
	
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
			"res\\bomb_inactive.png").getImage().getScaledInstance(CellSize, CellSize, 
					Image.SCALE_DEFAULT);
	
	/**
	 * Image of bomb for opened cell that user has clicked
	 */
	public static final Image BombActiveIcon = new ImageIcon(
			"res\\bomb_active.png").getImage().getScaledInstance(CellSize, CellSize,
					Image.SCALE_DEFAULT);
	
	/**
	 * Image of cell that user mark as "bomb may be here"
	 */
	public static final Image MaybeBomb = new ImageIcon("res\\flag.png")
			.getImage().getScaledInstance(CellSize, CellSize,
					Image.SCALE_DEFAULT);

}
