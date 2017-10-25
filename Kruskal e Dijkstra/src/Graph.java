
import java.util.HashMap;
import java.util.Vector;

/**
 * @author Daniel e Iuri
 */
public class Graph {

    protected int numNodes, numEdges;
    protected Edge adjMatrix[][];
    protected Node nodes[];
    protected String message;
    protected GraphListener listener;
    protected EdgeListener edgelistener;
    protected HashMap edgesAttributes;

    /**
     * Constrói um grafo com <i>n</i> nós.
     *
     * @param n Número de nós do grafo.
     */
    public Graph(int n) {
        numNodes = n;
        numEdges = 0;
        adjMatrix = new Edge[n][n];
        nodes = new Node[n];

        listener = null;
        edgelistener = null;
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
    }

    /**
     * @return O número de nós do grafo.
     */
    public int getNumNodes() {
        return numNodes;
    }

    /**
     * Adiciona um objeto que vai escutar os sinais emitidos pelo grafo.
     *
     * @param l Objeto do tipo GraphListener
     * @see GraphListener
     */
    public void addGraphListener(GraphListener l) {
        listener = l;
    }

    /**
     * Adiciona um objeto que vai escutar os sinais emitidos pelos nós do grafo.
     *
     * @param l Objeto do tipo NodeListener
     * @see NodeListener
     */
    public void addNodeListener(NodeListener l) {
        for (int i = 0; i < numNodes; i++) {
            nodes[i].addNodeListener(l);
        }
    }

    /**
     * Adiciona um objeto que vai escutar os sinais emitidos pelas arestas do
     * grafo.
     *
     * @param l Objeto do tipo EdgeListener
     * @see EdgeListener
     */
    public void addEdgeListener(EdgeListener l) {
        edgelistener = l;
    }

    /**
     * Adiciona uma aresta de peso <i>w</i> entre os nós de id <i>id1</i> e
     * <i>id2</i> do grafo
     *
     * @param id1 ID de um dos nós
     * @param id2 ID do outro nó
     */
    public void addEdge(int id1, int id2) {

        adjMatrix[Math.min(id1, id2)][Math.max(id1, id2)] = new Edge(nodes[Math.min(id1, id2)], nodes[Math.max(id1, id2)], numEdges);
        if (edgelistener != null) {
            adjMatrix[Math.min(id1, id2)][Math.max(id1, id2)].addEdgeListener(edgelistener);
        }
        numEdges++;
    }

    /**
     * Retorna um nó do grafo pelo seu ID
     *
     * @param id ID do nó desejado
     * @return Objeto do tipo nó cujo ID seja passado
     */
    public Node getNode(int id) {
        return nodes[id];
    }

    /**
     * Retorna todos os nós do grafo em um vetor.
     *
     * @return Retorna um vetor dos nós do grafo
     */
    /* TODO: conferir se o retorno abaixo é seguro */
    public Node[] getNodes() {
        return nodes;
    }

    /**
     * Retorna uma aresta, dados os dois objetos Node terminais.
     *
     * @param n1 Um dos nós da aresta
     * @param n2 O outro nó da aresta
     * @return O objeto Edge em questão
     */
    public Edge getEdge(Node n1, Node n2) {
        return adjMatrix[Math.min(n1.getId(), n2.getId())][Math.max(n1.getId(), n2.getId())];
    }

    /**
     * Retorna uma aresta, dados os dois IDs dos nós terminais
     *
     * @param id1 O ID de um dos nós da aresta
     * @param id2 O ID do outro nó da aresta
     * @return O objeto Edge em questão
     */
    public Edge getEdge(int id1, int id2) {
        return adjMatrix[Math.min(id1, id2)][Math.max(id1, id2)];
    }

    /* TODO: qual o erro do código abaixo?
     public Edge[] getEdges(int id) {
     Vector retorno = new Vector();
     for (int i=0;i<numNodes;i++)
     if (adjMatrix[id][i] != null)
     retorno.add(adjMatrix[id][i]);
     return (Edge[]) retorno.toArray();
     } 
	
     public Edge[] getEdges() {
     Vector retorno = new Vector();
     for (int i=0;i<numNodes;i++)
     for (int j=i;j<numNodes;j++)
     if (adjMatrix[i][j] != null)
     retorno.add(adjMatrix[i][j]);
     return (Edge[]) retorno.toArray();
     }*/
    /**
     * Retorna as arestas que incidem em um no de ID <i> id </i>
     *
     * @param id O ID do nó desejado.
     * @return Um array de objetos Edge.
     */
    public final Edge[] getEdges(int id) {
        Vector res = new Vector();
        for (int i = 0; i < numNodes; i++) {
            if (adjMatrix[Math.min(id, i)][Math.max(id, i)] != null) {
                res.add(adjMatrix[Math.min(id, i)][Math.max(id, i)]);
            }
        }
        return (Edge[]) res.toArray(new Edge[0]);

        /*int n = 0;
         for (int i=0;i<numNodes;i++)
         if (adjMatrix[Math.min(id,i)][Math.max(id,i)] != null) n++;
			
         Edge[] r = new Edge[n];
         n = 0;
         for (int i=0;i<numNodes;i++)
         if (adjMatrix[Math.min(id,i)][Math.max(id,i)] != null) r[n++] = adjMatrix[Math.min(id,i)][Math.max(id,i)];
		
         return r;*/
    }

    /**
     * Retorna as arestas que incidem em um no <i> n </i>
     *
     * @param n O objeto referenciando o nó desejado
     * @return Um array de objetos Edge com as arestas que incidem em n
     */
    public final Edge[] getEdges(Node n) {
        return this.getEdges(n.getId());
    }

    /**
     * Retorna todas arestas do grafo.
     *
     * @return Um array de objetos Edge.
     */
    public Edge[] getEdges() {
        Vector res = new Vector();
        for (int i = 0; i < numNodes; i++) {
            for (int j = i; j < numNodes; j++) {
                if (adjMatrix[i][j] != null) {
                    res.add(adjMatrix[i][j]);
                }
            }
        }
        return (Edge[]) res.toArray(new Edge[0]);
        /*
         int n = 0;
         for (int i=0;i<numNodes;i++)
         for (int j=i;j<numNodes;j++)
         if (adjMatrix[i][j] != null)
         n++;
				
         Edge[] r = new Edge[n];
         n = 0;
         for (int i=0;i<numNodes;i++)
         for (int j=i;j<numNodes;j++)
         if (adjMatrix[i][j] != null) r[n++] = adjMatrix[i][j];
		
         return r;*/
    }

    /**
     * Escreve uma mensagem para o usuário
     *
     * @param s A mensagem para ser escrita para o usuário
     */
    public synchronized void writeMessage(String s) {
        if (listener != null) {
            listener.setMessageEvent(new MessageEvent(this, s));
        }
        message = s;
    }
}
