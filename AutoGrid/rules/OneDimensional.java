package rules;

import core.Location;
import core.Grid;

public class OneDimensional extends Rule {
    @Override
    public int update(Location loc, Grid grid) throws Exception {
        int state = grid.getState(loc);
        int neighbours = grid.getNeighbours(loc);

        if (state == 0) {
            if (neighbours == 2) {
                return 1;
            } else {
                return 0;
            }
        }
        if (state == 1) {
            if (neighbours == 0) {
                return 0;
            }
            if (neighbours == 1) {
                return 1;
            }
            if (neighbours == 2) {
                return 0;
            }
        }
        return state;
    }
}
