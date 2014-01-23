package window.main.cell;

/**
 * <p>
 * Enumeration of states in which the cell can be.
 * </p>
 *
 * @author Vlad
 */
public enum CellState {

    /**
     * <p>
     * {@link Cell} state constant means that cell is closed. Looks like
     * JButton.
     * </p>
     *
     * @author Vlad
     */
    Closed,

    /**
     * <p>
     * {@link Cell} state constant means that cell is opened and there is not
     * bomb here.
     * </p>
     *
     * @author Vlad
     */
    Opened,

    /**
     * <p>
     * {@link Cell} state constant means that there is bomb in the cell, but
     * player not click it.
     * </p>
     *
     * @author Vlad
     */
    Bomb_inactive,

    /**
     * <p>
     * {@link Cell} state constant means that there is bomb in the cell, and
     * player click it.
     * </p>
     *
     * @author Vlad
     */
    Bomb_active,

    /**
     * <p>
     * {@link Cell} state constant means that player right-click cell and mark
     * it as Unknown. Looks like {@link #Opened} cell, but with sign "?".
     * </p>
     *
     * @author Vlad
     */
    Unknown,

    /**
     * <p>
     * {@link Cell} state constant means that player twice right-click cell and
     * mark it as "May be bomb". Looks like {@link #Opened} cell, but with flag
     * icon.
     * </p>
     *
     * @author Vlad
     */
    MaybeBomb

}
