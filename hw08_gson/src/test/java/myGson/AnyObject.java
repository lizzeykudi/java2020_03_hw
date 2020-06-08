package myGson;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AnyObject {
    int int1;
    String string;
    int int2;
    int[] ints;
    List<Integer> list;

    public AnyObject(int int1, String string, int int2, int[] ints, List<Integer> list) {
        this.int1 = int1;
        this.string = string;
        this.int2 = int2;
        this.ints = ints;
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyObject anyObject = (AnyObject) o;
        return int1 == anyObject.int1 &&
                int2 == anyObject.int2 &&
                Objects.equals(string, anyObject.string) &&
                Arrays.equals(ints, anyObject.ints) &&
                Objects.equals(list, anyObject.list);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(int1, string, int2, list);
        result = 31 * result + Arrays.hashCode(ints);
        return result;
    }
}
