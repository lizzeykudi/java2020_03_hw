package myGson;

import com.google.common.collect.Iterables;

import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class MyGson {

    public String toJson(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return "null";
        }
        if (obj.getClass().equals(String.class) || unwrap(obj.getClass()).equals(char.class)) {
            return "\"" + obj.toString() + "\"";
        }
        if (unwrap(obj.getClass()).isPrimitive()) {
            return obj.toString();
        }
        if (obj instanceof Collection) {
            return Iterables.toString((Iterable<?>) obj);
        }
        if (obj.getClass().isArray()) {
            return arrayAsString(obj);
        }
        StringBuilder stringBuilder = new StringBuilder("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = toJson(field.get(obj));
            stringBuilder.append("\"" + fieldName + "\":");
            stringBuilder.append(fieldValue + ",");

        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1) + "}";
    }

    private String arrayAsString(Object obj) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < Array.getLength(obj); i++) {
            stringBuilder.append(Array.get(obj, i) + ",");
        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1) + "]";
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> unwrap(Class<T> c) {
        return (Class<T>) MethodType.methodType(c).unwrap().returnType();
    }


}
