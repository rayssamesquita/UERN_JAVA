/* Implementação do Algoritmo de Prim para o problema da Árvore Geradora Mínima(AGM) */

/* importa a classe do java que trata das cores */
import java.awt.Color;

/* a classe Prim implementa a interface Algoritmo, logo tem que definir os métodos da interface */
public class Prim implements Algoritmo {

    private Graph graph;
    private boolean solution[];
    private int numvertices;
    private Node[] nodes;
    private int numNodesSolution;
    private Edge menor;

    /* Recebe o grafo e armazena em graph. É obrigatório implementar este método */
    public void setGraph(Graph g) {
        /* Aqui o programador recebe o objeto grafo e faz as inicializações do algoritmo */
        graph = g;

        /* Solucao */
        solution = new boolean[graph.getNumNodes()];
        numvertices = 1;

        /* Pegar todos os vertices */
        nodes = graph.getNodes();
    }

    /* Executa complementamente o algoritmo. É obrigatório implementar este método */
    public void execute() {
        for (startStep(); hasNextStep(); nextStep());
    }

    /* Inicializa o modo passo-a-passo. É obrigatório implementar esse método */
    public void startStep() {
        numNodesSolution = 1;

        /* Pegar um aresta qualquer */
        Node n = nodes[0];
        solution[n.getId()] = true;
    }

    /* Verifica se ainda existe um passo a ser dado no modo passo-a-passo. É obrigatório implementar esse método */
    public boolean hasNextStep() {
        if (numNodesSolution >= graph.getNumNodes()) {
            return false;
        }
        return true;
    }

    /* Executa um passo no modo passo-a-passo. É obrigatório implementar esse método */
    public void nextStep() {
        /* menor aresta que incide em nós que estão na solução */
        menor = null;
        for (int i = 0; i < graph.getNumNodes(); i++) {
            /* busca as arestas somente dos nós que estão na solução */
            if (!solution[i]) {
                continue;
            }

            Edge[] arestasIncidentes = graph.getEdges(graph.getNode(i));
            for (int j = 0; j < arestasIncidentes.length; j++) {
                if (menor == null || arestasIncidentes[j].getAttribute("Peso") < menor.getAttribute("Peso")) {
                    if (!solution[arestasIncidentes[j].getFrom().getId()] || !solution[arestasIncidentes[j].getTo().getId()]) {
                        menor = arestasIncidentes[j];
                    }
                }
            }
        }

        menor.setColor(Color.RED);
        if (!solution[menor.getFrom().getId()]) {
            numNodesSolution += 1;
        }
        if (!solution[menor.getTo().getId()]) {
            numNodesSolution += 1;
        }
        solution[menor.getFrom().getId()] = solution[menor.getTo().getId()] = true;

        graph.writeMessage("Adicionei a aresta entre " + menor.getFrom().getId() + " e " + menor.getTo().getId());
        if (!hasNextStep()) {
            graph.writeMessage("Fim.");
        }
    }

    public boolean validateGraph() {
        /* fazer busca em largura e ver se todos os nós estão conectados */
        if (graph.getNumNodes() <= 0) {
            return false;
        }
        if (graph instanceof DirectedGraph) {
            return false;
        }
        return true;
    }

    public String getAuthor() {
        return new String("Daniel e Iuri");
    }

    public String getAuthorEmail() {
        return new String("{danrocha,iuri}@lcc.ufrn.br");
    }

    public String getAlgorithmName() {
        return new String("Prim");
    }

    public String getAlgorithmComplexity() {
        return new String("O(n^3)");
    }

    public String getAlgorithmDescription() {
        return new String("Algoritmo que encontra a árvore geradora mínima do grafo utilizando o algoritmo de Prim.");
    }

    public String[] getEdgeAttributes() {
        String[] res = new String[1];
        res[0] = new String("Peso");
        return res;
    }

    public String[] getNodeTypes() {
        return null;
    }

    public double getDefaultValueAttribute(String attribute) {
        return 1.0;
    }

    public String getVisibleAttribute() {
        return new String("Peso");
    }
}
