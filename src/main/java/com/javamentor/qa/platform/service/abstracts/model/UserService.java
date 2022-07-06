package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.User;

public interface UserService <E, K> extends ReadWriteService <E, K>{
    E getByEmail(K email);
}
