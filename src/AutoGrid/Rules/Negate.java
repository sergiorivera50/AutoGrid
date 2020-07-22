package AutoGrid.Rules;
import AutoGrid.Grid;


public class Negate extends Rule {
    @Override
    public int update(int x, int y, Grid grid) {
        int state = grid.getState(x, y);
        if (state == 0) {
            return 1;
        } else if (state == 1) {
            return 0;
        } else {
            return state;
        }
    }
}
