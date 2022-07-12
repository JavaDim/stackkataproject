package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class IgnoredTagDaoImpl extends ReadWriteDaoImpl<IgnoredTag, Long> implements IgnoredTagDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override

    public Optional<IgnoredTag> getByUserAndTag(User user, Tag ignoredTag) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT i
                        FROM IgnoredTag i
                        WHERE i.user.id=:userId
                        AND i.ignoredTag.id=:ignoredtagId
                        """, IgnoredTag.class)
                .setParameter("userId", user.getId())
                .setParameter("ignoredtagId", ignoredTag.getId()));
    }
}
