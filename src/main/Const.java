package main;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Abstract class with static final fields for encapsulation of constants.
 * 
 * @author Влад
 */
public abstract class Const {

	// Game
	public static final String RecordsFile = "res\\records.ser";
	
	// Easy
	public static final int EasyFieldWidth = 9;
	public static final int EasyFieldHeight = 9;
	public static final int EasyBombsCount = 10;
	
	// Medium
	public static final int MediumFieldWidth = 15;
	public static final int MediumFieldHeight = 15;
	public static final int MediumBombsCount = 25;
	
	// Hard
	public static final int HardFieldWidth = 25;
	public static final int HardFieldHeight = 25;
	public static final int HardBombsCount = 70;

	// Graphics
	public static final int CellSize = 20;
	public static final int CellGapSize = 1;
	
	public static final String[] TableColumnNames = {
		"Player name", "Time"
	};

	public static final Image NegativeSmileIcon = new ImageIcon("res\\neg.png")
			.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
	public static final Image PositiveSmileIcon = new ImageIcon("res\\pos.png")
			.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
	public static final Image BombInactiveIcon = new ImageIcon(
			"res\\bomb_inactive.png").getImage().getScaledInstance(CellSize, CellSize, 
					Image.SCALE_DEFAULT);
	public static final Image BombActiveIcon = new ImageIcon(
			"res\\bomb_active.png").getImage().getScaledInstance(CellSize, CellSize,
					Image.SCALE_DEFAULT);
	public static final Image MaybeBomb = new ImageIcon("res\\flag.png")
			.getImage().getScaledInstance(CellSize, CellSize,
					Image.SCALE_DEFAULT);

}
