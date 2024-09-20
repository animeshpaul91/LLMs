package org.animesh.javabrains.rest.paramconverter;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import org.animesh.javabrains.rest.model.MyDate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class MyDateConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.getName().equals(MyDate.class.getName())) {
            return new MyDateConverter<>(rawType); // returns an instance of Param Converter
        }
        return null;
    }
}
