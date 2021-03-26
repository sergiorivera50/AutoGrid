package rules;

import core.Location;
import core.Grid;

public class GameOfLife extends Rule {
    @Override
    public int update(Location loc, Grid grid) throws Exception {
        int state = grid.getState(loc);
        int neighbours = grid.getNeighbours(loc);

        if (state == 1 && (neighbours == 2 || neighbours == 3)) {
            /* Any live cell with two or three live neighbours survives */
            return 1;
        }
        if (state == 0 && neighbours == 3) {
            /* Any dead cell with three live neighbours becomes a live cell */
            return 1;
        }
        /* All other live cells die in the next generation. Similarly, all other dead cells stay dead */
        return 0;
    }
}
