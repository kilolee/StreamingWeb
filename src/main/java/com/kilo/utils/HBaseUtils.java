package com.kilo.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HBase操作工具类
 * Created by kilo on 2018/4/12.
 */
public class HBaseUtils {
    HBaseAdmin admin = null;
    Configuration conf = null;
    private static HBaseUtils instance = null;

    /**
     * 私有构造方法：加载一些必要的参数
     */
    private HBaseUtils() {
        conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "sparksql:2181");
        conf.set("hbase.rootdir", "hdfs://sparksql:8020/hbase");
//        try {
//            admin = new HBaseAdmin(conf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static synchronized HBaseUtils getInstance() {
        if (instance == null) {
            instance = new HBaseUtils();
        }
        return instance;
    }

    /**
     * 根据表明获取HTable实例
     *
     * @param tableName
     * @return
     */
    public HTable getTable(String tableName) {
        HTable table = null;
        try {
            table = new HTable(conf, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 根据表名和输入条件获取HBase的记录
     *
     * @param tableName
     * @param condition
     * @return
     */
    public Map<String, Long> query(String tableName, String condition) throws Exception {
        Map<String, Long> map = new HashMap<>();
        String cf = "info";
        String qualifier = "click_count";

        //获取HBase表对象
        HTable table = HBaseUtils.getInstance().getTable(tableName);

        //设置过滤条件
        Scan scan = new Scan();
        Filter filter = new PrefixFilter(Bytes.toBytes(condition));
        scan.setFilter(filter);

        //根据筛选条件从表中获取结果集
        ResultScanner rs = table.getScanner(scan);
        for (Result result : rs) {
            String row = Bytes.toString(result.getRow());
            long clickCount = Bytes.toLong(result.getValue(cf.getBytes(), qualifier.getBytes()));
            map.put(row, clickCount);
        }
        return map;
    }


    public static void main(String[] args) throws Exception {
        Map<String, Long> map = HBaseUtils.getInstance().query("imooc_course_clickcount", "20180412");

        for (Map.Entry<String, Long> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
