
import java.util.EventListener;

/**
 * @author daniel
 *
 */
public interface EdgeListener extends EventListener {

    public void setColorEvent(ColorEvent e);

    public void setAttributeEvent(AttributeEvent e);

    public void setLabelEvent(LabelEvent e);
}
