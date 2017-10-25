
public class Grafinho {

    public static void main(String args[]) {
        Graph g;
        InterfaceGrafica i = new InterfaceGrafica();
        i.setAlgoritmo(new Prim());
    }
}
