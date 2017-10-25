
import java.util.EventObject;

/**
 * @author daniel
 */
public class MessageEvent extends EventObject {

    private String message;

    /**
     * @param arg0 arg0
     * @param m m
     */
    public MessageEvent(Object arg0, final String m) {
        super(arg0);
        message = m;
    }

    public String getMessage() {
        return message;
    }
}
