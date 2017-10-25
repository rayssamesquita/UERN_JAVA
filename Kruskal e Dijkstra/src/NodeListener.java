
import java.util.EventListener;

/**
 * @author daniel
 *
 */
public interface NodeListener extends EventListener {

    public void setColorEvent(ColorEvent e);

    public void setLabelEvent(LabelEvent e);
}
