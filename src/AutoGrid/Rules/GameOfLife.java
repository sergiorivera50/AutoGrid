package AutoGrid.Rules;
import AutoGrid.Grid;


public class GameOfLife extends Rule {
    @Override
    public int update(int x, int y, Grid grid) {
        int state = grid.getState(x, y);
        int neighbours = grid.getNeighbours(x, y);
        if (state == 1 && neighbours < 2) {
            return 0;
        }
        if (state == 1 && neighbours > 3) {
            return 0;
        }
        if (state == 0 && neighbours == 3) {
            return 1;
        }
        return state;
    }
}
