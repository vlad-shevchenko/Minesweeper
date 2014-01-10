package window.cell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.Const;

public class Cell {

	private boolean bomb;
	private int bombsAround;
	private CellState state;
	private JButton cellContent;
	private ActionListener listener;
	
	public Cell(ActionListener listener, CellState state) {
		this.listener = listener;
		
		cellContent = new JButton(".");
		cellContent.setPreferredSize(new Dimension(Const.CellSize, Const.CellSize));
		cellContent.setMaximumSize(new Dimension(Const.CellSize, Const.CellSize));
		cellContent.setFont(new Font("ArialBlack", Font.PLAIN, 12));
		cellContent.setMargin(new Insets(0, 0, 0, 0));
		cellContent.setFocusable(false);
		
		setState(state);
	}
	
	public void setState(CellState state) {
		this.state = state;
		
		switch (state) {
		case Closed:
			cellContent.setEnabled(true);
			cellContent.setText("");
			cellContent.addActionListener(listener);
			break;
		case Opened:
			cellContent.setEnabled(false);
			cellContent.setText(String.valueOf(bombsAround > 0 ? bombsAround : ""));
			if(bombsAround >= 3 && bombsAround <= 5) {
				cellContent.setBackground(Color.ORANGE);
			} else if(bombsAround > 5 && bombsAround <= 7) {
				cellContent.setBackground(Color.RED);
			} else if(bombsAround > 7) {
				cellContent.setBackground(Color.BLACK);
			}
			break;
		case Bomb_inactive: 
			cellContent.setIcon(new ImageIcon(Const.BombInactiveIcon));
			cellContent.setText("");
			cellContent.setEnabled(false);
			break;
		case Bomb_active: 
			cellContent.setIcon(new ImageIcon(Const.BombActiveIcon));
			cellContent.setText("");
			cellContent.setEnabled(false);
			break;
		}
	}
	
	public CellState getState() {
		return state;
	}
	
	public void incBombsCount() {
		bombsAround++;
	}
	
	public int getBombsCount() {
		return bombsAround;
	}
	
	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}
	
	public boolean isBomb() {
		return bomb;
	}
	
	public JButton getContent() {
		return cellContent;
	}
}
