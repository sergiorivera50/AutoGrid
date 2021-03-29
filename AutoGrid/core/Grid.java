package core;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid implements Cloneable {
    private final int GRID_WIDTH;
    private final int GRID_HEIGHT;
    private final int DEFAULT_STATE = 0;

    int[][] world;

    /**
     * Creates the world grid and sets all states to default.
     * @param width Width of the grid.
     * @param height Height of the grid.
     */
    public Grid(int width, int height) throws Exception {
        if (width > 0 && height > 0) {
            GRID_WIDTH = width;
            GRID_HEIGHT = height;
        } else {
            throw new Exception("Width or/and height of grid is/are invalid!");
        }

        world = new int[GRID_HEIGHT][GRID_WIDTH];
        setState(DEFAULT_STATE);
    }

    public int[][] getWorld() {
        return world.clone();
    }

    public void setWorld(int[][] newWorld) {
        world = newWorld;
    }

    private void saveGrid(File filename) {
        try {
            FileOutputStream stream = new FileOutputStream(filename);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));

            writer.write(GRID_WIDTH + ", " + GRID_HEIGHT);
            writer.newLine();
            for (int i = 0; i < GRID_HEIGHT; i++) {
                for (int j = 0; j < GRID_WIDTH; j++) {
                    Location here = new Location(j, i);
                    int state = getState(here);
                    writer.write(state + ", ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGrid() {
        try {
            File filename = new File("test.txt");
            if (filename.createNewFile()) {
                System.out.println("Saved grid filename: " + filename.getName());
                saveGrid(filename);
            } else {
                System.out.println("File already exists");
                boolean status = filename.delete();
                saveGrid(filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * @return The width of the grid.
     */
    public int getWidth() {
        return GRID_WIDTH;
    }

    /**
     * @return The height of the grid.
     */
    public int getHeight() {
        return GRID_HEIGHT;
    }

    /**
     * @param loc The location on the grid.
     * @return The state of a given cell from the world.
     */
    public int getState(Location loc) {
        return world[loc.y()][loc.x()];
    }

    /**
     * Sets the state of a given cell from the world.
     * @param loc The location of the given cell on the grid.
     * @param value Value to be set.
     * @return True if successful, false otherwise.
     */
    public boolean setState(Location loc, int value) {
        if (loc.x() >= 0 && loc.x() < GRID_WIDTH && loc.y() >= 0 && loc.y() < GRID_HEIGHT) {
            world[loc.y()][loc.x()] = value;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the state of all cells.
     * @param value Value to be set.
     */
    public void setState(int value) {
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                Location here = new Location(j, i);
                setState(here, value);
            }
        }
    }

    /**
     * Sets a given state to a list of location of cells.
     * @param locs Array storing all cells' locations.
     * @param state The state wanted to be set.
     */
    public void setState(ArrayList<Location> locs, int state) {
        for (Location here : locs) {
            setState(here, state);
        }
    }

    /**
     * Sets a random state between a given range for every cell.
     * @param min Minimum integer value.
     * @param max Maximum integer value.
     */
    public void setRandom(int min, int max) {
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                int num = min + (int)(Math.random() * ((max - min) + 1));
                Location here = new Location(j, i);
                setState(here, num);
            }
        }
    }

    /**
     * @return One-dimensional array containing all states.
     */
    private int[] asArray() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                arr.add(world[i][j]);
            }
        }
        // List to int[]
        int[] intArr = new int[arr.size()];
        int i = 0;
        for (Integer e : arr)
            intArr[i++] = e;
        return intArr;
    }

    /**
     * Sets all states to a value of 1.
     */
    public void setOnes() {
        setState(1);
    }

    /**
     * Resets all states to its default state.
     */
    public void reset() {
        setState(DEFAULT_STATE);
    }

    /**
     * @param loc Cell's location on the grid.
     * @param width Width of the neighbours matrix.
     * @param height Height of the neighbours matrix.
     * @return core.Grid containing nxm adjacent cells from a given cell.
     */
    public Grid getNeighboursGrid(Location loc, int width, int height) throws Exception {
        if (width % 2 == 0 || height % 2 == 0) {
            throw new Exception("Neighbour matrix dimensions must be odd!");
        }

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        Grid neighboursGrid = new Grid(width, height);

        int i = 0;
        int j = 0;

        for (int k = -halfHeight; k < height - halfHeight; k++) {
            for (int l = -halfWidth; l < width - halfWidth; l++) {
                Location here = new Location(j, i);
                try {
                    /* In bounds */
                    neighboursGrid.setState(here, world[loc.y() + k][loc.x() + l]);
                } catch (Exception e) {
                    /* Out of bounds */
                    neighboursGrid.setState(here, DEFAULT_STATE);
                } finally {
                    j++;
                }
            }
            i++;
            j = 0;
        }

        return neighboursGrid;
    }

    /**
     * @param loc core.Location of the cell on the grid.
     * @param width Width of the neighbours matrix.
     * @param height Height of the neighbours matrix.
     * @return The sum of all the nxm adjacent values from a given cell.
     */
    public int getNeighbours(Location loc, int width, int height) throws Exception {
        Grid neighboursGrid = getNeighboursGrid(loc, width, height);

        int total = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Location here = new Location(j, i);
                total += neighboursGrid.getState(here);
            }
        }

        return total - getState(loc); // discards central cell as neighbour
    }

    /**
     * Default use for getting the sum of immediate neighbours from a given cell.
     * @param loc Cell's location on the grid.
     * @return The sum of all 3x3 adjacent values from a given cell.
     */
    public int getNeighbours(Location loc) throws Exception {
        return getNeighbours(loc, 3, 3);
    }

    /**
     * @return The mean value of all states.
     */
    public double mean() {
        double sum = 0;
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                sum += world[i][j];
            }
        }
        return sum / (GRID_HEIGHT * GRID_WIDTH);
    }

    /**
     * @return The median value of all states.
     */
    public double median() {
        int[] arr = asArray();
        Arrays.sort(arr);
        double median;
        if (arr.length % 2 == 0) {
            median = ((double) arr[arr.length / 2] + (double) arr[arr.length / 2 - 1]) / 2;
        } else {
            median = arr[arr.length / 2];
        }
        return median;
    }

    /**
     * @return The sum of the states of all cells.
     */
    public int population() {
        int sum = 0;
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                sum += world[i][j];
            }
        }
        return sum;
    }

    /**
     * Overrides the toString() method in order to print this class in a nice format.
     * @return A String to represent this object.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < GRID_HEIGHT; i++) {
            for(int j = 0; j < GRID_WIDTH; j++) {
                str.append(world[i][j]);
                str.append("\t");
            }
            str.append("\n");
        }

        return str.toString();
    }
}
