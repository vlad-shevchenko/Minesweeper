package window;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.Const;
import window.cell.Cell;
import window.cell.CellState;
import events.BombsCountListener;
import events.GameListener;

public class Field extends JPanel {

	private Cell[][] cells;
	
	private GameListener gameListener;
	private BombsCountListener bombsListener;
	
	private MouseHandler mouseListener = new MouseHandler();
	
	private boolean started;
	
	private int bombsCount = Const.DefaultBombsCount;
	private int closedCells = Const.DefaultFieldWidth * Const.DefaultFieldHeight;
	
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
				cells[i][j] = new Cell(mouseListener, CellState.Closed);
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
	
	private Cell[][] getSubArray(int x, int y, int radius) {
		int maxX = Const.DefaultFieldWidth;
		int maxY = Const.DefaultFieldHeight;
		
		Cell[][] result = new Cell[radius * 2 + 1][radius * 2 + 1];
		
		for(int i = x - radius; i <= x + radius; ++i) {
			for(int j = y - radius; j <= y + radius; ++j) {
				if(i >= 0 && i < maxX && j >= 0 && j < maxY) {
					result[i - (x - radius)][j - (y - radius)] = cells[i][j];
				} else {
					result[i - (x - radius)][j - (y - radius)] = null;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Increase count of bombs in all cells close to target one
	 * 
	 * @param x - column of cell
	 * @param y - row of cell
	 */
	private void incAround(int x, int y) {
		Cell[][] subArray = getSubArray(x, y, 1);
		for(int i = 0; i < subArray.length; ++i) {
			for(int j = 0; j < subArray[0].length; ++j) {
				if(subArray[i][j] != null) {
					subArray[i][j].incBombsCount();
				}
			}
		}
	}
	
	private void openAround(int x, int y) {
		Cell[][] subArray = getSubArray(x, y, 1);
		for(int i = 0; i < subArray.length; ++i) {
			for(int j = 0; j < subArray[0].length; ++j) {
				Cell cell = subArray[i][j];
				if(cell != null) {
					if(cell.getState() != CellState.Opened) {
						cell.setState(CellState.Opened);
						closedCells--;
						
						if(cell.getBombsCount() == 0) {
							Point cords = findButtonCords(cell.getContent());
							openAround(cords.x, cords.y);
						}
					}
				}
			}
		}
	}
	
	private Point findButtonCords(JButton button) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if(cells[i][j].getContent() == button)
					return new Point(i, j);
			}
		}
		
		return null;
	}
	
	private void checkGameEnd() {
		if(closedCells == Const.DefaultBombsCount) {
			gameListener.endOfGame(true);
		}
	}
	
	public void setGameListener(GameListener listener) {
		this.gameListener = listener;
	}
	
	public GameListener getGameListener() {
		return gameListener;
	}
	
	public void removeGameListener() {
		gameListener = null;
	}

	public BombsCountListener getBombsCountListener() {
		return bombsListener;
	}

	public void setBombsCountListener(BombsCountListener bombsListener) {
		this.bombsListener = bombsListener;
	}
	
	public void removeBombsCountListener() {
		bombsListener = null;
	}
	
	private void bombActivated() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if(cells[i][j].isBomb()) {
					cells[i][j].setState(CellState.Bomb_inactive);
				}
			}
		}
	}
	
	private class MouseHandler extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent ev) {
			JButton source = (JButton) ev.getSource();
			Point cords = findButtonCords(source);
			Cell sourceCell = cells[cords.x][cords.y];
			
			if(ev.getButton() == MouseEvent.BUTTON3) {
				switch(sourceCell.getState()) {
				case Closed:
					sourceCell.setState(CellState.MaybeBomb);
					bombsListener.updateBombsCount(--bombsCount);
					break;
				case MaybeBomb:
					sourceCell.setState(CellState.Unknown);
					bombsListener.updateBombsCount(++bombsCount);
					break;
				case Unknown:
					sourceCell.setState(CellState.Closed);
					break;
				}
			} else if (ev.getButton() == MouseEvent.BUTTON1) {
				if (sourceCell != null) {
					if(!started) {
						started = true;
						gameListener.startGame();
					}
					if (sourceCell.isBomb()) {
						bombActivated();
						sourceCell.setState(CellState.Bomb_active);
					} else {
						sourceCell.setState(CellState.Opened);
						closedCells--;
						
						if (sourceCell.getBombsCount() == 0) {
							openAround(cords.x, cords.y);
						}
						
						checkGameEnd();
					}
				}
			}
			return;
		}
		
	}
}
