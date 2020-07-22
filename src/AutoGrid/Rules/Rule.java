package AutoGrid.Rules;
import AutoGrid.Grid;


public abstract class Rule {
    public abstract int update(int x, int y, Grid grid);
}
