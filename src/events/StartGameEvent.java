package events;

import java.awt.AWTEvent;
import java.awt.Event;

/**
 * Event suggests game have start.
 * 
 * @author ����
 */
public class StartGameEvent extends AWTEvent {

	public StartGameEvent(Object source) {
		super(source, 0);
	}
}
