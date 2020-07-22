import AutoGrid.*;


public class AutoGridMain {
    public static void main(String[] args) {
        Grid g = new Grid(15, 20);
        g.setRandom(0, 200);
        System.out.println(g);
        System.out.println(g.getNeighbours(12, 12, 3, 3));
    }
}