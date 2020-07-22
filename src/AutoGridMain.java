import AutoGrid.Grid;
import AutoGrid.Simulation;


public class AutoGridMain {
    public static void main(String[] args) {
        Grid g = new Grid(10, 10);
        System.out.println("Grid has these parameters:");
        System.out.println("width= " + g.getWidth());
        System.out.println("height= " + g.getHeight());

        g.setState(5, 4, 1);
        g.setState(4, 5, 1);
        g.setState(4, 4, 1);
        g.setState(5, 5, 1);
        g.setState(6, 4, 1);
        g.setState(3, 5, 1);

        System.out.println("This is the grid's world:");
        System.out.println(g);
        System.out.println(g.getNeighboursGrid(5, 5, 3, 3));
        System.out.println(g.getNeighbours(5, 5, 3, 3));

        Simulation sim = new Simulation(g, "GameOfLife");
        sim.step();
        sim.show();
    }
}