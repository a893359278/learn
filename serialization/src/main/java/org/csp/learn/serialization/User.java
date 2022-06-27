package org.csp.learn.serialization;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shaoping.chen
 * @version 1.0.0
 * @date: Created in 2022/6/27 下午3:31
 */
@Data
public class User implements Serializable {
    private String username;
    private String address;
    private Integer age;
    private String birthDay;
    private Long count;
    private String sex;
    private Long id;
    private Date createTime;

    public static List<User> getList(int count) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.username = RandomUtil.randomString(20);
            user.address = RandomUtil.randomString(20);
            user.age = RandomUtil.randomInt(10, 100);
            user.birthDay = RandomUtil.randomString(20);
            user.count = RandomUtil.randomLong();
            user.sex = RandomUtil.randomString(10);
            user.id = RandomUtil.randomLong();
            user.createTime = new Date();
            list.add(user);
        }
        return list;
    }


    public static String getListString(int count) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.username = RandomUtil.randomString(20);
            user.address = RandomUtil.randomString(20);
            user.age = RandomUtil.randomInt(10, 100);
            user.birthDay = RandomUtil.randomString(20);
            user.count = RandomUtil.randomLong();
            user.sex = RandomUtil.randomString(10);
            user.id = RandomUtil.randomLong();
            user.createTime = new Date();
            list.add(user);
        }
        return JSON.toJSONString(list);
    }
}
