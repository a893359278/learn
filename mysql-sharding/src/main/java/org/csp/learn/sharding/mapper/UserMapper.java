package org.csp.learn.sharding.mapper;

import org.csp.learn.sharding.DO.UserDO;

/**
 * @author 陈少平
 * @date 1/27/24 10:24 AM
 */
public interface UserMapper {
    int save(UserDO entity);
}
