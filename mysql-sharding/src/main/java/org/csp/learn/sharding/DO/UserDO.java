package org.csp.learn.sharding.DO;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 陈少平
 * @date 1/27/24 10:25 AM
 */
@Data
public class UserDO implements Serializable {


    private static final long serialVersionUID = -3684865048527917548L;

    private Long id;

    private Long userId;
}
