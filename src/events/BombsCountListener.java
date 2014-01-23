package events;

import window.main.Field;
import window.main.MainFrame;

/**
 * Allows {@link MainFrame} to listen events of changing unchecked bombs count to update
 * label.
 *
 * @author Vlad
 */
public interface BombsCountListener {

    /**
     * Invokes when amount of unchecked bombs on the {@link Field} changes.
     *
     * @param newCount - new count of unchecked bombs
     */
    public void updateBombsCount(int newCount);

}
