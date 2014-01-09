package events;

/**
 * Allow to listen events of start and end of game.
 * 
 * @author Влад
 */
public interface GameListener {

	public void endOfGame(EndOfGameEvent ev);
	public void startGame(StartGameEvent ev);
}
