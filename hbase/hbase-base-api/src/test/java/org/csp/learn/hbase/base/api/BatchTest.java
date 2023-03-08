package org.csp.learn.hbase.base.api;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Table;
import org.csp.learn.hbase.base.HBase;
import org.junit.Test;

/**
 * 不建议将同一行的不同操作，放在同一个批量操作中
 *
 *
 * @author 陈少平
 * @date 2023-03-07 23:13
 */
public class BatchTest extends HBase {

    private Table table;
    public BatchTest() {
        try {
            table = conn.getTable(TableName.valueOf("bigdata:hello"));
        } catch (Exception e){}
    }

    @Test
    public void test_1() throws IOException, InterruptedException {
        List<Row> batch = new ArrayList<>();

        Get get = new Get(toBytes("rowKey6"));
        batch.add(get);

        Put put = new Put(toBytes("rowKey5"));
        put.addColumn(toBytes("info"), toBytes("age"), toBytes(91));
        batch.add(put);

        Delete delete = new Delete(toBytes("rowKey3"));
        batch.add(delete);

        Object[] results = new Object[batch.size()];
        table.batch(batch, results);

        /**
         * null: 操作失败
         * EmptyResult: put, delete 操作成功返回的结果
         * Result: get 操作成功的返回结果
         * Throwable: 服务端抛出的异常
         */
        for (Object result : results) {
            System.out.println(JSON.toJSONString(result));
        }
    }
}
