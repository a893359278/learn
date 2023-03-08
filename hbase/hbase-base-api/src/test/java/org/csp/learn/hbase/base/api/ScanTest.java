package org.csp.learn.hbase.base.api;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.csp.learn.hbase.base.HBase;
import org.junit.Test;

/**
 * @author 陈少平
 * @date 2023-03-08 22:17
 */
public class ScanTest extends HBase {

    private Table table;
    public ScanTest() {
        try {
            table = conn.getTable(TableName.valueOf("bigdata:hello"));
        } catch (Exception e){}
    }


    @Test
    public void test_01() throws IOException {
        Scan scan = new Scan().withStartRow(toBytes("rowKey4")).readAllVersions();
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()) {
            Result next = iterator.next();
            print(next);
        }
        scanner.close();
    }

    /**
     * setBatch 每次返回多少列
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        Scan scan = new Scan()
                .readAllVersions()
                .setBatch(2);
        long now = System.currentTimeMillis();
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()) {
            Result next = iterator.next();
            print(next);
        }
        scanner.close();
    }

    /**
     * 每次 scan, 都是获取 hbase.client.scanner.caching 条数据。
     * 可以通过 setCaching() 设置一次请求的条数
     *
     * @throws IOException
     */
    @Test
    public void test_03() throws IOException {
        Scan scan = new Scan()
                .readAllVersions()
                .setCaching(10);
        long now = System.currentTimeMillis();
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()) {
            Result next = iterator.next();
            print(next);
        }
        scanner.close();
        System.out.println(System.currentTimeMillis() - now);
    }

    /**
     * batch caching 组合可以圈定一个 region.
     * batch 表明取多少列， caching 表明取多少行
     * @throws IOException
     */
    public void test_04() throws IOException {
        Scan scan = new Scan()
                .readAllVersions()
                .setBatch(10)
                .setCaching(10);
        long now = System.currentTimeMillis();
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()) {
            Result next = iterator.next();
            print(next);
        }
        scanner.close();
        System.out.println(System.currentTimeMillis() - now);
    }


}
