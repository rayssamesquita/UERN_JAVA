
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputListener;

/**
 * @author daniel e clx
 */
public class Node2D extends JComponent implements MouseInputListener, ActionListener {

    public static final int SIZE = 40;
    private final int FONTSIZE = 20;
    private Color myColor;
    private Object container;
    private JPopupMenu popup;
    private String type;
    private String[] nodeTypes;
    private int id;
    private String label;

    private int tempX, tempY;

    public Node2D(int i, int x, int y) {
        id = i;
        this.setSize(SIZE + 1, SIZE + 1);
        this.setLocation(x - SIZE / 2, y - SIZE / 2);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        type = new String("Normal");
        myColor = Color.BLACK;
        popup = new JPopupMenu("Editar Aresta");
        recreatePopUpItems();
        this.label = "No " + id;

    }

    public void setColor(Color c) {
        myColor = c;
        this.repaint();
    }

    public Color getColor() {
        return myColor;
    }

    public int getId() {
        return id;
    }

    public void setNodeTypes(String[] s) {
        nodeTypes = s;
    }

    public String[] getNodeTypes() {
        return nodeTypes;
    }

    public void setType(String s) {
        type = s;
    }

    public String getType() {
        return type;
    }

    public void paintComponent(Graphics g) {
        /* LEMBRAR
         * O desenho dos componentes é sempre relativo ao canto superior
         * esquerdo DO COMPONENTE, não do pai!
         */
        //System.out.println("Desenhei a bolinha");
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /* Tipo */
        Color c = null;
        if (type.equals("Normal")) {
            c = myColor;
        } else {
            c = Color.GREEN;
        }

        g2d.setColor(c);
        g2d.fillOval(0, 0, SIZE, SIZE);

        g2d.setColor(myColor);
        g2d.fillOval(3, 3, SIZE - 6, SIZE - 6);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Lucida Sans", Font.BOLD, FONTSIZE));

        if (id >= 10) {
            g2d.drawString(Integer.toString(id), 7, 29);
        } else {
            g2d.drawString(Integer.toString(id), 15, 28);
        }
		//g.drawString(Integer.toString(n.getId()), tempX, tempY);
        //System.out.println(tempX + "," + tempY);
    }

    public void mouseClicked(MouseEvent arg0) {
        /* deve enviar um evento para a interface gráfica dizendo que foi clicado, para
         * que a interface saiba aonde criar as arestas
         */
        if (arg0.getButton() == MouseEvent.BUTTON1) {
            ((Graph2D) this.getParent()).node2DClicked(this);
        }

        if (arg0.isPopupTrigger()) {
            checkPopUp(arg0);
        }
    }

    public void mouseDragged(MouseEvent arg0) {
        /* deve movimentar o nó pela tela */
        this.setLocation(this.getX() + arg0.getX() - SIZE / 2, this.getY() + arg0.getY() - SIZE / 2);
        ((Graph2D) this.getParent()).repaintEdges(this);
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
        if (arg0.isPopupTrigger()) {
            checkPopUp(arg0);
        }
    }

    public void mouseReleased(MouseEvent arg0) {
        if (arg0.isPopupTrigger()) {
            checkPopUp(arg0);
        }
    }

    public void mouseMoved(MouseEvent arg0) {
    }

    public void checkPopUp(MouseEvent e) {
        popup.show(e.getComponent(), e.getX(), e.getY());
    }

    public void recreatePopUpItems() {
        JMenuItem i;
        popup.removeAll();
        i = new JMenuItem("Normal");
        i.addActionListener(this);
        popup.add(i);

        for (int j = 0; nodeTypes != null && j < nodeTypes.length; j++) {
            System.out.println("Tipo:" + nodeTypes[j]);
            i = new JMenuItem(nodeTypes[j]);
            i.addActionListener(this);
            popup.add(i);
        }
    }

    /**
     * @return Returns the label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label The label to set.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public void actionPerformed(ActionEvent arg0) {
        String comando = arg0.getActionCommand();
        this.setType(comando);
        repaint();
    }
}
