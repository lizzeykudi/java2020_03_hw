package myGson;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MyGson {

    public String toJson(Object obj) throws IllegalAccessException {
        if (obj == null) {return "null";}
        StringBuilder stringBuilder = new StringBuilder("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = field.get(obj);
            if (field.getType().isArray()) {
                fieldValue = arrayAsString(field, obj);
            }
            stringBuilder.append("\"" + fieldName + "\":");
            stringBuilder.append(fieldValue + ",");

        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1) + "}";
    }

    private String arrayAsString(Field field, Object obj) throws IllegalAccessException {
        switch (field.getType().getComponentType().getName()) {
            case "int": {
                return Arrays.toString((int[]) field.get(obj));
            }
            case "char": {
                return Arrays.toString((char[]) field.get(obj));
            }
            case "boolean": {
                return Arrays.toString((boolean[]) field.get(obj));
            }
            case "double": {
                return Arrays.toString((double[]) field.get(obj));
            }
            case "float": {
                return Arrays.toString((float[]) field.get(obj));
            }
            case "long": {
                return Arrays.toString((long[]) field.get(obj));
            }
            case "short": {
                return Arrays.toString((short[]) field.get(obj));
            }
            case "byte": {
                return Arrays.toString((byte[]) field.get(obj));
            }
        }
        return null;
    }


}
