import AutoGrid.*;


public class AutoGridMain {
    public static void main(String[] args) {
        Grid g = new Grid(20, 1);
        g.setState(1, 0, 1);
        g.setState(2, 0, 1);
        g.setState(3, 0, 1);
        g.setState(5, 0, 1);
        g.setState(6, 0, 1);
        g.setState(8, 0, 1);
        g.setState(10, 0, 1);
        g.setState(12, 0, 1);
        g.setState(14, 0, 1);
        g.setState(17, 0, 1);

        System.out.println(g);
        Simulation sim = new Simulation(g, "OneDimensional");
        sim.step();
        System.out.println(g);
        sim.step();
        System.out.println(g);
        sim.step();
        System.out.println(g);
        sim.step();
        System.out.println(g);
        sim.step();
        System.out.println(g);
        sim.step();
        System.out.println(g);
        sim.step();
        System.out.println(g);
        sim.step();
        System.out.println(g);
    }
}