package org.animesh.javabrains.rest.paramconverter;

import jakarta.ws.rs.ext.ParamConverter;
import org.animesh.javabrains.rest.model.MyDate;

import java.util.Calendar;

public class MyDateConverter<T> implements ParamConverter<T> {
    private final Class<T> rawType;

    public MyDateConverter(Class<T> rawType) {
        this.rawType = rawType;
    }

    @Override
    public T fromString(String value) {
        Calendar requestedDate = Calendar.getInstance();
        if (value.equalsIgnoreCase("tomorrow"))
            requestedDate.add(Calendar.DATE, 1);
        if (value.equalsIgnoreCase("yesterday"))
            requestedDate.add(Calendar.DATE, -1);

        MyDate myDate = new MyDate(requestedDate.get(Calendar.DATE),
                requestedDate.get(Calendar.MONTH),
                requestedDate.get(Calendar.YEAR));

        return rawType.cast(myDate); // return type T
    }

    @Override
    public String toString(T myBean) {
        return myBean != null ? myBean.toString() : null;
    }
}
