package org.csp.learn.sharding.DO;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 陈少平
 * @date 1/27/24 10:27 AM
 */
@Data
public class UserLogDO implements Serializable {

    private static final long serialVersionUID = 147663285659847644L;
    private Long id;

    private Long userId;
}
