package rules;

import core.Grid;
import core.Location;

public class RandomChoice extends Rule {
    @Override
    public int update(Location loc, Grid grid) {
        return (int)(Math.random() * 2);
    }
}
