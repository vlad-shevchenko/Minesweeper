package window.main.cell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import window.main.Field;
import constant.Const;

/**
 * <p>
 * Represents one cell of the field. {@link Field} displays cellContent -
 * JButton that change it properties according to {@link CellState}.
 * </p>
 * 
 * @author Vlad
 */
public class Cell {

	private boolean bomb;
	private int bombsAround;
	private CellState state;
	private JButton cellContent;
	private MouseListener listener;
	
	/**
	 * <p>
	 * Initiate button's properties and registers listener.
	 * </p>
	 * 
	 * @param listener - MouseListener for listening of clicks
	 * @param state - default state of cell
	 * 
	 * @author Vlad
	 */
	public Cell(MouseListener listener, CellState state) {
		this.listener = listener;
		
		cellContent = new JButton(".");
		cellContent.setPreferredSize(new Dimension(Const.CellSize(), Const.CellSize()));
		cellContent.setMaximumSize(new Dimension(Const.CellSize(), Const.CellSize()));
		cellContent.setMargin(new Insets(0, 0, 0, 0));
		cellContent.setFocusable(false);
		cellContent.addMouseListener(listener);
		
		setState(state);
	}
	
	/**
	 * <p>
	 * Updates state of button.
	 * </p>
	 * 
	 * @param state - new {@link CellState state}
	 * 
	 * @author Vlad
	 */
	public void setState(CellState state) {
		this.state = state;
		
		switch (state) {
		case Closed:
			cellContent.setEnabled(true);
			cellContent.setText("");
			break;
		case Opened:
			cellContent.setFont(Const.DefaultCellFont());
			cellContent.setEnabled(false);
			cellContent.setText(String.valueOf((bombsAround > 0) ? bombsAround : ""));
			if(bombsAround >= 3 && bombsAround <= 4) {
				cellContent.setBackground(Color.ORANGE);
			} else if(bombsAround > 4 && bombsAround <= 6) {
				cellContent.setBackground(Color.RED);
			} else if(bombsAround > 6) {
				cellContent.setBackground(Color.BLACK);
			}
			break;
		case Bomb_inactive: 
			cellContent.setIcon(new ImageIcon(Const.BombInactiveIcon()));
			cellContent.setDisabledIcon(new ImageIcon(Const.BombInactiveIcon()));
			cellContent.setText("");
			cellContent.setEnabled(false);
			break;
		case Bomb_active: 
			cellContent.setIcon(new ImageIcon(Const.BombActiveIcon()));
			cellContent.setDisabledIcon(new ImageIcon(Const.BombActiveIcon()));
			cellContent.setText("");
			cellContent.setEnabled(false);
			break;
		case Unknown:
			cellContent.setIcon(null);
			cellContent.setText("?");
			cellContent.setEnabled(true);
			cellContent.setFont(Const.UnknownCellFont());
			break;
		case MaybeBomb:
			cellContent.setIcon(new ImageIcon(Const.MaybeBomb()));
			cellContent.setText("");
			cellContent.setEnabled(true);
			break;
		}
	}
	
	/**
	 * <p>
	 * Returns current cell state.
	 * </p>
	 * 
	 * @return current cell state
	 * 
	 * @author Vlad
	 */
	public CellState getState() {
		return state;
	}
	
	/**
	 * <p>
	 * Increases counter of bombs around this cell.
	 * </p>
	 * 
	 * @author Vlad
	 */
	public void incBombsCount() {
		bombsAround++;
	}
	
	/**
	 * <p>
	 * Returns bombs counter value.
	 * </p>
	 * 
	 * @return bombs counter value
	 * 
	 * @author Vlad
	 */
	public int getBombsCount() {
		return bombsAround;
	}
	
	/**
	 * <p>
	 * Set boolean field that determines is this cell contains the bomb.
	 * </p>
	 * 
	 * @param bomb <b>true</b> to set this cell a bomb, <b>false</b> otherwise.
	 * 
	 * @author Vlad
	 */
	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}
	
	/**
	 * <p>
	 * Check is this cell contains the bomb.
	 * </p>
	 * 
	 * @return <b>true</b> if this cell contains the bomb, <b>false</b> otherwise.
	 * 
	 * @author Vlad
	 */
	public boolean isBomb() {
		return bomb;
	}
	
	/**
	 * <p>
	 * Returns button.
	 * </p>
	 * 
	 * @return button
	 * 
	 * @author Vlad
	 */
	public JButton getContent() {
		return cellContent;
	}
}
