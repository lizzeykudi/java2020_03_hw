package myGson;

import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
            StringBuilder stringBuilder = new StringBuilder("[");
            for(Object o : (Collection)obj) {
                stringBuilder.append(toJson(o)+",");
            }
            if (stringBuilder.toString().length()<2) {return "[]";}
            return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1) + "]";
        }
        if (obj.getClass().isArray()) {
            return arrayAsString(obj);
        }
        StringBuilder stringBuilder = new StringBuilder("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!Modifier.toString(field.getModifiers()).contains("transient")&&!Modifier.toString(field.getModifiers()).contains("static")) {
                String fieldName = field.getName();
                Object fieldValue = toJson(field.get(obj));
                stringBuilder.append("\"" + fieldName + "\":");
                stringBuilder.append(fieldValue + ",");
            }
        }
        if (stringBuilder.toString().length()<2) {return "{}";}
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1) + "}";
    }

    private String arrayAsString(Object obj) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < Array.getLength(obj); i++) {
            stringBuilder.append(Array.get(obj, i) + ",");
        }
        if (stringBuilder.toString().length()<2) {return "[]";}
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1) + "]";
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> unwrap(Class<T> c) {
        return (Class<T>) MethodType.methodType(c).unwrap().returnType();
    }


}
