import AutoGrid.*;


public class Application {
    public static void main(String[] args) {
        Grid g = new Grid(20, 1);
        g.setOnes();

        System.out.println(g);
        Simulation sim = new Simulation(g, "OneDimensional");
        sim.simulate(5, true);
    }
}