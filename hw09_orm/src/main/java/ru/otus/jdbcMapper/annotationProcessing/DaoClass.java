package ru.otus.jdbcMapper.annotationProcessing;

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

    public List<Object> getValues() {
        return getValues(fields);
    }

    public List<Object> getValuesWithoutId() {
        return getValues(getFieldsWithoutId());
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

    public Object getObjectFromResultSet(ResultSet resultSet, Class clazz) {
        try {
            Object instance = clazz.newInstance();
            if (resultSet.next()) {
                for (Field field : fields) {
                    String value = resultSet.getString(field.getName());
                    field.set(instance, value);
                }
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException();
    }
}
