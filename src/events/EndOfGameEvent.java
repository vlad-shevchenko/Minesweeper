package events;

import java.awt.AWTEvent;

/**
 * Event suggests game have end. Contains information about game result.
 * 
 * @author Влад
 */
public class EndOfGameEvent extends AWTEvent {

	private boolean win;
	
	public EndOfGameEvent(Object target, boolean win) {
		super(target, 0);
		this.win = win;
	}
	
	public boolean isWin() {
		return win;
	}
}
