package events;

import java.awt.Event;

/**
 * Event suggests game have end. Contains information about game result.
 * 
 * @author Влад
 */
public class EndOfGameEvent extends Event {

	public EndOfGameEvent(Object target, int id, Object arg) {
		super(target, id, arg);
	}

}
