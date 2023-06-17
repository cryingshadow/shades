package robots;

import java.util.*;

import body.*;
import gun.*;
import other.*;
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
        super(
            List.of(
                new WobbleClosest(),
                new SimpleTargetting(),
                new SimpleMovement(),
                new ColorStrategy()
            )
        );
    }

}
