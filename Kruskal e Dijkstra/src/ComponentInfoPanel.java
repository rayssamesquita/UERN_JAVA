
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;
import javax.swing.table.AbstractTableModel;

/**
 * @author Daniel e Iuri
 */
public class ComponentInfoPanel extends JComponent implements MouseInputListener {

    protected JTable tabela;
    protected JLabel tipo;
    protected String[] colunas = {"Atributo", "Valor"};

    ComponentInfoPanel() {

        this.setLayout(new BorderLayout());
        this.tipo = new JLabel();
        tipo.setFont(new Font(null, Font.BOLD, 28));
        this.add(tipo, BorderLayout.NORTH);

    }

    public void showComponent(Object c) {

        if (c instanceof Edge2D) {
            this.showEdge2D((Edge2D) c);
        } else if (c instanceof Node2D) {
            this.showNode2D((Node2D) c);
        }

    }

    /**
     * @param c
     */
    private void showNode2D(Node2D n) {

        tipo.setText("Nó " + n.getId());
        if (tabela != null) {
            this.remove(tabela);
        }

        Object[][] data = new Object[4][2];

        int j = 0;

        data[j][0] = new String("ID");
        data[j++][1] = new Integer(n.getId());
        data[j][0] = new String("Rótulo");
        data[j++][1] = new String(n.getLabel());
        data[j][0] = new String("Cor");
        data[j++][1] = new String(n.getColor().toString());
        data[j][0] = new String("Tipo");
        data[j++][1] = new String(n.getType());

        tabela = new JTable(new ReadOnlyTable(data, colunas));
        this.add(tabela, BorderLayout.CENTER);
        this.validate();
        repaint();
    }

    /**
     * @param c
     */
    private void showEdge2D(Edge2D e) {

        tipo.setText("Aresta " + e.getFrom().getId() + "-" + e.getTo().getId());
        if (tabela != null) {
            this.remove(tabela);
        }
        Object[][] data = new Object[e.attributes.size() + 4][2];
        Iterator i = e.attributes.keySet().iterator();
        int j = 0;

        data[j][0] = new String("De");
        data[j++][1] = new Integer(e.getFrom().getId());

        data[j][0] = new String("Para");
        data[j++][1] = new Integer(e.getTo().getId());

        data[j][0] = new String("Rótulo");
        data[j++][1] = new String(e.getLabel());

        data[j][0] = new String("Cor");
        data[j++][1] = new String(e.getColor().toString());

        while (i.hasNext()) {
            String att = (String) i.next();
            data[j][0] = att;
            data[j][1] = e.attributes.get(att);
            j++;
        }

        tabela = new JTable(new ReadOnlyTable(data, colunas));
        this.add(tabela, BorderLayout.CENTER);
        this.validate();
        repaint();

    }

    public void mouseClicked(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {

        this.showComponent(arg0.getSource());
    }

    public void mouseExited(MouseEvent arg0) {
        if (tabela != null) {
            this.remove(tabela);
        }
        tipo.setText(null);
        repaint();
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void mouseDragged(MouseEvent arg0) {
    }

    public void mouseMoved(MouseEvent arg0) {
    }

}

class ReadOnlyTable extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] data;

    public ReadOnlyTable(Object[][] d, String[] colunas) {
        data = d;
        columnNames = colunas;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        if (data == null) {
            return 0;
        }
        return data.length;
    }

    public Object getValueAt(int arg0, int arg1) {
        return data[arg0][arg1];
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
}
