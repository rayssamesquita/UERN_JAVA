
import java.util.EventObject;

/**
 * @author daniel
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AttributeEvent extends EventObject {

    private double weight;
    private String attribute;

    /**
     * @param arg0 arg0
     * @param att att
     * @param w w
     */
    public AttributeEvent(Object arg0, final String att, final double w) {
        super(arg0);
        weight = w;
        attribute = att;
    }

    public double getAttributeValue() {
        return weight;
    }

    public String getAttributeName() {
        return attribute;
    }

}
