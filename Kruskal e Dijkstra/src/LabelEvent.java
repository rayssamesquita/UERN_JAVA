
import java.util.EventObject;

/**
 * @author daniel
 *
 */
public class LabelEvent extends EventObject {

    private String label;

    /**
     * @param arg0 arg0
     * @param s s
     */
    public LabelEvent(Object arg0, final String s) {
        super(arg0);
        label = s;
    }

    public String getLabel() {
        return label;
    }
}
