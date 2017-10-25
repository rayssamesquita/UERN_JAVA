
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

/**
 * @author Daniel e Iuri
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DirectedEdge2D extends Edge2D {

    private int DARROW = 5;

    /**
     * @param f from
     * @param t to
     */
    public DirectedEdge2D(Node2D f, Node2D t) {
        super(f, t);
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
            Arrow.drawArrow(g, w - DARROW, h - DARROW, DARROW, DARROW);
        } else if (inicio[0] >= fim[0] && inicio[1] < fim[1]) {
            line = new Line2D.Double(w - D, D, D, h - D);
            Arrow.drawArrow(g, w - DARROW, DARROW, DARROW, h - DARROW);
        } else if (inicio[0] < fim[0] && inicio[1] >= fim[1]) {
            line = new Line2D.Double(D, h - D, w - D, D);
            Arrow.drawArrow(g, DARROW, h - DARROW, w - DARROW, DARROW);
        } else if (inicio[0] < fim[0] && inicio[1] < fim[1]) {
            line = new Line2D.Double(D, D, w - D, h - D);
            Arrow.drawArrow(g, DARROW, DARROW, w - DARROW, h - DARROW);
        }

        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.draw(line);
    }
}
