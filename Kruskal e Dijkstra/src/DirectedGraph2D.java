
import java.awt.Color;
import java.util.Iterator;

/**
 * @author daniel
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DirectedGraph2D extends Graph2D {

    public DirectedGraph2D() {
        super();
    }

    public void addDirectedEdge2D(DirectedEdge2D e) {
        edgesArray.add(e);
        add(e);
    }

    public DirectedEdge2D getDirectedEdge2D(int i) {
        return (DirectedEdge2D) edgesArray.get(i);
    }

    /**
     * @return Retorna um Directed Graph como Graph (classe pai), voce no
     * recebimento deste metodo deve fazer um cast para DirectedGraph.
     */
    /* deve setar todos os atributos do grafo */
    public Graph generateGraph() {
        int numEdges = 0;
        DirectedGraph g = new DirectedGraph(numNodes);
        g.addEdgeListener(this);
        g.addNodeListener(this);
        Iterator i = edgesArray.iterator();
        while (i.hasNext()) {
            DirectedEdge2D e = (DirectedEdge2D) i.next();

            g.addEdge(e.getFrom().getId(), e.getTo().getId());
            Edge edge = g.getEdge(e.getFrom().getId(), e.getTo().getId());
            String[] keys = e.getAttributesNames();
            for (int j = 0; j < keys.length; j++) {
                edge.setAttribute(keys[j].toLowerCase(), e.getAttribute(keys[j].toLowerCase()));
            }

            g.nodes[e.getFrom().getId()].setType(e.getFrom().getType());
            g.nodes[e.getTo().getId()].setType(e.getTo().getType());
        }
        return (Graph) g;
    }

    public synchronized void node2DClicked(Node2D node) {
        if (firstClick) {
            /* seleciona o nó inicial da aresta */
            firstNode = node;
            firstNode.setColor(Color.BLUE);
            firstClick = false;
        } else {
            firstNode.setColor(Color.BLACK);
            /* TODO precisa existir a possibilidade de existirem arestas para o próprio nó (laços)
             * TODO não pode haver duas arestas entre os mesmos nós
             */
            /* cria aresta se o nó de origem for diferente do destino e não existir aresta entre os nós*/
            if (firstNode.getId() != node.getId()) {
                e = new DirectedEdge2D(firstNode, node);

                if (defaultAtributes != null) {
                    String[] att = new String[defaultAtributes.size()];
                    Iterator i = defaultAtributes.keySet().iterator();
                    int cont = 0;
                    while (i.hasNext()) {
                        att[cont++] = (String) i.next();
                    }
                    e.setVisibleAttributes(att);
                    e.setAttributesHash(defaultAtributes);
                }

                addEdge2D(e);

            }
            firstClick = true;
        }
        repaint();
    }
}
