package AutoGrid;


import AutoGrid.Rules.Rule;

public class Simulation {
    private Grid grid;
    private final Grid original;
    private Rule rules;

    /**
     * Creates a simulation according to a given Grid and Rules.
     * @param simulation_grid Grid to operate in.
     * @param simulation_rules String containing the name of the rules configuration.
     */
    public Simulation (Grid simulation_grid, String simulation_rules) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        grid = simulation_grid;
        original = simulation_grid;
        rules = getRules(simulation_rules);
    }

    /**
     * Gets rules configuration.
     * @param rules_name String containing the name of the rules configuration.
     */
    private Rule getRules(String rules_name) throws IllegalAccessException {
        Object obj = new Object();

        try {
            Class<?> c = Class.forName("AutoGrid.Rules." + rules_name);
            try {
                obj = c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("There was an error while instantiating " + c.getName() + "!");
                System.exit(0);
            }
        } catch (ClassNotFoundException e) {
            System.out.println(rules_name + " was not found inside the Rules folder!");
            System.exit(0);
        }

        return (Rule) obj;
    }

    /**
     * Moves a step forward in the simulation.
     */
    public void step() {
        simulate(1);
    }

    /**
     * Sets Grid to its original state when the simulation began.
     */
    public void reset() {
        grid = original;
    }

    /**
     * Moves n steps forward in the simulation.
     * @param generations Number of steps to simulate.
     */

    public void simulate(int generations) {
        int[][] currentWorld = grid.getWorld();

        for (int gens = 1; gens <= generations; gens++) {
            for (int i = 0; i < grid.getHeight(); i++) {
                for (int j = 0; j < grid.getWidth(); j++) {
                    int newState = rules.update(j, i, grid);
                    currentWorld[i][j] = newState;
                }
            }
            grid.setWorld(currentWorld);
        }
    }

    public void show() {
        System.out.println(this);
        System.out.println(grid);
    }

    public String toString() {
        return "Simulation " + hash() + "\n\tGrid: " + grid.hash() + "[w:"+grid.getWidth()+"][h:"
                + grid.getHeight()+"]\n\tRules: " + rules.getClass().getSimpleName();
    }

    public String hash() {
        return Integer.toHexString(this.hashCode());
    }
}