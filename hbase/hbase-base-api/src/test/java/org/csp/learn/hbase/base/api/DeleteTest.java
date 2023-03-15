package org.csp.learn.hbase.base.api;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.csp.learn.hbase.base.HBase;
import org.junit.Test;

/**
 * @author 陈少平
 * @date 2023-03-07 21:50
 */
public class DeleteTest extends HBase {

    /**
     * 删除最新的版本
     */
    @Test
    public void delete_1() throws IOException {
        Delete key5 = new Delete(toBytes("rowKey6"));
        key5.addColumn(toBytes("p"), toBytes("age"));
        table.delete(key5);
    }

    /**
     * 删除指定版本号的所有 family
     * @throws IOException
     */
    @Test
    public void delete_2() throws IOException {
        Delete k = new Delete(toBytes("rowKey6"));
        k.addFamilyVersion(toBytes("p"), 1678199204997L);
        table.delete(k);
    }

    @Test
    public void delete_3() throws IOException {
        Delete del = new Delete(toBytes("rowKey6"));
        table.delete(del);
    }
}
