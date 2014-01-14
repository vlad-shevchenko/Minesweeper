package window.main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import window.main.cell.Cell;
import window.main.cell.CellState;
import constant.Const;
import events.BombsCountListener;
import events.GameListener;

/**
 * <p>
 * Field - main element of game. Contains buttons ({@link Cell}) under which can
 * be bomb or number of bombs in neighbor cells. If user click the button with
 * bomb - game over. If all no-bomb cells have been clicked - user wins.
 * </p>
 * 
 * <p>
 * All login (buttons click handling, checking for game end, counting bombs)
 * encapsulated in class. Public method allow only start (or restart) game and
 * set/get {@link GameListener} and {@link BombsCountListener} to take
 * information from field.
 * </p>
 * 
 * @author Vlad
 */
@SuppressWarnings("serial")
public class Field extends JPanel {

	private Cell[][] cells;

	private int fieldWidth;
	private int fieldHeight;
	private int initialBombsCount;

	private GameListener gameListener;
	private BombsCountListener bombsListener;
	private MouseHandler mouseListener = new MouseHandler();

	private boolean started;
	private boolean ended;

	private int uncheckedBombsCount;
	private int closedCells;

	/**
	 * <p>
	 * Initiates panel. Creates grid layout and fills it with cells, places
	 * bombs, set all required field.
	 * </p>
	 * 
	 * @param fWidth
	 *            - number of cells along horizontal line
	 * @param fHeight
	 *            - number of cells along vertical line
	 * @param bombs
	 *            - number of bombs on the field
	 * 
	 * @author Vlad
	 */
	public Field(int fWidth, int fHeight, int bombs) {
		fieldWidth = fWidth;
		fieldHeight = fHeight;
		initialBombsCount = bombs;
		uncheckedBombsCount = bombs;
		closedCells = fieldWidth * fieldHeight;
		ended = false;

		GridLayout layout = new GridLayout(fieldHeight, fieldWidth,
				Const.CellGapSize(), Const.CellGapSize());
		setLayout(layout);

		int width = fieldWidth * (Const.CellSize() + Const.CellGapSize());
		int height = fieldHeight * (Const.CellSize() + Const.CellGapSize());
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(getPreferredSize());

		cells = new Cell[fieldWidth][fieldHeight];
		for (int i = 0; i < fieldWidth; ++i) {
			for (int j = 0; j < fieldHeight; ++j) {
				cells[i][j] = new Cell(mouseListener, CellState.Closed);
				add(cells[i][j].getContent());
			}
		}

		Random rand = new Random();
		for (int i = 0; i < initialBombsCount; ++i) {
			int x = rand.nextInt(fieldWidth);
			int y = rand.nextInt(fieldHeight);

			if (!cells[x][y].isBomb()) {
				cells[x][y].setBomb(true);
				incAround(x, y);
			} else {
				i--;
			}
		}
	}

	/**
	 * <p>
	 * Breaks current game and starts new - recreates all cells, places bombs in
	 * new places. Sets all fields to default values.
	 * </p>
	 * 
	 * @param fWidth
	 *            - number of cells along horizontal line
	 * @param fHeight
	 *            - number of cells along vertical line
	 * @param bombs
	 *            - number of bombs on the field
	 * 
	 * @author Vlad
	 */
	public void restart(int fWidth, int fHeight, int bombs) {
		fieldWidth = fWidth;
		fieldHeight = fHeight;
		initialBombsCount = bombs;
		uncheckedBombsCount = bombs;
		closedCells = fieldWidth * fieldHeight;
		started = false;
		ended = false;

		removeAll();

		GridLayout layout = new GridLayout(fieldHeight, fieldWidth,
				Const.CellGapSize(), Const.CellGapSize());
		setLayout(layout);

		int width = fieldWidth * (Const.CellSize() + Const.CellGapSize());
		int height = fieldHeight * (Const.CellSize() + Const.CellGapSize());
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(getPreferredSize());

		cells = new Cell[fieldWidth][fieldHeight];
		for (int i = 0; i < fieldWidth; ++i) {
			for (int j = 0; j < fieldHeight; ++j) {
				cells[i][j] = new Cell(mouseListener, CellState.Closed);
				add(cells[i][j].getContent());
			}
		}

		Random rand = new Random();
		for (int i = 0; i < initialBombsCount; ++i) {
			int x = rand.nextInt(fieldWidth);
			int y = rand.nextInt(fieldHeight);

			if (!cells[x][y].isBomb()) {
				cells[x][y].setBomb(true);
				incAround(x, y);
			} else {
				i--;
			}
		}
	}

	/**
	 * <p>
	 * Forms and returns array that contains some part of <b>cells</b> array.
	 * </p>
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * 
	 * @return new Cell[][] array with cells[x][y] element and all elements
	 *         within <b>radius</b>
	 * 
	 * @author Vlad
	 */
	private Cell[][] getSubArray(int x, int y, int radius) {
		int maxX = fieldWidth;
		int maxY = fieldHeight;

		Cell[][] result = new Cell[radius * 2 + 1][radius * 2 + 1];

		for (int i = x - radius; i <= x + radius; ++i) {
			for (int j = y - radius; j <= y + radius; ++j) {
				if (i >= 0 && i < maxX && j >= 0 && j < maxY) {
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
	 * @param x
	 *            - column of cell
	 * @param y
	 *            - row of cell
	 */
	private void incAround(int x, int y) {
		Cell[][] subArray = getSubArray(x, y, 1);
		for (int i = 0; i < subArray.length; ++i) {
			for (int j = 0; j < subArray[0].length; ++j) {
				if (subArray[i][j] != null) {
					subArray[i][j].incBombsCount();
				}
			}
		}
	}

	/**
	 * <p>
	 * Recursive procedure for opening specified cell and all zero-value cells
	 * around it.
	 * </p>
	 * 
	 * @param x of cell for open
	 * @param y of cell for open
	 * 
	 * @author Vlad
	 */
	private void openAround(int x, int y) {
		Cell[][] subArray = getSubArray(x, y, 1);
		for (int i = 0; i < subArray.length; ++i) {
			for (int j = 0; j < subArray[0].length; ++j) {
				Cell cell = subArray[i][j];
				if (cell != null) {
					if (cell.getState() != CellState.Opened) {
						cell.setState(CellState.Opened);
						closedCells--;

						if (cell.getBombsCount() == 0) {
							Point cords = findButtonCords(cell.getContent());
							openAround(cords.x, cords.y);
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * Returns index of {@link Cell} in array that contains specified JButton.
	 * </p>
	 * 
	 * @param button for search
	 * 
	 * @return Point with indexes of cell
	 * 
	 * @author Vlad
	 */
	private Point findButtonCords(JButton button) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j].getContent() == button)
					return new Point(i, j);
			}
		}

		return null;
	}

	/**
	 * <p>
	 * Check if player already click all non-bomb cells. If it is true invokes
	 * the {@link GameListener#endOfGame(boolean)} method of current
	 * {@link GameListener}.
	 * </p>
	 * 
	 * @author Vlad
	 */
	private void checkGameEnd() {
		if (closedCells == initialBombsCount) {
			gameListener.endOfGame(true);
		}
	}

	/**
	 * <p>
	 * Set state of all bombs at the field as {@link CellState#Bomb_inactive}.
	 * Invoked when player click the bomb cell.
	 * </p>
	 * 
	 * @author Vlad
	 */
	private void bombActivated() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j].isBomb()) {
					cells[i][j].setState(CellState.Bomb_inactive);
				}
			}
		}
	}

	/**
	 * <p>
	 * Sets new {@link GameListener}
	 * </p>
	 * 
	 * @param listener - new GameListener
	 */
	public void setGameListener(GameListener listener) {
		this.gameListener = listener;
	}

	/**
	 * <p>
	 * Returns current {@link GameListener}
	 * </p>
	 * 
	 * @return current {@link GameListener}
	 */
	public GameListener getGameListener() {
		return gameListener;
	}

	/**
	 * <p>
	 * Returns current {@link BombsCountListener}
	 * </p>
	 * 
	 * @return current {@link BombsCountListener}
	 */
	public BombsCountListener getBombsCountListener() {
		return bombsListener;
	}

	/**
	 * <p>
	 * Sets new {@link BombsCountListener}
	 * </p>
	 * 
	 * @param bombsListener new {@link BombsCountListener}
	 */
	public void setBombsCountListener(BombsCountListener bombsListener) {
		this.bombsListener = bombsListener;
	}

	/**
	 * <p>
	 * Handles event of mouse pressing.
	 * </p>
	 * 
	 * @author Vlad
	 */
	private class MouseHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent ev) {
			if (ended) {
				return;
			}

			JButton source = (JButton) ev.getSource();
			Point cords = findButtonCords(source);
			Cell sourceCell = cells[cords.x][cords.y];

			if (ev.getButton() == MouseEvent.BUTTON3) {
				rightClick(sourceCell);
			} else if (ev.getButton() == MouseEvent.BUTTON1) {
				leftClick(cords, sourceCell);
			}
			return;
		}

		/**
		 * <p>
		 * Handles clicking of cell with left mouse button. Change state of cell
		 * as {@link CellState#Opened} or inform Field about end of game if
		 * player click bomb.
		 * </p>
		 * 
		 * @param cords
		 *            of cell clicked
		 * @param sourceCell
		 *            - cell that was clicked
		 * 
		 * @author Vlad
		 */
		private void leftClick(Point cords, Cell sourceCell) {
			if (sourceCell != null) {
				if (!started) {
					started = true;
					gameListener.startGame();
				}
				if (sourceCell.isBomb()) {
					bombActivated();
					sourceCell.setState(CellState.Bomb_active);
					ended = true;
					gameListener.endOfGame(false);
				} else if (sourceCell.getState() != CellState.Opened) {
					sourceCell.setState(CellState.Opened);
					closedCells--;

					if (sourceCell.getBombsCount() == 0) {
						openAround(cords.x, cords.y);
					}

					checkGameEnd();
				}
			}
		}

		/**
		 * <p>
		 * Handles clicking of cell with right mouse button. Changes cell's
		 * state to mark it as {@link CellState#Unknown} or
		 * {@link CellState#MaybeBomb}.
		 * </p>
		 * 
		 * @param sourceCell
		 *            - cell was clicked
		 * 
		 * @author Vlad
		 */
		private void rightClick(Cell sourceCell) {
			switch (sourceCell.getState()) {
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
			default:
				// Not need because only Closed, MaybeBomb or Unknown cell
				// may be clicked
				break;
			}
		}

	}
}
