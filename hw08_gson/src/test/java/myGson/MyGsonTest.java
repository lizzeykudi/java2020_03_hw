package myGson;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.Arrays;

public class MyGsonTest {

    @Test
    public void test() throws IllegalAccessException, ClassNotFoundException {
        MyGson myGson = new MyGson();
        AnyObject obj = new AnyObject(22, "test", 10, new int[]{1, 2}, Arrays.asList(new Integer[]{1, 2}));
        String json = myGson.toJson(obj);
        System.out.println(json);

        Gson gson = new Gson();
        AnyObject obj2 = gson.fromJson(json, AnyObject.class);
        System.out.println(obj.equals(obj2));
    }

}