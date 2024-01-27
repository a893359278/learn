package org.csp.learn.sharding.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import javax.annotation.Resource;
import org.csp.learn.sharding.DO.UserDO;
import org.csp.learn.sharding.DO.UserLogDO;
import org.csp.learn.sharding.mapper.UserLogMapper;
import org.csp.learn.sharding.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 陈少平
 * @date 1/27/24 10:30 AM
 */
@Service
public class MyService {
    @Resource
    UserLogMapper userLogMapper;

    @Resource
    UserMapper userMapper;

    public static final Snowflake sf = IdUtil.getSnowflake(1, 1);

    @Transactional
    public void save() {
        long id = sf.nextId();
        System.out.println(id);


        UserLogDO userLogDO = new UserLogDO();
        userLogDO.setUserId(id);
        userLogMapper.save(userLogDO);

        UserDO userDO = new UserDO();
        userDO.setUserId(id);
        userMapper.save(userDO);

        long id2 = sf.nextId() + 1;
        System.out.println(id2);
        UserDO userDO2 = new UserDO();
        userDO2.setUserId(id2);
        userMapper.save(userDO2);

        throw new RuntimeException("111");
    }
}
