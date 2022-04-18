package ag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anil
 */
public class Message implements Serializable {

    private final Map<String, Object> map;

    /**
     * inits
     */
    public Message() {
        map = new HashMap<>();
    }

    /**
     *
     * @param key the key to put the value
     * @param value the value
     */
    public void put(String key, Object value) {
        map.put(key, value);
    }

    /**
     *
     * @param key the key to be removed
     */
    public void remove(String key) {
        map.remove(key);
    }

    /**
     *
     * @param key the key to get the value
     * @return the value
     */
    public Object get(String key) {
        return map.get(key);
    }
}
