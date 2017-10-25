
import java.util.Vector;

/**
 * @author daniel
 */
public class DirectedGraph extends Graph {

    /**
     * @param n n
     */
    public DirectedGraph(int n) {
        super(n);
    }

    public void addEdge(int idFrom, int idTo) {
        adjMatrix[idFrom][idTo] = new Edge(nodes[idFrom], nodes[idTo], numEdges++);
        if (edgelistener != null) {
            adjMatrix[idFrom][idTo].addEdgeListener(edgelistener);
        }
    }

    /**
     * Retorna uma aresta, dados os dois objetos Node terminais.
     *
     * @param n1 Um dos nós da aresta
     * @param n2 O outro nó da aresta
     * @return O objeto Edge em questão
     */
    public Edge getEdge(Node n1, Node n2) {
        return adjMatrix[n1.getId()][n2.getId()];
    }

    /**
     * Retorna uma aresta, dados os dois IDs dos nós terminais
     *
     * @param id1 O ID de um dos nós da aresta
     * @param id2 O ID do outro nó da aresta
     * @return O objeto Edge em questão
     */
    public Edge getEdge(int id1, int id2) {
        return adjMatrix[id1][id2];
    }

    public Edge[] getEdgesIn(int id) {
        Vector res = new Vector();
        for (int i = 0; i < numNodes; i++) {
            if (adjMatrix[i][id] != null) {
                res.add(adjMatrix[i][id]);
            }
        }
        return (Edge[]) res.toArray(new Edge[0]);
    }

    public Edge[] getEdgesOut(int id) {
        Vector res = new Vector();
        for (int i = 0; i < numNodes; i++) {
            if (adjMatrix[id][i] != null) {
                res.add(adjMatrix[id][i]);
            }
        }
        return (Edge[]) res.toArray(new Edge[0]);
    }

    /**
     * Retorna todas arestas do grafo.
     *
     * @return Um array de objetos Edge.
     */
    public Edge[] getEdges() {
        Vector res = new Vector();
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (adjMatrix[i][j] != null) {
                    res.add(adjMatrix[i][j]);
                }
            }
        }
        return (Edge[]) res.toArray(new Edge[0]);
    }
}
