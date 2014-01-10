package main;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Abstract class with static final field for encapsulation of constants.
 * 
 * @author Влад
 */
public abstract class Const {

	// Game
	public static final int DefaultFieldWidth = 9;
	public static final int DefaultFieldHeight = 9;
	public static final int DefaultBombsCount = 10;

	// Graphics
	public static final int CellSize = 30;
	public static final int CellGapSize = 1;

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
