package robots;

import body.*;
import gun.*;
import radar.*;

/**
 * My first robot.
 * @author cryingshadow
 */
public class SilentShade extends Shade {

    /**
     * Constructor.
     */
    public SilentShade() {
        super(new OldestScanned(), new SimpleTargetting(), new SimpleMovement());
    }

}
