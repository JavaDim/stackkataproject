package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserDto> getUserDtoById(Long id) {

        TypedQuery<UserDto> userDtoQuery = entityManager.createQuery(
                        """
                                  SELECT new com.javamentor.qa.platform.models.dto.user.UserDto
                                  (
                                      u.id,        
                                      u.email,
                                      u.fullName,
                                      u.imageLink,
                                      u.city,
                                      SUM(r.count),
                                      u.persistDateTime,
                                      (SELECT COUNT(DISTINCT va) + COUNT(DISTINCT vq)
                                              FROM VoteAnswer va, VoteQuestion vq
                                             WHERE va.user.id = :userId
                                               AND vq.user.id = :userId
                                      ) 
                                  )
                                 FROM User u
                                 LEFT JOIN Reputation r ON u.id = r.author.id
                                WHERE u.id = :userId
                                GROUP BY u.id
                                  """, UserDto.class)
                .setParameter("userId", id);

        return SingleResultUtil.getSingleResultOrNull(userDtoQuery);
    }

}
