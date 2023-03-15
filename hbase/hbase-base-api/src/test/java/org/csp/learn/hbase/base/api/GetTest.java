package org.csp.learn.hbase.base.api;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import cn.hutool.core.collection.CollectionUtil;
import java.util.Arrays;
import java.util.List;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.csp.learn.hbase.base.HBase;
import org.junit.Test;

/**
 * @author 陈少平
 * @date 2023-03-07 20:43
 */
public class GetTest extends HBase {

    /**
     * 按照 rowKey 读取
     * @throws Exception
     */
    @Test
    public void get_1() throws Exception {
        Get get = new Get(toBytes("rowKey4"));
        Result result = table.get(get);

        List<Cell> cells = result.listCells();
        for (Cell cell : cells) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            Object v = null;
            if (qualifier.equals("age")) {
                v = Bytes.toInt(CellUtil.cloneValue(cell));
            } else {
                v = Bytes.toString(CellUtil.cloneValue(cell));
            }

            System.out.println(Bytes.toString(CellUtil.cloneRow(cell))
                    + " " +
                    Bytes.toString(CellUtil.cloneFamily(cell))
                    + " " +
                    qualifier
                    + " " + v
                    );
        }
    }


    /**
     * 批量读取
     * @throws Exception
     */
    @Test
    public void get_2() throws Exception {
        Get get1 = new Get(toBytes("rowKey4"));
        Get get2 = new Get(toBytes("rowKey2"));
        Get get4 = new Get(toBytes("rowKey3"));

        Result[] results = table.get(Arrays.asList(get1, get2, get4));
        for (Result result : results) {
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                Object v = null;
                if (qualifier.equals("age")) {
                    v = Bytes.toInt(CellUtil.cloneValue(cell));
                } else {
                    v = Bytes.toString(CellUtil.cloneValue(cell));
                }

                System.out.println(Bytes.toString(CellUtil.cloneRow(cell))
                        + " " +
                        Bytes.toString(CellUtil.cloneFamily(cell))
                        + " " +
                        qualifier
                        + " " + v
                );
            }
        }
    }

    /**
     * 访问不存在的 rowKey
     */
    @Test
    public void get_3() throws Exception {
        Get get = new Get(toBytes("rowKey41"));
        Result result = table.get(get);
        // cells 会返回空，需要注意

    }



    /**
     * 获取所有版本的数据
     * @throws Exception
     */
    @Test
    public void get_4() throws Exception {
        Get get = new Get(toBytes("rowKey6")).readAllVersions();
        get = get.addColumn(toBytes("p"), toBytes("age"));
        get = get.readAllVersions();

        Result result = table.get(get);
        print(result);
    }
}
