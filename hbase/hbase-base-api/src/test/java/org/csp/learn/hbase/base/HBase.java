package org.csp.learn.hbase.base;

import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author 陈少平
 * @date 2023-03-07 20:43
 */
public class HBase {
    protected static Connection conn;
    protected static Configuration conf;
    static {
        try {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "127.0.0.1");
            conf.set("hbase.zookeeper.property.clientPort", "2182");
            conf.set("hbase.master", "127.0.0.1:60000");
            conn = ConnectionFactory.createConnection(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void print(Result result) {
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
                    + " " + cell.getTimestamp()
            );
        }
    }
}
