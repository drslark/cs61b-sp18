package byog.Core.terrain.concrete;

import java.io.Serializable;

import byog.Core.coordinate.Location;
import byog.Core.terrain.template.SingleLocationTerrain;
import byog.Core.terrain.view.Door;

/**
 * The SmallDoor is used to store information of the small door.
 */
public class SmallDoor<E extends Location<E>> extends SingleLocationTerrain<E>
    implements Door<E>, Serializable {
    
    private static final long serialVersionUID = 314L;

    private final int hashCode;

    /**
     * Constructor with location.
     *
     * @param location The location of the door.
     */
    public SmallDoor(E location) {
        super(location);
        hashCode = getLocation().hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof SmallDoor)) {
            return false;
        }

        SmallDoor<E> thatSmallDoor = (SmallDoor<E>) that;

        return getLocation().equals(thatSmallDoor.getLocation());
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
