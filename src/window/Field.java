package window;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.Const;
import window.cell.Cell;
import window.cell.CellState;

public class Field extends JPanel implements ActionListener {

	private Cell[][] cells;
	
	public Field() {
		GridLayout layout = new GridLayout(Const.DefaultFieldHeight, Const.DefaultFieldWidth, Const.CellGapSize, Const.CellGapSize);
		setLayout(layout);

		int width = Const.DefaultFieldWidth * (Const.CellSize + Const.CellGapSize);
		int height = Const.DefaultFieldHeight * (Const.CellSize + Const.CellGapSize);
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(getPreferredSize());
		
		cells = new Cell[Const.DefaultFieldWidth][Const.DefaultFieldHeight];
		for(int i = 0; i < Const.DefaultFieldWidth; ++i) {
			for(int j = 0; j < Const.DefaultFieldHeight; ++j) {
				cells[i][j] = new Cell(this, CellState.Closed);
				add(cells[i][j].getContent());
			}
		}
		
		Random rand = new Random();
		for(int i = 0; i < Const.DefaultBombsCount; ++i) {
			int x = rand.nextInt(Const.DefaultFieldWidth);
			int y = rand.nextInt(Const.DefaultFieldHeight);
			
			if(!cells[x][y].isBomb()) {
				cells[x][y].setBomb(true);
				incAround(x, y);
			} else {
				i--;
			}
		}
	}
	
	/**
	 * Increase count of bombs in all cells close to target one
	 * 
	 * @param x - column of cell
	 * @param y - row of cell
	 */
	private void incAround(int x, int y) {
		int maxX = Const.DefaultFieldWidth;
		int maxY = Const.DefaultFieldHeight;
		if(x - 1 >= 0 && y - 1 >= 0) cells[x - 1][y - 1].incBombsCount();
		if(y - 1 >= 0) cells[x][y - 1].incBombsCount();
		if(x + 1 < maxX && y - 1 >= 0) cells[x + 1][y - 1].incBombsCount();
		if(x - 1 >= 0) cells[x - 1][y].incBombsCount();
		if(x + 1 < maxX) cells[x + 1][y].incBombsCount();
		if(x - 1 >= 0 && y + 1 < maxY) cells[x - 1][y + 1].incBombsCount();
		cells[x][y].incBombsCount();
		if(x + 1 < maxX && y + 1 < maxY) cells[x + 1][y + 1].incBombsCount();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
	}
}
