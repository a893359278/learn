package org.csp.learn.hbase.base.api;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.Map;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.csp.learn.hbase.base.HBase;
import org.junit.Test;

/**
 * @author 陈少平
 * @date 2023-01-01 11:11
 */
public class HBaseDDLTest extends HBase {


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

    /**
     * 新增 column family
     * @throws IOException
     */
    @Test
    public void createTable2() throws IOException {
        Admin admin = conn.getAdmin();

        ColumnFamilyDescriptor build = ColumnFamilyDescriptorBuilder.newBuilder("p".getBytes()).setMaxVersions(3).build();

        admin.addColumnFamily(TableName.valueOf("bigdata:hello"), build);

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
