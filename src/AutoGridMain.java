import AutoGrid.*;


public class AutoGridMain {
    public static void main(String[] args) {
        Grid g = new Grid(20, 1);
        g.setOnes();

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