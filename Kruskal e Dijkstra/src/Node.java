
/**
 * @author Daniel e Iuri
 */

import java.awt.Color;

public class Node {

    private Color color;
    private int id;
    private String label;
    private NodeListener listener;
    private String nodeType;
    private String[] nodeTypes;

    public Node(int i) {
        listener = null;
        id = i;
        color = Color.BLACK;
        label = "No " + id;
    }

    /**
     * Adiciona um escutador de eventos ao nó.
     *
     * @param l Objeto EdgeListener que irá escutar os eventos do nó
     */
    public void addNodeListener(NodeListener l) {
        listener = l;
    }

    /**
     * @return Retorna a cor.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color A cor a ser colocada no nó
     */
    public void setColor(Color color) {
        if (listener != null) {
            listener.setColorEvent(new ColorEvent(this, color));
        }
        this.color = color;
    }

    /**
     * @return Retorna o identificador do nó no grafo
     */
    public int getId() {
        return id;
    }

    /**
     * @return Retorna o rótulo do grafo
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label O rótulo que o grafo irá receber
     */
    public void setLabel(String label) {
        if (listener != null) {
            listener.setLabelEvent(new LabelEvent(this, label));
        }
        this.label = label;
    }

    /**
     * @param t Tipo de nó
     *
     */
    public void setType(String t) {
        nodeType = t;
    }

    /**
     * @return Qual o tipo de nó
     */

    public String getType() {
        return nodeType;
    }

    public void setNodeTypes(String[] s) {
        nodeTypes = s;
    }

    public String[] getNodeTypes() {
        return nodeTypes;
    }
}
