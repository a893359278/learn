package org.csp.learn.hbase.base.api;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.Map;
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
import org.junit.Test;

/**
 * @author 陈少平
 * @date 2023-01-01 11:11
 */
public class HBaseDDL {
    static Connection conn;
    static {
        try {
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "127.0.0.1");
            conf.set("hbase.zookeeper.property.clientPort", "2182");
            conf.set("hbase.master", "127.0.0.1:60000");
            conn = ConnectionFactory.createConnection(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createNameSpace() throws IOException {
        String namespace = "bigdata";
        // 非线程安全
        Admin admin = conn.getAdmin();
        NamespaceDescriptor mynamespace = NamespaceDescriptor.create(namespace).build();
        admin.createNamespace(mynamespace);
        admin.close();
    }

    @Test
    public void createTable() throws IOException {
        Admin admin = conn.getAdmin();
        ColumnFamilyDescriptor build = ColumnFamilyDescriptorBuilder.newBuilder("info".getBytes()).build();
        TableDescriptor descriptor = TableDescriptorBuilder
                .newBuilder(TableName.valueOf("bigdata:hello"))
                .setColumnFamily(build).build();
        admin.createTable(descriptor);
        admin.close();
    }

    @Test
    public void descriptionTable() throws IOException {
        Admin admin = conn.getAdmin();
        TableName tableName = TableName.valueOf("bigdata:hello");
        TableDescriptor descriptor = admin.getDescriptor(tableName);
        ColumnFamilyDescriptor[] columnFamilies = descriptor.getColumnFamilies();
        for (ColumnFamilyDescriptor columnFamily : columnFamilies) {
            String nameAsString = columnFamily.getNameAsString();
            System.out.println("name = " + nameAsString);
            Map<String, String> configuration = columnFamily.getConfiguration();
            System.out.println("configuration = " + JSON.toJSONString(configuration));
        }
    }
}
