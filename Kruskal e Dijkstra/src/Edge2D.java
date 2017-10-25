
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputListener;

/**
 * @author daniel e iuri
 */
public class Edge2D extends JComponent implements MouseInputListener, ActionListener {

    protected Node2D from, to;
    protected Color myColor;
    protected Line2D line;
    protected String label;
    protected JPopupMenu popup;
    protected HashMap attributes;
    protected int D = 2;
    protected String[] visibleAttributes;

    public Edge2D(Node2D f, Node2D t) {
        from = f;
        to = t;
        label = "Aresta de " + f.getId() + " para " + t.getId();
        line = new Line2D.Double(0, 0, 0, 0);
        popup = new JPopupMenu("Editar Aresta");
        attributes = new HashMap();
        visibleAttributes = null;
        recreatePopUpItems();

        myColor = Color.BLACK;

        this.setLocation(Math.min(from.getX(), to.getX()) + Node2D.SIZE / 2 - D,
                Math.min(from.getY(), to.getY()) + Node2D.SIZE / 2 - D);

        this.setSize(Math.abs(from.getX() - to.getX()) + 2 * D, Math.abs(from.getY() - to.getY()) + 2 * D);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setColor(Color c) {
        myColor = c;
        this.repaint();
    }

    public Color getColor() {
        return myColor;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String l) {
        label = l;
    }

    public Node2D getFrom() {
        return from;
    }

    public String[] getAttributesNames() {
        Iterator i = attributes.keySet().iterator();
        String[] res = new String[attributes.keySet().size()];
        int cont = 0;
        while (i.hasNext()) {
            res[cont++] = (String) i.next();
        }
        return res;
    }

    public HashMap getAttributesHash() {
        return attributes;
    }

    public void setAttributesHash(HashMap h) {
        attributes = new HashMap(h);
        recreatePopUpItems();
    }

    public Node2D getTo() {
        return to;
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
            throw new Exception("Atributo 'Peso' não encontrado");
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

    public synchronized boolean contains(int x, int y) {
        int newX = x - 10, newY = y - 10, w = 20, h = 20;
        return line.intersects(newX, newY, w, h);
    }

    public void paintComponent(Graphics g) {
        g.setColor(myColor);
        Graphics2D g2d = (Graphics2D) g;

        int inicio[] = {from.getX(), from.getY()};
        int fim[] = {to.getX(), to.getY()};
        this.setLocation(Math.min(from.getX(), to.getX()) + Node2D.SIZE / 2 - D,
                Math.min(from.getY(), to.getY()) + Node2D.SIZE / 2 - D);
        this.setSize(Math.abs(from.getX() - to.getX()) + 2 * D, Math.abs(from.getY() - to.getY()) + 2 * D);

        int w = getWidth();
        int h = getHeight();
        if (inicio[0] >= fim[0] && inicio[1] >= fim[1]) {
            line = new Line2D.Double(w - D, h - D, D, D);
        } else if (inicio[0] >= fim[0] && inicio[1] < fim[1]) {
            line = new Line2D.Double(w - D, D, D, h - D);
        } else if (inicio[0] < fim[0] && inicio[1] >= fim[1]) {
            line = new Line2D.Double(D, h - D, w - D, D);
        } else if (inicio[0] < fim[0] && inicio[1] < fim[1]) {
            line = new Line2D.Double(D, D, w - D, h - D);
        }

        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.draw(line);
    }

    public boolean hasAttribute(String a) {
        return attributes.containsKey(a.toLowerCase());
    }

    /**
     *
     * @param a Atributo a ser consultado.
     * @return Valor do atributo <i>a</i>, ou NaN se nao existir.
     */
    public double getAttribute(String a) {
        a = a.toLowerCase();
        if (attributes.containsKey(a)) {
            return ((Double) attributes.get(a)).doubleValue();
        }
        System.err.println("Edge2D.getAttribute: Atributo '" + a + "' nao encontrado!");
        return Double.NaN;
    }

    /**
     * Define um atributo
     *
     * @param a Atributo a ser definido
     * @param v O valor do atributo.
     */
    public void setAttribute(String a, double v) {
        attributes.put(a.toLowerCase(), new Double(v));
    }

    public void mouseClicked(MouseEvent arg0) {
        checkPopUp(arg0);
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
        checkPopUp(arg0);
    }

    public void mouseReleased(MouseEvent arg0) {
        checkPopUp(arg0);
    }

    public void mouseDragged(MouseEvent arg0) {
    }

    public void mouseMoved(MouseEvent arg0) {

    }

    /* cria os itens do popup menu */
    public void recreatePopUpItems() {

        popup.removeAll();
        JMenuItem item;

        /* o usuario nao pode editar os labels das arestas */
        /*item = new JMenuItem("Editar Label");
         item.addActionListener(this);
         popup.add(item);*/
        if (visibleAttributes == null) {
            return;
        }
        for (int i = 0; i < visibleAttributes.length; i++) {
            if (attributes.containsKey(visibleAttributes[i].toLowerCase())) {
                item = new JMenuItem(visibleAttributes[i]);
                item.addActionListener(this);
                popup.add(item);
            }
        }
    }

    public void setVisibleAttributes(String[] menuNames) {
        visibleAttributes = menuNames;
    }

    private void checkPopUp(MouseEvent e) {
        popup.show(e.getComponent(), e.getX(), e.getY());
    }

    /* TODO deve haver uma ação de perguntar o valor para cada atributo */
    public void actionPerformed(ActionEvent arg0) {
        String comando = arg0.getActionCommand();
        String newatt = null;
        try {
            newatt = JOptionPane.showInputDialog(null, "Digite o novo valor " + comando + " da aresta", Double.toString(this.getWeight()));
            double natt = Double.parseDouble(newatt);
            this.setAttribute(comando, natt);
            getParent().repaint();
        } catch (Exception e) {
            System.out.println("Edge2D.actionPerformed() " + e.toString());
        }
    }
}
