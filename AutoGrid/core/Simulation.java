package core;

import rules.*;

import java.util.ArrayList;

public class Simulation {
    private Grid grid;
    private final Grid original_grid;
    private ArrayList<int[][]> record = new ArrayList<>();
    private Rule rules;

    /**
     * Creates a simulation according to a given core.Grid and Rules.
     * @param simulation_grid core.Grid to operate in.
     * @param simulation_rules String containing the name of the rules configuration.
     */
    public Simulation (Grid simulation_grid, String simulation_rules) {
        grid = simulation_grid;
        original_grid = simulation_grid;
        rules = getRules(simulation_rules);
        record.add(grid.getWorld());
    }

    /**
     * Gets rules configuration.
     * @param rules_name String containing the name of the rules configuration.
     */
    private Rule getRules(String rules_name) {
        Object obj = new Object();

        try {
            Class<?> c = Class.forName("rules." + rules_name);
            try {
                obj = c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("There was an error while instantiating " + c.getName() + "!");
                System.exit(0);
            }
        } catch (ClassNotFoundException e) {
            System.out.println(rules_name + " not found!");
            System.exit(0);
        }

        return (Rule) obj;
    }

    public int[][] getFromRecord(int generation) throws Exception {
        if ((generation-1) > record.size()) {
            throw new Exception("Cannot retrieve a world from a future generation!");
        }

        return record.get(generation-1);
    }

    public void updateLastGeneration(int generation) {
        ArrayList<int[][]> newRecord = new ArrayList<>();
        for (int i = 0; i < generation; i++) {
            int[][] currentWorld = record.get(i);
            newRecord.add(currentWorld);
        }
        record = newRecord;
    }

    /**
     * Moves a step forward in the simulation.
     */
    public void step() throws Exception {
        simulate(1);
    }

    /**
     * Sets core.Grid to its original state when the simulation began.
     */
    public void reset() {
        grid = original_grid;
    }

    /**
     * Moves n steps forward in the simulation.
     * @param generations Number of steps to simulate.
     */
    public void simulate(int generations, boolean print) throws Exception {
        Grid futureGrid = new Grid(grid.getWidth(), grid.getHeight());

        for (int gens = 1; gens <= generations; gens++) {
            for (int i = 0; i < grid.getHeight(); i++) {
                for (int j = 0; j < grid.getWidth(); j++) {
                    Location here = new Location(j, i);
                    int newState = rules.update(here, grid);
                    futureGrid.setState(here, newState);
                }
            }

            grid.setWorld(futureGrid.getWorld());
            record.add(grid.getWorld());
            if (print) System.out.println(grid);
        }
    }

    public void simulate(int generations) throws Exception {
        simulate(generations, false);
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
        return "core.Simulation " + this.hashCode() + "\n\tcore.Grid: " + grid.hashCode() + "[w:"+grid.getWidth()+"][h:"
                + grid.getHeight()+"]\n\tRules: " + rules.getClass().getSimpleName();
    }
}