package org.csp.learn.hbase.base;

import java.util.Iterator;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author 陈少平
 * @date 2023-03-07 20:43
 */
public class HBase {

    protected static Connection conn;
    protected static Configuration conf;
    protected static Table table;

    static {
        try {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "127.0.0.1");
            conf.set("hbase.zookeeper.property.clientPort", "2182");
            conf.set("hbase.master", "127.0.0.1:60000");
            conn = ConnectionFactory.createConnection(conf);
            table = conn.getTable(TableName.valueOf("bigdata:hello"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void print(Result result) {
        List<Cell> cells = result.listCells();
        for (Cell cell : cells) {
//            System.out.println(cell);
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            Object v = null;
            if (qualifier.equals("age")) {
                v = Bytes.toInt(CellUtil.cloneValue(cell));
            } else if (qualifier.equals("click") || qualifier.equals("click1")) {
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

    protected void print_scan(Scan scan) {
        try {
            ResultScanner scanner = table.getScanner(scan);
            Iterator<Result> iterator = scanner.iterator();
            while (iterator.hasNext()) {
                Result next = iterator.next();
                print(next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
