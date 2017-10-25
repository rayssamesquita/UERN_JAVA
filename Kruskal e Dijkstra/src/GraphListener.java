
import java.util.EventListener;

/**
 * @author Daniel e Iuri
 */
public interface GraphListener extends EventListener {

    public void setMessageEvent(MessageEvent e);

    public void setErrorEvent(ErrorEvent e);
}
