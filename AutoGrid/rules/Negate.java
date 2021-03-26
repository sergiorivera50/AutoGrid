package rules;

import core.Location;
import core.Grid;

public class Negate extends Rule {
    @Override
    public int update(Location loc, Grid grid) {
        int state = grid.getState(loc);
        if (state == 0) {
            return 1;
        } else if (state == 1) {
            return 0;
        } else {
            return state;
        }
    }
}
