package rules;

import core.Location;
import core.Grid;

public abstract class Rule {
    public abstract int update(Location loc, Grid grid) throws Exception;
}
