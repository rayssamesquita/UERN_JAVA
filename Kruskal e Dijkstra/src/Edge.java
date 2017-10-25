
/**
 * @author Daniel e Iuri
 */
import java.awt.Color;
import java.util.HashMap;

public class Edge {

    private Node from, to;
    private Color color;
    private String label;
    private EdgeListener listener;
    private int id;
    HashMap attributes;

    /**
     * Cria uma aresta de f para t
     *
     * @param f Nó de origem.
     * @param t Nó de destino.
     * @param i id
     */
    public Edge(Node f, Node t, int i) {
        listener = null;
        from = f;
        to = t;
        attributes = new HashMap();
        //weight = w;
        id = i;
        color = Color.BLACK;
    }

    /**
     * Adiciona um escutador de eventos da aresta
     *
     * @param l Objeto EdgeListener que irá escutar os eventos da aresta
     */
    public void addEdgeListener(EdgeListener l) {
        listener = l;
    }

    /**
     * As arestas são identificadas por números, que são utilizadas somente pela
     * interface. Esse número não está relacionado com a matriz de adjacência,
     * nem com nada do grafo. Não deve ser utilizado nos algoritmos.
     *
     * @return Retorna o identificador da aresta
     */
    public int getId() {
        return id;
    }

    /**
     * @return Retorna a cor.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param c Seta a cor da aresta
     */
    public void setColor(Color c) {
        if (listener != null) {
            listener.setColorEvent(new ColorEvent(this, c));
        }
        this.color = c;
    }

    /**
     * @return Retorna o label da aresta.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label O rótulo a ser colocado na aresta
     */
    public void setLabel(String label) {
        if (listener != null) {
            listener.setLabelEvent(new LabelEvent(this, label));
        }
        this.label = label;
    }

    /**
     * @deprecated Agora, deve-se utilizar getAttributes("atributo").
     * Atualmente, retorna o valor do atributo "Peso", mas deve sumir nas
     * versões futuras.
     * @throws Exception sem atributo peso
     * @return Retorna o peso da aresta
     */
    public double getWeight() throws Exception {
        if (attributes.containsKey("peso")) {
            return getAttribute("peso");
        } else {
            throw new Exception("Atributo 'Peso' nao encontrado");
        }
    }

    /**
     * @deprecated Agora, deve-se utilizar setAttributes("atributo",valor).
     * Atualmente, seta o valor do atributo "Peso", mas deve sumir nas versões
     * futuras.
     * @param weight O peso da aresta
     */
    public void setWeight(double weight) {
        this.setAttribute("peso", weight);
    }

    /**
     * @return Retorna o nó de saída da aresta
     */
    public Node getFrom() {
        return from;
    }

    /**
     * @return Retorna o nó de chegada da aresta
     */
    public Node getTo() {
        return to;
    }

    /**
     *
     * @param a Atributo a ser consultado.
     * @return Valor do atributo <i>a</i>.
     */
    public double getAttribute(String a) {
        a = a.toLowerCase();
        if (attributes.containsKey(a)) {
            return ((Double) attributes.get(a)).doubleValue();
        }
        System.err.println("Edge.getAttribute: Atributo '" + a + "' não encontrado!");
        return -1;
    }

    /**
     * Define um atributo
     *
     * @param a Atributo a ser definido
     * @param v O valor do atributo.
     */
    public void setAttribute(String a, double v) {
        if (listener != null) {
            listener.setAttributeEvent(new AttributeEvent(this, a.toLowerCase(), v));
        }
        attributes.put(a.toLowerCase(), new Double(v));
    }
}
