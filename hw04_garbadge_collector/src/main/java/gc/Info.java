package gc;

import java.util.HashMap;
import java.util.Map;

public class Info {
    Map<String, Integer> names = new HashMap<String, Integer>();
    int duration = 0;

    void init() {
        names = new HashMap<String, Integer>();
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
