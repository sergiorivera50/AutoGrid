import AutoGrid.*;
import AutoGrid.Rules.GameOfLife;
import AutoGrid.Rules.Rule;


public class Application {
    public static void main(String[] args) {
        Grid g = new Grid(20, 20);
        g.setOnes();
        System.out.println(g);

        Rule rules = new GameOfLife();
        Simulation sim = new Simulation(g, rules);
        sim.simulate(5, true);
    }
}