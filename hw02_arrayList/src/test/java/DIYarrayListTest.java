import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DIYarrayListTest {

    DIYarrayList list = new DIYarrayList<>();

    @Before
    public void beforeTest() {
        for (int i = 21; i > 0; i--) {
            list.add(i);
        }
    }

    @Test
    public void testAddAll() {

        System.out.println("list : " + Arrays.toString(list.toArray()));

        Collections.addAll(list, list.toArray());

        System.out.println("list after adding : " + Arrays.toString(list.toArray()));

    }

    @Test
    public void testCopy() {

        System.out.println("list : " + Arrays.toString(list.toArray()));
        DIYarrayList src = list;
        Collections.sort(src);
        Collections.copy(list, src);

        System.out.println("list after copy : " + Arrays.toString(list.toArray()));

    }

    @Test
    public void testCompare() {

        ArrayList arrayList = new ArrayList<>();

        System.out.println("Unsorted list : " + Arrays.toString(list.toArray()));

        Collections.sort(list);

        System.out.println("Sorted list : " + Arrays.toString(list.toArray()));

    }

}