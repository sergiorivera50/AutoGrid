package AutoGrid.Rules;
import AutoGrid.Grid;


public class GameOfLife extends Rule {
    @Override
    public int update(int x, int y, Grid grid) {
        int state = grid.getState(x, y);
        int neighbours = grid.getNeighbours(x, y, 3, 3);
        if (state == 1) {
            if (neighbours < 2) {
                return 0;
            } else if (neighbours == 2 || neighbours == 3) {
                return 1;
            } else {
                return 0;
            }
        } else if (state == 0) {
            if (neighbours == 3) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return state;
        }
    }
}
