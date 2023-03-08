package org.csp.learn.hbase.base.api;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;

/**
 * @author 陈少平
 * @date 2022-11-27 20:34
 */
public class HBaseDDL {

    static Connection conn;
    static Configuration conf;
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

    public void createNameSpace(String namespace) throws IOException {
        // 非线程安全
        Admin admin = conn.getAdmin();
        NamespaceDescriptor mynamespace = NamespaceDescriptor.create(namespace).build();
        admin.createNamespace(mynamespace);
        admin.close();
    }

    public void createTable() throws IOException {
        Admin admin = conn.getAdmin();
        ColumnFamilyDescriptor build = ColumnFamilyDescriptorBuilder.newBuilder("info".getBytes()).build();
        TableDescriptor descriptor = TableDescriptorBuilder
                .newBuilder(TableName.valueOf("bigdata:hello"))
                .setColumnFamily(build).build();
        admin.createTable(descriptor);
        admin.close();
    }

    public static void main(String[] args) throws IOException {
        HBaseDDL ddl = new HBaseDDL();
//        ddl.createNameSpace("bigdata");
        ddl.createTable();
        conn.close();
    }
}
