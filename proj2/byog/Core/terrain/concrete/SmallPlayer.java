package byog.Core.terrain.concrete;

import java.io.Serializable;

import byog.Core.coordinate.Location;
import byog.Core.terrain.template.SingleLocationTerrain;
import byog.Core.terrain.view.Player;

/**
 * The SmallPlayer is used to store information of the small player.
 */
public class SmallPlayer<E extends Location<E>>
    extends SingleLocationTerrain<E> implements Player<E>, Serializable {
    
    private static final long serialVersionUID = 314L;

    private final int hashCode;

    /**
     * Constructor with location.
     *
     * @param location The location of the player.
     */
    public SmallPlayer(E location) {
        super(location);
        hashCode = getLocation().hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof SmallPlayer)) {
            return false;
        }

        SmallPlayer<E> thatSmallPlayer = (SmallPlayer<E>) that;

        return getLocation().equals(thatSmallPlayer.getLocation());
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
