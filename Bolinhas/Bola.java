import java.awt.*;
import java.util.Random;

/**
 * Created by '-Maxsuel on 16/04/2016.
 */
public class Bola {
    private int x, y;
    private int raio;
    private Graphics g;
    private boolean xB, yB;
    private Color corBola;
    private Random random = new Random();

    public Bola(int x, int y, Graphics g, Color corBola, int raio) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.raio = raio;
        this.corBola = corBola;
        this.xB = random.nextBoolean();
        this.yB = random.nextBoolean();
        start();
    }

    private void start() {
        g.setColor(corBola);
        g.fillOval(x, y, raio, raio);
    }

    public void update() {
        g.clearRect(x, y, raio, raio);

        if (y <= 20) {
            yB = true;
        }
        if (y >= 560) {
            yB = false;
        }

        if (x <= 0) {
            xB = false;
        }
        if (x >= 760) {
            xB = true;
        }

        if (yB) {
            y++;
        } else {
            y--;
        }

        if (xB) {
            x--;
        } else {
            x++;
        }
        start();
    }
}
