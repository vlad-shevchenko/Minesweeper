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
	
	private int fieldWidth;
	private int fieldHeight;
	private int initialBombsCount;
	
	private GameListener gameListener;
	private BombsCountListener bombsListener;
	private MouseHandler mouseListener = new MouseHandler();
	
	private boolean started;
	
	private int uncheckedBombsCount;
	private int closedCells;
	
	public Field(int fWidth, int fHeight, int bombs) {
		fieldWidth = fWidth;
		fieldHeight = fHeight;
		initialBombsCount = bombs;
		uncheckedBombsCount = bombs;
		closedCells = fieldWidth * fieldHeight;
		
		GridLayout layout = new GridLayout(fieldHeight, fieldWidth, Const.CellGapSize, Const.CellGapSize);
		setLayout(layout);

		int width = fieldWidth * (Const.CellSize + Const.CellGapSize);
		int height = fieldHeight * (Const.CellSize + Const.CellGapSize);
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(getPreferredSize());
		
		cells = new Cell[fieldWidth][fieldHeight];
		for(int i = 0; i < fieldWidth; ++i) {
			for(int j = 0; j < fieldHeight; ++j) {
				cells[i][j] = new Cell(mouseListener, CellState.Closed);
				add(cells[i][j].getContent());
			}
		}
		
		Random rand = new Random();
		for(int i = 0; i < initialBombsCount; ++i) {
			int x = rand.nextInt(fieldWidth);
			int y = rand.nextInt(fieldHeight);
			
			if(!cells[x][y].isBomb()) {
				cells[x][y].setBomb(true);
				incAround(x, y);
			} else {
				i--;
			}
		}
	}
	
	private Cell[][] getSubArray(int x, int y, int radius) {
		int maxX = fieldWidth;
		int maxY = fieldHeight;
		
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
		if(closedCells == initialBombsCount) {
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
					bombsListener.updateBombsCount(--uncheckedBombsCount);
					break;
				case MaybeBomb:
					sourceCell.setState(CellState.Unknown);
					bombsListener.updateBombsCount(++uncheckedBombsCount);
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
						gameListener.endOfGame(false);
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
