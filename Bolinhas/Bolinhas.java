import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Created by '-Maxsuel on 16/04/2016.
 */
public class Bolinhas extends JFrame implements MouseListener {
    Random rand = new Random();
    private JPanel painel;
    private ThreadBola threadBola;

    public Bolinhas() {
        super("Bolinhas");

        painel = new JPanel();

        painel.addMouseListener(this);

        setContentPane(painel);
        setSize(800, 600);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            float r = (float) (rand.nextFloat() / 2f + 0.5);
            float g = (float) (rand.nextFloat() / 2f + 0.5);
            float b = (float) (rand.nextFloat() / 2f + 0.5);
            //int raio = rand.nextInt(6)*10;
            Color corBola = new Color(r, g, b);
            threadBola = new ThreadBola(new Bola(e.getX(), e.getY(), getGraphics(), corBola, 40));
            threadBola.start();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
