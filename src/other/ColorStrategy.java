package other;

import java.awt.*;

import robots.*;

public class ColorStrategy implements OtherStrategy {

    @Override
    public void initialize(final Shade robot) {
        robot.setColors(Color.BLACK, Color.BLACK, Color.RED);
    }

}
