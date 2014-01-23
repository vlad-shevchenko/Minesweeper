package events;

import window.main.MainFrame;

/**
 * Allow to listen events of start and end of game.
 *
 * @author Vlad
 */
public interface GameListener {

    /**
     * Invokes when player has selected cell with bomb or player has selected
     * all no-bomb cells. Pass boolean var - flag of game result.
     *
     * @param win - flag of game result. True if player has won game, otherwise
     *            false
     */
    public void endOfGame(boolean win);


    /**
     * Invokes when player selects first cell in current game to allow {@link MainFrame} start timer.
     */
    public void startGame();
}
