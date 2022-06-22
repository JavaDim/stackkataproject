package com.javamentor.qa.platform.dao.impl.model;

import java.lang.reflect.ParameterizedType;

public abstract class ReadOnlyDaoImpl<E, K> {

    private Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

}
