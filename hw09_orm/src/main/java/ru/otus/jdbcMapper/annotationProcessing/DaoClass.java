package ru.otus.jdbcMapper.annotationProcessing;

import com.google.gson.Gson;
import ru.otus.core.dao.UserDaoException;
import ru.otus.jdbcMapper.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class DaoClass {

    private final Object object;
    private final Class<?> clazz;
    private Field idField;
    private List<Field> fields;

    public DaoClass(Object object) {
        this.object = object;
        this.clazz = object.getClass();
        fields = scanFields();
        idField = findIdField();
    }

    protected List<Field> scanFields() {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    protected Field findIdField() {
        for (Field field : fields) {
            field.setAccessible(true);
            Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
            List<Annotation> annotations = Arrays.asList(fieldAnnotations);
            if (annotations.stream().anyMatch(annotation -> annotation.annotationType().getName().equals("ru.otus.jdbcMapper.annotation.Id"))) {
                return field;
            }
        }
        throw new RuntimeException("Expexted @Id");
    }

    public Field getIdField() {
        return idField;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Field> getFieldsWithoutId() {
        List<Field> fieldsList = new ArrayList<>(fields);
        fieldsList.remove(idField);
        return fieldsList;

    }

    public void setIdValue(Object o, long idValue) {
        idField.setAccessible(true);
        try {
            idField.set(o, idValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getIdFieldName() {
        return idField.getName();

    }

    public long getIdFieldValue() {
        idField.setAccessible(true);
        try {
            return (long)idField.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
throw new RuntimeException("expected id");
    }

    public List<Object> getValues() {
        return getValues(fields);
    }

    public List<Object> getValuesWithoutId() {
        return getValues(getFieldsWithoutId());
    }

    public List<Object> getValuesForUpdate() {
        List<Object> objects = new ArrayList<>();
        //objects.add(null);
        for (Field field : getFieldsWithoutId()) {
            try {
                field.setAccessible(true);
                objects.add(field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    public List<Object> getFieldNamesAndValues() {
        List<Object> objects = new ArrayList<>();
        for (Field field : getFieldsWithoutId()) {
            try {
                field.setAccessible(true);
                objects.add(field.getName());
                objects.add(field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    private List<Object> getValues(List<Field> fields) {
        List<Object> objects = new ArrayList<>();
        objects.add(null);
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                objects.add(field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    public Optional getObjectFromResultSet(ResultSet resultSet, Class clazz) {
        try {
            if (resultSet.next()) {
                String gsonString = "{";
                for (Field field : fields) {
                    String name = field.getName();
                    String value = resultSet.getString(name);
                    gsonString += "\"" + name + "\":";
                    gsonString += value + ",";
//                    Class<?> type = field.getType();
//                    Object cast = type.cast(value);
//
//                    field.set(instance, type.cast(value));
                }
                gsonString = gsonString.substring(0, gsonString.length() - 1) + "}";
                return Optional.of(new Gson().fromJson(gsonString, clazz));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean getBooleanFromResultSet(ResultSet resultSet, Class clazz) {
        try {
            if (resultSet.next()) {
          return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
