package com.javamentor.qa.platform.dao.abstracts.model;

import java.util.Optional;

public interface UserDao <E, K> extends ReadWriteDao <E, K>{
    Optional<E> getByEmail(K email);
}
