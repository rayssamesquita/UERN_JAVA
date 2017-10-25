
import java.util.EventObject;
/*
 * Created on 27/12/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author daniel
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
import java.awt.Color;

public class ColorEvent extends EventObject {

    private Color color;

    /**
     * @param arg0 arg0
     * @param c c
     */
    public ColorEvent(Object arg0, final Color c) {
        super(arg0);
        color = c;
    }

    public Color getColor() {
        return color;
    }
}
