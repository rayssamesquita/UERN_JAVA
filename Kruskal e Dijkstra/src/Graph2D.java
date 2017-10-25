
import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Daniel e Iuri
 */
public class Graph2D extends JComponent implements MouseInputListener, NodeListener, EdgeListener {

    protected int numNodes = 0;
    protected Node2D n, firstNode;
    protected Edge2D e;
    protected Vector edgesArray, nodesArray;
    protected boolean firstClick = true;
    protected String visibleAttribute;
    protected String[] nodeTypes;
    protected HashMap defaultAtributes;
    protected MouseInputListener edge2DMouseInputListener;
    protected MouseInputListener node2DMouseInputListener;
    /* guardar copia */
    private Vector corArestas, labelArestas, corNos, atributosArestas;

    /**
     * @return Returns the edge2DMouseInputListener.
     */
    public MouseInputListener getEdge2DMouseInputListener() {
        return edge2DMouseInputListener;
    }

    /**
     * @param edge2DMouseInputListener The edge2DMouseInputListener to set.
     */
    public void setEdge2DMouseInputListener(
            MouseInputListener edge2DMouseInputListener) {
        this.edge2DMouseInputListener = edge2DMouseInputListener;
    }

    /**
     * @return Returns the node2DMouseInputListener.
     */
    public MouseInputListener getNode2DMouseInputListener() {
        return node2DMouseInputListener;
    }

    /**
     * @param node2DMouseInputListener The node2DMouseInputListener to set.
     */
    public void setNode2DMouseInputListener(
            MouseInputListener node2DMouseInputListener) {
        this.node2DMouseInputListener = node2DMouseInputListener;
    }

    public Graph2D() {
        edgesArray = new Vector();
        nodesArray = new Vector();
        visibleAttribute = null;
        defaultAtributes = null;
        this.setLayout(null);
    }

    /**
     * @param defaultAtributes The defaultAtributes to set.
     */
    public void setDefaultAtributes(HashMap defaultAtributes) {
        this.defaultAtributes = new HashMap(defaultAtributes);
    }

    public void addEdge2D(Edge2D e) {
        if (edge2DMouseInputListener != null) {
            e.addMouseMotionListener(edge2DMouseInputListener);
            e.addMouseListener(edge2DMouseInputListener);
        }
        edgesArray.add(e);
        add(e);
    }

    public Node2D getNode2D(int i) {
        return (Node2D) nodesArray.get(i);
    }

    public Edge2D getEdge2D(int i) {
        return (Edge2D) edgesArray.get(i);
    }

    public int getNumNodes() {
        return nodesArray.size();
    }

    public int getNumEdges() {
        return edgesArray.size();
    }

    public void addNode2D(Node2D n) {
        if (node2DMouseInputListener != null) {
            n.addMouseMotionListener(node2DMouseInputListener);
            n.addMouseListener(node2DMouseInputListener);
        }
        nodesArray.add(n);
        add(n);
        numNodes++;
    }

    public void paint(Graphics g) {

        /* FIXME deve haver alguma maneira de deixar o fundo branco que não seja assim */
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.paint(g);

        /* DESENHA O LABEL DAS ARESTAS */
        g.setColor(Color.BLACK);
        Iterator i = edgesArray.iterator();
        while (i.hasNext() && getVisibleAttribute() != null) {
            Edge2D e = (Edge2D) i.next();

            /* nao imprime xx.xx se o numero for inteiro */
            String peso;
            double dpeso = 0.0;
            try {
                dpeso = e.getAttribute(getVisibleAttribute());
            } catch (Exception ex) {
                System.out.println("Graph2D.paint() : " + ex.toString());
            }

            if (dpeso - (int) dpeso == 0) {
                int ipeso = (int) dpeso;
                peso = Integer.toString(ipeso);
            } else {
                peso = Double.toString(dpeso);
            }

            g.setColor(Color.RED);
            g.setFont(new Font("Lucida Sans", Font.BOLD, 14));

            g.drawString(peso, e.getX() + e.getWidth() / 2, e.getY() + e.getHeight() / 2);
        }
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
                //String strpeso = JOptionPane.showInputDialog(null,"Digite o peso da aresta","1.0");
                e = new Edge2D(firstNode, node);

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

    public void makeCopy() {

        atributosArestas = new Vector();
        corArestas = new Vector();
        labelArestas = new Vector();
        corNos = new Vector();

        if (firstNode != null) {
            firstNode.setColor(Color.BLACK);
        }
        firstClick = true;

        Iterator i = edgesArray.iterator();
        while (i.hasNext()) {
            Edge2D e = (Edge2D) i.next();

            corArestas.add(new Color(e.getColor().getRGB()));
            atributosArestas.add(new HashMap(e.getAttributesHash()));
            labelArestas.add(new String(e.getLabel().toCharArray()));
        }

        i = nodesArray.iterator();
        while (i.hasNext()) {
            Node2D n = (Node2D) i.next();
            corNos.add(new Color(n.getColor().getRGB()));
        }
    }

    public void restoreCopy() {
        /* se existe uma cópia a ser restaurada */
        if (atributosArestas != null) {
            Iterator i = edgesArray.iterator();
            int j = 0;
            while (i.hasNext()) {
                Edge2D e = (Edge2D) i.next();
                e.setColor((Color) corArestas.get(j));
                e.setAttributesHash((HashMap) atributosArestas.get(j));
                e.setLabel((String) labelArestas.get(j));
                j++;
            }

            i = nodesArray.iterator();
            j = 0;
            while (i.hasNext()) {
                Node2D n = (Node2D) i.next();
                n.setColor((Color) corNos.get(j));
                j++;
            }
        }

        if (firstNode != null) {
            firstNode.setColor(Color.BLACK);
        }
        firstClick = true;
    }

    public Graph generateGraph() {
        int numEdges = 0;
        Graph g = new Graph(numNodes);
        g.addEdgeListener(this);
        g.addNodeListener(this);
        Iterator i = edgesArray.iterator();

        while (i.hasNext()) {
            String[] keys;
            Edge2D e = (Edge2D) i.next();
            g.addEdge(e.getFrom().getId(), e.getTo().getId());
            Edge edge = g.getEdge(e.getFrom().getId(), e.getTo().getId());
            keys = e.getAttributesNames();
            for (int j = 0; j < keys.length; j++) {
                edge.setAttribute(keys[j].toLowerCase(), e.getAttribute(keys[j].toLowerCase()));
            }
            //Set s = e.getAttributes
            g.nodes[e.getFrom().getId()].setType(e.getFrom().getType());
            g.nodes[e.getTo().getId()].setType(e.getTo().getType());
        }

        return g;
    }

    public void unregisterMouse() {
        Iterator i = edgesArray.iterator();
        while (i.hasNext()) {
            Edge2D e = (Edge2D) i.next();
            e.removeMouseListener(e);
            e.removeMouseMotionListener(e);
        }

        i = nodesArray.iterator();
        while (i.hasNext()) {
            Node2D n = (Node2D) i.next();
            n.removeMouseListener(n);
            n.removeMouseMotionListener(n);
        }
    }

    public void registerMouse() {
        Iterator i = edgesArray.iterator();
        while (i.hasNext()) {
            Edge2D e = (Edge2D) i.next();
            e.addMouseListener(e);
            e.addMouseMotionListener(e);
        }

        i = nodesArray.iterator();
        while (i.hasNext()) {
            Node2D n = (Node2D) i.next();
            n.addMouseListener(n);
            n.addMouseMotionListener(n);
        }
    }

    /**
     * @return Returns the visibleAttribute.
     */
    public String getVisibleAttribute() {
        return visibleAttribute;
    }

    /**
     * @param visibleAttribute The visibleAttribute to set.
     */
    public void setVisibleAttribute(String visibleAttribute) {
        this.visibleAttribute = visibleAttribute;
    }

    public String[] getNodeTypes() {
        return nodeTypes;
    }

    public void setNodeTypes(String[] s) {
        nodeTypes = s;
    }

    public void mouseClicked(MouseEvent arg0) {

        if (!(arg0.getButton() == MouseEvent.BUTTON1)) {
            return;
        }

        /* se ja estava pra criar uma aresta e clicou em lugar vazio,
         * volta ao estado anterior
         */
        if (firstClick == false) {
            firstClick = true;
            firstNode.setColor(Color.BLACK);
            return;
        }

        /* cria nova bolinha */
        n = new Node2D(numNodes, arg0.getX() - this.getX(), arg0.getY() - this.getY());
        n.setNodeTypes(getNodeTypes());
        addNode2D(n);
        repaint();
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void mouseDragged(MouseEvent arg0) {
    }

    public void mouseMoved(MouseEvent arg0) {
    }

    public void setColorEvent(ColorEvent e) {
        if (e.getSource() instanceof Node) {
            Node n = (Node) e.getSource();
            ((Node2D) nodesArray.get(n.getId())).setColor(e.getColor());
        } else if (e.getSource() instanceof Edge) {
            Edge ed = (Edge) e.getSource();
            ((Edge2D) edgesArray.get(ed.getId())).setColor(e.getColor());
        }
    }

    public void setLabelEvent(LabelEvent e) {
        if (e.getSource() instanceof Node) {
            Node n = (Node) e.getSource();
            ((Node2D) nodesArray.get(n.getId())).setLabel(e.getLabel());
        } else if (e.getSource() instanceof Edge) {
            Edge ed = (Edge) e.getSource();
            ((Edge2D) edgesArray.get(ed.getId())).setLabel(e.getLabel());
        }
    }

    public void setAttributeEvent(AttributeEvent e) {
        Edge edge = (Edge) e.getSource();
        Edge2D edge2d = (Edge2D) edgesArray.get(edge.getId());
        edge2d.setAttribute(e.getAttributeName(), e.getAttributeValue());
    }

    public void repaintEdges(Node2D n) {
        Iterator i = edgesArray.iterator();
        while (i.hasNext()) {
            Edge2D e = (Edge2D) i.next();
            if (e.getFrom().getId() == n.getId() || e.getTo().getId() == n.getId()) {
                e.repaint();
            }
        }
    }
}
