package events;

import java.awt.Event;

public class EndOfGameEvent extends Event {

	public EndOfGameEvent(Object target, int id, Object arg) {
		super(target, id, arg);
	}

}
