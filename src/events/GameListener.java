package events;

/**
 * Allow to listen events of start and end of game.
 * 
 * @author Влад
 */
public interface GameListener {

	public void endOfGame(boolean win);
	public void startGame();
}
