package events;

import java.awt.Event;

/**
 * Event suggests game have start.
 * 
 * @author ����
 */
public class StartGameEvent extends Event {

	public StartGameEvent(Object target, int id, Object arg) {
		super(target, id, arg);
	}

}
