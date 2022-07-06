package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.Role;

public interface RoleService <E, K> extends ReadWriteService <E, K>{
    E getByName(K name);
}
