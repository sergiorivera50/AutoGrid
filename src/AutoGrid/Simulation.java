package AutoGrid;


import AutoGrid.Rules.Rule;

public class Simulation {
    private Grid grid;
    private final Grid original_grid;
    private Rule rules;

    /**
     * Creates a simulation according to a given Grid and Rules.
     * @param simulation_grid Grid to operate in.
     * @param simulation_rules String containing the name of the rules configuration.
     */
    public Simulation (Grid simulation_grid, String simulation_rules) {
        grid = simulation_grid;
        original_grid = simulation_grid;
        rules = getRules(simulation_rules);
    }

    /**
     * Gets rules configuration.
     * @param rules_name String containing the name of the rules configuration.
     */
    private Rule getRules(String rules_name) {
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
        grid = original_grid;
    }

    /**
     * Moves n steps forward in the simulation.
     * @param generations Number of steps to simulate.
     */
    public void simulate(int generations) {
        int[][] currentWorld = grid.getWorld();
        int[][] futureWorld = new int[currentWorld.length][currentWorld[0].length];

        for (int gens = 1; gens <= generations; gens++) {
            for (int i = 0; i < grid.getHeight(); i++) {
                for (int j = 0; j < grid.getWidth(); j++) {
                    int newState = rules.update(j, i, grid);
                    futureWorld[i][j] = newState;
                }
            }
            grid.setWorld(futureWorld);
        }
    }

    /**
     * Calls the toString() method for this class and then prints the stored grid.
     */
    public void show() {
        System.out.println(this);
        System.out.println(grid);
    }

    /**
     * Overrides the toString() method in order to print this class in a nice format.
     * @return A String to represent this object.
     */
    public String toString() {
        return "Simulation " + hash() + "\n\tGrid: " + grid.hash() + "[w:"+grid.getWidth()+"][h:"
                + grid.getHeight()+"]\n\tRules: " + rules.getClass().getSimpleName();
    }

    /**
     * @return Hashcode of this object.
     */
    public String hash() {
        return Integer.toHexString(this.hashCode());
    }
}