package myGson;

import java.util.List;

public class AnyObject2 {
    private List<AnyObject> anyObject2s = List.of(new AnyObject(1, "2", 3, new int[]{4, 5}, List.of(6)));
 //   private AnyObject[] anyObject = new AnyObject[] {new AnyObject(7, "8", 9, new int[]{10, 11}, List.of(12))}; в задании написано "массивы примитивных типов"
    private transient String transientString = "transientString";
    private static String staticString = "staticString";
}
