package org.csp.learn.hbase.base.api;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.util.Arrays;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.csp.learn.hbase.base.HBase;
import org.junit.Test;

/**
 * @author 陈少平
 * @date 2023-03-07 20:08
 */
public class PutTest extends HBase {

    /**
     * column family 不存在.
     *
     * 会直接抛异常。
     * NoSuchColumnFamilyException
     * @throws Exception
     */
    @Test
    public void put_case1() throws Exception {
        Put put = new Put(toBytes("rowKey1"));
        put.addColumn(toBytes("person"), toBytes("name"), toBytes("chenshaoping"));
        conn.getTable(TableName.valueOf("bigdata:hello")).put(put);
    }

    /**
     * column qualifier 不存在.
     *
     * 写入成功
     * @throws Exception
     */
    @Test
    public void put_case2() throws Exception {
        Put put = new Put(toBytes("rowKey7"));
        put.addColumn(toBytes("p"), toBytes("zzx"), 1678199225003L, toBytes("zxczxc"));
        conn.getTable(TableName.valueOf("bigdata:hello")).put(put);
    }

    /**
     * 写入多个
     * @throws Exception
     */
    @Test
    public void put_case3() throws Exception {
        Put put = new Put(toBytes("rowKey2"));
        put.addColumn(toBytes("info"), toBytes("name"), toBytes("chenshaoping"))
                .addColumn(toBytes("info"), toBytes("age"), toBytes(12))
                .addColumn(toBytes("info"), toBytes("adress"), toBytes("福建省"));

        Put put1 = new Put(toBytes("rowKey3"));
        put1.addColumn(toBytes("info"), toBytes("name"), toBytes("chenshaoping"))
                .addColumn(toBytes("info"), toBytes("age"), toBytes(12))
                .addColumn(toBytes("info"), toBytes("adress"), toBytes("福建省"));

        table.put(Arrays.asList(put, put1));
    }

    /**
     * 通过 cell 写入
     */
    @Test
    public void put_case4() throws Exception {
        Put put = new Put(toBytes("rowKey4"));

        KeyValue value = new KeyValue(toBytes("rowKey4"), toBytes("info"), toBytes("age"), toBytes(12));
        KeyValue value2 = new KeyValue(toBytes("rowKey4"), toBytes("info"), toBytes("name"), toBytes("chenshaoping2"));
        put.add(value).add(value2);

        conn.getTable(TableName.valueOf("bigdata:hello")).put(put);
    }

    /**
     * 写入相同的 rowKey
     * @throws Exception
     */
    @Test
    public void put_case5() throws Exception {
        Put put = new Put(toBytes("rowKey4"));

        KeyValue value = new KeyValue(toBytes("rowKey4"), toBytes("info"), toBytes("age"), toBytes(15));
        KeyValue value2 = new KeyValue(toBytes("rowKey4"), toBytes("info"), toBytes("name"), toBytes("grtrt"));
        put.add(value).add(value2);

        conn.getTable(TableName.valueOf("bigdata:hello")).put(put);

    }

    /**
     * 写入多版本数据, 需要 table family 支持. 同时，需要写入 ts
     */
    @Test
    public void put_case6() throws Exception {
        Put put = new Put(toBytes("rowKey5"));
        put.addColumn(toBytes("p"), toBytes("age"), System.currentTimeMillis() - 40000, toBytes(13));

        Put put2 = new Put(toBytes("rowKey5"));
        put2.addColumn(toBytes("p"), toBytes("age"), System.currentTimeMillis() - 30000, toBytes(15));

        Put put3 = new Put(toBytes("rowKey5"));
        put3.addColumn(toBytes("p"), toBytes("age"), System.currentTimeMillis() - 20000, toBytes(16));

        Put put4 = new Put(toBytes("rowKey5"));
        put4.addColumn(toBytes("p"), toBytes("age"), System.currentTimeMillis() - 10000, toBytes(17));

        conn.getTable(TableName.valueOf("bigdata:hello")).put(Arrays.asList(put, put2, put3, put4));
    }

    @Test
    public void put_case7() throws Exception {
        Put put = new Put(toBytes("rowKey6"));
        put.addColumn(toBytes("p"), toBytes("age"), System.currentTimeMillis() - 40000, toBytes(13));

        Put put2 = new Put(toBytes("rowKey6"));
        put2.addColumn(toBytes("p"), toBytes("age"), System.currentTimeMillis() - 30000, toBytes(15));

        Put put3 = new Put(toBytes("rowKey6"));
        put3.addColumn(toBytes("p"), toBytes("age"), System.currentTimeMillis() - 20000, toBytes(12));

        conn.getTable(TableName.valueOf("bigdata:hello")).put(Arrays.asList(put,put2,put3));
    }


    @Test
    public void put_case8() throws Exception {
        Put put = new Put("rowKey5".getBytes());
        put.addColumn("p".getBytes(), "click1".getBytes(), Bytes.toBytes(1L));

        Put put1 = new Put("rowKey6".getBytes());
        put1.addColumn("p".getBytes(), "click1".getBytes(), Bytes.toBytes(1L));

        Put put2 = new Put("rowKey6".getBytes());
        put2.addColumn("p".getBytes(), "click1".getBytes(), Bytes.toBytes(1L));

        table.put(Arrays.asList(put, put1, put2));
    }
}
