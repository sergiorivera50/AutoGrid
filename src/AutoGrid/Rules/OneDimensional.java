package AutoGrid.Rules;

import AutoGrid.Grid;

public class OneDimensional extends Rule {
    @Override
    public int update(int x, int y, Grid grid) {
        int state = grid.getState(x, y);
        int neighbours = grid.getNeighbours(x, y);

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
