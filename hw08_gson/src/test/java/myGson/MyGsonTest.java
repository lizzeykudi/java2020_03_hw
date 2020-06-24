package myGson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MyGsonTest {

    @Test
    public void test() throws IllegalAccessException, ClassNotFoundException {
        MyGson myGson = new MyGson();
        AnyObject obj = new AnyObject(22, "test", 10, new int[]{1, 2}, Arrays.asList(new Integer[]{1, 2}));
        String json = myGson.toJson(obj);
        System.out.println(json);

        Gson gson = new Gson();
        AnyObject obj2 = gson.fromJson(json, AnyObject.class);
        assertEquals(gson.toJson(obj), myGson.toJson(obj));
    }

    @Test
    public void customTest () throws IllegalAccessException {
        MyGson myGson = new MyGson();
        Gson gson = new GsonBuilder().serializeNulls().create();

        assertEquals(gson.toJson(true), myGson.toJson(true));
        assertEquals(gson.toJson(false), myGson.toJson(false));
        assertEquals(gson.toJson((byte)1), myGson.toJson((byte)1));
        assertEquals(gson.toJson((short)2), myGson.toJson((short)2));
        assertEquals(gson.toJson(3), myGson.toJson(3));
        assertEquals(gson.toJson(4L), myGson.toJson(4L));
        assertEquals(gson.toJson(5f), myGson.toJson(5f));
        assertEquals(gson.toJson(6d), myGson.toJson(6d));
        assertEquals(gson.toJson("aaa"), myGson.toJson("aaa"));
        assertEquals(gson.toJson('b'), myGson.toJson('b'));
        assertEquals(gson.toJson(new int[] {1, 2, 3}), myGson.toJson(new int[] {1, 2, 3}));
        assertEquals(gson.toJson(Collections.singletonList(7)), myGson.toJson(Collections.singletonList(7)));
    }

    @Test
    public void test2() throws IllegalAccessException {
        AnyObject2 obj = new AnyObject2();

        MyGson myGson = new MyGson();
        Gson gson = new Gson();

        String json = myGson.toJson(obj);
        String gJson = gson.toJson(obj);

        System.out.println(json);
        System.out.println(gJson);

        assertEquals(gJson, json);
    }
}