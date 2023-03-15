package org.csp.learn.hbase.base.api;

import java.io.IOException;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.csp.learn.hbase.base.HBase;
import org.junit.Test;

/**
 *
 * hbase 提供单行列自增操作.
 *
 * 如果数据量特别多，那么使用 redis 存储是不合适的，这个时候用 hbase 就非常合适。
 * @author 陈少平
 * @date 2023-03-13 19:41
 */
public class CalculationTest extends HBase {

    /**
     * 单计数器
     */
    @Test
    public void incr() throws IOException {
        long l = table.incrementColumnValue("rowKey5".getBytes(), "p".getBytes(), "click".getBytes(), 1L);
        System.out.println(l);
    }

    /**
     * 多计数器
     */

    @Test
    public void incrMul() throws Exception {
        Increment increment = new Increment("rowKey5".getBytes());
        increment.addColumn("p".getBytes(), "click".getBytes(), 100L)
        .addColumn("p".getBytes(), "click1".getBytes(), 1L);
        Result result = table.increment(increment);
        print(result);
    }

}
