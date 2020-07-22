package AutoGrid;


public class Grid {
    private final int GRID_WIDTH;
    private final int GRID_HEIGHT;
    private final int DEFAULT_STATE = 0;

    int[][] world;

    /**
     * Creates the world grid and sets all states to default.
     * @param width Width of the grid.
     * @param height Height of the grid.
     */
    public Grid(int width, int height) {
        if (width > 0 && height > 0) {
            GRID_WIDTH = width;
            GRID_HEIGHT = height;
        } else {
            GRID_WIDTH = 1;
            GRID_HEIGHT = 1;
        }
        world = new int[GRID_HEIGHT][GRID_WIDTH];
        setState(DEFAULT_STATE);
    }

    /**
     * Creates the world grid using a custom matrix parameter.
     * @param custom_matrix Matrix containing all states to be copied.
     */
    public Grid(int[][] custom_matrix) {
        world = custom_matrix;
        GRID_HEIGHT = custom_matrix.length;
        GRID_WIDTH = custom_matrix[0].length;
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
     * @param x Cell's x-coordinate.
     * @param y Cell's y-coordinate.
     * @return The state of a given (x, y) cell from the world.
     */
    public int getState(int x, int y) {
        return world[y][x];
    }

    /**
     * Sets the state of a given (x, y) cell from the world.
     * @param x Cell's x-coordinate.
     * @param y Cell's y-coordinate.
     * @param value Value to be set.
     * @return True if successful, false otherwise.
     */
    public boolean setState(int x, int y, int value) {
        if (x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_HEIGHT) {
            world[y][x] = value;
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
                setState(j, i, value);
            }
        }
    }

    /**
     * Sets a random state between a given range for every cell.
     * @param min Minimum value.
     * @param max Maximum value.
     */
    public void setRandom(int min, int max) {
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                int num = min + (int)(Math.random() * ((max - min) + 1));
                setState(j, i, num);
            }
        }
    }

    /**
     * @return A copy of the world.
     */
    public int[][] getWorld() {
        return world;
    }

    /**
     * Overrides current world with a custom world.
     * @param custom_world New world.
     */
    public void setWorld(int[][] custom_world) {
        world = custom_world;
    }

    /**
     * Sets all states to a default value of 1.
     */
    public void setOnes() {
        setState(1);
    }

    // CHECK THIS METHOD
    public void reset() {
        setState(DEFAULT_STATE);
    }

    /**
     * @param x Cell's x-coordinate.
     * @param y Cell's y-coordinate.
     * @param width Width of the neighbours matrix.
     * @param height Height of the neighbours matrix.
     * @return Grid containing nxm adjacent cells from a given cell.
     */
    public Grid getNeighboursGrid(int x, int y, int width, int height) {
        if (width % 2 == 0 || height % 2 == 0) {
            System.out.println("Neighbour matrix dimensions must be odd!");
        }

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        Grid neighboursGrid = new Grid(width, height);

        int i = 0;
        int j = 0;

        for (int k = -halfHeight; k < height - halfHeight; k++) {
            for (int l = -halfWidth; l < width - halfWidth; l++) {
                try {
                    neighboursGrid.setState(j, i, world[y + k][x + l]);
                } catch (Exception e) {
                    neighboursGrid.setState(j, i, DEFAULT_STATE);
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
     * @param x Cell's x-coordinate.
     * @param y Cell's y-coordinate.
     * @param width Width of the neighbours matrix.
     * @param height Height of the neighbours matrix.
     * @return The sum of all the nxm adjacent values from a given cell.
     */
    public int getNeighbours(int x, int y, int width, int height) {
        Grid neighboursGrid = getNeighboursGrid(x, y, width, height);

        int total = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                total += neighboursGrid.getState(j, i);
            }
        }

        return total - getState(x, y); // discards central cell as neighbour
    }

    /**
     * Default use for getting the sum of immediate neighbours from a given cell.
     * @param x Cell's x-coordinate.
     * @param y Cell's y-coordinate.
     * @return The sum of all 3x3 adjacent values from a given cell.
     */
    public int getNeighbours(int x, int y) {
        return getNeighbours(x, y, 3, 3);
    }

    /**
     * Overrides the toString() method in order to print this Grid in a nice format.
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

    /**
     * @return Hashcode of this object.
     */
    public String hash() {
        return Integer.toHexString(this.hashCode());
    }
}