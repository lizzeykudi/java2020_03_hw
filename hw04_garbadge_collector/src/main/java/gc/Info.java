package gc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Info {
    Map<String, Integer> names = new HashMap<String, Integer>();
    int duration = 0;

    void init() {
        Set<String> strings = names.keySet();
        for (String key : strings) {
            names.put(key, 0);
        }
        duration = 0;
    }

    @Override
    public String toString() {
        return "Info{" +
                "names=" + names +
                ", duration=" + duration +
                '}';
    }

}
