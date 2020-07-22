import AutoGrid.*;


public class AutoGridMain {
    public static void main(String[] args) {
        Grid g = new Grid(15, 20);
        System.out.println(g);
        g.setState(6, 6, 1);
        g.setState(6, 7, 1);
        g.setState(6, 8, 1);
        System.out.println(g);
        Simulation sim = new Simulation(g, "GameOfLife");
        sim.step();
        System.out.println(g);
    }
}