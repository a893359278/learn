package org.csp.learn.hbase.base.api;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.DependentColumnFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SkipFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.filter.WhileMatchFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.csp.learn.hbase.base.HBase;
import org.junit.Test;

/**
 * 这应该是与 mysql 层类似的功能，对已查询出来的数据集进行过滤.
 *
 * @author 陈少平
 * @date 2023-03-13 15:26
 */
public class FilterTest extends HBase {


    /**
     * RowFilter
     */
    @Test
    public void test_01() {
        try {
            RowFilter filter = new RowFilter(CompareOperator.GREATER_OR_EQUAL, new RegexStringComparator("rowKey*"));

            Scan scan = new Scan().readAllVersions().setFilter(filter);

            print_scan(scan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FilterList
     */
    @Test
    public void test_02() {

        FilterList filterList = new FilterList();

//        FamilyFilter filter = new FamilyFilter(CompareOperator.EQUAL, new BinaryComparator("p".getBytes()));
//        filterList.addFilter(filter);

//        RowFilter rowFilter = new RowFilter(CompareOperator.EQUAL, new BinaryComparator("1678199225003".getBytes()));
//        filterList.addFilter(rowFilter);

        DependentColumnFilter filter = new DependentColumnFilter(Bytes.toBytes("p"), Bytes.toBytes("aaa"), true);
        filterList.addFilter(filter);

        Scan scan = new Scan();
        scan.setFilter(filterList);

        print_scan(scan);
    }

    /**
     * PageFilter
     */
    @Test
    public void test_03() throws IOException {
        PageFilter filter = new PageFilter(2);

        Scan scan = new Scan();
        scan.setFilter(filter);

        byte[] lastRow = null;
        while (true) {
            if (lastRow != null) {
                scan.withStartRow(lastRow, false);
            }
            ResultScanner scanner = table.getScanner(scan);
            Iterator<Result> it = scanner.iterator();
            int count = 0;
            while (it.hasNext()) {
                Result next = it.next();
                print(next);
                lastRow = next.getRow();
                count++;
            }
            scanner.close();
            if (count == 0) {
                break;
            }
        }
    }


    /* 附加过滤器 start */

    /**
     * 跳转过滤器，
     * ValueFilter 原先只能过滤 cell, 组合 SkipFilter 后，则直接过滤整行
     */
    @Test
    public void test_04() throws IOException {
        ValueFilter filter2 = new ValueFilter(CompareOperator.NOT_EQUAL, new BinaryComparator(Bytes.toBytes(200)));
        SkipFilter filter1 = new SkipFilter(filter2);
        Scan scan = new Scan().setFilter(filter1);
        print_scan(scan);
    }

    /**
     * WhileMatchFilter
     * 当数据被过滤时，则放弃扫描
     */
    @Test
    public void test_05() {
        ValueFilter filter2 = new ValueFilter(CompareOperator.NOT_EQUAL, new BinaryComparator(Bytes.toBytes(200)));
        WhileMatchFilter filter = new WhileMatchFilter(filter2);
        Scan scan = new Scan().setFilter(filter);
        print_scan(scan);
    }

    @Test
    public void test_06() {
        ValueFilter filter = new ValueFilter(CompareOperator.NOT_EQUAL, new BinaryComparator(Bytes.toBytes(200)));
        Scan scan = new Scan().setFilter(filter);
        print_scan(scan);
    }


    @Test
    public void test_07() {
        QualifierFilter filter = new QualifierFilter(CompareOperator.NOT_EQUAL, new BinaryComparator(Bytes.toBytes("age")));
        Scan scan = new Scan().setFilter(filter);
        print_scan(scan);
    }

    @Test
    public void test_08() {
        FamilyFilter filter = new FamilyFilter(CompareOperator.NOT_EQUAL, new BinaryComparator("p".getBytes()));
        Scan scan = new Scan().setFilter(filter);
        print_scan(scan);
    }

    @Test
    public void tes_09() {
        DependentColumnFilter filter = new DependentColumnFilter("p".getBytes(), "zzx".getBytes(), true);
        Scan scan = new Scan().setFilter(filter);
        print_scan(scan);
    }

    @Test
    public void test_10() {
        SingleColumnValueFilter filter = new SingleColumnValueFilter("p".getBytes(), "age".getBytes(), CompareOperator.NOT_EQUAL,
                new BinaryComparator(Bytes.toBytes(200)));
        // 当不匹配时，丢弃
        filter.setFilterIfMissing(true);
        Scan scan = new Scan().setFilter(filter);
        print_scan(scan);
    }

}
