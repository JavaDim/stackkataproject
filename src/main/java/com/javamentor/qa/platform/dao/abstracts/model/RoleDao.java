package com.javamentor.qa.platform.dao.abstracts.model;

import java.util.Optional;

public interface RoleDao <E, K> extends ReadWriteDao <E, K>{
    Optional<E> getByName(K name);
}
