import AutoGrid.*;


public class AutoGridMain {
    public static void main(String[] args) {
        Grid g = new Grid(15, 20);
        g.setRandom(-18, 18);
        System.out.println(g);
        System.out.println(g.mean());
        System.out.println(g.median());
    }
}